package com.developer.patientcare.interfaces;

import com.developer.patientcare.models.NewDateModel;

public interface Listeners {


    interface LoginListener {
        void checkDataLogin();
    }
    interface SkipListener
    {
        void skip();
    }
    interface BackListener
    {
        void back();
    }
    interface ShowCountryDialogListener
    {
        void showDialog();
    }

    interface NewDateListener {
        void checkNewDate(NewDateModel newDateModel);
    }
    interface SignUpListener {
        void checkDataSignUp();
    }



    interface UpdateProfileListener
    {
        void updateProfile();
    }

    /*interface ContactListener
    {
        void sendContact(ContactUsModel contactUsModel);
    }*/
}
