package com.wonear.common.utils;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局上下文保存和获取
 * 注意：这边的上下文是全局的context（ApplicationContext）,不能转成activity的使用场景，否则报错
 */
public class ContextManager {

    private static Map<String, Object> configMap = new HashMap<>();

    public static void init(Context context) {
        configMap.put("context", context);
    }

    public static Context getContext() {
        return (Context) configMap.get("context");
    }


}
