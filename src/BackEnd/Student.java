package BackEnd;

import java.util.ArrayList;

public class Student extends User {

    private ArrayList<String> enrolledCourses;
    private LessonsProgress lessonsProgress;
    

    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Student");
        enrolledCourses = new ArrayList<>();
        lessonsProgress = new LessonsProgress();
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
    

       public boolean isPassed(String quizID, char correctOption) {
        for (char x : ) {
            if (x == correctOption)
            { return true;}
        }
        return false;
    
    
    
    
    
    
    
}
