package kr.or.connect.todo.persistence;

public class TodoSqls {
    static final String DELETE_BY_ID = "DELETE FROM todo WHERE id= :id";
    static final String SELECT_ALL = "SELECT id, todo, completed FROM todo ORDER BY id DESC";
    static final String SELECT_BY_COMPLETED = "SELECT id, todo, completed FROM todo WHERE completed = :completed ORDER BY id DESC";
    static final String SELECT_ONE_BY_ID = "SELECT id, todo, completed FROM todo WHERE id = :id";
    static final String UPDATE = "UPDATE todo SET completed = :completed WHERE id = :id";
}
