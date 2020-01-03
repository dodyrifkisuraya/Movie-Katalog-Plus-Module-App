package com.dorizu.catalogmovietv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.item.MovieItem;
import com.dorizu.catalogmovietv.view.DetailMovieActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private Activity activity;
    private ArrayList<MovieItem> list;
    private List<MovieItem> films = new ArrayList<>();
    private Context context;

    public MovieAdapter(Activity activity){this.activity = activity;}

    public MovieAdapter(Context context){this.context = context;}

    public ArrayList<MovieItem> getFilms(){return list;}

    public void setMovie(List<MovieItem> data){
        this.films.clear();
        this.films.addAll(data);
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
        final MovieItem movieItem = films.get(i);
        Glide.with(movieViewHolder.itemView.getContext())
                .load(films.get(i).getThunail())
                .into(movieViewHolder.thumnail);

        movieViewHolder.title.setText(movieItem.getJudul());
        movieViewHolder.rating.setText(movieItem.getRate());
        movieViewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bawaKeDetail = new Intent(movieViewHolder.itemCard.getContext(), DetailMovieActivity.class);

                MovieItem movieObj = new MovieItem();
                movieObj.setId(films.get(i).getId());
                Log.d("id_movie_favorite",String.valueOf(films.get(i).getId()));
                movieObj.setJudul(films.get(i).getJudul());
                movieObj.setRate(films.get(i).getJudul());
                movieObj.setRateCount(films.get(i).getRateCount());
                movieObj.setTanggalRilis(films.get(i).getTanggalRilis());
                movieObj.setOverview(films.get(i).getOverview());
                movieObj.setBahasa(films.get(i).getBahasa());
                movieObj.setThunail(films.get(i).getThunail());
                movieObj.setBanner(films.get(i).getBanner());

                bawaKeDetail.putExtra(DetailMovieActivity.EXTRA_MOVIE, movieItem);
                movieViewHolder.itemView.getContext().startActivity(bawaKeDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return films.size() ;
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
}
