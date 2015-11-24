package com.csform.android.uiapptemplate;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.csform.android.uiapptemplate.adapter.SectionsPagerAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListRecyclerAdapter;
import com.csform.android.uiapptemplate.util.ColorUtil;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.csform.android.uiapptemplate.view.kbv.KenBurnsView;

import org.json.JSONException;
import org.json.JSONObject;

public class SpacesMainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private KenBurnsView mHeaderPicture;
    private static final String TAG = SpacesMainActivity.class.toString();

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
            Log.v(TAG, " SPACES ID "+spacesId);

            if (toolbar != null) {
                toolbar.setTitle(name);
            }

            setSupportActionBar(toolbar);
            setToolbarColor();
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

    private void setToolbarColor(){
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    if(!isShow){
                        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor(ColorUtil.getRandomColor()));
                        getSupportActionBar().setBackgroundDrawable(colorDrawable);
                        isShow = true;
                    }
                } else if(isShow) {
                    getSupportActionBar().setBackgroundDrawable(null);
                    isShow = false;
                }
            }
        });
    }

}