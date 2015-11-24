package com.csform.android.uiapptemplate.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.adapter.SpacesNewsRecyclerAdapter;
import com.csform.android.uiapptemplate.adapter.VideoAdapter;
import com.csform.android.uiapptemplate.adapter.YearAdapter;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.csform.android.uiapptemplate.model.VideoModel;
import com.csform.android.uiapptemplate.util.AsyncContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpacesVideosFragment extends Fragment {
    public static final String TAG = SpacesVideosFragment.class.toString();
    private static String spacesId;

    private ListView listView;
    private static ProgressBar progressBar;
    private VideoAdapter videoAdapter;
    private Boolean isVisible = false;
    private Boolean isStarted = false;

    private static View view;

    public SpacesVideosFragment() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        Log.v(TAG, "setUserVisibleHint");
        if (isStarted && isVisible) {
            setUpVideosList();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        Log.v(TAG, "onStart");
        if (isVisible && isStarted){
            setUpVideosList();
        }
    }

    public static SpacesVideosFragment newInstance(){
        return new SpacesVideosFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);

        container.removeView(getView());

        Intent intent = getActivity().getIntent();
        try {
            JSONObject params = new JSONObject(intent.getStringExtra(SpacesNewsRecyclerAdapter.EXTRA_MESSAGE));
            String image_url = (String) params.get("image_url");
            String name = (String) params.get("name");
            spacesId = (String) params.get("id");

            setViewLayout(inflater, R.layout.fragment_spaces_videos, container);
            listView = (ListView) view.findViewById(R.id.list_view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void setViewLayout(LayoutInflater inflater, int id, ViewGroup container){
        Log.v(TAG, " setViewLayout ");
        view = inflater.inflate(id, container, false);
    }

    private void setUpVideosList(){
        videoAdapter = new VideoAdapter(getActivity(), getVideosModelList(spacesId));
        listView.setAdapter(videoAdapter);
    }

    public ArrayList<VideoModel> getVideosModelList(String spacesId){
        String tag_json_obj = "json_obj_req";
        Log.d(TAG, "getVideosModelList for spaces_id " + spacesId);
        String url = "https://oditty.me/api/v0/get_community_videos?id="+spacesId;
        final ArrayList<VideoModel> list = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int len = response.length();
                        for (int i=0;i<len;i++){
                            try {
                                String title = response.getJSONObject(i).getString("title");
                                String url = response.getJSONObject(i).getString("url");
                                String publisher = response.getJSONObject(i).getString("publisher");
                                String imageURL = response.getJSONObject(i).getString("thumbnail");
                                int duration = response.getJSONObject(i).getInt("duration");
                                String publishedDate = response.getJSONObject(i).getString("published_date");
                                VideoModel videoModel = new VideoModel(0L, imageURL, title, publisher, publishedDate, duration, R.string.fontello_heart_empty, url);
                                list.add(videoModel);
                                videoAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                Toast.makeText(getActivity().getBaseContext(), "Error with Loading..", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                        }
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        AsyncContent.getInstance().addToRequestQueue(jsonArrayRequest, tag_json_obj);
        return list;
    }

}