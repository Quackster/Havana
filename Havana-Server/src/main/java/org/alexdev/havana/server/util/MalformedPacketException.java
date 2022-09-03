package org.alexdev.havana.server.util;

public class MalformedPacketException extends Exception {
    public MalformedPacketException(String error) {
        super(error);
    }
}
