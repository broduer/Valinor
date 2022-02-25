package com.valinor.game.content.areas.wilderness.content.larrans_key;

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

/**
 * @author Patrick van Elderen | February, 17, 2021, 14:17
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class LarransChest extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (obj.getId() == 34832) {
            if (player.inventory().contains(LARRANS_KEY)) {
                open(player);
            }
            return true;
        }
        return false;
    }

    private boolean rareDrop = false;

    private Item reward() {
        if (World.getWorld().rollDie(100, 1)) {
            rareDrop = true;
            return Utils.randomElement(EXTREME_RARE);
        } else if (World.getWorld().rollDie(50, 1)) {
            rareDrop = true;
            return Utils.randomElement(RARE);
        } else {
            return Utils.randomElement(OTHER);
        }
    }

    private static final List<Item> OTHER = Arrays.asList(
        new Item(UNCUT_DIAMOND + 1, 35 + World.getWorld().random(10)),
        new Item(UNCUT_RUBY + 1, 35 + World.getWorld().random(10)),
        new Item(COAL + 1, 450 + World.getWorld().random(650)),
        new Item(GOLD_ORE + 1, 150 + World.getWorld().random(250)),
        new Item(DRAGON_ARROWTIPS, 100 + World.getWorld().random(250)),
        new Item(COINS_995, 5_000_000 + World.getWorld().random(5_000_000)),
        new Item(IRON_ORE + 1, 500 + World.getWorld().random(250)),
        new Item(RUNE_FULL_HELM + 1, 3 + World.getWorld().random(5)),
        new Item(RUNE_PLATEBODY + 1, 3 + World.getWorld().random(5)),
        new Item(RUNE_PLATELEGS + 1, 3 + World.getWorld().random(5)),
        new Item(PURE_ESSENCE + 1, 4500 + World.getWorld().random(3000)),
        new Item(RAW_TUNA + 1, 150 + World.getWorld().random(525)),
        new Item(RAW_LOBSTER + 1, 150 + World.getWorld().random(525)),
        new Item(RAW_SWORDFISH + 1, 150 + World.getWorld().random(450)),
        new Item(RAW_MONKFISH + 1, 150 + World.getWorld().random(450)),
        new Item(RAW_SEA_TURTLE+1, 120 + World.getWorld().random(300)),
        new Item(RAW_MANTA_RAY+1, 120 + World.getWorld().random(240)),
        new Item(RUNITE_ORE+1, 20 + World.getWorld().random(35)),
        new Item(STEEL_BAR+1, 350 + World.getWorld().random(550)),
        new Item(MAGIC_LOGS+1, 180 + World.getWorld().random(220)),
        new Item(DRAGON_DART_TIP, 80 + World.getWorld().random(200)),
        new Item(TORSTOL_SEED, World.getWorld().random(1, 5)),
        new Item(SNAPDRAGON_SEED, World.getWorld().random(1, 5)),
        new Item(RANARR_SEED, World.getWorld().random(1, 5))
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(DAGONHAI_HAT, 1),
        new Item(DAGONHAI_ROBE_TOP, 1),
        new Item(DAGONHAI_ROBE_BOTTOM, 1)
    );

    private static final List<Item> EXTREME_RARE = Arrays.asList(
        new Item(VESTAS_LONGSWORD, 1),
        new Item(VESTAS_SPEAR, 1),
        new Item(VESTAS_CHAINBODY, 1),
        new Item(VESTAS_PLATESKIRT, 1),
        new Item(STATIUSS_WARHAMMER, 1),
        new Item(STATIUSS_FULL_HELM, 1),
        new Item(STATIUSS_PLATEBODY, 1),
        new Item(STATIUSS_PLATELEGS, 1),
        new Item(ZURIELS_STAFF, 1),
        new Item(ZURIELS_HOOD, 1),
        new Item(ZURIELS_ROBE_TOP, 1),
        new Item(ZURIELS_ROBE_BOTTOM, 1),
        new Item(MORRIGANS_JAVELIN, 100),
        new Item(MORRIGANS_THROWING_AXE, 100),
        new Item(MORRIGANS_COIF, 1),
        new Item(MORRIGANS_LEATHER_BODY, 1),
        new Item(MORRIGANS_LEATHER_CHAPS, 1)
    );

    private void open(Player player) {
        if (!player.inventory().contains(LARRANS_KEY)) {
            return;
        }

        player.animate(536);
        player.lock();
        Chain.bound(player).runFn(1, () -> {
            player.inventory().remove(new Item(LARRANS_KEY, 1), true);
            int roll = Utils.percentageChance(player.extraItemRollChance()) ? 2 : 1;
            for (int i = 0; i < roll; i++) {
                Item reward = reward();

                if (reward == null)
                    return;

                //Collection logs
                KEYS.log(player, LARRANS_KEY, reward);

                //When we receive a rare loot send a world message
                if (rareDrop) {
                    String msg = "<img=452><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Larran's chest</col>]: " + "<col=1e44b3>" + player.getUsername() + " has received " + Utils.getAOrAn(reward.unnote().name()) + " " + reward.unnote().name() + "!";
                    World.getWorld().sendWorldMessage(msg);
                    rareDrop = false;
                }
                player.inventory().addOrDrop(reward);
            }

            int keysUsed = (Integer) player.getAttribOr(AttributeKey.LARRANS_KEYS_OPENED, 0) + 1;
            player.putAttrib(AttributeKey.LARRANS_KEYS_OPENED, keysUsed);

            //Update achievements
            AchievementsManager.activate(player, Achievements.LARRANS_LOOTER_I, 1);
            AchievementsManager.activate(player, Achievements.LARRANS_LOOTER_II, 1);
            AchievementsManager.activate(player, Achievements.LARRANS_LOOTER_III, 1);
            player.unlock();
        });
    }
}
