package com.xiyuan.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiyuan_fengyu on 2018/6/19 17:10.
 */
@RestController
public class TestController {

    @Value("${server.port}")
    private int serverPort;

    @RequestMapping(value = "/test")
    public Object test() {
        return "test success from " + serverPort;
    }

}
