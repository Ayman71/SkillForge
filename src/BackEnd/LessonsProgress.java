/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LessonsProgress {

    // Maps courseID -> (lessonID -> completion status)
    private Map<String, Map<String, Boolean>> progressMap;

    public LessonsProgress() {
        progressMap = new HashMap<>();
    }

    // Initialize progress for a course with its lessons (all incomplete by default)
    public void addCourse(String courseID, ArrayList<Lesson> lessons) {
        Map<String, Boolean> lessonStatus = new HashMap<>();
        for (Lesson lesson : lessons) {
            lessonStatus.put(lesson.getId(), false); // not completed
        }
        progressMap.put(courseID, lessonStatus);
    }

    // Mark a lesson as completed
    public void markLessonCompleted(String courseID, String lessonID) {
        if (progressMap.containsKey(courseID)) {
            Map<String, Boolean> lessons = progressMap.get(courseID);
            if (lessons.containsKey(lessonID)) {
                lessons.put(lessonID, true);
            }
        }
    }

    // Get completion status of a lesson
    public boolean isLessonCompleted(String courseID, String lessonID) {
        return progressMap.containsKey(courseID) &&
               progressMap.get(courseID).getOrDefault(lessonID, false);
    }

    // Calculate progress in a course (percentage 0.0 - 100.0)
    public double getCourseProgress(String courseID) {
        if (!progressMap.containsKey(courseID)) return 0.0;
        Map<String, Boolean> lessons = progressMap.get(courseID);
        if (lessons.isEmpty()) return 0.0;

        int completedCount = 0;
        for (Boolean status : lessons.values()) {
            if (status) completedCount++;
        }
        return (completedCount * 100.0) / lessons.size();
    }

    // Get map of lessons and their completion for a course
    public Map<String, Boolean> getCourseLessonProgress(String courseID) {
        return progressMap.getOrDefault(courseID, new HashMap<>());
    }
}

