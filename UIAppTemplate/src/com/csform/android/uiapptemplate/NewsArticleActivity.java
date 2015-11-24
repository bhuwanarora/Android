package com.csform.android.uiapptemplate;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.csform.android.uiapptemplate.adapter.NewsArticleAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesNewsRecyclerAdapter;
import com.csform.android.uiapptemplate.util.AsyncContent;
import com.csform.android.uiapptemplate.util.ImageUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
                            Toast.makeText(getBaseContext(), "Error with Loading..", Toast.LENGTH_SHORT).show();
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

    public Html.ImageGetter getImageGetter(){
        Html.ImageGetter imgGetter=new Html.ImageGetter(){
            public Drawable getDrawable(String source){
                Drawable drawable = new Drawable() {
                    @Override
                    public void draw(Canvas canvas) {

                    }

                    @Override
                    public void setAlpha(int alpha) {

                    }

                    @Override
                    public void setColorFilter(ColorFilter colorFilter) {

                    }

                    @Override
                    public int getOpacity() {
                        return 0;
                    }
                };

                try {
                    URL url=new URL(source);
                    InputStream is=url.openStream();
                    drawable=Drawable.createFromStream(is,"");
                    drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
                }
                catch (IOException e) {
                    e.printStackTrace();
//                    return new BitmapDrawable();
                }
                catch (        NullPointerException e) {
//                    return new BitmapDrawable();
                }
//                Bitmap sb= ImageUtil.getScaledBitmap((RCApplication) getApplication(), ((BitmapDrawable) drawable).getBitmap());
//                drawable=new BitmapDrawable(sb);
//                drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
                return drawable;
            }
        }
        ;
        return imgGetter;
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