package com.valinor.game.world.entity.combat.magic.lunar;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;

import java.util.List;

import static com.valinor.util.ItemIdentifiers.BUCKET_OF_SAND;
import static com.valinor.util.ItemIdentifiers.MOLTEN_GLASS;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 17, 2021
 */
public class SuperglassMake {

    public static void makeGlass(Player player) {
        List<Item> items = player.inventory().collectItems(BUCKET_OF_SAND);
        if (items == null || items.size() == 0) {
            player.message("You don't have any sand to turn into glass.");
            return;
        }
        player.runFn(1, () -> {
            player.lock();
            player.animate(4413);
            player.graphic(729, 96, 0);
            items.forEach(item -> {
                player.inventory().remove(BUCKET_OF_SAND);
                player.inventory().add(new Item(MOLTEN_GLASS));
            });
            player.skills().addXp(Skills.CRAFTING, 8 * items.size(), true);
        }).then(2, player::unlock);
    }
}
