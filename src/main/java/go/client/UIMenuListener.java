package go.client;

import go.client.UI.UI;
import go.communications.Packet;
import go.communications.SocketFacade;

import java.net.SocketException;
import java.util.concurrent.Semaphore;

public class UIMenuListener extends Thread{
    SocketFacade socket;
    Semaphore semaphore;
    UI ui;
    public UIMenuListener(SocketFacade socket, Semaphore semaphore, UI ui){
        this.socket = socket;
        this.semaphore = semaphore;
        this.ui = ui;
    }

    @Override
    public void run(){
        while (true) {
            try {
                Packet command = ui.getCommand();
                semaphore.acquire();
                block_all();
                lock();
                executeCommand(command);
            } catch (SocketException | InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                semaphore.release();
            }
        }
    }

    private void executeCommand(Packet command) throws SocketException {
        switch (command.command) {
            case CHALLANGE:
                socket.send(command);
                if (!(socket.receive() instanceof Packet packet))
                    throw new RuntimeException("Expected packet");
                if (packet.command.equals(Packet.Type.BLOCKED) || packet.command.equals(Packet.Type.REJECT_CHALLANGE))
                    ui.promptMessage("Challange rejected");
                else if (packet.command.equals(Packet.Type.ACCEPT_CHALLANGE)) {
                    ui.promptMessage("Challange accepted");
                    new GameHandler(socket, ui, (String) command.args[0]).run();
                }
                break;
        }
    }

    private void block_all() throws SocketException {
        while (socket.dataAvailable()){
            try {
                socket.receive();
                socket.send(new Packet(Packet.Type.BLOCKED));
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }

    private void lock() throws SocketException {
        socket.send(new Packet(Packet.Type.LOCK));
        Packet command = (Packet) socket.receive();
        while (!command.command.equals(Packet.Type.LOCKED)){
            try {
                socket.send(new Packet(Packet.Type.BLOCKED));
                command = (Packet) socket.receive();
            }
            catch (Exception e){
                throw new RuntimeException(e);
            }
        }
    }
}
