package com.xiyuan.springcloud.bus.message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

/**
 * Created by xiyuan_fengyu on 2018/6/21 11:07.
 */
public class TestMsg implements Serializable {

    private static final long serialVersionUID = 8716474112416560223L;

    private static final Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .setPrettyPrinting()
            .create();

    private String id;

    private Object msg;

    public TestMsg(Object msg) {
        this.id = System.currentTimeMillis() + "_" + (int)(Math.random() * 10000);
        this.msg = msg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return gson.toJson(this);
    }
}
