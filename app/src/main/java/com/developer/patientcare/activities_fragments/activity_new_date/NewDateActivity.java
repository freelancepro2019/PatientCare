package com.developer.patientcare.activities_fragments.activity_new_date;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import com.developer.patientcare.R;
import com.developer.patientcare.databinding.ActivityNewDateBinding;
import com.developer.patientcare.databinding.DialogAlertBinding;
import com.developer.patientcare.interfaces.Listeners;
import com.developer.patientcare.language.LanguageHelper;
import com.developer.patientcare.models.DatesModel;
import com.developer.patientcare.models.NewDateModel;
import com.developer.patientcare.models.UserModel;
import com.developer.patientcare.preferences.Preferences;
import com.developer.patientcare.share.Common;
import com.developer.patientcare.tags.Tags;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.paperdb.Paper;

public class NewDateActivity extends AppCompatActivity implements Listeners.BackListener, Listeners.NewDateListener, TimePickerDialog.OnTimeSetListener {
    private ActivityNewDateBinding binding;
    private String lang;
    private TimePickerDialog timePickerDialog;
    private NewDateModel newDateModel;
    private Preferences preferences;
    private UserModel userModel;
    private DatabaseReference dRef;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(LanguageHelper.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_new_date);
        initView();

    }

    private void initView() {
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME).child(Tags.TABLE_Dates);
        preferences = Preferences.newInstance();
        userModel = preferences.getUserData(this);
        newDateModel = new NewDateModel();
        Paper.init(this);
        lang = Paper.book().read("lang", "ar");
        binding.setLang(lang);
        binding.setBackListener(this);
        binding.setModel(newDateModel);
        binding.setNewDateListener(this);
        binding.scrollView.getParent().requestChildFocus(binding.scrollView, binding.scrollView);
        createTimePickerDialog();



        binding.tvChangeTime.setOnClickListener(view -> {
            try {
                timePickerDialog.show(getFragmentManager(), "");

            } catch (Exception e) {
            }
        });




    }
    private void createTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
        timePickerDialog.dismissOnPause(true);
        timePickerDialog.setAccentColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        timePickerDialog.setCancelColor(ActivityCompat.getColor(this, R.color.gray4));
        timePickerDialog.setOkColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        timePickerDialog.setOkText(getString(R.string.select));
        timePickerDialog.setCancelText(getString(R.string.cancel));
        timePickerDialog.setLocale(new Locale(lang));
        timePickerDialog.setVersion(TimePickerDialog.Version.VERSION_2);


    }


    private void send(NewDateModel model)
    {
        ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.show();
        String id = dRef.child(userModel.getId()).push().getKey();
        String create_at = new SimpleDateFormat("dd/MMM/yyyy hh:mm aa",Locale.ENGLISH).format(new Date(Calendar.getInstance().getTimeInMillis()));
        DatesModel datesModel = new DatesModel(id,model.getDrugName(), model.getTime(), model.getDetails(),create_at);
        dRef.child(userModel.getId()).child(id)
                .setValue(datesModel)
                .addOnCompleteListener(task -> {
                    dialog.dismiss();
                    if (task.isSuccessful()) {
                        createDialogAlert();
                    }
                }).addOnFailureListener(e -> {
            dialog.dismiss();
            if (e.getMessage() != null) {
                Toast.makeText(this, getString(R.string.failed), Toast.LENGTH_SHORT).show();
            }
        });
    }




    private void createDialogAlert()
    {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .create();

        DialogAlertBinding binding = DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.dialog_alert, null, false);

        binding.tvMsg.setText(getString(R.string.suc));
        binding.btnCancel.setOnClickListener(v -> {

                    dialog.dismiss();
                    finish();
                }

        );
        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_window_bg);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(binding.getRoot());
        dialog.show();
    }



    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);

        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm aa", Locale.ENGLISH);
        String t = dateFormat.format(new Date(calendar.getTimeInMillis()));
        binding.tvTime.setText(t);

        newDateModel.setTime(t);
        binding.setModel(newDateModel);
    }

    @Override
    public void back() {
        finish();
    }



    @Override
    public void checkNewDate(NewDateModel newDateModel) {
        if (newDateModel.isDataValid(this)) {
            if (userModel != null) {
                send(newDateModel);

            } else {
                Common.CreateDialogAlert(this, getString(R.string.please_sign_in_or_sign_up));
            }
        }
    }
}
