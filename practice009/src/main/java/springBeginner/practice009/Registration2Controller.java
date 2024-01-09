package springBeginner.practice009;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;

@RestController
public class Registration2Controller {
    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute RegistData registData,
                                 ModelAndView mv) {
        StringBuilder sb = new StringBuilder();
        sb.append("名前：" + registData.getName());
        sb.append("，パスワード：" + registData.getPassword());
        sb.append("，性別：" + registData.getGender());
        sb.append("，地域：" + registData.getArea());
        sb.append("，興味のある分野：" + Arrays.toString(registData.getInterest()));
        sb.append("，備考：" + registData.getName().replaceAll("\n", ""));

        mv.setViewName("result");
        mv.addObject("registData", sb.toString());
        return mv;
    }
}
