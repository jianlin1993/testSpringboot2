package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.ArrayList;
import java.util.List;

@Api(value = "controller")
@Controller
@EnableAutoConfiguration
@RequestMapping("/*")
public class BillListController {

    @Autowired
    private BillMapper billMapper;

    @ApiOperation(value="获取所有bill", notes="getAll", produces="application/json",protocols="http",httpMethod="POST")
    @RequestMapping("/getAll")
    public String getAll(Model model){
        List<Bill> list=new ArrayList<Bill>();
        list= billMapper.getAll();
        model.addAttribute("list",list);
        return "billList";
    }

    @ResponseBody
    @RequestMapping("/getAllBill")
    public  List<Bill> getAllBill(){
        List<Bill> list=new ArrayList<Bill>();
        list= billMapper.getAll();
        return list;
    }
}
