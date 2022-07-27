package com.qinzi123.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qinzi123.dto.ActivityStandardDto;
import com.qinzi123.dto.ProductStandardDto;

import java.util.List;
import java.util.Map;

/**
 * Created by yj on 2022/7/26 0026 17:24.
 */
public class JsonUtil<T> {

    public static JSONObject mapToJson(Map<String, Object> map) {
        String data = JSON.toJSONString(map);
        return JSON.parseObject(data);
    }

    public static List<ProductStandardDto> productStandardMapToList(Map<String, Object> map, String key) {
        JSONObject jsonObject = mapToJson(map);
        JSONArray array = jsonObject.getJSONArray(key);
        return array.toJavaList(ProductStandardDto.class);
    }

    public static List<ActivityStandardDto> activityStandardMapToList(Map<String, Object> map, String key) {
        JSONObject jsonObject = mapToJson(map);
        JSONArray array = jsonObject.getJSONArray(key);
        return array.toJavaList(ActivityStandardDto.class);
    }
}
