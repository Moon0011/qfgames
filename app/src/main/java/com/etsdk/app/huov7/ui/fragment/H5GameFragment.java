package com.etsdk.app.huov7.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.adapter.VideListAdapter;
import com.etsdk.app.huov7.base.AutoLazyFragment;
import com.etsdk.app.huov7.model.H5GamesBean;
import com.etsdk.app.huov7.ui.WebActivity;
import com.etsdk.app.huov7.view.SlideShowView;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

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
    private List<H5GamesBean> h5GamesBeans = new ArrayList<>();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.h5game_layout);
        setupUI();
    }

    private void setupUI() {
        h5GamesBeans.add(new H5GamesBean("传奇来了",R.mipmap.chqi_coming_icon, "http://h5.520cai.com/media.php?s=/Game/open_game/game_id/8.html", "正版授权,千人同屏热血激情PK打怪", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("热血单机1.76",R.mipmap.menghuitc, "http://h5.520cai.com/media.php?s=/Game/open_game/game_id/6.html", "共铸热血传奇，重夺沙城之巅", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("梦回唐朝GM版",R.mipmap.rexuedanji, "http://h5.520cai.com/media.php?s=/Game/open_game/game_id/5.html", "万人激情战斗，梦回唐朝等你来战！", "角色扮演"));

        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        videListAdapter = new VideListAdapter(getActivity(), h5GamesBeans);
        videListAdapter.setOnRecyclerViewItemListener(this);
        mPullLoadMoreRecyclerView.setAdapter(videListAdapter);
        banner.bindImgSource(imageUrls);
    }

    @Override
    public void onItemClickListener(View view, int position) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle b = new Bundle();
        b.putString("url", h5GamesBeans.get(position).getWeburl());
        intent.putExtras(b);
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
