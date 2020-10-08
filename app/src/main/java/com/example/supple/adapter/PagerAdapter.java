package com.example.supple.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.supple.mallFragment.BcaaFragment;
import com.example.supple.mallFragment.MassFragment;
import com.example.supple.mallFragment.WheyFragment;


public class PagerAdapter extends FragmentStatePagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new WheyFragment();
                break;
            case 1:
                fragment = new MassFragment();
                break;
            case 2:
                fragment = new BcaaFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position) {
            case 0:
                title = "Whey";
                break;
            case 1:
                title = "Mass";
                break;
            case 2:
                title = "BCAA";
                break;
        }
        return title;
    }
}
