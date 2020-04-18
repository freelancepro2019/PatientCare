package com.developer.patientcare.local_database;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;

public class Alarm {
    private Context context;

    public Alarm(Context context) {
        this.context = context;
    }

    public void startAlarm(AlertTable alertTable)
    {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        @SuppressLint("InvalidWakeLockTag") PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"wake_lock");
        wakeLock.acquire();

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(context.ALARM_SERVICE);
        Intent intent = new Intent(context.getApplicationContext(), AlarmBroadCast.class);
        intent.putExtra("id",alertTable.getOnline_id());
        int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        if (alarmManager!=null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, alertTable.getTime_stamp(),AlarmManager.INTERVAL_DAY, pendingIntent);
            } else {
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alertTable.getTime_stamp(),AlarmManager.INTERVAL_DAY, pendingIntent);

            }
        }

        wakeLock.release();
    }
}
