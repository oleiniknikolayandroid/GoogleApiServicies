package com.example.googleapiservicies.presentation.activities.auth.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.example.googleapiservicies.presentation.fragments.FacebookFragment;
import com.example.googleapiservicies.presentation.fragments.GoogleFragment;
import com.example.googleapiservicies.presentation.fragments.NavigationFragment;

public class AuthPagerAdapter extends FragmentPagerAdapter {
    @Override
    public CharSequence getPageTitle(int position) {
        return "Title " + position;
    }

    public AuthPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NavigationFragment();
            case 1:
                return new GoogleFragment();
            case 2:
                return new FacebookFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

}