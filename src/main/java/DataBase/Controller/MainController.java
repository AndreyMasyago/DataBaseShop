package DataBase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//First Page and Second Page
@Controller
public class MainController {
    //choose: insert or select
    @GetMapping("/")
    public String greetingPage() {
        return "main";
    }

    //choose which select do u wanna
    @GetMapping("/insert")
    public String insert() {
        return "insert";
    }
}
