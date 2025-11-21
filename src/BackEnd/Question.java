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

public class Question {
    
    
    private String questionText;
    private ArrayList<String> choices;
    private char correctChoice;

    public Question(String questionText, ArrayList<String> choices, char correctChoice) {
        this.questionText = questionText;
        this.choices = choices;
        this.correctChoice = correctChoice;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(ArrayList<String> choices) {
        this.choices = choices;
    }

    public char getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(char correctChoice) {
        this.correctChoice = correctChoice;
    }
    
    
    
    
    
}
