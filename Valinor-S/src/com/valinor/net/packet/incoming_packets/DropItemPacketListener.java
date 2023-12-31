package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.packet_actions.interactions.items.ItemActionFour;
import com.valinor.game.world.InterfaceConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItem.State;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import com.valinor.net.packet.interaction.InteractionManager;
import com.valinor.util.Color;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.Utils;
import com.valinor.util.timers.TimerKey;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.SANGUINE_TWISTED_BOW;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * This packet listener is called when a player drops an item they
 * have placed in their inventory.
 * <p>
 * Redo packet as of : 2 augustus. 2019 : 15:05
 *
 * @author Patrick van Elderen | 2 augustus. 2019 : 15:05
 * @see <a href="https://github.com/Patrick9-10-1995">Github profile</a>
 */
public class DropItemPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        int id = packet.readUnsignedShortA();
        int interfaceId = packet.readUnsignedShort();
        int slot = packet.readUnsignedShortA();

        if (GameServer.properties().debugMode && player.getPlayerRights().isDeveloperOrGreater(player)) {
            player.debugMessage(String.format("drop item / item option 4 - Item: %d Interface: %o Slot: %o", id, interfaceId, slot));
        }

        boolean newAccount = player.<Boolean>getAttribOr(AttributeKey.NEW_ACCOUNT, false);

        if (newAccount) {
            player.message("You have to select your game mode before you can continue.");
            return;
        }

        //Check for bank pin
        if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            player.getBankPin().openIfNot();
            return;
        }

        if (player.askForAccountPin()) {
            player.sendAccountPinMessage();
            return;
        }

        if (player.getUsername().equalsIgnoreCase("Box test")) {
            player.message("This account can't drop items.");
            return;
        }

        if (player.busy()) {
            player.message("You cannot do this right now.");
            return;
        }

        if (player.getDueling().inDuel() || player.getDueling().endingDuel()) {
            player.message("You can't drop items in the duel arena.");
            return;
        }

        if (player.jailed()) {
            player.message("Items cannot be destroyed when jailed.");
            return;
        }

        if (interfaceId == InterfaceConstants.INVENTORY_INTERFACE) {
            if (!player.locked() && !player.dead()) {
                if (slot < 0 || slot > 27) return;
                Item item = player.inventory().get(slot);
                if (item != null && item.getId() == id) {
                    player.putAttrib(AttributeKey.ITEM_ID, item.getId());
                    player.putAttrib(AttributeKey.ITEM_SLOT, slot);
                    player.putAttrib(AttributeKey.FROM_ITEM, item);

                    player.afkTimer.reset();

                    player.stopActions(false);

                    List<Integer> charged_item_ids = Arrays.asList(TOXIC_BLOWPIPE, SERPENTINE_HELM, TRIDENT_OF_THE_SWAMP, TOXIC_STAFF_OF_THE_DEAD, TOME_OF_FIRE, SCYTHE_OF_VITUR, SANGUINESTI_STAFF, CRAWS_BOW, VIGGORAS_CHAINMACE, THAMMARONS_SCEPTRE, TRIDENT_OF_THE_SEAS, MAGMA_HELM, TANZANITE_HELM, ABYSSAL_TENTACLE,
                        ARMADYL_GODSWORD_OR,BANDOS_GODSWORD_OR,SARADOMIN_GODSWORD_OR,ZAMORAK_GODSWORD_OR,GRANITE_MAUL_12848,DIANGOS_CLAWS,AMULET_OF_FURY_OR,OCCULT_NECKLACE_OR,NECKLACE_OF_ANGUISH_OR,AMULET_OF_TORTURE_OR,BERSERKER_NECKLACE_OR,TORMENTED_BRACELET_OR, DRAGON_BOOTS_G, DRAGON_DEFENDER_T, SANGUINE_SCYTHE_OF_VITUR,
                        SANGUINE_TWISTED_BOW
                    );

                    boolean ignore_charged_actions = charged_item_ids.stream().anyMatch(charged_item_id -> charged_item_id == item.getId());

                    if (InteractionManager.checkItemInteraction(player, item, 4) && !ignore_charged_actions) {
                        return;
                    }

                    if (ItemActionFour.click(player, item)) {
                        return;
                    }

                    ItemDefinition def = item.definition(World.getWorld());
                    //System.out.println(Arrays.toString(def.ioptions));
                    //System.out.println(Arrays.toString(def.options));
                    if (def != null && def.ioptions != null && def.ioptions[4] != null && def.ioptions[4].equalsIgnoreCase("destroy")) {
                        destroyOption(player, slot, true);
                    } else {
                        //Check to see if the player is special teleblocked
                        if (player.getTimers().has(TimerKey.SPECIAL_TELEBLOCK)) {
                            player.teleblockMessage();
                            return;
                        }

                        if (item.getId() == CustomItemIdentifiers.WILDERNESS_KEY && WildernessArea.inWilderness(player.tile())) {
                            player.message("You can't drop this item.");
                            return;
                        }

                        int totalValueStack = item.getValue() * item.getAmount();
                        boolean stackableValuedItem = item.stackable() && totalValueStack > 10_000_000;
                        if ((stackableValuedItem || item.getValue() > 10_000_000) && WildernessArea.inWilderness(player.tile())) {
                            if(!player.inventory().contains(item)) {
                                return;
                            }
                            player.confirmDialogue(new String[]{"Are you sure you wish to drop your "+item.unnote().name()+"?", "Everyone can pick up your "+item.unnote().name()+" it will be lost forever.", "Dropping the item is at your own risk."}, "", "Proceed.", "Nevermind.", () -> {
                                if(!player.inventory().contains(item)) {
                                    return;
                                }
                                player.inventory().remove(item,true);
                                dropItem(player, item);
                            });
                            return;
                        }

                        player.inventory().remove(item, true);

                        /*if (item.getValue() == 0 && WildernessArea.inWilderness(player.tile())) {
                            player.message(Color.RED.wrap("You dropped a spawnable item in the wilderness, it disappears as it touches"));
                            player.message(Color.RED.wrap("the ground."));
                            return;
                        }*/

                        //Remove from inventory
                        dropItem(player, item);
                    }
                }
            }
        }
    }

    private void dropItem(Player player, Item item) {
        //We probably don't need to duplicate item attributes/properties here,
        //since dropping loses charges in OSRS.
        GroundItem groundItem = new GroundItem(item, player.tile(), player);

        //When dropping items in the wilderness everyone can instantly pick them up
        if (WildernessArea.inWilderness(player.tile())) {
            groundItem.setState(State.SEEN_BY_EVERYONE);
        }

        //Drop the item on the floor
        GroundItemHandler.createGroundItem(groundItem);
        player.getRisk().update();
        Utils.sendDiscordInfoLog("Player " + player.getUsername() + " with IP "+player.getHostAddress()+" dropped item " + item.getAmount() + "x " + item.unnote().name() + " at " + player.tile().toString(), "item_dropped");
    }

    private void destroyOption(Player player, int invSlot, boolean destroyIt) {
        player.stopActions(false);
        player.animate(-1); // Reset animation

        Item item = player.inventory().get(invSlot);
        if (item == null) return;

        String msg = destroyIt ? "Destroying this item permanently destroys it. You cannot get it back." : "It will be dropped to the ground, you can pick it up.";

        player.setDestroyItem(item.getId());
        String[][] info = {//The info the dialogue gives
            {"Are you sure you want to destroy this item?", "14174"},
            {"Yes.", "14175"}, {"No.", "14176"}, {"", "14177"},
            {msg, "14182"}, {"You cannot get it back if discarded.", "14183"},
            {item.name(), "14184"}};
        player.getPacketSender().sendItemOnInterfaceSlot(14171, item.getId(), item.getAmount(), 0);
        for (String[] strings : info) player.getPacketSender().sendString(Integer.parseInt(strings[1]), strings[0]);
        player.getPacketSender().sendChatboxInterface(14170);
    }

}
