package com.valinor.game.content.instance.impl;

import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.content.instance.MultiInstancedArea;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare.Nightmare;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare.TotemPlugin;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public class NightmareInstance {

    /**
     * The nightmare instance
     */
    private MultiInstancedArea instance;

    /**
     * get the instance
     *
     * @return the instance
     */
    public MultiInstancedArea getInstance() {
        return instance;
    }

    public List<Npc> npcList = new ArrayList<>();

    public NightmareInstance() {

    }

    public static final Area THE_NIGHTMARE_AREA = new Area(3859, 9941, 3886, 9962);
    public static final Tile ENTRANCE_POINT = new Tile(3872, 9942);
    public static final Tile THE_NIGHTMARE_SPAWN_TILE = new Tile(3870, 9949);
    private boolean inited = false;

    public void enterInstance(Player player) {
        instance = (MultiInstancedArea) InstancedAreaManager.getSingleton().createMultiInstancedArea(THE_NIGHTMARE_AREA);
        if (player != null && instance != null) {
            npcList.clear();

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
            npcList.add(nightmare);
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
        if(instance != null && player != null) {
            instance.setOnTeleport((p, t) -> {
                // so now we check if the target tile is inside or outside the instance, if its out, we know we're leaving, if inside, we don't care
                if (t.getZ() != instance.getzLevel() + 3) {
                    playerHasLeft = true;
                }
            });
        }
    }

    public boolean playerHasLeft;

    public void clear() {
        for (Npc npc : npcList) {
            World.getWorld().unregisterNpc(npc);
        }
        npcList.clear();
    }
}
