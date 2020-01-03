package com.dorizu.catalogmovietv.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.db.DatabaseContract;
import com.dorizu.catalogmovietv.helper.MappingHelper;
import com.dorizu.catalogmovietv.item.MovieItem;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> movieWidgetItem = new ArrayList<>();
    private ArrayList<MovieItem> movieItems;
    private final Context context;
    Cursor cursor;

    public StackRemoteViewsFactory(Context context) {
        this.context = context;
    }


    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null){
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(DatabaseContract.NoteCulumns.CONTENT_URI_MOVIE,
                null,
                null,
                null,
                null);
        movieItems = MappingHelper.mapCursorToArrayList(cursor);
        Binder.restoreCallingIdentity(identityToken);
        for (MovieItem mv : movieItems){
            try {
                Bitmap bitmap = Glide.with(context)
                        .asBitmap()
                        .load(mv.getThunail())
                        .submit(512,512)
                        .get();

                movieWidgetItem.add(bitmap);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        if (cursor != null){
            cursor.close();
        }
    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.imageView, movieWidgetItem.get(position));
        Bundle bundle = new Bundle();
        bundle.putInt(WidgetFavoritMovie.EXTRA_ITEM, position);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.imageView, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
