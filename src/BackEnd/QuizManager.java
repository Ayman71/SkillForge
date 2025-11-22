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

    public QuizManager() {
        quizzes = new ArrayList<>();
    }

    public boolean isPassed(Student student, String quizID, ArrayList<Character> studentChoices) {

        Quiz quiz = findQuizByID(quizID);
        if (quiz == null) {
            return false;
        }
        ArrayList<Question> questions = quiz.getQuestions();
        int score = 0;

        for (int i = 0; i < questions.size(); i++) {
            if (studentChoices.get(i) == questions.get(i).getCorrectChoice()) {
                score++;
            }
        }

        student.attemptQuiz(quizID, score);

        if (score >= (questions.size()) * 0.5) {
            return true;
        } else {
            return false;
        }
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

    public boolean LessonBasedOnQuiz(Student student, String courseId, String lessonId,
            String quizID, ArrayList<Character> studentChoices,
            CourseManager courseManager) {
        boolean passed = isPassed(student, quizID, studentChoices);

        if (passed) {
            courseManager.markLessonCompleted(courseId, student.getUserId(), lessonId);
        }

        return passed;
    }

}
