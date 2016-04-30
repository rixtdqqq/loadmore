package com.example.administrator.loadmore;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/4/30.
 */
public class LoadMoreListView extends ListView implements AbsListView.OnScrollListener {

    private ILoadMoreListener loadMoreListener;

    /**
     * 总数量
     */
    private int totalItemCount;
    /**
     * 最后一个 可见的item
     */
    private int lastVisibleItem;
    /**
     * 加载更多的布局
     */
    private LinearLayout loadMoreLayout;
    /**
     * 是否正在加载
     */
    private boolean isLoading = false;

    public LoadMoreListView(Context context) {
        this(context, null);
    }

    public LoadMoreListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View footer = inflater.inflate(R.layout.load_more_layout, null);
        loadMoreLayout = (LinearLayout) footer.findViewById(R.id.load_more_layout);
        loadMoreLayout.setVisibility(GONE);
        this.addFooterView(footer);
        this.setOnScrollListener(this);
    }


    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (lastVisibleItem >= totalItemCount && scrollState == SCROLL_STATE_IDLE) {
            if (!isLoading){
                isLoading = true;
                loadMoreLayout.setVisibility(VISIBLE);
                loadMoreListener.onLoadMore();
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        this.totalItemCount = totalItemCount;
        lastVisibleItem = firstVisibleItem + visibleItemCount;
    }

    public void setLoadMoreListener(ILoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    public void loadCompleted() {
        isLoading = false;
        loadMoreLayout.setVisibility(GONE);
    }

    public interface ILoadMoreListener{
        void onLoadMore();
    }
}
