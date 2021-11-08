package com.valinor.game.content.items.mystery_box.impl;

import com.valinor.game.content.items.mystery_box.MboxItem;
import com.valinor.game.content.items.mystery_box.MysteryBox;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.util.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import static com.valinor.util.ItemIdentifiers.*;

public class LegendaryMysteryBox extends MysteryBox {

    public static final int LEGENDARY_MYSTERY_BOX = 16454;

    @Override
    protected String name() {
        return "Legendary mystery box";
    }

    @Override
    public int mysteryBoxId() {
        return LEGENDARY_MYSTERY_BOX;
    }

    private static final int EXTREME_RARE_ROLL = 15;
    private static final int RARE_ROLL = 10;
    private static final int UNCOMMON_ROLL = 5;

    private static final MboxItem[] EXTREMELY_RARE = new MboxItem[]{
        new MboxItem(ARCANE_SPIRIT_SHIELD).broadcastWorldMessage(true),
        new MboxItem(AVERNIC_DEFENDER).broadcastWorldMessage(true),
        new MboxItem(INFERNAL_CAPE).broadcastWorldMessage(true),
        new MboxItem(FEROCIOUS_GLOVES).broadcastWorldMessage(true),
        new MboxItem(VESTAS_CHAINBODY).broadcastWorldMessage(true),
        new MboxItem(VESTAS_PLATESKIRT).broadcastWorldMessage(true),
        new MboxItem(VESTAS_LONGSWORD).broadcastWorldMessage(true),
        new MboxItem(STATIUSS_WARHAMMER).broadcastWorldMessage(true)
    };

    private static final MboxItem[] RARE = new MboxItem[]{
        new MboxItem(MORRIGANS_LEATHER_BODY),
        new MboxItem(MORRIGANS_LEATHER_CHAPS),
        new MboxItem(ZURIELS_ROBE_TOP),
        new MboxItem(ZURIELS_ROBE_BOTTOM),
        new MboxItem(STATIUSS_PLATEBODY),
        new MboxItem(STATIUSS_PLATELEGS),
        new MboxItem(STATIUSS_FULL_HELM)
    };

    private static final MboxItem[] UNCOMMON = new MboxItem[]{
        new MboxItem(MORRIGANS_COIF),
        new MboxItem(ZURIELS_HOOD),
        new MboxItem(ZURIELS_STAFF),
        new MboxItem(BLADE_OF_SAELDOR),
        new MboxItem(INQUISITORS_MACE),
        new MboxItem(INQUISITORS_GREAT_HELM),
        new MboxItem(INQUISITORS_HAUBERK),
        new MboxItem(INQUISITORS_PLATESKIRT),
        new MboxItem(AMULET_OF_TORTURE),
        new MboxItem(NECKLACE_OF_ANGUISH),
        new MboxItem(TORMENTED_BRACELET),
        new MboxItem(RING_OF_SUFFERING)
    };

    private static final MboxItem[] COMMON = new MboxItem[]{
        new MboxItem(ARMADYL_GODSWORD),
        new MboxItem(DRAGON_CLAWS),
        new MboxItem(HEAVY_BALLISTA),
        new MboxItem(SARADOMINS_BLESSED_SWORD),
        new MboxItem(SPECTRAL_SPIRIT_SHIELD),
        new MboxItem(DRAGON_WARHAMMER),
        new MboxItem(TOXIC_STAFF_OF_THE_DEAD),
        new MboxItem(TOXIC_BLOWPIPE),
        new MboxItem(DRAGONFIRE_SHIELD),
        new MboxItem(STAFF_OF_LIGHT)
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
        return AttributeKey.LEGENDARY_MYSTERY_BOXES_OPENED;
    }

    @Override
    public MboxItem rollReward() {
        if (Utils.rollDie(EXTREME_RARE_ROLL, 1)) {
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
