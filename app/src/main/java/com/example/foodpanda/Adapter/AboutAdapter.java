package com.example.foodpanda.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.foodpanda.fragment.about.CommentFragment;
import com.example.foodpanda.fragment.about.MapFragment;

public class AboutAdapter extends FragmentPagerAdapter {
    //integer to count number of tabs
    int tabCount;

    public AboutAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabCount = behavior;
    }


    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return MapFragment.newInstance();
            case 1:
                return CommentFragment.newInstance();
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
