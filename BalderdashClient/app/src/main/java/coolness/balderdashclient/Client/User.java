package coolness.balderdashclient.Client;

import java.util.ArrayList;

import coolness.balderdashclient.Model.Answer;
import coolness.balderdashclient.Model.Question;

public class User {
    private static User sUser;

    public static User get(String u, String s) {
        if (sUser == null) {
            sUser = new User(u, s);
        }
        return sUser;
    }
    public static User get() {
        return sUser;
    }
    private User(String u, String s) { userName = u; serverHost = s; }
    private String userName;
    private String serverHost;
    private Question question;
    private Answer[] answers;
    public String getUserName() { return userName; }
    public String getServerHost() { return serverHost; }
    public void setQuestion(Question q) { question = q; }
    public Question getQuestion() { return question; }
    public void setAnswers(Answer[] a) { answers = a; }
    public Answer[] getAnswers() { return answers; }
}
