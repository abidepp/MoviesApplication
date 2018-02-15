package com.example.userpc.myapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.userpc.myapplication.fragments.EventsFragment;
import com.example.userpc.myapplication.fragments.ExportsFragment;
import com.example.userpc.myapplication.fragments.ImportsFragment;

/**
 * Created by user pc on 12/28/2017.
 */
public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    public MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        if(position == 0)
        {
            fragment = new EventsFragment();
            return fragment;
        }
        if(position == 1)
        {
            fragment = new ImportsFragment();
            return fragment;
        }
        if(position == 2)
        {
            fragment = new ExportsFragment();
            return fragment;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0)
            return "Events";
        if(position == 1)
            return "Imports";
        if(position == 2)
            return "Exports";
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return 3;
    }
}
