package coolness.balderdashclient.Model;

public class RegisterRequest {
    public RegisterRequest() {}
    public RegisterRequest(String u) {
        userName = u;
        points = 0;
    }
    public void setUserName(String u) { userName = u; }
    private String userName;
    private int points;
}
