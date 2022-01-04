package com.valinor.game.content.items;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.valinor.game.world.entity.mob.player.GameMode;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.EnumSet;
import java.util.Optional;

import static com.valinor.game.world.entity.AttributeKey.EXP_LAMP_SKILL_SELECTED;
import static com.valinor.util.ItemIdentifiers.ANTIQUE_LAMP_11137;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 18, 2021
 */
public class ExperienceLamp extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == ANTIQUE_LAMP_11137) {
                rub(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        Optional<LampData> data = LampData.forId(button);
        if(data.isPresent()) {
            player.putAttrib(EXP_LAMP_SKILL_SELECTED, data.get().skill);
            player.getPacketSender().sendConfig(261, data.get().config);
            player.message("You have selected the "+ Utils.formatEnum(data.get().name())+" skill.");
            return true;
        }

        if(button == 2831) { // Confirm
            if(player.inventory().contains(ANTIQUE_LAMP_11137)) {
                if(player.gameMode() != GameMode.NONE) {
                    player.message(Color.RED.wrap("Ironmans are unable to rub experience lamps."));
                    return true;
                }
                player.inventory().remove(ANTIQUE_LAMP_11137);
                var skill = player.<Integer>getAttribOr(EXP_LAMP_SKILL_SELECTED, 0);

                var exp = switch (player.expmode()) {
                    case ROOKIE -> 10_000;
                    case GLADIATOR -> 3_500;
                    case CHALLENGER -> 1_000;
                };
                player.skills().addXp(skill, exp);
                player.getInterfaceManager().close();
            }
            return true;
        }
        return false;
    }

    private void rub(Player player) {
        player.getInterfaceManager().open(2808);
        player.getPacketSender().sendConfig(261,0);
        player.putAttrib(EXP_LAMP_SKILL_SELECTED,0);
    }

    private enum LampData {
        ATTACK(2812, Skills.ATTACK, 1),
        STRENGTH(2813, Skills.STRENGTH, 2),
        RANGE(2814, Skills.RANGED, 3),
        MAGIC(2815, Skills.MAGIC, 4),
        DEFENCE(2816, Skills.DEFENCE, 5),
        HITPOINTS(2817, Skills.HITPOINTS, 6),
        PRAYER(2818, Skills.PRAYER, 7),
        AGILITY(2819, Skills.AGILITY, 8),
        HERBLORE(2820, Skills.HERBLORE, 9),
        THIEVING(2821, Skills.THIEVING, 10),
        CRAFTING(2822, Skills.CRAFTING, 11),
        RUNECRAFTING(2823, Skills.RUNECRAFTING, 12),
        SLAYER(12034, Skills.SLAYER, 20),
        FARMING(13914, Skills.FARMING, 21),
        MINING(2824, Skills.MINING, 13),
        SMITHING(2825, Skills.SMITHING, 14),
        FISHING(2826, Skills.FISHING, 15),
        COOKING(2827, Skills.COOKING, 16),
        FIREMAKING(2828, Skills.FIREMAKING, 17),
        WOODCUTTING(2829, Skills.WOODCUTTING, 18),
        FLETCHING(2830, Skills.FLETCHING, 19),
        HUNTER(-1, Skills.HUNTER, -1),//TODO add button to interface
        CONSTRUCTION(-1, Skills.CONSTRUCTION, -1);//TODO add button to interface

        LampData(int button, int skill, int config) {
            this.button = button;
            this.skill = skill;
            this.config = config;
        }

        public int button, skill, config;

        /**
         * Caches our enum values.
         */
        private static final ImmutableSet<LampData> BUTTONS = Sets.immutableEnumSet(EnumSet.allOf(LampData.class));

        /**
         * Retrieves the button element for {@code id}.
         *
         * @param buttonId the button id that the exp lamp is attached to.
         * @return the exp lamp button wrapped in an optional, or an empty
         * optional if no button was found.
         */
        public static Optional<LampData> forId(int buttonId) {
            for (LampData data : BUTTONS) {
                if (buttonId == data.button) {
                    return Optional.of(data);
                }
            }
            return Optional.empty();
        }
    }
}
