package com.wonear.common.base.ui;

import android.app.Activity;
import android.content.Context;

import com.uber.autodispose.AutoDisposeConverter;

public interface BaseView {


    /**
     * 获取当前上下文
     *
     * @return
     */
    Context getCurContext();

    /**
     * 获取当前界面
     *
     * @return
     */
    Activity getCurActivity();

    /**
     * 显示加载弹窗
     */
    void showLoading();

    /**
     * 隐藏加载弹窗
     */
    void hideLoading();


    void loadError();

    /**
     * 绑定Android生命周期 防止RxJava内存泄漏
     *
     * @param <T>
     * @return
     */
    <T> AutoDisposeConverter<T> bindAutoDispose();


    /**
     * 判断是否又某些权限
     *
     * @param perms 要判断的权限
     * @return 是否有权限
     */
    boolean hasPermission(String... perms);

    /**
     * 请求权限
     *
     * @param rationale   请求权限时的弹窗解释信息
     * @param requestCode 请求码，自己设置
     * @param perms       请求的具体权限
     */
    void requestPermission(String rationale, int requestCode, String... perms);


}
