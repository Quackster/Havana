package org.alexdev.havana.game.commands.registered.admin;

import org.alexdev.havana.game.bot.Bot;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.util.FigureUtil;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class BotCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.ADMINISTRATOR);
    }

    @Override
    public void addArguments() {
        this.arguments.add("amount");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        if (player.getRoomUser().getRoom() == null) {
            return;
        }

        Room room = player.getRoomUser().getRoom();

        int amount = Integer.parseInt(args[0]);

        for (int i = 0; i < amount; i++) {
            String sex = ThreadLocalRandom.current().nextBoolean() ? "M" : "F";

            Bot bot = new Bot();

            try {
                bot.getDetails().fill(0, "BotDude" + ThreadLocalRandom.current().nextInt(0, Integer.MAX_VALUE), FigureUtil.getRandomFigure(sex, ThreadLocalRandom.current().nextBoolean()), "I'm here to bot things up!", sex);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Position bound = room.getMapping().getRandomWalkableBound(bot, false);

            if (bound != null)
                room.getEntityManager().enterRoom(bot, bound);
        }

        room.getTaskManager().scheduleTask("BotCommandTask", ()-> {
            for (Bot bot : room.getEntityManager().getEntitiesByClass(Bot.class)) {
                Position newBound = room.getMapping().getRandomWalkableBound(bot, false);

                if (newBound != null) {
                    bot.getRoomUser().walkTo(newBound.getX(), newBound.getY());
                }

                int switchint = ThreadLocalRandom.current().nextInt(0, 3);

                if (switchint == 0) {
                    bot.getRoomUser().dance(ThreadLocalRandom.current().nextInt(1, 4));
                }

                if (switchint == 1) {
                    bot.getRoomUser().carryItem(ThreadLocalRandom.current().nextInt(0, 40), null);
                }
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @Override
    public String getDescription() {
        return "Creates a bot partay!";
    }
}
