package com.etsdk.app.huov7.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.etsdk.app.huov7.R;
import com.liang530.photopicker.ShowPicVPActivity;
import com.liang530.utils.GlideDisplay;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liu hong liang on 2016/10/11.
 */

public class GameImageAdapter extends RecyclerView.Adapter<GameImageAdapter.ViewHolder> {
    private ArrayList<String> data=new ArrayList();
    public GameImageAdapter(ArrayList<String> data) {
        this.data = data;
    }
    @Override
    public GameImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_game_image, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(GameImageAdapter.ViewHolder holder, final int position) {
//        GlideDisplay.display(holder.ivGameImg,data.get(position),R.mipmap.portrait_load);
        Glide.with(holder.ivGameImg.getContext()).load(data.get(position)).dontAnimate().placeholder(R.mipmap.gg).into(holder.ivGameImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPicVPActivity.start(v.getContext(),data,position,false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data==null?0:data.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_game_img)
        ImageView ivGameImg;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
