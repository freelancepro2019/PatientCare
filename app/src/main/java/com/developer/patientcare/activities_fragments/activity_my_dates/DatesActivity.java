package com.developer.patientcare.activities_fragments.activity_my_dates;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.developer.patientcare.R;
import com.developer.patientcare.adapters.DatesAdapter;
import com.developer.patientcare.databinding.ActivityDatesBinding;
import com.developer.patientcare.interfaces.Listeners;
import com.developer.patientcare.language.LanguageHelper;
import com.developer.patientcare.models.DatesModel;
import com.developer.patientcare.models.UserModel;
import com.developer.patientcare.preferences.Preferences;
import com.developer.patientcare.tags.Tags;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class DatesActivity extends AppCompatActivity implements Listeners.BackListener {
    private ActivityDatesBinding binding;
    private String lang;
    private Preferences preferences;
    private UserModel userModel;
    private DatesAdapter adapter;
    private List<DatesModel> datesModelList;
    private DatabaseReference dRef;



    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dates);
        addNotification();
        initView();
    }


    private void initView() {
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME);
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        datesModelList = new ArrayList<>();
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        binding.setBackListener(this);
        binding.setLang(lang);
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DatesAdapter(datesModelList, this);
        binding.recView.setAdapter(adapter);

        getData();

        String currentTime = new SimpleDateFormat("HH:mm").format(new Date());
        String timeToCompare = "15:30";

        SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
        displayFormat.format("10:30 PM");
        boolean x = currentTime.equals(timeToCompare);
    }

    private void getData() {
        dRef.child(Tags.TABLE_Dates).child(userModel.getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.progBar.setVisibility(View.GONE);
                if (dataSnapshot.getValue() != null) {

                    for (DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        DatesModel datesModel = ds.getValue(DatesModel.class);
                        if (datesModel!=null)
                        {
                            datesModelList.add(datesModel);
                        }
                    }
                    if (datesModelList.size()>0)
                    {
                        binding.tvNoProduct.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();
                    }else
                    {
                        binding.tvNoProduct.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.tvNoProduct.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    @Override
    public void back() {
        finish();
    }

    private void addNotification() {
      if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
          CharSequence name="notfy";
          String desc="desccccc";
          int imprtance=NotificationManager.IMPORTANCE_DEFAULT;
          NotificationChannel channel=new NotificationChannel("notify",name,imprtance);
          channel.setDescription(desc);
          NotificationManager manager=getSystemService(NotificationManager.class);
          manager.createNotificationChannel(channel);
      }
    }

}
