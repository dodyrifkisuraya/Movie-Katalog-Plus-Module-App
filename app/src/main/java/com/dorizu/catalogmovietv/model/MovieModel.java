package com.dorizu.catalogmovietv.model;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dorizu.catalogmovietv.BuildConfig;
import com.dorizu.catalogmovietv.item.MovieItem;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieModel extends ViewModel {
    private MutableLiveData<ArrayList<MovieItem>> mvList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MovieItem>> mvListSearch = new MutableLiveData<>();


    public void cariMovie(String nama){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItem> mvItemSearch = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/movie?api_key=" + BuildConfig.API_KEY + "&language=en-US&query=" + nama;
        Log.i("cariMovie",url);
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String rMovie = new String(responseBody);
                    JSONObject objMov = new JSONObject(rMovie);
                    JSONArray LMovie = objMov.getJSONArray("results");
                    for(int i = 0; i < LMovie.length(); i++){
                        Log.i("length Movie", String.valueOf(LMovie.length()));
                        JSONObject mv = LMovie.getJSONObject(i);
                        MovieItem IMovie = new MovieItem(mv);
                        //IMovie.setId(mv.getInt("id"));
                        Log.i("onSuccess", IMovie.getJudul());
                        mvItemSearch.add(IMovie);
                    }
                    mvListSearch.postValue(mvItemSearch);
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

    public void listMovie(){
        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieItem> mvItem = new ArrayList<>();
        client.get(BuildConfig.API_URL_MOVIE, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String rMovie = new String(responseBody);
                    JSONObject objMov = new JSONObject(rMovie);
                    JSONArray LMovie = objMov.getJSONArray("results");
                    for(int i = 0; i < LMovie.length(); i++){
                        JSONObject mv = LMovie.getJSONObject(i);
                        MovieItem IMovie = new MovieItem(mv);
                        mvItem.add(IMovie);
                    }
                    mvList.postValue(mvItem);
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

    public LiveData<ArrayList<MovieItem>> getMovie(){
        return mvList;
    }

    public LiveData<ArrayList<MovieItem>> getMovieSearch() {
        return mvListSearch;
    }
}
