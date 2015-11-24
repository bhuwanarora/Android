package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.csform.android.uiapptemplate.NewsArticleActivity;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.csform.android.uiapptemplate.util.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bhuwan on 20/11/15.
 */
public class SpacesNewsRecyclerAdapter extends RecyclerView.Adapter<SpacesNewsRecyclerAdapter.NewsViewHolder> {


    private static ArrayList<NewsModel> newsModels;
    private Context mContext;
    private String mSpacesName;
    public static final String TAG = "SListRecyclerAdapter";
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public SpacesNewsRecyclerAdapter(Context context, ArrayList<NewsModel> newsModels, String spacesName){
        this.newsModels = newsModels;
        this.mContext = context;
        this.mSpacesName = spacesName;
    }

    @Override
    public SpacesNewsRecyclerAdapter.NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_spaces_news, viewGroup, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(SpacesNewsRecyclerAdapter.NewsViewHolder newsViewHolder, int position) {
        try {
            NewsModel newsModel = newsModels.get(position);
            newsViewHolder.title.setText(newsModel.getTitle());
            newsViewHolder.createdOn.setText((String) newsModel.getCreatedOn());
            newsViewHolder.description.setText(newsModel.getDescription());
            ImageUtil.displayImage(newsViewHolder.photo, newsModel.getImageURL(), null);
            JSONObject params = new JSONObject();
            params.put("url", newsModel.getUrl());
            params.put("spacesName", this.mSpacesName);

            newsViewHolder.title.setTag(params.toString());
            newsViewHolder.title.setOnClickListener(clickListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, NewsArticleActivity.class);
            Log.v(TAG, " onClick " + view.getTag());
            String params = (String) view.getTag();

            intent.putExtra(EXTRA_MESSAGE, params);
            mContext.startActivity(intent);
        }
    };

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public TextView title;
        public TextView createdOn;
        public TextView description;
        public ImageView photo;

        NewsViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            title = (TextView) itemView.findViewById(R.id.lvis_title);
            createdOn = (TextView) itemView.findViewById(R.id.lvis_created_on);
            description = (TextView) itemView.findViewById(R.id.lvis_description);
            photo = (ImageView) itemView.findViewById(R.id.lvis_photo);
        }
    }
}
