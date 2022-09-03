package org.alexdev.havana.server.rcon.messages;

import io.netty.buffer.ByteBuf;

import java.util.Map;

public class RconMessage {
    private RconHeader header;
    private Map<String, String> values;

    public RconMessage(String header, Map<String, String> values) {
        this.header = RconHeader.getByHeader(header);
        this.values = values;
    }

    public RconHeader getHeader() {
        return header;
    }

    public Map<String, String> getValues() {
        return values;
    }
}
