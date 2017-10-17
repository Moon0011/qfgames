package com.etsdk.app.huov7.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.widget.Toast;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.adapter.MainVpAdapter;
import com.etsdk.app.huov7.base.ImmerseActivity;
import com.etsdk.app.huov7.down.TasksManager;
import com.etsdk.app.huov7.model.StartupResultBean;
import com.etsdk.app.huov7.model.SwitchFragmentEvent;
import com.etsdk.app.huov7.model.TabEntity;
import com.etsdk.app.huov7.ui.fragment.MainGameFragment;
import com.etsdk.app.huov7.ui.fragment.MainMineFragmentNew;
import com.etsdk.app.huov7.update.UpdateVersionDialog;
import com.etsdk.app.huov7.update.UpdateVersionService;
import com.etsdk.app.huov7.view.widget.AileTabView;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.liang530.control.LoginControl;
import com.liang530.log.T;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends ImmerseActivity {

    @BindView(R.id.main_vp)
    ViewPager mainVp;
    @BindView(R.id.main_tab)
    AileTabView mainTab;
    private MainVpAdapter mainVpAdapter;
    ArrayList<CustomTabEntity> tabEntityList=new ArrayList<CustomTabEntity>();
    private int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        TasksManager.getImpl().init();
        setupUI();
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);
//        mainVp.toggleLock();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent!=null){
            position=intent.getIntExtra("position",0);
            switchFragment(position);
        }
    }

    /**
     * 在fragment之前收到切换通知
     * @param switchFragmentEvent
     */
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 2)
    public void onSwitchFragmentEvent(SwitchFragmentEvent switchFragmentEvent){
        if(this.getClass().getName().equals(switchFragmentEvent.activityClassName)){
            switchFragment(switchFragmentEvent.positions[0]);
            if(switchFragmentEvent.positions.length==1){//没有后续切换，中断
                EventBus.getDefault().removeStickyEvent(switchFragmentEvent);
            }
        }
    }
    /**
     * 切换到游戏fragment
     * @param position 要切换到游戏fragment中的Tab位置
     */
    public void switchGameFragment(final int position){
        mainTab.setCurrentTab(1);
        mainVp.setCurrentItem(1,false);
        Fragment item = mainVpAdapter.getItem(1);
        if(item instanceof MainGameFragment){
            ((MainGameFragment) item).switchFragment(position);
        }
    }
    /**
     * 切换到开服开测，并切换到开测tab
     * @param
     */
    public void switchGameTestFragment(){
        mainTab.setCurrentTab(1);
        mainVp.setCurrentItem(1,false);
        Fragment item = mainVpAdapter.getItem(1);
        if(item instanceof MainGameFragment){
            ((MainGameFragment) item).switchStartTestFragment();
        }
    }



    /**
     * 主界面切换到position位置的fragment
     * @param position
     */
    private void switchFragment(int position){
//        findTabImgView(position).setImageResource(tab_bg_select[position]);
        mainTab.setCurrentTab(position);
        mainVp.setCurrentItem(position,false);
    }

    private void setupUI() {
        if(getIntent()!=null){
            position=getIntent().getIntExtra("position",0);
        }
        mainVpAdapter=new MainVpAdapter(getSupportFragmentManager());
        mainVp.setOffscreenPageLimit(3);

        mainTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==3){
                    if(!LoginControl.isLogin()){
                        LoginActivity.start(mContext);
                        mainTab.setCurrentTab(mainVp.getCurrentItem());
                    }
                }
                mainVp.setCurrentItem(position,false);
            }
            @Override
            public void onTabReselect(int position) {
                mainVpAdapter.reloadFragment(position);
            }
        });
        mainVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                mainTab.setCurrentTab(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        tabEntityList.add(new TabEntity("首页",R.mipmap.tab_icon_tj_s,R.mipmap.tab_icon_tj_us));
        tabEntityList.add(new TabEntity("游戏",R.mipmap.tab_icon_game_s,R.mipmap.tab_icon_game_us));
        tabEntityList.add(new TabEntity("资讯",R.mipmap.zixun_s,R.mipmap.zixun_us));
        tabEntityList.add(new TabEntity("我的",R.mipmap.tab_icon_my_s,R.mipmap.tab_icon_my_us));
        mainTab.setTabSpaceEqual(true);
        mainTab.setTabData(tabEntityList);
        mainVp.setAdapter(mainVpAdapter);
        switchFragment(position);
        handleUpdate();
    }

    /**
     *  处理版本更新信息
     */
    private void handleUpdate(){
        final boolean showCancel;
        final StartupResultBean.UpdateInfo updateInfo = EventBus.getDefault().getStickyEvent(StartupResultBean.UpdateInfo.class);
        if(updateInfo!=null){//有更新
            if("1".equals(updateInfo.getUp_status())){//强制更新
                showCancel=false;
            }else if("2".equals(updateInfo.getUp_status())){//选择更新
                showCancel=true;
            }else{
                return;
            }
            if(TextUtils.isEmpty(updateInfo.getUrl())||
                    (!updateInfo.getUrl().startsWith("http")&&!updateInfo.getUrl().startsWith("https"))){
                return;//url不可用
            }
            new UpdateVersionDialog().showDialog(mContext, showCancel,updateInfo.getContent() , new UpdateVersionDialog.ConfirmDialogListener() {
                @Override
                public void ok() {
                    Intent intent = new Intent(mContext, UpdateVersionService.class);
                    intent.putExtra("url", updateInfo.getUrl());
                    mContext.startService(intent);
                    T.s(mContext, "开始下载,请在下载完成后确认安装！");
                    if(!showCancel){//是强更则关闭界面
                        finish();
                    }
                }
                @Override
                public void cancel() {
                }
            });
        }
    }
    public static void start(Context context,int position) {
        Intent starter = new Intent(context, MainActivity.class);
        starter.putExtra("position",position);
        context.startActivity(starter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //更新我的页面的用户信息
        int currentItem = mainVp.getCurrentItem();
        if(currentItem==3){
            ((MainMineFragmentNew)mainVpAdapter.getItem(currentItem)).updateData();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    // 退出时间
    private long currentBackPressedTime = 0;
    // 退出间隔
    private static final int BACK_PRESSED_INTERVAL = 2000;
    //重写onBackPressed()方法,继承自退出的方法
    @Override
    public void onBackPressed() {
        // 判断时间间隔
        if (System.currentTimeMillis()- currentBackPressedTime > BACK_PRESSED_INTERVAL) {
            currentBackPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
        } else {
            // 退出
            finish();
        }
    }

}
