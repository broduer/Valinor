package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public class LegendaryMysteryBox extends Interaction {

    private static final int RARE_ROLL = 30;
    private static final int UNCOMMON_ROLL = 10;

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == LEGENDARY_MYSTERY_BOX || usedWith.getId() == LEGENDARY_MYSTERY_BOX) && (use.getId() == KEY_OF_DROPS || usedWith.getId() == KEY_OF_DROPS)) {
            if(player.inventory().contains(KEY_OF_DROPS)) {
                player.inventory().remove(KEY_OF_DROPS);
                reward(player, LEGENDARY_MYSTERY_BOX, 2);
            }
            return true;
        }
        if ((use.getId() == LEGENDARY_MYSTERY_BOX || usedWith.getId() == LEGENDARY_MYSTERY_BOX) && (use.getId() == GIANT_KEY_OF_DROPS || usedWith.getId() == GIANT_KEY_OF_DROPS)) {
            if(player.inventory().contains(KEY_OF_DROPS)) {
                player.inventory().remove(KEY_OF_DROPS);
                reward(player, LEGENDARY_MYSTERY_BOX, 3);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == LEGENDARY_MYSTERY_BOX) {
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
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a legendary mystery box!");
                }
                player.inventory().addOrBank(reward);
                MYSTERY_BOX.log(player, LEGENDARY_MYSTERY_BOX, reward);
                rare = false;

                var amt = reward.getAmount();
                player.message("You open the legendary mystery box and found...");
                player.message("x"+amt+" "+reward.unnote().name()+".");
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a legendary mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
            }
            var opened = player.<Integer>getAttribOr(AttributeKey.LEGENDARY_MYSTERY_BOXES_OPENED, 0) + 1;
            player.putAttrib(AttributeKey.LEGENDARY_MYSTERY_BOXES_OPENED, opened);
        }
    }

    private static final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(TWISTED_BOW),
        new Item(SCYTHE_OF_VITUR),
        new Item(ELYSIAN_SPIRIT_SHIELD),
        new Item(KORASI_SWORD),
        new Item(TORVA_FULL_HELM),
        new Item(TORVA_PLATEBODY),
        new Item(TORVA_PLATELEGS),
        new Item(CustomItemIdentifiers.NIFFLER),
        new Item(CustomItemIdentifiers.FAWKES),
        new Item(DRAGON_HUNTER_LANCE),
        new Item(SANGUINESTI_STAFF),
        new Item(ANCESTRAL_HAT),
        new Item(ANCESTRAL_ROBE_TOP),
        new Item(ANCESTRAL_ROBE_BOTTOM),
        new Item(KODAI_WAND),
        new Item(AVERNIC_DEFENDER),
        new Item(DONATOR_TICKET, 5_000),
        new Item(PETS_MYSTERY_BOX),
        new Item(DRAGON_HUNTER_CROSSBOW)
    );

    private static final List<Item> UNCOMMON_REWARDS = Arrays.asList(
        new Item(CustomItemIdentifiers.GRIM_REAPER_PET),
        new Item(CustomItemIdentifiers.BARRELCHEST_PET),
        new Item(SARADOMINS_BLESSED_SWORD),
        new Item(GHRAZI_RAPIER),
        new Item(ELDER_MAUL),
        new Item(MORRIGANS_COIF),
        new Item(MORRIGANS_LEATHER_BODY),
        new Item(MORRIGANS_LEATHER_CHAPS),
        new Item(ZURIELS_HOOD),
        new Item(ZURIELS_ROBE_TOP),
        new Item(ZURIELS_ROBE_BOTTOM),
        new Item(STATIUSS_FULL_HELM),
        new Item(STATIUSS_PLATEBODY),
        new Item(STATIUSS_PLATELEGS),
        new Item(VESTAS_SPEAR),
        new Item(STATIUSS_WARHAMMER),
        new Item(VESTAS_CHAINBODY),
        new Item(VESTAS_PLATESKIRT),
        new Item(VESTAS_LONGSWORD),
        new Item(NIGHTMARE_STAFF),
        new Item(INQUISITORS_GREAT_HELM),
        new Item(INQUISITORS_HAUBERK),
        new Item(INQUISITORS_PLATESKIRT),
        new Item(INQUISITORS_MACE),
        new Item(INFERNAL_CAPE)
    );

    private static final List<Item> COMMON_REWARDS = Arrays.asList(
        new Item(ZAMORAK_GODSWORD+1,3),
        new Item(SARADOMIN_GODSWORD+1,3),
        new Item(BANDOS_GODSWORD+1,3),
        new Item(ARMADYL_GODSWORD),
        new Item(DINHS_BULWARK),
        new Item(HEAVY_BALLISTA),
        new Item(DHAROKS_ARMOUR_SET+1,5),
        new Item(DRAGON_CLAWS),
        new Item(ARCANE_SPIRIT_SHIELD),
        new Item(TOXIC_BLOWPIPE),
        new Item(DRAGON_WARHAMMER),
        new Item(HARMONISED_ORB),
        new Item(ELDRITCH_ORB),
        new Item(VOLATILE_ORB),
        new Item(AMULET_OF_TORTURE),
        new Item(NECKLACE_OF_ANGUISH),
        new Item(TORMENTED_BRACELET),
        new Item(RING_OF_SUFFERING),
        new Item(SPECTRAL_SPIRIT_SHIELD),
        new Item(PRIMORDIAL_BOOTS),
        new Item(PEGASIAN_BOOTS),
        new Item(ETERNAL_BOOTS),
        new Item(COINS_995, 100_000_000),
        new Item(DONATOR_TICKET,2000),
        new Item(VOTE_TICKET,25),
        new Item(DONATOR_MYSTERY_BOX,3),
        new Item(KEY_OF_DROPS)
    );
}
