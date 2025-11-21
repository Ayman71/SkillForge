/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author husse
 */

import java.time.LocalDate;

public class Certificate {
    
        private String issueDate;
        private String studentId;  
        private String courseId;
            private String certificateId;

    public Certificate( String certificateId, String studentId, String courseId) {
        this.issueDate = LocalDate.now().toString();
        this.studentId = studentId;
        this.courseId = courseId;
        this.certificateId = certificateId;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
    }
            
        
        
        
}
