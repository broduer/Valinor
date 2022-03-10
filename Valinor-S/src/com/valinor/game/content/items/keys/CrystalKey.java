package com.valinor.game.content.items.keys;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.KEYS;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <patrick.vanelderen@live.nl>
 * april 18, 2020
 */
public class CrystalKey extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == LOOP_HALF_OF_KEY && usedWith.getId() == TOOTH_HALF_OF_KEY) || (use.getId() == TOOTH_HALF_OF_KEY && usedWith.getId() == LOOP_HALF_OF_KEY)) {
            player.inventory().remove(new Item(LOOP_HALF_OF_KEY), true);
            player.inventory().remove(new Item(TOOTH_HALF_OF_KEY), true);
            player.inventory().add(new Item(CRYSTAL_KEY), true);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if (object.getId() == 172 && option == 1) {
            if (player.inventory().contains(CRYSTAL_KEY)) {
                player.lock();
                player.inventory().remove(new Item(CRYSTAL_KEY));
                player.message("You unlock the chest with your key.");
                player.sound(51);
                player.animate(536);
                Chain.bound(null).runFn(1, () -> {
                    GameObject old = new GameObject(172, object.tile(), object.getType(), object.getRotation());
                    GameObject spawned = new GameObject(173, object.tile(), object.getType(), object.getRotation());
                    ObjectManager.replace(old, spawned, 2);
                    int rolls = player.getMemberRights().isDiamondMemberOrGreater(player) ? 2 : 1;
                    if(Utils.percentageChance(player.extraItemRollChance())) {
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
        if (object.getId() == 172) {
            if (player.inventory().contains(CRYSTAL_KEY)) {
                player.lock();
                player.inventory().remove(new Item(CRYSTAL_KEY));
                player.message("You unlock the chest with your key.");
                player.sound(51);
                player.animate(536);
                Chain.bound(null).runFn(1, () -> {
                    GameObject old = new GameObject(172, object.tile(), object.getType(), object.getRotation());
                    GameObject spawned = new GameObject(173, object.tile(), object.getType(), object.getRotation());
                    ObjectManager.replace(old, spawned, 2);
                    int rolls = player.getMemberRights().isDiamondMemberOrGreater(player) ? 2 : 1;
                    if(Utils.percentageChance(player.extraItemRollChance())) {
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
        KEYS.log(player, CRYSTAL_KEY, reward);
        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " opened the crystal chest and received " + Utils.getAOrAn(reward.unnote().name()) + " " + reward.unnote().name() + ".", "crystal_chest");

        player.getInventory().addOrDrop(new Item(UNCUT_DRAGONSTONE));

        int keysUsed = player.<Integer>getAttribOr(AttributeKey.CRYSTAL_KEYS_OPENED, 0) + 1;
        player.putAttrib(AttributeKey.CRYSTAL_KEYS_OPENED, keysUsed);

        player.message("You find some treasure in the chest!");
        AchievementsManager.activate(player, Achievements.CRYSTAL_LOOTER_I, 1);
        AchievementsManager.activate(player, Achievements.CRYSTAL_LOOTER_II, 1);
        AchievementsManager.activate(player, Achievements.CRYSTAL_LOOTER_III, 1);
    }

    private Item generateReward() {
        if (World.getWorld().rollDie(50, 1)) {
            return Utils.randomElement(RARE);
        } else if (World.getWorld().rollDie(5, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    private final List<Item> COMMON = Arrays.asList(
        new Item(COINS_995, 500_000),
        new Item(UNCUT_DIAMOND + 1, 100 + World.getWorld().random(150)),
        new Item(UNCUT_SAPPHIRE + 1, 150 + World.getWorld().random(150)),
        new Item(UNCUT_RUBY + 1, 150 + World.getWorld().random(150)),
        new Item(UNCUT_EMERALD + 1, 150 + World.getWorld().random(150)),
        new Item(RAW_MONKFISH + 1, 250 + World.getWorld().random(150)),
        new Item(RAW_SHARK + 1, 200 + World.getWorld().random(150)),
        new Item(WILLOW_LOGS + 1, 200 + World.getWorld().random(150)),
        new Item(YEW_LOGS + 1, 150 + World.getWorld().random(150)),
        new Item(MAPLE_LOGS + 1, 250 + World.getWorld().random(150)),
        new Item(STEEL_BAR + 1, 250 + World.getWorld().random(150)),
        new Item(MITHRIL_BAR + 1, 100 + World.getWorld().random(150)),
        new Item(ADAMANTITE_BAR + 1, 50 + World.getWorld().random(150)),
        new Item(DRAGON_BONES + 1, 50 + World.getWorld().random(150)),
        new Item(DAGANNOTH_BONES + 1, 100 + World.getWorld().random(150))
        );


    private final List<Item> UNCOMMON = Arrays.asList(
        new Item(COINS_995, 5_000_000),
        new Item(MAGIC_LOGS + 1, 100 + World.getWorld().random(150)),
        new Item(RUNITE_BAR + 1, 100 + World.getWorld().random(150)),
        new Item(DRAGON_BOOTS, 1),
        new Item(SUPERIOR_DRAGON_BONES + 1, 35 + World.getWorld().random(35)),
        new Item(DRAGONSTONE_DRAGON_BOLTS_E, 150 + World.getWorld().random(150)),
        new Item(RUBY_DRAGON_BOLTS_E, 150 + World.getWorld().random(150)),
        new Item(OPAL_DRAGON_BOLTS_E, 150 + World.getWorld().random(150)),
        new Item(COOKED_KARAMBWAN, 250 + World.getWorld().random(150)),
        new Item(ABYSSAL_WHIP, 1),
        new Item(AMULET_OF_FURY, 1)
        );


    private final List<Item> RARE = Arrays.asList(
        new Item(NEW_CRYSTAL_BOW, 1),
        new Item(NEW_CRYSTAL_SHIELD, 1),
        new Item(NEW_CRYSTAL_HALBERD_FULL, 1)
    );

}
