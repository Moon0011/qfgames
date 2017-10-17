package com.etsdk.app.huov7.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etsdk.app.huov7.R;
import com.etsdk.app.huov7.model.StartServerInfo2;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.drakeet.multitype.ItemViewProvider;

/**
 * Created by liu hong liang on 2017/1/14.
 */
public class StartServerInfo2ViewProvider
        extends ItemViewProvider<StartServerInfo2, StartServerInfo2ViewProvider.ViewHolder> {

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm ");
    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(
            @NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View root = inflater.inflate(R.layout.item_start_server_info2, parent, false);
        return new ViewHolder(root);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull StartServerInfo2 startServerInfo2) {
        String startTime = null;
        try {
            startTime = sdf.format(new Date(Long.parseLong(startServerInfo2.getStarttime())*1000));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        if (startTime != null) {
            holder.tvInfo.setText(startTime + startServerInfo2.getSername());
        }else{
            holder.tvInfo.setText(startServerInfo2.getSername());
        }
        if("1".equals(startServerInfo2.getStatus())){
            holder.tvInfo.setTextColor(holder.itemView.getResources().getColor(R.color.text_black));
        }else{
            holder.tvInfo.setTextColor(holder.itemView.getResources().getColor(R.color.text_gray));
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_info)
        TextView tvInfo;
        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}