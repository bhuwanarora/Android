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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.csform.android.uiapptemplate.ParallaxKenBurnsActivity;
import com.csform.android.uiapptemplate.R;

import com.csform.android.uiapptemplate.SpacesMainActivity;
import com.csform.android.uiapptemplate.model.SearchItemModel;
import com.csform.android.uiapptemplate.model.SpacesModel;
import com.csform.android.uiapptemplate.util.CustomOnClickListener;
import com.csform.android.uiapptemplate.util.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bhuwan on 20/11/15.
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> {

    private Context mContext;

    private ArrayList<SearchItemModel> searchItemModels;
    public static final String TAG = "SearchRecyclerAdapter";
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public SearchRecyclerAdapter(Context context, ArrayList<SearchItemModel> searchItemModels){
        this.mContext = context;
        this.searchItemModels = searchItemModels;
    }

    @Override
    public SearchRecyclerAdapter.SearchViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_view_item_search, viewGroup, false);
        SearchViewHolder searchViewHolder = new SearchViewHolder(view);
        return searchViewHolder;
    }

    @Override
    public void onBindViewHolder(SearchRecyclerAdapter.SearchViewHolder spacesViewHolder, int position) {
        try {
            SearchItemModel searchItemModel = searchItemModels.get(position);
            spacesViewHolder.name.setText(searchItemModel.getName());
            spacesViewHolder.type.setText(String.valueOf(searchItemModel.getType()));
            ImageUtil.displayRoundImage(spacesViewHolder.photo, searchItemModel.getImageUrl(), null);

            JSONObject params = new JSONObject();
            String id = Long.toString(searchItemModel.getId());
            String name = searchItemModel.getName();
            String image_url = searchItemModel.getImageUrl();
            String type = searchItemModel.getType();
            params.put("id", id);
            params.put("name", name);
            params.put("image_url", image_url);
            params.put("type", type);
            spacesViewHolder.cardView.setTag(params.toString());
            spacesViewHolder.cardView.setOnClickListener(clickListener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, SpacesMainActivity.class);
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
        return searchItemModels.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView name;
        public TextView type;
        public ImageView photo;


        SearchViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.lvis_name);
            type = (TextView) itemView.findViewById(R.id.lvis_type);
            photo = (ImageView) itemView.findViewById(R.id.lvis_photo);
        }
    }
}