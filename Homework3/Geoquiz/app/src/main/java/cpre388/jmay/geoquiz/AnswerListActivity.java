package cpre388.jmay.geoquiz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import layout.AnswerListFragment;
import layout.QuestionListFragment;

public class AnswerListActivity extends SingleFragmentActivity {
    public static final String EXTRA_QUESTION_INDEX = "com.cpre388.jmay.geoquiz.question";

    @Override
    protected Fragment createFragment() {
        return new AnswerListFragment();
    }
}
