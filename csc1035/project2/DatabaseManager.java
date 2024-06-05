package csc1035.project2;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.*;
import java.io.*;

/**
 * This class contains the methods used to perform CRUD operations on the tables Questions, Quizzes and QuizQuestions and
 * also checks if a quiz or question being added, updated or removed actually exists in the table as well as having methods
 * used to read questions to and from a text file and the database.
 *
 * @author Team 09
 */
public class DatabaseManager {

    /**
     * This method retrieves all questions from the Questions table in the database and stores each record as an object
     * with its attributes being the attributes of a question object. Each question is added to an array list which is
     * then returned to the main program.
     * @return an array list of all the questions in the database
     */
    public static ArrayList<Question> getAllQuestions() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        ArrayList<Question> allQuestions = new ArrayList<>(); // Creates the array list the questions will be put in
        List questionList = session.createQuery("from Questions").list(); // Makes a list of all questions in the database
        for (Iterator<Question> iterator = questionList.iterator(); iterator.hasNext(); ) {
            Question newQuestion = iterator.next(); // Iterates through each question in the database
            allQuestions.add(newQuestion); // Each question is added to the array list
        }
        session.close(); // Query session is closed
        return allQuestions; // Returns the array list of all the questions
    }

    /**
     * This method returns a list of all questions that are requested by the user. It can store an array list of all questions and
     * all questions of a certain type and/or topic depending on the input of the user. It does this by querying through the
     * Questions table to find the relevant questions and then adding to an array list of question objects.
     * @param chooseQuestionType is true if the user wants questions of a certain topic
     * @param chooseQuestionTopic is true if the user wants questions of a certain type
     * @param isMultipleChoice is true if the user wants only multiple choice questions and is false if they only want short answers ones
     * @param questionTopic represents the topic the user wants if they want to list questions by a certain topic
     * @return an array list of all questions or all questions of a certain type and/or topic
     */
    public static ArrayList<Question> listQuestions(boolean chooseQuestionType, boolean chooseQuestionTopic,
                                                    boolean isMultipleChoice, String questionTopic) {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession(); // Opens a new query session
        ArrayList<Question> listOfQuestions = new ArrayList<>(); // Makes an array list of all questions to be returned
        String multipleChoiceString;
        if (isMultipleChoice) { // String becomes 1 if the questions should be multiple choice, and 0 if they should not
            multipleChoiceString = "1";
        }
        else {
            multipleChoiceString = "0";
        }
        if (!chooseQuestionTopic && !chooseQuestionType) { // This is chosen if the question type and topic stored in the array list don't matter
            String hql1 = "from Questions";
            Query query1 = session.createQuery(hql1);
            List<Question> list1 = query1.list(); // Makes a list of all questions in the Questions table
            for (Question question:list1) {
                listOfQuestions.add(question); // Iterates through the list and adds each of the questions to the array list
            }
        }

        else if (!chooseQuestionTopic) { // This is chosen if the question topic in the array list doesn't matter but the question type does
            String hql2 = "from Questions where isItMultipleChoice = "+multipleChoiceString;
            Query query2 = session.createQuery(hql2);
            List<Question> list2 = query2.list(); // Makes a list of all questions of a particular type
            for (Question question:list2) {
                listOfQuestions.add(question); // Iterates through this list and adds each question to an array list
            }
        }

        else if (!chooseQuestionType) { // This is chosen if the question type in the array list doesn't matter but the question topic does
            String hql3 = "from Questions where questionTopic = '"+questionTopic+"'";
            Query query3 = session.createQuery(hql3);
            List<Question> list3 = query3.list(); // Makes a list of all questions of a particular topic
            for (Question question:list3) {
                listOfQuestions.add(question); // Iterates through this list and adds each question to an array list
            }
        }

        else { // This is chosen if the question type and topic stored in the array list both matter
            String hql4 = "from Questions where questionTopic = '"+questionTopic+"' and isItMultipleChoice = "+multipleChoiceString;
            Query query4 = session.createQuery(hql4);
            List<Question> list4 = query4.list(); // Makes a list of all questions of a particular topic and type
            for (Question question:list4) {
                listOfQuestions.add(question); // Iterates through this list and adds each question to an array list
            }
        }
        session.close(); // Closes the query session
        return listOfQuestions; // Returns an array list of the relevant questions from the database
    }

    /**
     * This method returns a list of all the questions that the user got wrong on their most recent attempt of it by querying all
     * questions answered incorrectly and from the database and adding them to an array list of wrongly-answered questions.
     * @return an array list of all the questions the user got wrong
     */
    public static ArrayList<Question> listIncorrectQuestions() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.openSession(); // Opens a new query session
        ArrayList<Question> listOfQuestions = new ArrayList<>(); // Makes an array list for the incorrect questions to be put in
        String hql = "from Questions where isCorrect =" + 0;
        Query incorrectQuestionQuery = session.createQuery(hql); // Makes a query from all questions answered incorrectly
        List<Question> list = incorrectQuestionQuery.list(); // Makes a list of all incorrectly answered questions
        for(Question question:list){
            listOfQuestions.add(question); // Iterates through this list and adds each question to the array list
        }
        return listOfQuestions; // Returns an array list of all incorrectly answered questions
    }

    /**
     * This method generates a list of questions that are used for a quiz and does this by randomly selecting questions from the
     * Questions table and adding them to an array list of questions. The questions that are added depend on what is passed into
     * the method, so the list of questions could contain all questions, only maths questions, only physics and short answer questions
     * and so on.
     * @param noOfQuestions represents the number of questions the quiz will have
     * @param chooseCorrectAndIncorrect is true if correct and incorrect questions are added, and false if only incorrect ones are added
     * @param chooseQuestionType is true if questions have to be of a specific type and false if they don't
     * @param chooseQuestionTopic is true if questions have to be of a specific topic and false if they don't
     * @param isMultipleChoice is true if questions in the list should only be multiple choice and false if they should only be short answer
     * @param questionTopic represents the topic the questions in the list should be on if only questions of a specific topic should be added
     * @param allQuestions represents an array list of questions in the Questions table
     * @return an array list of the questions that will be in the quiz
     */
    public static ArrayList<Question> generateQuiz(int noOfQuestions, boolean chooseCorrectAndIncorrect, boolean chooseQuestionType, boolean chooseQuestionTopic,
                                                   boolean isMultipleChoice, String questionTopic, ArrayList<Question> allQuestions) {
        Random random = new Random(); // Creates an object used for randomisation
        int totalPossibleQuestions = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List questionList = session.createQuery("from Questions").list(); // Makes a list of all questions in the database
        for (Iterator<Question> iterator = questionList.iterator(); iterator.hasNext(); ) {
            Question newQuestion = iterator.next(); // Iterates through each question in the list
            if (((!chooseQuestionType && !chooseQuestionTopic) || (!chooseQuestionTopic && newQuestion.isItMultipleChoice() == isMultipleChoice) ||
                    (!chooseQuestionType && newQuestion.getQuestionTopic().equalsIgnoreCase(questionTopic)) ||
                    (newQuestion.isItMultipleChoice() == isMultipleChoice && newQuestion.getQuestionTopic().equalsIgnoreCase(questionTopic))) &&
                    ((chooseCorrectAndIncorrect) || (!newQuestion.isCorrect()))) {
                /* If a question matches the features needed for a question in the quiz, then total number of possible questions
                in the quiz increases by 1.
                 */
                totalPossibleQuestions++;
            }
        }
        session.close(); // Closes the query session
        if (totalPossibleQuestions < noOfQuestions) {
            /* If number of questions inputted for the quiz is lower than the total number of questions possible given the
            parameters, then the total number of questions becomes the minimum number of questions possible here */
            noOfQuestions = totalPossibleQuestions;
        }
        ArrayList<Question> generatedQuizList = new ArrayList<>(); // Makes an array list to store the questions of the generated quiz
        for (int x = 0; x < noOfQuestions; ) { // Iterates until the required number of questions are in the array list
            boolean sameQuestion = false;
            Question nextQuestion = allQuestions.get(random.nextInt(allQuestions.size())); // Selects a question at random
            // Iterates through the current list of quiz questions and checks if the new question is the same as any of them
            for (Question q : generatedQuizList) {
                if (q.sameQuestion(nextQuestion.getQuestion())) {
                    sameQuestion = true;
                }
            }
            /* If the question is different from the rest of the list, the method checks that it matches the features passed in
            that are needed for a question, like if it needs to be multiple choice or a certain topic and if it matches
            these features then it is added to the array list */
            if (!sameQuestion) {
                if (((!chooseQuestionType && !chooseQuestionTopic) || (!chooseQuestionTopic && nextQuestion.isItMultipleChoice() == isMultipleChoice) ||
                        (!chooseQuestionType && nextQuestion.getQuestionTopic().equalsIgnoreCase(questionTopic)) ||
                        (nextQuestion.isItMultipleChoice() == isMultipleChoice && nextQuestion.getQuestionTopic().equalsIgnoreCase(questionTopic))) &&
                        ((chooseCorrectAndIncorrect) || (!nextQuestion.isCorrect()))) {
                    generatedQuizList.add(nextQuestion);
                    x++;
                }
            }
        }
        return generatedQuizList; // Returns an array list of questions in the quiz
    }

    /**
     * This adds a question to the database as a new record with its attributes like the question, answer and type being
     * determined by user input. If the question already exists, then the user is told this and the new question isn't added.
     * @param question represents the question object whose attributes were entered by the user
     */
    public static boolean addQuestionToDB(Question question) {
        // Adapted from https://ncl.instructure.com/courses/43687/pages/hibernate-getting-started?module_item_id=2041654
        boolean alreadyExists = doesQuestionExist(question.getQuestion()); // Checks if the question already exists in the table
        if (!alreadyExists) {
            Session session = HibernateUtil.getSessionFactory().openSession(); // Opens the session
            session.beginTransaction(); // Performs a transaction to add the question as a record
            session.save(question);
            session.getTransaction().commit(); // Commits this transaction
            session.close(); // Closes the session
            return true; // Returns true if the question is added
        } else {
            return false; // Returns false if the question isn't added
        }
    }

    /**
     * This method updates an attribute in a question depending on what is entered by the user, they can change the answer and
     * question topic among other things and once it is changed this is written to the database and updated.
     * @param questionName represents the question to be changed
     * @param whatChanges represents the attribute of a question to be changed
     * @param stringChange represents what the attribute changes to if the answer or topic is being changed
     * @param booleanChange represents what the attribute changes to if the correctness or question type is being changed
     */
    public static boolean updateQuestionInDB(String questionName, String whatChanges, String stringChange, boolean booleanChange) {
        boolean questionExists = doesQuestionExist(questionName); // Checks if the question even exists in the table
        if (questionExists) {
            Session newSession = HibernateUtil.getSessionFactory().openSession();
            newSession.beginTransaction(); // Opens a transaction to change an attribute in a given question record
            Question question = (newSession.get(Question.class, questionName)); // A question object with the question passed in is made
            /* The answer, topic, type or correctness of a question changes depending on the user input with the change for the topic
            answer being a string and the change being a boolean for the others */
            if (whatChanges.equalsIgnoreCase("Answer")) {
                question.setAnswer(stringChange);
            } else if (whatChanges.equalsIgnoreCase("Question Topic") || whatChanges.equalsIgnoreCase("Topic")) {
                question.setQuestionTopic(stringChange);
            } else if (whatChanges.equalsIgnoreCase("Correctness")) {
                question.setCorrect(booleanChange);
            } else if (whatChanges.equalsIgnoreCase("Question Type")) {
                question.setItMultipleChoice(booleanChange);
            }
            newSession.update(question);
            newSession.getTransaction().commit(); // This updated is committed
            newSession.close(); // The session is closed
            return true; // Returns true if an attribute is successfully updated
        } else {
            System.out.println("The question doesn't exist.");
            return false; // Returns false if an attribute isn't updated
        }
    }

    /**
     * This method deletes a question from the database, the question itself is entered and then the record of the question is
     * deleted in the Questions table and any record containing that question in the linking table is deleted too.
     * @param questionName represents the question that is going to be deleted
     */
    public static boolean deleteQuestionInDB(String questionName) {
        boolean isQuestionReal = doesQuestionExist(questionName); // Checks if the question being deleted actually exists
        if (isQuestionReal) {
            Session session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction(); // Opens a transaction to delete a question
            Question deletedQuestion = session.get(Question.class, questionName); // A question object with the question passed in is made
            session.delete(deletedQuestion); // The question is deleted from the question table
            List linkList = session.createQuery("from QuizQuestions").list();
            for (Iterator<LinkTable> iterator = linkList.iterator(); iterator.hasNext(); ) {
                LinkTable newLinkRecord = iterator.next(); // Iterates through the linking table to find quizzes with the deleted question
                if (newLinkRecord.getQuestion().equalsIgnoreCase(questionName)) {
                    List quizList = session.createQuery("from Quizzes").list();
                    for (Iterator<Quiz> iterator1 = quizList.iterator(); iterator1.hasNext(); ) {
                        Quiz newQuiz = iterator1.next(); // Iterates through the quizzes table to find quizzes with the same name as those in the linking table
                        if (newQuiz.getQuizName().equalsIgnoreCase(newLinkRecord.getQuizName())) {
                            newQuiz.setNumberOfQuestions(newQuiz.getNumberOfQuestions() - 1); // The total number of questions in the quiz is decreased by one
                            session.update(newQuiz);
                        }
                    }
                    session.delete(newLinkRecord); // Deletes the record in the linking table with that question
                }
            }
            session.getTransaction().commit(); // Commits the changes made
            session.close(); // Closes the ongoing session
            return true; // Returns true if a question is deleted
        } else {
            return false; // Returns false if a question isn't deleted
        }


    }

    /**
     * This method adds a newly generated quiz to the database, the quiz name, score and length are added to a record in
     * the Quizzes table and the quiz name and each of its corresponding questions are added to the linking table called
     * QuizQuestions.
     * @param quiz represents the quiz object whose attributes are going to be added to the Quizzes table
     * @param generatedQuizList represents the list of questions in that quiz that will be added to the linking table
     */
    public static boolean addQuizToDB(Quiz quiz, ArrayList<Question> generatedQuizList) {
        boolean quizAlreadyExists = doesQuizExist(quiz.getQuizName()); // Checks if the quiz to be added already exists
        if (!quizAlreadyExists && generatedQuizList.size()>1) {
            Session session = HibernateUtil.getSessionFactory().openSession(); // Opens the session
            session.beginTransaction();
            for (Question question : generatedQuizList) {
                // Iterates through each question in the quiz and adds the quiz name and each question to a new record in the linking table
                LinkTable linkTable = new LinkTable(quiz.getQuizName(), question.getQuestion());
                session.save(linkTable);
            }
            session.save(quiz); // Adds the quiz object as a new record in the Quizzes table
            session.getTransaction().commit(); // Commits the quiz added
            session.close(); // Closes the session
            return true; // Returns true if the quiz is added
        } else {
            return false; // Returns false if the quiz isn't added
        }
    }

    /**
     * This method deletes a quiz from the database. It does this by taking in a quiz name and deleting the records with that
     * same quiz name in both the Quizzes table and the QuizQuestions table. If there is no quiz with that name, then the
     * user is told this.
     * @param quizName represents the name of the quiz to be deleted
     */
    public static boolean deleteQuizInDB(String quizName) {
        boolean isQuizReal = doesQuizExist(quizName); // Checks if the quiz to deleted actually exists
        if (isQuizReal) {
            Session session = HibernateUtil.getSessionFactory().openSession(); // Opens the Hibernate session
            session.beginTransaction();
            Quiz deletedQuiz = session.get(Quiz.class, quizName); // A quiz object with the quiz name passed in is made
            session.delete(deletedQuiz); // The quiz is deleted from the Quizzes table
            List linkList = session.createQuery("from QuizQuestions").list();
            for (Iterator<LinkTable> iterator = linkList.iterator(); iterator.hasNext(); ) {
                LinkTable newLinkRecord = iterator.next(); // Iterates through the linking table and deletes any record if
                if (newLinkRecord.getQuizName().equalsIgnoreCase(quizName)) {
                    session.delete(newLinkRecord);
                }
            }
            session.getTransaction().commit();
            session.close(); // Closes the session
            return true; // Returns true if the quiz is deleted
        } else {
            return false; // Returns true if the quiz isn't deleted
        }

    }

    /**
     * This method takes a quiz name and then finds the record for each question in that quiz in the Questions table and
     * adds it to a list of questions in that quiz. This list of questions is then returned to the main program.
     * @param quizName represents the name of quiz whose questions are being retrieved
     * @return an array list of all the question in that quiz
     */
    public static ArrayList<Question> readQuizInDB(String quizName) {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Opens the session
        ArrayList<Question> questionArrayList = new ArrayList<>(); // Creates an array list to store the questions of a quiz
        session.beginTransaction();
        List linkList = session.createQuery("from QuizQuestions").list(); // Makes a list of records from the linking table
        for (Iterator<LinkTable> iterator = linkList.iterator(); iterator.hasNext(); ) {
            // Iterates through each record and if its quiz name is the same as the one entered by the user then it opens the questions table
            LinkTable newLinkRecord = iterator.next();
            if (newLinkRecord.getQuizName().equalsIgnoreCase(quizName)) {
                List questionsList = session.createQuery("from Questions").list();
                for (Iterator<Question> questionIterator = questionsList.iterator(); questionIterator.hasNext(); ) {
                    Question newQuestion = questionIterator.next();
                    // Iterates through each question in the Questions table and if the question is one in the quiz then it is added to the array list
                    if (newLinkRecord.getQuestion().equalsIgnoreCase(newQuestion.getQuestion())) {
                        questionArrayList.add(newQuestion);
                    }
                }
            }
        }
        session.close(); // The session is closed
        return questionArrayList; // Returns an array list of questions in the quiz
    }

    /**
     * This method takes a quiz name and finds the quiz in the database with that name and returns that quiz's score, if a
     * matching quiz can't be found, then -1 is returned instead.
     * @param quizName represents the name of the quiz whose score is being retrieved
     * @return the score of the quiz
     */
    public static int readQuizScore(String quizName) {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Opens the session
        session.beginTransaction();
        List quizzes = session.createQuery("from Quizzes").list(); // Makes a list of records from Quizzes table
        for (Iterator<Quiz> iterator = quizzes.iterator(); iterator.hasNext(); ) {
            Quiz newQuiz = iterator.next(); // Iterates through each quiz in the table
            if (newQuiz.getQuizName().equalsIgnoreCase(quizName)) {
                // If a quiz has the same name as the quiz name entered than the score of that quiz is returned
                session.close(); // Closes the session
                return newQuiz.getScore();
            }
        }
        session.close();
        return -1; // If there is no matching quiz name, then -1 is returned
    }

    /**
     * This method is used to update the score of a quiz in the database. It takes in the name and score of the quiz and
     * then finds that quiz inside the Quizzes table and updates its score.
     * @param quizName represents the name of the quiz whose score will be changed
     * @param score represents the new score of the quiz
     */
    public static boolean updateQuizInDB(String quizName, int score) {
        boolean isQuizReal = doesQuizExist(quizName); // Checks if the quiz to be updated even exists
        if (isQuizReal) {
            Session session = HibernateUtil.getSessionFactory().openSession(); // Opens a session
            session.beginTransaction();
            Quiz quiz = session.get(Quiz.class, quizName); // A quiz object with the quiz name passed in is made
            quiz.setScore(score); // Changes the score of the quiz to its new value after it is taken
            session.update(quiz);
            session.getTransaction().commit(); // Commits this change to the database
            session.close(); // Closes the session
            return true; // Returns true if the quiz is updated
        }
        else {
            return false; // Returns true if the quiz isn't updated
        }
    }

    /**
     * This method checks if the question passed into it already exists inside the Questions table. If it does, then true
     * is returned, otherwise false is returned.
     * @param questionName represents the question the method is checking the existence of
     * @return whether the question is in the database or not
     */
    public static boolean doesQuestionExist(String questionName) {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Opens the session
        session.beginTransaction();
        List questionList = session.createQuery("from Questions").list(); // Makes a list of all questions in the database
        for (Iterator<Question> iterator = questionList.iterator(); iterator.hasNext(); ) {
            // Iterates through the list of questions to check if any of them are the same as the question entered
            Question newQuestion = iterator.next();
            if (newQuestion.sameQuestion(questionName)) {
                session.close();
                return true; // If the entered question is the same as one in the database, then true is returned
            }
        }
        session.close();
        return false; // If the entered question isn't the same as any in the database, then false is returned
    }

    /**
     * This method checks if the quiz with the name passed into it already exists inside the Quizzes table. If it does, then true
     * is returned, otherwise false is returned.
     * @param quizName represents the question the method is checking the existence of
     * @return whether the quiz is in the database or not
     */
    public static boolean doesQuizExist(String quizName) {
        Session session = HibernateUtil.getSessionFactory().openSession(); // Opens the session
        session.beginTransaction();
        List quizList = session.createQuery("from Quizzes").list(); // Makes a list of all quizzes in the database
        for (Iterator<Quiz> iterator = quizList.iterator(); iterator.hasNext(); ) {
            // Iterates through the list of quizzes to check if any of them are the same as the quiz entered
            Quiz newQuiz = iterator.next();
            if (newQuiz.sameQuiz(quizName)) {
                session.close();
                return true; // If the entered quiz is the same as one in the database, then true is returned
            }
        }
        session.close();
        return false; // If the entered quiz isn't the same as any in the database, then false is returned
    }

    /**
     * This method takes a series of questions from a text file and adds them all to a list of questions and then takes
     * every attribute of each question and tries to add it to the Questions table in the database.
     * @throws FileNotFoundException is thrown if the text file does not exist
     */
    public static void readQuestionFromFile() throws FileNotFoundException {
        //Adapted from https://www.javatpoint.com/how-to-read-csv-file-in-java
        Scanner fileInput = new Scanner(new File("questions.txt")); // Creates an object for the text file
        fileInput.useDelimiter(";");
        while (fileInput.hasNext()) {
            // Iterates through each line in the file and adds each line to a list
            List<String> questionAttributes = Arrays.asList(fileInput.next().split(","));
            // Adapted from https://stackoverflow.com/questions/4123385/remove-all-empty-lines
            String questionName = questionAttributes.get(0).replaceAll("[\\\\\\r\\\\\\n]+","");
            // Creates question object using the attributes of a question in the file
            Question question = new Question(questionName, questionAttributes.get(1), Boolean.parseBoolean(questionAttributes.get(2)),
                    questionAttributes.get(3), Boolean.parseBoolean(questionAttributes.get(4)));
            addQuestionToDB(question); // Adds each question in the file to the database
        }
        fileInput.close(); // Closes the file
    }

    /**
     * This method takes a list of short answer question objects and appends the attributes of each question to a text file, these can
     * then be read from the file later.
     * @param questionsList represents the list of question objects whose attributes are added to the text file
     * @throws IOException is thrown if the user tries adding the wrong data to the file
     */
    public static void writeQuestionToFile(ArrayList<Question> questionsList) throws IOException {
        //Adapted from https://springhow.com/java-write-csv/
        File questionsFile = new File("questions.txt"); // Creates an object for the text file
        FileWriter fileWriter = new FileWriter(questionsFile, true); // Makes sure the text file is appended to
        for (Question question : questionsList) {
            // Iterates through each question in the array list are writes its attributes on individual lines of the text file
            fileWriter.write("\n" + question.getQuestion() + "," + question.getAnswer() + "," + question.isItMultipleChoice() + "," + question.getQuestionTopic() + "," + question.isCorrect() + ";");
        }
        fileWriter.close(); // Closes the file
    }
}
