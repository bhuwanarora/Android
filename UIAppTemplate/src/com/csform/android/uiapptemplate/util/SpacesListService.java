package com.csform.android.uiapptemplate.util;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by bhuwan on 05/11/15.
 */
public class SpacesListService {
    public static final String TAG = SpacesListService.class.getSimpleName();

    public static JsonObjectRequest setSpaces(int skip){
        String url = "http://api.androidhive.info/volley/person_object.json";
        JsonObjectRequest response = AsyncResponse.sendJsonRequest(url);
        return response;
    }
}