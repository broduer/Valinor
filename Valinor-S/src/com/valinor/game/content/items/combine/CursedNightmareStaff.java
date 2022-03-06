package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.util.CustomItemIdentifiers.CURSED_NIGHTMARE_STAFF;
import static com.valinor.util.CustomItemIdentifiers.CURSED_ORB;
import static com.valinor.util.ItemIdentifiers.NIGHTMARE_STAFF;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 06, 2022
 */
public class CursedNightmareStaff extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        if(player.inventory().containsAll(NIGHTMARE_STAFF, CURSED_ORB)) {
            boolean dontAskAgain = player.getAttribOr(AttributeKey.NIGHTMARE_STAFF_QUESTION, false);
            if(dontAskAgain) {
                player.inventory().remove(new Item(NIGHTMARE_STAFF, 1));
                player.inventory().remove(new Item(CURSED_ORB, 1));
                player.inventory().add(new Item(CURSED_NIGHTMARE_STAFF, 1));
                send(DialogueType.ITEM_STATEMENT, new Item(CURSED_NIGHTMARE_STAFF), "", "You add the orb to the staff.");
                setPhase(1);
            } else {
                send(DialogueType.OPTION, "Add the orb to the staff?", "Yes.", "Yes, and don't ask again.", "No.");
                setPhase(0);
            }
        }
    }

    @Override
    protected void next() {
        if(getPhase() == 1) {
            stop();
        }
    }

    @Override
    protected void select(int option) {
        if(getPhase() == 0) {
            if(option == 1) {
                if(!player.inventory().containsAll(NIGHTMARE_STAFF, CURSED_ORB)) {
                    stop();
                    return;
                }
                player.inventory().remove(new Item(NIGHTMARE_STAFF, 1));
                player.inventory().remove(new Item(CURSED_ORB, 1));
                player.inventory().add(new Item(CURSED_NIGHTMARE_STAFF, 1));
                send(DialogueType.ITEM_STATEMENT, new Item(CURSED_NIGHTMARE_STAFF), "", "You add the orb to the staff.");
                setPhase(1);
            } else if(option == 2) {
                if(!player.inventory().contains(NIGHTMARE_STAFF, CURSED_ORB)) {
                    stop();
                    return;
                }
                player.inventory().remove(new Item(NIGHTMARE_STAFF, 1));
                player.inventory().remove(new Item(CURSED_ORB, 1));
                player.inventory().add(new Item(CURSED_NIGHTMARE_STAFF, 1));
                player.putAttrib(AttributeKey.NIGHTMARE_STAFF_QUESTION, true);
                send(DialogueType.ITEM_STATEMENT, new Item(CURSED_NIGHTMARE_STAFF), "", "You add the orb to the staff.");
                setPhase(1);
            } else if(option == 3) {
                stop();
            }
        }
    }

    public static boolean dismantle(Player player, Item item) {
        if(item.getId() != CURSED_NIGHTMARE_STAFF) {
            return false;
        }

        if(player.inventory().getFreeSlots() < 2) {
            player.message("You need at least 2 free inventory slots.");
            return false;
        }

        player.inventory().remove(new Item(CURSED_NIGHTMARE_STAFF, 1));
        player.inventory().add(new Item(CURSED_ORB, 1));
        player.inventory().add(new Item(NIGHTMARE_STAFF, 1));

        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.ITEM_STATEMENT, new Item(NIGHTMARE_STAFF), "", "You remove the orb from the staff.");
                setPhase(0);
            }

            @Override
            protected void next() {
                if(getPhase() == 0) {
                    stop();
                }
            }
        });
        return true;
    }
}
