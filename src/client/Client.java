package client;

import client.UI.ConsoleUI;
import client.UI.UI;

import java.io.IOException;
import java.net.Socket;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8080;
    public static void main(String[] args)
    {
        UI ui = new ConsoleUI();
        try
        {
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            if (!socket.isConnected())
            {
                ui.showErrorMessage("Could not connect to server");
                return;
            }
            MainHandler mainHandler = new MainHandler(socket, ui);
            mainHandler.run();

            socket.close();
        }
        catch (IOException e)
        {
            ui.showErrorMessage("Error happened while connecting to server");
        }
        catch (Exception e)
        {
            ui.showErrorMessage("Error happened while connecting to server");
        }
    }
}
