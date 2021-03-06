package com.etsdk.app.huov7.update;

/**
 * Created by 刘红亮 on 2015/11/9 18:20.
 * 启动下载的后台服务，通过广播接收下载完成的通知
 */

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.etsdk.app.huov7.R;
import com.liang530.log.L;

import java.io.File;

public class UpdateVersionService extends Service {
    private String url;
    /**
     * 安卓系统下载类
     **/
    DownloadManager manager;
    /**
     * 接收下载完的广播
     **/
    DownloadCompleteReceiver receiver;

    /**
     * 初始化下载器
     **/
    private void initDownManager() {
        manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        receiver = new DownloadCompleteReceiver();
        //设置下载地址
        DownloadManager.Request down = new DownloadManager.Request(
                Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                | DownloadManager.Request.NETWORK_WIFI);
        // 显示下载界面
        down.setVisibleInDownloadsUi(true);
        // 设置下载路径和文件名
        down.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getString(R.string.app_name) + ".apk");
        down.setDescription(getString(R.string.app_name) + "新版本下载");
        // 下载时，通知栏显示途中
        down.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        down.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        down.allowScanningByMediaScanner();
        L.d("hongliang", "准备下载apk");
        // 将下载请求放入队列
        manager.enqueue(down);
        //注册下载广播
        registerReceiver(receiver, new IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        // 注销下载广播
        if (receiver != null)
            unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        url = intent.getStringExtra("url");
        L.d("hongliang", "下载的url=" + url);
        if (!TextUtils.isEmpty(url) &&
                (url.startsWith("http") || url.startsWith("https"))) {
            // 调用下载
            initDownManager();
        }
        //意外杀死后，不在重启服务
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // 接受下载完成后的intent
    class DownloadCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            //判断是否下载完成的广播
            if (intent.getAction().equals(
                    DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
                //获取下载的文件id
                long downId = intent.getLongExtra(
                        DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                L.d("hongliang", "downId=" + downId);
                //停止服务并关闭广播
                UpdateVersionService.this.stopSelf();
                //自动安装apk
                installAPK(context, manager.getUriForDownloadedFile(downId), downId);

            }
        }

        /**
         * 安装apk文件
         */
        private void installAPK(Context context, Uri apk, long downId) {
            if (apk != null) {
//                Intent intent = new Intent();
//                intent.setAction(Intent.ACTION_VIEW);//动作
//                intent.addCategory(Intent.CATEGORY_DEFAULT);//类型
//                intent.setDataAndType(apk, "application/vnd.android.package-archive");
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);

                Cursor c = manager.query(new DownloadManager.Query().setFilterById(downId));
                if (c != null) {
                    c.moveToFirst();
                    int fileNameIdx = c.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME);
                    String fileName = c.getString(fileNameIdx);
                    File file = new File(fileName);
                    install(context, file);
                    c.close();
                }
            }
        }
    }

    private void install(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 24) { //判读版本是否在7.0以上
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri apkUri =
                    FileProvider.getUriForFile(context, "com.qf.pdl.installapk", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
}
