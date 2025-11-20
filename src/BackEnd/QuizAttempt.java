package BackEnd;




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

 
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

   
      public void AddAttempt() {
        this.attemptNumber++;
    }
    
    
    
    
    
    
     
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

