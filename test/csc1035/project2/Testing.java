package test.csc1035.project2;

import csc1035.project2.DatabaseManager;
import csc1035.project2.Question;
import csc1035.project2.Quiz;

import java.util.ArrayList;

public class Testing {
    public static void main(String[] args) {
        questionExistsTest();
        questionNotExistTest();
        quizExistTest();
        quizNotExistTest();
        createAndDeleteQuizTest();
        notDeletedQuizTest();
        createAndDeleteQuestionTest();
        notDeletedQuestionTest();
        updatedQuestionAnswerTest();
        updatedQuestionCorrectnessTest();
        readQuizTest();
        notReadQuizTest();
    }

    /**
     * This method tests if the program can tell if a question is already in the database, will output test passed or
     * test not passed
     */
    public static void questionExistsTest(){
        System.out.println("Test 1: Tests if program can see if question is already in database");
        if (DatabaseManager.doesQuestionExist("What is 2+6?")){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if program can see if question is not in database, will output test passed or test not passed
     */
    public static void questionNotExistTest(){
        System.out.println("\nTest 2: Tests if program can see if question is not in database");
        if (!DatabaseManager.doesQuestionExist("What is 2+8")){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if program can see if quiz is in the database, will output test passed or test not passed
     */
    public static void quizExistTest(){
        System.out.println("\nTest 3: Tests if program can see if quiz is in the database");
        if(DatabaseManager.doesQuizExist("quiz 3")){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if program can see if quiz is in the database, will output test passed or test not passed
     */
    public static void quizNotExistTest(){
        System.out.println("\nTest 4: Tests if program can see if quiz is in the database");
        if(!DatabaseManager.doesQuizExist("quiz99")){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if a quiz can be created and then deleted from the database, will output test passed or
     * test not passed
     */
    public static void createAndDeleteQuizTest(){
        System.out.println("\nTest 5: Tests if a quiz can be created and then deleted from database");
        Quiz quiz = new Quiz("quiz test", 15, 0);
        ArrayList<Question> allQuestions = DatabaseManager.getAllQuestions();
        ArrayList<Question> generatedQuizList = DatabaseManager.generateQuiz(10,true, false,
                false, false, "",allQuestions);

        System.out.println("Creating quiz test ...");
        if (DatabaseManager.addQuizToDB(quiz, generatedQuizList)){
            System.out.println("Test passed \u2713 - test quiz created");
        }
        else{
            System.out.println("Test not passed \u2717 - test quiz not created");
        }
        System.out.println("Deleting quiz test ...");
        if(DatabaseManager.deleteQuizInDB("quiz test")){
            System.out.println("Test passed \u2713 - test quiz deleted");
        }
        else{
            System.out.println("Test not passed \u2717 - test quiz not deleted");
        }

    }

    /**
     * This method tests if a quiz has not been deleted from the database, will output test passed or test not passed
     */
    public static void notDeletedQuizTest(){
        System.out.println("\nTest 6: Tests if a quiz is not deleted from database");
        if (!DatabaseManager.deleteQuizInDB("quiz99")){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if a question can be created and then deleted from the database, will output test passed or
     * test not passed
     */
    public static void createAndDeleteQuestionTest(){
        System.out.println("\nTest 7: Tests if a question can be created and then deleted from the database");
        Question question = new Question("What is the answer to life?", "42", false, "deep thought", true);

        System.out.println("Creating test question ...");
        if(DatabaseManager.addQuestionToDB(question)){
            System.out.println("Test passed \u2713 - test question created");
        }
        else{
            System.out.println("Test not passed \u2717 - test question not created");
        }

        System.out.println("Deleting test question test ...");
        if(DatabaseManager.deleteQuestionInDB("What is the answer to life?")){
            System.out.println("Test passed \u2713 - test question deleted");
        }
        else{
            System.out.println("Test not passed \u2717 - test question not deleted");
        }
    }

    /**
     * This method tests if a question has not been deleted from the database, will output test passed or
     * test not passed
     */
    public static void notDeletedQuestionTest(){
        System.out.println("\nTest 8: Tests if a question is not deleted from the database");
        if(!DatabaseManager.deleteQuestionInDB("What is 16 * 72")){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if a question's answer can be updated in the database, will output test passed or test
     * not passed
     */
    public static void updatedQuestionAnswerTest(){
        System.out.println("\nTest 9: Tests if a question's answer is updated in the database");
        if(DatabaseManager.updateQuestionInDB("What is 2+6?", "Answer", "8", true)){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if a question's correctness can be updated in the database, will output test passed or test
     * not passed
     */
    public static void updatedQuestionCorrectnessTest(){
        System.out.println("\nTest 10: Tests if a question's correctness is updated in the database");
        if(DatabaseManager.updateQuestionInDB("What is 2+6?", "Correctness", "8", true)){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if the program can read if a quiz has questions in it, will output test passed or test
     * not passed
     */
    public static void readQuizTest(){
        System.out.println("\nTest 11: Tests if the program can read a quiz with questions in it from the database");
        if(DatabaseManager.readQuizInDB("quiz 3").size() >= 1){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }

    /**
     * This method tests if the program doesn't have any questions in it, will output test passed or test not passed
     */
    public static void notReadQuizTest(){
        System.out.println("\nTest 12: Tests if the program can read if a quiz doesn't have any questions in it");
        if(DatabaseManager.readQuizInDB("quiz3").size() == 0){
            System.out.println("Test passed \u2713");
        }
        else{
            System.out.println("Test not passed \u2717");
        }
    }
}
