package com.csform.android.uiapptemplate;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.csform.android.uiapptemplate.adapter.NewsArticleAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesNewsRecyclerAdapter;
import com.csform.android.uiapptemplate.util.AsyncContent;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Please NOTE: In manifest, set theme for this class to
 * \@style/OverlayActionBar
 * @author MLADJO
 *
 */
public class NewsArticleActivity extends AppCompatActivity {

    public static final String TAG = "NewsArticleActivity";
    private TextView textView;
    private TextView textViewTitle;
    private NewsArticleAdapter newsArticleAdapter;
    private final static String baseUrl = "https://api.embed.ly/1/extract?key=0038e86d5e754f8d9a0c3823e338563d&url=";
    private final static String returnType = "format=json";
    private static ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            Intent intent = getIntent();
            JSONObject params = new JSONObject(intent.getStringExtra(SpacesNewsRecyclerAdapter.EXTRA_MESSAGE));
            String url = (String) params.get("url");
            String spacesName = (String) params.get("spacesName");
            getSupportActionBar().setTitle(spacesName);
            Log.v(TAG, "onCreate " + url);
            setContentView(R.layout.news_article_activity);
            textView = (TextView) findViewById(R.id.news_article);
            textViewTitle = (TextView) findViewById(R.id.news_article_title);
            progressBar = (ProgressBar) findViewById(R.id.progressBar);

            getArticle(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getArticle(String url){
        String fetchUrl = baseUrl + url + "&" + returnType;
        String tag_json_obj = "json_obj_req";
        final String[] article = {""};
        progressBar.setVisibility(View.VISIBLE);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                fetchUrl, null,
                new Response.Listener<JSONObject>(){
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.v(TAG, "News Article " + response.toString());
                            article[0] = (String) response.get("content");
                            String title = (String) response.get("title");
                            textView.setText(Html.fromHtml(article[0]));
                            textViewTitle.setText(title);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}