/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.ArrayList;

/**
 *
 * @author husse
 */
public class Course {
    private String courseID;
    private String title;
    private String description;
    private String instructorId;
    private ArrayList<Lesson> lessons;
    private ArrayList<User> enrolledStudents;

    public Course(String courseID, String title, String description, String instructorId, ArrayList<Lesson> lessons) {
        this.courseID = courseID;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = lessons;
    }

    public Course(String courseID, String title, String description, String instructorId, ArrayList<Lesson> lessons, ArrayList<User> enrolledStudents) {
        this.courseID = courseID;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = lessons;
        this.enrolledStudents = enrolledStudents;
    }
    

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(String instructorId) {
        this.instructorId = instructorId;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<User> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(ArrayList<User> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    
    
    
}
