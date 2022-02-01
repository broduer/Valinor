package com.valinor.game.content.skill.impl.farming;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.NpcIdentifiers.TOOL_LEPRECHAUN;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 01, 2022
 */
public class ToolLeprechaun extends Interaction {

    @Override
    public boolean handleItemOnNpc(Player player, Item item, Npc npc) {
        if(npc.id() == TOOL_LEPRECHAUN) {
            ItemDefinition def = item.definition(World.getWorld());
            if(def.name != null) {
                String name = def.name;

                if(name.contains("Grimy")) {
                    int amount = player.inventory().count(item.getId());
                    player.inventory().remove(item.getId(), amount);
                    player.inventory().add(new Item(item.note().getId(), amount));
                    player.message("The tool leprechaun has noted your "+name+"'s.");
                } else {
                    player.message("The tool leprechaun will only note uncleaned grimys.");
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        return false;
    }
}
