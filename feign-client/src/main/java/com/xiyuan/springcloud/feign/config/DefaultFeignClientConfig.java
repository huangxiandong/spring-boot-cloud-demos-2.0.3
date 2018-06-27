package com.xiyuan.springcloud.feign.config;

import feign.auth.BasicAuthRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by xiyuan_fengyu on 2018/6/20 14:37.
 */
@Component
public class DefaultFeignClientConfig {

    @Value("${spring.security.user.name}")
    private String name;

    @Value("${spring.security.user.password}")
    private String password;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(name, password);
    }

}
