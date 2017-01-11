package com.tistory.ovso.m1test;

import com.tistory.ovso.m1test.model.Item;

import java.util.List;

public interface MainPresenter {

    void onCreate();

    void onRecyclerViewLoadMore();

    void onSwipeRefresh();

    interface View {

        void setRecyclerView(List<Item> items);

        void setRootView();

        void setToolbar();

        void setDrawerLayout();

        void updateRecyclerView(List<Item> item);

        void setEventListener();

        void showRefresh();

        void hideRefresh();

        void clearRecyclerView();
    }
}
