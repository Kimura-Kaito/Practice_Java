package springBeginner.todolist.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import springBeginner.todolist.entity.Todo;
import springBeginner.todolist.form.TodoQuery;

import java.util.List;

public interface TodoDao {
    // JPQLによる検索
    List<Todo> findByJPQL(TodoQuery todoQuery);
//    List<Todo> findByCriteria(TodoQuery todoQuery);
    Page<Todo> findByCriteria(TodoQuery todoQuery, Pageable pageable); // 11.3で追加
}
