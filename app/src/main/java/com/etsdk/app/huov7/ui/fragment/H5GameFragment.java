package com.etsdk.app.huov7.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.adapter.VideListAdapter;
import com.etsdk.app.huov7.base.AutoLazyFragment;
import com.etsdk.app.huov7.common.UIHelper;
import com.etsdk.app.huov7.model.H5GamesBean;
import com.etsdk.app.huov7.ui.WebActivity;
import com.etsdk.app.huov7.util.GlideImageLoader;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2017/11/3.
 */

public class H5GameFragment extends AutoLazyFragment implements VideListAdapter.OnRecyclerViewItemListener {
    @BindView(R.id.recyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    @BindView(R.id.slideshowview)
    Banner banner;
    private String[] imageUrls;
    private VideListAdapter videListAdapter;
    private List<H5GamesBean> h5GamesBeans = new ArrayList<>();
    public static String[] imgUrlAd = new String[]{
            "http://h5.520cai.com/media.php?s=/Game/open_game/game_id/10.html",
            "http://h5.520cai.com/media.php?s=/opgm/8.html"
    };

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.h5game_layout);
        setupUI();
    }

    private void setupUI() {
        imageUrls = new String[2];
        imageUrls[0] = "android.resource://" + UIHelper.getAppPackageName(getActivity()) + "/drawable/" + R.drawable.h5_ad01;
        imageUrls[1] = "android.resource://" + UIHelper.getAppPackageName(getActivity()) + "/drawable/" + R.drawable.h5_ad02;
        banner.setImages(Arrays.asList(imageUrls))
                .isAutoPlay(true)
                .setDelayTime(2500)
                .setIndicatorGravity(BannerConfig.RIGHT)
                .setImageLoader(new GlideImageLoader())
                .start();

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                Bundle b = new Bundle();
                b.putString("url", imgUrlAd[position - 1]);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

        h5GamesBeans.clear();
        h5GamesBeans.add(new H5GamesBean("传奇来了", R.mipmap.chqi_coming_icon, "http://h5.520cai.com/media.php?s=/Game/open_game/game_id/8.html", "正版授权,千人同屏热血激情PK打怪", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("热血单机1.76", R.mipmap.menghuitc, "http://h5.520cai.com/media.php?s=/Game/open_game/game_id/17.html", "共铸热血传奇，重夺沙城之巅", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("大武林", R.mipmap.dawulin, "http://h5.520cai.com/mobile.php/?s=/opgm/10.html", "收徒弟、挑战宗师、合神器，扮演绝世\n高手成为江湖主宰！", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("仙剑诛魔", R.mipmap.xianjianzhumo, "http://h5.520cai.com/mobile.php/?s=/opgm/13.html", "酷炫换装要你好看   组队除魔让你装逼", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("大唐真龙无双挂机版", R.mipmap.datanzhenlong, "http://h5.520cai.com/mobile.php/?s=/opgm/5.html", "万人激情战斗，等你来战！", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("雪鹰领主", R.mipmap.xueyinlinzhu, "http://h5.520cai.com/media.php?s=/opgm/7.html", "同名玄幻大作，倾力打造的3DH5\n放置类手游", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("Q萌江湖", R.mipmap.qmengjianhu, "http://h5.520cai.com/media.php?s=/opgm/19.html", "集结诸多武林豪侠,学习各种盖世神功", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("比格德州", R.mipmap.bigedezhou, "http://h5.520cai.com/media.php?s=/opgm/15.html", "一款风靡全球、备受脑力精英者追捧的\n扑克游戏", "棋牌"));
        h5GamesBeans.add(new H5GamesBean("比格牛牛", R.mipmap.bigeniuniu, "http://h5.520cai.com/media.php?s=/opgm/14.html", "一款简单好玩的的对战棋牌游戏", "棋牌"));
        h5GamesBeans.add(new H5GamesBean("比格斗地主", R.mipmap.bigedoudizhu, "http://h5.520cai.com/media.php?s=/opgm/16.html", "一款可玩性较高的免费斗地主游戏", "棋牌"));
        h5GamesBeans.add(new H5GamesBean("武神传说", R.mipmap.wushenchuanshuo, "http://h5.520cai.com/media.php?s=/opgm/12.html", "还原经典传奇，大型仙侠ARPG游戏;\n超强引擎，无缝跨三屏", "角色扮演"));
        h5GamesBeans.add(new H5GamesBean("传奇威力加强版", R.mipmap.chuqiweili, "http://jiekou.diaigame.com/h5/play?param=UDAwMDEzNzIsQzAwMDA0OTQsLEFORFJPSUQsMS4wLDI5YzVlZDNmMmQ2MTZhMGRmYWFiOGE4YTdiZGVjZjA0", "快拿起手中的屠龙刀，开创自己的\n盛世霸业", "角色扮演"));

        mPullLoadMoreRecyclerView.setLinearLayout();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreListener());
        mPullLoadMoreRecyclerView.setPullRefreshEnable(false);
        videListAdapter = new VideListAdapter(getActivity(), h5GamesBeans);
        videListAdapter.setOnRecyclerViewItemListener(this);
        mPullLoadMoreRecyclerView.setAdapter(videListAdapter);

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
