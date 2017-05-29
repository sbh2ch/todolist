package kr.or.connect.todo.service;

import kr.or.connect.todo.common.TodoNotFoundException;
import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.domain.TodoDto;
import kr.or.connect.todo.persistence.TodoDao;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by kiost on 2017-05-29.
 */
@Slf4j
@Service
public class TodoService {
    private TodoDao dao;

    @Autowired
    private ModelMapper modelMapper;

    public TodoService(TodoDao dao) {
        this.dao = dao;
    }

    public Todo createTodo(TodoDto.Create create) {
        Todo todo = modelMapper.map(create, Todo.class);
        todo.setDate(new Date());
        todo.setCompleted(0);
        int id = dao.insert(todo);
        todo.setId(id);

        return todo;
    }

    public List<Todo> getAllTodos() {
        return dao.selectAll();
    }

    public List<Todo> getTodosByCompleted(int completed) {
        return dao.selectByCompleted(completed);
    }

    public Todo getTodo(Integer id) {
        Todo todo = dao.selectOneById(id);
        if (todo == null) {
            log.error("todo not found exception!");
            throw new TodoNotFoundException(id);
        }

        return todo;
    }

    public int updateTodo(Integer id, TodoDto.Update update) {
        Todo todo = getTodo(id);
        todo.setCompleted(update.getCompleted());

        return dao.update(todo);
    }

    public void deleteTodoById(Integer id) {
        Todo todo = getTodo(id);
        dao.deleteById(todo.getId());
    }

    public void deleteByCompleted() {
        dao.deleteByCompleted();
    }
}
