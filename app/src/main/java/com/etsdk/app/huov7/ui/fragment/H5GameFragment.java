package com.etsdk.app.huov7.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.adapter.VideListAdapter;
import com.etsdk.app.huov7.base.AutoLazyFragment;
import com.etsdk.app.huov7.ui.WebH5Activity;
import com.etsdk.app.huov7.view.SlideShowView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/3.
 */

public class H5GameFragment extends AutoLazyFragment implements VideListAdapter.OnRecyclerViewItemListener {
    @BindView(R.id.recyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.slideshowview)
    SlideShowView banner;
    private VideListAdapter videListAdapter;
private String[] imageUrls = {"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1510322370546&di=bc2fb703d19623b623ee9cedc6365cd8&imgtype=jpg&src=http%3A%2F%2Fimg2.imgtn.bdimg.com%2Fit%2Fu%3D3918893832%2C2475578041%26fm%3D214%26gp%3D0.jpg"
        , "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=1592819383,3598721570&fm=27&gp=0.jpg"
        , "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2696880958,3175469618&fm=27&gp=0.jpg"
        , "http://gameimg.yoyojie.com/apps/2f/11/040c4496e27d89b75b6424c05b836682.jpg"};
    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.h5game_layout);
        setupUI();
    }

    private void setupUI() {
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        videListAdapter = new VideListAdapter(getActivity(), null);
        videListAdapter.setOnRecyclerViewItemListener(this);
        mPullLoadMoreRecyclerView.setAdapter(videListAdapter);
        banner.bindImgSource(imageUrls);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), WebH5Activity.class);
        startActivity(intent);
    }

    class PullLoadMoreListener implements PullLoadMoreRecyclerView.PullLoadMoreListener {
        @Override
        public void onRefresh() {
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }

        @Override
        public void onLoadMore() {
            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        }
    }
}
