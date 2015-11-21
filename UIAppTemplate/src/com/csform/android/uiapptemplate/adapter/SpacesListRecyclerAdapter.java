package com.csform.android.uiapptemplate.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csform.android.uiapptemplate.R;

import com.csform.android.uiapptemplate.model.NewsModel;

import java.util.ArrayList;

/**
 * Created by bhuwan on 20/11/15.
 */
public class SpacesListRecyclerAdapter extends RecyclerView.Adapter<SpacesListRecyclerAdapter.NewsViewHolder> {


    ArrayList<NewsModel> newsModels;

    public SpacesListRecyclerAdapter(ArrayList<NewsModel> newsModels){
        this.newsModels = newsModels;
    }

    @Override
    public SpacesListRecyclerAdapter.NewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_parallax_social, viewGroup, false);
        NewsViewHolder newsViewHolder = new NewsViewHolder(view);
        return newsViewHolder;
    }

    @Override
    public void onBindViewHolder(SpacesListRecyclerAdapter.NewsViewHolder newsViewHolder, int position) {
        NewsModel newsModel = newsModels.get(position);
        newsViewHolder.title.setText(newsModel.getTitle());
        newsViewHolder.createdOn.setText(newsModel.getCreatedOn());
        newsViewHolder.description.setText(newsModel.getDescription());
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public static class NewsViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView createdOn;
        public TextView description;

        NewsViewHolder(View itemView) {
            super(itemView);
        }
    }
}
