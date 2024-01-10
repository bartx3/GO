package go.client;

import go.client.UI.UI;
import go.communications.Packet;
import go.communications.SocketFacade;

import java.io.Serializable;
import java.net.SocketException;
import java.util.concurrent.Semaphore;

public class ServerListener extends Thread{
    SocketFacade socket;
    Semaphore semaphore;
    Thread UIThread;
    UI ui;
    public ServerListener(SocketFacade socket, Semaphore semaphore, Thread UIThread, UI ui){
        this.socket = socket;
        this.semaphore = semaphore;
        this.UIThread = UIThread;
        this.ui = ui;
    }

    @Override
    public void run(){
        if (socket == null || semaphore == null){
            return;
        }
        while (true){
            try {
                if (!socket.dataAvailable())
                    continue;
                if (!semaphore.tryAcquire())
                    continue;

                Serializable response = socket.receive();
                if (!(response instanceof Packet command)){
                    semaphore.release();
                    continue;
                }
                executeCommand(command);

                semaphore.release();

            } catch (SocketException e) {
                throw new RuntimeException(e);
            }
        }
    }

    protected void executeCommand(Packet command) throws SocketException{
        if (command.command.equals(Packet.Type.CHALLANGE))
        {
            boolean confirmed = ui.getConfirmation("You have been challanged by " + (String)command.args[0] + " Do you accept?");
            if (confirmed)
                socket.send(new Packet(Packet.Type.ACCEPT_CHALLANGE));
            else
                socket.send(new Packet(Packet.Type.REJECT_CHALLANGE));

            if (confirmed)
                new GameHandler(socket, ui, (String)command.args[0]).run();
        }
    }
}
