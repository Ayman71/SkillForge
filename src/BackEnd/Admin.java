/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author husse
 */

public class Admin extends User {
    
      public Admin(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Admin");
    }
    
     public void approveCourse(Course course) {
        course.setApprovalStatus("Approved");
        
    }
       public void rejectCourse(Course course) {
        course.setApprovalStatus("Rejected");
      
    }
}
