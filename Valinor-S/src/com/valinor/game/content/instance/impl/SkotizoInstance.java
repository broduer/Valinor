package com.valinor.game.content.instance.impl;

import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.content.instance.SingleInstancedArea;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.vorkath.VorkathState;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.util.NpcIdentifiers.SKOTIZO;
import static com.valinor.util.NpcIdentifiers.VORKATH_8059;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 19, 2021
 */
public class SkotizoInstance {

    /**
     * The Skotizo instance
     */
    private SingleInstancedArea instance;

    /**
     * get the instance
     * @return the instance
     */
    public SingleInstancedArea getInstance() {
        return instance;
    }

    public List<Npc> npcList = new ArrayList<>();

    public SkotizoInstance() {

    }

    public Npc skotizo;

    public static final Tile ENTRANCE_POINT = new Tile(1695, 9878);
    public static final Area VORKATH_AREA = new Area(1678, 9870, 1714, 9905);

    public void enterInstance(Player player) {
        instance = (SingleInstancedArea) InstancedAreaManager.getSingleton().createSingleInstancedArea(player, VORKATH_AREA);
        if (player != null && instance != null) {
            npcList.clear();
            player.teleport(ENTRANCE_POINT.transform(0, 0, instance.getzLevel()));

            //Create a Skotizo instance
            skotizo = new Npc(SKOTIZO, new Tile(-3, 9, instance.getzLevel()));
            npcList.add(skotizo);
        }
    }

    public void clear(Player player) {
        for (Npc npc : npcList) {
            World.getWorld().unregisterNpc(npc);
        }
        npcList.clear();
    }
}
