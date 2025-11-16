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

public class Lesson {
    private String lessonId;
    private String title;
    private String content;
    private List<String> resources;

    public Lesson(String lessonId, String title, String content) {
        this.lessonId = lessonId;
        this.title = title;
        this.content = content;
        this.resources = new ArrayList<>();
    }

    public String getLessonId() {
        return lessonId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<String> getResources() {
        return resources;
    }

    public void addResource(String r) {
        resources.add(r);
    }

    public void editLesson(String newTitle, String newContent) {
        this.title = newTitle;
        this.content = newContent;
    }

    @Override
    public String toString() {
        return "Lesson{" + "lessonId=" + lessonId + ", title=" + title + ", content=" + content + ", resources=" + resources + '}';
    }

 
}

