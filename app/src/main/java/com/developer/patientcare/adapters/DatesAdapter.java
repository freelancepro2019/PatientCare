package com.developer.patientcare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.developer.patientcare.R;
import com.developer.patientcare.databinding.NewDateRowBinding;
import com.developer.patientcare.models.DatesModel;

import java.util.List;

public class DatesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DatesModel> list;
    private Context context;
    private LayoutInflater inflater;
    private AppCompatActivity activity;
    public DatesAdapter(List<DatesModel> list, Context context) {
        this.list = list;
        this.context = context;
        activity = (AppCompatActivity) context;
        inflater = LayoutInflater.from(context);


    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        NewDateRowBinding binding = DataBindingUtil.inflate(inflater, R.layout.new_date_row, parent, false);
        return new MyHolder(binding);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyHolder myHolder = (MyHolder) holder;
        myHolder.binding.setModel(list.get(position));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public NewDateRowBinding binding;

        public MyHolder(@NonNull NewDateRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

        }
    }




}
