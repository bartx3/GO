package go.server.ClientHandler;

import go.client.GameHandler;
import go.communications.Packet;
import go.communications.SocketFacade;
import go.server.Server;

import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {
    Logger logger = Logger.getLogger(RequestHandler.class.getName());
    SessionData sessionData;
    public RequestHandler(SessionData sessionData) {
        this.sessionData = sessionData;
    }
    public void run() {
        try {
            SocketFacade socket = sessionData.socket;
            while (true) {
                try {
                    if (!socket.dataAvailable())
                        continue;
                    if (!sessionData.semaphore.tryAcquire())
                        continue;
                    if (!(sessionData.socket.receive() instanceof Packet request)) {
                        logger.warning("Unexpected object received");
                        continue;
                    }
                    if (request.command.equals(Packet.Type.LOCK)) {
                        if (sessionData.isPlaying) {
                            sessionData.socket.send("You are already playing");
                        } else {
                            sessionData.socket.send("ok");
                            sessionData.isPlaying = true;
                            sessionData.observer.start();
                        }
                    } else if (request.command.equals(Packet.Type.CHALLANGE)) {
                        String opponent = (String) request.args[0];
                        if (!Server.usersOnline.containsKey(opponent)) {
                            sessionData.socket.send(new Packet(Packet.Type.REJECT_CHALLANGE));
                            sessionData.semaphore.release();
                            continue;
                        }
                        SessionData opponentData = Server.usersOnline.get(opponent);
                        if (opponentData.isPlaying || !opponentData.semaphore.tryAcquire()) {
                            sessionData.socket.send(new Packet(Packet.Type.REJECT_CHALLANGE));
                            sessionData.semaphore.release();
                            continue;
                        }
                        opponentData.socket.send(new Packet(Packet.Type.CHALLANGE, sessionData.username));
                        if (!(opponentData.socket.receive() instanceof Packet response)) {
                            logger.warning("Unexpected object received");
                            sessionData.semaphore.release();
                            continue;
                        }
                        if (response.command.equals(Packet.Type.ACCEPT_CHALLANGE)) {
                            sessionData.socket.send(new Packet(Packet.Type.ACCEPT_CHALLANGE));
                            sessionData.isPlaying = true;
                            opponentData.isPlaying = true;
                            new SGameHandler(sessionData, opponentData).run();
                        } else if (response.command.equals(Packet.Type.REJECT_CHALLANGE)) {
                            sessionData.socket.send(new Packet(Packet.Type.REJECT_CHALLANGE));
                        } else {
                            logger.warning("Unexpected object received");
                        }
                        opponentData.semaphore.release();
                    } else {
                        logger.warning("Unexpected object received");
                    }
                    sessionData.semaphore.release();
                }
                catch (SocketException e){
                    sessionData.semaphore.release();
                    if (socket.isConnected())
                        continue;
                    else
                        break;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
