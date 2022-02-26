package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;

import static com.valinor.util.CustomItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 26, 2022
 */
public class GrandMysterybox extends Interaction {
    /**
     * This option gives 1 legendary mystery box and 1 donator mystery box.
     * There is a 75% chance that you get this option.
     */
    private static final Item[] OPTION_ONE = {new Item(LEGENDARY_MYSTERY_BOX), new Item(DONATOR_MYSTERY_BOX)};

    /**
     * This option gives 1 legendary mystery box and 1 pet mystery box.
     * There is a 50% chance that you get this option.
     */
    private static final Item[] OPTION_TWO = {new Item(LEGENDARY_MYSTERY_BOX), new Item(PETS_MYSTERY_BOX)};

    /**
     * This option doubles your grand mystery box
     * There is a slim chance that you get this option.
     */
    private static final Item[] OPTION_THREE = {new Item(GRAND_MYSTERY_BOX), new Item(GRAND_MYSTERY_BOX)};

    /**
     * The grand mystery box opens three mystery boxes
     */
    private void open(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.STATEMENT, "<col=FF0000>75% chance to get</col> 1 Legendary and 1 Donator box.", "<col=FF0000>50% chance to get</col> 1 Legendary and 1 Pets mystery box.", "<col=FF0000>25% chance to get</col> Double your Grand Mystery Box.");
                setPhase(1);
            }

            @Override
            protected void next() {
                if(isPhase(1)) {
                    send(DialogueType.OPTION, "Open the Grand Mystery Box?", "Yes.", "No.");
                    setPhase(2);
                }
            }

            @Override
            protected void select(int option) {
                if(isPhase(2)) {
                    if(option == 1) {
                        reward(player);
                    }
                    stop();
                }
            }
        });
    }

    private void reward(Player player) {
        if (!player.inventory().contains(GRAND_MYSTERY_BOX))
            return;

        int avail = player.inventory().getFreeSlots();

        if(avail >= 2) {
            int slot = player.getAttribOr(AttributeKey.ITEM_SLOT, -1);
            player.inventory().remove(new Item(GRAND_MYSTERY_BOX), slot, true);

            if(Utils.percentageChance(75)) {
                player.inventory().addAll(OPTION_ONE);
            } else if(Utils.percentageChance(50)) {
                player.inventory().addAll(OPTION_TWO);
            } else if(Utils.percentageChance(25)) {
                player.inventory().addAll(OPTION_THREE);
                String worldMessage = "<img=505>[<col=" + Color.MEDRED.getColorValue() + ">Grand Mystery Box</col>]:<col=1e44b3> " + player.getUsername() + ""+Color.HOTPINK.tag()+" just doubled his Grand Mystery Box" + Color.BLACK.tag() + "!";
                World.getWorld().sendWorldMessage(worldMessage);
            }
        } else {
            player.message("You need at least 2 free inventory slots.");
        }
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == GRAND_MYSTERY_BOX) {
                open(player);
                return true;
            }
        }
        return false;
    }
}
