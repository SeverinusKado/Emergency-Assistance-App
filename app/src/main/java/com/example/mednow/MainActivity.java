package com.example.mednow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Log.d(TAG, "User is not signed in, navigating to StartupActivity");
            startActivity(new Intent(MainActivity.this, StartupActivity.class));
            finish();
            return;
        }

        Log.d(TAG, "User is signed in, email: " + currentUser.getEmail());

        FirebaseApp.initializeApp(this);
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    // User is signed out, return to StartupActivity
                    startActivity(new Intent(MainActivity.this, StartupActivity.class));
                    finish();
                }
            }
        });

        Button btnEmergencyInput = findViewById(R.id.btnEmergencyInput);
        Button btnCall = findViewById(R.id.btnCall);
        Button btnHospitalLocator = findViewById(R.id.btnHospitalLocator);
        Button btnSignOut = findViewById(R.id.btnSignOut);

        btnEmergencyInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, EmergencyInputActivity.class));
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CallActivity.class));
            }
        });

        btnHospitalLocator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, HospitalLocatorActivity.class));
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }

    private void signOut() {
        mAuth.signOut();
        startActivity(new Intent(MainActivity.this, StartupActivity.class));
        finish();
    }

}
