package com.dorizu.catalogmovietv.helper;

import android.database.Cursor;

import com.dorizu.catalogmovietv.item.MovieItem;

import static android.provider.BaseColumns._ID;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.TITLE;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.RATE;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.RATE_COUNT;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.DATE;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.DESCRIPTION;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.LANGUAGE;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.POSTER_PATH;
import static com.dorizu.catalogmovietv.db.DatabaseContract.NoteCulumns.BANNER;

import java.util.ArrayList;

public class MappingHelper {
    public static ArrayList<MovieItem> mapCursorToArrayList(Cursor cursor){
        ArrayList<MovieItem> list = new ArrayList<>();

        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String rate = cursor.getString(cursor.getColumnIndexOrThrow(RATE));
            String rate_count = cursor.getString(cursor.getColumnIndexOrThrow(RATE_COUNT));
            String realeseDate = cursor.getString(cursor.getColumnIndexOrThrow(DATE));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
            String language = cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE));
            String poster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH));
            String banner = cursor.getString(cursor.getColumnIndexOrThrow(BANNER));

            MovieItem movieItem = new MovieItem();
            movieItem.setId(id);
            movieItem.setJudul(title);
            movieItem.setRate(rate);
            movieItem.setRateCount(rate_count);
            movieItem.setTanggalRilis(realeseDate);
            movieItem.setOverview(desc);
            movieItem.setBahasa(language);
            movieItem.setThunail(poster);
            movieItem.setBanner(banner);

            list.add(movieItem);
        }
        return list;
    }
}
