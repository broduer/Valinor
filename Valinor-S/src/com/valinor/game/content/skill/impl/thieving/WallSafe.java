package com.valinor.game.content.skill.impl.thieving;

import com.valinor.game.content.items.ItemSet;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.loot.LootItem;
import com.valinor.game.world.items.loot.LootTable;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.COINS_995;
import static com.valinor.util.ObjectIdentifiers.WALL_SAFE;

/**
 * @author Patrick van Elderen | March, 26, 2021, 10:56
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class WallSafe extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == WALL_SAFE) {
                attempt(player, object);
                return true;
            }
        }
        return false;
    }

    private static final LootTable table = new LootTable().addTable(1,
        new LootItem(COINS_995, 5000, 20000, 295),
        new LootItem(1623, 1, 100),     //sapphire 1/5
        new LootItem(1621, 1, 50),      //emerald 1/10
        new LootItem(1619, 1, 33),      //ruby 1/15
        new LootItem(1617, 1, 16),      //diamond 1/30
        new LootItem(1631, 1, 5),       //dragonstone 1/100
        new LootItem(6571, 1, 1)        //onyx 1/500
    );

    private void attempt(Player player, GameObject wallSafe) {
        if (!player.skills().check(Skills.THIEVING, 50, "attempt this"))
            return;
        if (player.inventory().isFull()) {
            player.message("You don't have enough inventory space to do that.");
            return;
        }
        Chain.bound(player).runFn(1, () -> {
            player.sound(1243);
            player.message("You start cracking the safe.");
            player.animate(2247);
        }).then(2, () -> {
            double chance = 0.5 + (double) (player.skills().level(Skills.THIEVING) - 50) * 0.01;
            if (Utils.get() > Math.min(chance, 0.85)) {
                player.lock();
                player.sound(1242);
                player.message("You slip and trigger a trap!");
                player.hit(player, World.getWorld().random(1, 6));
                player.animate(1113);
                Chain.bound(player).runFn(2, () -> {
                    player.resetAnimation();
                    player.unlock();
                });
            } else {
                player.sound(1243);
                player.animate(2248);
                Chain.bound(player).runFn(2, () -> {
                    player.message("You get some loot.");
                    player.sound(1238);
                    player.skills().addXp(Skills.THIEVING,70,true);
                    Item loot = getLoot(player);
                    if(ItemSet.wearingRogueOutfit(player))
                        loot.setAmount(loot.getAmount() * 2);
                    player.inventory().add(loot);
                    openSafe(wallSafe);
                });
            }
        });
    }

    private void openSafe(GameObject wallSafe) {
        ObjectManager.replace(wallSafe, new GameObject(7238, wallSafe.tile()),3);
    }

    private Item getLoot(Player player) {
        Item item = table.rollItem();
        if (item.getId() == COINS_995) {
            item.setAmount((int) (item.getAmount() * ((1 + (player.skills().xpLevel(Skills.THIEVING) - 49) * 0.02))));
        }
        return item;
    }
}
