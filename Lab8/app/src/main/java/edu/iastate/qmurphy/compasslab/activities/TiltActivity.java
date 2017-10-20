package edu.iastate.qmurphy.compasslab.activities;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import edu.iastate.qmurphy.compasslab.R;
import edu.iastate.qmurphy.compasslab.interfaces.SensorUpdateCallback;
import edu.iastate.qmurphy.compasslab.models.BetterCompass;
import edu.iastate.qmurphy.compasslab.models.FlatCompass;
import edu.iastate.qmurphy.compasslab.models.TiltCalculator;

public class TiltActivity extends AppCompatActivity implements SensorUpdateCallback, View.OnClickListener {
    private TiltCalculator mTilt;
    private ImageView mArrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        mTilt = null; // TODO Instantiate a TiltCalculator object

        mArrow = (ImageView) findViewById(R.id.image);
        mArrow.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.arrow));

        Button btn1 = (Button) findViewById(R.id.btn1);
        btn1.setText("Flat Compass");
        btn1.setOnClickListener(this);

        Button btn2 = (Button) findViewById(R.id.btn2);
        btn2.setText("Better Compass");
        btn2.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mTilt.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mTilt.stop();
    }

    @Override
    public void update(float orientation) {
        // TODO Rotate arrow to orientation
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn1) {
            startActivity(new Intent(this, FlatCompassActivity.class));
        }
        else if (v.getId() == R.id.btn2) {
            startActivity(new Intent(this, BetterCompassActivity.class));
        }
    }
}
