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
import static com.valinor.util.CustomItemIdentifiers.*;
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
        List<Item> items = null;
        if (World.getWorld().rollDie(100, 1)) {
            rareDrop = true;
            items = EXTREME_RARE;
        } else if (World.getWorld().rollDie(50, 1)) {
            rareDrop = true;
            items = RARE;
        } else {
            items = OTHER;
        }
        return Utils.randomElement(items);
    }

    private static final List<Item> OTHER = Arrays.asList(
        new Item(PKP_TICKET, 500 + World.getWorld().random(500)),
        new Item(SUPER_COMBAT_POTION4 + 1, 50),
        new Item(ANTIVENOM4 + 1, 50),
        new Item(OBSIDIAN_HELMET),
        new Item(OBSIDIAN_PLATEBODY),
        new Item(OBSIDIAN_PLATELEGS),
        new Item(RANGERS_TUNIC),
        new Item(GRANITE_MAUL_24225 + 1, 3),
        new Item(BERSERKER_RING_I),
        new Item(ARCHERS_RING_I),
        new Item(SEERS_RING_I),
        new Item(WARRIOR_RING_I),
        new Item(BANDOS_GODSWORD),
        new Item(SARADOMIN_GODSWORD),
        new Item(ZAMORAK_GODSWORD)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(PRIMORDIAL_BOOTS),
        new Item(PEGASIAN_BOOTS),
        new Item(ETERNAL_BOOTS),
        new Item(ARMADYL_GODSWORD),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
        new Item(ARMADYL_CROSSBOW),
        new Item(STAFF_OF_THE_DEAD),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_HELMET),
        new Item(PKP_TICKET, 5000),
        new Item(SERPENTINE_HELM),
        new Item(ZAMORAKIAN_HASTA),
        new Item(FREMENNIK_KILT),
        new Item(DRAGON_CROSSBOW),
        new Item(AMULET_OF_TORTURE),
        new Item(NECKLACE_OF_ANGUISH),
        new Item(TORMENTED_BRACELET)
    );

    private static final List<Item> EXTREME_RARE = Arrays.asList(
        new Item(VESTAS_LONGSWORD),
        new Item(VESTAS_SPEAR),
        new Item(VESTAS_CHAINBODY),
        new Item(VESTAS_PLATESKIRT),
        new Item(STATIUSS_WARHAMMER),
        new Item(STATIUSS_FULL_HELM),
        new Item(STATIUSS_PLATEBODY),
        new Item(STATIUSS_PLATELEGS),
        new Item(ZURIELS_STAFF),
        new Item(ZURIELS_HOOD),
        new Item(ZURIELS_ROBE_TOP),
        new Item(ZURIELS_ROBE_BOTTOM),
        new Item(MORRIGANS_COIF),
        new Item(MORRIGANS_LEATHER_BODY),
        new Item(MORRIGANS_LEATHER_CHAPS),
        new Item(DRAGON_CLAWS),
        new Item(TOXIC_BLOWPIPE),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(DRAGON_WARHAMMER),
        new Item(ELDER_MAUL),
        new Item(DINHS_BULWARK),
        new Item(ANCIENT_STATIUS_WARHAMMER),
        new Item(ANCIENT_VESTAS_LONGSWORD)
    );

    private void open(Player player) {
        if (!player.inventory().contains(LARRANS_KEY)) {
            return;
        }

        player.animate(536);
        player.lock();
        Chain.bound(player).runFn(1, () -> {
            player.inventory().remove(new Item(LARRANS_KEY, 1), true);
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

            //Give half a teleblock
            player.teleblock(250, true);

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
