//package com.csform.android.uiapptemplate.adapter;
//
//import com.csform.android.uiapptemplate.R;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//
//import com.csform.android.uiapptemplate.fragment.Tab1Fragment;
//import com.csform.android.uiapptemplate.fragment.Tab2Fragment;
//import com.csform.android.uiapptemplate.fragment.Tab3Fragment;
//
//
//
//public class PagerAdapter extends BaseAdapter {
//    int mNumOfTabs;
//
//    public PagerAdapter(int NumOfTabs) {
//        this.mNumOfTabs = NumOfTabs;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        return convertView;
//    }
//
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//
//        switch (position) {
//            case 0:
//                Tab1Fragment tab1 = new Tab1Fragment();
//                return tab1;
//            case 1:
//                Tab2Fragment tab2 = new Tab2Fragment();
//                return tab2;
//            case 2:
//                Tab3Fragment tab3 = new Tab3Fragment();
//                return tab3;
//            default:
//                return null;
//        }
//    }
//
//    @Override
//    public int getCount() {
//        return mNumOfTabs;
//    }
//}