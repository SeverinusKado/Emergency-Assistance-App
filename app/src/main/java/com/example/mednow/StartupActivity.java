package com.example.mednow;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class StartupActivity extends AppCompatActivity {

    private static final String TAG = "StartupActivity";
    private EditText etEmail, etPassword, etName;
    private Button btnSignIn, btnSignUp;
    private TextView tvToggleMode;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private boolean isSignInMode = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Log.d(TAG, "User is already signed in" + ((FirebaseUser) currentUser).getEmail());
            navigateToMainActivity();
            return;
        }

        setContentView(R.layout.activity_startup);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etName = findViewById(R.id.etName);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        tvToggleMode = findViewById(R.id.tvToggleMode);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
        tvToggleMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleMode();
                }
        });
        updateUI();
    }

    private void toggleMode() {
        isSignInMode = !isSignInMode;
        updateUI();
    }

    private void updateUI() {
        if (isSignInMode) {
            btnSignIn.setVisibility(View.VISIBLE);
            btnSignUp.setVisibility(View.GONE);
            etName.setVisibility(View.GONE);
            tvToggleMode.setText("Don't have an account? Sign up");
        } else {
            btnSignIn.setVisibility(View.GONE);
            btnSignUp.setVisibility(View.VISIBLE);
            etName.setVisibility(View.VISIBLE);
            tvToggleMode.setText("Already have an account? Sign in");
        }
    }

    private void signIn() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Toast.makeText(StartupActivity.this, "Authentication successful.",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(StartupActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(StartupActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void signUp() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String name = etName.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        String userId = mAuth.getCurrentUser().getUid();
                        saveUserinfo(userId, name, email);
                        startActivity(new Intent(StartupActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Toast.makeText(StartupActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveUserinfo(String userId, String name, String email) {
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("createdAt", System.currentTimeMillis());

        mDatabase.child("users").child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> Log.d("StartupActivity", "User info saved successfully"))
                .addOnFailureListener(e -> Log.e("StartupActivity", "Error saving user info", e));
    }

    private void navigateToMainActivity() {
        Log.d(TAG, "Navigating to MainActivity");
        Intent intent = new Intent(StartupActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}