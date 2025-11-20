/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BackEnd;

/**
 *
 * @author husse
 */
import java.util.ArrayList;

public class Quiz {

    private String quizID;
    private ArrayList<Question> questions;

    public Quiz(String quizID, ArrayList<Question> questions) {
        this.quizID = quizID;
        this.questions = questions;
    }

    public String getQuizID() {
        return quizID;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question q) {
        if (questions == null) {
            questions = new ArrayList<>();
        }
        questions.add(q);
    }

}
