package com.wonear.common.network;

import com.wonear.common.utils.LogUtil;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.concurrent.TimeUnit;

/**
 * 网络请求，创建请求器
 */
public class HttpCenter {

    private OkHttpClient.Builder builder;
    private OkHttpClient okHttpClient;
    private TimeUnit mTimeUnit = TimeUnit.SECONDS;//时间单位，s
    private long mConnectTimeout = 60;//设置连接时间
    private long mReadTimeout = 60;//设置读取时间
    private long mWriteTimeout = 60;//设置写入时间


    private HttpCenter() {
        builder = new OkHttpClient.Builder();
    }


    private static class HttpCenterHolder {
        private static HttpCenter INSTANCE = new HttpCenter();
    }

    public static HttpCenter getInstance() {
        return HttpCenterHolder.INSTANCE;
    }

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     * @return HttpCenter
     */
    public HttpCenter addInterceptor(Interceptor interceptor) {
        builder.addInterceptor(interceptor);
        return this;
    }

    /**
     * 设置连接超时时间
     *
     * @param timeout 超时时间,秒为单位
     * @return HttpCenter
     */
    public HttpCenter connectTimeout(long timeout) {
        this.mConnectTimeout = timeout;
        return this;

    }

    /**
     * 设置连接读取时间
     *
     * @param timeout 超时时间，秒为单位
     * @return HttpCenter
     */
    public HttpCenter readTimeout(long timeout) {
        this.mReadTimeout = timeout;
        return this;

    }

    /**
     * 设置写时间
     *
     * @param timeout 超时时间，秒为单位
     * @return HttpCenter
     */
    public HttpCenter writeTimeout(long timeout) {
        this.mWriteTimeout = timeout;
        return this;

    }

    /**
     * 缓存
     *
     * @param cache 设置缓存器
     * @return HttpCenter
     */
    public HttpCenter cache(Cache cache) {
        builder.cache(cache);
        return this;
    }


    /**
     * 创建请求器
     *
     * @return HttpCenter
     */
    public HttpCenter create() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {

            @Override
            public void log(String message) {
                LogUtil.d("AppLog", message);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = builder.addInterceptor(loggingInterceptor)
                .connectTimeout(mConnectTimeout, mTimeUnit)
                .readTimeout(mReadTimeout, mTimeUnit)
                .writeTimeout(mWriteTimeout, mTimeUnit)
                .build();
        return this;
    }


    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }


    public <T> T createRepertory(String host, Class<T> clazz) {
        return RetrofitFactary.newClient(host).create(clazz);
    }


}
