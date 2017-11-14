package com.etsdk.app.huov7.provider;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.model.GameBean;
import com.etsdk.app.huov7.model.TjMustPlay;
import com.etsdk.app.huov7.ui.GameDetailV2Activity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by liu hong liang on 2016/12/21.
 */
public class TjMustPlayViewProvider
        extends ItemViewProvider<TjMustPlay, TjMustPlayViewProvider.ViewHolder> {
    private Context mContext;
    public TjMustPlayViewProvider(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_tj_must_play, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, @NonNull final TjMustPlay tjMustPlay) {
        final List<GameBean> gameBeanList = tjMustPlay.getGameBeans();
        Glide.with(mContext)
                .load(gameBeanList.get(0).getIcon())
                .placeholder(R.mipmap.error_pic)
                .crossFade()
                .into(holder.imgPlay01);
        Glide.with(mContext)
                .load(gameBeanList.get(1).getIcon())
                .placeholder(R.mipmap.error_pic)
                .crossFade()
                .into(holder.imgPlay02);
        Glide.with(mContext)
                .load(gameBeanList.get(2).getIcon())
                .placeholder(R.mipmap.error_pic)
                .crossFade()
                .into(holder.imgPlay03);
        Glide.with(mContext)
                .load(gameBeanList.get(3).getIcon())
                .placeholder(R.mipmap.error_pic)
                .crossFade()
                .into(holder.imgPlay04);

        holder.tvPlayer01.setText(gameBeanList.get(0).getGamename());
        holder.tvPlayer02.setText(gameBeanList.get(1).getGamename());
        holder.tvPlayer03.setText(gameBeanList.get(2).getGamename());
        holder.tvPlayer04.setText(gameBeanList.get(3).getGamename());

        holder.tvStatus01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameDetailV2Activity.start(mContext, gameBeanList.get(0).getGameid());
            }
        });

        holder.tvStatus02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameDetailV2Activity.start(mContext, gameBeanList.get(1).getGameid());
            }
        });

        holder.tvStatus03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameDetailV2Activity.start(mContext, gameBeanList.get(2).getGameid());
            }
        });

        holder.tvStatus04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameDetailV2Activity.start(mContext, gameBeanList.get(3).getGameid());
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.tv_down_status1)
        TextView tvStatus01;
        @BindView(R.id.tv_down_status2)
        TextView tvStatus02;
        @BindView(R.id.tv_down_status3)
        TextView tvStatus03;
        @BindView(R.id.tv_down_status4)
        TextView tvStatus04;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}