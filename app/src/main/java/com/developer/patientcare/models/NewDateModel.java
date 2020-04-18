package com.developer.patientcare.models;

import android.content.Context;
import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.ObservableField;
import androidx.databinding.library.baseAdapters.BR;

import com.developer.patientcare.R;

import java.io.Serializable;

public class NewDateModel extends BaseObservable implements Serializable {

    private String drugName;
    private String time;
    private String details;
    private long time_stamp;

    public ObservableField<String> error_drug_name = new ObservableField<>();
    public ObservableField<String> error_time = new ObservableField<>();
    public ObservableField<String> error_details = new ObservableField<>();


    public NewDateModel() {
        this.drugName = "";
        this.time = "";
        this.details = "";
        setTime_stamp(0);
        setDrugName(drugName);
        setTime(time);
        setDetails(details);
    }

    public boolean isDataValid(Context context) {

        if (!TextUtils.isEmpty(drugName) &&
                !TextUtils.isEmpty(time) &&
                !TextUtils.isEmpty(details)

        ) {
            error_drug_name.set(null);
            error_time.set(null);
            error_details.set(null);

            return true;

        } else {



            if (drugName.isEmpty()) {
                error_drug_name.set(context.getString(R.string.field_req));
            } else {
                error_drug_name.set(null);
            }
            if (time.isEmpty()) {
                error_time.set(context.getString(R.string.field_req));
            } else {
                error_time.set(null);
            }

            if (details.isEmpty()) {
                error_details.set(context.getString(R.string.field_req));
            } else {
                error_details.set(null);
            }


        }
        return false;

    }


    @Bindable
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
        notifyPropertyChanged(BR.time);

    }

    @Bindable
    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
        notifyPropertyChanged(BR.drugName);

    }

    @Bindable
    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
        notifyPropertyChanged(BR.details);

    }


    public long getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(long time_stamp) {
        this.time_stamp = time_stamp;
    }
}
