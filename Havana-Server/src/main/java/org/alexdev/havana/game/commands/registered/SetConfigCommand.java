package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.dao.mysql.SettingsDao;
import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;
import org.alexdev.havana.util.config.GameConfiguration;
import org.alexdev.havana.util.config.writer.GameConfigWriter;

public class SetConfigCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.ADMINISTRATOR);
    }

    @Override
    public void addArguments() {
        this.arguments.add("setting");
        this.arguments.add("value");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) entity;

        String setting = args[0];
        String value = args[1];

        if (!GameConfiguration.getInstance().getConfig().containsKey(setting)) {
            player.send(new ALERT("The setting \"" + setting + "\" doesn't exist!")); // TODO: Add locale
            return;
        }

        String oldValue = GameConfiguration.getInstance().getConfig().get(setting);

        SettingsDao.updateSetting(setting, value);
        GameConfiguration.reset(new GameConfigWriter());

        player.send(new ALERT("The setting \"" + setting + "\" value has been updated from \"" + oldValue + "\" to \"" + value + "\"")); // TODO: Add locale
    }

    @Override
    public String getDescription() {
        return "Allows to update server configs.";
    }
}
