/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author husse
 */
public class CertificateManager {
    
    private UserManager userManager;
    private CourseManager courseManager;

    public CertificateManager(UserManager userManager, CourseManager courseManager) {
        this.userManager = userManager;
        this.courseManager = courseManager;
    }
    
      public Certificate generateCertificate(String studentId, String courseId) {
        Student student = (Student) userManager.getUserFromID(studentId);
        Course course = courseManager.getCourseFromCourseID(courseId);
        
        if (student == null || course == null) {
            return null;
        }
        
        if (!student.isCourseCompleted(courseId, course)) {
            return null;
        }
        
        if (student.haveExisitingCertificateForCourse(courseId) != null) {
            return null;
        }
        
        String certId = "CERTIFICATE-" + studentId + "-" + courseId;
        Certificate certificate = new Certificate(certId, studentId, courseId);
        student.addCertificate(certificate);
        userManager.saveToFile();
        
        return certificate;
    }
    
      
    
}
