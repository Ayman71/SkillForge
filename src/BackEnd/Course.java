/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;
/**
 *
 * @author islam
 */
import java.util.ArrayList;
import java.util.List;
public class Course {
    private String courseId;
    private String title;
    private String description;
    private String instructorId;
    private List<Lesson> lessons;
    private List<String> studentIds;
    public Course(String courseId, String title, String description, String instructorId) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
        this.instructorId = instructorId;
        this.lessons = new ArrayList<>();
        this.studentIds = new ArrayList<>();
    }
    public String getCourseId() {
        return courseId;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getInstructorId() {
        return instructorId;
    }
    public List<Lesson> getLessons() {
        return lessons;
    }
    public List<String> getStudentIds() {
        return studentIds;
    }
   
    public void editCourse(String newTitle, String newDescription) {
        this.title = newTitle;
        this.description = newDescription;
    }
    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }
    public void removeLesson(String lessonId) {
        for (int i = 0; i < lessons.size(); i++) {
            if (lessons.get(i).getLessonId().equals(lessonId)) {
                lessons.remove(i);
                break; // Assuming lesson IDs are unique; remove this if multiple removals are needed
            }
        }
    }
    public Lesson getLessonById(String lessonId) {
        for (Lesson l : lessons)
            if (l.getLessonId().equals(lessonId)) return l;
        return null;
    }
    public void enrollStudent(String studentId) {
        if (!studentIds.contains(studentId))
            studentIds.add(studentId);
    }
    @Override
    public String toString() {
        return "Course{" + "courseId=" + courseId + ", title=" + title + ", description=" + description + ", instructorId=" + instructorId + ", lessons=" + lessons + ", studentIds=" + studentIds + '}';
    }
}