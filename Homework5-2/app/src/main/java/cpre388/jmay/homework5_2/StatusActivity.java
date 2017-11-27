package cpre388.jmay.homework5_2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
    }

    public void handlePlay(View v) {
        PlayerService.startActionPlay(this);
    }

    public void handlePause(View v) {
        PlayerService.startActionPause(this);
    }

    public void handleStop(View v) {
        PlayerService.startActionStop(this);
    }
}
