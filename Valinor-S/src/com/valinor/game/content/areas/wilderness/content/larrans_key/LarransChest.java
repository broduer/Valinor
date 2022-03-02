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
import static com.valinor.util.CustomItemIdentifiers.PKP_TICKET;
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
        new Item(PKP_TICKET, 500),
        new Item(SUPER_COMBAT_POTION4+1, 5),
        new Item(DRAGON_DART, 25),
        new Item(DRAGON_KNIFE, 15),
        new Item(DRAGON_JAVELIN, 25),
        new Item(DRAGON_THROWNAXE, 25),
        new Item(ANTIVENOM4+1, 5),
        new Item(GUTHIX_REST4+1, 5),
        new Item(OBSIDIAN_HELMET, 1),
        new Item(OBSIDIAN_PLATEBODY, 1),
        new Item(OBSIDIAN_PLATELEGS, 1),
        new Item(RANGERS_TUNIC, 1),
        new Item(REGEN_BRACELET, 1),
        new Item(GRANITE_MAUL_24225, 1),
        new Item(BERSERKER_RING_I, 1),
        new Item(ARCHERS_RING_I, 1),
        new Item(SEERS_RING_I, 1),
        new Item(WARRIOR_RING_I, 1)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(DAGONHAI_HAT, 1),
        new Item(DAGONHAI_ROBE_TOP, 1),
        new Item(DAGONHAI_ROBE_BOTTOM, 1),
        new Item(PRIMORDIAL_BOOTS),
        new Item(PEGASIAN_BOOTS),
        new Item(ETERNAL_BOOTS),
        new Item(ABYSSAL_TENTACLE),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
        new Item(BLADE_OF_SAELDOR),
        new Item(BANDOS_GODSWORD),
        new Item(SARADOMIN_GODSWORD),
        new Item(ZAMORAK_GODSWORD),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_HELMET),
        new Item(PKP_TICKET, 3500),
        new Item(SERPENTINE_HELM),
        new Item(ZAMORAKIAN_HASTA),
        new Item(FREMENNIK_KILT),
        new Item(DRAGON_CROSSBOW),
        new Item(OPAL_DRAGON_BOLTS_E, 25),
        new Item(DIAMOND_DRAGON_BOLTS_E, 25),
        new Item(DRAGONSTONE_DRAGON_BOLTS_E, 25),
        new Item(ONYX_DRAGON_BOLTS_E, 25),
        new Item(DRAGON_SCIMITAR_OR)
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
        new Item(MORRIGANS_LEATHER_CHAPS, 1),
        new Item(ARMADYL_GODSWORD),
        new Item(AMULET_OF_TORTURE),
        new Item(NECKLACE_OF_ANGUISH),
        new Item(TORMENTED_BRACELET),
        new Item(ABYSSAL_DAGGER),
        new Item(DRAGON_CLAWS),
        new Item(TOXIC_BLOWPIPE),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(TRIDENT_OF_THE_SWAMP),
        new Item(ABYSSAL_BLUDGEON),
        new Item(VESTAS_LONGSWORD),
        new Item(STATIUSS_WARHAMMER),
        new Item(DRAGON_WARHAMMER),
        new Item(ELDER_MAUL),
        new Item(DINHS_BULWARK)
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
