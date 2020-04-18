package com.developer.patientcare.local_database;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public class ManageDataBase {
    private AlertDataBase alertDataBase;
    private DataBaseListeners dataBaseListeners;

    public ManageDataBase(Context context, DataBaseListeners dataBaseListeners)
    {
        alertDataBase = AlertDataBase.newInstance(context);
        this.dataBaseListeners = dataBaseListeners;
    }



    public void insert(AlertTable alertTable)
    {
        new InsertTask().execute(alertTable);
    }

    public void update(AlertTable alertTable)
    {
        new UpdateTask().execute(alertTable);
    }

    public void delete(AlertTable alertTable)
    {
        new DeleteTask().execute(alertTable);
    }

    public void display()
    {
        new DisplayTask().execute();
    }

    public void displayAlertById(String id)
    {
        new DisplaySingleAlertByIdTask().execute(id);
    }


    private class InsertTask extends AsyncTask<AlertTable,Void,Void>
    {
        @Override
        protected Void doInBackground(AlertTable... alertTables) {
            alertDataBase.getDao().insert(alertTables[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dataBaseListeners.onInsertSuccess();
            super.onPostExecute(aVoid);
        }
    }

    private class UpdateTask extends AsyncTask<AlertTable,Void,Void>
    {
        @Override
        protected Void doInBackground(AlertTable... alertTables) {
            alertDataBase.getDao().update(alertTables[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dataBaseListeners.onUpdateSuccess();
            super.onPostExecute(aVoid);
        }
    }

    private class DeleteTask extends AsyncTask<AlertTable,Void,Void>
    {
        @Override
        protected Void doInBackground(AlertTable... alertTables) {
            alertDataBase.getDao().update(alertTables[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            dataBaseListeners.onDeleteSuccess();
            super.onPostExecute(aVoid);
        }
    }

    private class DisplayTask extends AsyncTask<Void,Void, List<AlertTable>>
    {


        @Override
        protected List<AlertTable> doInBackground(Void... voids) {
            List<AlertTable> list = alertDataBase.getDao().getAllAlert();
            return list;
        }

        @Override
        protected void onPostExecute(List<AlertTable> list) {
            super.onPostExecute(list);
            dataBaseListeners.display(list);
        }
    }


    private class DisplaySingleAlertByIdTask extends AsyncTask<String,Void, AlertTable>
    {

        @Override
        protected AlertTable doInBackground(String... strings) {
            AlertTable alertTable = alertDataBase.getDao().getAlertById(strings[0]);
            return alertTable;
        }

        @Override
        protected void onPostExecute(AlertTable alertTable) {
            super.onPostExecute(alertTable);
            dataBaseListeners.onSingleAlertSuccess(alertTable);
        }
    }
}
