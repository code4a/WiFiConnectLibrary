package com.code4a.wificonnectlibrary;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.code4a.wificonnectlibrary.frag.weal.CommonFragment;
import com.code4a.wificonnectlibrary.frag.weal.WealFragment;
import com.code4a.wificonnectlibrary.net.GankIoManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by code4a on 2017/5/23.
 */

public class RetrofitActivity extends AppCompatActivity {

    @BindView(android.R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.toolBar)
    Toolbar toolBar;

    GankIoManager gankIoManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        gankIoManager = GankIoManager.create();
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        return WealFragment.getInstance();
                    case 1:
                        CommonFragment android = CommonFragment.getInstance();
                        android.setArguments(getBundle(position));
                        return android;
                    case 2:
                        CommonFragment ios = CommonFragment.getInstance();
                        ios.setArguments(getBundle(position));
                        return ios;
                    case 3:
                        CommonFragment source = CommonFragment.getInstance();
                        source.setArguments(getBundle(position));
                        return source;
                    case 4:
                        CommonFragment web = CommonFragment.getInstance();
                        web.setArguments(getBundle(position));
                        return web;
                    default:
                        return WealFragment.getInstance();
                }
            }

            @NonNull
            private Bundle getBundle(int position) {
                Bundle pBundle = new Bundle();
                pBundle.putString(CommonFragment.BUNDLE_KEY, getPageTitle(position).toString());
                return pBundle;
            }

            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.title_weal);
                    case 1:
                        return getString(R.string.title_android);
                    case 2:
                        return getString(R.string.title_ios);
                    case 3:
                        return getString(R.string.title_resource);
                    case 4:
                        return getString(R.string.title_web);
                    default:
                        return getString(R.string.title_weal);
                }
            }
        });
        tabLayout.setupWithViewPager(viewPager);
    }

    public GankIoManager getGankIoManager() {
        return gankIoManager;
    }
}
