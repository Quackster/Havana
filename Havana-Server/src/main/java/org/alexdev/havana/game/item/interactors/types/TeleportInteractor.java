package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.dao.mysql.TeleporterDao;
import org.alexdev.havana.game.GameScheduler;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.ItemManager;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.entities.RoomPlayer;
import org.alexdev.havana.game.room.mapping.RoomTile;
import org.alexdev.havana.game.triggers.GenericTrigger;
import org.alexdev.havana.messages.outgoing.messenger.ROOMFORWARD;
import org.alexdev.havana.messages.outgoing.rooms.user.LOGOUT;

import java.util.concurrent.TimeUnit;

public class TeleportInteractor extends GenericTrigger {
    public static final String TELEPORTER_CLOSE = "0";
    public static final String TELEPORTER_OPEN = "1";
    public static final String TELEPORTER_EFFECTS = "2";

    public void onInteract(Player player, Room room, Item item, int status) {
        RoomPlayer roomUser = player.getRoomUser();

        if (player.getRoomUser().getAuthenticateTelporterId() != -1) {
            return;
        }

        Position front = item.getPosition().getSquareInFront();

        if (!front.equals(roomUser.getPosition()) && !item.getPosition().equals(roomUser.getPosition())) {
            roomUser.walkTo(front.getX(), front.getY());
            return;
        }

        long pairId = TeleporterDao.getTeleporterId(item.getDatabaseId());
        Item targetTeleporter = ItemDao.getItem(pairId);

        item.setCustomData(TELEPORTER_OPEN);
        item.updateStatus();

        roomUser.walkTo(item.getPosition().getX(), item.getPosition().getY());
        roomUser.setWalkingAllowed(false);

        // Broken link, make user walk in then walk out
        if (pairId == -1 || targetTeleporter == null || targetTeleporter.getRoom() == null) {
            GameScheduler.getInstance().getService().schedule(() -> {
                roomUser.walkTo(
                        item.getPosition().getSquareInFront().getX(),
                        item.getPosition().getSquareInFront().getY());
            }, 1, TimeUnit.SECONDS);

            GameScheduler.getInstance().getService().schedule(() -> {
                item.setCustomData(TELEPORTER_CLOSE);
                item.updateStatus();
            }, 2, TimeUnit.SECONDS);

            GameScheduler.getInstance().getService().schedule(() -> {
                roomUser.setWalkingAllowed(true);
            }, 2500, TimeUnit.MILLISECONDS);

            return;
        }

        var resolved = ItemManager.getInstance().resolveItem(pairId);

        Item pairedTeleporter = resolved != null ? resolved : targetTeleporter;
        roomUser.setAuthenticateTelporterId(pairedTeleporter.getDatabaseId());

        // Check if the user is inside the teleporter, if so, walk out. Useful if the user is stuck inside.
        if (item.getPosition().equals(roomUser.getPosition()) && !RoomTile.isValidTile(room, player, item.getPosition().getSquareInFront())) {
            item.setCustomData(TELEPORTER_EFFECTS);
            item.updateStatus();

            GameScheduler.getInstance().getService().schedule(() -> {
                if (roomUser.getAuthenticateTelporterId() == -1) {
                    return;
                }

                item.setCustomData(TELEPORTER_CLOSE);
                item.updateStatus();

                room.send(new LOGOUT(player.getRoomUser().getInstanceId()));
            }, 1, TimeUnit.SECONDS);

            GameScheduler.getInstance().getService().schedule(() -> {
                if (roomUser.getAuthenticateTelporterId() == -1) {
                    return;
                }

                if (pairedTeleporter.getRoomId() == item.getRoomId()) {
                    roomUser.warp(pairedTeleporter.getPosition(), true, true);
                    pairedTeleporter.setCustomData(TELEPORTER_EFFECTS);
                    pairedTeleporter.updateStatus();
                } else {
                    roomUser.setAuthenticateId(pairedTeleporter.getRoom().getId());
                    pairedTeleporter.getRoom().forward(player, false);
                    //player.send(new ROOMFORWARD(pairedTeleporter.getRoom().isPublicRoom(), pairedTeleporter.getRoom().getId()));
                }
            }, 2, TimeUnit.SECONDS);

            // Handle teleporting in the same room
            if (pairedTeleporter.getRoomId() == item.getRoomId()) {
                GameScheduler.getInstance().getService().schedule(() -> {
                    if (roomUser.getAuthenticateTelporterId() == -1) {
                        return;
                    }

                    pairedTeleporter.setCustomData(TELEPORTER_OPEN);
                    pairedTeleporter.updateStatus();

                    roomUser.walkTo(
                            pairedTeleporter.getPosition().getSquareInFront().getX(),
                            pairedTeleporter.getPosition().getSquareInFront().getY());

                    roomUser.setWalkingAllowed(true);
                }, 3, TimeUnit.SECONDS);

                GameScheduler.getInstance().getService().schedule(() -> {
                    if (roomUser.getAuthenticateTelporterId() == -1) {
                        return;
                    }

                    roomUser.setAuthenticateTelporterId(-1);

                    pairedTeleporter.setCustomData(TELEPORTER_CLOSE);
                    pairedTeleporter.updateStatus();
                }, 4, TimeUnit.SECONDS);
            }
            return;
        }

        // Resume normal teleportation
        GameScheduler.getInstance().getService().schedule(() -> {
            if (roomUser.getAuthenticateTelporterId() == -1) {
                return;
            }

            item.setCustomData(TELEPORTER_EFFECTS);
            item.updateStatus();
        }, 1000, TimeUnit.MILLISECONDS);

        GameScheduler.getInstance().getService().schedule(() -> {
            if (roomUser.getAuthenticateTelporterId() == -1) {
                return;
            }

            room.send(new LOGOUT(player.getRoomUser().getInstanceId()));
            item.setCustomData(TELEPORTER_CLOSE);
            item.updateStatus();

            if (pairedTeleporter.getRoomId() != item.getRoomId()) {
                roomUser.setAuthenticateId(pairedTeleporter.getRoom().getId());
                pairedTeleporter.getRoom().forward(player, false);
                //player.send(new ROOMFORWARD(pairedTeleporter.getRoom().isPublicRoom(), pairedTeleporter.getRoom().getId()));
            } else {
                roomUser.warp(pairedTeleporter.getPosition(), true, true);
            }

            if (pairedTeleporter.getRoomId() == item.getRoomId()) {
                pairedTeleporter.setCustomData(TELEPORTER_EFFECTS);
                pairedTeleporter.updateStatus();
            }
        }, 1500, TimeUnit.MILLISECONDS);

        if (pairedTeleporter.getRoomId() == item.getRoomId()) {
            GameScheduler.getInstance().getService().schedule(() -> {
                if (roomUser.getRoom().getId() != room.getId()) {
                    roomUser.setAuthenticateTelporterId(-1);
                    return;
                }
                /*if (roomUser.getAuthenticateTelporterId() == -1) {
                    return;
                }*/

                pairedTeleporter.setCustomData(TELEPORTER_OPEN);
                pairedTeleporter.updateStatus();

                roomUser.walkTo(
                        pairedTeleporter.getPosition().getSquareInFront().getX(),
                        pairedTeleporter.getPosition().getSquareInFront().getY());
            }, 3, TimeUnit.SECONDS);

            GameScheduler.getInstance().getService().schedule(() -> {
                if (roomUser.getRoom().getId() != room.getId()) {
                    roomUser.setAuthenticateTelporterId(-1);
                    return;
                }
                /*if (roomUser.getAuthenticateTelporterId() == -1) {
                    return;
                }*/

                roomUser.setAuthenticateTelporterId(-1);

                if (pairedTeleporter.getRoomId() == item.getRoomId()) {
                    pairedTeleporter.setCustomData(TELEPORTER_CLOSE);
                    pairedTeleporter.updateStatus();
                } else {
                    roomUser.getRoom().getItemManager().getByDatabaseId(pairId).setCustomData(TELEPORTER_CLOSE);
                    roomUser.getRoom().getItemManager().getByDatabaseId(pairId).updateStatus();
                }

                roomUser.setWalkingAllowed(true);
            }, 4000, TimeUnit.MILLISECONDS);
        }
    }
}
