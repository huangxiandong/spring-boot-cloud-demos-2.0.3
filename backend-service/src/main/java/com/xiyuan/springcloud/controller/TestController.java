package com.xiyuan.springcloud.controller;

import com.xiyuan.springcloud.mybatis.dao.LogDao;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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

    @Autowired
    private LogDao logDao;

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value = "/test")
    public String test(String msg) {
        return "test success from " + serverPort + ": " + testRefresh + " msg: " + msg;
    }

    @RequestMapping(value = "/test/log")
    public Object testLog(long id) {
        return logDao.selectById(id);
    }

    @RequestMapping(value = "/test/redis")
    public Object testRedis() {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get("test");
        }
        catch (Exception e) {
            return "fail";
        }
    }

    @RequestMapping(value = "/test/mongo")
    public Object testMongo() {
        try {
            return mongoTemplate.count(new BasicQuery("{}"), "users");
        }
        catch (Exception e) {
            return 0;
        }
    }

}
