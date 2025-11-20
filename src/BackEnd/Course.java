package BackEnd;

import java.util.ArrayList;
import java.util.Date;

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
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;
    private String approvedBy;
    private Date approvalDate;
    public Course(String courseID, String title, String description, String instructorId, ArrayList<Lesson> lessons, ArrayList<String> enrolledStudents) {
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
    public ArrayList<String> getEnrolledStudents() {
        if (enrolledStudents == null) {
            return new ArrayList<String>();
        }
        return enrolledStudents;
    }
    public void setEnrolledStudents(ArrayList<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }
    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }
    public void setApprovalStatus(ApprovalStatus status, String adminId) {
        this.approvalStatus = status;
        this.approvedBy = adminId;
        this.approvalDate = new Date();
    }
    public boolean isApproved() {
        return approvalStatus == ApprovalStatus.APPROVED;
    }
}
enum ApprovalStatus {
    PENDING, APPROVED, REJECTED
}