package pl.com.pwr.mysensorapp_lab4_257160;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
            x -= (int) event.values[0];
            y += (int) event.values[1];
            image.setY(y);
            image.setX(x);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor,int accuracy){
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