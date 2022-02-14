package com.valinor.game.content.skill.impl.magic;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.action.Action;
import com.valinor.game.action.policy.WalkablePolicy;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class EnchantDragonBolts {

    public enum DragonBolts {
        OPAL(OPAL_DRAGON_BOLTS, OPAL_DRAGON_BOLTS_E, 9),
        SAPPHIRE(SAPPHIRE_DRAGON_BOLTS, SAPPHIRE_DRAGON_BOLTS_E, 17),
        JADE(JADE_DRAGON_BOLTS, JADE_DRAGON_BOLTS_E, 19),
        PEARL(PEARL_DRAGON_BOLTS, PEARL_DRAGON_BOLTS_E, 29),
        EMERALD(EMERALD_DRAGON_BOLTS, EMERALD_DRAGON_BOLTS_E, 37),
        TOPAZ(TOPAZ_DRAGON_BOLTS, TOPAZ_DRAGON_BOLTS_E, 33),
        RUBY(RUBY_DRAGON_BOLTS, RUBY_DRAGON_BOLTS_E, 59),
        DIAMOND(DIAMOND_DRAGON_BOLTS, DIAMOND_DRAGON_BOLTS_E, 67),
        DRAGONSTONE(DRAGONSTONE_DRAGON_BOLTS, DRAGONSTONE_DRAGON_BOLTS_E, 78),
        ONYX(ONYX_DRAGON_BOLTS, ONYX_DRAGON_BOLTS_E, 97);

        private final int boltsToEnchant;
        private final int enchantedBoltsId;
        private final double exp;

        DragonBolts(int boltsToEnchant, int enchantedBoltsId, double exp) {
            this.boltsToEnchant = boltsToEnchant;
            this.enchantedBoltsId = enchantedBoltsId;
            this.exp = exp;
        }

        public static Optional<DragonBolts> forId(int boltsToEnchant) {
            for (DragonBolts bolt : DragonBolts.values()) {
                if (bolt.boltsToEnchant == boltsToEnchant)
                    return Optional.of(bolt);
            }
            return Optional.empty();
        }
    }

    /**
     * Handles enchanting the bolts.
     */
    public static void enchant(Player player, DragonBolts bolt) {
        if (player.skills().level(Skills.MAGIC) < 68) {
            player.message("You need a magic level of 68 to enchant these bolts!");
            return;
        }

        List<Item> runes = Arrays.asList(new Item(WATER_RUNE,15), new Item(EARTH_RUNE,15), new Item(COSMIC_RUNE,1));
        for (Item rune : runes) {
            if (!player.inventory().contains(rune.getId(), rune.getAmount())) {
                player.getPacketSender().sendMessage("You need " + rune.getAmount() + " " + rune.name() + (rune.getAmount() > 1 ? "s" : "") + " to enchant this bolt!");
                return;
            }
        }

        final var id = bolt.boltsToEnchant;
        if (!player.inventory().contains(new Item(id, 10))) {
            ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, id);
            String name = def.name;
            player.message("You need at least 10 " + Utils.getAOrAn(name) + "s " + name + " to do this!");
            return;
        }

        player.action.execute(action(player, bolt), true);
    }

    /**
     * Handles blowing the glass data.
     */
    private static Action<Player> action(Player player, DragonBolts bolt) {
        return new Action<>(player, 3, true) {

            @Override
            public void execute() {
                final var enchanted_id = bolt.enchantedBoltsId;
                final var remove_id = bolt.boltsToEnchant;
                player.getInterfaceManager().close();
                player.inventory().remove(remove_id, 10);
                List<Item> runes = Arrays.asList(new Item(WATER_RUNE,15), new Item(EARTH_RUNE,15), new Item(COSMIC_RUNE,1));
                player.inventory().removeAll(runes);
                player.animate(4462);
                player.graphic(759, 15, 0);
                player.inventory().add(new Item(enchanted_id, 10));
                player.skills().addXp(Skills.MAGIC, bolt.exp);

                String name = new Item(enchanted_id).name();
                player.message("You make " + Utils.getAOrAn(name) + " " + name + ".");
                stop();
            }

            @Override
            public String getName() {
                return "EnchantBolts";
            }

            @Override
            public boolean prioritized() {
                return false;
            }

            @Override
            public WalkablePolicy getWalkablePolicy() {
                return WalkablePolicy.NON_WALKABLE;
            }
        };
    }
}
