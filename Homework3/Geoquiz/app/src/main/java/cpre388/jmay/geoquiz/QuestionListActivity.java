package cpre388.jmay.geoquiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import layout.AnswerListFragment;
import layout.QuestionListFragment;

/**
 * Created by jmay on 2017-10-16.
 */

public class QuestionListActivity extends SingleFragmentActivity
        implements QuestionListFragment.Callbacks {
    private boolean mLand;
    private int mQuestionIndex;

    private static final String QUESTION_INDEX_KEY = "com.cpre388.jmay.geoquiz.current_q_index";
    private static final int ANSWER_LIST_REQUEST_CODE = 1;
    private static final String TAG = "QuestionListActivity";

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected Fragment createFragment() {
        return new QuestionListFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(QUESTION_INDEX_KEY)) {
            mQuestionIndex = savedInstanceState.getInt(QUESTION_INDEX_KEY);
            Log.i(TAG, "Loaded question: " + mQuestionIndex);
        } else {
            mQuestionIndex = -1;
            Log.i(TAG, "No saved question");
        }

        super.onCreate(savedInstanceState);

        mLand = findViewById(R.id.fragment_detail_container) != null;
        updateUI();
    }

    private void updateUI() {
        showQuestion(mQuestionIndex);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.i(TAG, "Saving question: " + mQuestionIndex);
        outState.putInt(QUESTION_INDEX_KEY, mQuestionIndex);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ANSWER_LIST_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Log.i(TAG, "Clearing question");
            mQuestionIndex = -1;
        }
    }

    @Override
    public void onQuestionSelected(int questionIndex) {
        mQuestionIndex = questionIndex;
        showQuestion(questionIndex);
    }

    private void showQuestion(int questionIndex) {
        if (!mLand) {
            if (mQuestionIndex > -1) {
                Intent intent = new Intent(this, AnswerListActivity.class);
                intent.putExtra(AnswerListActivity.EXTRA_QUESTION_INDEX, questionIndex);
                startActivityForResult(intent, ANSWER_LIST_REQUEST_CODE);
            }
        } else {
            if (mQuestionIndex < 0) {
                Fragment detailFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_detail_container);
                if (detailFragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .remove(detailFragment)
                            .commit();
                }
                Log.i(TAG, "Removing all views from question");
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
}
