package com.dorizu.favoritemovie.helper;

import android.content.Context;
import android.database.Cursor;

import static com.dorizu.favoritemovie.helper.DatabaseContract.NoteCulumns.*;
import static com.dorizu.favoritemovie.helper.DatabaseContract.PATH_DATA;

public class CRUDHelper {
    private Context context;

    public CRUDHelper(Context context){this.context = context;}

    private String selectAll[] = {_ID,
            TITLE,
            RATE,
            RATE_COUNT,
            DATE,
            DESCRIPTION,
            LANGUAGE,
            POSTER_PATH,
            BANNER};


    public boolean deleteDataId(String id){
        int result = context.getContentResolver().delete(
                CONTENT_URI_MOVIE,
                _ID + " = '" + id + "'",
                null);
        return result != 0;

    }

    public Cursor getMovieId(String id){
        return context.getContentResolver().query(
                CONTENT_URI_MOVIE,
                selectAll,
                _ID + " = '" + id + "'",
                null,
                null
        );
    }

    public Cursor getAllMovie(){
        return context.getContentResolver().query(
          CONTENT_URI_MOVIE,
          selectAll,
          null,
          null,
          null
        );
    }
}
