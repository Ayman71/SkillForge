package BackEnd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LessonsProgress {
    private Map<String, Map<String, Boolean>> progressMap;   
    private Map<String, Map<String, Boolean>> quizStatusMap; 
    public LessonsProgress() {
        progressMap = new HashMap<>();
        quizStatusMap = new HashMap<>();}
    public void addCourse(String courseID, ArrayList<Lesson> lessons) {
        Map<String, Boolean> lessonStatus = new HashMap<>();
        Map<String, Boolean> quizStatus = new HashMap<>();
        for (Lesson lesson : lessons) {
            String id = lesson.getId();
            lessonStatus.put(id, false);
            quizStatus.put(id, false);
        }
        progressMap.put(courseID, lessonStatus);
        quizStatusMap.put(courseID, quizStatus);
    }
    public Map<String, Map<String, Boolean>> getProgressMap() {
        return progressMap;
    }
    public void setProgressMap(Map<String, Map<String, Boolean>> progressMap) {
        this.progressMap = progressMap != null ? progressMap : new HashMap<>();
    }
    public Map<String, Map<String, Boolean>> getQuizStatusMap() {
        return quizStatusMap;
    }
    public void setQuizStatusMap(Map<String, Map<String, Boolean>> quizStatusMap) {
        this.quizStatusMap = quizStatusMap != null ? quizStatusMap : new HashMap<>();
    }
    public void markLessonCompleted(String courseID, String lessonID) {
        if (!progressMap.containsKey(courseID)) return;
        Map<String, Boolean> lessons = progressMap.get(courseID);
        if (!lessons.containsKey(lessonID)) return;
        if (isQuizPassed(courseID, lessonID)) {
            lessons.put(lessonID, true);
        }
    }
    public boolean isLessonCompleted(String courseID, String lessonID) {
        return progressMap.containsKey(courseID)
                && progressMap.get(courseID).getOrDefault(lessonID, false);
    }
    public double getCourseProgress(String courseID) {
        if (!progressMap.containsKey(courseID)) return 0.0;
        Map<String, Boolean> lessons = progressMap.get(courseID);
        if (lessons.isEmpty()) return 0.0;
        int completed = 0;
        for (boolean status : lessons.values()) {
            if (status) completed++;
        }
        return (completed * 100.0) / lessons.size();
    }
    public Map<String, Boolean> getCourseLessonProgress(String courseID) {
        return progressMap.getOrDefault(courseID, new HashMap<>());
    }
    public void syncWithCourse(String courseID, ArrayList<Lesson> updatedLessons) {
        Map<String, Boolean> oldLessons = progressMap.getOrDefault(courseID, new HashMap<>());
        Map<String, Boolean> oldQuiz = quizStatusMap.getOrDefault(courseID, new HashMap<>());
        Map<String, Boolean> newLessonMap = new HashMap<>();
        Map<String, Boolean> newQuizMap = new HashMap<>();
        for (Lesson lesson : updatedLessons) {
            String id = lesson.getId();
            newLessonMap.put(id, oldLessons.getOrDefault(id, false));
            newQuizMap.put(id, oldQuiz.getOrDefault(id, false));
        }
        progressMap.put(courseID, newLessonMap);
        quizStatusMap.put(courseID, newQuizMap);
    }
    public void updateQuizStatus(String courseID, String lessonID, boolean passed) {
        if (!quizStatusMap.containsKey(courseID)) return;
        Map<String, Boolean> quizzes = quizStatusMap.get(courseID);
        if (!quizzes.containsKey(lessonID)) return;
        quizzes.put(lessonID, passed);
        if (passed) {
            markLessonCompleted(courseID, lessonID);
        }
    }
    public boolean isQuizPassed(String courseID, String lessonID) {
        return quizStatusMap.containsKey(courseID)
                && quizStatusMap.get(courseID).getOrDefault(lessonID, false);
    }
    public boolean canAccessNextLesson(
            String courseID,
            String currentLessonID,
            ArrayList<Lesson> lessons
    ) {
        int index = -1;
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getId().equals(currentLessonID)) {
                index = i;
                break;
            }
        }
        if (index == -1) return true;            
        if (index == lessons.size() - 1) return true;
        return isQuizPassed(courseID, currentLessonID);
    }
}
