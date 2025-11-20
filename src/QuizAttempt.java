/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author husse
 */


public class QuizAttempt {
    
        private String quizId;
    private int attemptNumber;
    private int score;
    
    
    
     public QuizAttempt(String quizId, int attemptNumber, int score) {
        this.quizId = quizId;
        this.attemptNumber = attemptNumber;
        this.score = score;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
