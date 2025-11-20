package BackEnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;


public class CourseManager {

    private static CourseManager instance;
    public static CourseManager getInstance() {
        return instance;
    }
    private String filename;
    private ArrayList<Course> courses;
    private Gson gson;
    private UserManager userManager;
    public CourseManager(String filename, UserManager userManager) {
        this.filename = filename;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.userManager = userManager;
        this.courses = new ArrayList<>();
        readFromFile();
        instance = this;
    }
    private void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            courses = gson.fromJson(reader, new TypeToken<ArrayList<Course>>() {}.getType());
            if (courses == null) courses = new ArrayList<>();
        } catch (Exception e) {
            e.printStackTrace();
            courses = new ArrayList<>();
        }
    }
    public void saveToFile() {
        try (FileWriter writer = new FileWriter(filename)) {
            gson.toJson(courses, writer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public int indexOf(String courseID) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseID().equals(courseID)) return i;
        }
        return -1;
    }
    public Course getCourseByID(String courseID) {
        for (Course c : courses) {
            if (c.getCourseID().equals(courseID)) return c;
        }
        return null;
    }
    public boolean addCourse(Course course) {
        if (getCourseByID(course.getCourseID()) == null) {
            courses.add(course);
            saveToFile();
            return true;
        }
        return false;
    }
    public boolean modifyCourse(String oldCourseID, Course updated) {
        int idx = indexOf(oldCourseID);
        if (idx == -1) return false;
        updated.setEnrolledStudents(courses.get(idx).getEnrolledStudents());
        courses.set(idx, updated);
        saveToFile();
        return true;
    }
    public boolean deleteCourse(String courseID) {
        int idx = indexOf(courseID);
        if (idx == -1) return false;

        courses.remove(idx);
        saveToFile();
        return true;
    }
    public boolean updateLessons(String courseID, ArrayList<Lesson> lessons) {
        int idx = indexOf(courseID);
        if (idx == -1) return false;
        courses.get(idx).setLessons(lessons);
        saveToFile();
        return true;
    }
    public ArrayList<Course> getCoursesFromInstructor(String instructorID) {
        ArrayList<Course> result = new ArrayList<>();
        for (Course c : courses) {
            if (c.getInstructorId().equals(instructorID)) result.add(c);
        }
        return result;
    }
    public ArrayList<Course> getEnrolledCourses(String studentID) {
        ArrayList<Course> enrolled = new ArrayList<>();
        for (Course c : courses) {
            if (c.getEnrolledStudents().contains(studentID)) {
                enrolled.add(c);
            }
        }
        return enrolled;
    }
    public ArrayList<Course> getAvailableCourses(String studentID) {
        ArrayList<Course> available = new ArrayList<>();

        for (Course c : courses) {
            if (!c.isApproved()) continue;

            if (!c.getEnrolledStudents().contains(studentID)) {
                available.add(c);
            }
        }
        return available;
    }
    public void studentEnrolled(String studentID, String courseID) {
        Course c = getCourseByID(courseID);
        if (c == null || !c.isApproved()) return;
        c.getEnrolledStudents().add(studentID);
        Student student = (Student) userManager.getUserFromID(studentID);
        if (student != null) {
            student.enrollCourse(courseID);
        }
        saveToFile();
        userManager.saveToFile();
    }
    public ArrayList<Course> getPendingCourses() {
        ArrayList<Course> pending = new ArrayList<>();
        for (Course c : courses) {
            if (c.getApprovalStatus() == ApprovalStatus.PENDING) pending.add(c);
        }
        return pending;
    }
    public void updateCourseStatus(String courseID, ApprovalStatus status, String adminId) {
        Course c = getCourseByID(courseID);
        if (c == null) return;
        c.setApprovalStatus(status, adminId);
        saveToFile();
    }
    public void updateCourseStatus(Course course, ApprovalStatus status, String adminId) {
        if (course == null) return;
        course.setApprovalStatus(status, adminId);
        saveToFile();
    }
    public void markLessonCompleted(String courseID, String studentID, String lessonID) {
        Student s = (Student) userManager.getUserFromID(studentID);
        if (s == null) return;
        s.getLessonsProgress().markLessonCompleted(courseID, lessonID);
        userManager.saveToFile();
    }
    public boolean isLessonCompleted(String courseID, String studentID, String lessonID) {
        Student s = (Student) userManager.getUserFromID(studentID);
        if (s == null) return false;
        return s.getLessonsProgress().isLessonCompleted(courseID, lessonID);
    }
    public double getStudentCourseProgress(String courseID, String studentID) {
        Student s = (Student) userManager.getUserFromID(studentID);
        if (s == null) return 0.0;
        return s.getLessonsProgress().getCourseProgress(courseID);
    }
}
