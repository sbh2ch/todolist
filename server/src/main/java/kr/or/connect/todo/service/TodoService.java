package kr.or.connect.todo.service;

import kr.or.connect.todo.persistence.TodoDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by kiost on 2017-05-29.
 */
@Slf4j
@Service
public class TodoService {
    private TodoDao dao;

    public TodoService(TodoDao dao) {
        this.dao = dao;
    }

}
