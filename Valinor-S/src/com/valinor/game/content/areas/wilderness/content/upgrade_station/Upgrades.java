package com.valinor.game.content.areas.wilderness.content.upgrade_station;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.valinor.game.world.entity.AttributeKey.*;

/**
 * @author Patrick van Elderen | January, 28, 2021, 13:19
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public enum Upgrades {
    ABYSSAL_WHIP_TIER_ONE(WHIP_KILLS, 73010, 73030, 0, "Abyssal whip", "Tier 1", 125, ItemIdentifiers.ABYSSAL_WHIP, 15441, WHIP_KILLS_TIER_UNLOCKED),
    ABYSSAL_WHIP_TIER_TWO(WHIP_KILLS, 73010, 73030, 0, "Abyssal whip", "Tier 2", 275, 15441, 15442, WHIP_KILLS_TIER_UNLOCKED),
    ABYSSAL_WHIP_TIER_THREE(WHIP_KILLS, 73010, 73030, 0, "Abyssal whip", "Tier 3", 425, 15442, 15443, WHIP_KILLS_TIER_UNLOCKED),
    ABYSSAL_WHIP_TIER_FOUR(WHIP_KILLS, 73010, 73030, 0, "Abyssal whip", "Tier 4", 575, 15443, 15444, WHIP_KILLS_TIER_UNLOCKED),
    ABYSSAL_WHIP_TIER_FIVE(WHIP_KILLS, 73010, 73030, 0, "Abyssal whip", "Tier 5", 750, 15444, 15445, WHIP_KILLS_TIER_UNLOCKED),

    DARK_BOW_TIER_ONE(DBOW_KILLS, 73011, 73030, 6, "Dark bow", "Tier 1", 75, ItemIdentifiers.DARK_BOW, 12765, DBOW_KILLS_TIER_UNLOCKED),
    DARK_BOW_TIER_TWO(DBOW_KILLS, 73011, 73030, 6, "Dark bow", "Tier 2", 125, 12765, 12766, DBOW_KILLS_TIER_UNLOCKED),
    DARK_BOW_TIER_THREE(DBOW_KILLS, 73011, 73030, 6, "Dark bow", "Tier 3", 175, 12766, 12767, DBOW_KILLS_TIER_UNLOCKED),
    DARK_BOW_TIER_FOUR(DBOW_KILLS, 73011, 73030, 6, "Dark bow", "Tier 4", 225, 12767, 12768, DBOW_KILLS_TIER_UNLOCKED),
    DARK_BOW_TIER_FIVE(DBOW_KILLS, 73011, 73030, 6, "Dark bow", "Tier 5", 300, 12768, 15706, DBOW_KILLS_TIER_UNLOCKED),

    GRANITE_MAUL_TIER_ONE(GMAUL_KILLS, 73012, 73030, 12, "Granite maul", "Tier 1", 75, ItemIdentifiers.GRANITE_MAUL_24225, 16200, GMAUL_KILLS_TIER_UNLOCKED),
    GRANITE_MAUL_TIER_TWO(GMAUL_KILLS, 73012, 73030, 12, "Granite maul", "Tier 2", 150, 16200, 16201, GMAUL_KILLS_TIER_UNLOCKED),
    GRANITE_MAUL_TIER_THREE(GMAUL_KILLS, 73012, 73030, 12, "Granite maul", "Tier 3", 250, 16201, 16202, GMAUL_KILLS_TIER_UNLOCKED),
    GRANITE_MAUL_TIER_FOUR(GMAUL_KILLS, 73012, 73030, 12, "Granite maul", "Tier 4", 350, 16202, 16203, GMAUL_KILLS_TIER_UNLOCKED),
    GRANITE_MAUL_TIER_FIVE(GMAUL_KILLS, 73012, 73030, 12, "Granite maul", "Tier 5", 500, 16203, 16204, GMAUL_KILLS_TIER_UNLOCKED),

    DRAGON_DAGGER_TIER_ONE(DDS_KILLS, 73013, 73030, 18, "Dragon dagger", "Tier 1", 75, ItemIdentifiers.DRAGON_DAGGERP_5698, 16209, DDS_KILLS_TIER_UNLOCKED),
    DRAGON_DAGGER_TIER_TWO(DDS_KILLS, 73013, 73030, 18, "Dragon dagger", "Tier 2", 125, 16209, 16210, DDS_KILLS_TIER_UNLOCKED),
    DRAGON_DAGGER_TIER_THREE(DDS_KILLS, 73013, 73030, 18, "Dragon dagger", "Tier 3", 200, 16210, 16211, DDS_KILLS_TIER_UNLOCKED),
    DRAGON_DAGGER_TIER_FOUR(DDS_KILLS, 73013, 73030, 18, "Dragon dagger", "Tier 4", 300, 16211, 16212, DDS_KILLS_TIER_UNLOCKED),
    DRAGON_DAGGER_TIER_FIVE(DDS_KILLS, 73013, 73030, 18, "Dragon dagger", "Tier 5", 400, 16212, 16213, DDS_KILLS_TIER_UNLOCKED),

    STAFF_OF_LIGHT_TIER_ONE(STAFF_OF_LIGHT_KILLS, 73014, 73030, 24, "Staff of light", "Tier 1", 25, ItemIdentifiers.STAFF_OF_LIGHT, 16277, STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED),
    STAFF_OF_LIGHT_TIER_TWO(STAFF_OF_LIGHT_KILLS, 73014, 73030, 24, "Staff of light", "Tier 2", 50, 16277, 16269, STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED),
    STAFF_OF_LIGHT_TIER_THREE(STAFF_OF_LIGHT_KILLS, 73014, 73030, 24, "Staff of light", "Tier 3", 75, 16269, 16270, STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED),
    STAFF_OF_LIGHT_TIER_FOUR(STAFF_OF_LIGHT_KILLS, 73014, 73030, 24, "Staff of light", "Tier 4", 100, 16270, 16271, STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED),
    STAFF_OF_LIGHT_TIER_FIVE(STAFF_OF_LIGHT_KILLS, 73014, 73030, 24, "Staff of light", "Tier 5", 150, 16271, 16272, STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED),

    DRAGON_SCIMITAR_TIER_ONE(D_SCIM_KILLS, 74010, 74030, 0, "Dragon scimitar", "Tier 1", 25, ItemIdentifiers.DRAGON_SCIMITAR, 16236, D_SCIM_KILLS_TIER_UNLOCKED),
    DRAGON_SCIMITAR_TIER_TWO(D_SCIM_KILLS, 74010, 74030, 0, "Dragon scimitar", "Tier 2", 50, 16236, 16237, D_SCIM_KILLS_TIER_UNLOCKED),
    DRAGON_SCIMITAR_TIER_THREE(D_SCIM_KILLS, 74010, 74030, 0, "Dragon scimitar", "Tier 3", 75, 16237, 16238, D_SCIM_KILLS_TIER_UNLOCKED),
    DRAGON_SCIMITAR_TIER_FOUR(D_SCIM_KILLS, 74010, 74030, 0, "Dragon scimitar", "Tier 4", 100, 16238, 16239, D_SCIM_KILLS_TIER_UNLOCKED),
    DRAGON_SCIMITAR_TIER_FIVE(D_SCIM_KILLS, 74010, 74030, 0, "Dragon scimitar", "Tier 5", 150, 16239, 16240, D_SCIM_KILLS_TIER_UNLOCKED),

    DRAGON_MACE_TIER_ONE(D_MACE_KILLS, 74011, 74030, 6, "Dragon mace", "Tier 1", 50, ItemIdentifiers.DRAGON_MACE, 16254, D_MACE_KILLS_TIER_UNLOCKED),
    DRAGON_MACE_TIER_TWO(D_MACE_KILLS, 74011, 74030, 6, "Dragon mace", "Tier 2", 100, 16254, 16255, D_MACE_KILLS_TIER_UNLOCKED),
    DRAGON_MACE_TIER_THREE(D_MACE_KILLS, 74011, 74030, 6, "Dragon mace", "Tier 3", 150, 16255, 16256, D_MACE_KILLS_TIER_UNLOCKED),
    DRAGON_MACE_TIER_FOUR(D_MACE_KILLS, 74011, 74030, 6, "Dragon mace", "Tier 4", 200, 16256, 16257, D_MACE_KILLS_TIER_UNLOCKED),
    DRAGON_MACE_TIER_FIVE(D_MACE_KILLS, 74011, 74030, 6, "Dragon mace", "Tier 5", 250, 16257, 16258, D_MACE_KILLS_TIER_UNLOCKED),

    DRAGON_LONGSWORD_TIER_ONE(D_LONG_KILLS, 74012, 74030, 12, "Dragon longsword", "Tier 1", 50, ItemIdentifiers.DRAGON_LONGSWORD, 16245, D_LONG_KILLS_TIER_UNLOCKED),
    DRAGON_LONGSWORD_TIER_TWO(D_LONG_KILLS, 74012, 74030, 12, "Dragon longsword", "Tier 2", 100, 16245, 16246, D_LONG_KILLS_TIER_UNLOCKED),
    DRAGON_LONGSWORD_TIER_THREE(D_LONG_KILLS, 74012, 74030, 12, "Dragon longsword", "Tier 3", 150, 16246, 16247, D_LONG_KILLS_TIER_UNLOCKED),
    DRAGON_LONGSWORD_TIER_FOUR(D_LONG_KILLS, 74012, 74030, 12, "Dragon longsword", "Tier 4", 200, 16247, 16248, D_LONG_KILLS_TIER_UNLOCKED),
    DRAGON_LONGSWORD_TIER_FIVE(D_LONG_KILLS, 74012, 74030, 12, "Dragon longsword", "Tier 5", 250, 16248, 16249, D_LONG_KILLS_TIER_UNLOCKED),

    MAGIC_SHORTBOW_TIER_ONE(MSB_KILLS, 74013, 74030, 18, "Magic shortbow", "Tier 1", 35, ItemIdentifiers.MAGIC_SHORTBOW, 16218, MSB_KILLS_TIER_UNLOCKED),
    MAGIC_SHORTBOW_TIER_TWO(MSB_KILLS, 74013, 74030, 18, "Magic shortbow", "Tier 2", 70, 16218, 16219, MSB_KILLS_TIER_UNLOCKED),
    MAGIC_SHORTBOW_TIER_THREE(MSB_KILLS, 74013, 74030, 18, "Magic shortbow", "Tier 3", 105, 16219, 16220, MSB_KILLS_TIER_UNLOCKED),
    MAGIC_SHORTBOW_TIER_FOUR(MSB_KILLS, 74013, 74030, 18, "Magic shortbow", "Tier 4", 140, 16220, 16221, MSB_KILLS_TIER_UNLOCKED),
    MAGIC_SHORTBOW_TIER_FIVE(MSB_KILLS, 74013, 74030, 18, "Magic shortbow", "Tier 5", 175, 16221, 16222, MSB_KILLS_TIER_UNLOCKED),

    RUNE_CROSSBOW_TIER_ONE(RUNE_C_BOW_KILLS, 74014, 74030, 24, "Rune c'bow", "Tier 1", 35, ItemIdentifiers.RUNE_CROSSBOW, 16227, RUNE_C_BOW_KILLS_TIER_UNLOCKED),
    RUNE_CROSSBOW_TIER_TWO(RUNE_C_BOW_KILLS, 74014, 74030, 24, "Rune c'bow", "Tier 2", 70, 16227, 16228, RUNE_C_BOW_KILLS_TIER_UNLOCKED),
    RUNE_CROSSBOW_TIER_THREE(RUNE_C_BOW_KILLS, 74014, 74030, 24, "Rune c'bow", "Tier 3", 105, 16228, 16229, RUNE_C_BOW_KILLS_TIER_UNLOCKED),
    RUNE_CROSSBOW_TIER_FOUR(RUNE_C_BOW_KILLS, 74014, 74030, 24, "Rune c'bow", "Tier 4", 140, 16229, 16230, RUNE_C_BOW_KILLS_TIER_UNLOCKED),
    RUNE_CROSSBOW_TIER_FIVE(RUNE_C_BOW_KILLS, 74014, 74030, 24, "Rune c'bow", "Tier 5", 175, 16230, 16231, RUNE_C_BOW_KILLS_TIER_UNLOCKED);

    public final AttributeKey upgradeKey;
    public final int upgradeTextId;
    public final int containerId;
    public final int containerSlot;
    public final String name;
    public final String tier;
    public final int requiredKillsToUpgrade;
    public final int itemToUpgrade;
    public final int upgradeItemId;
    public final AttributeKey tierKey;

    Upgrades(AttributeKey upgradeKey, int upgradeTextId, int containerId, int containerSlot, String name, String tier, int requiredKillsToUpgrade, int itemToUpgrade, int upgradeItemId, AttributeKey tierKey) {
        this.upgradeKey = upgradeKey;
        this.upgradeTextId = upgradeTextId;
        this.containerId = containerId;
        this.containerSlot = containerSlot;
        this.name = name;
        this.tier = tier;
        this.requiredKillsToUpgrade = requiredKillsToUpgrade;
        this.itemToUpgrade = itemToUpgrade;
        this.upgradeItemId = upgradeItemId;
        this.tierKey = tierKey;
    }

    public static List<Upgrades> asList(int kills) {
        return Arrays.stream(Upgrades.values()).filter(u -> u.requiredKillsToUpgrade == kills).sorted(Comparator.comparing(Enum::name)).collect(Collectors.toList());
    }

    public int getTier() {
        return switch (name().substring(name().indexOf("TIER_") + 5).trim()) {
            case "ONE" -> 1;
            case "TWO" -> 2;
            case "THREE" -> 3;
            case "FOUR" -> 4;
            case "FIVE" -> 5;
            default -> throw new IllegalStateException("Unexpected value: " + name().substring(name().indexOf("TIER_") + 5).trim());
        };
    }
}
