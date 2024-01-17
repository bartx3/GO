package go.communications;

public class PlayerCredentials {
    private final String username;
    private final String password;

    public PlayerCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public PlayerCredentials(String username) {
        this.username = username;
        this.password = null;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
}
