package com.valinor.game.content.instance.impl;

import com.valinor.game.content.instance.InstancedArea;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.BRYOPHYTA;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 29, 2021
 */
public class BryophytaInstance {

    /**
     * The Bryophyta instance
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

    public BryophytaInstance() {

    }

    public static final Area BRYOPHYTA_AREA = new Area(3205, 9923, 3231, 9947);
    public static final Tile ENTRANCE_POINT = new Tile(3214, 9937);
    public static final Tile BRYOPHYTA_SPAWN_TILE = new Tile(3223, 9933);

    public void enterInstance(Player player) {
        instance = InstancedAreaManager.getSingleton().createInstancedArea(player, BRYOPHYTA_AREA);
        if (player != null && instance != null) {
            npcList.clear();
            player.teleport(ENTRANCE_POINT.transform(0, 0, instance.getzLevel()));

            //Create a Bryophyta instance, if there isn't one already spawning.
            var bryophyta = new Npc(BRYOPHYTA, BRYOPHYTA_SPAWN_TILE.transform(0, 0, instance.getzLevel())).spawn(false);
            bryophyta.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN,12);
            npcList.add(bryophyta);
        }
        if(instance != null && player != null) {
            instance.setOnTeleport((p, t) -> {
                // so now we check if the target tile is inside or outside the instance, if its out, we know we're leaving, if inside, we don't care
                if (t.getZ() != instance.getzLevel()) {
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

        for (GroundItem gi : GroundItemHandler.getGroundItems()) {
            if (!gi.getTile().inArea(BRYOPHYTA_AREA))
                continue;

            GroundItemHandler.sendRemoveGroundItem(gi);
        }
    }
}
