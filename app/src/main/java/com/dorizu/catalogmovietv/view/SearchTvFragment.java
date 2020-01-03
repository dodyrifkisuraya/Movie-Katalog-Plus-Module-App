package com.dorizu.catalogmovietv.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.adapter.TvAdapter;
import com.dorizu.catalogmovietv.item.MovieItem;
import com.dorizu.catalogmovietv.item.TvShowItem;
import com.dorizu.catalogmovietv.model.TvShowModel;

import java.util.ArrayList;

public class SearchTvFragment extends Fragment {
    ProgressBar progressBar;
    TvAdapter adapter;
    RecyclerView recyclerView;
    TvShowModel tvModel;
    ArrayList<TvShowItem> tvItems = new ArrayList<>();
    SearchView searchView;

    private final String STATE_QUERY = "state_query";
    private String query;


    public SearchTvFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void showLoading(Boolean state) {
        if (state) {
            progressBar.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvModel = ViewModelProviders.of(this).get(TvShowModel.class);
        tvModel.getTvSearch().observe(this, getMovies);

        recyclerView = view.findViewById(R.id.rv_search_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        adapter = new TvAdapter(getActivity());
        adapter.setTvshow(tvItems);

        recyclerView.setAdapter(adapter);

        if(savedInstanceState != null){
            query = savedInstanceState.getString(STATE_QUERY);
        }

        searchView = view.findViewById(R.id.search_mv);
        searchView.onActionViewExpanded();

        if (query != null){
            searchView.setQuery(query, false);
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String key) {
                tvModel.cariTv(key);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });


    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_fragment_movie = inflater.inflate(R.layout.fragment_search_movie, container, false);

        return view_fragment_movie;
    }
    private Observer<ArrayList<TvShowItem>> getMovies = new Observer<ArrayList<TvShowItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShowItem> tvList) {
            if (tvList != null){
                adapter.setTvshow(tvList);
            }

        }
    };
}
