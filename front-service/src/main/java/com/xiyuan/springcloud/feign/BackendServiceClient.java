package com.xiyuan.springcloud.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by xiyuan_fengyu on 2018/6/20 14:17.
 */
@FeignClient("backend-service")
public interface BackendServiceClient {

    @RequestMapping(method = RequestMethod.GET, value = "/test")
    String test();

}
