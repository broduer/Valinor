package com.valinor.game.content;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.items.tools.ItemPacks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.COINS_995;
import static com.valinor.util.ItemIdentifiers.PLATINUM_TOKEN;
import static com.valinor.util.ObjectIdentifiers.OPEN_CHEST_3194;

/**
 * @author PVE
 * @Since juli 19, 2020
 */
public class BankObjects extends Interaction {

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject obj) {
        final String name = obj.definition().name;
        final boolean bank = obj.getId() == OPEN_CHEST_3194 || name.toLowerCase().contains("bank booth") || name.toLowerCase().contains("bank chest") || name.toLowerCase().contains("grand exchange booth");
        if (bank) {
            int itemid = player.getAttribOr(AttributeKey.ITEM_ID, -1);
            int slot = player.getAttribOr(AttributeKey.ITEM_SLOT, -1);
            ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, itemid);
            if (def == null) return false;

            noteLogic(player, itemid, slot, def);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemOnNpc(Player player, Item item, Npc npc) {
        final boolean banker = npc.def().name.equalsIgnoreCase("Banker") || npc.def().name.equalsIgnoreCase("Ashuelot Reis");
        if (banker) {
            int itemid = player.getAttribOr(AttributeKey.ITEM_ID, -1);
            int slot = player.getAttribOr(AttributeKey.ITEM_SLOT, -1);
            ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, itemid);
            if (def == null) return false;

            noteLogic(player, itemid, slot, def);
            return true;
        }
        return false;
    }

    public static void noteLogic(Player player, int itemid, int slot, ItemDefinition def) {
        // Coins!
        if (def.id == COINS_995) {
            var coins = def.id;
            var tokens = PLATINUM_TOKEN;

            var name = "coins";
            var tokensName = "platinum";

            if (player.inventory().count(coins) < 1000) {
                player.messageBox("You need at least 1,000 " + name + " to exchange them for " + tokensName + " tokens.");
                return;
            }

            player.optionsTitled("Exchange your " + name + " for " + tokensName + " tokens?", "Yes", "No", () -> {
                var coinCount = player.inventory().count(coins);
                var amount = coinCount / 1000;

                if (player.inventory().getFreeSlots() == 0 && coinCount - (amount * 1000) > 0) {
                    player.message("You don't have enough room to carry the " + tokensName + " tokens.");
                    return;
                }
                if (amount >= 1) {
                    if (player.inventory().remove(new Item(itemid, amount * 1000), true)) {
                        player.inventory().add(new Item(tokens, amount), true);
                        player.doubleItemBox("The bank exchanges your coins for " + tokensName + " tokens.", new Item(itemid, amount * 1000), new Item(tokens, amount));
                    }
                }
            });
            // Plat tokens
        } else if (def.id == PLATINUM_TOKEN) {
            var tokens = def.id;
            var coins = COINS_995;

            var name = "coins";
            var tokensName = "platinum";

            player.optionsTitled("Exchange your " + tokensName + " tokens for " + name + "?", "Yes", "No", () -> {
                var amount = player.inventory().count(tokens);

                if (((long) amount * 1000L) + (long) player.inventory().count(coins) >= Integer.MAX_VALUE) {
                    player.itemBox("The amount you're trying to exchange extends the limit<br>of 2147m.", coins, amount);
                    return;
                }

                if (amount >= 1) {
                    if (player.inventory().remove(new Item(tokens, amount), true)) {
                        player.inventory().add(new Item(coins, amount * 1000), true);
                        player.doubleItemBox("The bank exchanges your " + tokensName + " tokens for " + name + ".", new Item(coins, amount * 1000), new Item(tokens, amount));
                    }
                }
            });
            // Other mechanics.. firstly, check sets.
        } else if (!openSet(player, itemid, slot)) {

            // If open sets was NOT triggered, continue checking other possible logic:
            // It's linked to a noted item, but isn't a note itself
            if (!offerToNoteExchange(player, itemid)) {
                // If the above note logic was not triggered, check _other_ logic:
                if (def.noteModel > 0) { // If the item is a note
                    player.getDialogueManager().start(new Dialogue() {
                        @Override
                        protected void start(Object... parameters) {
                            send(DialogueType.OPTION, "Un-note the bank notes?", "Yes", "No");
                            setPhase(0);
                        }

                        @Override
                        protected void next() {
                            if (isPhase(1)) {
                                stop();
                            }
                        }

                        @Override
                        protected void select(int option) {
                            if (isPhase(0)) {
                                if (option == 1) {
                                    int avail = player.inventory().getFreeSlots();
                                    int count = player.inventory().count(itemid);
                                    boolean delete_entire_note_pile = avail >= count - 1;
                                    player.debugMessage(String.format("%d, %d, %s %d", avail, count, delete_entire_note_pile, itemid));
                                    if (delete_entire_note_pile) {
                                        player.inventory().remove(new Item(itemid, count), true);
                                        player.inventory().add(new Item(itemid, count).unnote(), true);
                                    } else { // Only delete what we can fit in
                                        boolean deleted = player.inventory().remove(new Item(itemid, avail), true);
                                        boolean added = player.inventory().add(new Item(itemid, avail).unnote(), true);
                                        player.debugMessage(String.format("%s, %s", deleted, added));
                                    }
                                    send(DialogueType.ITEM_STATEMENT, new Item(def.id), "", "The bank exchanges your banknotes for items.");
                                    setPhase(1);
                                } else if (option == 2) {
                                    stop();
                                }
                            }
                        }
                    });
                } else {
                    // No more possible logic. Nothing to do!
                    player.message("Nothing interesting happens.");
                }
            }
        }
    }

    private static boolean openSet(Player player, int itemid, int slot) {
        for (ItemPacks.ItemSets data : ItemPacks.ItemSets.values()) {
            if (data.getSetId() == itemid) {
                if (data.getItems().length - 1 > player.inventory().getFreeSlots()) {
                    // -1 cos account for the set itself
                    player.message("You don't have enough free space to open this set.");
                } else {
                    player.inventory().remove(player.inventory().get(slot), true);
                    player.inventory().addAll(data.getItems());
                    player.message("You open the set.");
                }
                return true;
            }
        }
        return false;
    }

    private static boolean offerToNoteExchange(Player player, int itemId) {
        ItemDefinition def = World.getWorld().definitions().get(ItemDefinition.class, itemId);
        if (def == null) return false;

        if (def.notelink > 0 && def.noteModel < 1) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.OPTION, "Note the un-noted items?", "Yes", "No");
                    setPhase(0);
                }

                @Override
                protected void next() {
                    if (isPhase(1)) {
                        stop();
                    }
                }

                @Override
                protected void select(int option) {
                    if (isPhase(0)) {
                        if (option == 1) {
                            int count = player.inventory().count(itemId);
                            if (player.inventory().remove(new Item(itemId, count), true)) {
                                player.inventory().add(new Item(def.notelink, count), true);
                                send(DialogueType.ITEM_STATEMENT, new Item(itemId), "", "You've exchanged " + count + " items for notes.");
                                setPhase(1);
                            }
                        } else if (option == 2) {
                            stop();
                        }
                    }
                }
            });
            return true;
        }
        return false;
    }
}
