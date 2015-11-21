package com.csform.android.uiapptemplate.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.csform.android.uiapptemplate.model.SpacesModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by bhuwan on 19/11/15.
 */
public class AppContent {

    public static final String TAG = "AppContent";

    public static ArrayList<NewsModel> getNewsModelList(String spaces_id){
        int skip = 0;

        String tag_json_obj = "json_obj_req";
        Log.d(TAG, "getNewsModelList for spaces_id " + spaces_id);
        String url = "https://oditty.me/api/v0/get_community_news?id="+spaces_id+"&skip=0&time=2015/9";
        final ArrayList<NewsModel> list = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            response = response.getJSONObject(0).getJSONArray("news");
                            int len = response.length();
                            for (int i=0;i<len;i++){
                                try {
//									String name = response.getJSONObject(i).getString("name");
                                    int id = response.getJSONObject(i).getInt("news_id");
                                    String imageURL = response.getJSONObject(i).getString("image_url");
                                    String title = response.getJSONObject(i).getString("title");
                                    String description = response.getJSONObject(i).getString("description");
                                    String createdOn = response.getJSONObject(i).getString("created_at");
                                    int viewCount = response.getJSONObject(i).getInt("view_count");

                                    String newsURL = response.getJSONObject(i).getString("news_url");
                                    int bookmarkCount = response.getJSONObject(i).getInt("bookmark_count");

                                    NewsModel newsModel = new NewsModel(id, imageURL, title, description, createdOn, viewCount, R.string.fontello_heart_empty, newsURL);

                                	list.add(newsModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            Log.d(TAG, response.toString());
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
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

    public static ArrayList<SpacesModel> getSpacesModelList() {
        int skip = 0;

        String tag_json_obj = "json_obj_req";
        String url = "https://rooms.oditty.me/api/v0/get_rooms?skip="+skip;
        final ArrayList<SpacesModel> list = new ArrayList<>();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int len = response.length();
                        for (int i=0;i<len;i++){
                            try {
                                String name = response.getJSONObject(i).getString("name");
                                int view_count = response.getJSONObject(i).getInt("view_count");
                                int id = response.getJSONObject(i).getInt("id");
                                String image_url =  "http://rd-images.readersdoor.netdna-cdn.com/"+id+"/M.png";
                                SpacesModel spacesModel = new SpacesModel(id, image_url, name, view_count, R.string.fontello_heart_empty);
                                list.add(spacesModel);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
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