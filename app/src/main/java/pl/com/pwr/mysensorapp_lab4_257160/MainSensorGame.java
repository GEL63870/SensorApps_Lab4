package pl.com.pwr.mysensorapp_lab4_257160;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainSensorGame extends AppCompatActivity {

    private TextView textView;
    private Button menu_btn, start_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_sensor_game);

        textView = findViewById(R.id.welcome_game);

        back_to_menu();
        start_to_play();

    }

    private void start_to_play() {
        start_btn = findViewById(R.id.start_game);

        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (MainSensorGame.this, GameActivity.class);
                startActivity(intent);
            }
        });
    }

    private void back_to_menu() {
        menu_btn = findViewById(R.id.menu_button);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainSensorGame.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}