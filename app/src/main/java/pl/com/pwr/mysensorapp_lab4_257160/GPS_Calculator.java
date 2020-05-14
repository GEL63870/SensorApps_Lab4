package pl.com.pwr.mysensorapp_lab4_257160;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GPS_Calculator extends AppCompatActivity {

    private Button menu_btn;
    private TextView textView, advanceView;
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_calculator);

        back_to_menu();

        textView = findViewById(R.id.welcome_calculator);
        advanceView = findViewById(R.id.advancement);

        mProgressBar = findViewById(R.id.progressBar);

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
