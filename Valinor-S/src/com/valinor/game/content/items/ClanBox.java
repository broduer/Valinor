package com.valinor.game.content.items;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.FileUtil;
import com.valinor.util.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.valinor.util.CustomItemIdentifiers.CLAN_BOX;
import static com.valinor.util.CustomItemIdentifiers.PKP_TICKET;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class ClanBox extends Interaction {

    private static final Set<String> clanBoxClaimedIP = new HashSet<>();
    private static final Set<String> clanBoxClaimedMAC = new HashSet<>();

    private static final String directory = "./data/saves/clanBoxOpened.txt";

    public static void init() {
        clanBoxClaimed();
    }

    private static void clanBoxClaimed() {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(ClanBox.directory))) {
                String data;
                while ((data = in.readLine()) != null) {
                    clanBoxClaimedIP.add(data);
                    clanBoxClaimedMAC.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == CLAN_BOX) {
                open(player);
                return true;
            }
        }
        return false;
    }

    private final List<Item> ITEMS = Arrays.asList(new Item(DRAGON_CROSSBOW), new Item(ARMADYL_GODSWORD), new Item(STAFF_OF_LIGHT, 2), new Item(AHRIMS_ARMOUR_SET,2), new Item(KARILS_LEATHERTOP,2), new Item(VERACS_PLATESKIRT,2), new Item(DRAGONSTONE_DRAGON_BOLTS_E,2000), new Item(BERSERKER_RING,2), new Item(INFINITY_BOOTS,2), new Item(AMULET_OF_FURY,2), new Item(PKP_TICKET, 50_000));

    private void open(Player player) {
        if(player.inventory().contains(CLAN_BOX)) {
            var IP = player.getHostAddress();
            var MAC = player.<String>getAttribOr(AttributeKey.MAC_ADDRESS,"invalid");
            var clanBoxOpened = player.<Boolean>getAttribOr(AttributeKey.CLAN_BOX_OPENED,false);
            var fileAlreadyContainsAddress = FileUtil.claimed(IP, MAC, directory);

            //Check if the player doesn't have a spoofed mac address
            if(IP.isEmpty() || MAC.isEmpty() || MAC.equalsIgnoreCase("invalid")) {
                player.message(Color.RED.wrap("You are not on a valid IP or MAC address. You cannot open this box."));
                return; // No valid mac address
            }

            //Check if the player has already claimed the box
            if(clanBoxOpened || fileAlreadyContainsAddress) {
                player.message(Color.RED.wrap("You can only open one clan box."));
                return; // Already opened one
            }

            //Add the player address to the file
            FileUtil.addAddressToClaimedList(IP, MAC, clanBoxClaimedIP, clanBoxClaimedMAC, directory);

            player.inventory().remove(CLAN_BOX);
            player.inventory().addOrBank(ITEMS);

            Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" opened a clan box.", "boxes_opened");

            //Mark as opened
            player.putAttrib(AttributeKey.CLAN_BOX_OPENED,true);
        }
    }
}
