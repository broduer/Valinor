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
        new Item(SARADOMIN_BREW4+1,25),
        new Item(SUPER_RESTORE4+1,25),
        new Item(ANGLERFISH+1, 50),
        new Item(SUPER_COMBAT_POTION4+1, 10),
        new Item(DIVINE_SUPER_COMBAT_POTION4+1, 5),
        new Item(SNAPDRAGON_SEED, 3),
        new Item(RANARR_SEED, 5),
        new Item(ANTIQUE_LAMP_11137),
        new Item(MAGIC_LOGS+1, 200),
        new Item(YEW_LOGS+1, 300)
    );

    private static final List<Item> UNCOMMON = Arrays.asList(
        new Item(RUNE_POUCH),
        new Item(LOOTING_BAG),
        new Item(OBSIDIAN_HELMET),
        new Item(OBSIDIAN_PLATEBODY),
        new Item(OBSIDIAN_PLATELEGS),
        new Item(GRANITE_MAUL_24225),
        new Item(INFINITY_BOOTS),
        new Item(INFINITY_BOTTOMS),
        new Item(INFINITY_GLOVES),
        new Item(INFINITY_HAT),
        new Item(INFINITY_TOP),
        new Item(MASTER_WAND),
        new Item(DARK_BOW),
        new Item(ABYSSAL_WHIP),
        new Item(BERSERKER_NECKLACE)
    );

    private static final List<Item> RARE = Arrays.asList(
        new Item(RANGER_BOOTS),
        new Item(TOME_OF_FIRE),
        new Item(ZAMORAKIAN_SPEAR),
        new Item(ZAMORAKIAN_HASTA),
        new Item(ABYSSAL_TENTACLE),
        new Item(BRIMSTONE_RING),
        new Item(DRACONIC_VISAGE),
        new Item(DRAGON_BOOTS),
        new Item(AMULET_OF_FURY),
        new Item(BERSERKER_RING_I),
        new Item(ARCHERS_RING_I),
        new Item(SEERS_RING_I),
        new Item(GODSWORD_BLADE),
        new Item(LAVA_DRAGON_BONES+1,125),
        new Item(SUPERIOR_DRAGON_BONES+1,100)
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
