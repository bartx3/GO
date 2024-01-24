package go.server.ClientHandling;

import go.communications.SocketFacade;

public class LoginHandler implements Runnable {
        SocketFacade socket;
        String name;
        public LoginHandler(SocketFacade socket) {
            this.socket = socket;
        }

        public String getName() {
            return name;
        }
        @Override
        public void run() {

        }
}
