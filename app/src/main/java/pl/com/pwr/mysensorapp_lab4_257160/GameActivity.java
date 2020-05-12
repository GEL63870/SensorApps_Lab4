package pl.com.pwr.mysensorapp_lab4_257160;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;


public class GameActivity extends Activity implements SensorEventListener {

    private  int diameter;
    private int x;
    private int y;
    private ShapeDrawable bubble;

    private LinearLayout layout;
    private ImageView i;



    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createBubble();

        i.setImageResource(R.drawable.my_ball);
        i.setAdjustViewBounds(true);
        i.setLayoutParams(new Gallery.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.addView(i);
        layout.setGravity(Gravity.CENTER);
        setContentView(layout);
    }

    //public GameActivity(Context context) {
   //     super(context);
    //    createBubble();
   // }

    private void createBubble() {
        x=100;
        y=100;
        diameter = 100;
        bubble = new ShapeDrawable((new OvalShape()));
        bubble.setBounds(x, y, x + diameter, y + diameter);
        bubble.getPaint().setARGB(1,183, 26, 81);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Nothing to do here (at least for the moment)
    }
}
