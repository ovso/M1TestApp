package com.tistory.ovso.m1test;

import com.tistory.ovso.m1test.model.Channel;

import hugo.weaving.DebugLog;

public class MainPresenterImpl implements MainPresenter {

    private View mView;
    private MainModel mModel;
    private ImageInteractor mInteractor;
    MainPresenterImpl(MainPresenter.View view) {
        mView = view;
        mModel = new MainModel();
        mInteractor = new ImageInteractor();
        mInteractor.setOnResultListener(mOnImageInteractor);
    }
    private ImageInteractor.OnResultListener mOnImageInteractor = new ImageInteractor.OnResultListener() {
        @DebugLog
        @Override
        public void onChannel(Channel channel, boolean isUpdate) {
            if(isUpdate) {
                mView.updateRecyclerView(channel.item);
            } else {
                mView.setRecyclerView(channel.item);
            }

            mView.hideRefresh();
        }

        @Override
        public void onFail() {

        }
    };
    @Override
    public void onCreate() {
        mView.setRootView();
        mView.setToolbar();
        mView.setDrawerLayout();
        mView.setEventListener();
        mInteractor.execute(mModel.getPageCount(), false);
    }

    @Override
    public void onRecyclerViewLoadMore() {
        int pageCount = mModel.getPageCount();
        if(pageCount < 4) mInteractor.execute(pageCount, true);
    }

    @Override
    public void onSwipeRefresh() {
        mView.showRefresh();
        mView.clearRecyclerView();
        mModel.clearPageCount();
        mInteractor.execute(mModel.getPageCount(), false);
    }
}
