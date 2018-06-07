package com.wangsz.wusic.ui;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.wangsz.wusic.R;
import com.wangsz.wusic.ui.fragment.HomeFragment;
import com.wangsz.wusic.ui.fragment.TestFragment;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar mToolbar;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        showMenuFragment(navigationView.getMenu().getItem(0));
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
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            showMenuFragment(item);
        } else if (id == R.id.nav_gallery) {
            showMenuFragment(item);
        } else if (id == R.id.nav_slideshow) {
            showMenuFragment(item);
        } else if (id == R.id.nav_manage) {
            showMenuFragment(item);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showMenuFragment(MenuItem item) {
        mToolbar.setTitle(item.getTitle());
        showFragment(item);
    }

    //********************************************************************************************//
    FragmentManager mFragmentManager;
    FragmentTransaction mTransaction;

    private void showFragment(MenuItem item) {
        if (mFragmentManager == null) {
            mFragmentManager = getSupportFragmentManager();
        }
        mTransaction = mFragmentManager.beginTransaction();

        for (Fragment fragment : mFragmentManager.getFragments()) {
            mTransaction.hide(fragment);
        }

        Fragment fragment = mFragmentManager.findFragmentByTag(item.getTitle().toString());

        if (fragment != null) {
            mTransaction.show(fragment);
        } else {
            mTransaction.add(R.id.fragment, getCurrentFragment(item), item.getTitle().toString());
        }
        mTransaction.commitAllowingStateLoss();
    }

    private Fragment getCurrentFragment(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            return new HomeFragment();
        } else if (id == R.id.nav_gallery) {
            return TestFragment.getInstance(item.getTitle().toString());
        } else if (id == R.id.nav_slideshow) {
            return TestFragment.getInstance(item.getTitle().toString());
        } else if (id == R.id.nav_manage) {
            return TestFragment.getInstance(item.getTitle().toString());
        }
        return null;
    }

}
