package BackEnd;

import java.util.ArrayList;

public class Instructor extends User {
    private ArrayList<String> createdCourses;
    public Instructor(String userId, String username, String email, String passwordHash) {
        super(userId, username, email, passwordHash, "Instructor");
        this.createdCourses = new ArrayList<>();
    }
    public ArrayList<String> getCreatedCourses() {
        return createdCourses;
    }
    public void setCreatedCourses(ArrayList<String> createdCourses) {
        this.createdCourses = createdCourses;
    }
    public void addCourse(String courseId) {
        if (courseId != null && !createdCourses.contains(courseId)) {
            createdCourses.add(courseId);
        }
    }
}
