package com.developer.patientcare.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.developer.patientcare.models.UserModel;
import com.google.gson.Gson;

public class Preferences {

    private static Preferences instance = null;

    private Preferences() {
    }

    public static synchronized Preferences newInstance()
    {
        if (instance==null)
        {
            instance = new Preferences();
        }

        return instance;
    }
    public Boolean isLangSelected(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("langPref",Context.MODE_PRIVATE);
        return preferences.getBoolean("selected",false);

    }
    public void saveSelectedLanguage(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("langPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor. putBoolean("selected",true);
        editor.apply();
    }

    public void create_update_userData(Context context , UserModel userModel)
    {
        SharedPreferences preferences = context.getSharedPreferences("userPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        Gson gson = new Gson();
        String userDataGson = gson.toJson(userModel);
        editor.putString("user_data",userDataGson);
        editor.apply();
    }

    public UserModel getUserData(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("userPref",Context.MODE_PRIVATE);
        String userDataGson = preferences.getString("user_data","");
        return new Gson().fromJson(userDataGson,UserModel.class);
    }

    public void createSession(Context context,String session)
    {
        SharedPreferences preferences = context.getSharedPreferences("sessionPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("session",session);
        editor.apply();
    }

    public String getSession(Context context)
    {
        SharedPreferences preferences = context.getSharedPreferences("sessionPref",Context.MODE_PRIVATE);
        return preferences.getString("session","");
    }

    public void clear(Context context)
    {
        SharedPreferences preferences1 = context.getSharedPreferences("userPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.clear();
        editor1.apply();

        SharedPreferences preferences2 = context.getSharedPreferences("sessionPref",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.apply();

    }


}
