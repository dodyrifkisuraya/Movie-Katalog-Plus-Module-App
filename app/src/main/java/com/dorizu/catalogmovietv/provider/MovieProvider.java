package com.dorizu.catalogmovietv.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dorizu.catalogmovietv.db.DatabaseContract;
import com.dorizu.catalogmovietv.helper.FavoritHelper;

public class MovieProvider extends ContentProvider {
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private FavoritHelper favHelper;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(DatabaseContract.AUTHORITY,DatabaseContract.TABLE_FAV_MOVIE, MOVIE);
        sUriMatcher.addURI(DatabaseContract.AUTHORITY, DatabaseContract.TABLE_FAV_MOVIE+ "/#", MOVIE_ID);
    }


    @Override
    public boolean onCreate() {
        favHelper = FavoritHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        favHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)){
            case MOVIE:
                cursor = favHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
                default:
                    cursor = null;
                    break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}
