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

import static com.valinor.util.CustomItemIdentifiers.STARTER_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class StarterBox extends Interaction {

    public static boolean STARTER_BOX_ENABLED = true;

    private static final List<Item> STARTER_SETUP = Arrays.asList(new Item(DHAROKS_ARMOUR_SET), new Item(ABYSSAL_TENTACLE), new Item(GRANITE_MAUL_24225), new Item(DRAGON_BOOTS), new Item(AMULET_OF_FURY), new Item(COINS_995, 5_000_000));

    private static final Set<String> starterBoxClaimedIP = new HashSet<>();
    private static final Set<String> starterBoxClaimedMAC = new HashSet<>();

    private static final String directory = "./data/saves/starterBoxClaimed.txt";

    public static void init() {
        starterMysteryBoxClaimed();
    }

    private static void starterMysteryBoxClaimed() {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(StarterBox.directory))) {
                String data;
                while ((data = in.readLine()) != null) {
                    starterBoxClaimedIP.add(data);
                    starterBoxClaimedMAC.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void claimStarterBox(Player player) {
        var IP = player.getHostAddress();
        var creationIP = player.getCreationIp();
        var MAC = player.<String>getAttribOr(AttributeKey.MAC_ADDRESS, "invalid");
        var starterBoxClaimed = player.<Boolean>getAttribOr(AttributeKey.STARTER_BOX_CLAIMED,false);
        var fileAlreadyContainsAddress = FileUtil.claimed(IP, MAC, directory);

        if (!creationIP.equalsIgnoreCase(IP)) {
            player.message(Color.RED.wrap("Your IP doesn't match your creation IP, you cannot open this starter box."));
            return; // IP changer
        }

        //Check if the player doesn't have a spoofed mac address
        if (IP.isEmpty() || MAC.isEmpty() || MAC.equalsIgnoreCase("invalid")) {
            player.message(Color.RED.wrap("You are not on a valid IP or MAC address."));
            return; // No valid mac address
        }

        //Check if the player has already claimed the promo code
        if (starterBoxClaimed || fileAlreadyContainsAddress) {
            player.message(Color.RED.wrap("You've already opened a starter box and cannot open another."));
            return; // Already claimed one
        }

        if(!STARTER_BOX_ENABLED) {
            player.message(Color.RED.wrap("The starter box cannot be opened currently and is disabled."));
            return; // System disabled
        }

        //Add the player address to the file
        FileUtil.addAddressToClaimedList(IP, MAC, starterBoxClaimedIP, starterBoxClaimedMAC, directory);

        //Mark as claimed
        player.putAttrib(AttributeKey.STARTER_BOX_CLAIMED,true);
        player.inventory().addOrBank(new Item(STARTER_BOX));
        Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" received a starter box.", "boxes_opened");
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == STARTER_BOX) {
                var IP = player.getHostAddress();
                var MAC = player.<String>getAttribOr(AttributeKey.MAC_ADDRESS,"invalid");
                var starterBoxClaimed = player.<Boolean>getAttribOr(AttributeKey.STARTER_BOX_CLAIMED,false);
                var fileAlreadyContainsAddress = FileUtil.claimed(IP, MAC, directory);

                //Check if the player has already claimed the box
                if(starterBoxClaimed || fileAlreadyContainsAddress) {
                    player.message("You have already claimed the starter box.");
                    return true; // Already claimed
                }

                player.inventory().remove(STARTER_BOX);
                player.inventory().addOrBank(STARTER_SETUP);
                Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" opened a starter box.", "boxes_opened");
                return true;
            }
        }
        return false;
    }
}
