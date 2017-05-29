package kr.or.connect.todo.persistence;

import javax.sql.DataSource;

import kr.or.connect.todo.domain.Todo;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static kr.or.connect.todo.persistence.TodoSqls.*;
import static kr.or.connect.todo.persistence.TodoSqls.DELETE_BY_COMPLETED;

@Repository
public class TodoDao {
    private NamedParameterJdbcTemplate jdbc;
    private SimpleJdbcInsert insertAction;
    private BeanPropertyRowMapper<Todo> rowMapper = BeanPropertyRowMapper.newInstance(Todo.class);

    public TodoDao(DataSource dataSource) {
        this.jdbc = new NamedParameterJdbcTemplate(dataSource);
        this.insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("todo")
                .usingGeneratedKeyColumns("id");
    }

    public Integer insert(Todo Todo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(Todo);
        return insertAction.executeAndReturnKey(params).intValue();
    }

    public List<Todo> selectAll() {
        Map<String, Object> params = Collections.emptyMap();
        return jdbc.query(SELECT_ALL, params, rowMapper);
    }

    public List<Todo> selectByCompleted(int completed) {
        SqlParameterSource param = new MapSqlParameterSource("completed", completed);
        return jdbc.query(SELECT_BY_COMPLETED, param, rowMapper);
    }

    public Todo selectOneById(Integer id) {
        SqlParameterSource params = new MapSqlParameterSource("id", id);
        try {
            return jdbc.queryForObject(SELECT_ONE_BY_ID, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int update(Todo todo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(todo);

        return jdbc.update(UPDATE, params);
    }

    public void deleteByCompleted() {
        jdbc.update(DELETE_BY_COMPLETED, Collections.emptyMap());
    }

    public void deleteById(Integer id) {
        jdbc.update(DELETE_BY_ID, new MapSqlParameterSource("id", id));
    }

}
