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
import static com.valinor.util.CustomItemIdentifiers.KEY_OF_DROPS;
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
                player.lock();
                player.inventory().remove(new Item(BRIMSTONE_KEY));
                player.message("You unlock the chest with your key.");
                player.sound(51);
                player.animate(536);
                Chain.bound(null).runFn(1, () -> {
                    int rolls = player.getMemberRights().isDiamondMemberOrGreater(player) ? 2 : 1;
                    if (Utils.percentageChance(player.extraItemRollChance())) {
                        rolls += 1;
                    }
                    for (int i = 0; i < rolls; i++) {
                        reward(player);
                    }
                    player.unlock();
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
                player.lock();
                player.inventory().remove(new Item(BRIMSTONE_KEY));
                player.message("You unlock the chest with your key.");
                player.sound(51);
                player.animate(536);
                Chain.bound(null).runFn(1, () -> {
                    int rolls = player.getMemberRights().isDiamondMemberOrGreater(player) ? 2 : 1;
                    if (Utils.percentageChance(player.extraItemRollChance())) {
                        rolls += 1;
                    }
                    for (int i = 0; i < rolls; i++) {
                        reward(player);
                    }
                    player.unlock();
                });
            } else {
                player.message("You need a crystal key to open this chest.");
            }
            return true;
        }
        return false;
    }

    private void reward(Player player) {
        Item reward = generateReward();
        player.getInventory().addOrDrop(reward);
        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " opened the brimstone chest and received " + Utils.getAOrAn(reward.unnote().name()) + " " + reward.unnote().name() + ".", "brimstone_chest");
        KEYS.log(player, BRIMSTONE_KEY, reward);

        if (rareDrop) {
            String worldMessage = "<img=452><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Brimstone Key</col>]</shad>:<col=AD800F> " + player.getUsername() + " received " + Utils.getAOrAn(reward.unnote().name()) + " <shad=0>" + reward.unnote().name() + "</shad>!";
            World.getWorld().sendWorldMessage(worldMessage);
            rareDrop = false;
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
        if (World.getWorld().rollDie(10, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else if (World.getWorld().rollDie(100, 1)) {
            rareDrop = true;
            return Utils.randomElement(RARE);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    private final List<Item> COMMON = Arrays.asList(
        new Item(COINS_995, 500_000 + World.getWorld().random(1_000_000)),
        new Item(DRAGON_ARROW, 150 + World.getWorld().random(350)),
        new Item(DRAGON_DART, 150 + World.getWorld().random(350)),
        new Item(DARK_CRAB + 1, 80 + World.getWorld().random(360)),
        new Item(GRIMY_RANARR_WEED + 1, 40 + World.getWorld().random(50)),
        new Item(RANARR_SEED, World.getWorld().random(1, 25)),
        new Item(GRIMY_SNAPDRAGON + 1, 40 + World.getWorld().random(50)),
        new Item(GRIMY_TOADFLAX + 1, 10 + World.getWorld().random(50)),
        new Item(SNAPDRAGON_SEED, World.getWorld().random(1, 25)),
        new Item(PURE_ESSENCE + 1, 3000 + World.getWorld().random(5000)),
        new Item(SUPER_COMBAT_POTION4 + 1, 20 + World.getWorld().random(125))
    );


    private final List<Item> UNCOMMON = Arrays.asList(
        new Item(COINS_995, 500_000 + World.getWorld().random(3_000_000)),
        new Item(OPAL_DRAGON_BOLTS_E, 150 + World.getWorld().random(150)),
        new Item(RUBY_DRAGON_BOLTS_E, 150 + World.getWorld().random(150)),
        new Item(DIAMOND_DRAGON_BOLTS_E, 150 + World.getWorld().random(150)),
        new Item(DRAGONSTONE_DRAGON_BOLTS_E, 150 + World.getWorld().random(150)),
        new Item(COINS_995, 5_000_000 + World.getWorld().random(2_500_000)),
        new Item(MYSTIC_HAT_DUSK, 1),
        new Item(MYSTIC_ROBE_TOP_DUSK, 1),
        new Item(MYSTIC_ROBE_BOTTOM_DUSK, 1),
        new Item(MYSTIC_BOOTS_DUSK, 1),
        new Item(MYSTIC_GLOVES_DUSK, 1),
        new Item(DRAGON_HASTA, 1),
        new Item(DRAGON_BOOTS, 1),
        new Item(DRAGON_KNIFE, 250),
        new Item(DRAGON_JAVELIN, 250),
        new Item(DRAGON_THROWNAXE, 100),
        new Item(SUPERIOR_DRAGON_BONES + 1, 25 + World.getWorld().random(80)),
        new Item(DAGANNOTH_BONES + 1, 55 + World.getWorld().random(80))
        );

    private final List<Item> RARE = Arrays.asList(

        new Item(BOOTS_OF_BRIMSTONE, 1),
        new Item(DWARF_CANNON_SET, 1),
        new Item(IMBUED_HEART, 1),
        new Item(BRIMSTONE_RING, 1),
        new Item(KEY_OF_DROPS, 1),
        new Item(DRAGON_SWORD, 1),
        new Item(DRAGON_HARPOON, 1)
    );
}
