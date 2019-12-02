package com.wonear.common.base.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity  {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();

    }

    protected abstract P createPresenter();





}
