package com.example.iurymiguel.daggerapp.views.activities;

import android.databinding.DataBindingUtil;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.iurymiguel.daggerapp.R;
import com.example.iurymiguel.daggerapp.databinding.ActivityMainBinding;
import com.example.iurymiguel.daggerapp.views.fragments.DeviceDetailsFragment;
import com.example.iurymiguel.daggerapp.views.fragments.DevicesListFragment;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setSupportActionBar(mBinding.toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mBinding.viewPager.setAdapter(mSectionsPagerAdapter);

        mBinding.viewPager.addOnPageChangeListener(new TabLayout
                .TabLayoutOnPageChangeListener(mBinding.tabs));

        mBinding.tabs.addOnTabSelectedListener(new TabLayout
                .ViewPagerOnTabSelectedListener(mBinding.viewPager));
    }

    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        private final int NUM_OF_FRAGMENTS = 2;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return DevicesListFragment.newInstance();
                default:
                    return DeviceDetailsFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return NUM_OF_FRAGMENTS;
        }
    }
}
