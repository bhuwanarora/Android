package com.csform.android.uiapptemplate.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.csform.android.uiapptemplate.R;
import com.csform.android.uiapptemplate.adapter.SpacesListRecyclerAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesNewsRecyclerAdapter;
import com.csform.android.uiapptemplate.adapter.YearAdapter;
import com.csform.android.uiapptemplate.model.NewsModel;
import com.csform.android.uiapptemplate.util.AsyncContent;
import com.csform.android.uiapptemplate.view.MaterialRippleLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpacesNewsFragment extends Fragment implements OnItemClickListener {
    public static final String TAG = SpacesNewsFragment.class.toString();
    private static String spacesId;

    private YearAdapter yearAdapter;
    private static LinearLayout yearGallery = null;

    private RecyclerView recyclerView;
    private TypedValue mTypedValue = new TypedValue();
    private ViewPager mViewPager;
    private static int pastVisiblesItems, visibleItemCount, totalItemCount;
    private static LinearLayoutManager linearLayoutManager;
    private static boolean loading = false;
    private static ArrayList<NewsModel> newsModelList = new ArrayList<NewsModel>();
    private static SpacesNewsRecyclerAdapter spacesNewsRecyclerAdapter;
    private static ProgressBar progressBar;
    private static View view;
    private String name;
    private Boolean isStarted = false;
    private Boolean isVisible = false;

    public static SpacesNewsFragment newInstance() {
        return new SpacesNewsFragment();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        newsModelList = new ArrayList<NewsModel>();
        if(spacesNewsRecyclerAdapter != null){
            spacesNewsRecyclerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.v(TAG, "setUserVisibleHint setUserVisibleHint "+isVisibleToUser + " isStarted "+isStarted);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            setNewsModelRecycleAdapter();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        Log.v(TAG, "setUserVisibleHint onStart " + isStarted);
        if (isVisible && isStarted){
            setNewsModelRecycleAdapter();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(TAG, "onCreateView");
        super.onCreateView(inflater, container, savedInstanceState);
        container.removeView(getView());

        Intent intent = getActivity().getIntent();
        try {
            JSONObject params = new JSONObject(intent.getStringExtra(SpacesListRecyclerAdapter.EXTRA_MESSAGE));
            String image_url = (String) params.get("image_url");
            name = (String) params.get("name");
            spacesId = (String) params.get("id");
            setViewLayout(inflater, R.layout.fragment_spaces_news, container);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            handleYearSlider();
            handleRecyclerView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void handleRecyclerView(){
        recyclerView = (RecyclerView)view.findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        bindScrollListeners();
    }

    private void bindScrollListeners(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if (!loading) {
                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            loading = true;
                            setNewsModelRecycleAdapter();
                        }
                    }
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView view, int newState) {

            }
        });
    }

    private void setViewLayout(LayoutInflater inflater, int id, ViewGroup container){
        Log.v(TAG, "setViewLayout");
        view = inflater.inflate(id, container, false);
    }

    private void handleYearSlider(){
        yearGallery = (LinearLayout) view.findViewById(R.id.horizontallistview);
        ArrayList<String> yearsArray = new YearAdapter(getActivity()).getItemsArray();
        for(String year: yearsArray){
            int wrap_content = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(wrap_content, wrap_content);
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(layoutParams);
            textView.setText(year);
            textView.setTag(year);
            textView.setPadding(50, 10, 50, 30);
            textView.setTextSize(14);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newsModelList = new ArrayList<NewsModel>();
                    spacesNewsRecyclerAdapter.notifyDataSetChanged();

                    Integer year = Integer.parseInt((String) v.getTag());
                    setNewsModelRecycleAdapter(year);
                }
            });

            MaterialRippleLayout layout = new MaterialRippleLayout(getActivity());
            layout.setLayoutParams(layoutParams);
            layout.addView(textView);

            yearGallery.addView(layout);
        }
    }

    private void setNewsModelRecycleAdapter(){
        newsModelList = getNewsModelList(String.valueOf(spacesId), YearAdapter.endYear);
        spacesNewsRecyclerAdapter = new SpacesNewsRecyclerAdapter(getActivity(), newsModelList, name);
        recyclerView.setAdapter(spacesNewsRecyclerAdapter);
    }

    private void setNewsModelRecycleAdapter(int year){
        newsModelList = getNewsModelList(String.valueOf(spacesId), year);
        spacesNewsRecyclerAdapter = new SpacesNewsRecyclerAdapter(getActivity(), newsModelList, name);
        recyclerView.setAdapter(spacesNewsRecyclerAdapter);
    }

    public ArrayList<NewsModel> getNewsModelList(String spaces_id, int year){

        int skip = newsModelList.size();

        String tag_json_obj = "json_obj_req";
        Log.d(TAG, "getNewsModelList for spaces_id " + spaces_id);
        String url = "https://oditty.me/api/v0/get_community_news?id="+spaces_id+"&skip="+skip+"&time="+year+"/9";
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            if(response.getJSONObject(0).toString().equals("{}") || (response.length() == 0)){
                                NewsModel newsModel = NewsModel.getEmptyNewsModel();
                                newsModelList.add(newsModel);
                                spacesNewsRecyclerAdapter.notifyDataSetChanged();
                            }
                            else{
                                response = response.getJSONObject(0).getJSONArray("news");
                                int len = response.length();
                                for (int i=0;i<len;i++){
                                    try {
                                        int id = response.getJSONObject(i).getInt("news_id");
                                        String imageURL = response.getJSONObject(i).getString("image_url");
                                        String title = response.getJSONObject(i).getString("title");
                                        String description = response.getJSONObject(i).getString("description");
                                        String createdOn = response.getJSONObject(i).getString("created_at");
                                        int viewCount = response.getJSONObject(i).getInt("view_count");

                                        String newsURL = response.getJSONObject(i).getString("news_url");
                                        int bookmarkCount = response.getJSONObject(i).getInt("bookmark_count");

                                        NewsModel newsModel = new NewsModel(id, imageURL, title, description, createdOn, viewCount, R.string.fontello_heart_empty, newsURL);

                                        newsModelList.add(newsModel);
                                        Log.v(TAG, "getNewsModelList " + newsModelList.size());
                                        spacesNewsRecyclerAdapter.notifyDataSetChanged();
                                    } catch (JSONException e) {
                                        Toast.makeText(getActivity().getBaseContext(), "Error with Loading..", Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                }
                                loading = false;
                                Log.d(TAG, response.toString());
                            }
                            progressBar.setVisibility(View.INVISIBLE);
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
        return newsModelList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}