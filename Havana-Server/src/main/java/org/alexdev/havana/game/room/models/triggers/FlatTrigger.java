package org.alexdev.havana.game.room.models.triggers;

import org.alexdev.havana.dao.mysql.PetDao;
import org.alexdev.havana.dao.mysql.RoomVisitsDao;
import org.alexdev.havana.game.achievements.AchievementManager;
import org.alexdev.havana.game.achievements.AchievementType;
import org.alexdev.havana.game.entity.Entity;
import org.alexdev.havana.game.guides.GuideManager;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.item.interactors.InteractionType;
import org.alexdev.havana.game.item.interactors.types.PetNestInteractor;
import org.alexdev.havana.game.pathfinder.Position;
import org.alexdev.havana.game.pets.PetDetails;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.triggers.GenericTrigger;

import java.util.stream.Collectors;

public class FlatTrigger extends GenericTrigger {
    @Override
    public void onRoomEntry(Entity entity, Room room, boolean firstEntry, Object... customArgs) {
        if (!(entity instanceof Player)) {
            return;
        }

        if (room.getData().isCustomRoom())
            return;

        Player player = (Player) entity;

        /*player.send(new MessageComposer() {
            @Override
            public void compose(NettyResponse response) {
                response.writeBool(true);
            }

            @Override
            public short getHeader() {
                return 356; // Ed
            }
        });*/

        RoomVisitsDao.addVisit(player.getDetails().getId(), room.getId());
        AchievementManager.getInstance().tryProgress(AchievementType.ACHIEVEMENT_ROOMENTRY, player);

        if (player.getGuideManager().isGuide() && player.getGuideManager().getInvitedBy() > 0) {
            int invitedBy = player.getGuideManager().getInvitedBy();

            if (room.getData().getOwnerId() == invitedBy) {
                room.getEntityManager().getPlayers().stream()
                        .filter(p -> p.getDetails().getId() == invitedBy)
                        .findFirst()
                        .ifPresent(newb -> {
                            GuideManager.getInstance().tutorEnterRoom(player, newb);
                        });

                player.getGuideManager().setInvitedBy(0);
            }
        }

        if (firstEntry) {
            for (Item item : room.getItemManager().getFloorItems().stream().filter(item -> item.getDefinition().getInteractionType() == InteractionType.PET_NEST).collect(Collectors.toList())) {
                PetNestInteractor interactor = (PetNestInteractor) InteractionType.PET_NEST.getTrigger();

                PetDetails petDetails = PetDao.getPetDetails(item.getDatabaseId());

                if (petDetails != null) {
                    Position position = new Position(petDetails.getX(), petDetails.getY());
                    position.setRotation(petDetails.getRotation());

                    interactor.addPet(room, petDetails, position);
                }
            }
        }
    }

    @Override
    public void onRoomLeave(Entity entity, Room room, Object... customArgs)  {

    }
}
