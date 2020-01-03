package com.dorizu.favoritemovie.helper;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String DB = "favorite";
    public static final String CONTENT_AUTHORITY = "com.dorizu.catalogmovietv";
    public static String PATH_DATA = "fav_movie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);


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

        public static final Uri CONTENT_URI_MOVIE= Uri.withAppendedPath(BASE_CONTENT_URI, PATH_DATA);
    }
}
