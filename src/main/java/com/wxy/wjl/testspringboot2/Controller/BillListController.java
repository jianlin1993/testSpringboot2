package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.ArrayList;
import java.util.List;

@Controller
@EnableAutoConfiguration
@RequestMapping("hbill")
public class BillListController {

    @Autowired
    private BillMapper billMapper;

    @RequestMapping("/billlist1")
    public String getAll(Model model){
        List<Bill> list=new ArrayList<Bill>();
        list= billMapper.getAll();
        model.addAttribute("list",list);
        return "billList";
    }
}
