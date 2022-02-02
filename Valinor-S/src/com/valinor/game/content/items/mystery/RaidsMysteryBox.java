package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 01, 2021
 */
public class RaidsMysteryBox extends Interaction {

    private static final int RARE_ROLL = 12;
    private static final int UNCOMMON_ROLL = 7;

    private static final Item[] RARE = new Item[]{
        new Item(SCYTHE_OF_VITUR),
        new Item(TWISTED_BOW),
        new Item(ELDER_WAND_HANDLE),
        new Item(ELDER_WAND_STICK),
        new Item(TALONHAWK_CROSSBOW),
        new Item(SALAZAR_SLYTHERINS_LOCKET),
    };

    private static final Item[] UNCOMMON = new Item[]{
        new Item(TOM_RIDDLE_DIARY),
        new Item(MARVOLO_GAUNTS_RING),
        new Item(SANGUINESTI_STAFF),
        new Item(GHRAZI_RAPIER),
        new Item(ANCESTRAL_ROBE_BOTTOM),
        new Item(ANCESTRAL_ROBE_TOP),
        new Item(KODAI_INSIGNIA),
        new Item(AVERNIC_DEFENDER),
        new Item(JUSTICIAR_CHESTGUARD),
        new Item(JUSTICIAR_LEGGUARDS)
    };

    private static final Item[] COMMON = new Item[]{
        new Item(CLOAK_OF_INVISIBILITY),
        new Item(SWORD_OF_GRYFFINDOR),
        new Item(DRAGON_HUNTER_CROSSBOW),
        new Item(ELDER_MAUL),
        new Item(DINHS_BULWARK),
        new Item(DRAGON_CLAWS),
        new Item(DEXTEROUS_PRAYER_SCROLL),
        new Item(ARCANE_PRAYER_SCROLL),
        new Item(TWISTED_BUCKLER),
        new Item(ANCESTRAL_HAT),
        new Item(JUSTICIAR_FACEGUARD)
    };

    private boolean rare = false;

    public Item rollReward() {
        if (Utils.rollDie(RARE_ROLL, 1)) {
            rare = true;
            return Utils.randomElement(RARE);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == RAIDS_MYSTERY_BOX) {
                if(player.inventory().contains(RAIDS_MYSTERY_BOX)) {
                    player.inventory().remove(RAIDS_MYSTERY_BOX);
                    Item reward = rollReward();
                    if(rare) {
                        World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a raids mystery box!");
                    }
                    player.inventory().addOrBank(reward);
                    rare = false;

                    var amt = reward.getAmount();
                    player.message("You open the raids mystery box and found...");
                    player.message("x"+amt+" "+reward.unnote().name()+".");
                    Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a raids mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
                }
                return true;
            }
        }
        return false;
    }
}
