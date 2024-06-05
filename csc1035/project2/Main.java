package csc1035.project2;

import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class is the main class for this project and contains the command line interface which talks
 * to the methods within the other classes of the program. This contains interfaces for CRUD operations for both
 * quizzes and questions. Also included are reporting and listing options.
 *
 * @author Team 09
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        boolean exit = false;

        // loop that does not exit until the user chooses to do so.
        while(!exit) {

            System.out.println("Choose an option:\n1| Quiz\n2| Questions\n3| Reporting stats\n4| Read from/write to file \n5| exit");
            String choice = sc.nextLine(); // choices for the user.

            // Quiz options
            if (choice.equals("1")) {
                System.out.println("Quiz options:\n1| Take a quiz\n2| Create a quiz\n3| Delete a quiz\n4| Read a quiz");
                String quizChoice = sc.nextLine(); // quiz choices

                // Take a quiz
                if (quizChoice.equals("1")) {
                    System.out.println("Enter Quiz Name:");
                    String quizName = sc.nextLine();
                    ArrayList<Question> quizQuestions = DatabaseManager.readQuizInDB(quizName); // reads quiz with given name from db and creates an array list of questions.
                    if (quizQuestions.size() < 1) {
                        System.out.println("This quiz has no questions in it or doesn't exist."); // prints if quiz doesnt exist
                    } else {
                        QuizReport.takeQuiz(quizQuestions, quizName); //takes the quiz using the given array list
                        int score = DatabaseManager.readQuizScore(quizName); // gets the quiz score
                        QuizReport.quizReport(score, quizQuestions); // produces a quiz report.
                    }
                }

                // Create a Quiz
                if (quizChoice.equals("2")) {
                    boolean isMultipleChoice = false;
                    boolean chooseCorrectAndIncorrect = true;
                    boolean chooseQuestionType = false;
                    boolean chooseQuestionTopic = true; // parameters needed
                    System.out.println("Enter the length of the quiz:");
                    Scanner sc1 = new Scanner(System.in);
                    String noOfQuestionsStr = sc1.nextLine();
                    int noOfQuestions = Validation.validateInteger(noOfQuestionsStr, "Enter the length of the quiz:");

                    Scanner sc2 = new Scanner(System.in);
                    System.out.println("Choose question type: MCQ/SAQ/BOTH"); //choose question type
                    String questionType = sc2.nextLine();
                    if (questionType.equalsIgnoreCase("MCQ")){
                        isMultipleChoice = true;
                        chooseQuestionType = true;
                    }
                    if (questionType.equalsIgnoreCase("SAQ")){
                        chooseQuestionType = true;
                    }

                    Scanner sc3 = new Scanner(System.in);
                    System.out.println("Choose question topic (type all for 'all' topics):"); // choose question topic or all topics
                    String questionTopic = sc3.nextLine();
                    if (questionTopic.equalsIgnoreCase("all")){
                        chooseQuestionTopic = false;
                    }

                    Scanner sc4 = new Scanner(System.in);
                    System.out.println("Enter quiz name:"); // give the quiz a name
                    String quizName = sc4.nextLine();

                    ArrayList<Question> allQuestions = DatabaseManager.getAllQuestions(); // gets all questions from db

                    ArrayList<Question> generatedQuizList = DatabaseManager.generateQuiz(noOfQuestions, chooseCorrectAndIncorrect, chooseQuestionType, chooseQuestionTopic, isMultipleChoice, questionTopic, allQuestions); // generates quiz using the given parameters.
                    System.out.println(generatedQuizList.size());
                    Quiz quiz = new Quiz(quizName, generatedQuizList.size(), 0); // creates new quiz object
                    boolean quizIsAdded = DatabaseManager.addQuizToDB(quiz, generatedQuizList);
                    if (quizIsAdded) {
                        System.out.println("The quiz has been added to the database."); // confirms quiz has been added to db
                    }
                    else {
                        System.out.println("This quiz couldn't be added to the database."); // prints that quiz couldn't be added
                    }
                }

                // Delete a Quiz
                if (quizChoice.equals("3")) {
                    System.out.println("Enter quiz name:");
                    String quizName = sc.nextLine();
                    boolean quizIsDeleted = DatabaseManager.deleteQuizInDB(quizName); // deletes a quiz with a given name
                    if (quizIsDeleted) {
                        System.out.println("The quiz has been deleted."); // quiz has been deleted confirmation
                    }
                    else {
                        System.out.println("The quiz doesn't exist so it couldn't be deleted."); //quiz doesnt exist
                    }
                }

                // Read a quiz
                if (quizChoice.equals("4")){
                    System.out.println("Enter quiz name:");
                    String quizName = sc.nextLine();
                    ArrayList<Question> quiz = DatabaseManager.readQuizInDB(quizName);
                    for (Question q: quiz){
                        System.out.println(q.toString()); // displays all questions in array list
                    }
                }
            }

            // Question options
            else if (choice.equals("2")) {
                System.out.println("Question options:\n1| Add a question\n2| Update a question\n3| Delete a question\n4| List questions");
                String questionChoice = sc.nextLine(); // question options

                // Add a Question
                if (questionChoice.equals("1")) {
                    boolean isQuestionAdded = DatabaseManager.addQuestionToDB(addQuestion());
                    if (isQuestionAdded) {
                        System.out.println("The question was added to the database."); // confirmation it's been added to the db
                    }
                    else {
                        System.out.println("The question already exists in the database."); // prints that it already exists in db
                    }
                }

                // Update a Question
                if (questionChoice.equals("2")) {
                    System.out.println("Enter the question:");
                    String question = sc.nextLine();
                    System.out.println("What would you like to change?\n1| Answer\n2| Question Topic\n3| Correctness\n4| Question Type");
                    String changeChoice = sc.nextLine(); // asks for question and what the user wants to change.
                    if (changeChoice.equals("1")){
                        String whatChanges = "Answer";
                        System.out.println("Enter the new answer:");
                        String stringChange = sc.nextLine();
                        stringChange = Validation.blankInput(stringChange);
                        DatabaseManager.updateQuestionInDB(question, whatChanges, stringChange, false); //takes the new answer then calls method to update
                    }
                    if (changeChoice.equals("2")){
                        String whatChanges = "Question Topic";
                        System.out.println("Enter the new topic:");
                        String stringChange = sc.nextLine();
                        stringChange = Validation.blankInput(stringChange);
                        DatabaseManager.updateQuestionInDB(question, whatChanges, stringChange, false); //takes the new topic then calls the method to update
                    }
                    if (changeChoice.equals("3")){
                        String whatChanges = "Correctness";
                        System.out.println("Enter the new value:\n1| Correct\n2| Incorrect");
                        String stringChange = sc.nextLine();
                        if (stringChange.equals("1")){
                            DatabaseManager.updateQuestionInDB(question, whatChanges, stringChange, true); // takes correctness input then updates to true
                        }
                        if (stringChange.equals("2")){
                            DatabaseManager.updateQuestionInDB(question, whatChanges, stringChange, false); // takes correctness input then updates to false
                        }
                    }
                    if (changeChoice.equals("4")){
                        String whatChanges = "Question Type";
                        System.out.println("Enter the new type:\n1| MCQ\n2| SAQ");
                        String stringChange = sc.nextLine();
                        if (stringChange.equals("1")){
                            DatabaseManager.updateQuestionInDB(question, whatChanges, stringChange, true); // takes type input then updates to true
                        }
                        if (stringChange.equals("2")){
                            DatabaseManager.updateQuestionInDB(question, whatChanges, stringChange, false); // takes type input then updates to false
                        }
                    }

                }


                // Delete a Question
                else if (questionChoice.equals("3")) {
                    System.out.println("Enter the question:");
                    String question = sc.nextLine();
                    boolean isQuestionDeleted = DatabaseManager.deleteQuestionInDB(question); //takes the question and calls method to delete it from db
                    if (isQuestionDeleted) {
                        System.out.println("The question has been deleted."); // confirmation of deletion
                    }
                    else {
                        System.out.println("The question doesn't exist in the database so it couldn't be deleted."); //question does not exist message
                    }
                }

                // List All Questions
                if (questionChoice.equals("4")) {
                    System.out.println("Choose an option:\n1| List all questions\n2| List all incorrectly answered questions\n3| List questions by type\n4| List questions by topic.\n5| List questions by type and topic");
                    String listingChoice = sc.nextLine();
                    if (listingChoice.equals("1")){
                        ArrayList<Question> allQuestions = DatabaseManager.getAllQuestions(); // gets all questions and produces an arraylist
                        for (Question q: allQuestions){
                            System.out.println(q.toString()); // reads through each question in the array list
                        }
                    }
                    if (listingChoice.equals("2")){
                        ArrayList<Question> allIncorrectQuestions = DatabaseManager.listIncorrectQuestions(); // gets an array list of all incorrect questions
                        for (Question q: allIncorrectQuestions){
                            System.out.println(q.toString()); // reads through each question in the array list
                        }
                    }
                    if (listingChoice.equals("3")){
                        System.out.println("Enter question type (MCQ/SAQ)");
                        String type = sc.nextLine();
                        if (type.equalsIgnoreCase("MCQ")){
                            ArrayList<Question> allMCQQuestions = DatabaseManager.listQuestions(true, false, true, ""); // gets an array list of all MCQs
                            for (Question q: allMCQQuestions){
                                System.out.println(q.toString()); // displays all MCQs in array list
                            }
                        }
                        if (type.equalsIgnoreCase("SAQ")){
                            ArrayList<Question> allSAQQuestions = DatabaseManager.listQuestions(true, false, false, ""); // gets an array list of all SAQs
                            for (Question q: allSAQQuestions){
                                System.out.println(q.toString()); // prints all SAQs from the array list
                            }
                        }
                    }
                    if (listingChoice.equals("4")){
                        System.out.println("Enter topic");
                        String topic = sc.nextLine();
                        ArrayList<Question> allTopicQuestions = DatabaseManager.listQuestions(false, true, false, topic); // gets an array list of all questions from a specific topic
                        for (Question q: allTopicQuestions){
                            System.out.println(q.toString()); // prints questions of a specific topic from the array list
                        }
                    }
                    if (listingChoice.equals("5")){
                        System.out.println("Enter topic");
                        String topic = sc.nextLine();
                        System.out.println("Enter question type (MCQ/SAQ)");
                        String type = sc.nextLine();
                        if (type.equalsIgnoreCase("MCQ")){
                            ArrayList<Question> allMCQTopicQuestions = DatabaseManager.listQuestions(true, true, true, topic);
                            for (Question q: allMCQTopicQuestions){
                                System.out.println(q.toString()); // prints all SAQs from the array list
                            }
                        }
                        if (type.equalsIgnoreCase("SAQ")){
                            ArrayList<Question> allSAQTopicQuestions = DatabaseManager.listQuestions(true, true, false, topic);
                            for (Question q: allSAQTopicQuestions){
                                System.out.println(q.toString()); // prints all SAQs from the array list
                            }
                        }
                    }
                }
            }

            // Reporting Statistics
            else if (choice.equals("3")) {
                System.out.println("Enter Quiz Name:");
                String quizName = sc.nextLine();
                ArrayList<Question> quizQuestions = DatabaseManager.readQuizInDB(quizName); // gets array list of all questions in a specific quiz
                int score = DatabaseManager.readQuizScore(quizName);
                if (quizQuestions.size()<1) {
                    System.out.println("This quiz has no questions in it."); // quiz has no questions
                }
                else {
                    QuizReport.shortQuizReport(score, quizQuestions); // produces quiz report
                }
            }

            else if(choice.equals("4")){
                System.out.println("Choose an option:\n1| Read\n2| Write");
                String fileChoice = sc.nextLine();
                if (fileChoice.equalsIgnoreCase("1")){
                    DatabaseManager.readQuestionFromFile(); // reads question from file
                }
                if (fileChoice.equalsIgnoreCase("2")){
                    boolean quit = false;
                    ArrayList<Question> questionList = new ArrayList<>();
                    while (!quit){ // loops until user chooses to quit
                        System.out.println("Would you like to add a question to the file. y/n");
                        String addQuestionChoice = sc.nextLine();
                        if (addQuestionChoice.equalsIgnoreCase("y")){
                            Question questionObject = addQuestion(); // runs add question method to get a question object
                            questionList.add(questionObject); // adds question object to array list
                        }
                        if(addQuestionChoice.equalsIgnoreCase("n")){
                            DatabaseManager.writeQuestionToFile(questionList); // writes array list to file
                            quit = true; //quits loop
                        }
                    }
                }
            }

            else if (choice.equals("5")){
                exit = true; // exits menu loop and ends program.
            }
            else {
                System.out.println("The input is invalid.");
            }
        }

    }

    /**
     * This method takes in user inputs via an interface and creates a question object from these inputs.
     * @return addQuestion as a question object.
     */
    public static Question addQuestion(){
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the Question:");
            String question = sc.nextLine();// enter the question
            question = Validation.blankInput(question);

            System.out.println("Would you like to make your question multiple choice? y/n ");
            String multiChoice = sc.nextLine();//sets to multi choice if answer is y
            boolean questionLoop = false;//loops until set to true
            int answerNumber = 0;

            String questionType;
            boolean isMulti;
            if (multiChoice.equalsIgnoreCase("y")) {
                questionLoop = true;
                questionType = "MCQ";
                isMulti = true; // multi choice options
            } else {
                questionType = "SAQ";
                isMulti = false; // SAQ options
            }

            int i = 0;
            while (questionLoop) {
                System.out.println("Would you like to add an answer? y/n");
                String addQuestion = sc.nextLine(); // adds an answer
                if (!addQuestion.equalsIgnoreCase("y") && answerNumber>0) {
                    questionLoop = false;//loop continues
                } else {
                    i++;//i increments
                    System.out.println("Type answer " + (answerNumber + 1));
                    String questionAnswer = sc.nextLine();// the answer itself
                    questionAnswer = Validation.blankInput(questionAnswer);
                    answerNumber++;// increments
                    question = (question + " " + String.valueOf(answerNumber) + ") " + questionAnswer);// concatenates question
                }
            }

            boolean validAnswer = false;// not valid until true
            String answer = "";

            while (!validAnswer) {
                System.out.println("Enter the correct answer:");
                answer = sc.nextLine(); // enter correct answer
                answer = Validation.blankInput(answer);
                if (questionType.equalsIgnoreCase("SAQ")) {
                    validAnswer = true; // automatically valid if SAQ
                }
                if (!validAnswer) {
                    int intAnswer = Validation.validateInteger(answer,"Enter the correct answer:");
                    if ((intAnswer <= i) && (intAnswer > 0)) {
                        validAnswer = true; // checks the correct answer is within range of the options available
                    }
                }
            }

            System.out.println("Enter the Question topic:");
            String topic = sc.nextLine(); // enter question topic
            topic = Validation.blankInput(topic);
            Question addQuestion = new Question(question, answer, isMulti, topic, true); // creates a question object
            return addQuestion; // returns question object
    }
}
