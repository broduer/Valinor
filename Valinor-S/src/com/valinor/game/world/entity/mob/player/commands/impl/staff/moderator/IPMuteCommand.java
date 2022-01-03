package com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator;

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
import com.valinor.util.Utils;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Patrick van Elderen | January, 16, 2021, 11:01
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class IPMuteCommand implements Command {

    @Override
    public void execute(Player player, String command, String[] parts) {
        if (command.length() <= 7)
            return;
        String username = Utils.formatText(command.substring(7)); // after "ipmute "
        if (GameServer.properties().enableSql) {
            player.getDialogueManager().start(new MuteDialogue(username));
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isModeratorOrGreater(player));
    }

    public static final class MuteDialogue extends Dialogue {
        LocalDateTime unmuteDate = null;
        private final String username;

        public MuteDialogue(String username) {
            this.username = username;
        }

        @Override
        protected void start(Object... parameters) {
            send(DialogueType.OPTION, "Enter Mute Duration", "Minutes", "Hours", "Days", "Permanent");
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
                public void handleSyntax(Player player, @NotNull String input) {
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
                public void handleSyntax(Player player, @NotNull String reason) {
                    LocalDateTime unmuteDate = null;
                    if (!type.equals("perm")) {
                        switch (type) {
                            case "hours" -> unmuteDate = LocalDateTime.now().plusHours(Long.parseLong(duration));
                            case "days" -> unmuteDate = LocalDateTime.now().plusDays(Long.parseLong(duration));
                            case "minutes" -> unmuteDate = LocalDateTime.now().plusMinutes(Long.parseLong(duration));
                        }
                    } else {
                        unmuteDate = LocalDateTime.now().plusYears(Long.parseLong(duration));
                    }
                    Timestamp unmuteTimestamp = Timestamp.valueOf(unmuteDate);
                    Optional<Player> plr = World.getWorld().getPlayerByName(username);
                    //System.out.println("Looking for " + username + " to mute");
                    if (plr.isPresent()) {
                        if (plr.get().getPlayerRights().greater(player.getPlayerRights())) {
                            player.message("You cannot mute that player!");
                            return;
                        }
                        MiscKotlin.INSTANCE.addIPMute(player, username, unmuteTimestamp, reason, feedback(player));
                        player.message("Player " + username + " was successfully muted until " + unmuteTimestamp + " for: " + reason);
                        Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::ipmute "+username+" until " + unmuteTimestamp + " for: " + reason, "staff_cmd");
                    } else {
                        final String player2Username = username;
                        if (Arrays.stream(player.getPlayerRights().getOwners()).anyMatch(name -> name.equalsIgnoreCase(player2Username))) {
                            player.message("You cannot mute that player!");
                            return;
                        }
                        DatabaseExtensionsKt.submit(MiscKotlin.INSTANCE.getPlayerDbIdForName(username), id -> {
                            if (id == -1) {
                                player.message("There is no player by the name '"+username+"'");
                            } else {
                                MiscKotlin.INSTANCE.addIPMute(player, username, unmuteTimestamp, reason, feedback(player));
                                player.message("Player " + username + " was successfully muted until " + unmuteTimestamp + " for: " + reason);
                                Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::ipmute "+username+" until " + unmuteTimestamp + " for: " + reason, "staff_cmd");
                            }
                        });
                    }
                }
            });
            player.getPacketSender().sendEnterInputPrompt("Enter the reason:");
        }

    }

    public static Function1<? super List<? extends Player>, Unit> feedback(Player player) {
        return new Function1<List<? extends Player>, kotlin.Unit>() {
            @Override
            public kotlin.Unit invoke(List<? extends Player> players) {
                player.message(players.size()+" players were muted matching the same mute criteria.");
                player.message("Names: "+Arrays.toString(players.stream().map(Player::getUsername).toArray()));
                //System.out.println("Names: "+Arrays.toString(players.stream().map(p -> p.getUsername()).toArray()));
                return null;
            }
        };
    }

}
