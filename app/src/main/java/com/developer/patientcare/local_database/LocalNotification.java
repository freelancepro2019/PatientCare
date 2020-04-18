package com.developer.patientcare.local_database;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.developer.patientcare.R;

public class LocalNotification {

    private Context context;
    private String content;

    public LocalNotification(Context context, String content) {
        this.context = context;
        this.content = content;
    }

    public void manageNotification()
    {
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O)
        {
            createNewVersion();
        }else
            {
                createOldVersion();
            }
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNewVersion() {

        String channel_id = "done_channel_id";
        CharSequence channel_name ="done_channel_name";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel(channel_id,channel_name,importance);
        channel.setShowBadge(true);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channel_id);
        builder.setChannelId(channel_id);
        channel.setDescription("no sound");

        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher_round));
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(content);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager!=null)
        {
            manager.createNotificationChannel(channel);
            manager.notify("done_tag",1414,builder.build());
        }


    }

    private void createOldVersion() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.mipmap.ic_launcher_round);
        builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.ic_launcher_round));
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setContentText(content);
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(content));

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (manager!=null)
        {
            manager.notify("done_tag",1414,builder.build());
        }

    }

}
