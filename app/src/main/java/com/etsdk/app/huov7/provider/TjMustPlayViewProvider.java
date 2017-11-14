package com.etsdk.app.huov7.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.model.TjMustPlay;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by liu hong liang on 2016/12/21.
 */
public class TjMustPlayViewProvider
        extends ItemViewProvider<TjMustPlay, TjMustPlayViewProvider.ViewHolder> {

    public TjMustPlayViewProvider() {
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_tj_must_play, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull TjMustPlay tjMustPlay) {
        String[] playNames = tjMustPlay.getPlayNames();
        int[] playImgs = tjMustPlay.getPlayImgs();
        holder.imgPlay01.setImageResource(playImgs[0]);
        holder.imgPlay02.setImageResource(playImgs[1]);
        holder.imgPlay03.setImageResource(playImgs[2]);
        holder.imgPlay04.setImageResource(playImgs[3]);

        holder.tvPlayer01.setText(playNames[0]);
        holder.tvPlayer02.setText(playNames[1]);
        holder.tvPlayer03.setText(playNames[2]);
        holder.tvPlayer04.setText(playNames[3]);

        holder.player01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qfgames", "xiazai1");
            }
        });

        holder.player02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qfgames", "xiazai2");
            }
        });

        holder.player03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qfgames", "xiazai3");
            }
        });

        holder.player04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("qfgames", "xiazai4");
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.btn_player01)
        Button player01;
        @BindView(R.id.btn_player02)
        Button player02;
        @BindView(R.id.btn_player03)
        Button player03;
        @BindView(R.id.btn_player04)
        Button player04;
        @BindView(R.id.img_play01)
        ImageView imgPlay01;
        @BindView(R.id.img_play02)
        ImageView imgPlay02;
        @BindView(R.id.img_play03)
        ImageView imgPlay03;
        @BindView(R.id.img_play04)
        ImageView imgPlay04;
        @BindView(R.id.tv_play01)
        TextView tvPlayer01;
        @BindView(R.id.tv_play02)
        TextView tvPlayer02;
        @BindView(R.id.tv_play03)
        TextView tvPlayer03;
        @BindView(R.id.tv_play04)
        TextView tvPlayer04;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}