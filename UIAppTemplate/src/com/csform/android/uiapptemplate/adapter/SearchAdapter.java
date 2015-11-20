package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.SearchItemModel;
import com.csform.android.uiapptemplate.util.ImageUtil;

import java.util.ArrayList;

public class SearchAdapter extends BaseAdapter {
	
	private LayoutInflater mInflater;
	public static final String TAG = "SearchAdapter";
	private ArrayList<SearchItemModel> mSearchItemModels;
	
	public SearchAdapter(Context context, ArrayList<SearchItemModel> searchItemModels) {
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
		holder.photo.setTag(id);
		holder.name.setTag(id);
		holder.type.setTag(id);
		return convertView;
	}

	private static class ViewHolder {
		public ImageView photo;
		public TextView name;
		public TextView type;
	}
}
