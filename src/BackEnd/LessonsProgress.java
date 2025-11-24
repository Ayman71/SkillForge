/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LessonsProgress {

    private Map<String, Map<String, Double>> progressMap;
    
    public LessonsProgress() {
        progressMap = new HashMap<>();
    }

    public void addCourse(String courseID, ArrayList<Lesson> lessons) {
        Map<String, Double> lessonStatus = new HashMap<>();
        for (Lesson lesson : lessons) {
            lessonStatus.put(lesson.getId(), 0.0); // not completed
        }
        progressMap.put(courseID, lessonStatus);
    }

    public Map<String, Map<String, Double>> getProgressMap() {
        return progressMap;
    }

    public void setProgressMap(Map<String, Map<String, Double>> progressMap) {
        this.progressMap = progressMap;
    }

    public void setLessonScore(String courseID, String lessonID, double score) {
        if (progressMap.containsKey(courseID)) {
            Map<String, Double> lessons = progressMap.get(courseID);
            if (lessons.containsKey(lessonID)) {
                lessons.put(lessonID, score);
            }
        }
    }

    public double getLessonScore(String courseID, String lessonID) {
         if(progressMap.containsKey(courseID)){
             return progressMap.get(courseID).getOrDefault(lessonID,0.0);
         }
         return 0.0;       
    }

    public double getCourseProgress(String courseID) {
        if (!progressMap.containsKey(courseID)) {
            return 0.0;
        }
        Map<String, Double> lessons = progressMap.get(courseID);
        if (lessons.isEmpty()) {
            return 0.0;
        }

        int completedCount = 0;
        for (double score : lessons.values()) {
            if (score>=50.0) {
                completedCount++;
            }
        }
        return (completedCount * 100) / lessons.size();
    }
    public Map<String, Double> getLessonsProgress(String courseID){
        return progressMap.get(courseID);
    }

    public Map<String, Double> getCourseLessonProgress(String courseID) {
        return progressMap.getOrDefault(courseID, new HashMap<>());
    }

    public void syncWithCourse(String courseID, ArrayList<Lesson> updatedLessons) {

        Map<String, Double> lessonStatus = progressMap.get(courseID);
        if (lessonStatus == null) {
            lessonStatus = new HashMap<>();
            progressMap.put(courseID, lessonStatus);
        }

        Map<String, Double> newMap = new HashMap<>();

        for (Lesson lesson : updatedLessons) {
            String lessonID = lesson.getId();
            double old = lessonStatus.getOrDefault(lessonID, 0.0);
            newMap.put(lessonID, old);
        }

        progressMap.put(courseID, newMap);
    }
}
