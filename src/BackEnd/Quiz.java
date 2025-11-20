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
      private String question;
    private ArrayList<String> options; 
    private char correctOption; 
    private ArrayList<Character> studentChoice;
    
    
    
    
    
      public Quiz(String question, ArrayList<String> options, char correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
        this.studentChoice = new ArrayList<>();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public void setOptions(ArrayList<String> options) {
        this.options = options;
    }

    public char getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(char correctOption) {
        this.correctOption = correctOption;
    }

    public ArrayList<Character> getStudentChoice() {
        return studentChoice;
    }

    public void setStudentChoice(ArrayList<Character> studentChoice) {
        this.studentChoice = studentChoice;
    }

    
    
  
    }
    
    
    
    
    
    
    
}
