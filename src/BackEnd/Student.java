

package BackEnd;



import java.util.ArrayList;
import java.util.List;


public class Student extends User {
    private List<String> enrolledCourses;
    
    
    public Student(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Student");
                enrolledCourses = new ArrayList<>();
    }
    
    
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }
     public void enrollCourse(String courseId) {
        if (!enrolledCourses.contains(courseId)) 
            
        {enrolledCourses.add(courseId);
    } }
    
    
}
