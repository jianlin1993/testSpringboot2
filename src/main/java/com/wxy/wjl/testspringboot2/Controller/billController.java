package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import com.wxy.wjl.testspringboot2.utils.MDCUtils;
import com.wxy.wjl.testspringboot2.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@Service
@RequestMapping("/")
public class billController {
    @Autowired
    private BillMapper billMapper;


    @ResponseBody
    @RequestMapping("billinfo/{jrnNo}")
    public Bill getUser(@PathVariable String jrnNo){
        System.out.println("TRACE_ID="+MDCUtils.getTraceId());
        Bill bill = billMapper.getBill(Integer.parseInt(jrnNo));
        return bill;
    }

    @ResponseBody
    @RequestMapping("billlist/{str}")
    public List<Bill> getAll(@PathVariable String str){
        List<Bill> list=billMapper.getBillList(str);
        return list;
    }


    @ResponseBody
    @RequestMapping("getBillByNo")
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
