package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends User {

    private ArrayList<String> enrolledCourses;
    private LessonsProgress lessonsProgress;
        private Map<String, QuizAttempt> quizAttempts;

    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Student");
        enrolledCourses = new ArrayList<>();
        lessonsProgress = new LessonsProgress();
        quizAttempts = new HashMap<>();
    }

    public void setLessonsProgress(LessonsProgress lessonsProgress) {
        this.lessonsProgress = lessonsProgress;
    }

    public ArrayList<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public void setEnrolledCourses(ArrayList<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }
    public LessonsProgress getLessonsProgress() {
        return lessonsProgress;
    }
    public void enrollCourse(Course course) {
        lessonsProgress.addCourse(course.getCourseID(), course.getLessons());
    }

    public void completeLesson(String courseID, String lessonID) {
        lessonsProgress.markLessonCompleted(courseID, lessonID);
    }

    public double getCourseProgress(String courseID) {
        return lessonsProgress.getCourseProgress(courseID);
    }

    public boolean isLessonCompleted(String courseID, String lessonID) {
        return lessonsProgress.isLessonCompleted(courseID, lessonID);
    }
    
      public void attemptQuiz(String quizID, int score) {
        if (quizAttempts.containsKey(quizID)) {
            QuizAttempt q = quizAttempts.get(quizID);
            q.AddAttempt();
            q.setScore(score);
        } else {
            quizAttempts.put(quizID, new QuizAttempt(quizID, 1, score));
        }
    }

        public int getQuizAttemptNumber(String quizID) {
        return quizAttempts.getOrDefault(quizID, new QuizAttempt(quizID, 0, 0)).getAttemptNumber();
    }
        
       public int getQuizScore(String quizID) {
        return quizAttempts.getOrDefault(quizID, new QuizAttempt(quizID, 0, 0)).getScore();
    }
      
      
 public boolean isPassed(String quizID, Quiz quiz, char studentChoice) {
        int score = 0;
        if (studentChoice == quiz.getCorrectOption()) {
            score = 1;
        }
        attemptQuiz(quizID, score);
        return studentChoice == quiz.getCorrectOption();
    }
}
