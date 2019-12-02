package com.wonear.common.base.dialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author wonear
 * @date : 2019/9/20 11:04
 */

public abstract class BaseDialog extends DialogFragment {

    private static final int TIME = 600;
    protected View mView;
    private BaseDialogListener mBaseDialogListener;
    private Unbinder unbinder;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            dismiss();
            if (mBaseDialogListener != null) {
                mBaseDialogListener.dismiss();
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Window window = getDialog().getWindow();
        window.requestFeature(Window.FEATURE_NO_TITLE);

        window.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), android.R.color.transparent));
        int dialogHeight = getContextRect(getActivity());
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = gravity();
        layoutParams.dimAmount = 0.0f;
        window.setAttributes(layoutParams);


        mView = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    protected abstract int gravity();

    protected abstract boolean isFullWidth();

    protected abstract void init();

    protected abstract int getLayoutId();

    @Override
    public void onResume() {
        super.onResume();
        if (isFullWidth())
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        else
            getDialog().getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);

    }

    public void show(FragmentManager manager, String tag) {
        show(manager, tag, false);
    }

    public void show(FragmentManager manager, String tag, boolean autoHide) {
        if (autoHide) {
            mHandler.sendEmptyMessageDelayed(0, TIME);
        }
        super.show(manager, tag);
    }

    public void setBaseDialogListener(BaseDialogListener baseDialogListener) {
        mBaseDialogListener = baseDialogListener;
    }

    public interface BaseDialogListener {
        void dismiss();
    }

    public boolean isShowing() {
        if (getDialog() != null) return getDialog().isShowing();
        return false;
    }


    //获取内容区域
    private int getContextRect(Activity activity) {
        //应用区域
        Rect outRect1 = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
        return outRect1.height();
    }


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
}