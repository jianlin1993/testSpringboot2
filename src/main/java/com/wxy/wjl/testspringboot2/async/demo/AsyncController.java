package com.wxy.wjl.testspringboot2.async.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;

/**
 * 测试有返回值的异步服务
 */
@Service
@RequestMapping("/")
public class AsyncController {
    @Autowired
    AsyncTaskService asyncTaskService;
    CopyOnWriteArrayList resultList=new CopyOnWriteArrayList<>();

    @ResponseBody
    @RequestMapping("async")
    public String testAsync() throws Exception{
        for(int i=1;i<30;i++){
            Future<String> future= asyncTaskService.doTask1(String.valueOf(i));
            resultList.add(future);
        }
        //判断异步任务执行结果
        skip(resultList);
        System.out.println("所有任务完成");
        return "success";
    }

    private void skip(CopyOnWriteArrayList<Future> resultList) throws Exception{
        boolean endFlg=true;
        Iterator<Future> iterator = resultList.iterator();
        int i=-1;
        while(iterator.hasNext()) {
            i++;
            Future future=iterator.next();
            Boolean result=future.isDone();
            if(!result){
                endFlg=false;
                break;
            }else{
                System.out.println("任务 "+future.get().toString()+"完成");
                resultList.remove(i);
            }
        }
        if(!endFlg){
            Thread.sleep(1000);
            skip(resultList);
        }
    }
}
