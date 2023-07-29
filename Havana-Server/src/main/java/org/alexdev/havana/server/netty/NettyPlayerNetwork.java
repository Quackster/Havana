package org.alexdev.havana.server.netty;

import io.netty.channel.Channel;
import org.alexdev.havana.server.netty.codec.EncryptionDecoder;
import org.alexdev.havana.server.netty.codec.EncryptionEncoder;
import org.apache.commons.validator.routines.InetAddressValidator;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

public class NettyPlayerNetwork {
    private Channel channel;
    private int connectionId;

    private boolean saveMachineId;
    private String clientMachineId;
    private String pToken;
    private int pTx;
    private int pRx;

    public NettyPlayerNetwork(Channel channel, int connectionId) {
        this.channel = channel;
        this.connectionId = connectionId;
    }

    public static String removePadding(String tBody, int i) {
        if (i >= tBody.length())
            return tBody;

        return tBody.substring(i);
    }

    public static String addPad(String tMsg, int amount) {
        var secureRandom = new SecureRandom();

        for (int i = 0; i < amount; i++) {
            tMsg = Character.toString(secureRandom.nextInt(255) + 1) + tMsg;
        }

        return tMsg;
    }

    public void setToken(String tToken) {
        this.pToken = tToken;
        String tSeedHex = pToken.substring(pToken.length() - 4);
        Map<String, Integer> tVals = new HashMap<>();
        tVals.put("0", 0);
        tVals.put("1", 1);
        tVals.put("2", 2);
        tVals.put("3", 3);
        tVals.put("4", 4);
        tVals.put("5", 5);
        tVals.put("6", 6);
        tVals.put("7", 7);
        tVals.put("8", 8);
        tVals.put("9", 9);
        tVals.put("a", 10);
        tVals.put("b", 11);
        tVals.put("c", 12);
        tVals.put("d", 13);
        tVals.put("e", 14);
        tVals.put("f", 15);
        tVals.put("A", 10);
        tVals.put("B", 11);
        tVals.put("C", 12);
        tVals.put("D", 13);
        tVals.put("E", 14);
        tVals.put("F", 15);

        this.pTx = 0;

        for (int i = 0; i <= 3; i++) {
            this.pTx += (int)(Math.pow(16, i) * tVals.get(Character.toString(tSeedHex.charAt(3 - i))));
        }

        this.pRx = 0;

        tSeedHex = pToken.substring(0, 4);

        for (int i = 0; i <= 3; i++) {
            this.pRx += (int)(Math.pow(16, i) * tVals.get(Character.toString(tSeedHex.charAt(3 - i))));
        }
    }

    public static int iterateRandom(int tSeed) {
     return ((19979 * tSeed) + 5) % 65536;
    }

    public int getTx() {
        return pTx;
    }

    public int getRx() {
        return pRx;
    }

    public void setTx(int pTx) {
        this.pTx = pTx;
    }

    public void setRx(int pRx) {
        this.pRx = pRx;
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

    public void registerHandler(ServerHandlerType type, Object object) {
        if (type == ServerHandlerType.RC4) {
            this.channel.pipeline().addBefore("networkDecoder", "encryptionDecoder", new EncryptionDecoder((BigInteger) object));
            this.channel.pipeline().addBefore("networkEncoder", "encryptionEncoder", new EncryptionEncoder((BigInteger) object));
            // this.channel.pipeline().remove("gameDecoder");
        }
    }
}
