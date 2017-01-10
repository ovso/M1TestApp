package com.tistory.ovso.m1test;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.tistory.ovso.m1test.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainPresenter.View{

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

    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefreshLayout;

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
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(itemList);
        mRecyclerView.addOnScrollListener(new EndlessRecyclerScrollListener(layout) {
            @Override
            public void onLoadMore() {
                mPresenter.onRecyclerViewLoadMore();
            }
        });
        mRecyclerView.setAdapter(adapter);
    }

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
        RecyclerViewAdapter adapter = (RecyclerViewAdapter) mRecyclerView.getAdapter();
        adapter.setUpdateList(item);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setEvent() {
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.onSwipeRefresh();
            }
        });

    }

    @Override
    public void showRefresh() {
        mRefreshLayout.setEnabled(true);
    }

    @Override
    public void hideRefresh() {
        mRefreshLayout.setEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
