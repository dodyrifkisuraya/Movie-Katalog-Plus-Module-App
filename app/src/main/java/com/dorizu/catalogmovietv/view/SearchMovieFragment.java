package com.dorizu.catalogmovietv.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.adapter.MovieAdapter;
import com.dorizu.catalogmovietv.item.MovieItem;
import com.dorizu.catalogmovietv.model.MovieModel;

import java.util.ArrayList;

public class SearchMovieFragment extends Fragment {
    ProgressBar progressBar;
    MovieAdapter adapter;
    RecyclerView recyclerView;
    MovieModel movieModel;
    ArrayList<MovieItem> movieItems = new ArrayList<>();
    SearchView searchView;

    private final String STATE_QUERY = "state_query";
    private String query;


    public SearchMovieFragment() {
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

        movieModel = ViewModelProviders.of(this).get(MovieModel.class);
        movieModel.getMovieSearch().observe(this, getMovies);

        recyclerView = view.findViewById(R.id.rv_search_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));

        adapter = new MovieAdapter(getActivity());
        adapter.setMovie(movieItems);

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
                movieModel.cariMovie(key);
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
    private Observer<ArrayList<MovieItem>> getMovies = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieItem> movies) {
            if (movies != null){
                adapter.setMovie(movies);
            }

        }
    };
}
