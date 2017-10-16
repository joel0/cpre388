package cpre388.jmay.geoquiz;

/**
 * Created by jmay on 2017-10-16.
 */

/**
 * A question and a set of answers, one of which is correct.
 */
public class Question {
    /**
     * The question text.
     */
    private String mQuestion;
    /**
     * The set of all answers.
     */
    private String[] mAnswers;
    /**
     * The index of the correct answer.
     */
    private int mCorrectAnswer;

    public Question(String question, int correctAnswerIndex, String[] answers) {
        if (correctAnswerIndex >= answers.length || correctAnswerIndex < 0) {
            throw new IllegalArgumentException(
                    "The correct answer index must be within the bounds of the answers array.");
        }
        mQuestion = question;
        mAnswers = answers;
        mCorrectAnswer = correctAnswerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public String[] getAnswers() {
        return mAnswers;
    }

    public int getCorrectAnswerIndex() {
        return mCorrectAnswer;
    }
}
