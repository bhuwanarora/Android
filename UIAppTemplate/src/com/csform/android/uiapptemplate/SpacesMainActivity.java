package com.csform.android.uiapptemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.csform.android.uiapptemplate.adapter.SectionsPagerAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListRecyclerAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesRecyclerAdapter;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.csform.android.uiapptemplate.view.kbv.KenBurnsView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

public class SpacesMainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private KenBurnsView mHeaderPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_main);

        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            Intent intent = getIntent();
            JSONObject params = new JSONObject(intent.getStringExtra(SpacesRecyclerAdapter.EXTRA_MESSAGE));
            String image_url = (String) params.get("image_url");
            String name = (String) params.get("name");
            String spacesId = (String) params.get("id");

            mHeaderPicture = (KenBurnsView) findViewById(R.id.header_picture);
            ImageUtil.displayImage(mHeaderPicture, image_url, null);

            mHeaderPicture.setScaleType(ImageView.ScaleType.CENTER_CROP);

            // Create the adapter that will return a fragment for each of the three
            // primary sections of the activity.
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

            // Set up the ViewPager with the sections adapter.
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);

            TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
            tabLayout.setupWithViewPager(mViewPager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}