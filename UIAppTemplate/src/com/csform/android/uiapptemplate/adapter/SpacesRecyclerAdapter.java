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

import com.csform.android.uiapptemplate.model.SpacesModel;
import com.csform.android.uiapptemplate.util.CustomOnClickListener;
import com.csform.android.uiapptemplate.util.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bhuwan on 20/11/15.
 */
public class SpacesRecyclerAdapter extends RecyclerView.Adapter<SpacesRecyclerAdapter.SpacesViewHolder> {

    private Context mContext;

    private ArrayList<SpacesModel> spacesModels;
    public static final String TAG = "SpacesRecyclerAdapter";
    public static String EXTRA_MESSAGE = "com.csform.android.uiapptemplate.MESSAGE";

    public SpacesRecyclerAdapter(Context context, ArrayList<SpacesModel> spacesModels){
        this.mContext = context;
        this.spacesModels = spacesModels;
    }

    @Override
    public SpacesRecyclerAdapter.SpacesViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_spaces, viewGroup, false);
        SpacesViewHolder spacesViewHolder = new SpacesViewHolder(view);
        return spacesViewHolder;
    }

    @Override
    public void onBindViewHolder(SpacesRecyclerAdapter.SpacesViewHolder spacesViewHolder, int position) {
        try {
            SpacesModel spacesModel = spacesModels.get(position);
            spacesViewHolder.name.setText(spacesModel.getName());
            spacesViewHolder.viewCount.setText(String.valueOf(spacesModel.getViewCount()) + " Views");
            ImageUtil.displayRoundImage(spacesViewHolder.photo, spacesModel.getImageURL(), null);

            JSONObject params = new JSONObject();
            String id = Long.toString(spacesModel.getId());
            String name = spacesModel.getName();
            String image_url = spacesModel.getImageURL();
            params.put("id", id);
            params.put("name", name);
            params.put("image_url", image_url);
            spacesViewHolder.cardView.setTag(params.toString());
            spacesViewHolder.cardView.setOnClickListener(clickListener);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, ParallaxKenBurnsActivity.class);
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
        return spacesModels.size();
    }

    public static class SpacesViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView name;
        public TextView viewCount;
        public ImageView photo;


        SpacesViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.lvis_name);
            viewCount = (TextView) itemView.findViewById(R.id.lvis_view_count);
            photo = (ImageView) itemView.findViewById(R.id.lvis_photo);
        }

    }
}