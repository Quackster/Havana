package org.alexdev.havana.game.commands.registered;

import org.alexdev.havana.game.commands.Command;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.entity.EntityType;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.player.PlayerRank;
import org.alexdev.havana.messages.outgoing.alerts.ALERT;

public class AboutCommand extends Command {
    @Override
    public void setPlayerRank() {
        super.setPlayerRank(PlayerRank.NORMAL);
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity.getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)entity;
        
        player.send(new ALERT("Project Havana - Habbo Hotel v31 emulation" +
                "<br>" +
                "<br>Release: r31_20090312_0433_13751_b40895fb6101dbe96dc7b9d6477eeeb4" +
                "<br>" +
                "<br>Contributors:" +
                "<br> - ThuGie, Copyright, Raptosaur, Hoshiko, TOMYSSHADOW, Elijah " + // Call for help
                "<br>   Romuald, Glaceon, Nillus, Holo Team, Meth0d, office.boy, bbadzz" +
                "<br>" +
                "<br>   Big thanks to Sefhriloff & Ascii for assisting with SnowStorm." +
                "<br>" +
                "<br>" +
                "Made by Quackster from RaGEZONE"));
    }

    @Override
    public String getDescription() {
        return "Information about the software powering this retro";
    }
}
