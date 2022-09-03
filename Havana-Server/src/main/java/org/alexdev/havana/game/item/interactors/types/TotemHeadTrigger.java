package org.alexdev.havana.game.item.interactors.types;

import org.alexdev.havana.dao.mysql.ItemDao;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.item.interactors.enums.TotemColour;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;

import java.util.List;

public class TotemHeadTrigger extends GenericTrigger {
    @Override
    public void onInteract(Player player, Room room, Item item, int status) {
        //InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
        boolean placedOnLegs = item.getItemBelow() != null && item.getItemBelow().getDefinition().getSprite().equals("totem_leg");

        if (placedOnLegs) {
            return;
        }

        InteractionType.DEFAULT.getTrigger().onInteract(player, room, item, status);
    }

    @Override
    public void onItemPlaced(Player player, Room room, Item item) {
        this.onItemMoved(player, room, item, false, null, item.getItemBelow(), item.getItemAbove());
    }

    @Override
    public void onItemMoved(Player player, Room room, Item item, boolean isRotation, Position oldPosition, Item itemBelow, Item itemAbove) {
        Item below = item.getItemBelow();

        if (isRotation) {
            return;
        }

        boolean placedOnLegs = item.getItemBelow() != null && item.getItemBelow().getDefinition().getSprite().equals("totem_leg");

        if (!placedOnLegs) {
            TotemColour headColour = getHeadColour(item);

            // Turn the legs light back on
            if (itemBelow != null && itemBelow.getDefinition().getSprite().equals("totem_leg")) {
                if (headColour != TotemColour.NONE || item.getCustomData().equals("11")) { // "11" for the bird with the open wings
                    int state = convertHeadToColour(item, TotemColour.NONE);

                    if (state == 11) {
                        state = 2;
                    }

                    item.setCustomData(String.valueOf(state));
                    item.updateStatus();

                    itemBelow.setCustomData(String.valueOf(convertLegToColour(itemBelow, headColour)));
                    itemBelow.updateStatus();

                    ItemDao.updateItems(List.of(item, itemBelow));
                }
            }
        } else {
            // Convert the head to the colour of the legs once placed
            item.setCustomData(String.valueOf(convertHeadToColour(item, getLegColour(below))));
            item.updateStatus();

            below.setCustomData(String.valueOf(convertLegToColour(below, TotemColour.NONE)));
            below.updateStatus();

            ItemDao.updateItems(List.of(item, below));
        }
    }

    /**
     * Gets the colour of the totem legs.
     *
     * @param item the item colour
     * @return the totem colour
     */
    public static int convertHeadToColour(Item item, TotemColour colour) {
        boolean squid = item.getCustomData().equals("1")  || item.getCustomData().equals("8") || item.getCustomData().equals("9") || item.getCustomData().equals("10");
        boolean bird = item.getCustomData().equals("2") || item.getCustomData().equals("11") || item.getCustomData().equals("12") || item.getCustomData().equals("13") || item.getCustomData().equals("14");
        boolean human = item.getCustomData().equals("0") || item.getCustomData().equals("4") || item.getCustomData().equals("5") || item.getCustomData().equals("6");

        if (human) {
            if (colour == TotemColour.RED) {
                return 4;
            }

            if (colour == TotemColour.YELLOW) {
                return 5;
            }

            if (colour == TotemColour.BLUE) {
                return 6;
            }

            return 0;
        }

        if (bird) {
            if (colour == TotemColour.RED) {
                return 12;
            }

            if (colour == TotemColour.YELLOW) {
                return 13;
            }

            if (colour == TotemColour.BLUE) {
                return 14;
            }

            return 11; // Wings still out for bird
        }

        if (squid) {
            if (colour == TotemColour.RED) {
                return 8;
            }

            if (colour == TotemColour.YELLOW) {
                return 9;
            }

            if (colour == TotemColour.BLUE) {
                return 10;
            }

            return 1;
        }

        return 0;
    }

    /**
     * Gets the colour of the totem legs.
     *
     * @param item the item colour
     * @return the totem colour
     */
    public static TotemColour getHeadColour(Item item) {
        boolean redColour = item.getCustomData().equals("4") || item.getCustomData().equals("12") || item.getCustomData().equals("8");
        boolean yellowColour = item.getCustomData().equals("5") || item.getCustomData().equals("13") || item.getCustomData().equals("9");
        boolean blueColour = item.getCustomData().equals("6") || item.getCustomData().equals("14") || item.getCustomData().equals("10");
        boolean noColour = !yellowColour && !redColour && !blueColour;

        if (redColour) {
            return TotemColour.RED;
        }

        if (blueColour) {
            return TotemColour.BLUE;
        }

        if (yellowColour) {
            return TotemColour.YELLOW;
        }

        return TotemColour.NONE;
    }

    /**
     * Gets the colour of the totem legs.
     *
     * @param item the item colour
     * @return the totem colour
     */
    public static int convertLegToColour(Item item, TotemColour colour) {
        boolean squid = item.getCustomData().equals("4") || item.getCustomData().equals("5") || item.getCustomData().equals("6") || item.getCustomData().equals("7");
        boolean bird = item.getCustomData().equals("8") || item.getCustomData().equals("9") || item.getCustomData().equals("10") || item.getCustomData().equals("11");
        boolean human = item.getCustomData().equals("0") || item.getCustomData().equals("1") || item.getCustomData().equals("2") || item.getCustomData().equals("3");

        if (human) {
            if (colour == TotemColour.RED) {
                return 1;
            }

            if (colour == TotemColour.YELLOW) {
                return 2;
            }

            if (colour == TotemColour.BLUE) {
                return 3;
            }

            return 0;
        }

        if (bird) {
            if (colour == TotemColour.RED) {
                return 9;
            }

            if (colour == TotemColour.YELLOW) {
                return 10;
            }

            if (colour == TotemColour.BLUE) {
                return 11;
            }

            return 8;
        }

        if (squid) {
            if (colour == TotemColour.RED) {
                return 5;
            }

            if (colour == TotemColour.YELLOW) {
                return 6;
            }

            if (colour == TotemColour.BLUE) {
                return 7;
            }

            return 4;
        }

        return 0;
    }

    /**
     * Gets the colour of the totem legs.
     *
     * @param item the item colour
     * @return the totem colour
     */
    public static TotemColour getLegColour(Item item) {
        boolean redColour = item.getCustomData().equals("5") || item.getCustomData().equals("9") || item.getCustomData().equals("1");
        boolean yellowColour = item.getCustomData().equals("6") || item.getCustomData().equals("10") || item.getCustomData().equals("2");
        boolean blueColour = item.getCustomData().equals("3") || item.getCustomData().equals("7") || item.getCustomData().equals("11");
        boolean noColour = !yellowColour && !redColour && !blueColour;

        if (redColour) {
            return TotemColour.RED;
        }

        if (blueColour) {
            return TotemColour.BLUE;
        }

        if (yellowColour) {
            return TotemColour.YELLOW;
        }

        return TotemColour.NONE;
    }
}
