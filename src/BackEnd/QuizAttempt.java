package BackEnd;

/**
 *
 * @author husse
 */
public class QuizAttempt {

    private String quizId;
    private int attemptNumber;
    private double score;

    public QuizAttempt(String quizId, int attemptNumber, double score) {
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public void AddAttempt() {
        this.attemptNumber++;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }

}
