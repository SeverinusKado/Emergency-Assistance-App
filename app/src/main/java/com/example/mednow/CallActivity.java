package com.example.mednow;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CallActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDoctors;
    private DoctorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        recyclerViewDoctors = findViewById(R.id.recyclerViewDoctors);
        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(this));

        List<Doctor> doctors = getDoctors();
        adapter = new DoctorAdapter(doctors, this);
        recyclerViewDoctors.setAdapter(adapter);
    }

    private List<Doctor> getDoctors(){
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Dr. Jackie Kado", "Coptic Hospital", "Emergency Medicine", "0720566605", "Monday - Friday, 9:00 AM - 5:00 PM"));
        doctors.add(new Doctor("Dr. Francis Mureithi", "Avenue Hospital", "Cardiology", "0785012415", "Monday - Saturday, 10:00 AM - 4:00 PM"));
        doctors.add(new Doctor("Dr. Simon Gichuki", "Nairobi Women's Hospital", "Physiotherapy", "0705064689", "Monday - Friday, 8:00 AM - 2:00 PM"));
        doctors.add(new Doctor("Dr. Aisha Mwangi", "Kenyatta National Hospital", "Pediatrics", "0721345678", "Sunday - Thursday, 8:00 AM - 4:00 PM"));
        doctors.add(new Doctor("Dr. Peter Odhiambo", "Mater Hospital", "Orthopedics", "0732789456", "Monday - Friday, 9:00 AM - 3:00 PM"));
        doctors.add(new Doctor("Dr. Grace Wambui", "MP Shah Hospital", "Gynecology", "0712456789", "Monday - Saturday, 10:00 AM - 5:00 PM"));
        doctors.add(new Doctor("Dr. James Otieno", "Aga Khan University Hospital", "Oncology", "0721789123", "Monday - Friday, 8:00 AM - 2:00 PM"));
        doctors.add(new Doctor("Dr. Fatuma Hassan", "Nairobi Hospital", "Internal Medicine", "0789123456", "Monday - Saturday, 9:00 AM - 3:00 PM"));
        doctors.add(new Doctor("Dr. Mark Kimani", "Gertrude's Children's Hospital", "Pediatric Surgery", "0713678901", "Monday - Friday, 10:00 PM - 4:00 AM"));
        doctors.add(new Doctor("Dr. Nancy Chebet", "Coast General Hospital", "Emergency Medicine", "0709234567", "Sunday - Friday, 8:00 AM - 2:00 PM"));
        doctors.add(new Doctor("Dr. Samuel Karanja", "Moi Teaching and Referral Hospital", "Neurology", "0729123456", "Tuesday - Sunday, 9:00 AM - 3:00 PM"));
        doctors.add(new Doctor("Dr. Lillian Mwende", "Thika Level 5 Hospital", "General Surgery", "0730123456", "Monday - Friday, 10:00 AM - 4:00 PM"));
        doctors.add(new Doctor("Dr. Victor Ndungu", "Ruiru Hospital", "Urology", "0708234567", "Monday - Saturday, 8:00 AM - 2:00 PM"));
        doctors.add(new Doctor("Dr. Sarah Ouko", "Kisumu County Hospital", "Anesthesiology", "0723789456", "Monday - Friday, 9:00 AM - 3:00 PM"));
        doctors.add(new Doctor("Dr. Alice Njeri", "Embu General Hospital", "Psychiatry", "0714789456", "Monday - Saturday, 10:00 AM - 5:00 PM"));
        doctors.add(new Doctor("Dr. Evans Munene", "Machakos Level 5 Hospital", "Dermatology", "0718678902", "Monday - Friday, 8:00 AM - 2:00 PM"));
        // Add more doctors if needed
        return doctors;
    }
}
