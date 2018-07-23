package coolness.balderdashclient.Model;

public class Answer {
    public Answer() { votes = 0; }
    private String message;
    private String userName;
    private String correct;
    private int votes;
    public void setMessage(String m) { message = m; }
    public String getMessage() { return message; }
    public void setUserName(String u) { userName = u; }
    public void setCorrect(String c) { correct = c; }
}
