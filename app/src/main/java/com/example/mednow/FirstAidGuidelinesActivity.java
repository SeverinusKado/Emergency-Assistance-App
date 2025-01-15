package com.example.mednow;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class FirstAidGuidelinesActivity extends AppCompatActivity {

    private TextView textViewEmergencyType;
    private TextView textViewGuidelines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid_guidelines);

        textViewEmergencyType = findViewById(R.id.textViewEmergencyType);
        textViewGuidelines = findViewById(R.id.textViewGuidelines);

        String emergencyType = getIntent().getStringExtra("EMERGENCY_TYPE");
        String description = getIntent().getStringExtra("DESCRIPTION");

        textViewEmergencyType.setText(emergencyType);
        textViewGuidelines.setText(getFirstAidGuidelines(emergencyType));
    }

    private String getFirstAidGuidelines(String emergencyType) {

        // Fetch guidelines from a database or API
        switch (emergencyType) {
            case "Cardiac Arrest":
                return "1. Call for emergency help immediately.\n" +
                        "2. Begin chest compressions: Push hard and fast in the center of the chest.\n" +
                        "3. If trained, give rescue breaths after every 30 compressions.\n" +
                        "4. Continue CPR until professional help arrives.";
            case "Severe Bleeding":
                return "1. Apply direct pressure to the wound with a clean cloth.\n" +
                        "2. Elevate the injured area above the heart if possible.\n" +
                        "3. Do not remove the cloth if it becomes soaked; add more on top.\n" +
                        "4. Seek immediate medical attention.";
            // Add more cases for different emergency types
            default:
                return "Please seek professional medical help immediately.";
        }
    }
}
