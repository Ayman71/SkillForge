package BackEnd;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author husse
 */
import java.util.ArrayList;

public class QuizManager {

    private ArrayList<Quiz> quizzes;
    CourseManager courseManager;
    UserManager userManager;
    public QuizManager(CourseManager courseManager, UserManager userManager) {
        quizzes = new ArrayList<>();
        this.courseManager = courseManager;
        this.userManager = userManager;
    }

    public double isPassed(Student student, String quizID, ArrayList<Character> studentChoices) {

        Quiz quiz = findQuizByID(quizID);
        if (quiz == null) {
            return 0.0;
        }
        ArrayList<Question> questions = quiz.getQuestions();
        double score = 0.0;

        for (int i = 0; i < questions.size(); i++) {
            if (studentChoices.get(i) == questions.get(i).getCorrectChoice()) {
                score++;
            }
        }
        score =  (score/questions.size())*100;
        userManager.attemptQuiz(student, quizID, score);
        return score;
                
    }
    
    private Quiz findQuizByID(String quizID) {
        for (Quiz q : quizzes) {
            if (q.getQuizID().equals(quizID)) {
                return q;
            }
        }
        return null;
    }

    public void addQuiz(Quiz quiz) {
        quizzes.add(quiz);
    }
}
