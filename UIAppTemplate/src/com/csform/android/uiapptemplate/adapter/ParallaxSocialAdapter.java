package com.csform.android.uiapptemplate.adapter;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.nhaarman.listviewanimations.util.Swappable;

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

			holder.layout = (LinearLayout) convertView
					.findViewById(R.id.layout_header_of_item);
			holder.friends = (LinearLayout) convertView
					.findViewById(R.id.friends);
			holder.photo = (ImageView) convertView
					.findViewById(R.id.lvis_photo);
			holder.image = (ImageView) convertView
					.findViewById(R.id.lvis_image);
			holder.title = (TextView) convertView.findViewById(R.id.lvis_title);

			holder.createdOn = (TextView) convertView.findViewById(R.id.lvis_created_on);
			holder.description = (TextView) convertView.findViewById(R.id.lvis_description);
			holder.share = (TextView) convertView.findViewById(R.id.lvis_share);
			holder.image.setOnClickListener(this);
			holder.share.setOnClickListener(this);
			holder.friends.setOnClickListener(this);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position != 0)
			holder.layout.setVisibility(View.GONE);
		else
			holder.layout.setVisibility(View.VISIBLE);

		NewsModel dm = mNewsModelList.get(position);

		ImageUtil.displayRoundImage(holder.photo, "http://pengaja.com/uiapptemplate/newphotos/profileimages/2.jpg", null);
		ImageUtil.displayImage(holder.image, dm.getImageURL(), null);
		holder.createdOn.setText(dm.getCreatedOn());
		holder.title.setText(dm.getTitle());
		holder.description.setText(dm.getDescription());
		holder.image.setTag(position);
		holder.share.setTag(position);
		holder.friends.setTag(position);
		return convertView;
	}

	private static class ViewHolder {
		public LinearLayout layout;
		public LinearLayout friends;
		public ImageView photo;
		public ImageView image;
		public TextView title;
		public TextView createdOn;
		public TextView description;
		public TextView viewCount;
		public TextView like;
		public TextView comment;
		public TextView share;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.v(TAG, ""+v.getTag());
		int position = (Integer) v.getTag();
		switch (v.getId()){
		case R.id.lvis_image:
			Toast.makeText(mContext, "Image: " + position, Toast.LENGTH_SHORT)
					.show();
			break;

		case R.id.lvis_share:
			Toast.makeText(mContext, "Push: " + position, Toast.LENGTH_SHORT)
					.show();
			break;
		case R.id.friends:
			Toast.makeText(mContext, "Friends", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
