package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csform.android.uiapptemplate.ParallaxKenBurnsActivity;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.SearchItemModel;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.nhaarman.listviewanimations.util.Swappable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter implements View.OnClickListener {
	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<SearchItemModel> mSearchItemModels;
	public static final String TAG = "SearchAdapter";
	public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";
	
	public SearchAdapter(Context context, ArrayList<SearchItemModel> searchItemModels) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mSearchItemModels = searchItemModels;
	}

	@Override
	public int getCount() {
		return mSearchItemModels.size();
	}

	@Override
	public SearchItemModel getItem(int position) {
		return mSearchItemModels.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_view_item_search, parent, false);
			holder = new ViewHolder();

			holder.photo = (ImageView) convertView.findViewById(R.id.lvis_photo);
			holder.name = (TextView) convertView.findViewById(R.id.lvis_name);
			holder.type = (TextView) convertView.findViewById(R.id.lvis_type);
			holder.layout = (LinearLayout) convertView.findViewById(R.id.lvis_search_item);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		SearchItemModel searchItemModel = getItem(position);
		Log.v(TAG, searchItemModel.toString());
		String name = searchItemModel.getName();
		String imageUrl = searchItemModel.getImageUrl();
		String type = searchItemModel.getType();
		long id = searchItemModel.getId();

		ImageUtil.displayImage(holder.photo, imageUrl, null);
		holder.name.setText(name);
		holder.type.setText(type);

		try {
			JSONObject params = new JSONObject();
			params.put("id", Long.toString(searchItemModel.getId()));
			params.put("name", searchItemModel.getName());
			params.put("image_url", searchItemModel.getImageUrl());
			params.put("type", searchItemModel.getType());
			holder.layout.setTag(params.toString());
			holder.layout.setOnClickListener(this);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return convertView;
	}

	private static class ViewHolder {
		public ImageView photo;
		public TextView name;
		public TextView type;
		public LinearLayout layout;
	}

	@Override
	public void onClick(View v){
		try {
			Log.v(TAG, " onClick " + v.getTag());
			String params = (String) v.getTag();
			String type = (String) new JSONObject(params).get("type");
			if(type.equalsIgnoreCase("Spaces")){
				Intent intent = new Intent(mContext, ParallaxKenBurnsActivity.class);
				intent.putExtra(EXTRA_MESSAGE, params);
				mContext.startActivity(intent);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}