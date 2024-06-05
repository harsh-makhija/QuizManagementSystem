package csc1035.project2;

import javax.persistence.*;

@Entity(name = "Quizzes")
/**
 * This class is used to represent each quiz object entered by the user that will be added to the Quizzes table in the database
 * as a record. It contains getters and setters as well as a method checking if it has the same name as another quiz.
 *
 * @author Team 09
 */
public class Quiz {
    @Id
    private String quizName;

    @Column
    private int numberOfQuestions;

    @Column
    private int score;

    /**
     * The constructor connects the parameters of an entered quiz with the attributes of a quiz class.
     * @param quizName represents the name of a quiz
     * @param numberOfQuestions represents the number of questions in a quiz
     * @param score represents the score gotten on a quiz, it has an initial value of 0
     */
    public Quiz(String quizName, int numberOfQuestions, int score) {
        this.quizName = quizName;
        this.numberOfQuestions = numberOfQuestions;
        this.score = score;
    }

    public Quiz() {

    }

    /**
     * This method returns the name of a quiz
     * @return name of quiz
     */
    public String getQuizName() {
        return quizName;
    }

    /**
     * This method sets the name of a quiz to whatever is passed in
     * @param quizName represents the new name of a quiz
     */
    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    /**
     * This method returns the number of questions in a quiz.
     * @return number of questions in a quiz
     */
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    /**
     * This method sets the number of questions in the quiz to whatever is passed in.
     * @param numberOfQuestions represents the new number of questions in the quiz
     */
    public void setNumberOfQuestions(int numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    /**
     * This method returns the score of a quiz.
     * @return score of a quiz
     */
    public int getScore() {
        return score;
    }

    /**
     * This method sets the score of the quiz to whatever is passed in.
     * @param score represents the new score of the quiz
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * This method checks if a quiz object has the same name as another quiz object and ignores things like spaces and the case.
     * @param quizName represents the name of the quiz passed into the method
     * @return true if the two quizzes have the same name and false if they don't
     */
    public boolean sameQuiz(String quizName) {
        if (this.quizName.equalsIgnoreCase(quizName)) {
            return true; // If the two quizzes are the same then true is returned
        }
        return false; // If the two quizzes are not the same then false is returned
    }
}
