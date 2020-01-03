package com.dorizu.catalogmovietv.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.dorizu.catalogmovietv.R;
import com.dorizu.catalogmovietv.alarm.AlarmReceiver;

public class NotifActivity extends AppCompatActivity implements View.OnClickListener {
    private AlarmReceiver alarmReceiver;
    private Switch daily, realese;
    private CardView changeLanguage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setTitle(getString(R.string.setting));

        daily = findViewById(R.id.daily_reminder);
        realese = findViewById(R.id.daily_reminder_release);
        changeLanguage = findViewById(R.id.change_language);
        changeLanguage.setOnClickListener(this);

        alarmReceiver = new AlarmReceiver();

        if (alarmReceiver.isAlarmHasSet(this, AlarmReceiver.DAILY_REMINDER_MOVIE)){
            daily.setChecked(true);
        }else {
            daily.setChecked(false);
        }

        if (alarmReceiver.isAlarmHasSet(this, AlarmReceiver.DAILY_REMINDER_RELEASE)){
            realese.setChecked(true);
        }else{
            realese.setChecked(false);
        }

        daily.setOnClickListener(this);
        realese.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.daily_reminder:
                if (daily.isChecked()){
                    alarmReceiver.setDailyAlarm(this, AlarmReceiver.DAILY_REMINDER_MOVIE);
                    Toast.makeText(this,getString(R.string.daily_on),Toast.LENGTH_SHORT).show();
                }else {
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.DAILY_REMINDER_MOVIE);
                    Toast.makeText(this,getString(R.string.daily_off),Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.daily_reminder_release:
                if (realese.isChecked()){
                    alarmReceiver.setDailyRelease(this, AlarmReceiver.DAILY_REMINDER_RELEASE);
                    Toast.makeText(this, getString(R.string.realese_on),Toast.LENGTH_SHORT).show();
                }else {
                    alarmReceiver.cancelAlarm(this, AlarmReceiver.DAILY_REMINDER_RELEASE);
                    Toast.makeText(this, getString(R.string.realese_off),Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.change_language:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }
}
