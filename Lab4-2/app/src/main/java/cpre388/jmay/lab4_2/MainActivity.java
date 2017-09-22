package cpre388.jmay.lab4_2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends Activity {
    private TextView mTextView;
    ArrayList<TouchInfo> touches = new ArrayList<>();

    private class TouchInfo {
        float x;
        float y;
        int index;

        TouchInfo(float x, float y, int index) {
            this.x = x;
            this.y = y;
            this.index = index;
        }
    }

    private void setTouch(int index, float x, float y) {
        for (TouchInfo ti : touches) {
            if (ti.index == index) {
                ti.x = x;
                ti.y = y;
                return;
            }
        }
        TouchInfo ti = new TouchInfo(x, y, index);
        touches.add(ti);
    }

    private void remTouch(int index) {
        for (TouchInfo ti : touches) {
            if (ti.index == index) {
                touches.remove(ti);
                return;
            }
        }
    }

    private void updateText() {
        String touchStr = "";
        for (TouchInfo ti : touches) {
            touchStr += String.format(Locale.getDefault(), "%d: %f,%f\n", ti.index, ti.x, ti.y);
        }
        mTextView.setText(touchStr);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.textView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //mTextView.setText(mTextView.getText() + "\n" + event.getActionMasked());
        if (event.getActionMasked() == MotionEvent.ACTION_MOVE ||
            event.getActionMasked() == MotionEvent.ACTION_DOWN ||
            event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            // From https://stackoverflow.com/questions/9028357/android-multitouch-second-finger-action-move-ignored
            int pointerCount = event.getPointerCount();
            touches.clear();
            for(int i = 0; i < pointerCount; i++)
            {
                setTouch(event.getPointerId(i), event.getX(i), event.getY(i));
            }
        } else if (event.getActionMasked() == MotionEvent.ACTION_UP ||
                event.getActionMasked() == MotionEvent.ACTION_POINTER_UP ||
                event.getActionMasked() == MotionEvent.ACTION_CANCEL) {
            remTouch(event.getActionIndex());
        } else {
            //mTextView.setText(mTextView.getText() + "\n" + event.getActionMasked());
            Toast.makeText(this, event.getActionMasked(), Toast.LENGTH_LONG).show();
        }
        updateText();
        return true;
    }
}
