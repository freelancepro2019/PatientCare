package com.developer.patientcare.local_database;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.util.List;

public class AlarmBroadCast extends BroadcastReceiver implements DataBaseListeners{
    private Context context;
    private String id;
    private LocalNotification notification;
    private ManageDataBase manageDataBase;
    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        id =  intent.getStringExtra("id");
        if (id!=null&&!id.isEmpty())
        {
            manageDataBase = new ManageDataBase(context,this);
            manageDataBase.displayAlertById(id);

        }





    }


    @Override
    public void onUpdateSuccess() {

    }

    @Override
    public void onDeleteSuccess() {

    }

    @Override
    public void onInsertSuccess() {

    }

    @Override
    public void display(List<AlertTable> list) {

    }

    @Override
    public void onSingleAlertSuccess(AlertTable alertTable) {

        notification  = new LocalNotification(context,alertTable.getTitle()+" "+alertTable.getDetails());
        notification.manageNotification();
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator!=null)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(3000,VibrationEffect.DEFAULT_AMPLITUDE));
            }else
            {
                vibrator.vibrate(3000);
            }
        }

    }
}
