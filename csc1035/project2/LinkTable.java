package csc1035.project2;

import javax.persistence.*;

@Entity(name = "QuizQuestions")
/**
 * This class represents the attributes of each record in the QuizQuestions table which is the linking table that contains
 * a quiz and a corresponding question in each of its records. It contains getters and setters from each of its attributes.
 */
public class LinkTable {

    // Creates the attributes used for each object and that are written to the QuizQuestions table in the database
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private int quizQuestionID;

    @Column
    private String quizName;

    @Column
    private String question;

    /**
     * The constructor connects the parameters of an entered object with the attributes of a linking table class.
     * @param quizName represents the name of the quiz
     * @param question represents the question in that quiz
     */
    public LinkTable(String quizName, String question) {
        this.quizName = quizName;
        this.question = question;
    }

    public LinkTable(){

    }

    /**
     * This method returns the ID (primary key) of a record in the table linking the quiz and its corresponding question.
     * @return the ID of a linking table
     */
    public int getQuizQuestionID() {
        return quizQuestionID;
    }

    /**
     * This method changes the ID of a record in the linking table between quizzes and questions to the value passed in.
     * @param quizQuestionID represents the new value of that ID
     */
    public void setQuizQuestionID(int quizQuestionID) {
        this.quizQuestionID = quizQuestionID;
    }

    /**
     * This method returns the name of a quiz from a record in the linking table.
     * @return the name of a quiz
     */
    public String getQuizName() {
        return quizName;
    }

    /**
     * This method changes the name of a quiz in the linking table to the value passed in.
     * @param quizName represents the new name of the quiz.
     */
    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    /**
     * This method returns a question from a particular record in the linking table.
     * @return the question from a record
     */
    public String getQuestion() {
        return question;
    }

    /**
     * This method changes the question of a record in the linking table.
     * @param question represents tbe new value of the question in a certain record
     */
    public void setQuestion(String question) {
        this.question = question;
    }
}
