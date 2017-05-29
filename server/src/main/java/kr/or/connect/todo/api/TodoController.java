package kr.or.connect.todo.api;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.domain.TodoDto;
import kr.or.connect.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Created by kiost on 2017-05-29.
 */
@Slf4j
@RestController
@RequestMapping("/api/todos")
public class TodoController {
    private TodoService service;

    @Autowired
    public TodoController(TodoService service) {
        this.service = service;
    }

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDto.Response createTodo(@RequestBody TodoDto.Create create) {
        Todo newTodo = service.createTodo(create);
        log.info("created : {}", newTodo);

        return modelMapper.map(newTodo, TodoDto.Response.class);
    }

}
