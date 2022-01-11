package com.valinor.game.content.tournaments;

import com.valinor.game.world.position.Tile;
import com.valinor.util.NpcIdentifiers;

/**
 * @author Patrick van Elderen | Zerkikoth | PVE
 */
public class TournamentUtils {

    public static final Tile TORN_START_TILE = new Tile(1710, 4702, 0);
    public static final int THORVALD = 8146;
    public static final int GHOST_ID = NpcIdentifiers.GHOST_DISCIPLE;
    public static final int TOURNAMENT_REGION = 6729;
    public static final int TOURNAMENT_INTERFACE = 19999;
    public static final int TOURNAMENT_WALK_INTERFACE = 21100;
    public static final int TOURNAMENT_WALK_TIMER = 21102;
    public static final int TOURNAMENT_TEXT_FRAME = 20002;
    public static final int TOURNAMENT_TIME_LEFT_FRAME = 20003;
    public static final int PRIZE_FRAME = 20007;
    public static final int FIGHT_IMMUNE_TIMER = 50;//Equals 30 waiting seconds. This gives time to pot/setup prayers and inventory.
}
