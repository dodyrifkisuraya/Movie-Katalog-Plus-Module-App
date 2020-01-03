package com.dorizu.favoritemovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dorizu.favoritemovie.adapter.MovieAdapter;
import com.dorizu.favoritemovie.helper.CRUDHelper;

public class MainActivity extends AppCompatActivity {
    private MovieAdapter adapter;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private CRUDHelper helper;
    private TextView emptyText;
    private Cursor list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movie);
        progressBar = findViewById(R.id.movie_progressBar);
        emptyText = findViewById(R.id.tv_empty);

        helper = new CRUDHelper(this);
        adapter = new MovieAdapter(this);

        progressBar.setVisibility(View.INVISIBLE);
        emptyText.setVisibility(View.INVISIBLE);

        adapter.setMovie(list);

        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        new LoadNoteAsync().execute();
    }

    private class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return helper.getAllMovie();
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            progressBar.setVisibility(View.GONE);

            list = notes;
            adapter.setMovie(list);
            if (list.getCount() == 0){
                emptyText.setVisibility(View.VISIBLE);
            }
        }
    }
}
