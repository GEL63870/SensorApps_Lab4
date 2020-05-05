package pl.com.pwr.mysensorapp_lab4_257160;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainSensorList extends AppCompatActivity {

    private TextView textView;
    private Button menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_sensor_list);
        textView = findViewById(R.id.welcome_list);

        back_to_menu();


        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        ListView list = (ListView) findViewById((R.id.sensors_list));
        list.setAdapter(new SensorListAdapter(this, R.layout.item_sensor, sensors));
    }

    private void back_to_menu() {
        menu_btn = findViewById(R.id.menu_button);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSensorList.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}