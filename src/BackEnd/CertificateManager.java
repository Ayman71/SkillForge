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
    private ArrayList<Certificate> certificates;

    public CertificateManager(CourseManager courseManager, UserManager userManager) {
        this.userManager = userManager;
        this.courseManager = courseManager;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

    }

    public void setCertificates(ArrayList<Certificate> certificates) {
        this.certificates = certificates;
    }

    private void saveCertificatesToFile(Certificate certificate) {
        try {
            String id = certificate.getCertificateId();

            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Certificate ID cannot be empty");
            }

            String filename = id + ".json";

            try (FileWriter writer = new FileWriter(filename)) {
                gson.toJson(certificate, writer);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int generateCertificate(String studentId, String courseId) {
        Student student = (Student) userManager.getUserFromID(studentId);
        Course course = courseManager.getCourseFromCourseID(courseId);

        if (!student.isCourseCompleted(courseId, course)) {
            return -1;
        }
        if (certificateExistsForStudentCourse(studentId, courseId)) {
            return 0;
        }
        String certId = studentId + " CERTIFICATE FOR COURSE " + courseId;
        Certificate certificate = new Certificate(certId, studentId, courseId, student.getLessonsProgressByCourse(courseId));
        student.addCertificate(certificate);
        userManager.saveToFile();
        saveCertificatesToFile(certificate);
        return 1;
    }

    public ArrayList<Certificate> getAllCertificates() {
        return certificates;
    }

    public boolean certificateExists(String certificateID) {
        for (Certificate certificate : certificates) {
            if (certificate.getCertificateId().equals(certificateID)) {
                return true;
            }
        }
        return false;
    }
    public boolean certificateExistsForStudentCourse(String studentId, String courseId) {
    for (Certificate certificate : certificates) {
        if (certificate.getStudentId().equals(studentId) &&
            certificate.getCourseId().equals(courseId)) {
            return true;
        }
    }
    return false;
}

    public Certificate getCertificate(String certificateId) {
        for (Certificate certificate : certificates) {
            if (certificate.getCertificateId().equals(certificateId)) {
                return certificate;
            }
        }
        return null;
    }
}
