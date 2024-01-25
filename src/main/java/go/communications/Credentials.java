package go.communications;

public class Credentials implements java.io.Serializable {
    private final String username;
    private final String password;

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Credentials(String username) {
        this.username = username;
        this.password = null;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
