package com.etsdk.app.huov7.base;

import com.etsdk.app.huov7.BuildConfig;
import com.etsdk.app.huov7.model.InstallApkRecord;
import com.game.sdk.log.L;
import com.liang530.application.BaseApplication;
import com.liulishuo.filedownloader.FileDownloader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liu hong liang on 2016/12/1.
 */

public class AileApplication extends BaseApplication {
    private Map<String,InstallApkRecord> installingApkList=new HashMap<>();
    @Override
    public void onCreate() {
        super.onCreate();
        MultiTypeInstaller.start();
        L.init(BuildConfig.LOG_DEBUG);
        com.liang530.log.L.init(BuildConfig.LOG_DEBUG);
        //设置同时最大下载数量
        FileDownloader.init(getApplicationContext());
        FileDownloader.getImpl().setMaxNetworkThreadCount(8);
    }

    @Override
    public Class getLoginClass() {
        return null;
    }
    public Map<String, InstallApkRecord> getInstallingApkList() {
        return installingApkList;
    }
}
