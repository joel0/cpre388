package com.example.swamy.geoquiz;

/**
 * Created by jmay on 2017-09-10.
 */

class QuizQuestion {
    private String question;
    private int answerIndex;
    private String[] answers;

    public String getQuestion() {
        return question;
    }

    public String getCorrectAnswer() {
        return answers[answerIndex];
    }

    public int getCorrectAnswerIndex() {
        return answerIndex;
    }

    public String[] getAnswers() {
        return answers;
    }

    QuizQuestion(String question, String[] answers, int answerIndex) {
        if (answerIndex < 0 || answerIndex >= answers.length) {
            throw new IllegalArgumentException("Answer index is out of range");
        }
        this.question = question;
        this.answers = answers;
        this.answerIndex = answerIndex;
    }

    static QuizQuestion[] getSampleQuizSet() {
        QuizQuestion[] sampleSet = new QuizQuestion[7];
        sampleSet[0] = new QuizQuestion("What is the capital of Iowa?",
                new String[] {"Des Moines", "Ames", "Cedar Rapids", "Iowa City"},
                0);
        sampleSet[1] = new QuizQuestion("What river forms part of Washingon State's border?",
                new String[] {"Mississippi", "Columbia", "Skunk River", "None"},
                1);
        sampleSet[2] = new QuizQuestion("What is the tallest mountain in the United States?",
                new String[] {"Mount St. Helens", "Mount Rainier", "Denali", "Mount Adams"},
                2);
        sampleSet[3] = new QuizQuestion("California borders what ocean?",
                new String[] {"Indian", "Atlantic", "Pacific", "Arctic", "Southern"},
                2);
        sampleSet[4] = new QuizQuestion("What is the United State's northern neighbour?",
                new String[] {"Mexico", "United States", "Africa", "Britain", "Canada"},
                4);
        sampleSet[5] = new QuizQuestion("Who explored the west coast of the US?",
                new String[] {"Tom and Jerry", "Joel and Matthew", "John and John", "Lewis and Clark", "Wilbur and Orville Wright"},
                3);
        sampleSet[6] = new QuizQuestion("What city is Iowa State University in?",
                new String[] {"Iowa City", "Ames", "Hawkeye Towne", "Des Moines", "DC"},
                1);
        return sampleSet;
    }
}
