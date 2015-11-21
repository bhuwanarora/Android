package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.csform.android.uiapptemplate.NewsArticleActivity;
import com.csform.android.uiapptemplate.ParallaxKenBurnsActivity;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collections;

public class SpacesAdapter extends BaseAdapter implements Swappable,
        OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<NewsModel> mNewsModelList;
    private static final String TAG = "SpacesAdapter";
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public SpacesAdapter(Context context,
                                 ArrayList<NewsModel> newsModelList,
                                 boolean shouldShowDragAndDropIcon) {
        Log.v(TAG, "Constructor NewsModelList " + newsModelList);
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mNewsModelList = newsModelList;
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
        Log.v(TAG, " getView " + position + " convertView " + convertView);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_parallax_social, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.lvis_title);
//            holder.viewCount = (TextView) convertView.findViewById(R.id.lvis_views);
            holder.createdOn = (TextView) convertView.findViewById(R.id.lvis_created_on);
            holder.description = (TextView) convertView.findViewById(R.id.lvis_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NewsModel dm = mNewsModelList.get(position);
        holder.title.setText(dm.getTitle());
        holder.createdOn.setText(dm.getCreatedOn());
        holder.description.setText(dm.getDescription());

        holder.title.setTag(dm.getUrl());
        holder.title.setOnClickListener(this);
        return convertView;
    }

    private static class ViewHolder {
        // public ImageView image;
        // public ImageView photo;
        public TextView title;
        public TextView createdOn;
        public TextView description;
    }

    @Override
    public void onClick(View v){
        Log.v(TAG, " onClick "+v.getTag());
        String url = (String) v.getTag();

        Intent intent = new Intent(mContext, NewsArticleActivity.class);
        intent.putExtra(EXTRA_MESSAGE, url);
        mContext.startActivity(intent);
    }
}
