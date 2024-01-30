package springBeginner.todolist.controller;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import springBeginner.todolist.dao.TodoDaoImpl;
import springBeginner.todolist.entity.Todo;
import springBeginner.todolist.form.TodoData;
import springBeginner.todolist.form.TodoQuery;
import springBeginner.todolist.repository.TodoRepository;
import springBeginner.todolist.service.TodoService;

import java.util.List;

@Controller
//@AllArgsConstructor
@RequiredArgsConstructor
public class TodoListController {
    private final TodoRepository todoRepository;
    private final TodoService todoService; // Todolist2で追加
    private final HttpSession session; // 8章で追加

    // todolist5で追加
    @PersistenceContext
    private EntityManager entityManager;
    TodoDaoImpl todoDaoImpl;

    @PostConstruct
    public void init() {
        todoDaoImpl = new TodoDaoImpl(entityManager);
    }

    // ToDo一覧表示
    @GetMapping("/todo")
    public ModelAndView showTodoList(ModelAndView mv,
                                     Pageable pageable) { // 11章で追加
        // 一覧を検索して表示する
        mv.setViewName("todoList");
        List<Todo> todoList = todoRepository.findAll();
        mv.addObject("todoList", todoList);
        mv.addObject("todoQuery", new TodoQuery()); // ※Todolist4で追加
        return mv;
    }

    // ToDo入力フォーム表示(Todolist2で追加)
    // 【処理1】ToDo一覧画面で[新規追加]リンクがクリックされたとき
    @PostMapping("/todo/create/form")
    public ModelAndView createTodo(ModelAndView mv) {
        mv.setViewName("todoForm");
        mv.addObject("todoData", new TodoData());
        session.setAttribute("mode", "create");
        return mv;
    }

    // ToDo追加処理(Todolist2で追加)
    // 【処理2】ToDo入力画面で[登録]ボタンがクリックされたとき
    @PostMapping("/todo/create/do")
    public String createTodo(@ModelAttribute @Validated TodoData todoData,
                             BindingResult result, Model model) {
    /*
    public ModelAndView createTodo(@ModelAttribute @Validated TodoData todoData,
                                   BindingResult result,
                                   ModelAndView mv) {
     */

        // エラーチェック
        boolean isValid = todoService.isValid(todoData, result);
        if (!result.hasErrors() && isValid) {
            // エラーなし
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
//            return showTodoList(mv);
            return "redirect:/todo";
        } else {
            // エラーあり
//            mv.setViewName("todoForm");
            // mv.addObject("todoData", todoData);
//            return mv;
            return "todoForm";
        }
    }

    // ToDo一覧へ戻る(Todolist2で追加)
    // 【処理3】ToDo入力画面で[キャンセル登録]ボタンがクリックされたとき
    @PostMapping("/todo/cancel")
    public String cancel() {
        return "redirect:/todo";
    }

    // 既存ToDoの入力フォーム表示
    @GetMapping("/todo/{id}")
    public ModelAndView todoById(@PathVariable(name = "id") int id, ModelAndView mv) {
        mv.setViewName("todoForm");
        Todo todo = todoRepository.findById(id).get();
        mv.addObject("todoData", todo);
        session.setAttribute("mode", "update");
        return mv;
    }

    // ToDo更新処理(8章で追加)
    @PostMapping("/todo/update")
    public String updateTodo(@ModelAttribute @Validated TodoData todoData,
                             BindingResult result,
                             Model model) {

        // エラーチェック
        boolean isValid = todoService.isValid(todoData, result);
        if (!result.hasErrors() && isValid) {
            // エラーなし
            Todo todo = todoData.toEntity();
            todoRepository.saveAndFlush(todo);
            return "redirect:/todo";
        } else {
            // エラーあり
            // model.addAttribute("todoData", todoData);
            return "todoForm";
        }
    }

    // ToDo削除処理
    @PostMapping("/todo/delete")
    public String deleteTodo(@ModelAttribute TodoData todoData) {
        todoRepository.deleteById(todoData.getId());
        return "redirect:/todo";
    }

    // フォームに入力された条件でtodoを検索(todolist4で追加,todolist5で変更)
    @PostMapping("/todo/query")
    public ModelAndView queryTodo(@ModelAttribute TodoQuery todoQuery,
                                  BindingResult result, ModelAndView mv) {
        mv.setViewName("todoList");

        List<Todo> todoList = null;
        if (todoService.isValid(todoQuery, result)) {
            // エラーがなければ検索
//            todoList = todoService.doQuery(todoQuery);
            // ↓
            // JPQLによる検索
//            todoList = todoDaoImpl.findByJPQL(todoQuery);

            // Criteriaによる検索
            todoList = todoDaoImpl.findByCriteria(todoQuery);

        }

//        mv.addObject("todoQuery", todoQuery);
        mv.addObject("todoList", todoList);
        return mv;
    }
}
