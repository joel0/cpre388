package cpre388.jmay.lab4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText mEditText;
    Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
        mButton = findViewById(R.id.button);
    }

    public void doClick(View v) {
        Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("theString", mEditText.getText().toString());
        startActivity(i);
    }
}
