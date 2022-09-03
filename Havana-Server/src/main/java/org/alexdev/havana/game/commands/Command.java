package org.alexdev.havana.game.commands;

import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.player.PlayerRank;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected PlayerRank playerRank;
    protected List<Integer> groupPermission;
    protected List<String> arguments;

    public Command() {
        this.arguments = new ArrayList<>();
        this.groupPermission = new ArrayList<>();
        this.setPlayerRank();
        this.addArguments();
    }

    /**
     * Adds the permissions.
     */
    public abstract void setPlayerRank();

    /**
     * Handler for setting minimum player rank allowed.
     *
     * @param playerRank the rank
     */
    public void setPlayerRank(PlayerRank playerRank) {
        this.playerRank = playerRank;
    }

    /**
     * Get groups required (if any) to be able to use this./
     * @return
     */
    public List<Integer> getGroupPermission() {
        return groupPermission;
    }

    /**
     * Add the group id to the users who can execute this.
     *
     * @param groupIds the groups
     */
    public void addGroup(List<Integer> groupIds) {
        groupPermission.addAll(groupIds);
    }

    /**
     * Adds the argument names, must be overridden
     */
    public void addArguments() { };
    
    /**
     * Handle command.
     *
     * @param entity the entity
     * @param message the message
     */
    public abstract void handleCommand(Entity entity, String message, String[] args) throws Exception;
    
    /**
     * Gets the description.
     *
     * @return the description
     */
    public abstract String getDescription();

    /**
     * Gets the minimum player rank allowed.
     *
     * @return the permissions
     */
    public PlayerRank getPlayerRank() {
        return this.playerRank;
    }

    /**
     * Gets the arguments.
     *
     * @return the arguments
     */
    public String[] getArguments() {
        return this.arguments.parallelStream().toArray(String[]::new);
    }

}
