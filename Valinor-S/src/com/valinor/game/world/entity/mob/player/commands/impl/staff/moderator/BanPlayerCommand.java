package com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator;

import com.valinor.GameServer;
import com.valinor.db.DatabaseExtensionsKt;
import com.valinor.db.transactions.BanPlayerDatabaseTransaction;
import com.valinor.game.GameEngine;
import com.valinor.game.content.mechanics.referrals.Referrals;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.PlayerStatus;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.commands.impl.kotlin.MiscKotlin;
import com.valinor.game.world.entity.mob.player.save.PlayerSave;
import com.valinor.util.PlayerPunishment;
import com.valinor.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class BanPlayerCommand implements Command {

    private static final Logger logger = LogManager.getLogger(BanPlayerCommand.class);

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 4)
            return;
        String username = Utils.formatText(command.substring(4)); // after "ban "
        if (GameServer.properties().enableSql && GameServer.properties().punishmentsToDatabase) {
            player.getDialogueManager().start(new BanDialogue(username));
            return;
        }

        if(!GameServer.properties().punishmentsToDatabase) {
            Optional<Player> playerToBan = World.getWorld().getPlayerByName(username);
            if (playerToBan.isPresent()) {
                if(playerToBan.get().getPlayerRights().isStaffMember(playerToBan.get()) && !player.getPlayerRights().isDeveloperOrGreater(player)) {
                    player.message("You cannot ban this player.");
                    logger.warn(player.getUsername() + " tried to ban " + playerToBan.get().getUsername(), "warning");
                    return;
                }

                if (PlayerPunishment.banned(playerToBan.get().getUsername())) {
                    player.message("Player " + playerToBan.get().getUsername() + " already has an active ban.");
                    return;
                }

                //When in trade kick from trade first
                if(playerToBan.get().getStatus() == PlayerStatus.TRADING) {
                    playerToBan.get().getTrading().abortTrading();
                }

                //When in a duel forfeit for the player about to get banned
                if(playerToBan.get().getStatus() == PlayerStatus.DUELING) {
                    playerToBan.get().getDueling().onDeath();
                }

                //When in a gamble forfeit for the player about to get banned
                if(playerToBan.get().getStatus() == PlayerStatus.DUELING) {
                    playerToBan.get().getGamblingSession().abortGambling();
                }

                PlayerPunishment.addBan(playerToBan.get().getUsername());
                playerToBan.get().requestLogout();

                player.message("Player " + username + " was successfully banned.");
                Utils.sendDiscordInfoLog("Player " + username + " was banned by " + player.getUsername(), "staff_cmd");
            } else {
                //offline
                Player offlinePlayer = new Player();
                offlinePlayer.setUsername(Utils.formatText(username.substring(0, 1).toUpperCase() + username.substring(1)));

                GameEngine.getInstance().submitLowPriority(() -> {
                    try {
                        if (PlayerSave.loadOfflineWithoutPassword(offlinePlayer)) {
                            GameEngine.getInstance().addSyncTask(() -> {
                                if(!PlayerSave.playerExists(offlinePlayer.getUsername())) {
                                    player.message("There is no such player profile.");
                                    return;
                                }

                                if (PlayerPunishment.banned(offlinePlayer.getUsername())) {
                                    player.message("Player " + offlinePlayer.getUsername() + " already has an active ban.");
                                    return;
                                }

                                PlayerPunishment.addBan(offlinePlayer.getUsername());
                                player.message("Player " + offlinePlayer.getUsername() + " was successfully offline banned.");
                                Utils.sendDiscordInfoLog("Player " + offlinePlayer.getUsername() + " was offline banned by " + player.getUsername(), "staff_cmd");
                            });
                        } else {
                            player.message("Something went wrong trying to offline ban "+offlinePlayer.getUsername());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isModeratorOrGreater(player));
    }

    public static final class BanDialogue extends Dialogue {
        LocalDateTime unbanDate = null;
        private final String username;

        public BanDialogue(String username) {
            this.username = username;
        }

        @Override
        protected void start(Object... parameters) {
            send(DialogueType.OPTION, "Enter Ban Duration", "Minutes", "Hours", "Days", "Permanent");
            setPhase(1);
        }

        @Override
        public void select(int option) {

            if (isPhase(1)) {
                if (option == 1) {
                    stop();
                    enterDuration("minutes");
                } else if (option == 2) {
                    stop();
                    enterDuration("hours");
                } else if (option == 3) {
                    stop();
                    enterDuration("days");
                } else {
                    stop();
                    enterReason("99", "perm");
                }
            }
        }

        private void enterDuration(String type) {
            player.getInterfaceManager().close();
            player.setEnterSyntax(new EnterSyntax() {
                @Override
                public void handleSyntax(Player player, String input) {
                    //This is a sync task because it needs to be done on the next tick, since inputs cannot be chained, it closes immediately if you try to put an input event inside another one.
                    GameEngine.getInstance().addSyncTask(() -> enterReason(input, type));
                }
            });
            if (!type.equals("perm")) {
                player.getPacketSender().sendEnterInputPrompt("Enter duration in " + type + ":");
            }
        }

        private void enterReason(String duration, String type) {
            player.getInterfaceManager().close();
            player.setEnterSyntax(new EnterSyntax() {
                @Override
                public void handleSyntax(Player player, String input) {
                    LocalDateTime unbanDate = null;
                    if (!type.equals("perm")) {
                        switch (type) {
                            case "hours" -> unbanDate = LocalDateTime.now().plusHours(Long.parseLong(duration));
                            case "days" -> unbanDate = LocalDateTime.now().plusDays(Long.parseLong(duration));
                            case "minutes" -> unbanDate = LocalDateTime.now().plusMinutes(Long.parseLong(duration));
                        }
                    } else {
                        unbanDate = LocalDateTime.now().plusYears(Long.parseLong(duration));
                    }
                    Timestamp unbanTimestamp = Timestamp.valueOf(unbanDate);
                    Optional<Player> plr = World.getWorld().getPlayerByName(username);
                    //System.out.println("Looking for " + username + " to ban");
                    if (plr.isPresent()) {
                        if (plr.get().getPlayerRights().greater(player.getPlayerRights())) {
                            player.message("You cannot ban that player!");
                            return;
                        }
                        plr.get().requestLogout();
                        GameServer.getDatabaseService().submit(new BanPlayerDatabaseTransaction(username, unbanTimestamp, input));
                        player.message("Player " + username + " was successfully banned until " + unbanTimestamp + " for: " + input);
                        Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::ban "+username+" until " + unbanTimestamp + " for: " + input, "staff_cmd");
                    } else {
                        final String player2Username = username;
                        if (Arrays.stream(player.getPlayerRights().getOwners()).anyMatch(name -> name.equalsIgnoreCase(player2Username))) {
                            player.message("You cannot ban that player!");
                            return;
                        }
                        DatabaseExtensionsKt.submit(MiscKotlin.INSTANCE.getPlayerDbIdForName(username), id -> {
                            if (id == -1) {
                                player.message("There is no player by the name '"+username+"'");
                            } else {
                                GameServer.getDatabaseService().submit(new BanPlayerDatabaseTransaction(username, unbanTimestamp, input));
                                player.message("Player " + username + " was successfully banned until " + unbanTimestamp + " for: " + input);
                                Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::ban "+username+" until " + unbanTimestamp + " for: " + input, "staff_cmd");
                            }
                        });
                    }
                }
            });
            player.getPacketSender().sendEnterInputPrompt("Enter the reason:");
        }
    }

}
