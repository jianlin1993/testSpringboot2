package com.wxy.wjl.testspringboot2.Controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@EnableAutoConfiguration
public class formController {

    @RequestMapping("/formController")
    private String demo(Model model, HttpServletRequest request) {
        System.out.println("进入form");
        String a=request.getParameter("a");
        System.out.println(a);
        model.addAttribute("a",a);
        return "6";
    }
}

