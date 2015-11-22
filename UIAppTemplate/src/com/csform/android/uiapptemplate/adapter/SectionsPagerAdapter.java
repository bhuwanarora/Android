package com.csform.android.uiapptemplate.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.csform.android.uiapptemplate.fragment.PlaceholderFragment;
import com.csform.android.uiapptemplate.fragment.SpacesNewsFragment;
import com.csform.android.uiapptemplate.fragment.SpacesVideosFragment;


/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
    private static final String TAG = "SectionsPagerAdapter";
    private static final int NEWS = 0;
    private static final int VIDEOS = 1;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        Log.v(TAG, " getItem "+position);
        if(position == NEWS){
            return SpacesNewsFragment.newInstance();
        }
        else{
            return SpacesVideosFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case NEWS:
                return "News";
            case VIDEOS:
                return "Videos";
        }
        return null;
    }
}