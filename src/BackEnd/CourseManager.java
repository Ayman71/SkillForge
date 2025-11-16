/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author husse
 */
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CourseManager {

    private List<Course> courses;
    private String filename;
    private Gson gson;

    public CourseManager(String filename) {
        this.filename = filename;
        this.gson = new Gson();
        this.courses = new ArrayList<>();
        readFromFile();
    }
    public void readFromFile() {
        try (FileReader reader = new FileReader(filename)) {
            courses = gson.fromJson(reader, new TypeToken<List<Course>>() {}.getType());
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

    public boolean createCourse(String courseId, String title, String description, String instructorId) {
        if (getCourseById(courseId) != null) {
            return false;
        }

        Course c = new Course(courseId, title, description, instructorId);
        courses.add(c);
        saveToFile();
        return true;
    }

    public Course getCourseById(String courseId) {
        for (Course c : courses) {
            if (c.getCourseId().equals(courseId)) {
                return c;
            }
        }
        return null;
    }

    public boolean editCourse(String courseId, String newTitle, String newDescription) {
        Course c = getCourseById(courseId);
        if (c == null) return false;

        c.editCourse(newTitle, newDescription);
        saveToFile();
        return true;
    }

    public boolean deleteCourse(String courseId) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCourseId().equals(courseId)) {
                courses.remove(i);
                saveToFile();
                return true;
            }
        }
        return false;
    }

    public List<Course> getAllCourses() {
        return courses;
    }

    public boolean addLesson(String courseId, String lessonId, String title, String content) {
        Course c = getCourseById(courseId);
        if (c == null) return false;

        if (c.getLessonById(lessonId) != null) return false; 

        Lesson lesson = new Lesson(lessonId, title, content);
        c.addLesson(lesson);
        saveToFile();
        return true;
    }

    public boolean removeLesson(String courseId, String lessonId) {
        Course c = getCourseById(courseId);
        if (c == null) return false;

        c.removeLesson(lessonId);
        saveToFile();
        return true;
    }

    public List<Lesson> getLessons(String courseId) {
        Course c = getCourseById(courseId);
        if (c == null) return new ArrayList<>();
        return c.getLessons();
    }

    public boolean enrollStudent(String courseId, String studentId) {
        Course c = getCourseById(courseId);
        if (c == null) return false;

        c.enrollStudent(studentId);
        saveToFile();
        return true;
    }

    public List<String> getStudents(String courseId) {
        Course c = getCourseById(courseId);
        if (c == null) return new ArrayList<>();
        return c.getStudentIds();
    }
    }
      
