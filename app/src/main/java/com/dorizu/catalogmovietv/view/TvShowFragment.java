package com.dorizu.catalogmovietv.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.adapter.TvAdapter;
import com.dorizu.catalogmovietv.item.TvShowItem;
import com.dorizu.catalogmovietv.model.TvShowModel;

import java.util.ArrayList;

public class TvShowFragment extends Fragment {
    private ProgressBar progressBar;
    TvAdapter adapter;
    RecyclerView recyclerView;
    TvShowModel tvModel;

    public TvShowFragment() {
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
        recyclerView = view.findViewById(R.id.rv_tv);
        progressBar = view.findViewById(R.id.tv_progressBar);

        tvModel = ViewModelProviders.of(this).get(TvShowModel.class);
        tvModel.getTvShow().observe(this, getTvShow);

        adapter = new TvAdapter(getActivity());
        adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        recyclerView.setAdapter(adapter);

        tvModel.listTv();
        showLoading(false);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view_fragment_movie = inflater.inflate(R.layout.fragment_tv, container, false);

        return view_fragment_movie;
    }
    private Observer<ArrayList<TvShowItem>> getTvShow = new Observer<ArrayList<TvShowItem>>() {
        @Override
        public void onChanged(@Nullable ArrayList<TvShowItem> tv) {
            if (tv != null){
                adapter.setTvshow(tv);
                showLoading(true);
            }

        }
    };
}
