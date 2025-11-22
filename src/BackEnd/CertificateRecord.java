/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author husse
 */
public class CertificateRecord {
    
       private String certificateId;
    private String studentId;
    private String courseId;
    private String issueDate;
    private int latestQuizScore;

    public CertificateRecord(String certificateId, String studentId, String courseId, String issueDate, int latestQuizScore) {
        this.certificateId = certificateId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.issueDate = issueDate;
        this.latestQuizScore = latestQuizScore;
    }

    public String getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(String certificateId) {
        this.certificateId = certificateId;
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

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public int getLatestQuizScore() {
        return latestQuizScore;
    }

    public void setLatestQuizScore(int latestQuizScore) {
        this.latestQuizScore = latestQuizScore;
    }
    
    
    
    
    
    
    
}
