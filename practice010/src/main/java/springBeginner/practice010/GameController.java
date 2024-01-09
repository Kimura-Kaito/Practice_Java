package springBeginner.practice010;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class GameController {

    @Autowired
    HttpSession session;

    @GetMapping("/")
    public String index() {
        session.invalidate();
        Random rnd = new Random();
        int answer = rnd.nextInt(100) + 1;
        session.setAttribute("answer", answer);
        System.out.println("answer=" + answer);
        return "game";
    }

    @PostMapping("/challenge")
    public ModelAndView challenge(@RequestParam("number") int number,
                                  ModelAndView mv) {

        int answer = (Integer) session.getAttribute("answer");

        @SuppressWarnings("unchecked")
        List<History> histories = (List<History>) session.getAttribute("histories");
        if (histories == null) {
            histories = new ArrayList<>();
            session.setAttribute("histories", histories);
        }

        if (answer < number) {
            histories.add(new History(histories.size() + 1, number, "もっと小さいです"));
        } else if (answer == number) {
            histories.add(new History(histories.size() + 1, number, "正解です！"));
        } else {
            histories.add(new History(histories.size() + 1, number, "もっと大きいです"));
        }

        mv.setViewName("game");
        mv.addObject("histories", histories);
        return mv;
    }
}
