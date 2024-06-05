package csc1035.project2;
import java.util.Scanner;
import java.util.ArrayList;

public class QuizReport {

    /**
     * This method takes the users score and the size of the list of questions in the quiz and shows how many questions
     * they got right and the percentage. It also prints out each question and whether it was correct or incorrect
     * @param score represents the score of the quiz
     * @param quizQuestions list of the questions that are being used for the quiz
     */
    public static void quizReport(int score, ArrayList<Question> quizQuestions) {
        double percentage = (double) score / quizQuestions.size() * 100;
        int questionNo = 0;
        System.out.println("You scored " + score + "/" + quizQuestions.size() + " which is " + Math.round(percentage) +
                "%" + "\nYour results:");

        for (Question q : quizQuestions){
            questionNo++;
            if (q.isCorrect()) {
                // prints out that the answer is correct
                System.out.println("\u2713 Correct! For question " + questionNo + ": " + q.getQuestion());
            }
            else{
                //prints the incorrect question and the correct answer
                System.out.println("\u2717 Incorrect. For question " + questionNo + ": " + q.getQuestion() +
                        ", the correct answer is " + "'" + q.getAnswer() + "'");
            }
        }
    }

    /**
     * This method takes the users score and the size of the list of questions in the quiz and shows how many questions
     * they got right and the percentage.
     * @param score represents the score of the quiz
     * @param quizQuestions list of the questions that are being used for the quiz
     */
    public static void shortQuizReport(int score, ArrayList<Question> quizQuestions){
        double percentage = (double) score / quizQuestions.size() * 100;
        System.out.println("You scored " + score + "/" + quizQuestions.size() + " which is " + Math.round(percentage) +
                "%" + "\nYour results:");
    }

    /**
     * This method gets a quiz, asks the questions and then gets the input of the user's answers and updates the
     * corrects of the question in the database. It then updates the quiz in the database after the quiz is finished
     * @param generatedQuizList represents the questions in the quiz
     * @param quizName represents the name of the quiz whose correctness will be changed
     */
    public static void takeQuiz(ArrayList<Question> generatedQuizList, String quizName){
        Scanner answerScanner = new Scanner(System.in);
        System.out.println("You are taking " + quizName + " quiz");

        int score = 0;
        int questionNo = 0;

        for(Question q: generatedQuizList){
            questionNo++;
            System.out.println(questionNo + "| " + q.getQuestion() + "\nYour answer: ");
            String answer = answerScanner.nextLine();
            boolean correct = q.isRightAnswer(answer);
            if(correct){
                DatabaseManager.updateQuestionInDB(q.getQuestion(), "Correctness", "", true);
                score++;
            }
            else {
                DatabaseManager.updateQuestionInDB(q.getQuestion(), "Correctness", "", false);
            }
        }

        System.out.println("Quiz finished");
        DatabaseManager.updateQuizInDB(quizName, score);
    }

}