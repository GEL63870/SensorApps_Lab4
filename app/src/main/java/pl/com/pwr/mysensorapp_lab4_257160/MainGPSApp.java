package pl.com.pwr.mysensorapp_lab4_257160;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainGPSApp extends AppCompatActivity {

    private TextView textView;
    private Button menu_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_gps_app);

        textView = findViewById(R.id.welcome_gps);

        back_to_menu();

    }

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
