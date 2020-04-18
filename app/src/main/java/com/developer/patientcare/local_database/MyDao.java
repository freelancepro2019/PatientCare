package com.developer.patientcare.local_database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MyDao {

    @Insert
    void insert(AlertTable alertTable);

    @Update
    void update(AlertTable alertTable);

    @Delete
    void delete(AlertTable alertTable);

    @Query("SELECT * FROM alert_table")
    List<AlertTable> getAllAlert();


    @Query("SELECT * FROM alert_table WHERE online_id =:id LIMIT 1")
    AlertTable getAlertById(String id);


}
