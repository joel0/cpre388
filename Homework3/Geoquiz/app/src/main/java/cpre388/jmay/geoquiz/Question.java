package cpre388.jmay.geoquiz;

/**
 * Created by jmay on 2017-10-16.
 */

import java.util.ArrayList;

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

    private static Question[] mSintletonQuestions = null;

    public Question(String question, int correctAnswerIndex, String... answers) {
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

    public String getAnswer(int index) {
        return mAnswers[index];
    }

    public int getCorrectAnswerIndex() {
        return mCorrectAnswer;
    }

    public boolean checkAnswer(int answerIndex) {
        return answerIndex == mCorrectAnswer;
    }

    public static Question[] getSampleQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("What is the capital of Iowa?",
                0, "Des Moines", "Ames", "Cedar Rapids", "Iowa City"));
        questions.add(new Question("What river forms part of Washingon State's border?",
                1, "Mississippi", "Columbia", "Skunk River", "None"));
        questions.add(new Question("What is the tallest mountain in the United States?",
                2, "Mount St. Helens", "Mount Rainier", "Denali", "Mount Adams"));
        questions.add(new Question("California borders what ocean?",
                2, "Indian", "Atlantic", "Pacific", "Arctic", "Southern"));
        questions.add(new Question("What is the United State's northern neighbour?",
                4, "Mexico", "United States", "Africa", "Britain", "Canada"));
        questions.add(new Question("Who explored the west coast of the US?",
                3, "Tom and Jerry", "Joel and Matthew", "John and John", "Lewis and Clark", "Wilbur and Orville Wright"));
        questions.add(new Question("What city is Iowa State University in?",
                1, "Iowa City", "Ames", "Hawkeye Towne", "Des Moines", "DC"));
        return questions.toArray(new Question[questions.size()]);
    }

    public static Question[] getInstance() {
        if (mSintletonQuestions == null) {
            mSintletonQuestions = getSampleQuestions();
        }
        return mSintletonQuestions;
    }

    public static String[] questionsToStringArray(Question[] questions) {
        ArrayList<String> out = new ArrayList<>();
        for (Question question :
                questions) {
            out.add(question.getQuestion());
        }
        return out.toArray(new String[out.size()]);
    }
}
