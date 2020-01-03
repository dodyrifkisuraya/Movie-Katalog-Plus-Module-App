package com.dorizu.catalogmovietv.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Movie;

import static com.dorizu.catalogmovietv.db.DatabaseContract.TABLE_FAV_MOVIE;
import static com.dorizu.catalogmovietv.db.DatabaseContract.TABLE_FAV_TVSHOW;
import com.dorizu.catalogmovietv.item.MovieItem;
import com.dorizu.catalogmovietv.item.TvShowItem;

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

public class FavoritHelper {
    private static final String DATABASE_TABLE_1 = TABLE_FAV_MOVIE;
    private static final String DATABASE_TABLE_2 = TABLE_FAV_TVSHOW;
    private static DatabaseHelper dbHelper;
    private static FavoritHelper INSTANCE;
    private static SQLiteDatabase db;

    public FavoritHelper(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public static FavoritHelper getInstance(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new FavoritHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException{
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
        if (db.isOpen()){
            db.close();
        }
    }

    public ArrayList<MovieItem> getFavoritMovie(){
        ArrayList<MovieItem> arrayList = new ArrayList<>();
        Cursor cursor = db.query(DATABASE_TABLE_1,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        MovieItem movieItem;
        if (cursor.getCount() > 0){
            do{
                movieItem = new MovieItem();
                movieItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movieItem.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movieItem.setRate(cursor.getString(cursor.getColumnIndexOrThrow(RATE)));
                movieItem.setRateCount(cursor.getString(cursor.getColumnIndexOrThrow(RATE_COUNT)));
                movieItem.setTanggalRilis(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movieItem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movieItem.setBahasa(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                movieItem.setThunail(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                movieItem.setBanner(cursor.getString(cursor.getColumnIndexOrThrow(BANNER)));
                arrayList.add(movieItem);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean isExistMovie(MovieItem movie){
        db = dbHelper.getReadableDatabase();
        String QUERY = "SELECT * FROM " + TABLE_FAV_MOVIE + " WHERE " + _ID + "=" + movie.getId();
        Cursor cursor = db.rawQuery(QUERY,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insertFavMovie(MovieItem movie){
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getId());
        args.put(TITLE, movie.getJudul());
        args.put(RATE, movie.getRate());
        args.put(RATE_COUNT, movie.getRateCount());
        args.put(DATE, movie.getTanggalRilis());
        args.put(DESCRIPTION, movie.getOverview());
        args.put(LANGUAGE, movie.getBahasa());
        args.put(POSTER_PATH, movie.getThunail());
        args.put(BANNER, movie.getBanner());
        return db.insert(DATABASE_TABLE_1, null, args);
    }

    public int deleteFavMovie(int id){
        return db.delete(TABLE_FAV_MOVIE, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id){
        return db.query(DATABASE_TABLE_1,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    public Cursor queryProvider(){
        return db.query(DATABASE_TABLE_1,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    public ArrayList<TvShowItem> getFavoritTv(){
        ArrayList<TvShowItem> arrayList = new ArrayList<>();
        Cursor cursor = db.query(DATABASE_TABLE_2,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TvShowItem tvItem;
        if (cursor.getCount() > 0){
            do{
                tvItem = new TvShowItem();
                tvItem.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvItem.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvItem.setRate(cursor.getString(cursor.getColumnIndexOrThrow(RATE)));
                tvItem.setRateCount(cursor.getString(cursor.getColumnIndexOrThrow(RATE_COUNT)));
                tvItem.setTanggalRilis(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                tvItem.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvItem.setBahasa(cursor.getString(cursor.getColumnIndexOrThrow(LANGUAGE)));
                tvItem.setThunail(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                tvItem.setBanner(cursor.getString(cursor.getColumnIndexOrThrow(BANNER)));
                arrayList.add(tvItem);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public boolean isExistTv(TvShowItem tvshow){
        db = dbHelper.getReadableDatabase();
        String QUERY = "SELECT * FROM " + TABLE_FAV_TVSHOW + " WHERE " + _ID + "=" + tvshow.getId();

        Cursor cursor = db.rawQuery(QUERY,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public long insertFavTv(TvShowItem tvshow){
        ContentValues args = new ContentValues();
        args.put(_ID, tvshow.getId());
        args.put(TITLE, tvshow.getJudul());
        args.put(RATE, tvshow.getRate());
        args.put(RATE_COUNT, tvshow.getRateCount());
        args.put(DATE, tvshow.getTanggalRilis());
        args.put(DESCRIPTION, tvshow.getOverview());
        args.put(LANGUAGE, tvshow.getBahasa());
        args.put(POSTER_PATH, tvshow.getThunail());
        args.put(BANNER, tvshow.getBanner());
        return db.insert(DATABASE_TABLE_2, null, args);
    }

    public int deleteFavTv(int id){
        return db.delete(TABLE_FAV_TVSHOW, _ID + " = '" + id + "'", null);
    }
}
