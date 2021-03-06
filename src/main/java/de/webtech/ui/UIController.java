package de.webtech.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UIController {
    @RequestMapping({
            "/login",
            "/register",
            "/dashboard",
            "/todo",
            "/todo/{id}"
    })
    public String forwardToAngular(){
        return "forward:/index.html";
    }
}
