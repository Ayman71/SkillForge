package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student extends User {

    private ArrayList<String> enrolledCourses;
    private LessonsProgress lessonsProgress;
    private Map<String, QuizAttempt> quizAttempts;
    private ArrayList<Certificate> certificates;

    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Student");
        enrolledCourses = new ArrayList<>();
        lessonsProgress = new LessonsProgress();
        quizAttempts = new HashMap<>();
        certificates = new ArrayList<>();
    }

    public void setLessonsProgress(LessonsProgress lessonsProgress) {
        this.lessonsProgress = lessonsProgress;
    }

    public ArrayList<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public Map<String, QuizAttempt> getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(Map<String, QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public void setCertificates(ArrayList<Certificate> certificates) {
        this.certificates = certificates;
    }

    public void setEnrolledCourses(ArrayList<String> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public LessonsProgress getLessonsProgress() {
        return lessonsProgress;
    }

    public Map<String, Double> getLessonsProgressByCourse(String courseID) {
        return lessonsProgress.getProgressMap().get(courseID);
    }
    
    public void enrollCourse(Course course) {
        lessonsProgress.addCourse(course.getCourseID(), course.getLessons());
    }

    public void setScore(String courseID, String lessonID, double score) {
        lessonsProgress.setLessonScore(courseID, lessonID, score);
    }

    public double getCourseProgress(String courseID) {
        return lessonsProgress.getCourseProgress(courseID);
    }

    public double getScore(String courseID, String lessonID) {
        return lessonsProgress.getLessonScore(courseID, lessonID);
    }

    public void attemptQuiz(String quizID, double score) {
        if (quizAttempts == null) {
            quizAttempts = new HashMap<>();
        }
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

    public double getQuizScore(String quizID) {
        return quizAttempts.getOrDefault(quizID, new QuizAttempt(quizID, 0, 0)).getScore();
    }

    public boolean isCourseCompleted(String courseId, Course course) {
        ArrayList<Lesson> lessons = course.getLessons();
        if (lessons == null) {
            return false;
        }

        for (Lesson lesson : lessons) {
            if (getScore(courseId, lesson.getId()) < 50.0) {
                return false;
            }
        }
        return true;
    }

    public void addCertificate(Certificate certificate) {
        if (certificates == null) {
            certificates = new ArrayList<>();
        }
        certificates.add(certificate);
    }

    public ArrayList<Certificate> getCertificates() {
        if (certificates == null) {
            return new ArrayList<>();
        }
        return certificates;
    }

    public int haveExisitingCertificateForCourse(String courseId) {
        if (certificates == null) {
            return 0;
        }
        for (Certificate certificate : certificates) {
            if (certificate.getCourseId().equals(courseId)) {
                return 1;
            }
        }
        return 0;
    }
}
