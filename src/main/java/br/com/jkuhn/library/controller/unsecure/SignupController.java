package br.com.jkuhn.library.controller.unsecure;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SignupController {

    @GetMapping("/signup")
    public String login() {
        return "signup";
    }

}
