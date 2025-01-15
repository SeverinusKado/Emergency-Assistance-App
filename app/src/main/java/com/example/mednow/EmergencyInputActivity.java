package com.example.mednow;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.tensorflow.lite.Interpreter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import okhttp3.*;
import org.json.JSONObject;
import java.util.concurrent.TimeUnit;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

public class EmergencyInputActivity extends AppCompatActivity {
    private TextInputEditText etEmergencyInput;
    private TextView tvGuidelines;
    private Interpreter tflite;
    private static final String API_KEY = "Insert API Key";
    private static final String API_URL = "https://api.cohere.ai/v1/generate";
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_input);

        etEmergencyInput = findViewById(R.id.etEmergencyInput);
        MaterialButton btnGetGuidelines = findViewById(R.id.btnGetGuidelines);
        tvGuidelines = findViewById(R.id.tvGuidelines);

        // Initialize TFLite model for offline use
        try {
            tflite = new Interpreter(loadModelFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

        btnGetGuidelines.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EmergencyInputActivity.this,
                        "Button Clicked",
                        Toast.LENGTH_SHORT).show();
                String emergencyDescription = etEmergencyInput.getText().toString().trim();
                if (emergencyDescription.isEmpty()) {
                    Toast.makeText(EmergencyInputActivity.this,
                            "Please describe the emergency situation",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (isNetworkAvailable()) {
                    // Use online AI service
                    Toast.makeText(EmergencyInputActivity.this, "Connected to Internet", Toast.LENGTH_SHORT).show();
                    getOnlineGuidelines(emergencyDescription);
                } else {
                    // Use offline AI model
                    Toast.makeText(EmergencyInputActivity.this, "No Internet Connection - Using Offline Mode", Toast.LENGTH_SHORT).show();
                    getOfflineGuidelines(emergencyDescription);
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void getOnlineGuidelines(String emergencyDescription) {
        String prompt = "Given this emergency situation: " + emergencyDescription +
                "\n\nProvide clear, step-by-step first aid guidelines for this specific situation. " +
                "Include what to do immediately and what not to do. Format the response in bullet points.";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("model", "command-xlarge-nightly");
            jsonBody.put("prompt", prompt);
            jsonBody.put("max_tokens", 200);
            jsonBody.put("temperature", 0.7);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"), jsonBody.toString());

        Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("Authorization", "Bearer " + API_KEY)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(EmergencyInputActivity.this,
                            "Network error. Switching to offline mode.",
                            Toast.LENGTH_SHORT).show();
                    getOfflineGuidelines(emergencyDescription);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response.body().string());
                        JSONArray generationsArray = jsonResponse.getJSONArray("generations");
                        String guidelines = generationsArray.getJSONObject(0).getString("text");

                        // Format the response for bullet points and bold
                        String formattedGuidelines = String.valueOf(formatGuidelinesForDisplay(guidelines));

                        runOnUiThread(() -> tvGuidelines.setText(formattedGuidelines));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> tvGuidelines.setText("Failed to parse response"));
                    }
                } else {
                    String errorMessage = "Error" + response.code() + ": " + response.message();
                    runOnUiThread(() -> tvGuidelines.setText("Failed to get response from server\n" + errorMessage));
                }
            }
        });
    }

    private Spanned formatGuidelinesForDisplay(String guidelines) {
        // Split the text into lines and format each as a bullet point
        StringBuilder formattedText = new StringBuilder();
        String[] lines = guidelines.split("\n");

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                // Convert asterisks to HTML bold formatting
                String formattedLine = line.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");
                formattedText.append("&#8226; ").append(formattedLine).append("<br>");
            }
        }

        // Return the formatted text as Spanned HTML
        return Html.fromHtml(formattedText.toString(), Html.FROM_HTML_MODE_LEGACY);
    }

    private void getOfflineGuidelines(String emergencyDescription) {
        // Process the input using TFLite model
        String[] keywords = processEmergencyText(emergencyDescription);
        String guidelines = getOfflineGuidelinesFromKeywords(keywords);
        tvGuidelines.setText(guidelines);
    }

    private String[] processEmergencyText(String text) {
        // Implement text processing logic here
        return text.toLowerCase().split(" ");
    }

    private String getOfflineGuidelinesFromKeywords(String[] keywords) {
        FirstAidDatabaseHelper dbHelper = new FirstAidDatabaseHelper(this);
        StringBuilder guidelines = new StringBuilder();
        guidelines.append("OFFLINE MODE - Basic First Aid Guidelines:\n\n");

        String guidelinesFromDb = dbHelper.getGuidelinesByKeywords(keywords);
        if (!guidelinesFromDb.isEmpty()) {
            guidelines.append(guidelinesFromDb);
        } else {
            guidelines.append("No guidelines found for the provided keywords.");
        }

        dbHelper.close();
        return guidelines.toString();
    }

    private boolean containsAnyKeyword(String[] words, String... keywords) {
        for (String word : words) {
            for (String keyword : keywords) {
                if (word.contains(keyword)) return true;
            }
        }
        return false;
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        String modelPath = "first_aid_model.tflite";
        File file = new File(getFilesDir(), modelPath);
        FileInputStream inputStream = new FileInputStream(file);
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = 0;
        long declaredLength = file.length();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (tflite != null) {
            tflite.close();
        }
    }
}
