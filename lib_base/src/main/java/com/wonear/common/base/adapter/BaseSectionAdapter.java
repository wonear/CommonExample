package com.wonear.common.base.adapter;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * @author wonear
 * @date : 2019/10/18 16:40
 */
public class BaseSectionAdapter<T extends SectionEntity> extends BaseSectionQuickAdapter<T, BaseViewHolder> {
    private RecyclerView mRecycler;

    public BaseSectionAdapter(int layoutResId, int sectionLayoutId) {
        super(layoutResId, sectionLayoutId, null);
    }

    public BaseSectionAdapter(int layoutResId, int sectionLayoutId, RecyclerView recycler) {
        super(layoutResId, sectionLayoutId, null);
        mRecycler = recycler;
        bindToRecyclerView(recycler);
        disableLoadMoreIfNotFullPage();
        setEnableLoadMore(true);
    }


    @Override
    protected void convert(BaseViewHolder helper, T item) {

    }

    public RecyclerView getRecycler() {
        return mRecycler;
    }

    @Override
    protected void convertHead(BaseViewHolder helper, T item) {

    }
}
