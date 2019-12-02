package com.wonear.common.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口创建工厂
 */
public class RetrofitFactary {

    public static Retrofit newClient(String host) {
        return new Retrofit.Builder()
                .baseUrl(host)  //自己配置
                .client(HttpCenter.getInstance().getOkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
