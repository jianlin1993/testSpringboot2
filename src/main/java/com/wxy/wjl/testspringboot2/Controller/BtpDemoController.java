package com.wxy.wjl.testspringboot2.Controller;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import com.wxy.wjl.testspringboot2.service.iml.BillServiceIml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

@Controller
@RequestMapping("btp")
public class BtpDemoController {

    ConcurrentHashMap<Integer, Future<Boolean>> result=new ConcurrentHashMap<>();

    @Autowired
    private BillMapper billMapper;
    @Autowired
    BillServiceIml billServiceIml;
    /**
     * 调用执行任务  查出bill所有记录 逐条处理
     */
    @RequestMapping("/start")
    @ResponseBody
    public String getAll( ) throws Exception{
        List<Bill> list=billMapper.getAll();
        for(Bill bill :list){
            //    此处异步调用明细处理
            //     * 异步方法使用条件
            //     * 它必须仅适用于public方法
            //     * 在同一个类中调用异步方法将无法正常工作（self-invocation）
            Future<Boolean> future=billServiceIml.detailProcess(bill.getNo());
            result.put(bill.getNo(),future);
        }
        skip(result);
        return "success";
    }

    // 判断线程是否处理完成
    private void skip(ConcurrentHashMap<Integer, Future<Boolean>> result) throws Exception{
        boolean endFlg=true;
        Iterator<Map.Entry<Integer, Future<Boolean>>> iterator = result.entrySet().iterator();
        while(iterator.hasNext()) {
            Boolean isDone=iterator.next().getValue().isDone();
            System.out.println("no为："+iterator.next().getKey()+"的记录是否处理结束："+isDone);
            if(!isDone){
                endFlg=false;
                //break;
            }
        }
        if(!endFlg){
            Thread.sleep(1000);
            skip(result);
        }
    }


}
