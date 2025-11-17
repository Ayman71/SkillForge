/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 *
 * @author husse
 */
public class CourseManager {

    private String filename;
    private ArrayList<Course> courses;
    private Gson gson;

    public CourseManager(String filename) {
        this.filename = filename;
        this.gson = new Gson();
        this.courses = new ArrayList<>();
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

    public boolean addCourse(Course course) {
        if (contains(course.getCourseID()) == -1) {
            courses.add(course);
            saveToFile();
            return true;
        }
        return false;
    }

    public boolean modifyCourse(String oldCourseID, Course course) {
        int i = contains(oldCourseID);
        Course oldCourse = courses.get(i);
        course.setEnrolledStudents(oldCourse.getEnrolledStudents());
        courses.set(i, course);
        saveToFile();
        return true;
    }

    public boolean deleteCourse(String courseID) {
        courses.remove(contains(courseID));
        saveToFile();
        return true;
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
    
    public void studentEnrolled(String studentID, String courseID){
        int i =0;
        for(Course c : courses){
            if(c.getCourseID().equals(courseID)){
                courses.get(i).getEnrolledStudents().add(studentID);
                break;
            }
            i++;
        }
    }
}
