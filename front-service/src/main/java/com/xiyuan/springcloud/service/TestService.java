package com.xiyuan.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xiyuan.springcloud.feign.client.BackendServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xiyuan_fengyu on 2018/6/20 10:46.
 */
@Service
public class TestService {

    @Autowired
    private BackendServiceClient backendServiceClient;

    @HystrixCommand(fallbackMethod = "defaultFallback")
    public Object test(String msg) {
        return backendServiceClient.test(msg == null ? "just test" : msg);
    }

    public Object defaultFallback(String msg) {
        Map<String, Object> res = new LinkedHashMap<>();
        res.put("success", false);
        res.put("message", msg);
        return res;
    }

}
