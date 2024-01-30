package springBeginner.todolist.dao;

import springBeginner.todolist.entity.Todo;
import springBeginner.todolist.form.TodoQuery;

import java.util.List;

public interface TodoDao {
    // JPQLによる検索
    List<Todo> findByJPQL(TodoQuery todoQuery);
    List<Todo> findByCriteria(TodoQuery todoQuery);
}
