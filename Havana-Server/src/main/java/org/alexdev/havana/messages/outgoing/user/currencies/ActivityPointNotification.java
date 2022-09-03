package org.alexdev.havana.messages.outgoing.user.currencies;

import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class ActivityPointNotification extends MessageComposer {
    public enum ActivityPointAlertType {
        PIXELS_RECEIVED,
        PIXELS_SOUND,
        NO_SOUND,
    }

    private final int pixels;
    private final ActivityPointAlertType alertType;

    public ActivityPointNotification(int pixels, ActivityPointAlertType alertType) {
        this.pixels = pixels;
        this.alertType = alertType;
    }

    @Override
    public void compose(NettyResponse response) {
        response.writeInt(this.pixels);

        if (this.alertType == ActivityPointAlertType.PIXELS_RECEIVED) {
            response.writeInt(15);
        }

        if (this.alertType == ActivityPointAlertType.PIXELS_SOUND) {
            response.writeInt(-1);
        }

        if (this.alertType == ActivityPointAlertType.NO_SOUND) {
            response.writeInt(0);
        }
    }

    @Override
    public short getHeader() {
        return 438; // "Fv"
    }
}
