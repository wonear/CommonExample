package com.wonear.common.base.adapter;

import androidx.recyclerview.widget.RecyclerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;


/**
 * @param <T>
 */
public abstract class BaseAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public BaseAdapter(int layoutResId) {
        super(layoutResId);
    }

    private RecyclerView mRecycler;

    public BaseAdapter(int layoutResId, RecyclerView recycler) {
        super(layoutResId);
        mRecycler = recycler;
        bindToRecyclerView(recycler);
        disableLoadMoreIfNotFullPage();
        setEnableLoadMore(true);
    }

    public RecyclerView getRecycler() {
        return mRecycler;
    }
}
