/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author T
 */
public class AnalyticsManager {

    public enum ChartType {
        STUDENT_PERFORMANCE,
        QUIZ_AVERAGE,
        COURSE_COMPLETION
    }
    CourseManager courseManager;
    UserManager userManager;
    String instructorID;

    public AnalyticsManager(String instructorID, CourseManager courseManager, UserManager userManager) {
        this.courseManager = courseManager;
        this.userManager = userManager;
        this.instructorID = instructorID;
    }

    public HashMap<String, Double> getCourseCompletionMap() {
        HashMap<String, Double> map = new HashMap<String, Double>();
        for (Course course : courseManager.getCoursesFromInstructor(instructorID)) {
            double completed = 0;

            for (String studentID : course.getEnrolledStudents()) {
                if (courseManager.getStudentCourseProgress(studentID, course.getCourseID()) == 100.0) {
                    completed++;
                }
            }
            map.put(course.getCourseID(), completed / course.getEnrolledStudents().size());
        }
        return map;
    }

    public HashMap<String, Double> getStudentPerformanceMap() {
        HashMap<String, Double> map = new HashMap<String, Double>();
        
        
        
        
        return map;
    }
}
