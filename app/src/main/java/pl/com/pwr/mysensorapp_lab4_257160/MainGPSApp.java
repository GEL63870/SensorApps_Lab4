package pl.com.pwr.mysensorapp_lab4_257160;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class MainGPSApp extends AppCompatActivity  {

    private TextView textView, coordinatesView, lengthView, description;
    private Button menu_btn, init_gps, launch_btn;
    private SeekBar selected_length;
    private int min, max, step;

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 1;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_gps_app);

        // Method that can be launch from onCreateMethod
        back_to_menu();
        launch_Calculator();

        // Initialisation of all XML objects designed
        textView = findViewById(R.id.welcome_gps);
        description = findViewById(R.id.description_GPS);
        coordinatesView = findViewById(R.id.coordinates);
        init_gps = findViewById(R.id.start_location);
        lengthView = findViewById(R.id.length_view);

        // Initialisation of the SeekBar (for selecting maximal distance available for the user)
        selected_length = findViewById(R.id.select_length);
        step = 1;
        min = 1;
        max = 100;
        selected_length.setMax((max - min / step));
        selected_length.setProgress(step);
            // If you want to take the number of km selected, just use progress as a int
        selected_length.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                double value = min + (progress * step);
                lengthView.setText("Selected length : " + progress + "km");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Initialisation of the initial location button

        init_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainGPSApp.this, new String [] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION_PERMISSION);

                } else {
                    getCurrentLocation();
                }
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_LOCATION_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Permission_denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getCurrentLocation() {

        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.getFusedLocationProviderClient(MainGPSApp.this).requestLocationUpdates(locationRequest, new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                LocationServices.getFusedLocationProviderClient(MainGPSApp.this).removeLocationUpdates(this);
                if (locationResult != null && locationResult.getLocations().size() > 0) {
                    int latestLocationIndex = locationResult.getLocations().size() -1;
                    double latitude = locationResult.getLocations().get(latestLocationIndex).getLatitude();
                    double longitude = locationResult.getLocations().get(latestLocationIndex).getLongitude();
                    coordinatesView.setText(String.format("Lat.: %s | Long.: %s", latitude, longitude));
                }
            }
    }, Looper.getMainLooper());
}





    // Once every actions is initialized, you can move to the second part of the activity
    private void launch_Calculator() {
        launch_btn = findViewById(R.id.launch_app);
        launch_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGPSApp.this, GPS_Calculator.class);
                startActivity(intent);
            }
        });
    }

    // If you want to go out from this app and go back to the Main Menu
    private void back_to_menu() {
        menu_btn = findViewById(R.id.menu_button);
        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainGPSApp.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
