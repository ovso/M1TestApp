package com.tistory.ovso.m1test;

import com.tistory.ovso.m1test.model.Item;

import java.util.List;

public interface MainPresenter {

    void onCreate();

    interface View {

        void setRecyclerView(List<Item> items);

        void setRootView();

        void setToolbar();

        void setDrawerLayout();
    }
}
