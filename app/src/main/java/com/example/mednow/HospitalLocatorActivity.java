package com.example.mednow;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.database.collection.BuildConfig;

import org.osmdroid.config.Configuration;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class HospitalLocatorActivity extends AppCompatActivity {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationClient;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize OSMDroid
        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);
        setContentView(R.layout.activity_hospital_locator);

        //Initialize Map View
        mapView = findViewById(R.id.map);
        mapView.setTileSource(org.osmdroid.tileprovider.tilesource.TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Button btnFindHospitals = findViewById(R.id.btnFindHospitals);
        btnFindHospitals.setOnClickListener(v -> findNearbyHospitals());

        enableMyLocation();
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permissions are granted
            // Start location tracking
            getCurrentLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                enableMyLocation(); // Retry enabling location
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        try {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, location -> {
                        if (location != null) {
                        GeoPoint currentGeoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                        mapView.getController().setCenter(currentGeoPoint);
                            mapView.getController().setZoom(15);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace(); // Log the exception
            Toast.makeText(this, "Error retrieving location", Toast.LENGTH_SHORT).show();
        }
    }

    private void findNearbyHospitals() {
        // Simulated nearby hospitals
        List<Hospital> nearbyHospitals = getNearbyHospitals();
        displayHospitalsOnMap(nearbyHospitals);
    }

    private List<Hospital> getNearbyHospitals() {
        List<Hospital> hospitals = new ArrayList<>();

        // List of hospitals in Kenya with accurate GeoPoints
        hospitals.add(new Hospital("Coptic Hospital", new GeoPoint(-1.2921, 36.8219))); // Nairobi
        hospitals.add(new Hospital("Nairobi Hospital", new GeoPoint(-1.3000, 36.8000))); // Nairobi
        hospitals.add(new Hospital("Kenyatta National Hospital", new GeoPoint(-1.2800, 36.8300))); // Nairobi
        hospitals.add(new Hospital("Mater Hospital", new GeoPoint(-1.2975, 36.8325))); // Nairobi
        hospitals.add(new Hospital("Aga Khan University Hospital", new GeoPoint(-1.2609, 36.8125))); // Nairobi
        hospitals.add(new Hospital("MP Shah Hospital", new GeoPoint(-1.3084, 36.8153))); // Nairobi
        hospitals.add(new Hospital("Nairobi West Hospital", new GeoPoint(-1.3145, 36.7491))); // Nairobi
        hospitals.add(new Hospital("Karen Hospital", new GeoPoint(-1.3250, 36.6851))); // Nairobi
        hospitals.add(new Hospital("Mombasa Hospital", new GeoPoint(-4.0434, 39.6682))); // Mombasa
        hospitals.add(new Hospital("Coast General Teaching and Referral Hospital", new GeoPoint(-4.0475, 39.6702))); // Mombasa
        hospitals.add(new Hospital("Gertrude's Children's Hospital", new GeoPoint(-1.2860, 36.8260))); // Nairobi
        hospitals.add(new Hospital("Nairobi South Hospital", new GeoPoint(-1.3324, 36.7515))); // Nairobi
        hospitals.add(new Hospital("Umoja Hospital", new GeoPoint(-1.2735, 36.8598))); // Nairobi
        hospitals.add(new Hospital("Nyeri County Referral Hospital", new GeoPoint(-0.4167, 36.9497))); // Nyeri
        hospitals.add(new Hospital("Kisii Teaching and Referral Hospital", new GeoPoint(-0.6705, 34.7670))); // Kisii
        hospitals.add(new Hospital("Eldoret Hospital", new GeoPoint(0.5124, 35.2695))); // Eldoret
        hospitals.add(new Hospital("Kakamega County Referral Hospital", new GeoPoint(-0.2830, 34.7497))); // Kakamega
        hospitals.add(new Hospital("Malindi Hospital", new GeoPoint(-3.2166, 40.1181))); // Malindi
        hospitals.add(new Hospital("Moi Teaching and Referral Hospital", new GeoPoint(0.5149, 35.2654))); // Eldoret
        hospitals.add(new Hospital("Embu Level 5 Hospital", new GeoPoint(-0.5244, 37.4586))); // Embu
        hospitals.add(new Hospital("Machakos Level 5 Hospital", new GeoPoint(-1.5152, 37.2607))); // Machakos
        hospitals.add(new Hospital("Kajiado County Referral Hospital", new GeoPoint(-1.8824, 36.8214))); // Kajiado
        hospitals.add(new Hospital("Nakuru Level 5 Hospital", new GeoPoint(-0.3035, 36.0670))); // Nakuru
        hospitals.add(new Hospital("Bomet County Referral Hospital", new GeoPoint(-0.6707, 35.0969))); // Bomet
        hospitals.add(new Hospital("Kisumu County Referral Hospital", new GeoPoint(-0.0910, 34.7675))); // Kisumu
        hospitals.add(new Hospital("Siaya County Referral Hospital", new GeoPoint(-0.0613, 34.2877))); // Siaya

        return hospitals;
    }


    private void displayHospitalsOnMap(List<Hospital> nearbyHospitals) {
        for (Hospital hospital : nearbyHospitals) {
            Marker hospitalMarker = new Marker(mapView);
            hospitalMarker.setPosition(hospital.getLocation());
            hospitalMarker.setTitle(hospital.getName());
            hospitalMarker.setOnMarkerClickListener((marker, clickEvent) -> {
                navigateToHospital(hospital.getLocation());
                return true;
            });
            mapView.getOverlays().add(hospitalMarker);
            hospitalMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        }
        mapView.invalidate();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void navigateToHospital(GeoPoint hospitalGeoPoint) {
        // Set the map to the hospital's location using OSMDroid
        mapView.getController().animateTo(hospitalGeoPoint); // Assuming mapView is your OSM MapView instance
        // Add a marker to indicate the hospital's location
        Marker hospitalMarker = new Marker(mapView);
        hospitalMarker.setPosition(hospitalGeoPoint);
        hospitalMarker.setTitle("Hospital");
        hospitalMarker.setIcon(getResources().getDrawable(R.drawable.ic_launcher_background)); // Set an icon for the hospital marker
        mapView.getOverlays().add(hospitalMarker);
        mapView.invalidate(); // Refresh the map view

        // Optional: Show a Toast message indicating the hospital's location
        Toast.makeText(this, "Navigating to Hospital", Toast.LENGTH_SHORT).show();

        // If the user wants to open Google Maps for navigation
        new AlertDialog.Builder(this)
                .setTitle("Open Google Maps")
                .setMessage("Do you want to navigate to this hospital using Google Maps?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Redirect to Google Maps
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + hospitalGeoPoint.getLatitude() + "," + hospitalGeoPoint.getLongitude());
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                })
                .setNegativeButton("No", null)
                .show();
    }


    private static class Hospital {
        private String name;
        private GeoPoint location;

        public Hospital(String name, GeoPoint location) {
            this.name = name;
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public GeoPoint getLocation() {
            return location;
        }
    }
}
