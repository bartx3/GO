package go.communications;

public class Request implements java.io.Serializable{
    public final String request;
    public final String[] args;

    public Request(String request, String... args) {
        this.request = request;
        this.args = args;
    }
}
