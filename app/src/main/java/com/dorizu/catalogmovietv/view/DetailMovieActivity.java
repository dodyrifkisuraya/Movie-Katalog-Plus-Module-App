package com.dorizu.catalogmovietv.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.helper.FavoritHelper;
import com.dorizu.catalogmovietv.item.MovieItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMovieActivity extends AppCompatActivity{
    public static String EXTRA_MOVIE = "extra_movie";
    private MovieItem movieItem;
    private FavoritHelper favoritHelper;
    private Menu thisMenu = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_activity);

        TextView tvJudul,tvRating, tvRilis, tvOverview, tvOrilang;
        ImageView imgThumnail, imgBanner;

        getSupportActionBar().setTitle("");

        movieItem = getIntent().getParcelableExtra(EXTRA_MOVIE);

        String judul = movieItem.getJudul();
        String rating = movieItem.getRate();
        String rateCount = movieItem.getRateCount();
        String rilis = movieItem.getTanggalRilis();
        String overview = movieItem.getOverview();
        String lang = movieItem.getBahasa();
        String thumnail = movieItem.getThunail();
        String banner = movieItem.getBanner();

        tvJudul = findViewById(R.id.tv_judul);
        tvRating = findViewById(R.id.tv_rating);
        tvRilis = findViewById(R.id.tv_rilis);
        tvOverview = findViewById(R.id.tv_overview);
        tvOrilang = findViewById(R.id.tv_lang);
        imgThumnail = findViewById(R.id.img_thumnail);
        imgBanner = findViewById(R.id.img_banner);
        tvOrilang = findViewById(R.id.tv_lang);


        SimpleDateFormat date_format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = date_format.parse(rilis);

            SimpleDateFormat new_date_format = new SimpleDateFormat("EEEE, MMM dd, yyyy");
            String date_of_release = new_date_format.format(date);
            tvRilis.setText(date_of_release);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvJudul.setText(judul);
        tvRating.setText("( "+rating+"/10 ) "+rateCount+" Ratingers");
        tvOverview.setText(overview);
        tvOrilang.setText(lang);

        Glide.with(DetailMovieActivity.this)
                .load(thumnail)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgThumnail);
        Glide.with(DetailMovieActivity.this)
                .load(banner)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(imgBanner);

        favoritHelper = FavoritHelper.getInstance(getApplicationContext());
        favoritHelper.open();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        thisMenu = menu;
        if (favoritHelper.isExistMovie(movieItem)){
            getMenuInflater().inflate(R.menu.menu_add_unselect_fav, thisMenu);

        }else {
            getMenuInflater().inflate(R.menu.menu_add_select_fav, thisMenu);
        }

        return super.onCreateOptionsMenu(menu);
    }

    public void addFav(){
        long result = favoritHelper.insertFavMovie(movieItem);
        if (result > 0){

            thisMenu.getItem(0).setIcon(R.drawable.ic_favorite_black_24dp);
            Toast.makeText(DetailMovieActivity.this, getString(R.string.succes_add),Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(DetailMovieActivity.this, getString(R.string.fail_add),Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFav(){
        int result = favoritHelper.deleteFavMovie(movieItem.getId());
        if (result > 0){
            thisMenu.getItem(0).setIcon(R.drawable.ic_favorite_border_black_24dp);
            Toast.makeText(DetailMovieActivity.this, getString(R.string.succes_delete),Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(DetailMovieActivity.this, getString(R.string.fail_delete),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_favorite){ //jika border merah diklik
            if (!favoritHelper.isExistMovie(movieItem)){
                addFav();
            }else{
                removeFav();
            }
        }else {
            if (item.getItemId() == R.id.action_unsellect_favorite){ //jika merah diklik

                if(favoritHelper.isExistMovie(movieItem)){
                    removeFav();
                }else {
                    addFav();
                }
            }

        }

        return super.onOptionsItemSelected(item);
    }
}
