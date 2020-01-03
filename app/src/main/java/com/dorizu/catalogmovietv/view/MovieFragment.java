package com.dorizu.catalogmovietv.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dorizu.catalogmovietv.adapter.MovieAdapter;
import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.item.MovieItem;
import com.dorizu.catalogmovietv.model.MovieModel;

import java.util.ArrayList;
import androidx.lifecycle.Observer;

public class MovieFragment extends Fragment {
    private ProgressBar progressBar;
    MovieAdapter adapter;
    RecyclerView recyclerView;
    MovieModel movieModel;

    public MovieFragment() {
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
        recyclerView = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.movie_progressBar);

        movieModel = ViewModelProviders.of(this).get(MovieModel.class);
        movieModel.getMovie().observe(this, getMovies);

        adapter = new MovieAdapter(getActivity());
        adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(adapter);

        movieModel.listMovie();
        showLoading(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_fragment_movie = inflater.inflate(R.layout.fragment_movie, container, false);

        return view_fragment_movie;
    }
    private Observer<ArrayList<MovieItem>> getMovies = new Observer<ArrayList<MovieItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<MovieItem> movies) {
            if (movies != null){
                adapter.setMovie(movies);
                showLoading(true);
            }

        }
    };
}
