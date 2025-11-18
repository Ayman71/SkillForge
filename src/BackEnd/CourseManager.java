/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author husse
 */
public class CourseManager {

    private String filename;
    private ArrayList<Course> courses;
    private Gson gson;
    private UserManager userManager;

    public CourseManager(String filename, UserManager userManager) {
        this.filename = filename;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.courses = new ArrayList<>();
        this.userManager = userManager;
        readFromFile();
    }

    private void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            courses = gson.fromJson(reader, new TypeToken<ArrayList<Course>>() {
            }.getType());
            if (courses == null) {
                courses = new ArrayList<>();
            }
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

    public int contains(String courseID) {
        int i = 0;
        for (Course c : courses) {
            if (c.getCourseID().equals(courseID)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    public Course getCourseFromCourseID(String courseID) {
        for (Course c : courses) {
            if (c.getCourseID().equals(courseID)) {
                return c;
            }
        }
        return null;
    }

    public boolean addCourse(Course course) {
        if (getCourseFromCourseID(course.getCourseID()) == null) {
            courses.add(course);
            saveToFile();
            return true;
        }
        return false;
    }

    public boolean modifyCourse(String oldCourseID, Course course) {
        for (Course c : courses) {
            if (c.getCourseID().equals(oldCourseID)) {
                course.setEnrolledStudents(c.getEnrolledStudents());
                courses.remove(c);
                courses.add(course);
                saveToFile();
                return true;
            }
        }
        return true;
    }

    public boolean deleteCourse(String courseID) {
        for (Course c : courses) {
            if (c.getCourseID().equals(courseID)) {
                courses.remove(c);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public boolean updateLessons(String courseID, ArrayList<Lesson> lessons) {
        courses.get(contains(courseID)).setLessons(lessons);
        saveToFile();
        return true;
    }

    public ArrayList<Course> getCoursesFromInstructor(String instructorID) {
        ArrayList<Course> instructorCourses = new ArrayList<Course>();
        for (Course c : courses) {
            if (c.getInstructorId().equals(instructorID)) {
                instructorCourses.add(c);
            }
        }
        return instructorCourses;
    }

    public Course getCourseFromID(String courseID) {
        for (Course c : courses) {
            if (c.getCourseID().equals(courseID)) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Course> getEnrolledCourses(String studentID) {
        ArrayList<Course> enrolledCourses = new ArrayList<Course>();
        for (Course c : courses) {
            for (String s : c.getEnrolledStudents()) {
                if (s.equals(studentID)) {
                    enrolledCourses.add(c);
                }
            }
        }
        return enrolledCourses;
    }

    public ArrayList<Course> getAvailableCourses(String studentID) {
        ArrayList<Course> enrolledCourses = getEnrolledCourses(studentID);
        ArrayList<Course> availableCourses = new ArrayList<>();

        for (Course c : courses) {
            boolean isEnrolled = false;
            for (Course e : enrolledCourses) {
                if (c.getCourseID().equals(e.getCourseID())) {
                    isEnrolled = true;
                    break;
                }
            }
            if (!isEnrolled) {
                availableCourses.add(c);
            }
        }
        return availableCourses;
    }

    public void studentEnrolled(String studentID, String courseID) {
        int i = 0;
        for (Course c : courses) {
            if (c.getCourseID().equals(courseID)) {
                courses.get(i).getEnrolledStudents().add(studentID);
                Student student = (Student) userManager.getUserFromID(studentID);
                student.enrollCourse(c);
                break;
            }
            i++;
        }
        saveToFile();
        userManager.saveToFile();
    }

    public void syncCourseLessonsForAllStudents(String courseID) {
        Course course = getCourseFromCourseID(courseID);
        if (course == null) {
            return;
        }
        ArrayList<Lesson> updatedLessons = course.getLessons();
        ArrayList<Student> allStudents = userManager.getAllStudents();
        for (Student s : allStudents) {
            if (!s.getEnrolledCourses().contains(courseID)) {
                continue;
            }
            LessonsProgress lp = s.getLessonsProgress();
            if (lp == null) {
                lp = new LessonsProgress();  
                s.setLessonsProgress(lp);
            }
            lp.syncWithCourse(courseID, updatedLessons);
        }
        userManager.saveToFile();
    }

    public void markLessonCompleted(String courseID, String studentID, String lessonID) {
        Student student = (Student) userManager.getUserFromID(studentID);
        student.completeLesson(courseID, lessonID);
        userManager.saveToFile();
    }

    public double getStudentCourseProgress(String courseID, String studentID) {
        Student student = (Student) userManager.getUserFromID(studentID);
        if (student != null) {
            return student.getCourseProgress(courseID);
        }
        return 0.0;
    }

    public boolean isLessonCompleted(String courseID, String studentID, String lessonID) {
        Student student = (Student) userManager.getUserFromID(studentID);
        if (student != null) {
            return student.isLessonCompleted(courseID, lessonID);
        }
        return false;
    }
}
