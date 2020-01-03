package com.dorizu.catalogmovietv.view;

import android.content.Intent;
import android.provider.Settings;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;


import com.bumptech.glide.Glide;
import com.dorizu.catalogmovietv.R;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    CircleImageView profileCircleImageView;


    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }



        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        profileCircleImageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);

        Glide.with(MainActivity.this)
                .load(R.drawable.dody)
                .into(profileCircleImageView);

        /*
        Jika savedinstance masih null, maka redirect ke fragment home
        Berguna ketika aplikasi pertama dijalankan untuk mengisi halaman default
        dan berguna juga ketika config changes terjadi, karena fragment akan
        secara otomatis ditambahkan ke dalam activity,
        maka kita tidak perlu replace fragment kembali.
         */
        if (savedInstanceState == null) {
            Fragment currentFragment = new MovieFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.content_main, currentFragment)
                    .commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }


    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent setting =  new Intent(this, NotifActivity.class);
            startActivity(setting);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Bundle bundle = new Bundle();
        Fragment fragment = null;

        String title = "";

        if (id == R.id.nav_movie) {

            fragment = new MovieFragment();

        } else if (id == R.id.nav_tvshow) {

            fragment = new TvShowFragment();

        }else if(id == R.id.nav_fav_movie){
            fragment = new FavoriteMovieFragment();
        }else if (id == R.id.nav_fav_tvshow){
            fragment = new FavoriteTvShowFragment();
        }else if(id == R.id.nav_search_mv){
            fragment = new SearchMovieFragment();
        }else if(id == R.id.nav_search_tv){
            fragment = new SearchTvFragment();
        }

        /*
        Ganti halaman dengan memanggil fragment replace
         */

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(title);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
