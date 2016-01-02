package com.ivb.udacity.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by S.Shivasurya on 1/2/2016 - androidStudio.
 */
public class youtubeAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public youtubeAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
