package com.valinor.game.content.areas.wilderness.content.revenant_caves;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.CustomItemIdentifiers;

import java.util.ArrayList;

import static com.valinor.util.ItemIdentifiers.COINS_995;

/**
 * It can be given to the Emblem Trader wandering around in the Revenant Caves
 * in exchange for {@code rewardAmount} Coins. Much like the bracelet of
 * ethereum, the emblem is always lost on death, even if it is the only item in
 * the player's inventory.
 * 
 * @author Patrick van Elderen | Zerikoth (PVE) | 23 sep. 2019 : 14:33
 * @see <a href="https://github.com/Patrick9-10-1995">Github profile</a>
 * @version 1.0
 */
public enum AncientArtifacts {
    
    ANCIENT_EMBLEM(21807,5_000_000),
    ANCIENT_TOTEM(21810,8_000_000),
    ANCIENT_STATUETTE(21813,15_000_000),
    ANCIENT_MEDALLION(22299,25_000_000),
    ANCIENT_EFFIGY(22302,35_000_000),
    ANCIENT_RELIC(22305,50_000_000),
    DARK_ANCIENT_EMBLEM(CustomItemIdentifiers.DARK_ANCIENT_EMBLEM,10_000_000),
    DARK_ANCIENT_TOTEM(CustomItemIdentifiers.DARK_ANCIENT_TOTEM,16_000_000),
    DARK_ANCIENT_STATUETTE(CustomItemIdentifiers.DARK_ANCIENT_STATUETTE,30_000_000),
    DARK_ANCIENT_MEDALLION(CustomItemIdentifiers.DARK_ANCIENT_MEDALLION,50_000_000),
    DARK_ANCIENT_EFFIGY(CustomItemIdentifiers.DARK_ANCIENT_EFFIGY,70_000_000),
    DARK_ANCIENT_RELIC(CustomItemIdentifiers.DARK_ANCIENT_RELIC,100_000_000);
    
    /**
     * The emblem item
     */
    private final int itemId;
    
    /**
     * The amount of coins is being rewarded
     */
    private final int rewardAmount;
    
    /**
     * The {@code EmblemTrader} constructor
     * 
     * @param itemId       The emblem
     * @param rewardAmount the coins amount we receive
     */
    AncientArtifacts(int itemId, int rewardAmount) {
        this.itemId = itemId;
        this.rewardAmount = rewardAmount;
    }

    public int getItemId() {
        return itemId;
    }

    public int getRewardAmount() {
        return rewardAmount;
    }
    
    /**
     * Exchanges the emblems for coins
     * 
     * @param player The player trying to sell his emblems
     * @param sell   perform the sale don't just price check
     */
    public static int exchange(Player player, boolean sell) {
        ArrayList<AncientArtifacts> list = new ArrayList<>();
        
        for (AncientArtifacts emblem : AncientArtifacts.values()) {
            if (player.inventory().contains(emblem.getItemId())) {
                list.add(emblem);
            }
        }

        if (list.isEmpty()) {
            return 0;
        }

        int value = 0;

        for (AncientArtifacts emblem : list) {
            int amount = player.inventory().count(emblem.getItemId());
            if (amount > 0) {
                if (sell) {
                    if(!player.inventory().contains(emblem.getItemId())) {
                        return 0;
                    }
                    player.inventory().remove(emblem.getItemId(), amount);
                    int increase = emblem.getRewardAmount() * amount;
                    player.inventory().addOrDrop(new Item(COINS_995, increase));
                }
                value += (emblem.getRewardAmount() * amount);
            }
        }
        return value;
    }
    
}
