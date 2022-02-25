package com.valinor.game.content.items.keys;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.KEYS;
import static com.valinor.game.world.entity.AttributeKey.COLLECTION_LOG_KEYS_OPENED;
import static com.valinor.util.CustomItemIdentifiers.COLLECTION_KEY;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.STONE_CHEST;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 10, 2021
 */
public class CollectionKey extends Interaction {

    private static final int RARE_ROLL = 10;
    private static final int UNCOMMON_ROLL = 5;

    private void open(Player player) {
        if(!player.inventory().contains(COLLECTION_KEY)) {
            player.message("This chest wont budge, I need some sort of Collection log key.");
            return;
        }

        player.lock();
        player.message("You unlock the chest with your key.");
        player.sound(51);
        player.animate(536);
        player.inventory().remove(COLLECTION_KEY,1);

        int roll = Utils.percentageChance(player.extraItemRollChance()) ? 2 : 1;
        for (int i = 0; i < roll; i++) {
            Item reward = reward();
            KEYS.log(player, COLLECTION_KEY, reward);
            player.inventory().addOrBank(reward);
            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " opened the collection log chest and received " + Utils.getAOrAn(reward.unnote().name()) +" "+reward.unnote().name()+".", "collection_chest");

            var sendWorldMessage = rare;
            var amount = reward.getAmount();
            var plural = amount > 1 ? "x" + amount : "x1";
            if (sendWorldMessage && !player.getUsername().equalsIgnoreCase("Box test")) {
                String worldMessage = "<img=452><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Collection Log Key</col>]</shad>:<col=AD800F> " + player.getUsername() + " received " + plural + " <shad=0>" + reward.name() + "</shad>!";
                World.getWorld().sendWorldMessage(worldMessage);
                rare = false;
            }
        }

        var keysUsed = player.<Integer>getAttribOr(COLLECTION_LOG_KEYS_OPENED,0) + 1;
        player.putAttrib(COLLECTION_LOG_KEYS_OPENED, keysUsed);
        player.message(Color.PURPLE.wrap("You have now opened "+Utils.formatNumber(keysUsed)+" collection log keys!"));
        player.unlock();
    }

    private boolean rare = false;
    private Item reward() {
        if (Utils.rollDie(RARE_ROLL, 1)) {
            rare = true;
            return Utils.randomElement(RARE);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    private static final List<Item> COMMON = Arrays.asList(
        new Item(ABYSSAL_WHIP, 1),
        new Item(AMULET_OF_FURY, 1),
        new Item(BERSERKER_RING, 1),
        new Item(ARCHERS_RING, 1),
        new Item(SEERS_RING, 1),
        new Item(WARRIOR_RING, 1)
    );

    private static final List<Item> UNCOMMON = Arrays.asList(
        new Item(ABYSSAL_DAGGER, 1),
        new Item(ABYSSAL_BLUDGEON, 1),
        new Item(ZAMORAK_HILT, 1),
        new Item(BANDOS_HILT, 1),
        new Item(INFERNAL_AXE, 1),
        new Item(INFERNAL_PICKAXE, 1)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(SARADOMIN_HILT, 1),
        new Item(ARMADYL_HILT, 1),
        new Item(ARMADYL_HELMET, 1),
        new Item(ARMADYL_CHESTPLATE, 1),
        new Item(ARMADYL_CHAINSKIRT, 1),
        new Item(BANDOS_BOOTS, 1),
        new Item(BANDOS_TASSETS, 1),
        new Item(BANDOS_CHESTPLATE, 1),
        new Item(BLADE_OF_SAELDOR, 1)
    );

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == STONE_CHEST) {
                open(player);
                return true;
            }
        }
        return false;
    }
}
