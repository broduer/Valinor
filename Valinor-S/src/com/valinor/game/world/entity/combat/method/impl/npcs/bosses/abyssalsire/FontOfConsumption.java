package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.abyssalsire;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.route.RouteDirection;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.ABYSSAL_SIRE;
import static com.valinor.util.ObjectIdentifiers.THE_FONT_OF_CONSUMPTION;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 22, 2021
 */
public class FontOfConsumption extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if(option == 1) {
            if(object.getId() == 27057) {
                List<Item> pieces = player.inventory().collectOneOfEach(BLUDGEON_SPINE, BLUDGEON_CLAW, BLUDGEON_AXON);
                if (pieces == null) {
                    DialogueManager.sendStatement(player, "You'll need a bludgeon claw, axon and spine", "to have the Overseer create a weapon for you.");
                } else {
                    Chain.bound(null).runFn(1, () -> {
                        player.lock();
                        player.inventory().removeAll(pieces);
                        player.doubleItemBox("You hand over the components to the Overseer.",BLUDGEON_SPINE, BLUDGEON_CLAW);
                    }).then(2, () -> {
                        player.inventory().add(ABYSSAL_BLUDGEON, 1);
                        player.itemBox("The Overseer presents you with an Abyssal Bludgeon.", ABYSSAL_BLUDGEON);
                        player.unlock();
                    });
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if(item.getId() == UNSIRED && object.getId() == THE_FONT_OF_CONSUMPTION) {
            Chain.bound(null).runFn(1, () -> {
                player.lock();
                player.face(RouteDirection.NORTH);
            }).then(1, () -> {
                player.itemBox("You place the Unsired into the Font of Consumption...", 13273);
                player.animate(827);
                World.getWorld().tileGraphic(1276, player.tile().relative(0, 1),0, 0);
            }).then(2, () -> {
                Item reward = new Item(roll(player));
                player.inventory().addOrDrop(reward);
                BOSSES.log(player, ABYSSAL_SIRE, reward);
                player.itemBox("The Font consumes the Unsired and returns you a<br>reward.", reward.getId());
                player.unlock();
            });
            return true;
        }
        return false;
    }

    private static int roll(Player player) {
        int roll = World.getWorld().get(127);
        if (roll < 5)
            return ABYSSAL_ORPHAN; // abyssal orphan (pet)
        if (roll < 15)
            return ABYSSAL_HEAD; // abyssal head
        if (roll < 41)
            return ABYSSAL_DAGGER; // abyssal dagger
        if (roll < 53)
            return ABYSSAL_WHIP; // whip
        if (roll < 66)
            return JAR_OF_MIASMA; // jar of miasma
        return getNextBludgeonPiece(player); // 66-127 -> bludgeon piece
    }

    private static int getNextBludgeonPiece(Player player) {
        int claws = 0, spines = 0, axons = 0;
        for (Item item : player.getBank().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == BLUDGEON_SPINE)
                spines += item.getAmount();
            else if (item.getId() == BLUDGEON_CLAW)
                claws += item.getAmount();
            else if (item.getId() == BLUDGEON_AXON)
                axons += item.getAmount();
        }
        for (Item item : player.getInventory().getItems()) {
            if (item == null)
                continue;
            if (item.getId() == BLUDGEON_SPINE)
                spines++;
            else if (item.getId() == BLUDGEON_CLAW)
                claws++;
            else if (item.getId() == BLUDGEON_AXON)
                axons++;
        }
        int lowest = Math.min(Math.min(claws, axons), spines);
        List<Integer> possible = new ArrayList<>();
        if (lowest == spines)
            possible.add(BLUDGEON_SPINE);
        if (lowest == claws)
            possible.add(BLUDGEON_CLAW);
        if (lowest == axons)
            possible.add(BLUDGEON_AXON);
        return World.getWorld().get(possible);
    }
}
