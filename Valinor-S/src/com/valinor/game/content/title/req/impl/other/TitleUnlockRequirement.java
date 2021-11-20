package com.valinor.game.content.title.req.impl.other;

import com.valinor.game.content.title.req.TitleRequirement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Utils;

import java.util.EnumSet;
import java.util.Set;

import static com.valinor.util.ItemIdentifiers.COINS_995;

public class TitleUnlockRequirement extends TitleRequirement {

    public enum UnlockableTitle {
        MILLIONAIRE(10_000_000), BILLIONAIRE(10_000_000), KING(10_000_000), QUEEN(10_000_000), SIR(10_000_000), MR(10_000_000), MISS(10_000_000), MRS(10_000_000), THE_IDIOT(10_000_000), LAZY(10_000_000), NOOB(10_000_000), DRUNKEN(10_000_000), THE_MAGNIFICENT(10_000_000), THE_AWESOME(10_000_000), COWARDLY(10_000_000);

        public static final Set<UnlockableTitle> SET = EnumSet.allOf(UnlockableTitle.class);

        private final int cost;

        UnlockableTitle(int cost) {
            this.cost = cost;
        }

        public String getName() {
            String name = name().toLowerCase().replaceAll("_", " ");
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            return name;
        }
    }

    private final UnlockableTitle title;

    public TitleUnlockRequirement(UnlockableTitle title) {
        super("Costs " + Utils.formatNumber(title.cost) + " BM<br>to unlock");
        this.title = title;
    }

    @Override
    public boolean satisfies(Player player) {
        if (!player.getUnlockedTitles().contains(title)) {

            int bmInInventory = player.inventory().count(COINS_995);
            if (bmInInventory > 0) {
                if(bmInInventory >= title.cost) {
                    player.inventory().remove(COINS_995, title.cost);
                    player.getUnlockedTitles().add(title);
                }
            } else {
                player.message("You do not have enough Blood money to purchase this title.");
            }
        }
        return player.getUnlockedTitles().contains(title);
    }
}
