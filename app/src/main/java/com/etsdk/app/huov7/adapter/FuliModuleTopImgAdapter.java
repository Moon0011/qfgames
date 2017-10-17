package com.etsdk.app.huov7.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.model.AdImage;
import com.etsdk.app.huov7.ui.CouponDetailActivity;
import com.etsdk.app.huov7.ui.GameDetailV2Activity;
import com.etsdk.app.huov7.ui.GiftDetailActivity;
import com.etsdk.app.huov7.ui.WebViewActivity;
import com.liang530.utils.GlideDisplay;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu hong liang on 2016/12/9.
 */

public class FuliModuleTopImgAdapter extends RecyclerView.Adapter {
    private List<AdImage> datas;

    public FuliModuleTopImgAdapter(List<AdImage> datas) {
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_fuli_module_top_img, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final AdImage adImage = datas.get(position);
        if(adImage!=null){
            GlideDisplay.display(((ViewHolder)holder).ivImage,datas.get(position).getImage(),R.mipmap.gg);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if("1".equals(adImage.getType())){
                        WebViewActivity.start(holder.itemView.getContext(),"",adImage.getUrl());
                    }else if("2".equals(adImage.getType())){
                        GameDetailV2Activity.start(holder.itemView.getContext(),adImage.getTarget()+"");
                    }else if("3".equals(adImage.getType())){
                        GiftDetailActivity.start(holder.itemView.getContext(),adImage.getTarget()+"");
                    }else if("4".equals(adImage.getType())){
                        CouponDetailActivity.start(holder.itemView.getContext(),adImage.getTarget()+"");
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return datas==null?0:datas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
