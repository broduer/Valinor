package com.valinor.game.content.items.skillcape;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.util.CustomItemIdentifiers;

import java.util.EnumSet;
import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 31, 2022
 */
public enum MasterCape {

    ATTACK_CAPE(Skills.ATTACK, CustomItemIdentifiers.ATTACK_MASTER_CAPE),
    DEFENCE_CAPE(Skills.DEFENCE, CustomItemIdentifiers.DEFENCE_MASTER_CAPE),
    STRENGTH_CAPE(Skills.STRENGTH, CustomItemIdentifiers.STRENGTH_MASTER_CAPE),
    HITPOINTS_CAPE(Skills.HITPOINTS, CustomItemIdentifiers.HITPOINTS_MASTER_CAPE),
    RANGED_CAPE(Skills.RANGED, CustomItemIdentifiers.RANGE_MASTER_CAPE),
    PRAYER_CAPE(Skills.PRAYER, CustomItemIdentifiers.PRAYER_MASTER_CAPE),
    MAGIC_CAPE(Skills.MAGIC, CustomItemIdentifiers.MAGIC_MASTER_CAPE),
    COOKING_CAPE(Skills.COOKING, CustomItemIdentifiers.COOKING_MASTER_CAPE),
    WOODCUTTING_CAPE(Skills.WOODCUTTING, CustomItemIdentifiers.WOODCUTTING_MASTER_CAPE),
    FLETCHING_CAPE(Skills.FLETCHING, CustomItemIdentifiers.FLETCHING_MASTER_CAPE),
    FISHING_CAPE(Skills.FISHING, CustomItemIdentifiers.FISHING_MASTER_CAPE),
    FIREMAKING_CAPE(Skills.FIREMAKING, CustomItemIdentifiers.FIREMAKING_MASTER_CAPE),
    CRAFTING_CAPE(Skills.CRAFTING, CustomItemIdentifiers.CRAFTING_MASTER_CAPE),
    SMITHING_CAPE(Skills.SMITHING, CustomItemIdentifiers.SMITHING_MASTER_CAPE),
    MINING_CAPE(Skills.MINING, CustomItemIdentifiers.MINING_MASTER_CAPE),
    HERBLORE_CAPE(Skills.HERBLORE, CustomItemIdentifiers.HERBLORE_MASTER_CAPE),
    AGILITY_CAPE(Skills.AGILITY, CustomItemIdentifiers.AGILITY_MASTER_CAPE),
    THIEVING_CAPE(Skills.THIEVING, CustomItemIdentifiers.THIEVING_MASTER_CAPE),
    SLAYER_CAPE(Skills.SLAYER, CustomItemIdentifiers.SLAYER_MASTER_CAPE),
    FARMING_CAPE(Skills.FARMING, CustomItemIdentifiers.FARMING_MASTER_CAPE),
    RUNECRAFTING_CAPE(Skills.RUNECRAFTING, CustomItemIdentifiers.RUNECRAFTING_MASTER_CAPE),
    HUNTER_CAPE(Skills.HUNTER, CustomItemIdentifiers.HUNTER_MASTER_CAPE),
    CONSTRUCTION_CAPE(Skills.CONSTRUCTION, CustomItemIdentifiers.CONSTRUCTION_MASTER_CAPE);

    public int skill, cape;

    MasterCape(int skill, int cape) {
        this.skill = skill;
        this.cape = cape;
    }

    private static final ImmutableSet<MasterCape> MASTER_CAPES = Sets.immutableEnumSet(EnumSet.allOf(MasterCape.class));
    public static Optional<MasterCape> forId(int skill) {
        for (MasterCape masterCape : MASTER_CAPES) {
            if (skill == masterCape.skill) {
                return Optional.of(masterCape);
            }
        }
        return Optional.empty();
    }
}
