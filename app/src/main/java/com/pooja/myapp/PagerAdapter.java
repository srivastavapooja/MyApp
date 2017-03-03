package com.pooja.myapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Dumbledore on 10/19/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                HomeFragment home= new HomeFragment();
                return home;
            case 1:
                ProfileFragment profile = new ProfileFragment();
                return profile;
            case 2:
                StoreLocatorFragment cart = new StoreLocatorFragment();
                return cart;

        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
