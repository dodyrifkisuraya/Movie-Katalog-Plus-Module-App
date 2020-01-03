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
import com.dorizu.catalogmovietv.item.TvShowItem;
import com.dorizu.catalogmovietv.view.DetailMovieActivity;
import com.dorizu.catalogmovietv.view.DetailTvActivity;

import java.util.ArrayList;
import java.util.List;

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.TvViewHolder> {
    private Activity activity;
    private ArrayList<TvShowItem> list;
    private List<TvShowItem> tvshow = new ArrayList<>();
    private Context context;

    public TvAdapter(Activity activity){this.activity = activity;}

    public TvAdapter(Context context){this.context = context;}

    public ArrayList<TvShowItem> getTvSHow(){return list;}

    public void setTvshow(List<TvShowItem> data){
        this.tvshow.clear();
        this.tvshow.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        return new TvViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TvViewHolder tvViewHolder, final int i) {
        final TvShowItem tvItem = tvshow.get(i);
        Glide.with(tvViewHolder.itemView.getContext())
                .load(tvshow.get(i).getThunail())
                .into(tvViewHolder.thumnail);

        tvViewHolder.title.setText(tvItem.getJudul());
        tvViewHolder.rating.setText(tvItem.getRate());
        tvViewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bawaKeDetail = new Intent(tvViewHolder.itemCard.getContext(), DetailTvActivity.class);

                TvShowItem tvObj = new TvShowItem();
                tvObj.setId(tvshow.get(i).getId());
                Log.d("id_tv_favorite",String.valueOf(tvshow.get(i).getId()));
                tvObj.setJudul(tvshow.get(i).getJudul());
                tvObj.setRate(tvshow.get(i).getJudul());
                tvObj.setRateCount(tvshow.get(i).getRateCount());
                tvObj.setTanggalRilis(tvshow.get(i).getTanggalRilis());
                tvObj.setOverview(tvshow.get(i).getOverview());
                tvObj.setBahasa(tvshow.get(i).getBahasa());
                tvObj.setThunail(tvshow.get(i).getThunail());
                tvObj.setBanner(tvshow.get(i).getBanner());

                bawaKeDetail.putExtra(DetailTvActivity.EXTRA_TV, tvItem);
                tvViewHolder.itemView.getContext().startActivity(bawaKeDetail);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tvshow.size() ;
    }

    public class TvViewHolder extends RecyclerView.ViewHolder {
        ImageView thumnail;
        TextView title,rating;
        CardView itemCard;

        public TvViewHolder(@NonNull View itemView) {
            super(itemView);
            thumnail = itemView.findViewById(R.id.img_thumnail);
            title = itemView.findViewById(R.id.tv_judul);
            rating = itemView.findViewById(R.id.tv_rating);
            itemCard = itemView.findViewById(R.id.cardview);

        }
    }
}
