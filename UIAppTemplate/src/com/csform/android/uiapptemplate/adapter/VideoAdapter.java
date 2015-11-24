package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.VideoModel;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collections;

public class VideoAdapter extends BaseAdapter implements Swappable,
        OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<VideoModel> mVideosModelList;
    private static final String TAG = "VideoAdapter";
    private com.nostra13.universalimageloader.core.ImageLoader imageLoader;
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public VideoAdapter(Context context, ArrayList<VideoModel> videosModelList) {
        Log.v(TAG, "Constructor VideosModelList " + videosModelList);
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mVideosModelList = videosModelList;
//        imageLoader = com.nostra13.universalimageloader.core.ImageLoader.getInstance();
//        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return mVideosModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mVideosModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mVideosModelList.get(position).getId();
    }

    @Override
    public void swapItems(int positionOne, int positionTwo) {
        Collections.swap(mVideosModelList, positionOne, positionTwo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v(TAG, " getView " + position + " convertView " + convertView);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_video, parent, false);
            holder = new ViewHolder();
            holder.layout = (LinearLayout) convertView.findViewById(R.id.lvis_video_item);
            holder.photo = (ImageView) convertView.findViewById(R.id.lvis_photo);
            holder.title = (TextView) convertView.findViewById(R.id.lvis_title);
            holder.publishedDate = (TextView) convertView.findViewById(R.id.lvis_published_date);
            holder.duration = (TextView) convertView.findViewById(R.id.lvis_duration);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        VideoModel dm = mVideosModelList.get(position);
        Log.v(TAG, " getView VideoModel " + dm);
        ImageUtil.displayImage(holder.photo, dm.getImageURL(), null);
        holder.title.setText(dm.getTitle());
        holder.publishedDate.setText((String) dm.getCreatedOn());
        holder.duration.setText(String.valueOf(dm.getViewCount()/60) + " mins");
        holder.layout.setTag(dm.getUrl());
        holder.layout.setOnClickListener(this);
        return convertView;
    }

    private static class ViewHolder {
        public LinearLayout layout;
        public ImageView photo;
        public TextView title;
        public TextView publishedDate;
        public TextView duration;
    }

    @Override
    public void onClick(View v){
        Log.v(TAG, " onClick "+v.getTag());
        String url = (String) v.getTag();

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.putExtra("force_fullscreen",true);
//        Intent intent = new Intent(mContext, VideoActivity.class);
//        intent.putExtra(EXTRA_MESSAGE, url);
        mContext.startActivity(intent);
    }
}
