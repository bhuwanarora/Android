package com.csform.android.uiapptemplate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.csform.android.uiapptemplate.adapter.BookModel;
import com.csform.android.uiapptemplate.adapter.SpacesAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesRecyclerAdapter;
import com.csform.android.uiapptemplate.adapter.VideoAdapter;
import com.csform.android.uiapptemplate.adapter.YearAdapter;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.csform.android.uiapptemplate.model.VideoModel;
import com.csform.android.uiapptemplate.util.AsyncContent;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.csform.android.uiapptemplate.view.AlphaForegroundColorSpan;
import com.csform.android.uiapptemplate.view.MaterialRippleLayout;
import com.csform.android.uiapptemplate.view.kbv.KenBurnsView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Please NOTE: In manifest, set theme for this class to
 * \@style/OverlayActionBar
 * @author MLADJO
 *
 */
public class ParallaxKenBurnsActivity extends Activity {
	
	public static final String TAG = "PKBurnsActivity";
	private static String spacesId;

	private SpacesAdapter spacesAdapter;
	private VideoAdapter videoAdapter;
	private YearAdapter yearAdapter;
	private static LinearLayout yearGallery = null;

	private int mActionBarTitleColor;
	private int mActionBarHeight;
	private int mHeaderHeight;
	private int mMinHeaderTranslation;
	private ListView mListView;
	private SeekBar volumeControl = null;
	private KenBurnsView mHeaderPicture;
	private TextView mHeaderLogo;
	private View mHeader;
	private View mPlaceHolderView;
	private RecyclerView recyclerView;
	private AccelerateDecelerateInterpolator mSmoothInterpolator;
	private RectF mRect1 = new RectF();
	private RectF mRect2 = new RectF();
	private AlphaForegroundColorSpan mAlphaForegroundColorSpan;
	private SpannableString mSpannableString;
	private TypedValue mTypedValue = new TypedValue();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        Intent intent = getIntent();
		try {
			JSONObject params = new JSONObject(intent.getStringExtra(SpacesListAdapter.EXTRA_MESSAGE));
			String image_url = (String) params.get("image_url");
			String name = (String) params.get("name");
			spacesId = (String) params.get("id");

			mSmoothInterpolator = new AccelerateDecelerateInterpolator();
			mHeaderHeight = getResources().getDimensionPixelSize(R.dimen.ken_burns_header);
			mMinHeaderTranslation = -mHeaderHeight + getActionBarHeight();
			setContentView(R.layout.activity_parallax_ken_burns);
			mListView = (ListView) findViewById(R.id.list_view);

//			handleRecyclerView();

			mHeader = findViewById(R.id.header);

			mHeaderPicture = (KenBurnsView) findViewById(R.id.header_picture);
			ImageUtil.displayImage(mHeaderPicture, image_url, null);

			mHeaderPicture.setScaleType(ScaleType.CENTER_CROP);
			mHeaderLogo = (TextView) findViewById(R.id.header_logo);
			mHeaderLogo.setText(name.toUpperCase());
			mHeaderLogo.setTypeface(Typeface.DEFAULT_BOLD);
			mActionBarTitleColor = Color.WHITE;
//			mSpannableString = new SpannableString(name.toUpperCase());
			mAlphaForegroundColorSpan = new AlphaForegroundColorSpan(mActionBarTitleColor);
			setupActionBar();
			handleYearSlider();
//			setupListView(params);
			setUpVideosList(params);

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	private void handleRecyclerView(){
//		recyclerView = (RecyclerView)findViewById(R.id.rv);
//		recyclerView.setHasFixedSize(true);

//		LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ParallaxKenBurnsActivity.this);
//		recyclerView.setLayoutManager(linearLayoutManager);
	}

	private void handleYearSlider(){
		yearGallery = (LinearLayout) findViewById(R.id.horizontallistview);
		ArrayList<String> yearsArray = new YearAdapter(this).getItemsArray();
		for(String year: yearsArray){
			int wrap_content = LinearLayout.LayoutParams.WRAP_CONTENT;
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wrap_content, wrap_content);
			TextView textView = new TextView(this);
			textView.setLayoutParams(layoutParams);
			textView.setText(year);
			textView.setTag(year);
			textView.setPadding(50, 10, 50, 20);
			textView.setTextSize(14);
			textView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Integer year = Integer.parseInt((String) v.getTag());
					spacesAdapter = new SpacesAdapter(ParallaxKenBurnsActivity.this, getNewsModelList(spacesId, year), false);
					mListView.setAdapter(spacesAdapter);
				}
			});

			MaterialRippleLayout layout = new MaterialRippleLayout(this);
			layout.setLayoutParams(layoutParams);
			layout.addView(textView);

			yearGallery.addView(layout);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

//	private void setupListView(JSONObject params){
//		SpacesRecyclerAdapter adapter = new SpacesRecyclerAdapter(getNewsModelList(newsId, YearAdapter.endYear));
//		recyclerView.setAdapter(adapter);
//	}

	private void setUpVideosList(JSONObject params){
		mPlaceHolderView = getLayoutInflater().inflate(R.layout.header_fake, mListView, false);
		videoAdapter = new VideoAdapter(this, getVideosModelList(spacesId));
		mListView.setAdapter(videoAdapter);
		bindScrollListeners();
	}

	private void bindScrollListeners(){
		mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				int scrollY = getScrollY();
				mHeader.setTranslationY(Math.max(-scrollY, mMinHeaderTranslation));
				float ratio = clamp(mHeader.getTranslationY() / mMinHeaderTranslation, 0.0f, 1.0f);
				interpolate(mHeaderLogo, getActionBarIconView(), mSmoothInterpolator.getInterpolation(ratio));
				setTitleAlpha(clamp(5.0F * ratio - 4.0F, 0.0F, 1.0F));
			}
		});

		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(ParallaxKenBurnsActivity.this,
						mListView.getAdapter().getItem(position).toString(),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void setupListView(JSONObject params){
		mPlaceHolderView = getLayoutInflater().inflate(R.layout.header_fake, mListView, false);
		spacesAdapter = new SpacesAdapter(this, getNewsModelList(spacesId, YearAdapter.endYear), false);
		mListView.setAdapter(spacesAdapter);
		bindScrollListeners();
	}

	public ArrayList<BookModel> getBooksModelList(String spacesId){
//		https://oditty.me/api/v0/basic_community_info?id=4998086
		return null;
	}

	public ArrayList<VideoModel> getVideosModelList(String spacesId){
		String tag_json_obj = "json_obj_req";
		Log.d(TAG, "getVideosModelList for spaces_id " + spacesId);
		String url = "https://oditty.me/api/v0/get_community_videos?id="+spacesId;
		final ArrayList<VideoModel> list = new ArrayList<>();

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
			url, null,
			new Response.Listener<JSONArray>() {
				@Override
				public void onResponse(JSONArray response) {
					int len = response.length();
					for (int i=0;i<len;i++){
						try {
							String title = response.getJSONObject(i).getString("title");
							String url = response.getJSONObject(i).getString("url");
							String publisher = response.getJSONObject(i).getString("publisher");
							String imageURL = response.getJSONObject(i).getString("thumbnail");
							int duration = response.getJSONObject(i).getInt("duration");
							String publishedDate = response.getJSONObject(i).getString("published_date");
							VideoModel videoModel = new VideoModel(0L, imageURL, title, publisher, publishedDate, duration, R.string.fontello_heart_empty, url);
							list.add(videoModel);
							videoAdapter.notifyDataSetChanged();
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					Log.d(TAG, response.toString());
				}
			}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				// hide the progress dialog
			}
		});

		// Adding request to request queue
		AsyncContent.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);
		return list;
	}

	public ArrayList<NewsModel> getNewsModelList(String spaces_id, int year){

		int skip = 0;

		String tag_json_obj = "json_obj_req";
		Log.d(TAG, "getNewsModelList for spaces_id " + spaces_id);
		String url = "https://oditty.me/api/v0/get_community_news?id="+spaces_id+"&skip=0&time="+year+"/9";
		final ArrayList<NewsModel> list = new ArrayList<>();

		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
				url, null,
				new Response.Listener<JSONArray>() {
					@Override
					public void onResponse(JSONArray response) {
						try {
							if(response.toString().equals("{}")){
								NewsModel newsModel = NewsModel.getEmptyNewsModel();
								list.add(newsModel);
								spacesAdapter.notifyDataSetChanged();
							}
							else{
								response = response.getJSONObject(0).getJSONArray("news");
								int len = response.length();
								for (int i=0;i<len;i++){
									try {
										int id = response.getJSONObject(i).getInt("news_id");
										String imageURL = response.getJSONObject(i).getString("image_url");
										String title = response.getJSONObject(i).getString("title");
										String description = response.getJSONObject(i).getString("description");
										String createdOn = response.getJSONObject(i).getString("created_at");
										int viewCount = response.getJSONObject(i).getInt("view_count");

										String newsURL = response.getJSONObject(i).getString("news_url");
										int bookmarkCount = response.getJSONObject(i).getInt("bookmark_count");

										NewsModel newsModel = new NewsModel(id, imageURL, title, description, createdOn, viewCount, R.string.fontello_heart_empty, newsURL);

										list.add(newsModel);
										spacesAdapter.notifyDataSetChanged();
									} catch (JSONException e) {
										e.printStackTrace();
									}
								}
								Log.d(TAG, response.toString());
							}
						}
						catch (JSONException e){
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				VolleyLog.d(TAG, "Error: " + error.getMessage());
				// hide the progress dialog
			}
		});

		// Adding request to request queue
		AsyncContent.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);
		return list;
	}

	private void setTitleAlpha(float alpha) {
		mAlphaForegroundColorSpan.setAlpha(alpha);
//		mSpannableString.setSpan(mAlphaForegroundColorSpan, 0, mSpannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		getActionBar().setTitle(mSpannableString);
	}

	public static float clamp(float value, float max, float min) {
		return Math.max(Math.min(value, min), max);
	}

	private void interpolate(View view1, View view2, float interpolation) {
		setOnScreenRect(mRect1, view1);
		setOnScreenRect(mRect2, view2);
		float scaleX = 1.0F + interpolation * (mRect2.width() / mRect1.width() - 1.0F);
		float scaleY = 1.0F + interpolation * (mRect2.height() / mRect1.height() - 1.0F);
		float translationX = 0.5F * (interpolation * (mRect2.left + mRect2.right - mRect1.left - mRect1.right));
		float translationY = 0.5F * (interpolation * (mRect2.top + mRect2.bottom - mRect1.top - mRect1.bottom));
//		view1.setTranslationX(translationX);
		view1.setTranslationY(translationY - mHeader.getTranslationY());
//		view1.setScaleX(scaleX);
//		view1.setScaleY(scaleY);
	}

	private void setOnScreenRect(RectF rect, View view) {
		rect.set(view.getLeft(), view.getTop(), view.getRight(),
				view.getBottom());
	}

	public int getScrollY() {
		View c = mListView.getChildAt(0);
		if (c == null) {
			return 0;
		}
		int firstVisiblePosition = mListView.getFirstVisiblePosition();
		int top = c.getTop();
		int headerHeight = 0;
		if (firstVisiblePosition >= 1) {
			headerHeight = mPlaceHolderView.getHeight();
		}
		return -top + firstVisiblePosition * c.getHeight() + headerHeight;
	}

	private void setupActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setIcon(R.drawable.ic_transparent);
	}

	private ImageView getActionBarIconView() {
		return (ImageView) findViewById(android.R.id.home);
	}

	public int getActionBarHeight() {
		if (mActionBarHeight != 0) {
			return mActionBarHeight;
		}
		getTheme().resolveAttribute(android.R.attr.actionBarSize, mTypedValue,
				true);
		mActionBarHeight = TypedValue.complexToDimensionPixelSize(
				mTypedValue.data, getResources().getDisplayMetrics());
		return mActionBarHeight;
	}
}