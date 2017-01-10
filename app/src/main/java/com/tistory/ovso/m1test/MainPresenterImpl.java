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
        public void onChannel(Channel channel) {
            mView.setRecyclerView(channel.item);
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
        mInteractor.execute();
    }
}
