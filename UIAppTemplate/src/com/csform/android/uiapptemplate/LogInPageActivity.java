package com.csform.android.uiapptemplate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

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
		TextView login, register;
		login = (TextView) findViewById(R.id.login);
		register = (TextView) findViewById(R.id.register);

		login.setOnClickListener(this);
		register.setOnClickListener(this);
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