package com.dorizu.catalogmovietv.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.dorizu.catalogmovietv.db.DatabaseContract;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "favorite";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FILM);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAV_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAV_TVSHOW);
        onCreate(db);
    }

    private static final String SQL_CREATE_TABLE_FILM = String.format("CREATE TABLE %s "+
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAV_MOVIE,
            DatabaseContract.NoteCulumns._ID,
            DatabaseContract.NoteCulumns.TITLE,
            DatabaseContract.NoteCulumns.RATE,
            DatabaseContract.NoteCulumns.RATE_COUNT,
            DatabaseContract.NoteCulumns.DATE,
            DatabaseContract.NoteCulumns.DESCRIPTION,
            DatabaseContract.NoteCulumns.LANGUAGE,
            DatabaseContract.NoteCulumns.POSTER_PATH,
            DatabaseContract.NoteCulumns.BANNER);

    private static final String SQL_CREATE_TABLE_TV = String.format("CREATE TABLE %s "+
                    "(%s INTEGER PRIMARY KEY," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAV_TVSHOW,
            DatabaseContract.NoteCulumns._ID,
            DatabaseContract.NoteCulumns.TITLE,
            DatabaseContract.NoteCulumns.RATE,
            DatabaseContract.NoteCulumns.RATE_COUNT,
            DatabaseContract.NoteCulumns.DATE,
            DatabaseContract.NoteCulumns.DESCRIPTION,
            DatabaseContract.NoteCulumns.LANGUAGE,
            DatabaseContract.NoteCulumns.POSTER_PATH,
            DatabaseContract.NoteCulumns.BANNER);
}
