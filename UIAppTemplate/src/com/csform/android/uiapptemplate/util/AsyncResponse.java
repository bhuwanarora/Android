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
public class AsyncResponse extends AsyncContent {
    public static final String TAG = AsyncResponse.class.getSimpleName();

    public static JsonObjectRequest sendJsonRequest(String url){
        // Tag used to cancel the request
        String tag_json_obj = "json_obj_req";


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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
        AsyncContent.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        return jsonObjReq;
    }

}
