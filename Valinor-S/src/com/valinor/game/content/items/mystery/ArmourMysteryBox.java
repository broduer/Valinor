package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import static com.valinor.game.content.collection_logs.LogType.MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 26, 2022
 */
public class ArmourMysteryBox extends Interaction {

    private static final int RARE_ROLL = 30;
    private static final int UNCOMMON_ROLL = 10;

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == ARMOUR_MYSTERY_BOX || usedWith.getId() == ARMOUR_MYSTERY_BOX) && (use.getId() == KEY_OF_DROPS || usedWith.getId() == KEY_OF_DROPS)) {
            if(player.inventory().contains(KEY_OF_DROPS)) {
                player.inventory().remove(KEY_OF_DROPS);
                reward(player, ARMOUR_MYSTERY_BOX, 2);
            }
            return true;
        }
        if ((use.getId() == ARMOUR_MYSTERY_BOX || usedWith.getId() == ARMOUR_MYSTERY_BOX) && (use.getId() == GIANT_KEY_OF_DROPS || usedWith.getId() == GIANT_KEY_OF_DROPS)) {
            if(player.inventory().contains(KEY_OF_DROPS)) {
                player.inventory().remove(KEY_OF_DROPS);
                reward(player, ARMOUR_MYSTERY_BOX, 3);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == ARMOUR_MYSTERY_BOX) {
                reward(player, item.getId(),1);
                return true;
            }
        }
        return false;
    }

    private boolean rare = false;

    public Item rollReward() {
        if (Utils.rollDie(RARE_ROLL, 1)) {
            rare = true;
            return Utils.randomElement(RARE_REWARDS);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON_REWARDS);
        } else {
            return Utils.randomElement(COMMON_REWARDS);
        }
    }

    private void reward(Player player, int id, int rolls) {
        if(player.inventory().contains(id)) {
            player.inventory().remove(id);
            for (int i = 0; i < rolls; i++) {
                Item reward = rollReward();
                if(rare) {
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a armour mystery box!");
                }
                player.inventory().addOrBank(reward);
                MYSTERY_BOX.log(player, ARMOUR_MYSTERY_BOX, reward);
                rare = false;

                var amt = reward.getAmount();
                player.message("You open the armour mystery box and found...");
                player.message("x"+amt+" "+reward.unnote().name()+".");
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a armour mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
            }
            var opened = player.<Integer>getAttribOr(AttributeKey.ARMOUR_MYSTERY_BOXES_OPENED, 0) + 1;
            player.putAttrib(AttributeKey.ARMOUR_MYSTERY_BOXES_OPENED, opened);
        }
    }

    private static final Item[] RARE_REWARDS = new Item[]{
        new Item(ELYSIAN_SPIRIT_SHIELD),
        new Item(STATIUSS_FULL_HELM),
        new Item(STATIUSS_PLATEBODY),
        new Item(STATIUSS_PLATELEGS),
        new Item(VESTAS_CHAINBODY),
        new Item(VESTAS_PLATESKIRT),
        new Item(ANCESTRAL_HAT),
        new Item(ANCESTRAL_ROBE_TOP),
        new Item(ANCESTRAL_ROBE_BOTTOM),
        new Item(MORRIGANS_COIF),
        new Item(MORRIGANS_LEATHER_BODY),
        new Item(MORRIGANS_LEATHER_CHAPS),
        new Item(ZURIELS_HOOD),
        new Item(ZURIELS_ROBE_TOP),
        new Item(ZURIELS_ROBE_BOTTOM),
        new Item(ARCANE_SPIRIT_SHIELD),
        new Item(NEITIZNOT_FACEGUARD),
        new Item(DINHS_BULWARK),
    };

    private static final Item[] UNCOMMON_REWARDS = new Item[]{
        new Item(DRAGONFIRE_SHIELD),
        new Item(DRAGONFIRE_WARD),
        new Item(ANCIENT_WYVERN_SHIELD),
        new Item(SPECTRAL_SPIRIT_SHIELD),
        new Item(SERPENTINE_HELM),
        new Item(MAGMA_HELM),
        new Item(TANZANITE_HELM),
        new Item(ARMADYL_HELMET),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
    };

    private static final Item[] COMMON_REWARDS = new Item[]{
        new Item(FIGHTER_HAT),
        new Item(FIGHTER_TORSO),
        new Item(FIRE_CAPE),
        new Item(DRAGON_DEFENDER),
        new Item(DRAGON_PLATELEGS),
        new Item(DRAGON_CHAINBODY_3140),
        new Item(DRAGON_PLATESKIRT),
        new Item(BLESSED_SPIRIT_SHIELD),
        new Item(SPIRIT_SHIELD),
        new Item(DRAGON_BOOTS),
        new Item(ROBIN_HOOD_HAT),
        new Item(RANGER_BOOTS),
        new Item(DRAGON_FULL_HELM),
        new Item(OBSIDIAN_HELMET),
        new Item(OBSIDIAN_PLATEBODY),
        new Item(OBSIDIAN_PLATELEGS),
        new Item(MAGES_BOOK),
        new Item(AVAS_ASSEMBLER),
        new Item(ODIUM_WARD),
        new Item(MALEDICTION_WARD),
        new Item(DHAROKS_ARMOUR_SET),
        new Item(KARILS_ARMOUR_SET),
        new Item(AHRIMS_ARMOUR_SET),
        new Item(VERACS_ARMOUR_SET),
        new Item(TORAGS_ARMOUR_SET),
        new Item(GUTHANS_ARMOUR_SET),
        new Item(DRAGON_PLATEBODY),
        new Item(PRIMORDIAL_BOOTS),
        new Item(PEGASIAN_BOOTS),
        new Item(ETERNAL_BOOTS),
    };
}
