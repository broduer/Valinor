package com.valinor.game.world.position.areas.impl;

import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.mechanics.Transmogrify;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.PLAYERS_IN_WILDERNESS;

public class WildernessArea extends Controller {

    private static final Logger log = LoggerFactory.getLogger(WildernessArea.class);

    /**
     * All players currently in the wilderness.
     */
    public static ArrayList<Player> players = new ArrayList<>(500);

    /**
     * Checks if the {@code player}'s remote MAC address has 2 or more players already in the wilderness.
     */
    public static boolean isAtWildernessLimitForMac(Player player) {
        int count = 0;
        for (Player others : players) {
            if (player.<String>getAttribOr(AttributeKey.MAC_ADDRESS,"invalid").equals(others.<String>getAttribOr(AttributeKey.MAC_ADDRESS,"invalid")) && ++count >= 2) {
                return true;
            }
        }
        return false;
    }

    public static boolean inWilderness(Tile tile) {
        return wildernessLevel(tile) > 0;
    }

    public static int wildernessLevel(Tile tile) {
        int z = (tile.y > 6400) ? tile.y - 6400 : tile.y;

        /*CustomWildernessRegions customWildernessRegions = CustomWildernessRegions.byRegion(tile.region());
        if(customWildernessRegions != null && customWildernessRegions.region == tile.region()) {
            return customWildernessRegions.level;
        }*/

        return ((z - 3520) >> 3) + 1;
    }

    public static boolean inUndergroundWilderness(Tile tile) {
        int region = tile.region();

        // Revenant caves:
        if (region == 12701 || region == 12702 || region == 12703 || region == 12957 || region == 12958 || region == 12959) return true;

        return region == 12192 || region == 12193 || region == 12961 || region == 11937 || region == 12443 || region == 12190;
    }

    /**
     * Any area, such as Wilderness, dangerous Instances, FFA clan war arena which a Player can attack another Player
     */
    public static boolean inAttackableArea(Mob player) {
        return WildernessArea.inWilderness(player.tile()) || player.getController() instanceof TournamentArea || Dueling.in_duel(player);
    }

    public static boolean inside_pirates_hideout(Tile tile) {
        Area original = new Area(3038, 3949, 3044, 3959);
        return tile.inArea(original);
    }

    public static boolean inside_axehut(Tile tile) {
        return tile.inArea(3187, 3958, 3194, 3962);
    }

    public static boolean inside_rouges_castle(Tile tile) {
        return tile.inArea(3275, 3922, 3297, 3946);
    }

    public static boolean inside_extended_pj_timer_zone(Tile tile) {
        return tile.inArea(3047, 3524, 3096, 3539);
    }

    public static boolean at_west_dragons(Tile tile) {
        return tile.inArea(2964, 3585, 2999, 3622);
    }

    public WildernessArea() {
        super(Collections.emptyList());
    }

    @Override
    public void enter(Mob mob) {
        if (mob.isPlayer()) {
            Player player = mob.getAsPlayer();
            //Clear the damage map when entering wilderness
            player.getCombat().getDamageMap().clear();
            players.add(player);
            player.getPacketSender().sendString(PLAYERS_IN_WILDERNESS.childId, QuestTab.InfoTab.INFO_TAB.get(PLAYERS_IN_WILDERNESS.childId).fetchLineData(player));
        }
    }

    @Override
    public void leave(Mob mob) {
        if (mob.isPlayer()) {
            Player player = mob.getAsPlayer();
            player.putAttrib(AttributeKey.LAST_WILD_LVL, 0);
            if (!Skulling.skulled(player)) {
                // wipe skull history incase
                player.clearAttrib(AttributeKey.SKULL_ENTRIES_TRACKER);
            }
            player.getInterfaceManager().openWalkable(-1);
            player.getPacketSender().sendInteractionOption("null", 2, true);
            players.remove(player);
            player.getPacketSender().sendString(PLAYERS_IN_WILDERNESS.childId, QuestTab.InfoTab.INFO_TAB.get(PLAYERS_IN_WILDERNESS.childId).fetchLineData(player));
            player.clearAttrib(AttributeKey.INWILD);
            player.clearAttrib(AttributeKey.PVP_WILDY_AGGRESSION_TRACKER);
        }
    }

    @Override
    public void process(Mob mob) {
        if (mob.isPlayer()) {
            Player player = mob.getAsPlayer();
            if (!inWilderness(player.tile())) {
                leave(player);
                return;
            }

            if (Transmogrify.isTransmogrified(player)) {
                Transmogrify.hardReset(player);
            }

            final int lvl = wildernessLevel(player.tile());
            if (lvl != player.getOrT(AttributeKey.LAST_WILD_LVL, -1)) {
                player.putAttrib(AttributeKey.LAST_WILD_LVL, lvl);

                // new level is inside wildy
                if (lvl > 0) {
                    // ONLY SET THIS WHEN ENTERING not 24.7 ever tick
                    player.putAttrib(AttributeKey.INWILD, World.getWorld().cycleCount());
                    player.getPacketSender().sendString(199, "Level: " + lvl);
                    player.getPacketSender().sendInteractionOption("Attack", 2, true);
                }
                player.getInterfaceManager().openWalkable(197);
            }
        }
    }

    @Override
    public boolean canTeleport(Player player) {
        return true;
    }

    @Override
    public boolean canAttack(Mob attacker, Mob target) {
        // Level checks only apply to PvP
        if (attacker.isPlayer() && target.isPlayer()) {

            // Is the player deep enough in the wilderness?

            var oppWithinLvl = attacker.skills().combatLevel() >= CombatFactory.getLowestLevel(target, attacker) && attacker.skills().combatLevel() <= CombatFactory.getHighestLevel(target, attacker);

            if (!oppWithinLvl) {
                attacker.message("Your level difference is too great! You need to move deeper into the Wilderness.");
                attacker.getMovementQueue().clear();
                return false;
            } else {
                var withinLvl = (target.skills().combatLevel() >= CombatFactory.getLowestLevel(attacker, target) && target.skills().combatLevel() <= CombatFactory.getHighestLevel(attacker, target));
                if (!withinLvl) {
                    attacker.message("Your level difference is too great! You need to move deeper into the Wilderness.");
                    attacker.getMovementQueue().clear();
                    return false;
                }
            }

            if (!inWilderness(target.tile())) {
                attacker.message("That player cannot be attacked, because they are not in the Wilderness.");
                attacker.getMovementQueue().clear();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean canTrade(Player player, Player target) {
        return true;
    }

    @Override
    public boolean isMulti(Mob mob) {
        return mob.<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA,-1) == 1;
    }

    @Override
    public boolean canEat(Player player, int itemId) {
        return true;
    }

    @Override
    public boolean canDrink(Player player, int itemId) {
        return true;
    }

    @Override
    public void onPlayerRightClick(Player player, Player rightClicked, int option) {
    }

    @Override
    public void defeated(Player killer, Mob mob) {
    }

    @Override
    public boolean handleObjectClick(Player player, GameObject object, int type) {
        return false;
    }

    @Override
    public boolean handleNpcOption(Player player, Npc npc, int type) {
        return false;
    }

    @Override
    public boolean inside(Mob mob) {
        return wildernessLevel(mob.tile()) > 0;
    }

    @Override
    public boolean useInsideCheck() {
        return true; //We want to check using the inside method of this class.
    }
}
