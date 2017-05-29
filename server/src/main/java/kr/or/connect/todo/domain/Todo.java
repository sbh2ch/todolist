package kr.or.connect.todo.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by kiost on 2017-05-29.
 */
@Data
public class Todo {
    private Integer id;
    private String todo;
    private Integer completed;
    private Date date;
}
