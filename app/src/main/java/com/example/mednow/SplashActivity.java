package com.example.mednow;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 2000;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the splash screen theme
        setTheme(R.style.SplashTheme);

        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUser();
            }
        }, SPLASH_TIME_OUT);
    }

    private void checkUser() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is signed in, go to MainActivity
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        } else {
            // No user is signed in, go to StartupActivity
            startActivity(new Intent(SplashActivity.this, StartupActivity.class));
        }
        finish();
    }
}