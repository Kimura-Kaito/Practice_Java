package springBeginner.todolist.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import springBeginner.todolist.entity.Todo;
import springBeginner.todolist.repository.TodoRepository;

import java.util.List;

@Controller
@AllArgsConstructor
public class TodoListController {
    private final TodoRepository todoRepository;

    @GetMapping("/todo")
    public ModelAndView showTodoList(ModelAndView mv) {

        mv.setViewName("todoList");
        List<Todo> todoList = todoRepository.findAll();
        mv.addObject("todoList", todoList);
        return mv;
    }
}
