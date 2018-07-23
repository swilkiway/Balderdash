package coolness.balderdashclient.Model;

import coolness.balderdashclient.Model.Question;

public class ReadyResponse {
    private boolean ready;
    private Question question;
    public boolean isReady() { return ready; }
    public void setQuestion(Question q) { question = q; }
    public Question getQuestion() { return question; }
}
