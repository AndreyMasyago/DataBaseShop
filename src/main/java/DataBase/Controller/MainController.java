package DataBase.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/api/")
    public String greetingPage() {
        return "main";
    }

    @GetMapping("/api/insert")
    public String insert() {
        return "insert";
    }
}
