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
            int completed = 0;
            ArrayList<String> students = course.getEnrolledStudents();
            for (String studentID : students) {
                if (courseManager.getStudentCourseProgress(course.getCourseID(), studentID) == 100.0) {
                    completed++;
                }
            }

            int total = students.size();

            double percentage = total == 0 ? 0 : ((double) completed / total) * 100;

            map.put(course.getCourseID(), percentage);
        }
        return map;
    }

    public HashMap<String, Double> getStudentPerformanceMap(Course course) {
        HashMap<String, Double> map = new HashMap<String, Double>();
        ArrayList<String> students = course.getEnrolledStudents();

        for (String s : students) {
            double studentAvg = 0;
            Student student = (Student) userManager.getUserFromID(s);
            for (double score : student.getLessonsProgressByCourse(course.getCourseID()).values()) {
                studentAvg += score;
            }
            map.put(s, studentAvg / student.getLessonsProgressByCourse(course.getCourseID()).size());
        }
        return map;
    }

    public HashMap<String, Double> getQuizAverageMap(Course course) {
        HashMap<String, Double> map = new HashMap<String, Double>();
        ArrayList<Lesson> lessons = course.getLessons();
        ArrayList<String> students = course.getEnrolledStudents();
        for (int i = 0; i < lessons.size(); i++) {
            double lessonAvg = 0;
            for (String s : students) {
                lessonAvg += ((Student) userManager.getUserFromID(s)).getLessonsProgressByCourse(course.getCourseID()).get(lessons.get(i).getId());
            }
            map.put(lessons.get(i).getId(), lessonAvg / students.size());
        }
        return map;
    }

}
