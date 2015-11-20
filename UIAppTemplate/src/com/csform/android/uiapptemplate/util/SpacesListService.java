package com.csform.android.uiapptemplate.util;

import com.android.volley.toolbox.JsonObjectRequest;

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