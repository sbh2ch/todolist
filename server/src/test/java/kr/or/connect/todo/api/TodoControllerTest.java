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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
}
