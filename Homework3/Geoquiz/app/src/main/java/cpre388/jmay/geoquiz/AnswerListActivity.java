package cpre388.jmay.geoquiz;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import layout.AnswerListFragment;
import layout.QuestionListFragment;

public class AnswerListActivity extends SingleFragmentActivity {
    public static final String EXTRA_QUESTION_INDEX = "com.cpre388.jmay.geoquiz.question";

    @Override
    protected Fragment createFragment() {
        Fragment fragment = new AnswerListFragment();
        Bundle args = new Bundle();
        int questionIndex = getIntent().getIntExtra(EXTRA_QUESTION_INDEX, 0);
        args.putInt(EXTRA_QUESTION_INDEX, questionIndex);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        setResult(RESULT_OK);
        finish();
    }
}
