package com.rabbiter.bms.utils;

import java.util.HashMap;

public class MyResult {

//    用于没有数据的简单响应
    public static HashMap<String, Object> getResultMap(Integer status, String message) {
        return new HashMap<String, Object>() {
            {
                put("status", status);
                put("message", message);
                put("timestamp", System.currentTimeMillis());
            }
        };
    }
//用于返回带数据的响应
    public static HashMap<String, Object> getResultMap(Integer status, String message, Object data) {
        return new HashMap<String, Object>() {
            {
                put("status", status);
                put("message", message);
                put("data", data);
                put("timestamp", System.currentTimeMillis());
            }
        };
    }
//用于分页或列表数据的响应，包含数据总数。
    public static HashMap<String, Object> getListResultMap(Integer status, String message, Integer count, Object data) {
        return new HashMap<String, Object>() {
            {
                put("code", status);
                put("message", message);
                put("count", count);
                put("data", data);
            }
        };
    }
}
