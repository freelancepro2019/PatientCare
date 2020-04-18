package com.developer.patientcare.local_database;

import java.util.List;

public interface DataBaseListeners {

    void onUpdateSuccess();
    void onDeleteSuccess();
    void onInsertSuccess();
    void display(List<AlertTable> list);
    void onSingleAlertSuccess(AlertTable alertTable);

}
