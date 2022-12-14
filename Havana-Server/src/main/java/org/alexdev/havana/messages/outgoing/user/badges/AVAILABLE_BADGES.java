package org.alexdev.havana.messages.outgoing.user.badges;

import org.alexdev.havana.game.badges.Badge;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

import java.util.List;

public class AVAILABLE_BADGES extends MessageComposer {
    private final List<Badge> badges;
    private final List<Badge> equippedBadges;

    public AVAILABLE_BADGES(List<Badge> badges, List<Badge> equippedBadges) {
        this.badges = badges;
        this.equippedBadges = equippedBadges;
    }


    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.badges.size());

        for (Badge badge : this.badges) {
            response.writeString(badge.getBadgeCode());
        }

        response.writeInt(this.equippedBadges.size());

        for (Badge badge : this.equippedBadges) {
            response.writeInt(badge.getSlotId());
            response.writeString(badge.getBadgeCode());
        }
    }

    @Override
    public short getHeader() {
        return 229; // "Ce"
    }
}
