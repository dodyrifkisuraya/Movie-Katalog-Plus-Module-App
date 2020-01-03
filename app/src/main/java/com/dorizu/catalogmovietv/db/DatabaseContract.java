package com.dorizu.catalogmovietv.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static String TABLE_FAV_MOVIE = "fav_movie";
    public static String TABLE_FAV_TVSHOW = "fav_tvshow";
    public static final String AUTHORITY = "com.dorizu.catalogmovietv";
    private static final String SCHEME = "content";

    public static final class NoteCulumns implements BaseColumns {

        public static String TITLE = "title";
        public static String RATE  = "rate";
        public static String RATE_COUNT ="rate_count";
        public static String DATE = "date";
        public static String DESCRIPTION = "description";
        public static String LANGUAGE = "language";
        public static String POSTER_PATH = "poster_path";
        public static String BANNER = "banner";

        public static final Uri CONTENT_URI_MOVIE= new Uri.Builder()
                .scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_FAV_MOVIE)
                .build();
    }
}
