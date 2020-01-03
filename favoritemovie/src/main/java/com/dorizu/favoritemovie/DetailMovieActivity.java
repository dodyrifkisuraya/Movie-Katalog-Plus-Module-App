package com.dorizu.favoritemovie;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
import com.dorizu.favoritemovie.helper.CRUDHelper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailMovieActivity extends AppCompatActivity {
    public static String EXTRA_MOVIE = "extra_movie";
    private MovieItem movieItem;
    private CRUDHelper crudHelper;
    private Menu thisMenu = null;
    private boolean isFavorite = false;
    String myId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_movie_activity);

        crudHelper = new CRUDHelper(this);

        TextView tvJudul, tvRating, tvRilis, tvOverview, tvOrilang;
        ImageView imgThumnail, imgBanner;

        getSupportActionBar().setTitle("");

        movieItem = getIntent().getParcelableExtra(EXTRA_MOVIE);
        int id = movieItem.getId();
        myId = String.valueOf(id);
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
        tvRating.setText("( " + rating + "/10 ) " + rateCount + " Ratingers");
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

//        checkFavorite(myId);


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        thisMenu = menu;
//        getMenuInflater().inflate(R.menu.menu_add_select_fav, menu);
//        setIconFavorite();
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void setIconFavorite() {
//        if (isFavorite) {
//            thisMenu.getItem(0).setIcon(R.drawable.ic_favorite_black_24dp);
//        } else {
//            thisMenu.getItem(0).setIcon(R.drawable.ic_favorite_border_black_24dp);
//        }
//    }
//
//    public void checkFavorite(String id) {
//        if (crudHelper.getMovieId(id).getCount() == 0) {
//            isFavorite = false;
//        } else {
//            isFavorite = true;
//        }
//    }
//
//    private void setRemoveFromFavorite(String mId) {
//        try {
//            crudHelper.deleteDataId(mId);
//            Toast.makeText(DetailMovieActivity.this, "Succes Deleted", Toast.LENGTH_SHORT).show();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_add_favorite) {
//            if (isFavorite) {
//                setRemoveFromFavorite(myId);
//            } else {
//                Toast.makeText(DetailMovieActivity.this, "Cannot Added", Toast.LENGTH_SHORT).show();
//            }
//            isFavorite = !isFavorite;
//            setIconFavorite();
//
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
