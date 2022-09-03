package org.alexdev.havana.game.room.entities;

import org.alexdev.havana.dao.mysql.PlayerDao;
import org.alexdev.havana.dao.mysql.TagDao;
import org.alexdev.havana.game.achievements.AchievementManager;
import org.alexdev.havana.game.achievements.AchievementType;
import org.alexdev.havana.game.commands.CommandManager;
import org.alexdev.havana.game.games.Game;
import org.alexdev.havana.game.games.GameManager;
import org.alexdev.havana.game.games.enums.GameState;
import org.alexdev.havana.game.games.enums.GameType;
import org.alexdev.havana.game.games.player.GamePlayer;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.managers.RoomTradeManager;
import org.alexdev.havana.messages.outgoing.rooms.user.CHAT_MESSAGE;
import org.alexdev.havana.messages.outgoing.rooms.user.FIGURE_CHANGE;
import org.alexdev.havana.messages.outgoing.rooms.user.TAG_LIST;
import org.alexdev.havana.messages.outgoing.user.USER_OBJECT;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.config.GameConfiguration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RoomPlayer extends RoomEntity {
    private Player player;

    private int authenticateId;
    private long authenticateTelporterId;
    private int observingGameId;

    private boolean isTyping;
    private boolean isDiving;

    private Player tradePartner;

    private CopyOnWriteArrayList<Item> tradeItems;
    private CopyOnWriteArrayList<Position> walkHistory;

    private boolean tradeConfirmed;
    private boolean canConfirmTrade;
    private boolean tradeAccept;

    private GamePlayer gamePlayer;
    private String currentGameId;

    public int chatSpamCount = 0;
    public int chatSpamTicks = 16;
    private GameType lastLobbyRedirection;

    private int lidoVote;

    private long lastGiftTime;
    private long muteTime;

    public RoomPlayer(Player player) {
        super(player);
        this.player = player;
        this.authenticateId = -1;
        this.authenticateTelporterId = -1;
        this.tradeItems = new CopyOnWriteArrayList<>();
    }

    @Override
    public void reset() {
        super.reset();
        this.isTyping = false;
        this.isDiving = false;
        this.observingGameId = -1;
        this.lidoVote = 0;
        this.walkHistory = new CopyOnWriteArrayList<>();
        RoomTradeManager.close(this, false);
    }

    public void handleSpamTicks() {
        if (this.chatSpamTicks >= 0) {
            this.chatSpamTicks--;

            if (this.chatSpamTicks == -1) {
                this.chatSpamCount = 0;
            }
        }
    }

    @Override
    public void talk(String message, CHAT_MESSAGE.ChatMessageType chatMessageType, List<Player> recieveMessages) {
        if (message.endsWith("o/")) {
            this.wave();

            if (message.equals("o/")) {
                return; // Don't move mouth if it's just a wave
            }
        }

        this.chatSpamCount++;

        if (this.chatSpamTicks == -1) {
            this.chatSpamTicks = 8;

            // Game ticks go twice as fast in SnowStorm
            if (this.gamePlayer != null &&
                    this.gamePlayer.getGame() != null &&
                    this.gamePlayer.getGame().getGameType() == GameType.SNOWSTORM) {
                this.chatSpamTicks = 8 * 2;
            }
        }

        if (this.chatSpamCount >= 6) {
            this.muteTime = DateUtil.getCurrentTimeSeconds() + 30;
        }

        if (this.muteTime > DateUtil.getCurrentTimeSeconds()) {
            return;
        }

        if (this.getRoom().getData().isRoomMuted()) {
            if (!CommandManager.getInstance().hasPermission(this.player.getDetails(), "roommute")) {
                this.player.send(new CHAT_MESSAGE(CHAT_MESSAGE.ChatMessageType.WHISPER, player.getRoomUser().getInstanceId(), "Shh! The room is currently muted by the moderators", 0));
                this.getTimerManager().resetRoomTimer();
                return;
            }
        }

        super.talk(message, chatMessageType, recieveMessages);
        this.muteTime = 0;
    }

    @Override
    public boolean canCarry(int carryId) {
        if (carryId == 20) {
            return this.player.getInventory().getItems().stream().anyMatch(item -> item.getDefinition().getSprite().equalsIgnoreCase("camera"));
        }

        var room = this.player.getRoomUser().getRoom();

        if (room == null) {
            return false;
        }

        if (room.isPublicRoom()) {
            return true; // TODO: Handle public rooms
        }

        /*for (var angle : Pathfinder.DIAGONAL_MOVE_POINTS) {
            var position = this.getPosition().add(angle);
            var roomTile = room.getMapping().getTile(position);

            if (roomTile == null) {
                continue;
            }

            var highestItem = roomTile.getHighestItem();

            if (highestItem == null || highestItem.getDefinition().getInteractionType() != InteractionType.VENDING_MACHINE) {
                continue;
            }

            for (int drinkId : highestItem.getDefinition().getDrinkIds()) {
                if (drinkId == carryId) {
                    return true;
                }
            }
        }*/

        return false;
    }

    @Override
    public boolean walkTo(int X, int Y) {
        boolean unidle = true;

        for (Position pos : this.walkHistory) {
            if (pos.equals(new Position(X, Y))) {
                unidle = false;
                break;
            }
        }

        this.walkHistory.add(new Position(X, Y));

        if (this.walkHistory.size() > GameConfiguration.getInstance().getInteger("walk.spam.count")) {
            this.walkHistory.remove(0);
        }

        boolean walking = super.walkTo(X, Y);

        if (walking && unidle) {
            this.getTimerManager().resetRoomTimer();
        }

        return walking;
    }

    @Override
    public void stopWalking() {
        super.stopWalking();

        /*Item item = this.getCurrentItem();

        if (item != null) {
            // Kick out user from teleporter if link is broken
            if (item.hasBehaviour(ItemBehaviour.TELEPORTER)) {
                Item linkedTeleporter = ItemDao.getItem(item.getTeleporterId());

                if (linkedTeleporter == null || RoomManager.getInstance().getRoomById(linkedTeleporter.getRoomId()) == null) {
                    item.setCustomData("TRUE");
                    item.updateStatus();

                    player.getRoomUser().walkTo(item.getPosition().getSquareInFront().getX(), item.getPosition().getSquareInFront().getY());
                    player.getRoomUser().setWalkingAllowed(true);
                    return;
                }
            }
        }*/
    }

    @Override
    public void kick(boolean allowWalking, boolean isBeingKicked) {
        super.kick(allowWalking, isBeingKicked);

        // Remove authentications
        this.authenticateId = -1;
        this.authenticateTelporterId = -1;
    }

    public void startObservingGame(int gameId) {
        if (this.observingGameId != -1) {
            this.stopObservingGame();
        }

        this.observingGameId = gameId;
        Game game = GameManager.getInstance().getGameById(this.observingGameId);

        if (game != null) {
            game.getObservers().add(this.player);
        }
    }

    public void stopObservingGame() {
        if (this.observingGameId != -1) {
            Game game = GameManager.getInstance().getGameById(this.observingGameId);

            if (game != null) {
                game.getObservers().remove(this.player);
            }

            this.observingGameId = -1;
        }
    }

    /**
     * Refreshes user appearance
     */
    public void refreshAppearance() {
        var oldDetails = this.player.getDetails();
        var newDetails = PlayerDao.getDetails(this.player.getDetails().getId());

        if (!oldDetails.getMotto().toLowerCase().equals(newDetails.getMotto().toLowerCase())) {
            AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_MOTTO, player);
        }

        if (!oldDetails.getFigure().equals(newDetails.getFigure())) {
            AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_LOOKS, player);
        }

        // Reload figure, gender and motto
        this.player.getDetails().setFigure(newDetails.getFigure());
        this.player.getDetails().setSex(newDetails.getSex());
        this.player.getDetails().setMotto(newDetails.getMotto());

        // Don't send packets during midgame
        if (this.player.getRoomUser().getGamePlayer() != null &&
                this.player.getRoomUser().getGamePlayer().getGame() != null &&
                this.player.getRoomUser().getGamePlayer().getGame().getGameState() == GameState.STARTED) {
            return;
        }
        // Send refresh to user
        this.player.send(new USER_OBJECT(this.player.getDetails()));

        // Send refresh to room if inside room
        if (this.getRoom() != null) {
            this.getRoom().send(new FIGURE_CHANGE(this.getInstanceId(), this.player.getDetails()));
        }
    }

    public void refreshTags() {
        var tagList = TagDao.getUserTags(this.player.getDetails().getId());
        this.player.getRoomUser().getRoom().send(new TAG_LIST(this.player.getDetails().getId(), tagList));
    }

    public int getAuthenticateId() {
        return authenticateId;
    }

    public void setAuthenticateId(int authenticateId) {
        this.authenticateId = authenticateId;
    }

    public long getAuthenticateTelporterId() {
        return authenticateTelporterId;
    }

    public void setAuthenticateTelporterId(long authenticateTelporterId) {
        this.authenticateTelporterId = authenticateTelporterId;
    }

    public boolean isTyping() {
        return isTyping;
    }

    public void setTyping(boolean typing) {
        isTyping = typing;
    }

    public boolean isDiving() {
        return isDiving;
    }

    public void setDiving(boolean diving) {
        isDiving = diving;
    }

    public Player getTradePartner() {
        return tradePartner;
    }

    public void setTradePartner(Player tradePartner) {
        this.tradePartner = tradePartner;
    }

    public CopyOnWriteArrayList<Item> getTradeItems() {
        return tradeItems;
    }

    public boolean hasAcceptedTrade() {
        return tradeAccept;
    }

    public void setTradeAccept(boolean tradeAccept) {
        this.tradeAccept = tradeAccept;
    }

    public String getCurrentGameId() {
        return currentGameId;
    }

    public void setCurrentGameId(String currentGameId) {
        this.currentGameId = currentGameId;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }

    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public int getObservingGameId() {
        return observingGameId;
    }

    public boolean isTradeConfirmed() {
        return tradeConfirmed;
    }

    public void setTradeConfirmed(boolean tradeConfirmed) {
        this.tradeConfirmed = tradeConfirmed;
    }

    public int getLidoVote() {
        return lidoVote;
    }

    public void setLidoVote(int lidoVote) {
        this.lidoVote = lidoVote;
    }

    public boolean canConfirmTrade() {
        return canConfirmTrade;
    }

    public void setCanConfirmTrade(boolean canConfirmTrade) {
        this.canConfirmTrade = canConfirmTrade;
    }

    public int resetObservingGameId() {
        return observingGameId;
    }

    public void setObservingGameId(int observingGameId) {
        this.observingGameId = observingGameId;
    }

    public long getLastGiftTime() {
        return lastGiftTime;
    }

    public void setLastGiftTime(long lastGiftTime) {
        this.lastGiftTime = lastGiftTime;
    }

    public GameType getLastLobbyRedirection() {
        return lastLobbyRedirection;
    }

    public void setLastLobbyRedirection(GameType lastLobbyRedirection) {
        this.lastLobbyRedirection = lastLobbyRedirection;
    }
}