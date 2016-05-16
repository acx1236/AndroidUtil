package com.ancx.ancxutil.widget;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ancx.ancxutil.R;
import com.ancx.ancxutil.holder.BaseViewHolder;

import java.util.List;

/**
 * Created by Ancx
 */
public class SuperRefreshLayout extends LinearLayout implements SwipeRefreshLayout.OnRefreshListener, LoadingView.OnReloadDataListener {

    // ===============================================接口=======================================================

    public interface OnSuperRefreshListener {
        void onRefresh();

        void onReload();
    }

    private OnSuperRefreshListener onSuperRefreshListener;

    public void setOnSuperRefreshListener(OnSuperRefreshListener onSuperRefreshListener) {
        this.onSuperRefreshListener = onSuperRefreshListener;
    }

    public interface OnLoadListener {
        void onLoad();
    }

    private OnLoadListener onLoadListener;

    public void setOnLoadListener(OnLoadListener onLoadListener) {
        addFooterView();
        this.onLoadListener = onLoadListener;
    }

    // =========================================================================================================

    public SuperRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.layout_super_refresh, this);
        initView();
    }

    private LoadingView mLoadView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private boolean isLoading, isLoadFinish, isRefreshing;

    private void initView() {
        mLoadView = (LoadingView) findViewById(R.id.mLoadView);
        mLoadView.setOnReloadDataListener(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_purple, android.R.color.holo_blue_bright, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setHasFixedSize(true);
        mGridLayoutManager = new GridLayoutManager(getContext(), 1);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState != RecyclerView.SCROLL_STATE_IDLE)
                    return;
                boolean isBottom = mGridLayoutManager.findLastCompletelyVisibleItemPosition() == mGridLayoutManager.getItemCount() - 1;
                if (isBottom && onLoadListener != null && !isLoading && !isRefreshing && !isLoadFinish) {
                    isLoading = true;
                    mSwipeRefreshLayout.setEnabled(false);
                    onLoadListener.onLoad();
                }
            }
        });
    }

    public void setSpanCount(int spanCount) {
        mGridLayoutManager.setSpanCount(spanCount);
    }

    public void errorNetWork() {
        mLoadView.errorNet();
    }

    @Override
    public void onRefresh() {
        isRefreshing = true;
        if (onSuperRefreshListener != null) {
            onSuperRefreshListener.onRefresh();
        }
    }

    @Override
    public void onReload() {
        if (onSuperRefreshListener != null) {
            onSuperRefreshListener.onReload();
        }
    }

    public void setRefresh() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                isRefreshing = true;
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    private View headerView;

    public void addHeaderView(View headerView) {
        this.headerView = headerView;
    }

    private View footerView;
    private ProgressBar mProgressBar;
    private TextView mTextView;

    public void addFooterView() {
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.layout_super_refresh_footer, null, false);
        footerView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        mProgressBar = (ProgressBar) footerView.findViewById(R.id.mProgressBar);
        mTextView = (TextView) footerView.findViewById(R.id.mTextView);
    }

    /**
     * RecyclerView的加载器
     */
    public class SuperAdapter extends RecyclerView.Adapter {

        private final int HEADERHOLDER = 10001;
        private final int FOOTERHOLDER = 10002;

        @Override
        public int getItemCount() {
            int itemCount = 0;
            if (headerView != null)
                itemCount++;
            if (onLoadListener != null)
                itemCount++;
            return itemCount + dataAdapter.getData().size();
        }

        @Override
        public int getItemViewType(int position) {
            if (headerView == null) {
                if (onLoadListener == null) {
                    return dataAdapter.getItemViewType(position);
                } else {
                    if (position == dataAdapter.getData().size())
                        return FOOTERHOLDER;
                    else
                        return dataAdapter.getItemViewType(position);
                }
            } else {
                if (onLoadListener == null) {
                    if (position == 0)
                        return HEADERHOLDER;
                    else
                        return dataAdapter.getItemViewType(position - 1);
                } else {
                    if (position == 0)
                        return HEADERHOLDER;
                    else if (position == dataAdapter.getData().size() + 1)
                        return FOOTERHOLDER;
                    else
                        return dataAdapter.getItemViewType(position - 1);
                }
            }
        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {

            public HeaderViewHolder(View itemView) {
                super(itemView);
            }
        }

        private class FooterViewHolder extends RecyclerView.ViewHolder {

            public FooterViewHolder(View itemView) {
                super(itemView);
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == HEADERHOLDER)
                return new HeaderViewHolder(headerView);
            else if (viewType == FOOTERHOLDER)
                return new FooterViewHolder(footerView);
            else
                return dataAdapter.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof BaseViewHolder) {
                if (headerView != null)
                    position--;
                dataAdapter.onBindViewHolder(holder, position);
            } else if (holder instanceof FooterViewHolder) {
                if (isLoadFinish) {
                    mProgressBar.setVisibility(GONE);
                    mTextView.setText("暂无更多数据!");
                } else {
                    mProgressBar.setVisibility(VISIBLE);
                    mTextView.setText("努力加载中...");
                }
            }
        }

        public void notifyUpdate(int total) {
            if (isLoading) {
                isLoading = false;
                mSwipeRefreshLayout.setEnabled(true);
            }
            if (isRefreshing) {
                isRefreshing = false;
                mSwipeRefreshLayout.setRefreshing(false);
            }
            if (total == 0 && headerView == null) {
                mLoadView.noData();
                return;
            }
            mLoadView.loadComplete();
            if (dataAdapter.getData().size() < total) {
                isLoadFinish = false;
            } else {
                isLoadFinish = true;
            }
            notifyDataSetChanged();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (headerView == null) {
                        if (position < dataAdapter.getData().size())
                            return 1;
                        else
                            return mGridLayoutManager.getSpanCount();
                    } else {
                        if (position > 0 && position <= dataAdapter.getData().size())
                            return 1;
                        else
                            return mGridLayoutManager.getSpanCount();
                    }
                }
            });
        }
    }

    public static abstract class DataAdapter {

        private final int CONTENTHOLDER = 100001;

        public abstract List getData();

        public int getItemViewType(int position) {
            return CONTENTHOLDER;
        }

        public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

        public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);

    }

    private DataAdapter dataAdapter;
    private SuperAdapter superAdapter;

    public SuperAdapter setDataAdapter(DataAdapter dataAdapter) {
        this.dataAdapter = dataAdapter;
        superAdapter = new SuperAdapter();
        mRecyclerView.setAdapter(superAdapter);
        return superAdapter;
    }
}