package tdtu.edu.ex01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String goHomePage(){
        return "index";
    }
    @GetMapping("/contact")
    public String getContact(){
        return "contact";
    }
    @PostMapping("/contact")
    public String postContact(){
        return "contact";
    }

    @GetMapping("/about")
    public String getAbout(){
        return "about";
    }
    @PostMapping("/about")
    public String postAbout(){
        return "about";
    }
}
