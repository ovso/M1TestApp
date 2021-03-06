package com.tistory.ovso.m1test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tistory.ovso.m1test.me.MeActivity;
import com.tistory.ovso.m1test.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import hugo.weaving.DebugLog;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View {

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MainPresenterImpl(this);
        mPresenter.onCreate();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @BindView(R.id.coordinator)
    CoordinatorLayout mCoordinator;

    @OnClick(R.id.fab)
    void onFloatingActionClick() {
        Snackbar.make(mCoordinator, "Thank you!", Snackbar.LENGTH_LONG).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;

    @Override
    public void setRecyclerView(List<Item> itemList) {
        GridLayoutManager layout = new GridLayoutManager(this, 3);
        layout.setOrientation(GridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layout);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerScrollListener(layout) {
            @Override
            public void onLoadMore() {
                mPresenter.onRecyclerViewLoadMore();
            }
        });
        mRecyclerViewAdapter = new RecyclerViewAdapter(itemList);
        mRecyclerViewAdapter.setOnClickListener(mOnRecyclerViewItemClickListener);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(mRecyclerViewAdapter);
        scaleAdapter.setDuration(1000);
        scaleAdapter.setFirstOnly(false);
        mRecyclerView.setAdapter(scaleAdapter);
    }

    private View.OnClickListener mOnRecyclerViewItemClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            navigateToMe();
        }
    };

    @DebugLog
    private void navigateToMe() {
        Intent intent = new Intent(MainActivity.this, MeActivity.class);
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(this, true);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(this, pairs);
        startActivity(intent, transitionActivityOptions.toBundle());
    }

    private RecyclerViewAdapter mRecyclerViewAdapter;
    private Unbinder mUnbinder;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
    }

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public void setToolbar() {
        setSupportActionBar(mToolbar);
    }

    @Override
    public void setDrawerLayout() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void updateRecyclerView(List<Item> item) {
        mRecyclerViewAdapter.setUpdateList(item);
        //mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;

    @Override
    public void setEventListener() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @DebugLog
            @Override
            public void onRefresh() {
                mPresenter.onSwipeRefresh();
            }
        });
    }

    @Override
    public void showRefresh() {
        mRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void clearRecyclerView() {
        mRecyclerViewAdapter.removeItem();
        mRecyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
