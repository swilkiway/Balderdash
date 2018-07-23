package coolness.balderdashserver.Model;

public class Answer {
    public Answer() {}
    public Answer(String m, String u, String c) {
        message = m;
        userName = u;
        correct = c;
        votes = 0;
    }
    public String getMessage() { return message; }
    public void setMessage(String m) { message = m; }
    public String getUserName() { return userName; }
    public void setUserName(String u) { userName = u; }
    public String isCorrectAnswer() { return correct; }
    public void setCorrect(String c) { correct = c; }
    public void setVotes(int v) { votes = v; }
    private String message;
    private String userName;
    private String correct;
    private int votes;
}
