package com.xiyuan.springcloud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xiyuan_fengyu on 2018/6/19 17:10.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test")
    public Object test() {
        return "test";
    }

}
