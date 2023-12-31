package com.valinor.game.content.instance.impl;

import com.valinor.game.content.EffectTimer;
import com.valinor.game.content.instance.InstancedArea;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.method.impl.npcs.hydra.AlchemicalHydra;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

public class AlchemicalHydraInstance {

    /**
     * The Alchemical hydra instance
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

    public List<Npc> npcList = new ArrayList<>();

    public AlchemicalHydraInstance() {

    }

    public static final Area ALCHEMICAL_HYDRA_AREA = new Area(1356, 10257, 1377, 10278);
    public static final Tile ENTRANCE_POINT = new Tile(1356, 10258);
    public static final Tile HYDRA_SPAWN_TILE = new Tile(1364, 10265);

    public void enterInstance(Player player) {
        instance = InstancedAreaManager.getSingleton().createInstancedArea(player, ALCHEMICAL_HYDRA_AREA);
        if (player != null && instance != null) {
            npcList.clear();
            player.teleport(ENTRANCE_POINT.transform(0, 0, instance.getzLevel()));

            //Create a Alchemical hydra instance, if there isn't one already spawning.
            var hydra = new AlchemicalHydra(HYDRA_SPAWN_TILE.transform(0, 0, instance.getzLevel()), player);
            hydra.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN,25);
            World.getWorld().registerNpc(hydra);
            npcList.add(hydra);
        }
        if(instance != null && player != null) {
            instance.setOnTeleport((p, t) -> {
                // so now we check if the target tile is inside or outside of the instance, if its out, we know we're leaving, if inside, we don't care
                if (t.getZ() != instance.getzLevel()) {
                    playerHasLeft = true;
                    player.getPacketSender().sendEffectTimer(0, EffectTimer.MONSTER_RESPAWN);
                }
            });
        }
    }

    public boolean playerHasLeft;

    public void clear(Player player) {
        for (Npc npc : npcList) {
            World.getWorld().unregisterNpc(npc);
        }
        npcList.clear();

        for (GroundItem gi : GroundItemHandler.getGroundItems()) {
            if (gi.getTile().inArea(ALCHEMICAL_HYDRA_AREA) && gi.getTile().level == player.tile().level) {
                GroundItemHandler.sendRemoveGroundItem(gi);
            }
        }
    }

    public void death(Player killer) {
        int respawnTimer = 50;
        if (killer != null) {
            killer.getPacketSender().sendEffectTimer((int) Utils.ticksToSeconds(respawnTimer), EffectTimer.MONSTER_RESPAWN);

            Chain.bound(null).cancelWhen(() -> {
                return playerHasLeft; // cancels as expected
            }).runFn(respawnTimer, () -> {
                //Create a new Alchemical hydra instance
                if(instance == null) {
                    return;
                }
                var hydra = new AlchemicalHydra(HYDRA_SPAWN_TILE.transform(0, 0, instance.getzLevel()), killer);
                World.getWorld().registerNpc(hydra);
                npcList.add(hydra);
            });
        }
    }
}
