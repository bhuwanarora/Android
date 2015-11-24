package com.csform.android.uiapptemplate.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.csform.android.uiapptemplate.R;

import java.util.ArrayList;
import java.util.Calendar;

public class YearAdapter extends BaseAdapter {

    Context context;

    public static ArrayList<String> itemsArray = new ArrayList<String>();
    public static final int startYear = 1988;
    public static final int endYear = Calendar.getInstance().get(Calendar.YEAR);;

    public YearAdapter(Context c){
        context = c;
        for(int year=endYear; year >= startYear; year--){
            itemsArray.add(Integer.toString(year));
        }
    }

    public static ArrayList<String> getItemsArray(){
        return itemsArray;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return itemsArray.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return itemsArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View rowView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.year_slider, null);
        TextView listTextView = (TextView)rowView.findViewById(R.id.itemtext);
        listTextView.setText(itemsArray.get(position));

        return rowView;
    }


}