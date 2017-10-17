package com.etsdk.app.huov7.provider;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.model.Goods;
import com.etsdk.app.huov7.ui.EntityDetailActivity;
import com.game.sdk.util.GsonUtil;
import com.liang530.utils.GlideDisplay;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by liu hong liang on 2016/12/30.
 */
public class EntityListItemViewProvider extends ItemViewProvider<Goods, EntityListItemViewProvider.ViewHolder> {



    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_entity_list_item, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull final Goods goods) {
        holder.tvPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntityDetailActivity.start(v.getContext(), GsonUtil.getGson().toJson(goods));
            }
        });
        holder.tvPrice.setText("市场价:"+goods.getMarket_price()+"元");
        holder.tvName.setText(goods.getGoodsname());
        holder.tvRequestScore.setText(goods.getIntegral()+"积分");
        GlideDisplay.display(holder.ivGameImg,goods.getOriginal_img(),R.mipmap.ic_launcher);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EntityDetailActivity.start(v.getContext(),GsonUtil.getGson().toJson(goods));
            }
        });
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_game_img)
        ImageView ivGameImg;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.tv_request_score)
        TextView tvRequestScore;
        @BindView(R.id.btn_exchange)
        Button btnExchange;
        @BindView(R.id.game_list_item)
        RelativeLayout gameListItem;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}