package com.dorizu.favoritemovie.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dorizu.favoritemovie.DetailMovieActivity;
import com.dorizu.favoritemovie.MovieItem;
import com.dorizu.favoritemovie.R;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private Cursor data;

    public MovieAdapter(Context context){this.context = context;}

    public void setMovie(Cursor data){
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieViewHolder movieViewHolder, final int i) {
        final MovieItem movieItem = getItem(i);
        Glide.with(movieViewHolder.itemView.getContext())
                .load(movieItem.getThunail())
                .into(movieViewHolder.thumnail);

        movieViewHolder.title.setText(movieItem.getJudul());
        movieViewHolder.rating.setText(movieItem.getRate());
        movieViewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bawaKeDetail = new Intent(movieViewHolder.itemCard.getContext(), DetailMovieActivity.class);

                MovieItem movieObj = new MovieItem();
                movieObj.setId(movieItem.getId());
                Log.d("id_movie_favorite",String.valueOf(movieItem.getId()));
                movieObj.setJudul(movieItem.getJudul());
                movieObj.setRate(movieItem.getJudul());
                movieObj.setRateCount(movieItem.getRateCount());
                movieObj.setTanggalRilis(movieItem.getTanggalRilis());
                movieObj.setOverview(movieItem.getOverview());
                movieObj.setBahasa(movieItem.getBahasa());
                movieObj.setThunail(movieItem.getThunail());
                movieObj.setBanner(movieItem.getBanner());

                bawaKeDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItem);
                movieViewHolder.itemView.getContext().startActivity(bawaKeDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (data == null){return 0;}
        return data.getCount();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView thumnail;
        TextView title,rating;
        CardView itemCard;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            thumnail = itemView.findViewById(R.id.img_thumnail);
            title = itemView.findViewById(R.id.tv_judul);
            rating = itemView.findViewById(R.id.tv_rating);
            itemCard = itemView.findViewById(R.id.cardview);

        }
    }

    private MovieItem getItem(int posisi){
        if (!data.moveToPosition(posisi)){
            throw new IllegalStateException("Position invalid");
        }
        return new MovieItem(data);
    }
}
