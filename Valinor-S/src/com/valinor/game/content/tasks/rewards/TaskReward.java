package com.valinor.game.content.tasks.rewards;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | April, 08, 2021, 21:57
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class TaskReward {

    private static final List<Item> COMMON = Arrays.asList(
        new Item(DRAGON_PLATEBODY),
        new Item(DRAGONFIRE_SHIELD),
        new Item(ARMADYL_HELMET),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(BANDOS_CHESTPLATE),
        new Item(BANDOS_TASSETS),
        new Item(ZAMORAKIAN_SPEAR),
        new Item(ZAMORAKIAN_HASTA),
        new Item(ABYSSAL_TENTACLE),
        new Item(GRANITE_MAUL_24225,2),
        new Item(ANTIQUE_LAMP_11137)
    );

    private static final List<Item> UNCOMMON = Arrays.asList(
        new Item(ABYSSAL_DAGGER),
        new Item(STAFF_OF_THE_DEAD),
        new Item(TOXIC_BLOWPIPE),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(ABYSSAL_DAGGER_P_13271),
        new Item(ARMADYL_CROSSBOW),
        new Item(SERPENTINE_HELM),
        new Item(DINHS_BULWARK),
        new Item(NEITIZNOT_FACEGUARD),
        new Item(DRAGONFIRE_WARD),
        new Item(SARADOMIN_GODSWORD),
        new Item(ZAMORAK_GODSWORD),
        new Item(BANDOS_GODSWORD)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(HARMONISED_ORB),
        new Item(ELDRITCH_ORB),
        new Item(VOLATILE_ORB),
        new Item(NIGHTMARE_STAFF),
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
        new Item(STATIUSS_WARHAMMER),
        new Item(VESTAS_CHAINBODY),
        new Item(VESTAS_PLATESKIRT),
        new Item(VESTAS_SPEAR),
        new Item(VESTAS_LONGSWORD),
        new Item(DRAGON_CLAWS),
        new Item(ARMADYL_GODSWORD),
        new Item(HEAVY_BALLISTA),
        new Item(AMULET_OF_TORTURE),
        new Item(NECKLACE_OF_ANGUISH),
        new Item(RING_OF_SUFFERING),
        new Item(TORMENTED_BRACELET)
    );

    public static List<Item> getPossibleRewards() {
        return new ArrayList<>(RARE);
    }

    private static boolean rare = false;

    public static void reward(Player player) {
        var coins = World.getWorld().random(2_500_000, 7_500_000);
        List<Item> items;
        if (Utils.rollDie(20, 1)) {
            rare = true;
            items = RARE;
        } else if (Utils.rollDie(10, 1)) {
            items = UNCOMMON;
        } else {
            items = COMMON;
        }

        Item item = Utils.randomElement(items);
        if (rare && !player.getUsername().equalsIgnoreCase("Box test")) {
            boolean amOverOne = item.getAmount() > 1;
            String amtString = amOverOne ? "x " + Utils.format(item.getAmount()) + "" : Utils.getAOrAn(item.unnote().name());
            String msg = "<img=452><shad=0><col=AD800F>" + player.getUsername() + " has received " + amtString + "<shad=0> " + item.unnote().name() + "</shad>!";
            World.getWorld().sendWorldMessage(msg);
            rare = false;
        }

        player.inventory().addOrBank(new Item(COINS_995, coins), item);
    }
}
