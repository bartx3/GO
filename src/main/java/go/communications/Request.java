package go.communications;

public class Request implements java.io.Serializable{
    public final String command;
    public final String[] args;

    public Request(String request, String... args) {
        this.command = request;
        this.args = args;
    }
}
