package com.valinor.game.content.items.skillcape;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;

/**
 *
 * Created by Jason MacKeigan on July 4th, 2016 at 11:45AM
 *
 * An enumeration of skill capes where the first element of the
 * vararg is the untrimmed cape, and the second is the trimmed
 * cape.
 */
public enum CapeOfCompletion {

    ATTACK(9747, 9748),
    STRENGTH(9750, 9751),
    DEFENCE(9753, 9754),
    RANGED(9756, 9757),
    PRAYER(9759, 9760),
    MAGIC(9762, 9763),
    RUNECRAFT(9765, 9766),
    HITPOINT(9768, 9769),
    AGILITY(9771, 9772),
    HERBLORE(9774, 9775),
    THIEVE(9777, 9778),
    CRAFTING(9780, 9781),
    FLETCHING(9783, 9784),
    SLAYER(9786, 9787),
    CONSTRUCTION(9789, 9790),
    HUNTER(9948, 9949),
    MINING(9792, 9793),
    SMITHING(9795, 9796),
    FISHING(9798, 9799),
    COOKING(9801, 9802),
    FIREMAKE(9804, 9805),
    WOODCUTTING(9807, 9808),
    FARMING(9810, 9811);

    CapeOfCompletion(int untrimmed, int trimmed) {
        this.untrimmed = untrimmed;
        this.trimmed = trimmed;
    }

    private final int untrimmed;
    private final int trimmed;

    public boolean operating(Player player) {
        var equipment = player.getEquipment();

        return equipment.hasAt(EquipSlot.CAPE, untrimmed) || equipment.hasAt(EquipSlot.CAPE, trimmed);
    }

    public void boost(int skill, Player player) {
        if (player.skills().level(skill) < 99) {
            return;
        }
        if (player.skills().level(skill) > 99) {
            player.message("Your current level is above the possible boosted level, please wait until it drops.");
            return;
        }
        if (skill == 5) {
            if (System.currentTimeMillis() - player.<Long>getAttribOr(AttributeKey.PRAYERCAPE_BOOST, 0L) < 60000L) {
                player.message("You can only boost your Prayer once every minute.");
                return;
            }
            player.putAttrib(AttributeKey.PRAYERCAPE_BOOST, System.currentTimeMillis());
        }
        player.skills().setLevel(skill, 100);
        player.message("You have temporarily boosted your level to 100.");
    }

}
