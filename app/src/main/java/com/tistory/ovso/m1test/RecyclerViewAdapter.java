package com.tistory.ovso.m1test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tistory.ovso.m1test.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderExt> {

    private List<Item> mItemList;

    public RecyclerViewAdapter(List<Item> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerViewAdapter.ViewHolderExt onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.content_main_item, null);
        return new ViewHolderExt(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolderExt holder, int position) {
        Context context = holder.contentIv.getContext();
        Glide.with(context).load(mItemList.get(position).image).into(holder.contentIv);
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    final static class ViewHolderExt extends RecyclerView.ViewHolder {
        @BindView(R.id.content_imageview)
        ImageView contentIv;

        public ViewHolderExt(View itemView) {
            super(itemView);
            contentIv = (ImageView) itemView.findViewById(R.id.content_imageview);
            //ButterKnife.bind(itemView);
        }
    }
}