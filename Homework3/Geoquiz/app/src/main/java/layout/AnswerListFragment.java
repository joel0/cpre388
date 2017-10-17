package layout;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cpre388.jmay.geoquiz.Question;
import cpre388.jmay.geoquiz.R;

import static cpre388.jmay.geoquiz.AnswerListActivity.EXTRA_QUESTION_INDEX;

/**
 * A simple {@link Fragment} subclass.
 */
public class AnswerListFragment extends Fragment {
    private Question mQuestion;
    private TextView mQuestionText;
    private LinearLayout mAnswerLinearLayout;

    public AnswerListFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int questionIndex = getArguments().getInt(EXTRA_QUESTION_INDEX);
            mQuestion = Question.getInstance()[questionIndex];
        } else {
            // TODO exception
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_answer_list, container, false);
        mQuestionText = (TextView) v.findViewById(R.id.question_title_answer_fragment);
        mAnswerLinearLayout = (LinearLayout) v.findViewById(R.id.answer_linear_layout);

        updateUI();

        return v;
    }


    private void updateUI() {
        mQuestionText.setText(mQuestion.getQuestion());
        for (int i = 0; i < mQuestion.getAnswers().length; i++) {
            Button newButton = new Button(getContext());
            newButton.setText(mQuestion.getAnswer(i));
            newButton.setOnClickListener(new AnswerClickListener(i));
            mAnswerLinearLayout.addView(newButton);
        }
    }

    private class AnswerClickListener implements View.OnClickListener {
        private int mAnswerIndex;

        public AnswerClickListener(int answerIndex) {
            mAnswerIndex = answerIndex;
        }

        @Override
        public void onClick(View v) {
            if (mQuestion.checkAnswer(mAnswerIndex)) {
                Toast.makeText(getContext(), "Correct", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getContext(), "Incorrect", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}
