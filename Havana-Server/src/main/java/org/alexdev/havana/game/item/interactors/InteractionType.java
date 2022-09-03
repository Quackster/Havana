package org.alexdev.havana.game.item.interactors;

import org.alexdev.havana.game.games.triggers.BattleShipsTrigger;
import org.alexdev.havana.game.games.triggers.ChessTrigger;
import org.alexdev.havana.game.games.triggers.PokerTrigger;
import org.alexdev.havana.game.games.triggers.TicTacToeTrigger;
import org.alexdev.havana.game.item.interactors.types.*;
import org.alexdev.havana.game.item.interactors.types.idol.IdolChairVoteInteractor;
import org.alexdev.havana.game.item.interactors.types.idol.IdolScoreboardInteractor;
import org.alexdev.havana.game.item.interactors.types.pool.*;
import org.alexdev.havana.game.item.interactors.types.wobblesquabble.WobbleSquabbleJoinQueue;
import org.alexdev.havana.game.item.interactors.types.wobblesquabble.WobbleSquabbleQueueTile;
import org.alexdev.havana.game.item.interactors.types.wobblesquabble.WobbleSquabbleTileStart;
import org.alexdev.havana.game.triggers.GenericTrigger;

public enum  InteractionType {
    DEFAULT(new DefaultInteractor()),
    BED(new BedInteractor()),
    CHAIR(new ChairInteractor()),
    TELEPORT(new TeleportInteractor()),
    ROOM_HIRE(new TeleportRoomHireInteractor()),
    VENDING_MACHINE(new VendingMachineInteractor()),
    LERT(new LertInteractor()),
    SCOREBOARD(new ScoreboardInteractor()),
    FORTUNE(new FortuneInteractor()),
    PET_NEST(new PetNestInteractor()),

    POOL_BOOTH(new PoolBoothInteractor()),
    POOL_LADDER(new PoolLadderInteractor()),
    POOL_EXIT(new PoolExitInteractor()),
    POOL_LIFT(new PoolLiftInteractor()),
    POOL_QUEUE(new PoolQueueInteractor()),

    GAME_TIC_TAC_TOE(new TicTacToeTrigger()),
    GAME_CHESS(new ChessTrigger()),
    GAME_BATTLESHIPS(new BattleShipsTrigger()),
    GAME_POKER(new PokerTrigger()),

    TOTEM_LEG(new TotemLegTrigger()),
    TOTEM_HEAD(new TotemHeadTrigger()),
    TOTEM_PLANET(new TotemPlanetTrigger()),

    WS_JOIN_QUEUE(new WobbleSquabbleJoinQueue()),
    WS_QUEUE_TILE(new WobbleSquabbleQueueTile()),
    WS_TILE_START(new WobbleSquabbleTileStart()),

    IDOL_VOTE_CHAIR(new IdolChairVoteInteractor()),
    IDOL_SCOREBOARD(new IdolScoreboardInteractor()),

    STEP_LIGHT(new StepLightInteractor()),
    LOVE_RANDOMIZER(new LoveRandomizerInteractor()),

    MULTI_HEIGHT(new MultiHeightInteractor()),
    ;

    private final GenericTrigger genericTrigger;

    InteractionType(GenericTrigger genericTrigger) {
        this.genericTrigger = genericTrigger;
    }

    public GenericTrigger getTrigger() {
        return genericTrigger;
    }
}
