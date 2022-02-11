package com.valinor.game.content.items.mystery;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 11, 2022
 */
public enum Points {

    VOTE_POINTS(AttributeKey.VOTE_POINTS, -1, 1, 3, 2, 6),
    PK_POINTS(AttributeKey.PK_POINTS, -1, 100, 500, 500, 1500),
    SLAYER_REWARD_POINTS(AttributeKey.SLAYER_REWARD_POINTS, -1, 5, 15, 15, 35),
    BOSS_POINTS(AttributeKey.BOSS_POINTS, -1, 15, 30, 30, 60),
    VALINOR_COINS(null, CustomItemIdentifiers.VALINOR_COINS, 25, 100, 100, 750),
    MARKS_OF_GRACE(null, ItemIdentifiers.MARK_OF_GRACE, 1, 3, 2, 6),
    DOUBLE_DROP_SCROLLS(null, CustomItemIdentifiers.DOUBLE_DROPS_SCROLL, 1, 5, 3, 10);

    public AttributeKey key;
    public int id;
    public int mysteryBoxMinAmount;
    public int mysteryBoxMaxAmount;
    public int mysteryChestMinAmount;
    public int mysteryChestMaxAmount;

    Points(AttributeKey key, int id, int mysteryBoxMinAmount, int mysteryBoxMaxAmount, int mysteryChestMinAmount, int mysteryChestMaxAmount) {
        this.key = key;
        this.id = id;
        this.mysteryBoxMinAmount = mysteryBoxMinAmount;
        this.mysteryBoxMaxAmount = mysteryBoxMaxAmount;
        this.mysteryChestMinAmount = mysteryChestMinAmount;
        this.mysteryChestMaxAmount = mysteryChestMaxAmount;
    }

}
