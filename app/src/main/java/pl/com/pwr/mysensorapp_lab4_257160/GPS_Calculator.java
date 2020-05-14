package pl.com.pwr.mysensorapp_lab4_257160;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class GPS_Calculator extends MainGPSApp {

    private Button menu_btn, calcul_btn;
    private TextView textView, advanceView, tempo;
    private ProgressBar mProgressBar;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;
    public double initial_lat, initial_long;
    public int distance_selected;
    private double new_lat, new_long;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_calculator);

        back_to_menu();
        start_calculate();

        textView = findViewById(R.id.welcome_calculator);
        advanceView = findViewById(R.id.advancement);

        mProgressBar = findViewById(R.id.circle_progress_bar);
        mProgressBar.setProgress(1); // Initialisation

        tempo = findViewById(R.id.temp_coord);


        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                initial_lat = 0.0;
                initial_long = 0.0;
                distance_selected = 0;

            } else {
                initial_lat = extras.getDouble("First_latitude");
                initial_long = extras.getDouble("First_longitude");
                distance_selected = extras.getInt("Distance_selected");
            }
        } else {
            initial_lat = (Double) savedInstanceState.getSerializable("First_latitude");
            initial_long = (Double) savedInstanceState.getSerializable("First_longitude");
            distance_selected = (Integer) savedInstanceState.getSerializable("First_longitude");
        }
    }





    private void start_calculate(){
        calcul_btn = findViewById(R.id.calculate_btn);
        calcul_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Calculate new coord + modify graphic + modify textview
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(GPS_Calculator.this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);

                } else {
                    getCurrentLocation();
                }
            }
        });

    }

    private void getCurrentLocation() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(GPS_Calculator.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(GPS_Calculator.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() - 1;
                    new_lat = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    new_long = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    tempo.setText(String.format("Lat.: %s | Long.: %s", new_lat, new_long));

                    calculate_distance();
                }
            }
        }, Looper.getMainLooper());

    }

    private void calculate_distance() {
        Float init_lat_float = (float)initial_lat;
        Float init_long_float = (float)initial_long;
        Float new_lat_float = (float)new_lat;
        Float new_long_float = (float)new_long;

        Float lat_difference_degree = (new_lat_float - init_lat_float);
        Float long_difference_degree = (new_long_float - init_long_float);

        // One ° of Latitude = 111km | One ° of Longitude = 80km (in Europe)
        Float lat_difference_distance = lat_difference_degree * 111000;
        Float long_difference_distance = long_difference_degree * 80000;

        Float square_lat = lat_difference_distance * lat_difference_distance;
        Float square_long = long_difference_distance * long_difference_distance;

        Float sum = square_lat + square_long;
        Float distance_float = (float)Math.sqrt(sum);

        String str = Float.toString(distance_float);
        int distance = Integer.parseInt(str);
        int advancement = ((distance / distance_selected)*100);
        int real = 100-advancement;
        mProgressBar.setProgress(real);
        advanceView.setText("Percentage of advancement : " + advancement + " | Distance done :  " + distance );

    }





    private void back_to_menu() {
        menu_btn = findViewById(R.id.menu_button);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPS_Calculator.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
