package com.developer.patientcare.local_database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {AlertTable.class},version = 1)
public abstract class AlertDataBase extends RoomDatabase {

    public static AlertDataBase instance = null;
    public abstract MyDao getDao();


    public static synchronized AlertDataBase newInstance(Context context)
    {
        if (instance==null)
        {
            instance = Room.databaseBuilder(context,AlertDataBase.class,"patient_care.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
