package com.developer.patientcare.activities_fragments.activity_home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;

import com.developer.patientcare.R;
import com.developer.patientcare.activities_fragments.activity_login.LoginActivity;
import com.developer.patientcare.activities_fragments.activity_my_dates.DatesActivity;
import com.developer.patientcare.activities_fragments.activity_new_date.NewDateActivity;
import com.developer.patientcare.activities_fragments.activity_profile.ProfileActivity;
import com.developer.patientcare.language.LanguageHelper;
import com.developer.patientcare.models.UserModel;
import com.developer.patientcare.preferences.Preferences;
import com.developer.patientcare.share.Common;
import com.developer.patientcare.tags.Tags;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.developer.patientcare.databinding.ActivityHomeBinding;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private ActionBarDrawerToggle toggle;
    private List<String> images;
    private Timer timer;
    private TimerTask timerTask;
    private DatabaseReference dRef;
    private FirebaseAuth mAuth;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        initView();
    }

    private void initView() {
        mAuth = FirebaseAuth.getInstance();
        images = new ArrayList<>();
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);

        toggle = new ActionBarDrawerToggle(this, binding.drawer, binding.toolBar, R.string.open, R.string.close);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(ContextCompat.getColor(this, R.color.white));

        binding.tab.addTab(binding.tab.newTab().setText("عربي"));
        binding.tab.addTab(binding.tab.newTab().setText("English"));


        if (lang.equals("ar")) {

            binding.tab.getTabAt(0).select();

        } else {
            binding.tab.getTabAt(1).select();


        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            binding.drawer.setElevation(0.0f);
        }

        binding.flnewDate.setOnClickListener(view -> {
            if (userModel == null) {
                Common.CreateDialogAlert(this, getString(R.string.please_sign_in_or_sign_up));
            } else {
                navigateToNewDateActivity();

            }
        });
        binding.flDates.setOnClickListener(view -> {
            if (userModel == null) {
                Common.CreateDialogAlert(this, getString(R.string.please_sign_in_or_sign_up));
            } else {
                navigateToMyDatesActivity();

            }
        });

        binding.drawer.setScrimColor(Color.TRANSPARENT);

        binding.drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                float slideX = drawerView.getWidth() * slideOffset;
                if (lang.equals("ar")) {
                    slideX = slideX * -1;
                }
                binding.llHomeContent.setTranslationX(slideX);

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        binding.tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                binding.drawer.closeDrawer(GravityCompat.START);


                int pos = tab.getPosition();

                if (pos == 0) {
                    RefreshActivity("ar");
                } else {
                    RefreshActivity("en");

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        binding.cardViewLogout.setOnClickListener(view -> logout());

        binding.consProfile.setOnClickListener(view -> {
            if (userModel == null) {
                Common.CreateDialogAlert(this, getString(R.string.please_sign_in_or_sign_up));
            } else {

                navigateToProfileActivity();
            }
        });





    }

    private void navigateToProfileActivity()
    {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    private void navigateToNewDateActivity()
    {
        Intent intent = new Intent(this, NewDateActivity.class);
        startActivity(intent);
    }
    private void navigateToMyDatesActivity()
    {
        Intent intent = new Intent(this, DatesActivity.class);
        startActivity(intent);
    }
    public void navigateToSignInActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void logout()
    {
        binding.drawer.closeDrawer(GravityCompat.START);
        if (userModel==null)
        {
            navigateToSignInActivity();

        }else
            {
                mAuth.signOut();
                preferences.clear(this);
                navigateToSignInActivity();


            }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            navigateToSignInActivity();
        }
    }

    public void RefreshActivity(String lang)
    {
        Paper.book().write("lang", lang);
        LanguageHelper.setNewLocale(this, lang);

        new Handler()
                .postDelayed(() -> {

                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }, 1050);


    }



    @Override
    public void onBackPressed() {

        if (binding.drawer.isDrawerOpen(GravityCompat.START))
        {
            binding.drawer.closeDrawer(GravityCompat.START);
        }else
            {
                if (userModel == null) {
                    navigateToSignInActivity();
                } else {
                    finish();
                }
            }


    }


}
