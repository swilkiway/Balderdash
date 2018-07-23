package coolness.balderdashclient.Model;

public class Question {
    public Question() {}
    public Question(String m, String u) {
        message = m;
        userName = u;
    }
    private String message;
    private String userName;
    public void setMessage(String m) { message = m; }
    public String getMessage() { return message; }
    public void setUserName(String u) { userName = u; }
    public String getUserName() { return userName; }
}
