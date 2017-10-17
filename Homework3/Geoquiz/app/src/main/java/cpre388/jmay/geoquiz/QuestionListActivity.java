package cpre388.jmay.geoquiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import layout.QuestionListFragment;

/**
 * Created by jmay on 2017-10-16.
 */

public class QuestionListActivity extends SingleFragmentActivity {
    private Question[] mQuestions;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment createFragment() {
        Fragment fragment = new QuestionListFragment();
        return fragment;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mQuestions = Question.getSampleQuestions();

        super.onCreate(savedInstanceState);
    }
}
