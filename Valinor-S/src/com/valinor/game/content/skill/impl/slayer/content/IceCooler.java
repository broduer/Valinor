package com.valinor.game.content.skill.impl.slayer.content;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.method.impl.npcs.slayer.DesertLizards;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.NpcIdentifiers;

import java.util.Arrays;
import java.util.List;

/**
 * @author PVE
 * @Since augustus 05, 2020
 */
public class IceCooler {

    private static final List<Integer> DESERT_LIZARDS = Arrays.asList(NpcIdentifiers.DESERT_LIZARD, NpcIdentifiers.DESERT_LIZARD_460, NpcIdentifiers.DESERT_LIZARD_461);

    public static boolean onItemOnNpc(Player player, Npc npc) {
        for (int DESERT_LIZARDS : DESERT_LIZARDS) {
            if (npc.id() == DESERT_LIZARDS) {
                int item = player.getAttrib(AttributeKey.ITEM_ID);

                if (item == ItemIdentifiers.ICE_COOLER) {
                    DesertLizards.iceCooler(player, npc, true);
                }
                return true;
            }
        }
        return false;
    }
}
