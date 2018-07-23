package coolness.balderdashclient.Model;

public class Register {
    public Register() {}
    public Register(String sh, String u) {
        serverHost = sh;
        userName = u;
    }
    private String serverHost = "";
    private String userName = "";
    public void setServerHost(String s) { serverHost = s; }
    public String getServerHost() { return serverHost; }
    public void setUserName(String s) { userName = s; }
    public String getUserName() { return userName; }
    public boolean confirmRegister() {
        if (serverHost.equals("") || userName.equals("")) {
            return false;
        } else {
            return true;
        }
    }
}

