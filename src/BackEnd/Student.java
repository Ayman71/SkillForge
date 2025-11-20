package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student extends User {
    private List<String> enrolledCourses;
    private LessonsProgress lessonsProgress = new LessonsProgress();
    private Map<String, Map<String, List<Integer>>> quizAttempts = new HashMap<>();
    private int maxRetries = 3;
    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Student");
        this.enrolledCourses = new ArrayList<>();
    }
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }
    public void setEnrolledCourses(List<String> enrolledCourses) {
        this.enrolledCourses =
                enrolledCourses != null ? new ArrayList<>(enrolledCourses) : new ArrayList<>();
    }
    public void enrollCourse(String courseId) {
        if (courseId == null) return;
        if (!enrolledCourses.contains(courseId)) {
            enrolledCourses.add(courseId);
            CourseManager cm = CourseManager.getInstance();
            if (cm != null) {
                Course course = cm.getCourseByID(courseId);
                if (course != null) {
                    lessonsProgress.addCourse(courseId, course.getLessons());
                }
            }
        }
    }
    public LessonsProgress getLessonsProgress() {
        return lessonsProgress;
    }
    public void setLessonsProgress(LessonsProgress lessonsProgress) {
        this.lessonsProgress =
                lessonsProgress != null ? lessonsProgress : new LessonsProgress();
    }
    public void takeQuiz(String courseId, String lessonId, List<Integer> answers, Quiz quiz) {
        Map<String, List<Integer>> courseAttempts =
                quizAttempts.computeIfAbsent(courseId, k -> new HashMap<>());
        List<Integer> lessonAttempts =
                courseAttempts.computeIfAbsent(lessonId, k -> new ArrayList<>());
        if (lessonAttempts.size() >= maxRetries) {
            throw new IllegalStateException("Max retries exceeded for quiz in lesson " + lessonId);
        }
        int score = quiz.calculateScore(answers);
        lessonAttempts.add(score);
        boolean passed = score >= quiz.getPassingScore();
        lessonsProgress.updateQuizStatus(courseId, lessonId, passed);
    }
    public boolean hasPassedQuiz(String courseId, String lessonId) {
        return lessonsProgress.isQuizPassed(courseId, lessonId);
    }
    public boolean canRetryQuiz(String courseId, String lessonId) {
        Map<String, List<Integer>> courseAttempts = quizAttempts.get(courseId);
        if (courseAttempts == null) return true;
        List<Integer> lessonAttempts = courseAttempts.get(lessonId);
        return lessonAttempts == null || lessonAttempts.size() < maxRetries;
    }
    public Map<String, Map<String, List<Integer>>> getQuizAttempts() {
        return quizAttempts;
    }
    public void setQuizAttempts(Map<String, Map<String, List<Integer>>> quizAttempts) {
        this.quizAttempts = quizAttempts != null ? quizAttempts : new HashMap<>();
    }
    public int getMaxRetries() {
        return maxRetries;
    }
    public void setMaxRetries(int maxRetries) {
        this.maxRetries = maxRetries;
    }
}
