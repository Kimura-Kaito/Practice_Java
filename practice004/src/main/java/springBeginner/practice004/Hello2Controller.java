package springBeginner.practice004;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello2Controller {
    @GetMapping("Hello2")
    public String sayHello(
            @RequestParam("name") String name) {
        return "Hello, World! " + "こんにちは " + name + "さん!";
    }
}
