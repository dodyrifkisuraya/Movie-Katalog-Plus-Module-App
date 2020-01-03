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
import com.dorizu.catalogmovietv.adapter.TvAdapter;
import com.dorizu.catalogmovietv.helper.FavoritHelper;
import com.dorizu.catalogmovietv.item.TvShowItem;

import java.util.ArrayList;

public class FavoriteTvShowFragment extends Fragment {
    private static final String EXTRA_TVSHOW = "extra_tvshow";
    private ArrayList<TvShowItem> listTv = new ArrayList<>();
    private RecyclerView rv;
    private ProgressBar progressBar;
    private TvAdapter tvAdapter;
    private FavoritHelper favoritHelper;
    private Bundle state;

    public FavoriteTvShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tv_favorite, container, false);
    }

    @Override
    public void onStart() {
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));
        rv.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);
        favoritHelper = FavoritHelper.getInstance(getContext());
        favoritHelper.open();

        tvAdapter = new TvAdapter(getContext());
        rv.setAdapter(tvAdapter);

        if (state == null) {
            listTv.clear();
            listTv.addAll(favoritHelper.getFavoritTv());
            if (listTv != null) {
                tvAdapter.setTvshow(listTv);
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.empty_data), Toast.LENGTH_SHORT).show();
            }
        } else {
            ArrayList<TvShowItem> listobj = state.getParcelableArrayList(EXTRA_TVSHOW);
            if (listobj != null) {
                tvAdapter.setTvshow(listobj);
            }
        }
        super.onStart();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rv = view.findViewById(R.id.rv_tv);
        progressBar = view.findViewById(R.id.tv_progressBar);
        if (savedInstanceState != null) {
            state = savedInstanceState;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_TVSHOW, tvAdapter.getTvSHow());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoritHelper.close();
    }
}
