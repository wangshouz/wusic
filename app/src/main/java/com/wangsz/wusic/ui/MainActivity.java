package com.wangsz.wusic.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.wangsz.wusic.R;
import com.wangsz.wusic.adapter.MainAdapter;
import com.wangsz.wusic.ui.fragment.LocalMusicsFragment;
import com.wangsz.wusic.ui.fragment.RecommendFragment;
import com.wangsz.wusic.ui.fragment.SheetFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;
    BottomNavigationViewEx mBottomNavigationViewEx;
    ViewPager mViewPager;
    List<Fragment> mFragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBottomNavigationViewEx = findViewById(R.id.bottomNavigationViewEx);
        mBottomNavigationViewEx.enableItemShiftingMode(true); //除了当前选中项，其他项的文本将会隐藏
        mBottomNavigationViewEx.enableShiftingMode(false); //选中项和其他项的宽度不一样。
        mBottomNavigationViewEx.setItemIconTintList(null);
        mBottomNavigationViewEx.setLargeTextSize(10);

        mFragments.add(new LocalMusicsFragment());
        mFragments.add(new SheetFragment());
        mFragments.add(new RecommendFragment());
        mViewPager = findViewById(R.id.viewPager);
        mViewPager.setAdapter(new MainAdapter(getSupportFragmentManager(), mFragments));
        mViewPager.setOffscreenPageLimit(2);
        mBottomNavigationViewEx.setupWithViewPager(mViewPager);

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search) {
//            Logger.d("search");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //*****************************************必要权限申请*******************************************//

}
