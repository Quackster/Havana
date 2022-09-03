package org.alexdev.havana.messages.outgoing.rooms;

import org.alexdev.havana.game.room.models.RoomModel;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class FLOOR_MAP extends MessageComposer {
    private final RoomModel roomModel;

    public FLOOR_MAP(RoomModel roomModel) {
        this.roomModel = roomModel;
    }

    @Override
    public void compose(NettyResponse response) {
        String[] lines = roomModel.getHeightmap().split("\r");
        
        for (int y = 0; y < roomModel.getMapSizeY(); y++) {
            String line = lines[y];

            for (int x = 0; x < roomModel.getMapSizeX(); x++) {
                char tile = line.charAt(x);

                if (x == roomModel.getDoorLocation().getX() && y == roomModel.getDoorLocation().getY()) {
                    response.write((int) roomModel.getDoorLocation().getZ());
                } else {
                    response.write(tile);
                }
            }

            response.write((char)13);
        }
    }

    @Override
    public short getHeader() {
        return 470; // "GV"
    }
}
