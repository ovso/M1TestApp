package com.tistory.ovso.m1test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.tistory.ovso.m1test.model.Item;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    private List<Item> mItemList = new ArrayList<>();

    public RecyclerViewAdapter(List<Item> itemList) {
        mItemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.content_main_item, null);
        return new ViewHolderExt(view);
    }
    private View.OnClickListener mOnClickListener;
    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolderExt viewHolder = (ViewHolderExt) holder;
        Context context = viewHolder.contentIv.getContext();
        Item item = mItemList.get(position);
        Glide.with(context).load(item.image).into(viewHolder.contentIv);
        viewHolder.onClickListener = mOnClickListener;

    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public void setUpdateList(List<Item> item) {
        for (Item item1 : item) {
            mItemList.add(item1);
            notifyItemInserted(mItemList.indexOf(item1));
        }
    }

    public void removeItem() {
        mItemList.clear();
    }

    final static class ViewHolderExt extends RecyclerView.ViewHolder {
        @BindView(R.id.content_imageview)
        ImageView contentIv;
        View.OnClickListener onClickListener;
        public ViewHolderExt(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.content_imageview)
        void onItemClick() {
            onClickListener.onClick(null);
        }
    }

}