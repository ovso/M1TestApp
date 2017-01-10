package com.tistory.ovso.m1test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.tistory.ovso.m1test.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private final static int VIEW_TYPE_NORMAL = 0;
    private final static int VIEW_TYPE_PROGRESS = 1;
    private List<Item> mItemList;

    public RecyclerViewAdapter(List<Item> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        if(VIEW_TYPE_NORMAL == viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.content_main_item, null);
            return new ViewHolderExt(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.content_main_item_progress, null);
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderExt) {
            ViewHolderExt viewHolder = (ViewHolderExt) holder;
            Context context = viewHolder.contentIv.getContext();
            Glide.with(context).load(mItemList.get(position).image).into(viewHolder.contentIv);
        } else {
            ((FooterViewHolder) holder).mProgressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mItemList.get(position) instanceof Item) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_PROGRESS;
        }
    }

    final static class ViewHolderExt extends RecyclerView.ViewHolder {
        @BindView(R.id.content_imageview)
        ImageView contentIv;

        public ViewHolderExt(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    final static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.footer)
        ProgressBar mProgressBar;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}