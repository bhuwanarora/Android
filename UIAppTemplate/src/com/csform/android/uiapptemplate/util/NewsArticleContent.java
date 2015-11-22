package com.csform.android.uiapptemplate.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bhuwan on 20/11/15.
 */
public class NewsArticleContent {
    private final static String baseUrl = "https://api.embed.ly/1/extract?key=0038e86d5e754f8d9a0c3823e338563d&url=";
    private final static String returnType = "format=json";
    private static final String TAG = "NewsArticleContent";
    public String mArticle;
    public String mUrl;

    public NewsArticleContent(String url){
        this.mUrl = url;
    }

    public String getArticle(String url){
        String fetchUrl = baseUrl + url + "&" + returnType;
        String tag_json_obj = "json_obj_req";
        final String[] article = {""};
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                fetchUrl, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v(TAG, "News Article " + response.toString());
                            article[0] = (String) response.get("content");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
            }
        });

        // Adding request to request queue
        AsyncContent.getInstance().addToRequestQueue(jsonObjectRequest, tag_json_obj);
        return article[0];
    }

}
