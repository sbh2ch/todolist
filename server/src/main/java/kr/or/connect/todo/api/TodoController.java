package kr.or.connect.todo.api;

import kr.or.connect.todo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
