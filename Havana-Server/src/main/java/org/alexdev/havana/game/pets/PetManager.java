package org.alexdev.havana.game.pets;

import org.alexdev.havana.game.fuserights.Fuseright;
import org.alexdev.havana.game.item.Item;
import org.alexdev.havana.game.player.Player;
import org.alexdev.havana.game.room.Room;
import org.alexdev.havana.game.room.enums.StatusType;
import org.alexdev.havana.game.wordfilter.WordfilterManager;
import org.alexdev.havana.util.DateUtil;
import org.alexdev.havana.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class PetManager {
    private static PetManager instance;
    public String[] petSpeeches = {
            "pet.saying.play.croc.0=*Chases tail*",
            "pet.saying.sleep.cat.4=Meow!",
            "pet.saying.sniff.dog.2=*Grrruff! *Sniffs happily*",
            "pet.saying.angry.croc.2=*licks foot*",
            "pet.saying.sniff.dog.1=Grrruff! *Sniffs happily*",
            "pet.saying.eat.dog.0=Woof! *wags tail excitedly*",
            "pet.saying.sniff.dog.0=Grrruff! *Sniffs happily*",
            "pet.saying.beg.cat.2=Meow!",
            "pet.saying.play.dog.0=*Chases tail*",
            "pet.saying.beg.croc.2=Snap!",
            "pet.saying.play.croc.1=*Snaps happily*",
            "pet.saying.generic.dog.2=*Runs happily up to Habbo*",
            "pet.saying.generic.dog.3=*Jumps on Habbo happily*",
            "pet.saying.sniff.croc.0=*sniffs inquisitively*",
            "pet.saying.angry.dog.4=*Licks foot apologetically*",
            "pet.saying.angry.dog.3=Woof! Woof!",
            "pet.saying.play.dog.1=*Fetches ball*",
            "pet.saying.eat.cat.2=Meow! *Licks food*",
            "pet.saying.eat.cat.1=Meow! *Sniffs food*",
            "pet.saying.angry.croc.3=Snap! *innocent smile*",
            "pet.saying.beg.croc.1=Snap!",
            "pet.saying.beg.cat.0=Purrrrrr *walks around legs*",
            "pet.saying.generic.cat.4=Meow",
            "pet.saying.sleep.dog.6=Woof! Zzzzzzz",
            "pet.saying.angry.croc.4=Snap! *innocent smile*",
            "pet.saying.play.cat.1=*Chases mouse*",
            "pet.saying.sleep.dog.5=Ruff! Zzzzzzzz *wags tail*",
            "pet.saying.eat.croc.4=*Smiles contently*",
            "pet.saying.eat.cat.0=Meow! *Sniffs food*",
            "pet.saying.sleep.dog.3=Ruff! Zzzzzzzz *wags tail*",
            "pet.saying.sleep.dog.4=Ruff! Zzzzzzzz *wags tail*",
            "pet.saying.generic.croc.0=Rrrr....Grrrrrg....",
            "pet.saying.eat.croc.3=Snap! *Chomps on food*",
            "pet.saying.eat.croc.1=Snap! *Swallows food whole*",
            "pet.saying.angry.cat.2=Meow!",
            "pet.saying.sleep.dog.2=Ruff! Zzzzzzzz *wags tail*",
            "pet.saying.eat.croc.2=Snap! *Swallows food whole*",
            "pet.saying.sleep.croc.0=Zzzzzz *dreams of wilderbeast*",
            "pet.saying.sleep.croc.1=Zzzzzz *dreams of zebra*",
            "pet.saying.sleep.croc.5=Zzzzzz *dreams of wilderbeast*",
            "pet.saying.sleep.dog.0=Ruff! Zzzzzzzz *wags tail*",
            "pet.saying.sleep.croc.3=Zzzzzz *dreams of springboekt*",
            "pet.saying.angry.cat.3=Meow",
            "pet.saying.sleep.cat.1=Purrr zzzzz",
            "pet.saying.generic.croc.1=Snap!",
            "pet.saying.sleep.dog.1=Zzzzzz",
            "pet.saying.angry.cat.0=Mew *sad eyes*",
            "pet.saying.sniff.croc.2=*Watches for hours until it moves*",
            "pet.saying.sniff.croc.1=*Watches for hours until it moves*",
            "pet.saying.sleep.croc.2=Zzzzzz *dreams of wilderbeast*",
            "pet.saying.angry.cat.1=Purrrrrr ... *walks around legs*",
            "pet.saying.sniff.cat.0=*sniffs inquisitively*",
            "pet.saying.eat.dog.2=Woof! *chews*",
            "pet.saying.eat.dog.1=Woof! *chews*",
            "pet.saying.sleep.croc.6=Zzzzzz *dreams of wilderbeast*",
            "pet.saying.eat.dog.3=Woof! *wags tail excitedly*",
            "pet.saying.beg.croc.0=Snap!",
            "pet.saying.play.cat.0=*Jumps at shadow puppet*",
            "pet.saying.generic.croc.2=*Tail flip*",
            "pet.saying.beg.dog.1=Woof! Woof!",
            "pet.saying.generic.cat.1=Purrrr",
            "pet.saying.generic.cat.0=Purrrr",
            "pet.saying.eat.croc.0=Snap! *Swallows food whole*",
            "pet.saying.beg.dog.2=Woof! *Sits patiently*",
            "pet.saying.angry.dog.1=Ruff!",
            "pet.saying.generic.cat.3=Meow",
            "pet.saying.generic.dog.1=Woof! Woof!",
            "pet.saying.eat.dog.4=Woof! *wags tail excitedly*",
            "pet.saying.angry.croc.1=Snap! *innocent smile*",
            "pet.saying.generic.croc.3=*Walks up to Habbo excitedly*",
            "pet.saying.angry.croc.0=Snap!",
            "pet.saying.angry.dog.0=*Puppy dog eyes*",
            "pet.saying.beg.dog.0=*lifts paws onto Habbos knees*",
            "pet.saying.beg.cat.1=Purrrrrr *walks around legs*",
            "pet.saying.generic.cat.2=Purrrr",
            "pet.saying.sleep.cat.3=Meow!",
            "pet.saying.sleep.cat.0=Purrr zzzzz",
            "pet.saying.generic.dog.0=Grrrrufff!",
            "pet.saying.sleep.croc.4=Zzzzzz *dreams of elephant burgers*",
            "pet.saying.sleep.cat.2=Purrr zzzzz",
            "pet.saying.angry.dog.2=*Whines*"
    };

    /**
     * Handle speech for pets.
     *
     * @param player the player to call it
     * @param speech the speech to do
     */
    public void handleSpeech(Player player, Room room, String speech) {
        String[] data = speech.split(" ");

        if (data.length < 2)
            return;

        if (!room.hasRights(player.getDetails().getId()) && !player.hasFuse(Fuseright.MOD)) {
            return;
        }

        for (Pet pet : room.getEntityManager().getEntitiesByClass(Pet.class)) {
            if (pet.getDetails().getName().toLowerCase().equals(data[0].toLowerCase())) {
                petCommand(player, room, pet, speech.replace(data[0] + " ", ""));
            }
        }
    }

    private void petCommand(Player player, Room room, Pet pet, String command) {
        var item = pet.getRoomUser().getCurrentItem();
        boolean petCommanded = false;

        switch (command.toLowerCase()) {
            case "speak": {
                // Bark, meow, etc
                break;
            }
            case "good": {
                // Boosts pet's ego and makes them happy
                break;
            }
            case "beg": {
                // Beg for reward
                break;
            }
            case "go":
            case "go away": {
                // Pet moves away from player
                break;
            }
            case "bad": {
                // Tells pet off
                break;
            }
            case "come over":
            case "come":
            case "heel": {
                // Follow player
                break;
            }
            case "play dead":
            case "dead": {
                int length = ThreadLocalRandom.current().nextInt(4, 11);

                pet.getRoomUser().getStatuses().clear();
                pet.getRoomUser().getPosition().setRotation(pet.getRoomUser().getPosition().getBodyRotation());
                pet.getRoomUser().setStatus(StatusType.DEAD, StatusType.DEAD.getStatusCode(), length, null, -1, -1);
                pet.getRoomUser().setNeedsUpdate(true);

                pet.setAction(PetAction.DEAD);
                pet.setActionDuration(length);
                petCommanded = true;
                break;
            }
            case "sit": {
                int length = ThreadLocalRandom.current().nextInt(10, 30);

                pet.getRoomUser().getStatuses().clear();
                pet.getRoomUser().getPosition().setRotation(pet.getRoomUser().getPosition().getBodyRotation());
                pet.getRoomUser().setStatus(StatusType.SIT, StringUtil.format(pet.getRoomUser().getPosition().getZ()), length, null, -1, -1);
                pet.getRoomUser().setNeedsUpdate(true);

                pet.setAction(PetAction.SIT);
                pet.setActionDuration(length);
                petCommanded = true;
                break;
            }
            case "lie down":
            case "lay": {
                pet.getRoomUser().getStatuses().clear();
                pet.getRoomUser().getPosition().setRotation(pet.getRoomUser().getPosition().getBodyRotation());
                pet.getRoomUser().setStatus(StatusType.LAY, StringUtil.format(pet.getRoomUser().getPosition().getZ()) + " null");
                pet.getRoomUser().setNeedsUpdate(true);

                pet.setAction(PetAction.LAY);
                pet.setActionDuration(ThreadLocalRandom.current().nextInt(10, 30));

                petCommanded = true;
                break;
            }
            case "jump": {
                if (!pet.isActionAllowed()) {
                    return;
                }

                int length = ThreadLocalRandom.current().nextInt(2, 4);

                pet.getRoomUser().getStatuses().clear();
                pet.getRoomUser().getPosition().setRotation(pet.getRoomUser().getPosition().getBodyRotation());
                pet.getRoomUser().setStatus(StatusType.JUMP, StatusType.JUMP.getStatusCode().toLowerCase(), length, null, -1, -1);
                pet.getRoomUser().setNeedsUpdate(true);

                pet.setAction(PetAction.JUMP);
                pet.setActionDuration(length);
                petCommanded = true;
                break;
            }
            case "sleep": {
                if (pet.isDoingAction()) {
                    return;
                }

                Item nest = room.getItemManager().getByDatabaseId(pet.getDetails().getItemId());
                pet.getRoomUser().walkTo(nest.getPosition().getX(), nest.getPosition().getY());

                if (pet.getRoomUser().isWalking()) {
                    pet.setAction(PetAction.SLEEP);
                } else {
                    if (item != null) {
                        if (item.getDatabaseId() == pet.getDetails().getItemId()) {
                            item.getDefinition().getInteractionType().getTrigger().onEntityStop(pet, pet.getRoomUser(), item, false);
                            pet.setAction(PetAction.SLEEP);
                        }
                    }
                }

                if (pet.getAction() == PetAction.SLEEP) {
                    pet.getRoomUser().getStatuses().clear();
                    pet.getRoomUser().setNeedsUpdate(true);
                }

                break;
            }
            case "awake": {
                if (pet.getAction() != PetAction.SLEEP) {
                    return;
                }

                pet.awake();
                break;
            }
        }

        if (petCommanded) {
            if (pet.getRoomUser().isWalking()) {
                pet.getRoomUser().stopWalking();
            }
        }
    }

    /**
     * Get the pet stats when given the last time and stat type.
     *
     * @param lastTime the last time for an action
     * @param stat the current pet stat
     * @return the stat type
     */
    public int getPetStats(long lastTime, PetStat stat) {
        int a = (int) TimeUnit.SECONDS.toHours(DateUtil.getCurrentTimeSeconds() - lastTime);

        if (a < 2) {
            return stat.getAttributeType();
        }

        for (int x = 1; x <= stat.getAttributeType(); x++) {
            if (a > (2 * x))
                return x;
        }

        return stat.getAttributeType();

    }

    /**
     * Get if the pet name is valid.
     *
     * @param ownerName
     * @param name the name of the pet
     */
    public int isValidName(String ownerName, String name) {
        String[] words = StringUtil.getWords(name);

        for (String word : words) {
            if (WordfilterManager.getInstance().getBannedWords().contains(word)) {
                return 3;
            }
        }

        // Bad!
        if (!StringUtil.filterInput(name, true).equals(name)) {
            return 2;
        }

        if (name.isBlank() || name.contains(" "))  {
            return 2;
        }
        
        if (name.length() > 15) {
            return 1;
        }

        if (name.length() < 1) {
            return 1;
        }

        if (ownerName.toLowerCase().equals(name.toLowerCase())) {
            return 4;
        }

        return 0;
    }

    public PetType getType(Pet pet) {
        switch (pet.getDetails().getType()) {
            case "0": {
                return PetType.DOG;
            }
            case "1": {
                return PetType.CAT;
            }
            case "2": {
                return PetType.CROC;
            }
        }

        return null;
    }

    /**
     * Gets a random speech element by pet type.
     *
     * @param pet the pet type given
     * @return the speech selected
     */
    public String getRandomSpeech(Pet pet) {
        List<String> possibleChatValues = new ArrayList<>();

        String speech = null;

        if (pet.getAction() != null) {
            switch (pet.getAction()) {
                case SLEEP:
                case DEAD:
                    possibleChatValues.addAll(getChatValue(this.getType(pet), "sleep"));
                    break;
                case EAT:
                    possibleChatValues.addAll(getChatValue(this.getType(pet), "eat"));
                    break;
                case BEG:
                    possibleChatValues.addAll(getChatValue(this.getType(pet), "beg"));
                    break;
                case JUMP:
                case PLAY:
                    possibleChatValues.addAll(getChatValue(this.getType(pet), "play"));
                    break;
            }
        }

        if (pet.getAction() == PetAction.NONE || pet.getAction() == PetAction.SIT || pet.getAction() == PetAction.LAY) {
            possibleChatValues.addAll(getChatValue(this.getType(pet), "sniff"));
            possibleChatValues.addAll(getChatValue(this.getType(pet), "generic"));
        }

        if (possibleChatValues.size() > 0) {
            return possibleChatValues.get(ThreadLocalRandom.current().nextInt(possibleChatValues.size()));
        }

        return null;
    }

    private List<String> getChatValue(PetType type, String action) {
        List<String> speeches = new ArrayList<>();

        for (var speech : this.petSpeeches) {
            if (speech.startsWith("pet.saying." + action + "." + type.name().toLowerCase())) {
                speeches.add(speech.split("=")[1]);
            }
        }

        return speeches;
    }

    /**
     * Get the {@link PetManager} instance
     *
     * @return the item manager instance
     */
    public static PetManager getInstance() {
        if (instance == null) {
            instance = new PetManager();
        }

        return instance;
    }
}
