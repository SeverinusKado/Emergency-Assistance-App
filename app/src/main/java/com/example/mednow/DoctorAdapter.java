package com.example.mednow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private List<Doctor> doctors;
    private Context context;

    public DoctorAdapter(List<Doctor> doctors, Context context) {
        this.doctors = doctors;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        Doctor doctor = doctors.get(position);
        holder.textViewDoctorName.setText(doctor.getName());
        holder.textViewHospital.setText(doctor.getHospital());
        holder.textViewSpecialty.setText(doctor.getSpeciality());

        holder.buttonCall.setOnClickListener(v ->{
                //Initiate call to doctor
                Toast.makeText(context, "Calling" + doctor.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + doctor.getPhoneNumber()));
                context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount(){
        return doctors.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewDoctorName;
        TextView textViewHospital;
        TextView textViewSpecialty;
        TextView textViewAvailableHours;
        Button buttonCall;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewDoctorName = itemView.findViewById(R.id.textViewDoctorName);
            textViewHospital = itemView.findViewById(R.id.textViewHospital);
            textViewSpecialty = itemView.findViewById(R.id.textViewSpecialty);
            textViewAvailableHours = itemView.findViewById(R.id.textViewAvailableHours);
            buttonCall = itemView.findViewById(R.id.buttonCall);
        }
    }
    }

