package com.etsdk.app.huov7.ui;

import android.content.Intent;
import android.widget.Toast;

import com.etsdk.app.huov7.view.h5view.BaseWebActivity;
import com.ta.utdid2.android.utils.StringUtils;

/**
 * Created by cenxiaozhong on 2017/5/22.
 *  <p>
 *
 */


public class WebActivity extends BaseWebActivity {

    @Override
    public String getUrl() {
        String webUrl = getIntent().getExtras().getString("url");
        if (StringUtils.isEmpty(webUrl)) {
            Toast.makeText(this, "链接地址错误", Toast.LENGTH_SHORT).show();
            finish();
            return "http://h5.520cai.com/";
        }
        return webUrl;
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent mIntent=new Intent();
    }
}
