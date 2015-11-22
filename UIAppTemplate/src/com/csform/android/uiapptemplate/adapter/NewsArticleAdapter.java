package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csform.android.uiapptemplate.R;
import com.nhaarman.listviewanimations.util.Swappable;

/**
 * Created by bhuwan on 21/11/15.
 */
public class NewsArticleAdapter extends BaseAdapter implements Swappable,
        View.OnClickListener {
    private LayoutInflater mInflater;
    public String[] article;
    private Context mContext;
    private String mContent;
    public final static String TAG = "NewsArticleAdapter";


    public NewsArticleAdapter(Context context, String content) {
        this.mContext = context;
        this.mContent = content;
        this.article = new String[]{content};
    }

    @Override
    public int getCount() {
        return article.length;
    }

    @Override
    public Object getItem(int position) {
        return article[0];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.news_article_content, parent, false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.news_article);
            holder.textView.setText(Html.fromHtml(article[0]));
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private static class ViewHolder{
        public TextView textView;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void swapItems(int i, int i1) {

    }
}
