package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.models.RoomModel;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class HEIGHTMAP_UPDATE extends MessageComposer {
    private final String heightmap;

    public HEIGHTMAP_UPDATE(Room room, RoomModel roomModel) {
        String[] lines = roomModel.getHeightmap().split("\r");

        StringBuilder updateMap = new StringBuilder();

        for (int y = 0; y < roomModel.getMapSizeY(); y++) {
            String line = lines[y];

            for (int x = 0; x < roomModel.getMapSizeX(); x++) {
                char tile = line.charAt(x);

                if (Character.isDigit(tile)) {
                    var roomTile = room.getMapping().getTile(x, y);

                    if (roomTile == null) {
                        continue;
                    }

                    int height = (int) Math.round(roomTile.getWalkingHeight());//Character.getNumericValue(tile);

                    if (height < 0 || height > 8) {
                        height = 0;
                    }

                    /*if (room.getMapping().getTile(x, y).getHighestItem() != null &&
                            room.getMapping().getTile(x, y).getHighestItem().hasBehaviour(ItemBehaviour.CAN_STACK_ON_TOP)) {
                        //char updateChar = (char) (height + 65);
                        updateMap.append(height);
                    } else {
*/
                        //char updateChar = (char) (height + 65);
                        updateMap.append(height);
                   // }
                } else {
                    updateMap.append("x");
                }
            }

            updateMap.append("\r");
        }

        this.heightmap = updateMap.toString();
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeString(this.heightmap);
    }

    @Override
    public short getHeader() {
        return 219; // "@_"
    }
}
