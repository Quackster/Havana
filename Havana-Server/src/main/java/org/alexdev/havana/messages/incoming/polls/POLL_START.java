package org.alexdev.havana.messages.incoming.polls;

import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.messages.types.MessageComposer;
import org.alexdev.havana.messages.types.MessageEvent;
import org.alexdev.havana.server.netty.streams.NettyRequest;
import org.alexdev.havana.server.netty.streams.NettyResponse;

public class POLL_START implements MessageEvent {
    @Override
    public void handle(Player player, NettyRequest reader) throws Exception {
        player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeInt(1);
                response.writeString("123");
                response.writeString("456");

                int questions = 1;
                response.writeInt(questions);

                for (int i = 0; i < questions; i++) {
                    response.writeInt(1); // Question ID
                    response.writeInt(1); // Question number

                    response.writeInt(1); // Question type
                    response.writeString("Question?");

                    response.writeInt(2); // Selection count
                    response.writeInt(1); // Minimum select
                    response.writeInt(1); // Maximum select

                    response.writeString("test1");
                    response.writeString("test2");
                }
            }

            @Override
            public short getHeader() {
                return 317; // tMsgs.setaProp(317, #handle_poll_contents)
            }
        });
    }
}
