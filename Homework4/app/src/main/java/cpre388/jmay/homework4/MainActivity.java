package cpre388.jmay.homework4;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        setContentView(R.layout.activity_main);
    }

    public void settingsButtonClick(View v) {
        Intent i = new Intent(this, SettingsActivity.class);
        startActivity(i);
    }

    public void runButtonClick(View v) {
        Intent i = new Intent(this, RunActivity.class);
        startActivity(i);
    }
}
