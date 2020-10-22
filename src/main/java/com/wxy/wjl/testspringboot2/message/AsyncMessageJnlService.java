package com.wxy.wjl.testspringboot2.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wxy.wjl.testspringboot2.mapper.BillJnlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;


@Slf4j
@Service
public class AsyncMessageJnlService implements InitializingBean, DisposableBean, Runnable {

    @Autowired
    private BillJnlMapper billJnlMapper;

    /** 执行状态 */
    private volatile boolean         running    = true;

    /** 阻塞队列的长度 */
    private static final int         QUEUE_SIZE = 2000;

    /** 阻塞队列 */
    private BlockingQueue<BillJnlDO> queue      = new ArrayBlockingQueue<>(QUEUE_SIZE);

    @Override
    public void destroy() {
        running = false;
    }

    @Override
    public void afterPropertiesSet() {
        CustomizableThreadFactory threadFactory = new CustomizableThreadFactory("msc-jnl-");
        ThreadPoolExecutor serviceExecutor = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.SECONDS,
            new SynchronousQueue<>(), threadFactory, new ThreadPoolExecutor.AbortPolicy());
        serviceExecutor.execute(this);
    }

    @Override
    public void run() {
        while (running) {
            BillJnlDO mscMsgJnl=null;
            try {
                mscMsgJnl = queue.take();
                mscMsgJnl.setCreDt(new SimpleDateFormat("yyyyMMdd").format(new Date()));
                //log.info("INFO insert mscMsgJnl = ", JSON.toJSONString(mscMsgJnl));
                System.out.println("INFO insert mscMsgJnl = "+ JSON.toJSONString(mscMsgJnl));
                System.out.println("JRN_NO ="+mscMsgJnl.getJrnNo());
                billJnlMapper.add(mscMsgJnl);
            } catch (Exception e) {
                if(mscMsgJnl != null){
                    log.error("insert mscMsgJnl = ", JSON.toJSONString(mscMsgJnl));
                }
                log.error("async insert msc message inf error", e);
            }
        }
        log.info("{} message jnls exsit in the queue, they will be abandon.", queue.size());
    }

    /**
     * 向阻塞队列中添加消息流水，如果队列已满，直接丢弃
     * @param mscMsgJnl       消息流水对象
     * @param sendData       未加密的待发送数据
     */
    public void offer(BillJnlDO mscMsgJnl, JSONObject sendData) {
        try {
            boolean success = queue.offer(mscMsgJnl);
            log.info("offer msc msg jnl into queue {}", success);
        } catch (Exception e) {
            log.error("offer message jnl data into queue error, message: {}", sendData, e);
        }
    }

}
