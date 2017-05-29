package kr.or.connect.todo.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by kiost on 2017-05-29.
 */
@Data
@AllArgsConstructor
public class ErrorResponse {
    private String errorCode;
    private String message;
}
