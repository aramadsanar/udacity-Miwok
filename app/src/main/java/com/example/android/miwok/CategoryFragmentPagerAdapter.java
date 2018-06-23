package com.example.android.miwok;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CategoryFragmentPagerAdapter extends FragmentPagerAdapter {
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {
            case 0:
                return new NumbersFragment();
            case 1:
                return new ColorFragment();
            case 2:
                return new FamilyFragment();
            case 3:
                return new PhrasesFragment();
        }
        return null;
    }
    @Override
    public CharSequence getPageTitle(int pos) {
        switch(pos) {
            case 0:
                return "Numbers";
            case 1:
                return "Colors";
            case 2:
                return "Family";
            case 3:
                return "Phrases";
        }
        return "";
    }
    private Context mContext;
    public CategoryFragmentPagerAdapter(Context c, FragmentManager fm) {
        super(fm);
        mContext = c;
    }
}