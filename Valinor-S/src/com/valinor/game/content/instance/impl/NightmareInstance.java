package com.valinor.game.content.instance.impl;

import com.valinor.game.content.instance.InstancedArea;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare.Nightmare;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public class NightmareInstance {

    /**
     * The nightmare instance
     */
    private InstancedArea instance;

    /**
     * get the instance
     *
     * @return the instance
     */
    public InstancedArea getInstance() {
        return instance;
    }

    public NightmareInstance() {

    }

    public static final Area THE_NIGHTMARE_AREA = new Area(3859, 9941, 3886, 9962);
    public static final Tile ENTRANCE_POINT = new Tile(3872, 9942);
    public static final Tile THE_NIGHTMARE_SPAWN_TILE = new Tile(3870, 9949);
    private boolean inited = false;
    private Nightmare nightmare;

    public void enterInstance(Player player) {
        instance = InstancedAreaManager.getSingleton().createInstancedArea(player, THE_NIGHTMARE_AREA);
        if (player != null && instance != null) {
            System.out.println("huh");
            instance.setOnTeleport((p, t) -> {
                System.out.println("setter activated");
                if (t.getZ() != instance.getzLevel()) {
                    cleanupInstance();
                    System.out.println("trigger");
                }
            });

            //Create a Nightmare instance
            var nightmare = new Nightmare(THE_NIGHTMARE_9432, THE_NIGHTMARE_SPAWN_TILE.transform(0, 0, instance.getzLevel() + 3));
            nightmare.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN,25);
            nightmare.transmog(THE_NIGHTMARE_9432);
            /*//TODO later
            Tile totemBase = new Tile(3840, 9936, instance.getzLevel() + 3);
            nightmare.setTotems(new TotemPlugin[] { new TotemPlugin(9434, totemBase.transform(23, 6)), new TotemPlugin(9437, totemBase.transform(39, 6)), new TotemPlugin(9440, totemBase.transform(23, 22)), new TotemPlugin(9443, totemBase.transform(39, 22)) });
            for (TotemPlugin t : nightmare.getTotems()) {
                t.setNightmare(nightmare);
                npcList.add(t);
            }*/
            nightmare.spawn(false);
            this.nightmare = nightmare;
            Chain.bound(null).runFn(1, () -> {
                if (inited) {
                    return;
                }
                nightmare.getUpdateFlag().reset();
                //nightmare.toggleShield();
            }).then(1, () -> player.teleport(ENTRANCE_POINT.transform(0, 0, instance.getzLevel() + 3))).then(25, () -> {
                nightmare.transmog(THE_NIGHTMARE_9430);
                nightmare.animate(8611);
                player.message("<col=ff0000>The Nightmare has awoken!");
                //nightmare.setStage(0);
                inited = true;
            }).then(8, () -> {
                nightmare.transmog(THE_NIGHTMARE_9425);
                nightmare.setHitpoints(nightmare.maxHp());
                nightmare.getCombat().setTarget(player);
                nightmare.getCombat().attack(player);
                nightmare.animate(-1);
            });
        }
    }

    public void cleanupInstance() {
        int count = 0;
        for (Player p : World.getWorld().getPlayers()) {
            if (p != null && nightmare != null && p.tile().region() == nightmare.tile().region())
                count++;
        }

        //No players left in the region, lets clean up the instance
        if(count == 0) {
            //remove all the npcs alive in the instance
            clearNpcs();
            nightmare = null;
        }
    }

    private void clearNpcs() {
        World.getWorld().getNpcs().forEachInArea(THE_NIGHTMARE_AREA, npc -> {
            if(npc.isRegistered() || npc != null || !npc.dead()) {
                World.getWorld().unregisterNpc(npc);
            }
        });
    }
}
