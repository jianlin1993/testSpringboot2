package com.wxy.wjl.testspringboot2.service.iml;

import com.alibaba.fastjson.JSON;
import com.wxy.wjl.testspringboot2.domain.Bill;
import com.wxy.wjl.testspringboot2.mapper.BillMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

@Service
public class BillServiceIml {

    @Autowired
    private BillMapper billMapper;

    /**
     * 异步方法使用条件
     * 它必须仅适用于public方法
     * 在同一个类中调用异步方法将无法正常工作（self-invocation）
     * @param no
     * @throws Exception
     */
    @Async("asyncServiceExecutor")
    public Future<Boolean> detailProcess(int no) throws Exception{
        Bill bill=billMapper.getBillByNo(no);
        Thread.sleep(3000);
        System.out.println(JSON.toJSONString(bill));
        return new AsyncResult<Boolean>(true);
    }

}
