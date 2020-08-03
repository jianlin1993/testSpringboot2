package com.wxy.wjl.testspringboot2.Controller;

import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import com.wxy.wjl.testspringboot2.utils.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
@Service
@RequestMapping("/")
public class billController {
    @Autowired
    private BillMapper billMapper;

    /**
     * 测试并发
     * @param
     * @return
     */
    @Async("asyncServiceExecutor")
    @ResponseBody
    @RequestMapping("test1")
    public String test() throws Exception{
        System.out.println("sleep 2秒");
        Thread.sleep(5000);
        System.out.println("sleep 结束");
        return "test";
    }


    @ResponseBody
    @RequestMapping("billinfo/{jrnNo}")
    public Bill getUser(@PathVariable String jrnNo){
        Bill bill = billMapper.getBill(Integer.parseInt(jrnNo));
        return bill;
    }

/*    @ResponseBody
    @RequestMapping("billinfo3/{jrnNo}")
    public Bill getUser2(@PathVariable String jrnNo){
        Bill bill=null;
        for(int i=0;i<2;i++){
            System.out.println("循环次数i:"+i);
            bill = billMapper.getBill(jrnNo);
            if(bill != null){
                break;
            }
        }
        return bill;
    }*/


    @ResponseBody
    @RequestMapping("billlist/{str}")
    public List<Bill> getAll(@PathVariable String str){
        List<Bill> list=billMapper.getBillList(str);
        return list;
    }
    public void sout(){
        System.out.println("test调用");
    }

    @ResponseBody
    @RequestMapping("/*/*.dom")
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
