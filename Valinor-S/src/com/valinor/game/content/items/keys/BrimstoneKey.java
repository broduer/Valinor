package com.valinor.game.content.items.keys;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.KEYS;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.BRIMSTONE_CHEST;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 17, 2021
 */
public class BrimstoneKey extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if (object.getId() == BRIMSTONE_CHEST && option == 1) {
            if (player.inventory().contains(BRIMSTONE_KEY)) {
                player.inventory().remove(new Item(BRIMSTONE_KEY));
                player.message("You unlock the chest with your key.");
                player.sound(51);
                player.animate(536);
                Chain.bound(null).runFn(1, () -> {
                    int roll = Utils.percentageChance(player.extraItemRollChance()) ? 2 : 1;
                    for (int i = 0; i < roll; i++) {
                        reward(player);
                    }
                });
            } else {
                player.message("You need a crystal key to open this chest.");
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if (object.getId() == BRIMSTONE_CHEST) {
            if (player.inventory().contains(BRIMSTONE_KEY)) {
                player.inventory().remove(new Item(BRIMSTONE_KEY));
                player.message("You unlock the chest with your key.");
                player.sound(51);
                player.animate(536);
                Chain.bound(null).runFn(1, () -> {
                    int roll = Utils.percentageChance(player.extraItemRollChance()) ? 2 : 1;
                    for (int i = 0; i < roll; i++) {
                        reward(player);
                    }
                });
            } else {
                player.message("You need a crystal key to open this chest.");
            }
            return true;
        }
        return false;
    }

    private void reward(Player player) {
        int rolls = player.getMemberRights().isDiamondMemberOrGreater(player) ? 2 : 1;
        for (int i = 0; i < rolls; i++) {
            Item reward = generateReward();
            player.getInventory().addOrDrop(reward);
            KEYS.log(player, BRIMSTONE_KEY, reward);

            if(rareDrop) {
                String worldMessage = "<img=505><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Brimstone Key</col>]</shad>:<col=AD800F> " + player.getUsername() + " received " + Utils.getAOrAn(reward.unnote().name()) + " <shad=0>" + reward.unnote().name() + "</shad>!";
                World.getWorld().sendWorldMessage(worldMessage);
                rareDrop = false;
            }
        }

        int keysUsed = player.<Integer>getAttribOr(AttributeKey.BRIMSTONE_KEYS_OPENED, 0) + 1;
        player.putAttrib(AttributeKey.BRIMSTONE_KEYS_OPENED, keysUsed);

        player.message("You find some treasure in the chest!");
        AchievementsManager.activate(player, Achievements.BRIMSTONE_LOOTER_I, 1);
        AchievementsManager.activate(player, Achievements.BRIMSTONE_LOOTER_II, 1);
        AchievementsManager.activate(player, Achievements.BRIMSTONE_LOOTER_III, 1);
    }

    private boolean rareDrop = false;

    private Item generateReward() {
        if(World.getWorld().rollDie(10,1)) {
            return Utils.randomElement(UNCOMMON);
        } else if(World.getWorld().rollDie(100,1)) {
            rareDrop = true;
            return Utils.randomElement(RARE);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    private final List<Item> COMMON = Arrays.asList(
        new Item(COINS_995, 2_500_000 + World.getWorld().random(2_500_000)),
        new Item(DRAGON_ARROWTIPS, 150 + World.getWorld().random(350)),
        new Item(DRAGON_DART_TIP, 150 + World.getWorld().random(350)),
        new Item(UNCUT_DIAMOND+1, 25 + World.getWorld().random(25)),
        new Item(UNCUT_RUBY+1, 25 + World.getWorld().random(25)),
        new Item(COAL+1, 300 + World.getWorld().random(200)),
        new Item(IRON_ORE+1, 350 + World.getWorld().random(200)),
        new Item(RUNE_FULL_HELM, 1),
        new Item(RUNE_PLATEBODY, 1),
        new Item(RUNE_PLATELEGS, 1),
        new Item(RAW_MONKFISH+1, 100 + World.getWorld().random(300)),
        new Item(RAW_SHARK+1, 100 + World.getWorld().random(250)),
        new Item(RAW_SEA_TURTLE+1, 80 + World.getWorld().random(200)),
        new Item(RAW_MANTA_RAY+1, 80 + World.getWorld().random(160)),
        new Item(RUNITE_ORE+1, 25),
        new Item(STEEL_BAR+1, 300 + World.getWorld().random(200)),
        new Item(RAW_DARK_CRAB+1, 80 + World.getWorld().random(160)),
        new Item(GRIMY_RANARR_WEED+1, 40 + World.getWorld().random(50)),
        new Item(RANARR_SEED, 30 + World.getWorld().random(25)),
        new Item(GRIMY_SNAPDRAGON+1, 40 + World.getWorld().random(50)),
        new Item(GRIMY_TOADFLAX+1, 40 + World.getWorld().random(50)),
        new Item(SNAPDRAGON_SEED, 30 + World.getWorld().random(25)),
        new Item(PURE_ESSENCE+1, 3000 + World.getWorld().random(3000)));

    private final List<Item> UNCOMMON = Arrays.asList(
        new Item(COINS_995, 5_000_000 + World.getWorld().random(2_500_000)),
        new Item(MYSTIC_HAT_DUSK, 1),
        new Item(MYSTIC_ROBE_TOP_DUSK, 1),
        new Item(MYSTIC_ROBE_BOTTOM_DUSK, 1),
        new Item(MYSTIC_BOOTS_DUSK, 1),
        new Item(MYSTIC_GLOVES_DUSK, 1),
        new Item(DRAGON_BOOTS, 1),
        new Item(DRAGON_BONES+1, 50),
        new Item(OPAL_DRAGON_BOLTS_E, 100),
        new Item(JADE_DRAGON_BOLTS_E, 100),
        new Item(PEARL_DRAGON_BOLTS_E, 100),
        new Item(TOPAZ_DRAGON_BOLTS_E, 100),
        new Item(SAPPHIRE_DRAGON_BOLTS_E, 100),
        new Item(EMERALD_DRAGON_BOLTS_E, 100),
        new Item(RUBY_DRAGON_BOLTS_E, 100),
        new Item(DIAMOND_DRAGON_BOLTS_E, 100),
        new Item(DRAGONSTONE_DRAGON_BOLTS_E, 100),
        new Item(ONYX_DRAGON_BOLTS_E, 100),
        new Item(RUBY_BOLTS_E, 75),
        new Item(SUPERIOR_DRAGON_BONES+1, 25 + World.getWorld().random(40)),
        new Item(DAGANNOTH_BONES+1, 20 + World.getWorld().random(50)),
        new Item(SUPER_COMBAT_POTION4+1, 20 + World.getWorld().random(25)));

    private final List<Item> RARE = Arrays.asList(
        new Item(HYDRAS_HEART, 1),
        new Item(HYDRAS_FANG, 1),
        new Item(HYDRAS_EYE, 1),
        new Item(BOOTS_OF_BRIMSTONE, 1));
}
