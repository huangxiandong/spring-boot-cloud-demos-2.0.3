package com.xiyuan.springcloud.receiver;

import com.xiyuan.bus.message.TestMsg;
import com.xiyuan.bus.queue.Queues;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by xiyuan_fengyu on 2018/6/21 10:20.
 */
@Component
@RabbitListener(queues = Queues.queue_com_xiyuan_test)
public class TestReceiver {

    @RabbitHandler
    public void receive(TestMsg msg) {
        System.out.println("receive: " + msg);
    }

}
