package com.dorizu.catalogmovietv.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.adapter.MovieAdapter;
import com.dorizu.catalogmovietv.helper.FavoritHelper;
import com.dorizu.catalogmovietv.item.MovieItem;

import java.util.ArrayList;

public class FavoriteMovieFragment extends Fragment {
    public static final String EXTRA_MOVIE = "extra_movie";
    public ArrayList<MovieItem> listMovie = new ArrayList<>();
    public RecyclerView rvMovie;
    public ProgressBar progressBar;
    private MovieAdapter movieAdapter;
    private FavoritHelper favoritHelper;
    private Bundle bundle;

    public FavoriteMovieFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_movie_favorite, container,false);
    }

    @Override
    public void onStart() {
        rvMovie.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvMovie.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);
        favoritHelper = FavoritHelper.getInstance(getContext());
        favoritHelper.open();

        movieAdapter = new MovieAdapter(getContext());
        rvMovie.setAdapter(movieAdapter);

        if (bundle == null) {
            listMovie.clear();
            listMovie.addAll(favoritHelper.getFavoritMovie());
            if (listMovie != null) {
                movieAdapter.setMovie(listMovie);
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
            }
        } else {
            ArrayList<MovieItem> list = bundle.getParcelableArrayList(EXTRA_MOVIE);
            if (list != null) {
                movieAdapter.setMovie(list);
            }
        }
        super.onStart();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.rv_movie);
        progressBar = view.findViewById(R.id.movie_progressBar);
        if (savedInstanceState != null) {
            bundle = savedInstanceState;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_MOVIE, movieAdapter.getFilms());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritHelper.close();
    }
}
