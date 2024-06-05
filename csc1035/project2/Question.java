package csc1035.project2;
import javax.persistence.*;

@Entity(name = "Questions")
/**
 * This class is used to represent each question object that will be entered as a record into the Questions table in the
 * databases. It has all the getters and setters for its attributes and has a method checking if a question is the same as
 * a question entered by the user.
 *
 * @author Team 09
 */
public class Question {

    // Creates the attributes used for each object and that are written to the Questions table in the database
    @Id
    private String question;

    @Column
    private String answer;

    @Column
    private boolean isItMultipleChoice; // true = MCQ   false = SAQ

    @Column
    private String questionTopic;

    @Column
    private boolean isCorrect; // true = answered correctly, false = answered incorrectly

    /**
     * The constructor connects the parameters of an entered question with the attributes of a question class.
     * @param question represents the question itself
     * @param answer represents the answer to this question
     * @param isItMultipleChoice represents whether the question is multiple choice or short answer
     * @param questionTopic represents the question topic
     * @param isCorrect represents whether the question was answered correctly or not
     */
    public Question(String question, String answer, boolean isItMultipleChoice, String questionTopic, boolean isCorrect) {
        this.question = question;
        this.answer = answer;
        this.isItMultipleChoice = isItMultipleChoice;
        this.questionTopic = questionTopic;
        this.isCorrect = isCorrect;
    }
    public Question() {

    }

    /**
     * This method returns the question itself.
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * This method sets the question to whatever is passed in.
     * @param question represents the new version of the question itself
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * This method returns the answer of a question
     * @return the answer of a question
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * This method sets the question to whatever is passed in.
     * @param answer represents the new answer to the question
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * This method returns whether the question is multiple choice or not.
     * @return whether the question is multiple choice or not.
     */
    public boolean isItMultipleChoice() {
        return isItMultipleChoice;
    }

    /**
     * This method sets the question to whatever is passed in.
     * @param isItMultipleChoice represents whether the question is multiple choice or short answer
     */
    public void setItMultipleChoice(boolean isItMultipleChoice) {
        this.isItMultipleChoice = isItMultipleChoice;
    }

    /**
     * This method returns the topic of the question.
     * @return the question topic
     */
    public String getQuestionTopic() {
        return questionTopic;
    }

    /**
     * This method sets the question to whatever is passed in.
     * @param questionTopic represents the new topic of the question
     */
    public void setQuestionTopic(String questionTopic) {
        this.questionTopic = questionTopic;
    }

    /**
     * This method returns the whether the question is answered correctly or not.
     * @return whether the question is answered correctly or not
     */
    public boolean isCorrect() {
        return isCorrect;
    }

    /**
     * This method sets the question to whatever is passed in.
     * @param correct represents whether the question is answered correctly or not
     */
    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    /**
     * This method returns the attributes of a question object.
     * @return string representation of the question
     */
    @Override
    public String toString() {
        return "{question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", isItMultipleChoice=" + isItMultipleChoice +
                ", questionTopic='" + questionTopic + '\'' +
                ", isCorrect=" + isCorrect +
                '}';
    }

    /**
     * Checks if a question passed in is the same as the question of this object, the comparison ignores cases and spaces
     * @param questionName represents the question entered by the user
     * @return true if the question entered is the same as this question and false if it isn't
     */
    public boolean sameQuestion(String questionName) {
        if (this.question.equalsIgnoreCase(questionName)) {
            return true; // If the two questions are the same then true is returned
        }
        return false; // If the two questions are the same then false is returned
    }

    /**
     * Checks if the answer given to a question is correct, if it is then true is returned and the comparison of the answers
     * ignores things like the case and spaces.
     * @param userAnswer represents the answer entered by the user
     * @return true if the question entered was correct and false if it wasn't
     */
    public boolean isRightAnswer(String userAnswer) {
        if (this.answer.replaceAll(" ","").equalsIgnoreCase(userAnswer.replaceAll(" ",""))) {
            this.isCorrect = true;
            return true; // If the user's answer is the same as the question answer then isCorrect becomes true and true is returned
        }
        else {
            this.isCorrect = false;
            return false; // If the user's answer is not the same as the question answer then isCorrect becomes false and false is returned
        }
    }
}
