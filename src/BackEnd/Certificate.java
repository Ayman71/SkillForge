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
import java.util.Map;

public class Certificate {

    private String certificateId;
    private String issueDate;
    private String studentId;
    private String courseId;
    private Map<String, Double> performance;

    public Certificate(String certificateId, String studentId, String courseId, Map<String, Double> performance) {
        this.issueDate = LocalDate.now().toString();
        this.studentId = studentId;
        this.courseId = courseId;
        this.certificateId = certificateId;
        this.performance = performance;
    }

    public Map<String, Double> getPerformance() {
        return performance;
    }

    public void setPerformance(Map<String, Double> performance) {
        this.performance = performance;
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
