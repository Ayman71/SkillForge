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
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.util.ArrayList;

public class CertificateManager {

    private UserManager userManager;
    private CourseManager courseManager;

    private Gson gson;
    private ArrayList<CertificateRecord> certificateRecords;

    public CertificateManager(UserManager userManager, CourseManager courseManager) {
        this.userManager = userManager;
        this.courseManager = courseManager;
  
        this.gson = new GsonBuilder().setPrettyPrinting().create();

    
    }

  

    private void saveCertificatesToFile(CertificateRecord record) {
       try {
            String filename = record.getCertificateId() + ".json";
            FileWriter writer = new FileWriter(filename);
            gson.toJson(record, writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        int latestQuizScore = getLatestQuizScore(student, course);

        CertificateRecord record = new CertificateRecord(certId, studentId, courseId,certificate.getIssueDate(), latestQuizScore);
        saveCertificatesToFile(record);
        return certificate;
    }

    private int getLatestQuizScore(Student student, Course course) {
        ArrayList<Lesson> lessons = course.getLessons();
        if (lessons == null) {
            return 0;
        }
        int latestScore = 0;
        for (Lesson lesson : lessons) {
            if (lesson.getQuiz() != null) {
                String quizId = lesson.getQuiz().getQuizID();
                int score = student.getQuizScore(quizId);
                latestScore = score;
            }
        }

        return latestScore;
    }

    public ArrayList<CertificateRecord> getAllCertificateRecords() {
        return certificateRecords;
    }

    public CertificateRecord getCertificateRecord(String certificateId) {
        for (CertificateRecord record : certificateRecords) {
            if (record.getCertificateId().equals(certificateId)) {
                return record;
            }
        }
        return null;
    }
}
