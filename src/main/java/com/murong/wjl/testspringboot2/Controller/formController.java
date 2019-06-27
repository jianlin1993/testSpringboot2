package com.murong.wjl.testspringboot2.Controller;

import com.sun.tracing.dtrace.ModuleAttributes;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

