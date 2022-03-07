package com.valinor.game.world.entity.mob.player.commands.impl.players;

import com.valinor.game.GameEngine;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;
import com.valinor.util.FileUtil;
import com.valinor.util.Utils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.valinor.util.CustomItemIdentifiers.PKP_TICKET;
import static com.valinor.util.CustomItemIdentifiers.WEAPON_MYSTERY_BOX;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 07, 2022
 */
public class PromoCodeCommand implements Command {

    private long lastCommandUsed;

    public static boolean PROMO_CODE_COMMAND_ENABLED = true;

    private static final Set<String> promoCodeClaimedIP = new HashSet<>(), promoCodeClaimedMAC = new HashSet<>();

    private static final String directory = "./data/saves/promo_codes/PromoCodeClaimed.txt";

    public static void init() {
        promoClaimed();
    }

    private static void promoClaimed() {
        try {
            try (BufferedReader in = new BufferedReader(new FileReader(PromoCodeCommand.directory))) {
                String data;
                while ((data = in.readLine()) != null) {
                    promoCodeClaimedIP.add(data);
                    promoCodeClaimedMAC.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final List<Item> ITEM_LIST = Arrays.asList(
        new Item(COINS_995, 5_000_000),
        new Item(ABYSSAL_TENTACLE, 1),
        new Item(DHAROKS_ARMOUR_SET, 1),
        new Item(WEAPON_MYSTERY_BOX, 1)
    );

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (!PROMO_CODE_COMMAND_ENABLED) {
            return;
        }

        //Only allow this command to be used every 30 seconds.
        if (System.currentTimeMillis() - lastCommandUsed >= 30_000) {
            lastCommandUsed = System.currentTimeMillis();
            if (command.length() <= 10) {
                player.message("Invalid usage of the command ::promocode.");
                return;
            }

            String username = Utils.formatText(command.substring(10)); // after "promocode "

            List<String> youtubers = Arrays.asList("fpk merk", "vexia", "i pk max jr",
                "lanors", "smoothie", "juicy orange", "respire", "baguette rsps", "primatol",
                "fewb", "sohan");

            if (youtubers.stream().anyMatch(name -> name.toLowerCase().equalsIgnoreCase(username))) {
                var IP = player.getHostAddress();
                var creationIP = player.getCreationIp();
                var MAC = player.<String>getAttribOr(AttributeKey.MAC_ADDRESS, "invalid");
                var promoClaimed = player.<Boolean>getAttribOr(AttributeKey.PROMO_CODE_CLAIMED, false);
                var fileAlreadyContainsAddress = FileUtil.claimed(IP, MAC, directory);

                player.message(Color.RED.wrap("Checking for availability this could take a moment..."));

                GameEngine.getInstance().submitLowPriority(() -> {

                    // this file checking code has to go in low prio which is not the game thread, so it doesn't lag
                    try {
                        if (FileUtil.existingPlayer(IP, MAC)) {
                            player.message(Color.RED.wrap("You cannot claim this promo code as an existing player."));
                            return; // Existing player
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    // then afterwards, we run the next code back on the game thread

                    GameEngine.getInstance().addSyncTask(() -> {
                        // all this code is back in game thread

                        if (!creationIP.equalsIgnoreCase(IP)) {
                            player.message(Color.RED.wrap("Your IP doesn't match your creation IP, you cannot claim this promo code."));
                            return; // IP changer
                        }

                        //Check if the player doesn't have a spoofed mac address
                        if (IP.isEmpty() || MAC.isEmpty() || MAC.equalsIgnoreCase("invalid")) {
                            player.message(Color.RED.wrap("You are not on a valid IP or MAC address."));
                            return; // No valid mac address
                        }

                        //Check if the player has already claimed the promo code
                        if (promoClaimed || fileAlreadyContainsAddress) {
                            player.message(Color.RED.wrap("You've already claimed a promo code and cannot claim another."));
                            return; // Already claimed one
                        }

                        //Add the player address to the file
                        // more file io

                        GameEngine.getInstance().submitLowPriority(() -> {
                            FileUtil.addAddressToClaimedList(IP, MAC, promoCodeClaimedIP, promoCodeClaimedMAC, directory);
                            if(username.equalsIgnoreCase("fpk merk")) {
                                FileUtil.writeTextToDir("fpk merk - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("vexia")) {
                                FileUtil.writeTextToDir("vexia - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("i pk max jr")) {
                                FileUtil.writeTextToDir("i pk max jr - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("smoothie")) {
                                FileUtil.writeTextToDir("smoothie - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("juicy orange")) {
                                FileUtil.writeTextToDir("juicy orange - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("respire")) {
                                FileUtil.writeTextToDir("respire - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("baguette rsps")) {
                                FileUtil.writeTextToDir("baguette rsps - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("primatol")) {
                                FileUtil.writeTextToDir("primatol - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("fewb")) {
                                FileUtil.writeTextToDir("fewb - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            } else if(username.equalsIgnoreCase("sohan")) {
                                FileUtil.writeTextToDir("sohan - "+player.getUsername()+" - "+IP+" - "+MAC, directory);
                            }
                        });

                        player.inventory().addOrBank(ITEM_LIST);

                        Utils.sendDiscordInfoLog(player.getUsername() + " with IP "+player.getHostAddress()+" and MAC "+MAC+" claimed the promo code.", "promo_code_claimed");

                        //Mark as opened
                        player.putAttrib(AttributeKey.PROMO_CODE_CLAIMED, true);
                    });
                });
            } else {
                player.message(username + " doesn't have a promo code.");
            }
        }
    }

    @Override
    public boolean canUse(Player player) {
        return true;
    }
}
