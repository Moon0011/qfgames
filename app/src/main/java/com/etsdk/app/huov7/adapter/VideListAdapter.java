package com.etsdk.app.huov7.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.model.H5GamesBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */

public class VideListAdapter extends RecyclerView.Adapter<VideListAdapter.ViewHolder> {
    private Context mContext;
    private LayoutInflater inf;
    private List<H5GamesBean> mDatas;

    public interface OnRecyclerViewItemListener {
        public void onItemClickListener(View view, int position);
    }

    private OnRecyclerViewItemListener mOnRecyclerViewItemListener;

    public void setOnRecyclerViewItemListener(OnRecyclerViewItemListener listener) {
        mOnRecyclerViewItemListener = listener;
    }

    public VideListAdapter(Context mContext, List<H5GamesBean> datas) {
        this.mContext = mContext;
        inf = LayoutInflater.from(mContext);
        mDatas = datas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = inf.inflate(R.layout.h5_card_item_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        holder.tvTitle.setText(mDatas.get(position).getGameName());
        holder.tvType.setText(mDatas.get(position).getType());
        holder.tvIntroduce.setText(mDatas.get(position).getIntroduce());
//        Glide.with(mContext)
//                .load(mDatas.get(position).getImgurl())
//                .placeholder(R.mipmap.error_pic)
//                .error(R.mipmap.error_pic)
//                .into(holder.imgAnchor);
        holder.imgAnchor.setImageResource(mDatas.get(position).getImgId());
        if (mOnRecyclerViewItemListener != null) {
            itemOnClick(holder, holder.btnopen);
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle, tvType, tvIntroduce;
        public ImageView imgAnchor;
        public Button btnopen;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvType = (TextView) itemView.findViewById(R.id.tv_type);
            tvIntroduce = (TextView) itemView.findViewById(R.id.tv_introduce);
            imgAnchor = (ImageView) itemView.findViewById(R.id.img_anchor);
            btnopen = (Button) itemView.findViewById(R.id.btn_open);
        }
    }

    private void itemOnClick(final RecyclerView.ViewHolder holder, Button open) {
        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                mOnRecyclerViewItemListener.onItemClickListener(holder.itemView, position);
            }
        });
    }

    public void clearData() {
        mDatas.clear();
    }

    public void addAllData(List<H5GamesBean> dataList) {
        mDatas.addAll(dataList);
        notifyDataSetChanged();
    }
}