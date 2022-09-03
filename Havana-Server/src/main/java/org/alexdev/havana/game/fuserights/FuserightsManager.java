package org.alexdev.havana.game.fuserights;

import org.alexdev.havana.game.player.PlayerDetails;
import org.alexdev.havana.game.player.PlayerRank;

import java.util.*;

public class FuserightsManager {
    private static FuserightsManager instance;

    private List<Fuseright> fuserights;

    public FuserightsManager() {
        this.fuserights = new ArrayList<>();
        this.fuserights.addAll(Arrays.asList(Fuseright.values()));
    }

    /**
     * Get the available groups for user.
     *
     * @param minimumRank the minimum rank to see the fuseright
     * @return the lsit of groups
     */
    public List<Fuseright> getFuserightsForRank(PlayerRank minimumRank) {
        List<Fuseright> fuses = new ArrayList<>();

        for (Fuseright f : this.fuserights) {
            if (f.getMinimumRank() == null || f.isClubOnly()) {
                continue;
            }

            if (minimumRank.getRankId() >= f.getMinimumRank().getRankId()) {
                fuses.add(f);
            }
        }

        return fuses;
    }

    /*
     * Get available groups for users with a club subscription
     */
    public List<Fuseright> getClubFuserights() {
        List<Fuseright> fuses = new ArrayList<>();

        for (Fuseright f : this.fuserights) {
            if (f.isClubOnly()) {
                fuses.add(f);
            }
        }

        return fuses;
    }

    /**
     * Get if the rank has a fuseright.
     *
     * @param fuse the fuse to check against
     * @return true, if successful
     */
    public boolean hasFuseright(Fuseright fuse, PlayerDetails details) {
        for (Fuseright f : this.fuserights) {
            if (f.getMinimumRank() == null) {
                continue;
            }

            if (details.getRank().getRankId() >= f.getMinimumRank().getRankId() && f == fuse) {
                return true;
            }
        }

        if (details.hasClubSubscription()) {
            for (Fuseright f : this.fuserights) {
                if (f.isClubOnly() && f == fuse) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Get the fuseright manager instance.
     *
     * @return the fuseright manager
     */
    public static FuserightsManager getInstance() {
        if (instance == null) {
            instance = new FuserightsManager();
        }

        return instance;
    }
}
