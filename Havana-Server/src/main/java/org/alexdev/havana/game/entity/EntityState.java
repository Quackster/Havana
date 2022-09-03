package org.alexdev.havana.game.entity;

import org.alexdev.havana.game.groups.GroupMember;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.RoomUserStatus;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EntityState {
    private int entityId;
    private int instanceId;
    private EntityType entityType;
    private PlayerDetails details;
    private Room room;
    private Position position;
    private Map<String, RoomUserStatus> statuses;
    private GroupMember groupMember;
    private Entity entity;

    public EntityState(int entityId, int instanceId, EntityType entityType, PlayerDetails details, Room room, Position position, Map<String, RoomUserStatus> statuses, Entity user) {
        this.entityId = entityId;
        this.instanceId = instanceId;
        this.entityType = entityType;
        this.details = details;
        this.room = room;
        this.position = position;
        this.statuses = new ConcurrentHashMap<>(statuses);

        if (this.details.getFavouriteGroupId() > 0) {
            this.groupMember = this.details.getGroupMember();
        }

        this.entity = user;
    }

    public Entity getEntity() {
        return entity;
    }

    public int getInstanceId() {
        return instanceId;
    }

    public Position getPosition() {
        return position;
    }

    public Map<String, RoomUserStatus> getStatuses() {
        return statuses;
    }

    public int getEntityId() {
        return entityId;
    }

    public PlayerDetails getDetails() {
        return details;
    }

    public Room getRoom() {
        return room;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public GroupMember getGroupMember() {
        return groupMember;
    }
}
