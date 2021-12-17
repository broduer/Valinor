package com.valinor.game.world.entity.combat.magic.lunar;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.util.chainedwork.Chain;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 17, 2021
 */
public class BakePie {

    private enum Pie {

        REDBERRY_PIE(10, 78.0, 2321, 2325),
        PIE_MEAT(20, 104.0, 2317, 2327),
        MUD_PIE(29, 128.0, 2319, 7170),
        APPLE_PIE(30, 130.0, 7168, 2323),
        GARDEN_PIE(34, 128.0, 7186, 7188),
        FISH_PIE(47, 164.0, 7186, 7188),
        ADMIRAL_PIE(70, 210.0, 7196, 7198),
        WILD_PIE(85, 240.0, 7206, 7208),
        SUMMER_PIE(95, 260.0, 7216, 7218);

        private final int levelReq, raw, baked;
        private final double exp;

        Pie(int levelReq, double exp, int raw, int baked) {
            this.levelReq = levelReq;
            this.exp = exp;
            this.raw = raw;
            this.baked = baked;
        }
    }

    public static void bake(Player player) {
        if (player != null) {
            Chain.bound(player).repeatingTask(3, r -> {
                int count = 0;
                for (Item item : player.getInventory().getItems()) {
                    for (Pie pie : Pie.values()) {
                        if (item != null && item.getId() == pie.raw) {
                            if (player.skills().level(Skills.COOKING) < pie.levelReq) {
                                continue;
                            }
                            player.inventory().remove(item);
                            player.inventory().add(new Item(pie.baked));
                            player.animate(4413);
                            player.graphic(746, 96, 0);
                            player.sound(2879);
                            player.skills().addXp(Skills.COOKING, pie.exp, true);
                            player.skills().addXp(Skills.MAGIC, 60, true);
                            count++;
                        }
                    }
                }
                if (count == 0) {
                    player.message("You don't have any pies to bake.");
                    r.stop();
                }
            });
        }
    }

}
