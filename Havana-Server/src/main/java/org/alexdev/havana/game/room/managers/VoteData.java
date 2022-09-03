package org.alexdev.havana.game.room.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VoteData {
    private final int userId;
    private final int vote;
    private final List<String> ipAddresses;
    private final String machineId;

    public VoteData(int userId, int vote, String ipAddresses, String machineId) {
        this.userId = userId;
        this.vote = vote;
        this.ipAddresses = ipAddresses.split(",").length == 0 ? new ArrayList<>() : Arrays.asList(ipAddresses.split(","));
        this.machineId = machineId;
    }

    public int getUserId() {
        return userId;
    }

    public int getVote() {
        return vote;
    }

    public List<String> getIpAddresses() {
        return ipAddresses;
    }

    public String getMachineId() {
        return machineId;
    }
}
