package com.valinor.game.world.items.container.shop.currency;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.container.shop.currency.impl.*;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;
import com.google.common.collect.ImmutableSet;

/**
 * The enumerated type whom holds all the currencies usable for a server.
 *
 * @author <a href="http://www.rune-server.org/members/stand+up/">Stand Up</a>
 */
public enum CurrencyType {

    COINS(new ItemCurrency(ItemIdentifiers.COINS_995)),
    DONATOR_TICKETS(new ItemCurrency(CustomItemIdentifiers.DONATOR_TICKET)),
    MARK_OF_GRACE(new ItemCurrency(ItemIdentifiers.MARK_OF_GRACE)),
    VALINOR_COINS(new ItemCurrency(CustomItemIdentifiers.VALINOR_COINS)),
    HWEEN_TOKEN(new ItemCurrency(CustomItemIdentifiers.HWEEN_TOKENS)),
    STARDUST(new ItemCurrency(ItemIdentifiers.STARDUST)),
    SLAYER_REWARD_POINTS(new SlayerPointsCurrency()),
    VOTE_POINTS(new VotePointsCurrency()),
    BOSS_POINTS(new BossPointsCurrency()),
    ACHIEVEMENT_POINTS(new AchievementPointsCurrency()),
    PK_POINTS(new PkPointsCurrency()),
    BOUNTY_HUNTER_POINTS(new BountyHunterPointsCurrency()),

    ;

    private static final ImmutableSet<CurrencyType> VALUES = ImmutableSet.copyOf(values());

    public final Currency currency;

    CurrencyType(Currency currency) {
        this.currency = currency;
    }

    public static boolean isCurrency(int id) {
        return VALUES.stream().filter(i -> i.currency.tangible()).anyMatch(i -> ((ItemCurrency) i.currency).itemId == id);
    }

    public static String getValue(Player player, CurrencyType currency) {
        String value = "";
        switch (currency) {
            case DONATOR_TICKETS -> value = Utils.formatNumber(player.inventory().contains(CustomItemIdentifiers.DONATOR_TICKET) ? player.inventory().count(CustomItemIdentifiers.DONATOR_TICKET) : 0);
            case MARK_OF_GRACE -> value = Utils.formatNumber(player.inventory().contains(ItemIdentifiers.MARK_OF_GRACE) ? player.inventory().count(ItemIdentifiers.MARK_OF_GRACE) : 0);
            case VALINOR_COINS -> value = Utils.formatNumber(player.inventory().contains(CustomItemIdentifiers.VALINOR_COINS) ? player.inventory().count(CustomItemIdentifiers.VALINOR_COINS) : 0);
            case SLAYER_REWARD_POINTS -> {
                int slayerRewardPoints = player.getAttribOr(AttributeKey.SLAYER_REWARD_POINTS, 0);
                value = Utils.formatNumber(slayerRewardPoints);
            }
            case VOTE_POINTS -> {
                int votePoints = player.getAttribOr(AttributeKey.VOTE_POINTS, 0);
                value = Utils.formatNumber(votePoints);
            }
            case BOSS_POINTS -> {
                int bossPoints = player.getAttribOr(AttributeKey.BOSS_POINTS, 0);
                value = Utils.formatNumber(bossPoints);
            }
            case ACHIEVEMENT_POINTS -> {
                int achievementPoints = player.getAttribOr(AttributeKey.ACHIEVEMENT_POINTS, 0);
                value = Utils.formatNumber(achievementPoints);
            }
            case PK_POINTS -> {
                int pkPoints = player.getAttribOr(AttributeKey.PK_POINTS, 0);
                value = Utils.formatNumber(pkPoints);
            }
            case BOUNTY_HUNTER_POINTS -> {
                int bounties = player.getAttribOr(AttributeKey.BOUNTY_HUNTER_POINTS, 0);
                value = Utils.format(bounties);
            }
            default -> {
            }
        }
        return value.equals("0") ? "None!" : value;
    }

    @Override
    public String toString() {
        return name().toLowerCase().replace("_", " ");
    }
}
