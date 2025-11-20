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
    private ArrayList<String> enrolledStudents;
    private String approvalStatus;
    private String approverId;
    private String approvalDate;

    public Course(String courseID, String title, String description, String instructorId, ArrayList<Lesson> lessons, ArrayList<String> enrolledStudents) {
        this.courseID = courseID;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = lessons;
        this.enrolledStudents = enrolledStudents;
        
         this.approvalStatus = "Pending";
        this.approverId = null;
        this.approvalDate = null;
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
        if (lessons == null)
        { return new ArrayList<>();}
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<String> getEnrolledStudents() {
        if(enrolledStudents == null){
            return new ArrayList<String>();
        }
        return enrolledStudents;
    }

    public void setEnrolledStudents(ArrayList<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    
    public String getApprovalStatus()
    { return approvalStatus; }
    public void setApprovalStatus(String approvalStatus)
    { this.approvalStatus = approvalStatus; }

    public String getApproverId()
    { return approverId; }
    public void setApproverId(String approverId)
    { this.approverId = approverId; }

    public String getApprovalDate()
    { return approvalDate; }
    
    public void setApprovalDate(String approvalDate)
    { this.approvalDate = approvalDate; }

    
}
