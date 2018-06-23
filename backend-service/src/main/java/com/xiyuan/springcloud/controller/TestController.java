package com.xiyuan.springcloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiyuan_fengyu on 2018/6/19 17:10.
 */
@RestController
@RefreshScope
public class TestController {

    @Value("${test.refresh}")
    private String testRefresh;

    @Value("${server.port}")
    private int serverPort;

    @RequestMapping(value = "/test")
    public String test() {
        return "test success from " + serverPort + ": " + testRefresh;
    }

}
