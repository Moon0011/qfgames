package com.etsdk.app.huov7.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsdk.app.huov7.BuildConfig;
import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.http.AppApi;
import com.etsdk.app.huov7.model.NewsListBean;
import com.etsdk.app.huov7.ui.WebViewActivity;
import com.liang530.utils.GlideDisplay;
import com.liang530.views.refresh.mvc.IDataAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu hong liang on 2016/12/6.
 */

public class NewsListAdapter extends RecyclerView.Adapter implements IDataAdapter<List<NewsListBean.DataBean.ListBean>> {

    List<NewsListBean.DataBean.ListBean> newsList = new ArrayList<>();

    public NewsListAdapter(List<NewsListBean.DataBean.ListBean> newsList) {
        if(newsList != null) {
            this.newsList = newsList;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final NewsListBean.DataBean.ListBean news = newsList.get(position);
        ((ViewHolder)holder).tvNewsTitle.setText(news.getTitle());
        int type = news.getType();//0-所有 1-新闻 2-活动 3-攻略 5-评测
        if(type == 1) {
            ((ViewHolder) holder).tvNewsType.setText("新闻");
        }else if(type == 2){
            ((ViewHolder) holder).tvNewsType.setText("活动");
        }else if(type == 3){
            ((ViewHolder) holder).tvNewsType.setText("攻略");
        }else if(type == 5){
            ((ViewHolder) holder).tvNewsType.setText("评测");
        }else{
            ((ViewHolder) holder).tvNewsType.setVisibility(View.GONE);
        }
        ((ViewHolder)holder).tvNewsTime.setText(news.getPudate());
        int placehoder = R.mipmap.error_pic;
        if(BuildConfig.projectCode == 74){
            placehoder = R.mipmap.news_default;
        }
        GlideDisplay.display(((ViewHolder)holder).ivNewsImage, news.getImg(), placehoder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebViewActivity.start(v.getContext(), news.getTitle(), AppApi.getUrl(AppApi.newsDetail+news.getId()), news.getGameid()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    @Override
    public void notifyDataChanged(List<NewsListBean.DataBean.ListBean> newsListBeen, boolean b) {

    }

    @Override
    public List<NewsListBean.DataBean.ListBean> getData() {
        return newsList;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_news_image)
        ImageView ivNewsImage;
        @BindView(R.id.tv_news_title)
        TextView tvNewsTitle;
        @BindView(R.id.tv_news_type)
        TextView tvNewsType;
        @BindView(R.id.tv_news_time)
        TextView tvNewsTime;
        @BindView(R.id.v_line)
        View vLine;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
