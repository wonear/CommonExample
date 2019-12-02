package com.wonear.common.base.ui;

public abstract class BasePresenter<V extends BaseView> {
    protected V mView;

    public BasePresenter(V view) {
        this.mView = view;
    }


    void detachView() {
        mView = null;
    }
}
