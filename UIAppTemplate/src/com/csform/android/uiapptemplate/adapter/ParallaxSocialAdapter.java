package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collections;

public class ParallaxSocialAdapter extends BaseAdapter implements Swappable,
		OnClickListener {

	private Context mContext;
	private LayoutInflater mInflater;
	private ArrayList<NewsModel> mNewsModelList;
	private static final String TAG = "ParallaxSocialAdapter";

	public ParallaxSocialAdapter(Context context,
			ArrayList<NewsModel> NewsModelList,
			boolean shouldShowDragAndDropIcon) {
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mNewsModelList = NewsModelList;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public int getCount() {
		return mNewsModelList.size();
	}

	@Override
	public Object getItem(int position) {
		return mNewsModelList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return mNewsModelList.get(position).getId();
	}

	@Override
	public void swapItems(int positionOne, int positionTwo) {
		Collections.swap(mNewsModelList, positionOne, positionTwo);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(
					R.layout.list_item_parallax_social, parent, false);
			holder = new ViewHolder();

			holder.title = (TextView) convertView.findViewById(R.id.lvis_title);

			holder.createdOn = (TextView) convertView.findViewById(R.id.lvis_created_on);
			holder.description = (TextView) convertView.findViewById(R.id.lvis_description);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}


		NewsModel dm = mNewsModelList.get(position);

		holder.createdOn.setText(dm.getCreatedOn());
		holder.title.setText(dm.getTitle());
		holder.description.setText(dm.getDescription());
		holder.share.setTag(position);
		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView title;
		public TextView createdOn;
		public TextView description;
		public TextView share;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.v(TAG, ""+v.getTag());
		int position = (Integer) v.getTag();

	}
}