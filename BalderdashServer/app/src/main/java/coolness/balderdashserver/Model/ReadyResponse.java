package coolness.balderdashserver.Model;

public class ReadyResponse {
    public ReadyResponse() {}
    public ReadyResponse(boolean r, Question q) {
        ready = r;
        question = q;
    }
    private boolean ready;
    private Question question;
    public boolean ifReady() { return ready; }
    public void setQuestion(Question q) { question = q; }
}
