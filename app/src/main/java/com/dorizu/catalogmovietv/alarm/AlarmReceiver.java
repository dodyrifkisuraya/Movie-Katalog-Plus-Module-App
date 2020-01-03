package com.dorizu.catalogmovietv.alarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;


import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.item.MovieItem;
import com.dorizu.catalogmovietv.view.MainActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.dorizu.catalogmovietv.BuildConfig;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class AlarmReceiver extends BroadcastReceiver {
    public static final String DAILY_REMINDER_MOVIE = "daily_reminder_movie";
    public static final String DAILY_REMINDER_RELEASE = "daily_reminder_release";
    public static final String EXTRA_TYPE = "extra_type";
    public static final String EXTRA_NOTIF = "extra_notif";
    public final int ID_DAILY = 1000;
    public final int ID_RELEASE = 10001;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String type = intent.getStringExtra(EXTRA_TYPE);
        final Context todayRelease = context;

        if(type.equals(DAILY_REMINDER_RELEASE)){
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String url = BuildConfig.API_URL +
                    "discover/movie?api_key=" +
                    BuildConfig.API_KEY +
                    "&primary_release_date.qte=" +
                    currentDate +
                    "&primary_release_date.lte="+
                    currentDate;

            Log.i("URL",url);
            Log.i("TANGGAL",currentDate);

            AsyncHttpClient client = new AsyncHttpClient();
            client.get(url, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    ArrayList<MovieItem> movie = new ArrayList<>();
                    try {
                        String rMovie = new String(responseBody);
                        JSONObject objMov = new JSONObject(rMovie);
                        JSONArray LMovie = objMov.getJSONArray("results");
                        for (int i = 0; i < LMovie.length(); i++){
                            JSONObject mv = LMovie.getJSONObject(i);
                            MovieItem IMovie = new MovieItem(mv);
                            movie.add(IMovie);
                        }
                        int jumlah = movie.size();
                        String notif = "Hi, there are now " + jumlah + " new movie! Lets check it out.";

                        for (MovieItem iMovie: movie){
                            if(iMovie.getJudul() == movie.get(movie.size() - 1).getJudul()){
                                notif = notif + iMovie.getJudul();
                            }else{
                                notif = notif + iMovie.getJudul() + ", ";
                            }
                            //Beri Alarm Notifikasi
                            showAlarmNotif(todayRelease,type,notif,ID_RELEASE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.d("onFailure: ",error.getMessage());
                }
            });
        }else {
            String notif = intent.getStringExtra(EXTRA_NOTIF);
            String title = type.equalsIgnoreCase(DAILY_REMINDER_MOVIE) ? DAILY_REMINDER_MOVIE : DAILY_REMINDER_RELEASE;
            int id = type.equalsIgnoreCase(DAILY_REMINDER_MOVIE) ? ID_DAILY : ID_RELEASE;
            //Beri alarm Notif
            showAlarmNotif(context,title,notif,id);
        }
    }

    public boolean isAlarmHasSet(Context context, String type){
        Intent intent = new Intent(context, AlarmReceiver.class);
        int requestCode = type.equalsIgnoreCase(DAILY_REMINDER_MOVIE) ? ID_DAILY : ID_RELEASE;

        return PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_NO_CREATE) != null;
    }

    private void showAlarmNotif(Context context, String title, String message, int id){
        String CHANNEL_ID = "channel_2";
        String CHANNEL_NAME = "Release";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,id,intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_search_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(sound)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        //Notifikasi OREO
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            builder.setChannelId(CHANNEL_ID);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
        Notification notification = builder.build();
        notificationManager.notify(id, notification);
    }

    public void setDailyAlarm(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE, type);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,ID_DAILY, intent,0);
        if(alarmManager != null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }

    public void setDailyRelease(Context context, String type){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(context, AlarmReceiver.class);
        intent.putExtra(EXTRA_TYPE,type);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,ID_RELEASE, intent,0);
        if(alarmManager != null){
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY,pendingIntent);
        }
    }

    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManager.class);
        int id = type.equalsIgnoreCase(DAILY_REMINDER_MOVIE) ? ID_DAILY : ID_RELEASE;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, id, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }
    }
}
