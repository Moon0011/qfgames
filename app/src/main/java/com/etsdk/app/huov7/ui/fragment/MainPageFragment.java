package com.etsdk.app.huov7.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.base.AutoLazyFragment;
import com.etsdk.app.huov7.http.AppApi;
import com.etsdk.app.huov7.model.AdImage;
import com.etsdk.app.huov7.model.GameBean;
import com.etsdk.app.huov7.model.GamelikeBean;
import com.etsdk.app.huov7.model.HomePage1Data;
import com.etsdk.app.huov7.model.MessageEvent;
import com.etsdk.app.huov7.model.TjAdText;
import com.etsdk.app.huov7.model.TjAdTop;
import com.etsdk.app.huov7.model.TjColumnHead;
import com.etsdk.app.huov7.model.TjMustPlay;
import com.etsdk.app.huov7.model.TjTestNewVp;
import com.etsdk.app.huov7.provider.AdImageViewProvider;
import com.etsdk.app.huov7.provider.GameItemViewProvider;
import com.etsdk.app.huov7.provider.GamelikeListProvider;
import com.etsdk.app.huov7.provider.TjAdTextViewProvider;
import com.etsdk.app.huov7.provider.TjAdTopViewProvider;
import com.etsdk.app.huov7.provider.TjColumnHeadViewProvider;
import com.etsdk.app.huov7.provider.TjMustPlayViewProvider;
import com.etsdk.app.huov7.provider.TjTestNewVpViewProvider;
import com.etsdk.app.huov7.ui.DownloadManagerActivity;
import com.etsdk.app.huov7.ui.MessageActivity;
import com.etsdk.app.huov7.ui.SearchActivity;
import com.etsdk.app.huov7.util.RecyclerViewNoAnimator;
import com.etsdk.hlrefresh.AdvRefreshListener;
import com.etsdk.hlrefresh.BaseRefreshLayout;
import com.etsdk.hlrefresh.MVCSwipeRefreshHelper;
import com.kymjs.rxvolley.client.HttpParams;
import com.liang530.rxvolley.HttpJsonCallBackDialog;
import com.liang530.rxvolley.NetRequest;
import com.liang530.utils.BaseAppUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by liu hong liang on 2016/12/21.
 */

public class MainPageFragment extends AutoLazyFragment implements AdvRefreshListener {
    public static final float BANNER_WH_RATA = 2.4f;
    @BindView(R.id.recy_main_tj)
    RecyclerView recyMainTj;
    @BindView(R.id.srf_main_tj)
    SwipeRefreshLayout srfMainTj;
    @BindView(R.id.iv_gotoMsg)
    ImageView ivGotoMsg;
    @BindView(R.id.main_gameSearch)
    TextView mainGameSearch;
    private MultiTypeAdapter multiTypeAdapter;
    private BaseRefreshLayout baseRefreshLayout;
    Items items = new Items();

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        super.onCreateViewLazy(savedInstanceState);
        setContentView(R.layout.fragment_main_tj_new);
        EventBus.getDefault().register(this);
        setupUI();
    }

    private void setupUI() {
        baseRefreshLayout = new MVCSwipeRefreshHelper(srfMainTj);
        multiTypeAdapter = new MultiTypeAdapter(items);
        multiTypeAdapter.applyGlobalMultiTypePool();
        TjAdTopViewProvider tjAdTopViewProvider = new TjAdTopViewProvider();
        setBannerImageWH(tjAdTopViewProvider);
        multiTypeAdapter.register(TjAdTop.class, tjAdTopViewProvider);
        multiTypeAdapter.register(TjAdText.class, new TjAdTextViewProvider());
//        multiTypeAdapter.register(TjOptionColumn.class, new TjOptionColumnViewProvider());
        multiTypeAdapter.register(TjColumnHead.class, new TjColumnHeadViewProvider(baseRefreshLayout));
        multiTypeAdapter.register(TjMustPlay.class, new TjMustPlayViewProvider());
        multiTypeAdapter.register(AdImage.class, new AdImageViewProvider());
        multiTypeAdapter.register(GameBean.class, new GameItemViewProvider());
        multiTypeAdapter.register(TjTestNewVp.class, new TjTestNewVpViewProvider());
        multiTypeAdapter.register(GamelikeBean.class, new GamelikeListProvider());
        recyMainTj.setLayoutManager(new LinearLayoutManager(mContext));
        recyMainTj.setItemAnimator(new RecyclerViewNoAnimator());
        multiTypeAdapter.notifyDataSetChanged();
//        recyMainTj.addOnScrollListener(new MainSearchRcyScrollListener(rlMsg, llSearch));//滑动监听
//        baseRefreshLayout = new MVCSwipeRefreshHelper(srfMainTj);
        baseRefreshLayout.setAdvRefreshListener(this);
        baseRefreshLayout.setAdapter(multiTypeAdapter);
        baseRefreshLayout.refresh();
        MessageEvent messageEvent = EventBus.getDefault().getStickyEvent(MessageEvent.class);
        onMessageEvent(messageEvent);

    }

    /**
     * 设置banner图片宽高
     */
    private void setBannerImageWH(TjAdTopViewProvider tjAdTopViewProvider) {
        int deviceWidth = BaseAppUtil.getDeviceWidth(getContext());
        tjAdTopViewProvider.setBannerHeight((int) (deviceWidth / BANNER_WH_RATA));
    }

    @OnClick({R.id.rl_goto_msg, R.id.main_gameSearch, R.id.iv_tj_downManager})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_goto_msg:
                MessageActivity.start(mContext);
                break;
            case R.id.main_gameSearch:
                SearchActivity.start(mContext);
                break;
            case R.id.iv_tj_downManager:
                DownloadManagerActivity.start(mContext);
                break;
        }
    }

    @Override
    public void getPageData(int requestPageNo) {
        HttpParams httpParams = AppApi.getCommonHttpParams(AppApi.hompageApi);
        //成功，失败，null数据
        NetRequest.request(this).setParams(httpParams).get(AppApi.getUrl(AppApi.hompageApi), new HttpJsonCallBackDialog<HomePage1Data>() {
            @Override
            public void onDataSuccess(HomePage1Data data) {
                if (data != null && data.getData() != null) {
                    updateHomeData(data.getData());
                } else {
                    baseRefreshLayout.resultLoadData(items, null, 1);
                }
            }

            @Override
            public void onJsonSuccess(int code, String msg, String data) {
                baseRefreshLayout.resultLoadData(items, null, null);
            }

            @Override
            public void onFailure(int errorNo, String strMsg, String completionInfo) {
                baseRefreshLayout.resultLoadData(items, null, null);
            }
        });
    }

    private void updateHomeData(HomePage1Data.DataBean homePage1Data) {
        Items allItems = new Items();
        //顶部banner
        if (homePage1Data.getHometopper() != null && homePage1Data.getHometopper().getList() != null
                && homePage1Data.getHometopper().getList().size() > 0) {
            allItems.add(new TjAdTop(homePage1Data.getHometopper().getList()));
        } else {
            allItems.add(new TjAdTop(new ArrayList<AdImage>()));
        }
        //滚动文字信息
        if (homePage1Data.getTexthome() != null && homePage1Data.getTexthome().getList() != null
                && homePage1Data.getTexthome().getList().size() > 0) {
            allItems.add(new TjAdText(homePage1Data.getTexthome().getList()));
        }
        //秒杀代金券、活动资讯等
        boolean isReuqestLine = false;
        TjColumnHead tjColumnHead;

        //必玩游戏
        tjColumnHead = new TjColumnHead(TjColumnHead.TYPE_GAME_MUSTPLAYER);
        allItems.add(tjColumnHead);
        String[] playNames = {"北凉悍刀行", "剑雨逍遥", "狼人杀", "魂之轨迹"};
        int[] playImgs = {R.mipmap.about_us, R.mipmap.delete, R.mipmap.safe, R.mipmap.chongzhika};
        allItems.add(new TjMustPlay(playNames, playImgs));

        //新游推荐头
        isReuqestLine = false;
        tjColumnHead = new TjColumnHead(TjColumnHead.TYPE_GAME_TJ);
        allItems.add(tjColumnHead);
        //新游推荐
        if (homePage1Data.getNewgame() != null && homePage1Data.getNewgame() != null
                && homePage1Data.getNewgame().getList() != null
                && homePage1Data.getNewgame().getList().size() > 0) {
            List<GameBean> gameBeanList = homePage1Data.getNewgame().getList();
            int i = 0;
            for (GameBean gameBean : gameBeanList) {
                i++;
                if (i > 3) {
                    break;
                }
                allItems.add(gameBean);
            }
            isReuqestLine = true;
        }

        //手游风向标头(热门游戏)
        isReuqestLine = false;
        tjColumnHead = new TjColumnHead(TjColumnHead.TYPE_GAME_FXB);
        allItems.add(tjColumnHead);
        //手游风向标(热门游戏)
        if (homePage1Data.getHotgame() != null && homePage1Data.getHotgame() != null
                && homePage1Data.getHotgame().getList() != null
                && homePage1Data.getHotgame().getList().size() > 0) {
            List<GameBean> gameBeanList = homePage1Data.getHotgame().getList();
            int i = 0;
            for (GameBean gameBean : gameBeanList) {
                i++;
                if (i > 3) {
                    break;
                }
                allItems.add(gameBean);
            }
            isReuqestLine = true;
        }
        items.clear();
        baseRefreshLayout.resultLoadData(items, allItems, 1);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {
        if (messageEvent != null && "2".equals(messageEvent.getNewMsg())) {
            ivGotoMsg.setImageResource(R.mipmap.syxiaoxi_red);
        } else {
            ivGotoMsg.setImageResource(R.mipmap.syxiaoxi_nomal);
        }
    }

    @Override
    protected void onDestroyViewLazy() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroyViewLazy();
    }

}
