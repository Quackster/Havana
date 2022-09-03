package org.alexdev.havana.game.room.managers;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.game.achievements.AchievementManager;
import org.alexdev.havana.game.achievements.AchievementType;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.base.ItemBehaviour;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.game.song.Song;
import org.alexdev.havana.messages.outgoing.rooms.items.JUDGE_GUI_STATUS;
import org.alexdev.havana.messages.outgoing.songs.START_PLAYING_SONG;
import org.alexdev.havana.messages.outgoing.songs.STOP_PLAYING_SONG;
import org.alexdev.havana.server.netty.NettyPlayerNetwork;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RoomIdolManager {
    private Room room;
    private Player performer;
    private List<Player> voted;
    private Song song;

    public RoomIdolManager(Room room) {
        this.room = room;
        this.voted = new ArrayList<>();
    }

    public void resetChairs() {
        for (Item voteChair : this.room.getItemManager().getItemsByDefinition(InteractionType.IDOL_VOTE_CHAIR)) {
            if (voteChair.getCustomData().equals("0"))
                continue;

            voteChair.setCustomData("0");
            voteChair.updateStatus();
            voteChair.save();
        }
    }

    public void startPerformance(Song song) {
        if (this.performer == null)
            return;

        if (this.room.getItemManager().getIdolScoreboard() == null)
            return;

        this.song = song;

        for (Item voteChair : this.room.getItemManager().getItemsByDefinition(InteractionType.IDOL_VOTE_CHAIR)) {
            for (Entity entity : voteChair.getTile().getEntities()) {
                if (entity.getType() != EntityType.PLAYER)
                    continue;

                Player player = (Player) entity;
                player.send(new JUDGE_GUI_STATUS(2, this.performer.getDetails().getId()));
            }
        }

        // Send song to players
        this.startListening();

        // Wooooooooo
        Item scoreboard = this.room.getItemManager().getIdolScoreboard();

        scoreboard.setCustomData("0");
        scoreboard.updateStatus();
        scoreboard.save();
    }

    public void stopListening() {
        if (this.song == null)
            return;

        // Stop song to players
        if (this.room.getItemManager().getSoundMachine() != null && this.room.getItemManager().getSoundMachine().hasBehaviour(ItemBehaviour.SOUND_MACHINE)) {
            this.room.send(new STOP_PLAYING_SONG(song.getId()));

            this.room.getItemManager().getSoundMachine().setCustomData("0");
            this.room.getItemManager().getSoundMachine().updateStatus();
        }

        this.song = null;
    }

    public void startListening() {
        if (this.song == null)
            return;

        // Stop song to players
        if (this.room.getItemManager().getSoundMachine() != null && this.room.getItemManager().getSoundMachine().hasBehaviour(ItemBehaviour.SOUND_MACHINE)) {
            this.room.send(new START_PLAYING_SONG(song.getId()));

            this.room.getItemManager().getSoundMachine().setCustomData("1");
            this.room.getItemManager().getSoundMachine().updateStatus();
        }
    }

    /**
     * Update the performer instance
     */
    public void updatePerformer() {
        this.performer = null;

        if (this.room.getItemManager().getIdolScoreboard() == null) {
            return;
        }

        Item scoreboard = this.room.getItemManager().getIdolScoreboard();

        if (scoreboard.getTile() == null)
            return;

        RoomTile roomTile = scoreboard.getTile();
        var entities = roomTile.getEntireEntities();

        if (entities.stream().anyMatch(e -> e.getType() == EntityType.PLAYER)) {
            var optional = entities.stream().filter(e -> e.getType() == EntityType.PLAYER).findFirst();
            optional.ifPresent(entity -> this.performer = (Player) entity);
        }

    }

    /**
     * Force stop when user steps off platform
     */
    public void forceStop() {
        for (Item voteChair : this.room.getItemManager().getItemsByDefinition(InteractionType.IDOL_VOTE_CHAIR)) {
            for (Entity entity : voteChair.getTile().getEntireEntities()) {
                if (entity.getType() != EntityType.PLAYER)
                    continue;

                Player player = (Player) entity;
                player.send(new JUDGE_GUI_STATUS(1, -1));
            }
        }

        Item item = this.room.getItemManager().getIdolScoreboard();

        if (item != null) {
            item.setCustomData("-1");
            item.updateStatus();
            item.save();
        }

        this.stopListening();
        this.voted.clear();
    }

    /**
     * Perform voting
     * @param type the positive/negative vote
     * @param player the player who voted
     */
    public void vote(boolean type, Player player) {
        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Item chair = player.getRoomUser().getCurrentItem();

        if (chair == null || chair.getDefinition().getInteractionType() != InteractionType.IDOL_VOTE_CHAIR) {
            return;
        }

        Item scoreboard = this.room.getItemManager().getIdolScoreboard();

        if (scoreboard == null) {
            return;
        }

        Integer value = StringUtils.isNumeric(scoreboard.getCustomData()) ? Integer.parseInt(scoreboard.getCustomData()) : 0;

        if (type) {
            chair.setCustomData("1");
            chair.updateStatus();

            value++;

            scoreboard.setCustomData(String.valueOf(value));
            scoreboard.updateStatus();
        }
        else {
            chair.setCustomData("2");
            chair.updateStatus();

            if (value > 0) {
                value--;
            }

            scoreboard.setCustomData(String.valueOf(value));
            scoreboard.updateStatus();
        }

        var voteChairs = this.room.getItemManager().getItemsByDefinition(InteractionType.IDOL_VOTE_CHAIR).stream().filter(item -> item.getCustomData().equals("0") && item.getTile().getEntireEntities().size() > 0).collect(Collectors.toList());

        if (voteChairs.isEmpty()) {
            this.forceStop();
        }

        if (this.performer != null) {
            if (this.performer.getAchievementManager().getPossibleAchievements().stream().anyMatch(achievementInfo -> achievementInfo.getName().equals("ACH_AIPerformanceVote"))) {
                String firstIp = NettyPlayerNetwork.getIpAddress(player.getNetwork().getChannel());
                String secondIp = NettyPlayerNetwork.getIpAddress(this.performer.getNetwork().getChannel());

                if (firstIp != null && !(firstIp.equals(secondIp) || PlayerDao.getIpAddresses(player.getDetails().getId(), RoomTradeManager.TRADE_BAN_IP_HISTORY_LIMIT).contains(secondIp))) {
                    AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_AIPERFORMANCEVOTE, this.performer);
                }
            }
        }

        this.voted.add(player);
    }

    public List<Player> getVoted() {
        return voted;
    }

    /**
     * Get the current performer.
     *
     * @return the performer
     */
    public Player getPerformer() {
        return performer;
    }

}
