package com.xiyuan.springcloud.controller;

import com.xiyuan.springcloud.bean.AmqpSender;
import com.xiyuan.springcloud.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiyuan_fengyu on 2018/6/19 17:10.
 */
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private AmqpSender amqpSender;

    @RequestMapping(value = "/test")
    public Object test(String msg) {
        return testService.test(msg);
    }

    @RequestMapping(value = "/test/amqp")
    public Object testAmqp(String msg) {
        amqpSender.justTest(msg == null ? "" : msg);
        return "success";
    }

}
