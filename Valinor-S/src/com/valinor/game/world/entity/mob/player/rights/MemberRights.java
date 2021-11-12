package com.valinor.game.world.entity.mob.player.rights;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import static com.valinor.util.CustomItemIdentifiers.*;

public enum MemberRights {

    NONE(0, "None", -1, -1, 0, Color.BLACK.tag(), 1.0, 1.0, 1.0),
    SAPHIRE_MEMBER(10, "Saphire Member", 1871, 5, 1, Color.ORANGE.tag(), 1.05, 1.025, 1.025),
    EMERALD_MEMBER(50, "Emerald Member", 1875, 6, 2, Color.BLUE.tag(), 1.07, 1.025, 1.025),
    RUBY_MEMBER(100, "Ruby Member", 1872, 22, 3, Color.PURPLE.tag(), 1.15, 1.025, 1.025),
    DIAMOND_MEMBER(250, "Diamond Member", 1874, 7, 4, Color.YELLOW.tag(), 1.20, 1.025, 1.025),
    DRAGONSTONE_MEMBER(750, "Dragonstone Member", 1876, 24, 5, Color.DRAGON.tag(), 1.025, 1.025, 1.025),
    ONYX_MEMBER(1500, "Onyx Member", 1877, 15, 6, Color.GOLDENROD.tag(), 1.025, 1.025, 1.025),
    ZENYTE_MEMBER(3000, "Zenyte Member", 1873, 23, 7, Color.RUNITE.tag(), 1.025, 1.025, 1.0255);

    private final double spent;

    private final String name;

    private final int spriteId;

    private final int right;

    /**
     * The value of the right. The higher the value, the more permissions the player has.
     */
    private final int rightValue;

    private final String yellNameColour;

    private final double[] modifiers;

    MemberRights(double spent, String name, int spriteId, int right, int rightValue, String yellNameColour, double... modifiers) {
        this.spent = spent;
        this.name = name;
        this.spriteId = spriteId;
        this.right = right;
        this.rightValue = rightValue;
        this.yellNameColour = yellNameColour;
        this.modifiers = modifiers;
    }

    public double required() {
        return spent;
    }

    public final String getName() {
        return name;
    }

    public final int getSpriteId() {
        return spriteId;
    }

    public final int getRight() {
        return right;
    }

    public int getRightValue() {
        return rightValue;
    }

    public String yellNameColour() {
        return yellNameColour;
    }

    public double[] modifiers() {
        return modifiers;
    }

    public String tag() {
        return "<img=" + spriteId + ">";
    }

    private double bloodMoneyMultiplier() {
        return modifiers[0];
    }

    private double slayerPointsMultiplier() {
        return modifiers[1];
    }

    public double petRateMultiplier() {
        return modifiers[2];
    }

    /**
     * Checks if the player has saphire member status or higher.
     */
    public boolean isSaphireMemberOrGreater(Player player) {
        return player.getMemberRights().getRightValue() >= SAPHIRE_MEMBER.getRightValue();
    }

    /**
     * Checks if the player has emerald member status or higher.
     */
    public boolean isEmeraldMemberOrGreater(Player player) {
        return player.getMemberRights().getRightValue() >= EMERALD_MEMBER.getRightValue();
    }

    /**
     * Checks if the player has ruby member status or higher.
     */
    public boolean isRubyMemberOrGreater(Player player) {
        return player.getMemberRights().getRightValue() >= RUBY_MEMBER.getRightValue();
    }

    /**
     * Checks if the player has diamond member status or higher.
     */
    public boolean isDiamondMemberOrGreater(Player player) {
        return player.getMemberRights().getRightValue() >= DIAMOND_MEMBER.getRightValue();
    }

    /**
     * Checks if the player has dragonstone member status or higher.
     */
    public boolean isDragonstoneMemberOrGreater(Player player) {
        return player.getMemberRights().getRightValue() >= DRAGONSTONE_MEMBER.getRightValue();
    }

    /**
     * Checks if the player has onyx member status or higher.
     */
    public boolean isOnyxMemberOrGreater(Player player) {
        return player.getMemberRights().getRightValue() >= ONYX_MEMBER.getRightValue();
    }

    /**
     * Checks if the player has zenyte member status or higher.
     */
    public boolean isZenyteMemberOrGreater(Player player) {
        return player.getMemberRights().getRightValue() >= ZENYTE_MEMBER.getRightValue();
    }

    public void update(Player player, boolean silent) {
        double totalAmountPaid = player.getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D);

        boolean memberUnlocked = player.getAttribOr(AttributeKey.MEMBER_UNLOCKED, false);
        if (totalAmountPaid >= 10.00 && !memberUnlocked) {
            player.setMemberRights(MemberRights.SAPHIRE_MEMBER);
            player.putAttrib(AttributeKey.MEMBER_UNLOCKED, true);
            player.inventory().addOrBank(new Item(WEAPON_MYSTERY_BOX));
            if (!silent)
                World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has been promoted to <col=" + Color.HOTPINK.getColorValue() + "><img=" + SAPHIRE_MEMBER.spriteId + ">Member</col>!");
        }

        boolean superMemberUnlocked = player.getAttribOr(AttributeKey.SUPER_MEMBER_UNLOCKED, false);
        if (totalAmountPaid >= 50.00 && !superMemberUnlocked) {
            player.setMemberRights(MemberRights.EMERALD_MEMBER);
            player.putAttrib(AttributeKey.SUPER_MEMBER_UNLOCKED, true);
            player.inventory().addOrBank(new Item(ARMOUR_MYSTERY_BOX));
            if (!silent)
                World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has been promoted to <col=" + Color.HOTPINK.getColorValue() + "><img=" + EMERALD_MEMBER.spriteId + ">Super Member</col>!");
        }

        boolean eliteMemberUnlocked = player.getAttribOr(AttributeKey.ELITE_MEMBER_UNLOCKED, false);
        if (totalAmountPaid >= 100.00 && !eliteMemberUnlocked) {
            player.setMemberRights(MemberRights.RUBY_MEMBER);
            player.inventory().addOrBank(new Item(DONATOR_MYSTERY_BOX));
            player.putAttrib(AttributeKey.ELITE_MEMBER_UNLOCKED, true);
            if (!silent)
                World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has been promoted to <col=" + Color.HOTPINK.getColorValue() + "><img=" + RUBY_MEMBER.spriteId + ">Elite Member</col>!");
        }

        boolean extremeMemberUnlocked = player.getAttribOr(AttributeKey.EXTREME_MEMBER_UNLOCKED, false);
        if (totalAmountPaid >= 500 && !extremeMemberUnlocked) {
            player.setMemberRights(MemberRights.DIAMOND_MEMBER);
            player.putAttrib(AttributeKey.EXTREME_MEMBER_UNLOCKED, true);
            player.inventory().addOrBank(new Item(LEGENDARY_MYSTERY_BOX));
            if (!silent)
                World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has been promoted to <col=" + Color.HOTPINK.getColorValue() + "><img=" + DIAMOND_MEMBER.spriteId + ">Extreme Member</col>!");
        }

        boolean legendaryMemberUnlocked = player.getAttribOr(AttributeKey.LEGENDARY_MEMBER_UNLOCKED, false);
        if (totalAmountPaid >= 1000 && !legendaryMemberUnlocked) {
            player.setMemberRights(MemberRights.DRAGONSTONE_MEMBER);
            player.putAttrib(AttributeKey.LEGENDARY_MEMBER_UNLOCKED, true);
            player.inventory().addOrBank(new Item(GRAND_MYSTERY_BOX));
            if (!silent)
                World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has been promoted to <col=" + Color.HOTPINK.getColorValue() + "><img=" + DRAGONSTONE_MEMBER.spriteId + ">Legendary Member</col>!");
        }

        boolean vipMemberUnlocked = player.getAttribOr(AttributeKey.VIP_UNLOCKED, false);
        if (totalAmountPaid >= 2500 && !vipMemberUnlocked) {
            player.setMemberRights(MemberRights.ONYX_MEMBER);
            player.putAttrib(AttributeKey.VIP_UNLOCKED, true);
            player.inventory().addOrBank(new Item(MYSTERY_TICKET, 5));
            if (!silent)
                World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has been promoted to <col=" + Color.HOTPINK.getColorValue() + "><img=" + ONYX_MEMBER.spriteId + ">V.I.P Member</col>!");
        }

        boolean sponsorMemberUnlocked = player.getAttribOr(AttributeKey.SPONSOR_UNLOCKED, false);
        if (totalAmountPaid >= 5000.00 && !sponsorMemberUnlocked) {
            player.setMemberRights(MemberRights.ZENYTE_MEMBER);
            player.putAttrib(AttributeKey.SPONSOR_UNLOCKED, true);
            player.inventory().addOrBank(new Item(MYSTERY_CHEST));
            if (!silent)
                World.getWorld().sendWorldMessage("<img=1081> " + player.getUsername() + " has been promoted to <col=" + Color.HOTPINK.getColorValue() + "><img=" + ZENYTE_MEMBER.spriteId + ">Sponsor Member</col>!");
        }

        if (totalAmountPaid >= 10.00) {
            player.getPacketSender().sendRights();
        }
    }

    private static final Set<MemberRights> RIGHTS = Collections.unmodifiableSet(EnumSet.allOf(MemberRights.class));

    public static MemberRights get(int value) {
        return RIGHTS.stream().filter(element -> element.rightValue == value).findFirst().orElse(NONE);
    }

    @Override
    public String toString() {
        return "MemberRights{" + "name='" + name + '\'' + '}';
    }
}
