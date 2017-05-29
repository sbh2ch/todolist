package kr.or.connect.todo.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.domain.TodoDto;
import kr.or.connect.todo.service.TodoService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kiost on 2017-05-29.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Transactional
public class TodoControllerTest {
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TodoService service;

    private MockMvc mockMvc;

    private TodoDto.Create todoCreateFixture(String todo) {
        TodoDto.Create createDto = new TodoDto.Create();
        createDto.setTodo(todo);

        return createDto;
    }

    private TodoDto.Update todoUpdateFixture() {
        TodoDto.Update updateDto = new TodoDto.Update();
        updateDto.setCompleted(1);

        return updateDto;
    }

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createTodo() throws Exception {
        TodoDto.Create createDto = todoCreateFixture("create test");

        ResultActions result = mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createDto)));
        result.andDo(print());
        result.andExpect(status().isCreated());
    }

    @Test
    public void getTodos() throws Exception {
        TodoDto.Create createDto = todoCreateFixture("get all todo test");
        service.createTodo(createDto);
        service.createTodo(createDto);

        ResultActions result = mockMvc.perform(get("/api/todos"));

        result.andDo(print());
        result.andExpect(status().isOk());
    }

    @Test
    public void getCompletedTodos() throws Exception {
        Todo sample = service.createTodo(todoCreateFixture("completed todo"));
        service.createTodo(todoCreateFixture("uncompleted todo"));

        service.updateTodo(sample.getId(), todoUpdateFixture());

        ResultActions result = mockMvc.perform(get("/api/todos/completed"));

        result.andDo(print());
        result.andExpect(status().isOk());
    }

    @Test
    public void getActiveTodos() throws Exception {
        Todo sample = service.createTodo(todoCreateFixture("completed todo"));
        service.createTodo(todoCreateFixture("uncompleted todo"));

        service.updateTodo(sample.getId(), todoUpdateFixture());

        ResultActions result = mockMvc.perform(get("/api/todos/active"));

        result.andDo(print());
        result.andExpect(status().isOk());
    }

    @Test
    public void updateTodo() throws Exception {
        TodoDto.Create create = todoCreateFixture("update todo test");
        Todo createdTodo = service.createTodo(create);
        TodoDto.Update update = todoUpdateFixture();

        ResultActions result = mockMvc.perform(put("/api/todos/" + createdTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));
        result.andExpect(status().isOk());
    }

    @Test
    public void updateTodo_NotFound() throws Exception {
        TodoDto.Update update = todoUpdateFixture();

        ResultActions result = mockMvc.perform(put("/api/todos/-4")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(update)));
        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void deleteTodoByCompleted() throws Exception {
        TodoDto.Create create = todoCreateFixture("delete todo by completed test");
        Todo createdTodo = service.createTodo(create);
        TodoDto.Update updateTodo = todoUpdateFixture();
        service.updateTodo(createdTodo.getId(), updateTodo);

        ResultActions result = mockMvc.perform(delete("/api/todos"));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteTodoById() throws Exception {
        TodoDto.Create create = todoCreateFixture("deleteTodo By id test");
        Todo createdTodo = service.createTodo(create);

        ResultActions result = mockMvc.perform(delete("/api/todos/" + createdTodo.getId()));
        result.andExpect(status().isNoContent());
    }

    @Test
    public void deleteTodoById_NotFound() throws Exception {
        ResultActions result = mockMvc.perform(delete("/api/todos/-4"));
        result.andExpect(status().isBadRequest());
    }
}
