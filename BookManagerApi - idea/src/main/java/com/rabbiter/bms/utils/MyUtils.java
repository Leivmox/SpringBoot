package com.rabbiter.bms.utils;

import java.util.Map;

public class MyUtils {

    // 解析分页参数并添加到 params 中，以便在数据库查询中使用
    // params 是从前端传入的分页和查询参数
    public static void parsePageParams(Map<String, Object> params) {
        // 从 params 中获取前端传递的当前页码，并转换为整数
        int page = Integer.parseInt((String) params.get("page"));

        // 从 params 中获取前端传递的每页显示条数，并转换为整数
        int size = Integer.parseInt((String) params.get("limit"));

        // 确保页码不小于1，如果 page < 1，则将其设置为 1
        page = Math.max(page, 1);

        // 计算数据库查询的起始记录位置（begin），并存入 params
        // begin = (page - 1) * size, 例如 page=2, size=10，则 begin=10
        params.put("begin", (page - 1) * size);

        // 将每页条数（size）存入 params，供数据库查询使用
        params.put("size", size);
    }

}
