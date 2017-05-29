package kr.or.connect.todo.api;

import kr.or.connect.todo.domain.Todo;
import kr.or.connect.todo.domain.TodoDto;
import kr.or.connect.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto.Response> getTodos() {
        return service.getAllTodos().stream()
                .map(todo -> modelMapper.map(todo, TodoDto.Response.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/completed")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto.Response> getCompletedTodos() {
        return service.getTodosByCompleted(1).stream()
                .map(todo -> modelMapper.map(todo, TodoDto.Response.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/active")
    @ResponseStatus(HttpStatus.OK)
    public List<TodoDto.Response> getActiveTodos() {
        return service.getTodosByCompleted(0).stream()
                .map(todo -> modelMapper.map(todo, TodoDto.Response.class))
                .collect(Collectors.toList());
    }

}
