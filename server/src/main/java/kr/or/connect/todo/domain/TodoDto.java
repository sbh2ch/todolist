package kr.or.connect.todo.domain;

import lombok.Data;

/**
 * Created by kiost on 2017-05-29.
 */
public class TodoDto {
    @Data
    public static class Create {
        private String todo;
    }

    @Data
    public static class Response {
        private Integer id;
        private String todo;
        private Integer completed;
    }

    @Data
    public static class Update {
        private Integer completed;
    }
}
