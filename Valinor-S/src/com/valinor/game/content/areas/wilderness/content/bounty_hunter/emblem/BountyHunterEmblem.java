package com.valinor.game.content.areas.wilderness.content.bounty_hunter.emblem;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.ItemIdentifiers;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Patrick van Elderen | December, 07, 2020, 10:14
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public enum BountyHunterEmblem {

    ANTIQUE_EMBLEM_TIER_1(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_1,500,0),
    ANTIQUE_EMBLEM_TIER_2(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_2,750,1),
    ANTIQUE_EMBLEM_TIER_3(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_3,1000,2),
    ANTIQUE_EMBLEM_TIER_4(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_4,1500,3),
    ANTIQUE_EMBLEM_TIER_5(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_5,2000,4),
    ANTIQUE_EMBLEM_TIER_6(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_6,2750,5),
    ANTIQUE_EMBLEM_TIER_7(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_7,3500,6),
    ANTIQUE_EMBLEM_TIER_8(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_8,4500,7),
    ANTIQUE_EMBLEM_TIER_9(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_9,6000,8),
    ANTIQUE_EMBLEM_TIER_10(ItemIdentifiers.ANTIQUE_EMBLEM_TIER_10,8000,9);

    private final int itemId;
    private final int pkPoints;
    private final int index;

    BountyHunterEmblem(int itemId, int pkPoints, int index) {
        this.itemId = itemId;
        this.pkPoints = pkPoints;
        this.index = index;
    }

    public int getItemId() {
        return itemId;
    }

    public int getPkPoints() {
        return pkPoints;
    }

    public int getIndex() {
        return index;
    }

    public static Optional<BountyHunterEmblem> forId(int id) {
        return Arrays.stream(values()).filter(a -> a.itemId == id).findAny();
    }

    public BountyHunterEmblem getNextOrLast() {
        int increaseBy = 1;
        return valueOf(index + increaseBy).orElse(ANTIQUE_EMBLEM_TIER_10);
    }

    public BountyHunterEmblem getPreviousOrFirst() {
        return valueOf(index - 1).orElse(ANTIQUE_EMBLEM_TIER_1);
    }

    public static final Set<BountyHunterEmblem> EMBLEMS = Collections.unmodifiableSet(EnumSet.allOf(BountyHunterEmblem.class));

    public static Optional<BountyHunterEmblem> valueOf(int index) {
        return EMBLEMS.stream().filter(emblem -> emblem.index == index).findFirst();
    }

    static final Comparator<BountyHunterEmblem> BEST_EMBLEM_COMPARATOR = Comparator.comparingInt(bountyHunterEmblem -> bountyHunterEmblem.itemId);

    public static Optional<BountyHunterEmblem> getBest(Player player, boolean exclude) {
        List<BountyHunterEmblem> emblems = EMBLEMS.stream().filter(exclude(player, exclude)).collect(Collectors.toList());

        if (emblems.isEmpty()) {
            return Optional.empty();
        }

        return emblems.stream().max(BEST_EMBLEM_COMPARATOR);
    }

    private static Predicate<BountyHunterEmblem> exclude(Player player, boolean exclude) {
        return emblem -> player.inventory().contains(new Item(emblem.getItemId())) && (!exclude || exclude && !emblem.equals(ANTIQUE_EMBLEM_TIER_10));
    }
}
