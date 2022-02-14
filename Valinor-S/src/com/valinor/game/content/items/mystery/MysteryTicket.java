package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.collection_logs.LogType.MYSTERY_BOX;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

public class MysteryTicket extends Interaction {

    //1/20
    private final List<Item> RARE_REWARDS = Arrays.asList(
        new Item(BANDOS_TASSETS), new Item(BANDOS_CHESTPLATE), new Item(ARMADYL_CHESTPLATE), new Item(ARMADYL_CHAINSKIRT), new Item(SARADOMIN_GODSWORD),
        new Item(ARMADYL_GODSWORD), new Item(DRAGON_WARHAMMER), new Item(DRAGON_HUNTER_LANCE), new Item(SPECTRAL_SPIRIT_SHIELD));

    private final List<Item> COMMON_REWARDS = Arrays.asList(
        new Item(BRIMSTONE_RING), new Item(DRAGONFIRE_SHIELD), new Item(ABYSSAL_DAGGER_P_13271), new Item(SARADOMIN_SWORD), new Item(STAFF_OF_THE_DEAD),
        new Item(BLESSED_SPIRIT_SHIELD), new Item(ARMADYL_CROSSBOW), new Item(ARMADYL_HELMET), new Item(BANDOS_GODSWORD), new Item(ZAMORAK_GODSWORD));

    private Item rewardRoll() {
        if (Utils.rollDie(20, 1)) {
            return Utils.randomElement(RARE_REWARDS);
        } else {
            return Utils.randomElement(COMMON_REWARDS);
        }
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == MYSTERY_TICKET) {
                String worldMessage = "";
                player.inventory().remove(new Item(MYSTERY_TICKET));
                var opened = player.<Integer>getAttribOr(AttributeKey.MYSTERY_TICKETS_OPENED, 0) + 1;
                player.putAttrib(AttributeKey.MYSTERY_TICKETS_OPENED, opened);
                if(World.getWorld().rollDie(75,1)) {
                    player.inventory().addOrBank(new Item(MYSTERY_CHEST));
                    MYSTERY_BOX.log(player, MYSTERY_TICKET, new Item(MYSTERY_CHEST));
                    Utils.sendDiscordInfoLog("Player " + player.getUsername() + " received a Mystery chest from a mystery ticket.", "tickets_opened");
                    worldMessage = "<img=452><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Mystery ticket</col>]</shad>:<col=AD800F> " + player.getUsername() + " received a <shad=0>Mystery chest</shad>!";
                } else if(World.getWorld().rollDie(10,1)) {
                    Item reward = rewardRoll();
                    MYSTERY_BOX.log(player, MYSTERY_TICKET, reward);
                    player.inventory().addOrBank(reward);
                    Utils.sendDiscordInfoLog("Player " + player.getUsername() + " received a "+reward.name()+" from a mystery ticket.", "tickets_opened");
                    worldMessage = "<img=452><shad=0>[<col=" + Color.MEDRED.getColorValue() + ">Mystery ticket</col>]</shad>:<col=AD800F> " + player.getUsername() + " received a <shad=0>" + reward.name() + "</shad>!";
                } else {
                    Utils.sendDiscordInfoLog("Player " + player.getUsername() + " received a Donator mystery box from a mystery ticket.", "tickets_opened");
                    player.inventory().addOrBank(new Item(DONATOR_MYSTERY_BOX));
                    MYSTERY_BOX.log(player, MYSTERY_TICKET, new Item(DONATOR_MYSTERY_BOX));
                }
                if(!worldMessage.isEmpty() && !player.getUsername().equalsIgnoreCase("Box test")) {
                    World.getWorld().sendWorldMessage(worldMessage);
                }
                return true;
            }
        }
        return false;
    }
}
