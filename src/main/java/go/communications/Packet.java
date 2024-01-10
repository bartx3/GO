package go.communications;

import java.io.Serializable;
import java.util.Map;

public class Packet implements java.io.Serializable{
    public enum Type {
        GETLIST,
        CHALLANGE,
        EXIT,
        BLOCKED,
        LOCK,
        LOCKED,
        ACCEPT_CHALLANGE,
        REJECT_CHALLANGE,
};
    public static Map<String, Type> commandMap = Map.of(
            "GETLIST", Type.GETLIST,
            "CHALLANGE", Type.CHALLANGE,
            "EXIT", Type.EXIT,
            "BLOCKED", Type.BLOCKED,
            "ACCEPT_CHALLANGE", Type.ACCEPT_CHALLANGE,
            "REJECT_CHALLANGE", Type.REJECT_CHALLANGE,
            "LOCK", Type.LOCK,
            "LOCKED", Type.LOCKED
    );
    public Type command;
    public Serializable[] args;

    public Packet(Type command, Serializable... args) {
        this.command = command;
        this.args = args;
    }

    public Packet(Type command) {
        this.command = command;
        this.args = null;
    }


    public Packet(String type, Serializable... args) {
        this.args = args;
        this.command = commandMap.get(type);
    }
}
