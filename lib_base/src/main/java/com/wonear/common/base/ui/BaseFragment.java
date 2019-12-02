package com.wonear.common.base.ui;

import android.app.Activity;
import androidx.lifecycle.Lifecycle;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

import com.afollestad.materialdialogs.MaterialDialog;
import com.wonear.common.R;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.AutoDisposeConverter;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

public abstract class BaseFragment extends Fragment implements BaseView, EasyPermissions.PermissionCallbacks {
    private boolean isVisible = false;//当前Fragment是否可见
    private boolean isFirstLoad = true;//是否是第一次加载数据
    protected View mView;
    private Unbinder mUnbinder;
    private MaterialDialog mDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(layoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mView);
        return mView;
    }


    @Nullable
    @Override
    public Context getCurContext() {
        return getActivity();
    }

    @Override
    public Activity getCurActivity() {
        return getActivity();
    }

    protected abstract int layoutId();

    @Override
    public void showLoading() {
        if (mDialog == null) {
            mDialog = new MaterialDialog.Builder(getActivity())
                    .backgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent))
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
                .from(this, Lifecycle.Event.ON_DESTROY));
    }

    @Override
    public void onDestroy() {
        if (mUnbinder != null) mUnbinder.unbind();
        super.onDestroy();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        isVisible = isVisibleToUser;
        doLoadFirst();
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void doLoadFirst() {
        if (isVisible && isFirstLoad && mView != null) {
            lazyLoadData();
            isFirstLoad = false;
        }

        if (isVisible && mView != null) {
            doLoadVisible();
        }


    }

    /**
     * 每次显示都会加载
     * 注意：这个方法和lazyLoadDa不能同时重写
     */
    public void doLoadVisible() {

    }


    @Override
    public void onResume() {
        super.onResume();
        doLoadFirst();
    }


    /**
     * 第一次显示的时候加载，只加载一次
     */
    protected void lazyLoadData() {

    }

    public void startForResult(Intent intent, int requestCode) {
        this.startActivityForResult(intent, requestCode);
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

    }

    @Override
    public boolean hasPermission(String... perms) {
        return EasyPermissions.hasPermissions(getCurContext(), perms);
    }

    @Override
    public void requestPermission(String rationale, int requestCode, String... perms) {
        EasyPermissions.requestPermissions(this, rationale, requestCode, perms);
    }
}
