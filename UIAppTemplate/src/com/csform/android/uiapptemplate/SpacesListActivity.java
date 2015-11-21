package com.csform.android.uiapptemplate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Space;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.csform.android.uiapptemplate.adapter.SearchAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesListRecyclerAdapter;
import com.csform.android.uiapptemplate.adapter.SpacesRecyclerAdapter;
import com.csform.android.uiapptemplate.model.DummyModel;
import com.csform.android.uiapptemplate.model.SearchItemAuthorModel;
import com.csform.android.uiapptemplate.model.SearchItemBookModel;
import com.csform.android.uiapptemplate.model.SearchItemModel;
import com.csform.android.uiapptemplate.model.SearchItemNewsModel;
import com.csform.android.uiapptemplate.model.SearchItemSpacesModel;
import com.csform.android.uiapptemplate.model.SpacesModel;
import com.csform.android.uiapptemplate.util.AppContent;
import com.csform.android.uiapptemplate.util.AsyncContent;
import com.csform.android.uiapptemplate.util.DummyContent;
import com.nhaarman.listviewanimations.appearance.AnimationAdapter;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.DynamicListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SpacesListActivity extends Activity {

    public static final String LIST_VIEW_OPTION = "com.csform.android.uiapptemplate.ListViewsActivity";
    public static final String TAG = "SpacesListActivity";

    private static DynamicListView mDynamicListView;
    private static BaseAdapter adapter;
    private static  SpacesRecyclerAdapter spacesRecyclerAdapter;
    private static SearchAdapter searchAdapter;
    private static int pastVisiblesItems, visibleItemCount, totalItemCount;
    private static LinearLayoutManager linearLayoutManager;
    private static boolean loading = false;
    private static RecyclerView recyclerView;
    private static EditText mSearchField;
    private static TextView mXMark;
    private static RelativeLayout footer;
    private static ArrayList<SpacesModel> spacesModelsList = new ArrayList<SpacesModel>();

//    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE); // Removing
        setContentView(R.layout.spaces_list_views);
//        mDynamicListView = (DynamicListView) findViewById(R.id.dynamic_listview);

        footer = (RelativeLayout) findViewById(R.id.footer);
        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchField.clearFocus();
        mSearchField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleEmptySearchText();
            }
        });
        bindSearchTextChangedListener();
        handleRecyclerView();
    }

    private void handleRecyclerView(){
		recyclerView = (RecyclerView)findViewById(R.id.rv);
		recyclerView.setHasFixedSize(true);

		linearLayoutManager = new LinearLayoutManager(SpacesListActivity.this);
		recyclerView.setLayoutManager(linearLayoutManager);
        setSpacesRecycleAdapter();
        bindScrollListeners();
    }

    private void setSpacesRecycleAdapter(){
        spacesRecyclerAdapter = new SpacesRecyclerAdapter(SpacesListActivity.this, getSpacesModelList());
        recyclerView.setAdapter(spacesRecyclerAdapter);
    }

    private void bindScrollListeners(){
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy){
                if(dy > 0){
                    visibleItemCount = linearLayoutManager.getChildCount();
                    totalItemCount = linearLayoutManager.getItemCount();
                    pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if(!loading){
                        if((visibleItemCount + pastVisiblesItems) >= totalItemCount){
                            loading = true;
                            setSpacesRecycleAdapter();
                        }
                    }
                }
            }
        });
    }

    private void setUpSearchItemListView(){
        AnimationAdapter animAdapter = new SwingBottomInAnimationAdapter(adapter);

        animAdapter.setAbsListView(mDynamicListView);
        mDynamicListView.setAdapter(searchAdapter);
    }

    private void bindSearchTextChangedListener(){
        mSearchField.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @SuppressLint("DefaultLocale")
            @Override
            public void afterTextChanged(Editable editable) {
                String searchText = editable.toString().trim();
                Log.v(TAG, "afterTextChanged "+searchText);
                if (searchText.isEmpty()) {
                    handleEmptySearchText();
                }
                else{
                    searchAdapter = new SearchAdapter(SpacesListActivity.this, getSearchResults(searchText));
                    setUpSearchItemListView();
                }
            }
        });
    }

    private void handleEmptySearchText(){
        String currentSearchText = mSearchField.getText().toString().trim();
        if(currentSearchText.isEmpty()){
            searchAdapter = new SearchAdapter(SpacesListActivity.this, getTopSearchResults());
            setUpSearchItemListView();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<SearchItemModel> getSearchResults(String searchText) {
        Log.v(TAG, "getSearchResults " + searchText);
        final ArrayList<SearchItemModel> list = new ArrayList<>();
        String url = "https://searchservice.oditty.me/api/v0/search?q="+searchText+"&count=4";
        String tag_json_obj = "json_obj_req";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int len = response.length();
                        for (int i=0;i<len;i++){
                            try {
                                SearchItemModel searchItemModel = handleSearchItemType(response.getJSONObject(i));
                                list.add(searchItemModel);
                                searchAdapter.notifyDataSetChanged();
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

    private ArrayList<SearchItemModel> getTopSearchResults(){
        final ArrayList<SearchItemModel> list = new ArrayList<>();
        String url = "https://searchservice.oditty.me/api/v0/top_results";
        String tag_json_obj = "json_obj_req";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int len = response.length();
                        for (int i=0;i<len;i++){
                            try {
                                SearchItemModel searchItemModel = handleSearchItemType(response.getJSONObject(i));
                                list.add(searchItemModel);
                                searchAdapter.notifyDataSetChanged();
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

    private ArrayList<SpacesModel> getSpacesModelList() {
        int skip = spacesModelsList.size();
        footer.setVisibility(View.VISIBLE);
        String tag_json_obj = "json_obj_req";
        String url = "https://rooms.oditty.me/api/v0/get_rooms?skip="+skip;
        Log.v(TAG, "FETCHING... getSpacesModelList skip "+skip);
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
                                spacesModelsList.add(spacesModel);
//                                adapter.notifyDataSetChanged();
                                spacesRecyclerAdapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loading = false;
                        footer.setVisibility(View.GONE);
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

        return spacesModelsList;
    }

    private SearchItemModel handleSearchItemType(JSONObject response){
        String labels = null;
        SearchItemModel searchItemModel = null;
        try {
            labels = response.getString("labels");
            String name = null;
            String imageUrl = null;
            int id = response.getInt("id");
            switch (labels){
                case "Author":
                    name = response.getString("name");
                    imageUrl =  "http://rd-images.readersdoor.netdna-cdn.com/"+id+"/M.png";
                    searchItemModel = new SearchItemAuthorModel(name, imageUrl, id);
                    break;
                case "Book":
                    name = response.getString("title");
                    imageUrl =  "http://rd-images.readersdoor.netdna-cdn.com/"+id+"/M.png";
                    String isbn = response.getString("isbn");
                    String authorName = response.getString("author_name");
                    long authorId = response.getInt("author_id");
                    searchItemModel = new SearchItemBookModel(name, imageUrl, id, isbn, authorName, authorId);
                    break;
                case "Community":
                    name = response.getString("name");
                    imageUrl =  response.getString("image_url");
                    searchItemModel = new SearchItemSpacesModel(name, imageUrl, id);
                    break;
                case "News":
                    name = response.getString("title");
                    imageUrl =  response.getString("image_url");
                    searchItemModel = new SearchItemNewsModel(name, imageUrl, id);
                    break;
                case "User":
                    name = response.getString("name");
                    imageUrl =  response.getString("image_url");
                    searchItemModel = new SearchItemSpacesModel(name, imageUrl, id);
                    break;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return searchItemModel;
    }

}