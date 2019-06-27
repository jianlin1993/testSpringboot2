package com.murong.wjl.testspringboot2.Controller;

import com.murong.wjl.testspringboot2.domain.Student;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class fuckController {

        @RequestMapping("/fuck")
        private Student fuck() {
            Student student=new Student();
            student.setClassid("1309");
            student.setE_mail("1015894275@qq.com");
            student.setId("2013066");
            student.setPhone("13426332201");
            return student;
        }
}
