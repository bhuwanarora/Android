package com.csform.android.uiapptemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csform.android.uiapptemplate.util.ImageUtil;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class LogInPageActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
																// ActionBar
        ImageLoader imageLoader = ImageLoader.getInstance();
        if (!imageLoader.isInited()) {
            imageLoader.init(ImageLoaderConfiguration.createDefault(this));
        }

		setContentView();
	}

	private void setContentView(){
		setContentView(R.layout.activity_login_page_light);
		TextView explore;
		explore = (TextView) findViewById(R.id.explore);
		explore.setOnClickListener(this);
		ImageView appLogo = (ImageView) findViewById(R.id.app_logo);
		ImageUtil.displayImage(appLogo, "https://scontent-mxp1-1.xx.fbcdn.net/hphotos-xtf1/v/t1.0-9/11987175_485412268296315_6744033056838674439_n.png?oh=d256084625a894a7b0a21b329bc21c72&oe=56F936E9", null);
	}

	@Override
	public void onClick(View v) {
        Intent myIntent = new Intent(LogInPageActivity.this, SpacesListActivity.class);
//        myIntent.putExtra("key", value); //Optional parameters
        LogInPageActivity.this.startActivity(myIntent);

		if (v instanceof TextView) {
			TextView tv = (TextView) v;
			Toast.makeText(this, "WELCOME", Toast.LENGTH_SHORT).show();
		}
	}
}