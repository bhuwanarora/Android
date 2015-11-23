package com.csform.android.uiapptemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.csform.android.uiapptemplate.adapter.SectionsPagerAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListRecyclerAdapter;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.csform.android.uiapptemplate.view.kbv.KenBurnsView;

import org.json.JSONException;
import org.json.JSONObject;

public class SpacesMainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private KenBurnsView mHeaderPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_main);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

            Intent intent = getIntent();
            JSONObject params = new JSONObject(intent.getStringExtra(SpacesListRecyclerAdapter.EXTRA_MESSAGE));
            String image_url = (String) params.get("image_url");
            String name = (String) params.get("name");
            String spacesId = (String) params.get("id");

            if (toolbar != null) {
                toolbar.setTitle(name);
            }
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            mHeaderPicture = (KenBurnsView) findViewById(R.id.header_picture);
            ImageUtil.displayImage(mHeaderPicture, image_url, null);
            mHeaderPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);

            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}