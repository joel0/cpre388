package layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cpre388.jmay.geoquiz.AnswerListActivity;
import cpre388.jmay.geoquiz.Question;
import cpre388.jmay.geoquiz.R;

public class QuestionListFragment extends Fragment {
    private RecyclerView mQuestionRecyclerView;
    private QuestionAdapter mAdapter;

    private Question[] mQuestions;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onQuestionSelected(int questionIndex);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestions = Question.getInstance();
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_question_list, container, false);
        mQuestionRecyclerView = (RecyclerView) v.findViewById(R.id.question_recycler_view);
        mQuestionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return v;
    }

    private void updateUI() {
        mAdapter = new QuestionAdapter(mQuestions);
        mQuestionRecyclerView.setAdapter(mAdapter);
    }

    public class QuestionHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mQuestionText;
        private int mQuestionIndex;

        public QuestionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_question, parent, false));

            mQuestionText = (TextView) itemView.findViewById(R.id.question_title);
            itemView.setOnClickListener(this);
        }

        public void bind(Question question, int questionIndex) {
            mQuestionIndex = questionIndex;
            mQuestionText.setText(question.getQuestion());
        }

        @Override
        public void onClick(View v) {
            mCallbacks.onQuestionSelected(mQuestionIndex);
        }
    }

    public class QuestionAdapter extends RecyclerView.Adapter<QuestionHolder> {
        private Question[] mQuestions;

        public QuestionAdapter(Question[] questions) {
            mQuestions = questions;
        }

        @Override
        public QuestionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new QuestionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(QuestionHolder holder, int position) {
            holder.bind(mQuestions[position], position);
        }

        @Override
        public int getItemCount() {
            return mQuestions.length;
        }
    }
}
