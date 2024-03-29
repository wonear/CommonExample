package com.wonear.common.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {


    /**
     * 字符串转成对象
     *
     * @param json  JsonObject类型的字符串
     * @param clazz 模型class
     * @return 模型
     */
    public static <T> T strToModel(String json, Class<T> clazz) {
        try {
            return new Gson().fromJson(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }


    /**
     * 模型转成json字符串
     *
     * @param model 模型
     * @return 字符串
     */
    public static String moderToString(Object model) {
        return new Gson().toJson(model);
    }

    public static JsonObject moderToJsonObject(Object model) {
        String s = moderToString(model);
        return stringToJson(s);
    }

    /**
     * 字符串转json
     *
     * @param str
     * @return
     */
    public static JsonObject stringToJson(String str) {
        return new Gson().fromJson(str, JsonObject.class);

    }


    /**
     * json 转数组
     *
     * @param json  json字符串
     * @param clazz 模型
     * @param <T>   模型
     * @return 模型集合
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        JsonElement element = new JsonParser().parse(json);
        if (element.isJsonArray()) {
            JsonArray jsonArray = element.getAsJsonArray();
            for (JsonElement object : jsonArray) {
                list.add(new Gson().fromJson(object, clazz));
            }
        }
        return list;
    }


    /**
     * jsonObject转map
     *
     * @param json 要转成map的json字符串
     * @return map对象
     */
    public static Map<String, Object> jsonToMap(String json) {
        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();

        Map<String, Object> mapTemp = new Gson().fromJson(json, type);
        return mapTemp;
    }

    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        return (T) obj;
    }


}
