package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;

import static com.valinor.game.content.collection_logs.LogType.MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.CustomItemIdentifiers.WEAPON_MYSTERY_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 26, 2022
 */
public class WeaponMysteryBox extends Interaction {

    private static final int RARE_ROLL = 30;
    private static final int UNCOMMON_ROLL = 10;

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == WEAPON_MYSTERY_BOX || usedWith.getId() == WEAPON_MYSTERY_BOX) && (use.getId() == KEY_OF_DROPS || usedWith.getId() == KEY_OF_DROPS)) {
            if(player.inventory().contains(KEY_OF_DROPS)) {
                player.inventory().remove(KEY_OF_DROPS);
                reward(player, WEAPON_MYSTERY_BOX, 2);
            }
            return true;
        }
        if ((use.getId() == WEAPON_MYSTERY_BOX || usedWith.getId() == WEAPON_MYSTERY_BOX) && (use.getId() == GIANT_KEY_OF_DROPS || usedWith.getId() == GIANT_KEY_OF_DROPS)) {
            if(player.inventory().contains(KEY_OF_DROPS)) {
                player.inventory().remove(KEY_OF_DROPS);
                reward(player, WEAPON_MYSTERY_BOX, 3);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == WEAPON_MYSTERY_BOX) {
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
                    World.getWorld().sendWorldMessage("<img=452><shad=0><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a weapon mystery box!");
                }
                player.inventory().addOrBank(reward);
                MYSTERY_BOX.log(player, WEAPON_MYSTERY_BOX, reward);
                rare = false;

                var amt = reward.getAmount();
                player.message("You open the weapon mystery box and found...");
                player.message("x"+amt+" "+reward.unnote().name()+".");
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" just opened a weapon mystery box and received x"+amt+" "+reward.unnote().name()+".", "boxes_opened");
            }
            var opened = player.<Integer>getAttribOr(AttributeKey.WEAPON_MYSTERY_BOXES_OPENED, 0) + 1;
            player.putAttrib(AttributeKey.WEAPON_MYSTERY_BOXES_OPENED, opened);
        }
    }

    private static final Item[] RARE_REWARDS = new Item[]{
        new Item(NIGHTMARE_STAFF),
        new Item(SANGUINESTI_STAFF),
        new Item(GHRAZI_RAPIER),
        new Item(ELDER_MAUL),
        new Item(DRAGON_CLAWS),
        new Item(ARMADYL_GODSWORD),
        new Item(VESTAS_LONGSWORD),
        new Item(STATIUSS_WARHAMMER),
        new Item(VESTAS_SPEAR),
        new Item(TOXIC_BLOWPIPE),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(HEAVY_BALLISTA),
        new Item(ABYSSAL_DAGGER_P_13271),
        new Item(ARMADYL_CROSSBOW),
    };

    private static final Item[] UNCOMMON_REWARDS = new Item[]{
        new Item(BANDOS_GODSWORD),
        new Item(SARADOMIN_GODSWORD),
        new Item(ZAMORAK_GODSWORD),
        new Item(BARRELCHEST_ANCHOR),
        new Item(ZAMORAKIAN_SPEAR),
        new Item(DRAGON_HUNTER_CROSSBOW),
        new Item(ABYSSAL_DAGGER),
        new Item(LIGHT_BALLISTA),
        new Item(ZURIELS_STAFF),
    };

    private static final Item[] COMMON_REWARDS = new Item[]{
        new Item(GRANITE_MAUL_24225),
        new Item(DRAGON_2H_SWORD),
        new Item(ABYSSAL_TENTACLE),
        new Item(DRAGON_CROSSBOW),
        new Item(STAFF_OF_LIGHT),
        new Item(STAFF_OF_THE_DEAD),
        new Item(MASTER_WAND),
        new Item(DRAGON_SPEAR),
        new Item(DRAGON_SWORD),
    };
}
