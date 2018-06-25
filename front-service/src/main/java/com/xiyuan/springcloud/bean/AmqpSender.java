package com.xiyuan.springcloud.bean;

import com.xiyuan.springcloud.bus.message.TestMsg;
import com.xiyuan.springcloud.bus.queue.Queues;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by xiyuan_fengyu on 2018/6/21 10:10.
 */
@Component
public class AmqpSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void justTest(Object msg) {
        amqpTemplate.convertAndSend(Queues.queue_com_xiyuan_test, new TestMsg(msg));
    }

}
