package com.developer.patientcare.models;

import java.io.Serializable;

public class DatesModel implements Serializable {
    private String id;
    private String drugName;
    private String time;
    private String details;
    private String create_at;

    public DatesModel() {
    }

    public DatesModel(String id, String drugName , String time, String details, String create_at) {
        this.id = id;
        this.drugName = drugName;
        this.time = time;
        this.details = details;
        this.create_at = create_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }


}
