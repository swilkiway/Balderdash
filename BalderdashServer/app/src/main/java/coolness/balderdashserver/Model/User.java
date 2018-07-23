package coolness.balderdashserver.Model;

public class User {
    public User() {}
    public User(String u) {
        userName = u;
        points = 0;
    }
    public String getUsername() { return userName; }
    public void setUsername(String u) { userName = u; }
    public int getPoints() { return points; }
    public void setPoints(int p) { points = p; }
    private String userName;
    private int points;
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) { return false; }
        if (((User) o).getUsername().equals(getUsername())) { return true; }
        else { return false; }
    }
}

