package com.xiyuan.bus.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by xiyuan_fengyu on 2018/6/21 10:28.
 */
@Component
public class Queues {

    public static final String queue_com_xiyuan_test = "com.xiyuan.test";

    // 队列不存在，则新建
    @Bean
    public Queue com_xiyuan_test(){
        return new Queue(queue_com_xiyuan_test);
    }

}
