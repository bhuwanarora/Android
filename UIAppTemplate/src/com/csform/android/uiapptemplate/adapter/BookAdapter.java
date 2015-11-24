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
import com.csform.android.uiapptemplate.model.BookModel;
import com.csform.android.uiapptemplate.util.ImageUtil;
import com.nhaarman.listviewanimations.util.Swappable;

import java.util.ArrayList;
import java.util.Collections;

public class BookAdapter extends BaseAdapter implements Swappable,
        OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<BookModel> mBooksModelList;
    private static final String TAG = "BookAdapter";
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public BookAdapter(Context context, ArrayList<BookModel> booksModelList) {
        Log.v(TAG, "Constructor mBooksModelList " + booksModelList);
        mContext = context;
        mInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mBooksModelList = booksModelList;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public int getCount() {
        return mBooksModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return mBooksModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mBooksModelList.get(position).getId();
    }

    @Override
    public void swapItems(int positionOne, int positionTwo) {
        Collections.swap(mBooksModelList, positionOne, positionTwo);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Log.v(TAG, " getView " + position + " convertView " + convertView);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item_book, parent, false);
            holder = new ViewHolder();
            holder.layout = (LinearLayout) convertView.findViewById(R.id.lvis_book_item);
            holder.photo = (ImageView) convertView.findViewById(R.id.lvis_photo);
            holder.title = (TextView) convertView.findViewById(R.id.lvis_title);
            holder.publishedYear = (TextView) convertView.findViewById(R.id.lvis_published_date);
            holder.authorName = (TextView) convertView.findViewById(R.id.lvis_author_name);
            holder.pageCount = (TextView) convertView.findViewById(R.id.lvis_pages);
            holder.rating = (TextView) convertView.findViewById(R.id.lvis_rating);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BookModel dm = mBooksModelList.get(position);
        Log.v(TAG, " getView BookModel " + dm);
        ImageUtil.displayImage(holder.photo, dm.getImageURL(), null);
        holder.title.setText(dm.getTitle());
        holder.publishedYear.setText(dm.getPublishedYear());
        holder.authorName.setText(dm.getAuthorName());
        holder.pageCount.setText(dm.getPageCount() + " Pages");

        String rating = dm.getRating();
        if(rating.isEmpty() || (rating.compareTo("null") == 0)){
            TextView ratingText = (TextView) convertView.findViewById(R.id.lvis_goodness_rating_text);
            holder.rating.setVisibility(View.GONE);
            ratingText.setVisibility(View.GONE);
        }
        else{
            holder.rating.setText(rating);
        }
        return convertView;
    }

    private static class ViewHolder {
        public LinearLayout layout;
        public ImageView photo;
        public TextView title;
        public TextView publishedYear;
        public TextView authorName;
        public TextView pageCount;
        public TextView rating;
    }

    @Override
    public void onClick(View v){
        Log.v(TAG, " onClick "+v.getTag());
        String url = (String) v.getTag();

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.putExtra("force_fullscreen",true);
        mContext.startActivity(intent);
    }
}
