package com.murong.wjl.testspringboot2.Controller;

import com.murong.wjl.testspringboot2.domain.Bill;
import com.murong.wjl.testspringboot2.mapper.BillMapper;
import com.murong.wjl.testspringboot2.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
@Service
@RequestMapping("t")
public class billController {
    @Autowired
    private BillMapper billMapper;

    @ResponseBody
    @RequestMapping("billinfo/{jrnNo}")
    public Bill getUser(@PathVariable String jrnNo){
        String no= StringUtil.getJrnNo();
        Bill bill = billMapper.getBill(no);
        return bill;
    }

    @ResponseBody
    @RequestMapping("billlist")
    public List<Bill> getAll(){
        List<Bill> list=new ArrayList<Bill>();
        list= billMapper.getAll();
        return list;
    }
    public void sout(){
        System.out.println("test调用");
    }

}
