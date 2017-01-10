package com.tistory.ovso.m1test;

import com.tistory.ovso.m1test.model.Item;

import java.util.ArrayList;
import java.util.List;

class MainModel {
    private int mPageCount = 0;
    public int getPageCount() {
        ++mPageCount;
        return mPageCount;
    }
    public void setPageCount(int pageCount) {
        this.mPageCount = pageCount;
    }
    private List<Item> mItemList = new ArrayList<>();
    public void setItemList(List<Item> item) {
        mItemList.addAll(item);
    }
    public List<Item> getItemList() {
        return mItemList;
    }
}
