package com.wonear.common.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wonear.common.R;
import com.wonear.common.utils.LogUtil;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements BaseView, EasyPermissions.PermissionCallbacks {

    private Unbinder mUnbinder;
    private MaterialDialog mDialog;

    private boolean enableBaseStatusSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (layoutId() != 0) {
            setContentView(layoutId());
            mUnbinder = ButterKnife.bind(this);
            initView();
        }

        LogUtil.e("activity", getClass().getName());
    }

    @Override
    public Context getCurContext() {
        return this;
    }

    @Override
    public Activity getCurActivity() {
        return this;
    }

    protected abstract int layoutId();


    protected void initView() {

    }


    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(this)
                    .backgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                    .customView(R.layout.dialog_loading, false)
                    .build();
            Window window = mDialog.getWindow();
            if (window == null) return;
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.setDimAmount(0);
        }

        if (mDialog.isShowing()) return;
        mDialog.show();


    }

    @Override
    public void hideLoading() {
        if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
    }


    @Override
    public void loadError() {

    }

    @Override
    public <T> AutoDisposeConverter<T> bindAutoDispose() {
        return AutoDispose.autoDisposable(AndroidLifecycleScopeProvider
                .from(this));
    }

    @Override
    protected void onDestroy() {
        hideLoading();
        if (mUnbinder != null) mUnbinder.unbind();
        super.onDestroy();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        // 请求权限成功
        // ...
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // 请求权限失败
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list))
            new AppSettingsDialog.Builder(this).build().show();


    }

    @Override
    public boolean hasPermission(String... perms) {
        return EasyPermissions.hasPermissions(this, perms);
    }

    @Override
    public void requestPermission(String rationale, int requestCode, String... perms) {
        EasyPermissions.requestPermissions(this, rationale, requestCode, perms);


    }


}
