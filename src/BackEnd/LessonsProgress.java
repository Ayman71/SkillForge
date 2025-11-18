/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LessonsProgress {

    private Map<String, Map<String, Boolean>> progressMap;

    public LessonsProgress() {
        progressMap = new HashMap<>();
    }

    public void addCourse(String courseID, ArrayList<Lesson> lessons) {
        Map<String, Boolean> lessonStatus = new HashMap<>();
        for (Lesson lesson : lessons) {
            lessonStatus.put(lesson.getId(), false); // not completed
        }
        progressMap.put(courseID, lessonStatus);
    }

    public Map<String, Map<String, Boolean>> getProgressMap() {
        return progressMap;
    }

    public void setProgressMap(Map<String, Map<String, Boolean>> progressMap) {
        this.progressMap = progressMap;
    }

    public void markLessonCompleted(String courseID, String lessonID) {
        if (progressMap.containsKey(courseID)) {
            Map<String, Boolean> lessons = progressMap.get(courseID);
            if (lessons.containsKey(lessonID)) {
                lessons.put(lessonID, true);
            }
        }
    }

    public boolean isLessonCompleted(String courseID, String lessonID) {
        return progressMap.containsKey(courseID)
                && progressMap.get(courseID).getOrDefault(lessonID, false);
    }

    public double getCourseProgress(String courseID) {
        if (!progressMap.containsKey(courseID)) {
            return 0.0;
        }
        Map<String, Boolean> lessons = progressMap.get(courseID);
        if (lessons.isEmpty()) {
            return 0.0;
        }

        int completedCount = 0;
        for (Boolean status : lessons.values()) {
            if (status) {
                completedCount++;
            }
        }
        return (completedCount*100) / lessons.size();
    }

    public Map<String, Boolean> getCourseLessonProgress(String courseID) {
        return progressMap.getOrDefault(courseID, new HashMap<>());
    }

    public void syncWithCourse(String courseID, ArrayList<Lesson> updatedLessons) {

        Map<String, Boolean> lessonStatus = progressMap.get(courseID);
        if (lessonStatus == null) {
            lessonStatus = new HashMap<>();
            progressMap.put(courseID, lessonStatus);
        }

        Map<String, Boolean> newMap = new HashMap<>();

        for (Lesson lesson : updatedLessons) {
            String lessonID = lesson.getId();
            boolean old = lessonStatus.getOrDefault(lessonID, false);
            newMap.put(lessonID, old);
        }

        progressMap.put(courseID, newMap);
    }
}
