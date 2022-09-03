package org.alexdev.havana.server.netty;

import io.netty.channel.Channel;
import org.alexdev.havana.Havana;
import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.player.PlayerManager;
import org.alexdev.havana.util.StringUtil;
import org.alexdev.havana.util.config.ServerConfiguration;
import org.apache.commons.validator.routines.InetAddressValidator;

public class NettyPlayerNetwork {
    private Channel channel;
    private int connectionId;

    private boolean saveMachineId;
    private String clientMachineId;

    public NettyPlayerNetwork(Channel channel, int connectionId) {
        this.channel = channel;
        this.connectionId = connectionId;
    }

    public Channel getChannel() {
        return this.channel;
    }

    public void send(Object response) {
        this.channel.writeAndFlush(response);
    }

    public void disconnect() {
        this.channel.close();
    }

    public int getConnectionId() {
        return connectionId;
    }

    public static String getIpAddress(Channel channel) {
        var data = channel.remoteAddress().toString().replace("/", "");
        String[] ipData = data.split(":");

        InetAddressValidator validator = InetAddressValidator.getInstance();

        // Validate an IPv4 address
        if (validator.isValidInet4Address(ipData[0])) {
            return ipData[0];
        } else {
            // Try validate IPv6
            String ip = data.replace(":" + ipData[ipData.length - 1], "");

            if (validator.isValidInet6Address(ip)) {
                return ip;
            }
        }

        return null;
    }

    public String getClientMachineId() {
        return clientMachineId;
    }

    public void setClientMachineId(String clientMachineId) {
        this.clientMachineId = clientMachineId;
    }

    public boolean saveMachineId() {
        return saveMachineId;
    }

    public void setSaveMachineId(boolean saveMachineId) {
        this.saveMachineId = saveMachineId;
    }

}
