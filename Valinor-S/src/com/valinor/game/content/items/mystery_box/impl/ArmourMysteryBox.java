package com.valinor.game.content.items.mystery_box.impl;

import com.valinor.game.content.items.mystery_box.MboxItem;
import com.valinor.game.content.items.mystery_box.MysteryBox;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import static com.valinor.util.ItemIdentifiers.*;

public class ArmourMysteryBox extends MysteryBox {

    public static final int ARMOUR_MYSTERY_BOX = 16452;

    @Override
    protected String name() {
        return "Armour mystery box";
    }

    @Override
    public int mysteryBoxId() {
        return ARMOUR_MYSTERY_BOX;
    }

    private static final int EXTREME_ROLL = 28;
    private static final int RARE_ROLL = 14;
    private static final int UNCOMMON_ROLL = 7;

    private static final MboxItem[] EXTREMELY_RARE = new MboxItem[]{
        new MboxItem(ARMADYL_CHESTPLATE).broadcastWorldMessage(true),
        new MboxItem(ARMADYL_CHAINSKIRT).broadcastWorldMessage(true),
        new MboxItem(BANDOS_CHESTPLATE).broadcastWorldMessage(true),
        new MboxItem(BANDOS_TASSETS).broadcastWorldMessage(true)
    };

    private static final MboxItem[] RARE = new MboxItem[]{
        new MboxItem(GUARDIAN_BOOTS).broadcastWorldMessage(true),
        new MboxItem(TWISTED_BUCKLER).broadcastWorldMessage(true)
    };

    private static final MboxItem[] UNCOMMON = new MboxItem[]{
        new MboxItem(ARMADYL_HELMET),
        new MboxItem(DAGONHAI_HAT),
        new MboxItem(DAGONHAI_ROBE_TOP),
        new MboxItem(DAGONHAI_ROBE_BOTTOM),
        new MboxItem(DHAROKS_ARMOUR_SET),
        new MboxItem(AHRIMS_ARMOUR_SET),
        new MboxItem(KARILS_ARMOUR_SET),
        new MboxItem(DRAGON_FULL_HELM),
        new MboxItem(DRAGON_PLATEBODY),
        new MboxItem(GUTHANS_ARMOUR_SET),
        new MboxItem(VERACS_ARMOUR_SET),
        new MboxItem(TORAGS_ARMOUR_SET),
        new MboxItem(KARILS_ARMOUR_SET),
        new MboxItem(AHRIMS_ARMOUR_SET),
        new MboxItem(DHAROKS_ARMOUR_SET),
    };

    private static final MboxItem[] COMMON = new MboxItem[]{
        new MboxItem(DRAGON_DEFENDER),
        new MboxItem(FIGHTER_TORSO),
        new MboxItem(FIRE_CAPE),
        new MboxItem(BLESSED_SPIRIT_SHIELD),
        new MboxItem(ROBIN_HOOD_HAT),
        new MboxItem(DRAGON_BOOTS),
        new MboxItem(RANGER_BOOTS),
        new MboxItem(INFINITY_BOOTS),
        new MboxItem(OBSIDIAN_HELMET),
        new MboxItem(OBSIDIAN_PLATEBODY),
        new MboxItem(OBSIDIAN_PLATELEGS),
        new MboxItem(MAGES_BOOK),
        new MboxItem(VERACS_ARMOUR_SET),
        new MboxItem(TORAGS_ARMOUR_SET),
        new MboxItem(GUTHANS_ARMOUR_SET),
    };

    private MboxItem[] allRewardsCached;

    public MboxItem[] allPossibleRewards() {
        if (allRewardsCached == null) {
            ArrayList<MboxItem> mboxItems = new ArrayList<>();
            mboxItems.addAll(Arrays.asList(EXTREMELY_RARE));
            mboxItems.addAll(Arrays.asList(RARE));
            mboxItems.addAll(Arrays.asList(UNCOMMON));
            mboxItems.addAll(Arrays.asList(COMMON));
            allRewardsCached = mboxItems.toArray(new MboxItem[0]);
        }
        return allRewardsCached;
    }

    @Override
    public AttributeKey key() {
        return AttributeKey.ARMOUR_MYSTERY_BOXES_OPENED;
    }

    @Override
    public MboxItem rollReward() {
        if (Utils.rollDie(EXTREME_ROLL, 1)) {
            return Utils.randomElement(EXTREMELY_RARE);
        } else if (Utils.rollDie(RARE_ROLL, 1)) {
            return Utils.randomElement(RARE);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }
}

