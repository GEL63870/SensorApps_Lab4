package pl.com.pwr.mysensorapp_lab4_257160;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;


public class GameActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "MainActivity";
    private ImageView image;

    private SensorManager manager;
    private Sensor accelerometer;
    private int x, y;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.game_activity);
        image = findViewById(R.id.ballView);

        manager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        Log.d(TAG, "onCreate: Registered accelerometer listener");


    }

    @Override
    public void onSensorChanged(SensorEvent event){
        Log.d(TAG, "onSensorChange : X" + event.values[0] + "Y: " + event.values[1] + "Z: " + event.values[2]);

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // We multiply by 4 in order to be a bit more funny
            x = x - (int) event.values[0] * 4;
            y = y + (int) event.values[1] * 4;
            image.setY(y);
            image.setX(x);
        }
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        if (x < 0) {
            manager.unregisterListener(this);
            Intent intent = new Intent (GameActivity.this, MainSensorGame.class);
            startActivity(intent);
        }

        if (y < 0) {
            manager.unregisterListener(this);
            Intent intent = new Intent (GameActivity.this, MainSensorGame.class);
            startActivity(intent);
        }

        if (y > (height - 360)) {
            manager.unregisterListener(this);
            Intent intent = new Intent (GameActivity.this, MainSensorGame.class);
            startActivity(intent);
        }

        if (x > (width-200)) {
            manager.unregisterListener(this);
            Intent intent = new Intent (GameActivity.this, MainSensorGame.class);
            startActivity(intent);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){
        // Nothing to do here for the moment
    }
    @Override
    protected void onResume() {
        super.onResume();
        manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

    }

    @Override
    protected void onPause(){
        super.onPause();
        manager.unregisterListener(this);
    }
}