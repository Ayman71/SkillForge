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
            return true;
        }
        return false;
    }

    public boolean modifyCourse(Course course) {
        int i = contains(course.getCourseID());
        Course oldCourse = courses.get(i);
        course.setEnrolledStudents(oldCourse.getEnrolledStudents());
        courses.set(i, course);
        return true;
    }
    
    public boolean updateLessons(String courseID,ArrayList<Lesson> lessons){
        courses.get(contains(courseID)).setLessons(lessons);
        return true;
    }
    
    public ArrayList<Course> getCourses(){
        return courses;
    }
}
