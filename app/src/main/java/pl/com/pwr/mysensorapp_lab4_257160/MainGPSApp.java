package pl.com.pwr.mysensorapp_lab4_257160;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainGPSApp extends AppCompatActivity {

    private TextView textView, coordinatesView, lengthView, description;
    private Button menu_btn, init_gps, launch_btn;
    private SeekBar selected_length;
    private int min, max, step;

    private LocationManager locationManager;
    private LocationListener locationListener;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_gps_app);

        // Method that can be launch from onCreateMethod
        back_to_menu();
        launch_Calculator();
        configureButton();

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
        selected_length.setMax((max-min/step));
        selected_length.setProgress(step);
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

        // Initialisation of the location Manager (to find Initial Coordinates) :
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {

            // Call whenever the location is updated : changed only once TextView
            @Override
            public void onLocationChanged(Location location) {
                coordinatesView.setText("Your initial coordinates are : " + "\n" + location.getLatitude() + " " + location.getLongitude());
            }
            // Never used
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }
            // Never used
            @Override
            public void onProviderEnabled(String provider) {

            }

            // Check if the GPS is turn off or not : if it is, it ask to turn it on
            @Override
            public void onProviderDisabled(String provider) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };


    }

    // Ask for Permission Request in the manifest and if it was clicked in the app by the user
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                break;
            default:
                break;
        }
    }

    // This configure Button method 1st ask if the location permission are available on the phone
    // Then we activate the OnClickListener to give location update to the user if accees are OK
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void configureButton() {
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);

                init_gps.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {
                        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}, 10);
                            }
                        }
                        locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                    }
                });
            }
        }
    }

    // Once every actions is initialized, you can moove to the second part of the activity
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
