package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import com.wxy.wjl.testspringboot2.utils.StrUtil;
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
        String no= StrUtil.getJrnNo();
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

    @ResponseBody
    @RequestMapping("billinfo2/{jrnNo}")
    public String getNo(){
        String no= StrUtil.getJrnNo();
        return no;
    }


    @ResponseBody
    @RequestMapping("bill3")
    public Bill getBillByNo(){
        String no= StrUtil.getJrnNo();
        Bill bill = billMapper.getBillByNo(1);
        return bill;
    }
    @ResponseBody
    @RequestMapping("add")
    public String add(){
        Bill bill=new Bill();
        bill.setRemark("test");
        bill.setCnlNo("1");
        bill.setTxAmt("1");
        bill.setTxDt("20190708");
        bill.setUsrNo("001");
        bill.setNo(1);
        bill.setTxTyp("1");
        int res= billMapper.add(bill);
        return String.valueOf(res);
    }
}
