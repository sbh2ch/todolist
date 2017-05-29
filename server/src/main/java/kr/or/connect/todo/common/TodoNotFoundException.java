package kr.or.connect.todo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by kiost on 2017-05-29.
 */
@Getter
@AllArgsConstructor
public class TodoNotFoundException extends RuntimeException {
    int id;
}
