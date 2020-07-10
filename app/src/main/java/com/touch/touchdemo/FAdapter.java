package com.touch.touchdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author weilgu
 * @time 2020/7/9
 * @des
 */
public class FAdapter extends FragmentStatePagerAdapter {

    List<String> mTabs;
    List<Fragment> mFragments;

    public FAdapter(@NonNull FragmentManager fm,List<String> tabs,List<Fragment> fragments) {
        super(fm);
        this.mTabs = tabs;
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTabs.get(position);
    }
}
