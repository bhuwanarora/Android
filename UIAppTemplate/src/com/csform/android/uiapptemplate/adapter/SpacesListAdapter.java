package com.csform.android.uiapptemplate.adapter;

import java.util.ArrayList;
import java.util.Collections;

import android.content.Context;
import android.content.Intent;
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

import com.csform.android.uiapptemplate.ParallaxKenBurnsActivity;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.SpacesActivity;
import com.csform.android.uiapptemplate.SpacesListActivity;
import com.csform.android.uiapptemplate.SpacesParallaxActivity;
import com.csform.android.uiapptemplate.model.SpacesModel;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.nhaarman.listviewanimations.util.Swappable;

public class SpacesListAdapter extends BaseAdapter implements Swappable,
        OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SpacesModel> mSpacesModelList;
    private static final String TAG = "ParallaxSocialAdapter";
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public SpacesListAdapter(Context context,
                         ArrayList<SpacesModel> SpacesModelList,
                         boolean shouldShowDragAndDropIcon) {
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mSpacesModelList = SpacesModelList;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return mSpacesModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mSpacesModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mSpacesModelList.get(position).getId();
    }

    @Override
    public void swapItems(int positionOne, int positionTwo) {
        Collections.swap(mSpacesModelList, positionOne, positionTwo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_spaces, parent, false);
            holder = new ViewHolder();

            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout_header_of_item);
            holder.photo = (ImageView) convertView.findViewById(R.id.lvis_photo);
            holder.name = (TextView) convertView.findViewById(R.id.lvis_name);
            holder.view_count = (TextView) convertView.findViewById(R.id.lvis_view_count);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position != 0)
            holder.layout.setVisibility(View.GONE);
        else
            holder.layout.setVisibility(View.VISIBLE);

        SpacesModel dm = mSpacesModelList.get(position);

        ImageUtil.displayImage(holder.photo, dm.getImageURL(), null);
        holder.name.setText(dm.getName());
        holder.view_count.setText(dm.getViewCount() + " Views");
        holder.photo.setTag(dm.getId());
        holder.name.setTag(dm.getId());
        holder.name.setOnClickListener(this);
        holder.photo.setOnClickListener(this);
        return convertView;
    }

    private static class ViewHolder {
        public LinearLayout layout;
        public LinearLayout friends;
        public ImageView photo;
        public TextView name;
        public TextView view_count;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Log.v(TAG, ""+v.getTag());
        Intent intent = new Intent(mContext, ParallaxKenBurnsActivity.class);
        String id = v.getTag().toString();
        intent.putExtra(EXTRA_MESSAGE, id);
        mContext.startActivity(intent);

    }
}