package com.dorizu.catalogmovietv.model;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dorizu.catalogmovietv.BuildConfig;
import com.dorizu.catalogmovietv.item.TvShowItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvShowModel extends ViewModel {
    private MutableLiveData<ArrayList<TvShowItem>> tvList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TvShowItem>> tvListSearch = new MutableLiveData<>();


    public void cariTv(String nama){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShowItem> tvItemSearch = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=" + nama;
        Log.i("cariTvShow",url);
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String rTv = new String(responseBody);
                    JSONObject objTv = new JSONObject(rTv);
                    JSONArray arrTv = objTv.getJSONArray("results");
                    for(int i = 0; i < arrTv.length(); i++){
                        Log.i("length Movie", String.valueOf(arrTv.length()));
                        JSONObject mv = arrTv.getJSONObject(i);
                        TvShowItem ITv = new TvShowItem(mv);
                        //IMovie.setId(mv.getInt("id"));
                        Log.i("onSuccess", ITv.getJudul());
                        tvItemSearch.add(ITv);
                    }
                    tvListSearch.postValue(tvItemSearch);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("onFailure", error.getMessage());
            }
        });
    }

    public void listTv(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TvShowItem> tvItem = new ArrayList<>();
        client.get(BuildConfig.API_URL_TV, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String rTv = new String(responseBody);
                    JSONObject objTv = new JSONObject(rTv);
                    JSONArray arrTv = objTv.getJSONArray("results");
                    for(int i = 0; i < arrTv.length(); i++){
                        JSONObject tv = arrTv.getJSONObject(i);
                        TvShowItem iTv = new TvShowItem(tv);
                        tvItem.add(iTv);
                    }
                    tvList.postValue(tvItem);
                } catch (Exception e) {
                    Log.i("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvShowItem>> getTvShow(){
        return tvList;
    }

    public LiveData<ArrayList<TvShowItem>> getTvSearch() {
        return tvListSearch;
    }
}
