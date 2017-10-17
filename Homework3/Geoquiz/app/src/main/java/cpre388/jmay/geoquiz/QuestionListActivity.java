package cpre388.jmay.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import layout.AnswerListFragment;
import layout.QuestionListFragment;

/**
 * Created by jmay on 2017-10-16.
 */

public class QuestionListActivity extends SingleFragmentActivity
        implements QuestionListFragment.Callbacks {
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

    @Override
    public void onQuestionSelected(int questionIndex) {
        if (findViewById(R.id.fragment_detail_container) == null) {
            Intent intent = new Intent(this, AnswerListActivity.class);
            intent.putExtra(AnswerListActivity.EXTRA_QUESTION_INDEX, questionIndex);
            startActivity(intent);
        } else {
            Fragment newDetail = new AnswerListFragment();
            Bundle args = new Bundle();
            args.putInt(AnswerListActivity.EXTRA_QUESTION_INDEX, questionIndex);
            newDetail.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_detail_container, newDetail)
                    .commit();
        }
    }
}
