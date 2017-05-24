package com.code4a.wificonnectlibrary.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.code4a.wificonnectlibrary.R;
import com.code4a.wificonnectlibrary.bean.GankIoBean;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by code4a on 2017/5/24.
 */

public class GankIoListviewAdapter extends RecyclerView.Adapter {

    List<GankIoBean.ResultsBean> datas;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item2, parent, false);
        return new GankIoHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        GankIoHolder gankIoHolder = (GankIoHolder) holder;
        GankIoBean.ResultsBean gankIoBean = datas.get(position);
        gankIoHolder.descriptionTv.setText(gankIoBean.getDesc());
        if (gankIoBean.getImages() == null) {
            gankIoHolder.imageIv.setVisibility(View.GONE);
        } else {
            gankIoHolder.imageIv.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView.getContext()).load(gankIoBean.getImages().get(0)).into(gankIoHolder.imageIv);
        }
        String who = gankIoBean.getWho();
        gankIoHolder.who.setText("作者:" + (who == null ? "未知" : who));
    }

    public void addDatas(List<GankIoBean.ResultsBean> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    static class GankIoHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imageIv)
        ImageView imageIv;
        @BindView(R.id.descriptionTv)
        TextView descriptionTv;
        @BindView(R.id.who)
        TextView who;

        public GankIoHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
