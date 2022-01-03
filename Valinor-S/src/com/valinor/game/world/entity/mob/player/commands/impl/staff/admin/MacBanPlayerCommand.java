package com.valinor.game.world.entity.mob.player.commands.impl.staff.admin;

import com.valinor.GameServer;
import com.valinor.db.DatabaseExtensionsKt;
import com.valinor.game.GameEngine;
import com.valinor.game.content.mechanics.referrals.Referrals;
import com.valinor.game.content.syntax.EnterSyntax;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.commands.impl.kotlin.MiscKotlin;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator.BanPlayerCommand;
import com.valinor.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public class MacBanPlayerCommand implements Command {

    private static final Logger logger = LogManager.getLogger(MacBanPlayerCommand.class);

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 7)
            return;
        String username = Utils.formatText(command.substring(7)); // after "macban "
        if (GameServer.properties().enableSql) {
            player.getDialogueManager().start(new BanPlayerCommand.BanDialogue(username));
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isAdminOrGreater(player));
    }

    public static final class BanDialogue extends Dialogue {
        LocalDateTime unbanDate = null;
        private String username;

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
                public void handleSyntax(Player player, String reason) {
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
                            //logger.warn(player.getUsername() + " tried to ban " + plr.get().getUsername(), "warning");
                            return;
                        }
                        plr.get().requestLogout();
                        MiscKotlin.INSTANCE.addMacBan(player, username, unbanTimestamp, reason, IPBanPlayerCommand.feedback(player));
                        player.message("Player " + username + " was successfully banned until " + unbanTimestamp + " for: " + reason);
                        Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::macban "+username+" until " + unbanTimestamp + " for: " + reason, "staff_cmd");
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
                                MiscKotlin.INSTANCE.addMacBan(player, username, unbanTimestamp, reason, IPBanPlayerCommand.feedback(player));
                                player.message("Player " + username + " was successfully banned until " + unbanTimestamp + " for: " + reason);
                                Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::macban "+username+" until " + unbanTimestamp + " for: " + reason, "staff_cmd");
                            }
                        });
                    }
                }
            });
            player.getPacketSender().sendEnterInputPrompt("Enter the reason:");
        }
    }

}
