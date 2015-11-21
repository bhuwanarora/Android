package com.csform.android.uiapptemplate;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.csform.android.uiapptemplate.adapter.DefaultAdapter;
import com.csform.android.uiapptemplate.adapter.NewsArticleAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListAdapter;
import com.csform.android.uiapptemplate.util.AsyncContent;
import com.csform.android.uiapptemplate.util.DummyContent;
import com.csform.android.uiapptemplate.util.NewsArticleContent;
import com.csform.android.uiapptemplate.view.AlphaForegroundColorSpan;
import com.csform.android.uiapptemplate.view.kbv.KenBurnsView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Please NOTE: In manifest, set theme for this class to
 * \@style/OverlayActionBar
 * @author MLADJO
 *
 */
public class NewsArticleActivity extends ActionBarActivity {

    public static final String TAG = "NewsArticleActivity";
    private TextView textView;
    private TextView textViewTitle;
    private NewsArticleAdapter newsArticleAdapter;
    private final static String baseUrl = "https://api.embed.ly/1/extract?key=0038e86d5e754f8d9a0c3823e338563d&url=";
    private final static String returnType = "format=json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        String url = intent.getStringExtra(SpacesAdapter.EXTRA_MESSAGE);
        Log.v(TAG, "onCreate " + url);
        setContentView(R.layout.news_article_activity);
        textView = (TextView) findViewById(R.id.news_article);
        textViewTitle = (TextView) findViewById(R.id.news_article_title);
        getArticle(url);
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
                            String title = (String) response.get("title");
                            textView.setText(Html.fromHtml(article[0]));
                            textViewTitle.setText(title);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}