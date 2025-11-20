package BackEnd;

/**
 *
 * @author husse
 */
public class Lesson {
    private String id;
    private String title;
    private String content;
    private Quiz quiz;
    public Lesson(String id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Quiz getQuiz() {
        return quiz;
    }
    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    public boolean isCompletedForStudent(Student student, String courseId) {
        return student.hasPassedQuiz(courseId, id);
    }
}