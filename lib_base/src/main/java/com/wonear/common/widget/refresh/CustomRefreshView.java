package com.wonear.common.widget.refresh;

import android.content.Context;
import android.util.AttributeSet;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.entity.SectionEntity;
import com.wonear.common.base.adapter.BaseAdapter;
import com.wonear.common.base.adapter.BaseMulAdapter;
import com.wonear.common.base.adapter.BaseSectionAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.util.List;

public class CustomRefreshView extends SmartRefreshLayout implements OnRefreshListener, OnLoadMoreListener {
    private int pageNo = DEFAULT_PAGENO;
    private int pageSize = 10;

    private BaseAdapter baseAdapter;
    private BaseSectionAdapter sectionAdapter;
    public static final int DEFAULT_PAGENO = 1;
    private CustomRefreshListener refreshListener;

    public CustomRefreshView(Context context) {
        super(context);
        init();
    }

    public CustomRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomRefreshView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        setEnableLoadMoreWhenContentNotFull(false);
//        setEnableFooterTranslationContent(false);
        setEnableAutoLoadMore(false);
        setDragRate(0.9f);
        setOnRefreshListener(this);
//        setOnLoadMoreListener(this);
        setEnableLoadMore(false);//关闭上拉加载
        setEnableOverScrollBounce(false);//关闭越界回弹
    }

    public void setRefreshListener(CustomRefreshListener refreshListener) {
        this.refreshListener = refreshListener;
    }

    public void attach(BaseAdapter baseAdapter, CustomRefreshListener listener) {
        this.refreshListener = listener;
        this.baseAdapter = baseAdapter;
        baseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNo++;
                if (refreshListener == null) return;
                refreshListener.onRefreshData(pageNo, pageSize);
            }
        }, baseAdapter.getRecycler());
    }

    public void attach(BaseSectionAdapter baseAdapter, CustomRefreshListener listener) {
        this.refreshListener = listener;
        this.sectionAdapter = baseAdapter;
        baseAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                pageNo++;
                if (refreshListener == null) return;
                refreshListener.onRefreshData(pageNo, pageSize);
            }
        }, baseAdapter.getRecycler());
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        pageNo = DEFAULT_PAGENO;
        setNoMoreData(false);
        if (refreshListener == null) return;
        refreshListener.onRefreshData(pageNo, pageSize);

    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        pageNo++;
        if (refreshListener == null) return;
        refreshListener.onRefreshData(pageNo, pageSize);
    }

    /**
     * 取消加载
     */
    public void finishLoading() {
        if (isRefreshing()) finishRefresh();
        if (isLoading()) finishLoadMore();
        if (baseAdapter != null)
            baseAdapter.loadMoreComplete();
    }


    /**
     * 下啦刷新
     *
     * @return
     */
    public boolean isRefresh() {
        return pageNo == DEFAULT_PAGENO;
    }


    /**
     * 刷新适配器数据，是否由更多数据由内部判断，适用于pageNo和pageSize在前端控制的情况
     *
     * @param adapter 适配器
     * @param list    数据集合
     * @param <T>     数据类型
     */
    public <T> void noticeAdapterData(BaseAdapter<T> adapter, List<T> list) {
        finishLoading();
        if (pageNo > DEFAULT_PAGENO) {
            adapter.addData(list);
            if (list.size() < pageSize) {
                finishLoadMoreWithNoMoreData();
                adapter.loadMoreEnd();
                return;
            }
            adapter.loadMoreComplete();
        } else {
            adapter.setNewData(list);
        }
        if (pageSize > list.size()) {
            finishLoadMoreWithNoMoreData();
            //第一页加载不满一屏幕，那么隐藏掉没有更多的文案
            adapter.loadMoreEnd(pageNo == DEFAULT_PAGENO);
        }
    }


    public <T extends SectionEntity> void noticeAdapterData(BaseSectionAdapter<T> adapter, List<T> list) {
        finishLoading();
        if (pageNo > DEFAULT_PAGENO) {
            adapter.addData(list);
            if (list.size() < pageSize) {
                finishLoadMoreWithNoMoreData();
                adapter.loadMoreEnd();
                return;
            }
            adapter.loadMoreComplete();
        } else {
            adapter.setNewData(list);
        }
        if (pageSize > list.size()) {
            finishLoadMoreWithNoMoreData();
            //第一页加载不满一屏幕，那么隐藏掉没有更多的文案
            adapter.loadMoreEnd(pageNo == DEFAULT_PAGENO);
        }
    }

    /**
     * 刷新多布局适配器数据，是否由更多数据由内部判断，适用于pageNo和pageSize在前端控制的情况
     *
     * @param adapter 适配器
     * @param list    数据集合
     * @param <T>     数据类型
     */
    public <T extends MultiItemEntity> void noticeAdapterData(BaseMulAdapter<T> adapter, List<T> list) {
        finishLoading();
//        pageNo++;
        if (pageNo > DEFAULT_PAGENO) {
            adapter.addData(list);
            if (list.size() < pageSize) {
                finishLoadMoreWithNoMoreData();
                adapter.loadMoreEnd();
                return;
            }
            adapter.loadMoreComplete();
        } else {
            adapter.setNewData(list);
        }
    }

    /**
     * 单布局下刷新适配器数据，是否有下一页数据由外部传入
     *
     * @param adapter 适配器
     * @param list    数据集合
     * @param <T>     数据类型
     * @param hasNext 是否还有分页数据
     */
    public <T> void noticeAdapterData(BaseAdapter<T> adapter, List<T> list, boolean hasNext) {
        finishLoading();
        if (pageNo > DEFAULT_PAGENO) {
            adapter.addData(list);
            adapter.loadMoreComplete();
        } else {
            adapter.setNewData(list);
        }

        if (!hasNext) {
            finishLoadMoreWithNoMoreData();
            adapter.loadMoreEnd(pageNo == DEFAULT_PAGENO);
        }
    }

    /**
     * 多布局情况下刷新适配器数据，是否有下一页数据由外部传入
     *
     * @param adapter 适配器
     * @param list    数据集合
     * @param <T>     数据类型
     * @param hasNext 是否还有分页数据
     */
    public <T extends MultiItemEntity> void noticeAdapterData(BaseMulAdapter<T> adapter, List<T> list, boolean hasNext) {
        finishLoading();
//        pageNo++;
        if (pageNo > DEFAULT_PAGENO) {
            adapter.addData(list);
            adapter.loadMoreComplete();
        } else {
            adapter.setNewData(list);
        }

        if (!hasNext) {
            finishLoadMoreWithNoMoreData();
            adapter.loadMoreEnd(pageNo == DEFAULT_PAGENO);
        }
    }

    public void doRefresh() {
        initData();
        autoRefresh();
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void initData() {
        pageNo = DEFAULT_PAGENO;
    }

}
