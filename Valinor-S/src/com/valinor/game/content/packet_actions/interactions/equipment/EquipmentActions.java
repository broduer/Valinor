package com.valinor.game.content.packet_actions.interactions.equipment;

import com.valinor.game.content.skill.impl.slayer.content.SlayerRing;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.InteractionManager;

public class EquipmentActions {

    public static boolean operate(Player player, int slot, Item item) {

        if(InteractionManager.onEquipmentAction(player, item, slot)) {
            return true;
        }

        if (SlayerRing.onEquipmentOption(player, item, slot)) {
            return true;
        }
        return false;
    }
}
