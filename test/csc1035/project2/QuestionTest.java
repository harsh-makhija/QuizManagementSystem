package test.csc1035.project2; 

import csc1035.project2.Question;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;
import java.util.ArrayList;


/** 
* Question Tester. 
* 
* @author <Authors name> 
* @since <pre>3月 21, 2022</pre> 
* @version 1.0 
*/ 
public class QuestionTest {

    @Before
    public void before() throws Exception {
        System.out.println("before");
    }

    @After
    public void after() throws Exception {
        System.out.println("after");
    }

    /**
     * Method: getQuestion()
     */


    @Test
    public void testGetQuestion() throws Exception {
        Question question = new Question();
        question.setQuestion("What is 3+3?, What is 3+4?, What is 5+6?, What is 8+0?, What is 5+3?, What is 2+6?");
        System.out.println(question.getQuestion());
//TODO: Test goes here... 
    }

    /**
     * Method: setQuestion(String question)
     */
    @Test
    public void testSetQuestion() throws Exception {
        Question question = new Question();
        question.setQuestion("What is 3+3?, What is 3+4?, What is 5+6?, What is 8+0?, What is 5+3?, What is 2+6?");
//TODO: Test goes here... 
    }

    /**
     * Method: getAnswer()
     */
    @Test
    public void testGetAnswer() throws Exception {
        Question question = new Question();
        question.setAnswer("6, 7, 11, 8, 8, 8");
        System.out.println(question.getAnswer());
//TODO: Test goes here... 
    }

    /**
     * Method: setAnswer(String answer)
     */
    @Test
    public void testSetAnswer() throws Exception {
        Question question = new Question();
        question.setAnswer("6, 7, 11, 8, 8, 8");
//TODO: Test goes here... 
    }

    /**
     * Method: isItMultipleChoice()
     */
    @Test
    public void testIsItMultipleChoice() throws Exception {
        Question question = new Question();
        question.isItMultipleChoice();
//TODO: Test goes here... 
    }

    /**
     * Method: setItMultipleChoice(boolean isItMultipleChoice)
     */
    @Test
    public void testSetItMultipleChoice() throws Exception {
        Question question = new Question();
        question.setItMultipleChoice(false);
        System.out.println(false);
//TODO: Test goes here... 
    }

    /**
     * Method: getQuestionTopic()
     */
    @Test
    public void testGetQuestionTopic() throws Exception {
        Question question = new Question();
        question.setQuestionTopic("maths, maths, maths, maths, maths, maths");
        System.out.println(question.getQuestionTopic());
//TODO: Test goes here... 
    }

    /**
     * Method: setQuestionTopic(String questionTopic)
     */
    @Test
    public void testSetQuestionTopic() throws Exception {
        Question question = new Question();
        question.setQuestionTopic("maths, maths, maths, maths, maths, maths");
        System.out.println("maths, maths, maths, maths, maths, maths");
//TODO: Test goes here... 
    }

    /**
     * Method: isCorrect()
     */
    @Test
    public void testIsCorrect() throws Exception {
        Question question = new Question();
        question.isCorrect();
//TODO: Test goes here... 
    }

    /**
     * Method: setCorrect(boolean correct)
     */
    @Test
    public void testSetCorrect() throws Exception {
        Question question = new Question();
        question.setCorrect(true);
        System.out.println(true);
//TODO: Test goes here... 
    }

    /**
     * Method: toString()
     */
    @Test
    public void testToString() throws Exception {
        ArrayList<Question>  list=new ArrayList<>();
        list.add(new Question("What is 3+3?", "6", false, "Maths", true));
        list.add(new Question("What is 3+4?", "7",false, "Maths", true));
        list.add(new Question("What is 5+6?", "11", false, "Maths", true));
        Question question=new Question();
        question.toString();
        System.out.println(list.toString());
//TODO: Test goes here...
    }

    /**
     * Method: sameQuestion(String questionName)
     */
    @Test
    public void testSameQuestion() throws Exception {
        ArrayList<Question>  list=new ArrayList<>();
        list.add(new Question("What is 3+3?", "6", false, "Maths", true));
        list.add(new Question("What is 3+4?", "7",false, "Maths", true));
        list.add(new Question("What is 5+6?", "11", false, "Maths", true));
        Question question=new Question();
        if (question.equals(this.equals(list))) {
           System.out.println(true);
        }else {
            System.out.println(false);
        }
//TODO: Test goes here... 
    }

    /**
     * Method: isRightAnswer(String userAnswer)
     */
    @Test
    public void testIsRightAnswer() throws Exception {
        ArrayList<Question>  list=new ArrayList<>();
        list.add(new Question("What is 3+3?", "6", false, "Maths", true));
        list.add(new Question("What is 3+4?", "7",false, "Maths", true));
        list.add(new Question("What is 5+6?", "11", false, "Maths", true));
        ArrayList<Question>  userAnswer=new ArrayList<>();
        userAnswer.add(new Question("What is 3+3?", "7", false, "Maths", false));
        userAnswer.add(new Question("What is 3+4?", "8",false, "Maths", false));
        userAnswer.add(new Question("What is 5+6?", "20", false, "Maths", false));
        Question question=new Question();
        if (list.equals(userAnswer)) {
            System.out.println(true);
        }
        else {
            System.out.println(userAnswer);
        }

//TODO: Test goes here... 
    }

    /**
     * Method: checkQuestionAnsweredRight()
     */
    @Test
    public void testCheckQuestionAnsweredRight() throws Exception {

//TODO: Test goes here... 
    }
     Question question=new Question();

    @Test
     public void testAdd() throws Exception {
                 question.toString();
             }
             @Test
     public void testDelete() throws Exception {
                 question.toString();
             }

             @Test
     public void testUpdate() throws Exception {
                 question.toString();
             }

             @Test
     public void testQuery() throws Exception {
                 question.toString();
             }
    @Test
     public void testExit() {
                 question.toString();
             }
}

