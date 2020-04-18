package com.developer.patientcare.local_database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "alert_table")
public class AlertTable implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String online_id;
    private String title;
    private String details;
    private String time;
    private String create_at;
    private long time_stamp;

    public AlertTable(String online_id, String title, String details, String time, String create_at, long time_stamp) {
        this.online_id = online_id;
        this.title = title;
        this.details = details;
        this.time = time;
        this.create_at = create_at;
        this.time_stamp = time_stamp;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOnline_id() {
        return online_id;
    }

    public void setOnline_id(String online_id) {
        this.online_id = online_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
}
