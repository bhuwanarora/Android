package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.csform.android.uiapptemplate.ParallaxKenBurnsActivity;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.SpacesModel;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.csform.android.uiapptemplate.view.MaterialRippleLayout;
import com.nhaarman.listviewanimations.util.Swappable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class SpacesListAdapter extends BaseAdapter implements Swappable,
        OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<SpacesModel> mSpacesModelList;
    private static final String TAG = "SpacesListAdapter";
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public SpacesListAdapter(Context context,
                         ArrayList<SpacesModel> SpacesModelList,
                         boolean shouldShowDragAndDropIcon) {
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Log.v(TAG, "Constructor" + SpacesModelList);
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
        Log.v(TAG, "getView "+position+" convertView " + convertView);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_spaces, parent, false);
            holder = new ViewHolder();

            holder.layout = (LinearLayout) convertView.findViewById(R.id.layout_header_of_item);
            holder.photo = (ImageView) convertView.findViewById(R.id.lvis_photo);
            holder.name = (TextView) convertView.findViewById(R.id.lvis_name);
            holder.view_count = (TextView) convertView.findViewById(R.id.lvis_view_count);
            holder.item = (LinearLayout) convertView.findViewById(R.id.lvis_spaces);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position != 0)
            holder.layout.setVisibility(View.GONE);
        else
            holder.layout.setVisibility(View.VISIBLE);

        SpacesModel dm = mSpacesModelList.get(position);
        Log.v(TAG, "getView " + dm.getId() + " dm " + dm);

        ImageUtil.displayRoundImage(holder.photo, dm.getImageURL(), null);
        holder.name.setText(dm.getName());
        holder.view_count.setText(dm.getViewCount() + " Views");
        try {
            JSONObject params = new JSONObject();
            String id = Long.toString(dm.getId());
            String name = dm.getName();
            String image_url = dm.getImageURL();
            params.put("id", id);
            params.put("name", name);
            params.put("image_url", image_url);
            Log.v(TAG, " getView " + params.toString());
            holder.item.setTag(params.toString());
            holder.item.setOnClickListener(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private static class ViewHolder {
        public LinearLayout layout;
        public LinearLayout item;
        public ImageView photo;
        public TextView name;
        public TextView view_count;
    }

    @Override
    public void onClick(View v){
        // TODO Auto-generated method stub
        Intent intent = new Intent(mContext, ParallaxKenBurnsActivity.class);
        Log.v(TAG, " onClick " + v.getTag());
        String params = (String) v.getTag();

        intent.putExtra(EXTRA_MESSAGE, params);
        mContext.startActivity(intent);
    }
}