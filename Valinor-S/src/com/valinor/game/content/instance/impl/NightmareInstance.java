package com.valinor.game.content.instance.impl;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare.Nightmare;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare.TotemPlugin;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public class NightmareInstance {

    private static final Map<String, NightmareInstance> INSTANCES = new HashMap<>();
    public static final Area THE_NIGHTMARE_AREA = new Area(3859, 9941, 3886, 9962);
    private final int instanceLevel;
    private final Tile ENTRANCE_POINT = new Tile(3872, 9942);
    private static final Tile THE_NIGHTMARE_SPAWN_TILE = new Tile(3870, 9949);
    private boolean inited = false;
    private final ArrayList<Player> players;
    private final Nightmare nightmare;

    private NightmareInstance(ArrayList<Player> players) {
        this.players = players;
        instanceLevel = this.players.get(0).getIndex() * 4 + 3;

        //Create a Nightmare instance
        nightmare = new Nightmare(THE_NIGHTMARE_9432, THE_NIGHTMARE_SPAWN_TILE.transform(0, 0, instanceLevel));
        nightmare.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN, 25);
        nightmare.transmog(THE_NIGHTMARE_9432);
        Tile totemBase = new Tile(3840, 9936, instanceLevel);
        nightmare.setTotems(new TotemPlugin[]{new TotemPlugin(9434, totemBase.transform(23, 6)), new TotemPlugin(9437, totemBase.transform(39, 6)), new TotemPlugin(9440, totemBase.transform(23, 22)), new TotemPlugin(9443, totemBase.transform(39, 22))});
        for (TotemPlugin t : nightmare.getTotems()) {
            t.setNightmare(nightmare);
        }
        nightmare.spawn(false);
        Chain.bound(null).runFn(1, () -> {
            if (inited) {
                return;
            }
            nightmare.getUpdateFlag().reset();
            //nightmare.toggleShield();
        }).then(1, () -> {
            for (Player p : this.players) {
                p.teleport(ENTRANCE_POINT.transform(0, 0, instanceLevel));
                p.setNightmareInstance(this);
            }
        }).then(25, () -> {
            nightmare.transmog(THE_NIGHTMARE_9430);
            nightmare.animate(8611);
            for (Player p : this.players) {
                p.message("<col=ff0000>The Nightmare has awoken!");
            }
            //nightmare.setStage(0);
            inited = true;
        }).then(8, () -> {
            nightmare.transmog(THE_NIGHTMARE_9425);
            nightmare.setHitpoints(nightmare.maxHp());
            nightmare.animate(-1);
            nightmare.getCombat().attack(Utils.randomElement(players));
        });
    }

    public static NightmareInstance createInstance(ArrayList<Player> players) {
        NightmareInstance nightmareInstance = new NightmareInstance(players);
        INSTANCES.put(players.get(0).getUsername(), nightmareInstance);
        return nightmareInstance;
    }

    public static NightmareInstance joinInstance(String key, Player player) {
        NightmareInstance nightmareInstance = INSTANCES.get(key);
        if (nightmareInstance == null) {
            player.message(key + " has no registered instances.");
        } else {
            nightmareInstance.add(player);
        }
        return nightmareInstance;
    }

    public static void hmSize() {
        System.out.println(INSTANCES.size());
    }

    public static void hmClear() {
        INSTANCES.clear();
    }

    private void add(Player player) {
        if (!players.contains(player)) {
            players.add(player);
            player.teleport(ENTRANCE_POINT.transform(0, 0, instanceLevel));
        }
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void onDeath(Player player) {
        players.remove(player);
        //No players left in the region, lets clean up the instance
        if (getPlayers().size() == 0) {
            clearAll();
        }
    }

    public void onTeleport(Player player) {
        players.remove(player);
        //No players left in the region, lets clean up the instance
        if (getPlayers().size() == 0) {
            clearAll();
        }
    }

    public void onLogout(Player player) {
        players.remove(player);
        //No players left in the region, lets clean up the instance
        if (getPlayers().size() == 0) {
            clearAll();
        }
    }

    private void clearAll() {
        //remove all the npcs alive in the instance
        for (TotemPlugin t : nightmare.getTotems()) {
            World.getWorld().unregisterNpc(t);
        }

        World.getWorld().getNpcs().forEachInArea(THE_NIGHTMARE_AREA, n -> {
            if (nightmare != null) {
                nightmare.husksSpawned.clear();
            }
            if(!n.dead()) {
                n.hit(n, n.hp());
            }
        });

        for (GroundItem gi : GroundItemHandler.getGroundItems()) {
            if (!gi.getTile().inArea(THE_NIGHTMARE_AREA))
                continue;

            GroundItemHandler.sendRemoveGroundItem(gi);
        }
    }
}
