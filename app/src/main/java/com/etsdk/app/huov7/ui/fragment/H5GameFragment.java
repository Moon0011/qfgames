package com.etsdk.app.huov7.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.adapter.VideListAdapter;
import com.etsdk.app.huov7.ui.WebH5Activity;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

/**
 * Created by Administrator on 2017/11/3.
 */

public class H5GameFragment extends Fragment implements VideListAdapter.OnRecyclerViewItemListener {
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private VideListAdapter videListAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.h5game_layout, container, false);
        mPullLoadMoreRecyclerView = (PullLoadMoreRecyclerView) view.findViewById(R.id.recyclerView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        videListAdapter = new VideListAdapter(getActivity(), null);
        videListAdapter.setOnRecyclerViewItemListener(this);
        mPullLoadMoreRecyclerView.setAdapter(videListAdapter);
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
