package org.alexdev.havana.util.config.writer;

import org.alexdev.havana.game.commands.CommandManager;
import org.alexdev.havana.util.DateUtil;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class GameConfigWriter implements ConfigWriter {
    @Override
    public Map<String, String>  setConfigurationDefaults() {
        Map<String, String> config = new HashMap<>();
        config.put("site.path", "http://localhost");

        config.put("room.ads", "false");
        config.put("room.intersitial.ads", "false");

        /*
        config.put("bot.connection.allow", "false");
        config.put("bot.connection.sso.prefix", "botcon2019-");
        */

        config.put("fuck.aaron", "true");
        config.put("max.connections.per.ip", "2");
        config.put("normalise.input.strings", "false");

        config.put("room.dispose.timer.enabled", "true");
        config.put("room.dispose.timer.seconds", "30");

        config.put("welcome.message.enabled", "false");
        config.put("welcome.message.content", "Hello, %username%! And welcome to the Havana server!");

        config.put("roller.tick.default", "2000");

        config.put("afk.timer.seconds", "900");
        config.put("sleep.timer.seconds", "300");
        config.put("carry.timer.seconds", "300");

        config.put("stack.height.limit", "8");
        config.put("players.online", "0");

        config.put("credits.scheduler.timeunit", "MINUTES");
        config.put("credits.scheduler.interval", "15");
        config.put("credits.scheduler.amount", "20");

        config.put("daily.credits.wait.time", "300");
        config.put("daily.credits.amount", "120");

        config.put("pixels.received.timeunit", "MINUTES");
        config.put("pixels.received.interval", "10");
        config.put("pixels.max.tries.single.room.instance", String.valueOf(Integer.MAX_VALUE));

        config.put("talk.garbled.text", "true");
        config.put("talk.bubble.timeout.seconds", "15");

        config.put("messenger.max.friends.nonclub", "100");
        config.put("messenger.max.friends.club", "600");

        config.put("battleball.create.game.enabled", "true");
        config.put("battleball.start.minimum.active.teams", "2");
        config.put("battleball.preparing.game.seconds", "10"); // 5, 4, 3, 2, 1 - then destruction of 1
        config.put("battleball.game.lifetime.seconds", "180");
        config.put("battleball.restart.game.seconds", "30");
        config.put("battleball.ticket.charge", "2");
        config.put("battleball.increase.points", "true");

        config.put("snowstorm.create.game.enabled", "false");
        config.put("snowstorm.start.minimum.active.teams", "2");
        config.put("snowstorm.preparing.game.seconds", "10"); // 5, 4, 3, 2, 1 - then destruction of 1
        config.put("snowstorm.game.lifetime.seconds", "0");
        config.put("snowstorm.restart.game.seconds", "30");
        config.put("snowstorm.ticket.charge", "2");
        config.put("snowstorm.increase.points", "true");

        config.put("tutorial.enabled", "true");
        config.put("profile.editing", "false");
        config.put("vouchers.enabled", "true");
        config.put("shutdown.minutes", "1");

        config.put("reset.sso.after.login", "true");
        config.put("navigator.show.hidden.rooms", "false");
        config.put("navigator.hide.empty.public.categories", "true");

        config.put("events.category.count", "11");
        config.put("events.expiry.minutes", "120");

        config.put("club.gift.timeunit", "DAYS");
        config.put("club.gift.interval", "30");
        config.put("club.gift.present.label", "You have just received your monthly club gift!");

        config.put("alerts.gift.message", "A new gift has arrived. This time you received a %item_name%.");

        config.put("wordfitler.enabled", "true");
        config.put("wordfilter.word.replacement", "bobba");

        config.put("reward.credits.winner.range", "10-20");
        config.put("reward.credits.loser.range", "0-4");

        //config.put("advertisement.api", "http://localhost/api/get_ad?picture={pictureName}&roomId={roomId}");
        config.put("xp.monthly.expiry", DateUtil.getCurrentDate("dd-MM"));
        config.put("april.fools", "false");

        config.put("delete.chatlogs.after.x.age", "2592000");
        config.put("delete.iplogs.after.x.age", "2592000");
        config.put("delete.tradelogs.after.x.age", "2592000");

        config.put("guides.group.id", "1");
        config.put("guide.search.timeout.minutes", "5");
        config.put("guide.completion.minutes", "4320");

        config.put("habbo.experts.group.id", "0");
        config.put("childline.group.id", "0");

        config.put("happy.hour.weekday.start", "17:00:00");
        config.put("happy.hour.weekday.end", "18:00:00");

        config.put("happy.hour.weekend.start", "12:00:00");
        config.put("happy.hour.weekend.end", "13:00:00");

        config.put("regenerate.map.enabled", "true");
        config.put("regenerate.map.interval", "1");

        config.put("players.all.time.peak", "0");
        config.put("players.daily.peak", "0");
        config.put("players.daily.peak.date", DateUtil.getCurrentDate(DateUtil.SHORT_DATE));

        config.put("catalogue.frontpage.input.1", "topstory_habbo_beta.gif");
        config.put("catalogue.frontpage.input.2", "Server is in beta!");
        config.put("catalogue.frontpage.input.3", "Please bare with us while we sort out the final kinks and hitches");
        config.put("catalogue.frontpage.input.4", "");

        config.put("enforce.strict.packet.policy", "true");
        config.put("trade.email.verification", "false");
        config.put("seasonal.items", "false");

        config.put("chat.spam.count", "10");
        config.put("walk.spam.count", "10");

        config.put("messenger.enable.official.update.speed", "false");

        config.put("poker.entry.price", "0");
        config.put("poker.entry.price.only.in.rooms", "");

        config.put("poker.entry.price.redistribute", "true");
        config.put("poker.entry.price.redistribute.on.tie", "true");
        config.put("poker.entry.price.redistribute.only.in.rooms", "");

        config.put("poker.reward.min.player", "2");
        config.put("poker.reward.min.player.only.in.rooms", "");

        config.put("poker.reward.credits.bonus", "0");
        config.put("poker.reward.credits.bonus.on.tie", "false");
        config.put("poker.reward.credits.bonus.only.in.rooms", "");

        config.put("poker.reward.rares", "");
        config.put("poker.reward.rares.only.in.rooms", "");
        config.put("poker.reward.rares.quantity", "1");
        config.put("poker.reward.rares.on.tie", "false");

        config.put("poker.reward.tickets", "0");
        config.put("poker.reward.tickets.on.tie", "false");
        config.put("poker.reward.tickets.only.in.rooms", "");

        config.put("poker.announce.winner", "false");
        config.put("poker.announce.winner.only.in.rooms", "0");
        config.put("poker.announce.rewards", "false");
        config.put("poker.announce.rewards.only.in.rooms", "");

        for (var set : CommandManager.getCommands()) {
            if (set.getValue().getPlayerRank().getRankId() > 1) {
                config.put("groups.ids.permission." + set.getKey()[0], "");
            }
        }

        return config;
    }

    @Override
    public void setConfigurationData(Map<String, String> config, PrintWriter writer) {

    }
}
