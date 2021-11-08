package com.valinor.game.content.areas.edgevile;

import com.valinor.GameServer;
import com.valinor.db.transactions.CollectPayments;
import com.valinor.game.content.areas.edgevile.dialogue.PaymentManagerDialogue;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.PacketInteraction;

import static com.valinor.util.NpcIdentifiers.WISE_OLD_MAN;

/**
 * @author Patrick van Elderen | April, 22, 2021, 11:46
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class WiseOldMan extends PacketInteraction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if (option == 1) {
            if (npc.id() == WISE_OLD_MAN) {
                player.getDialogueManager().start(new PaymentManagerDialogue());
                return true;
            }
        }
        if (option == 2) {
            if (npc.id() == WISE_OLD_MAN) {
                openStore(player);
                return true;
            }
        }
        if (option == 3) {
            if (npc.id() == WISE_OLD_MAN) {
                if (GameServer.properties().enableSql) {
                    claim(player);
                } else {
                    player.message("The database is disabled at this point.");
                }
                return true;
            }
        }
        return false;
    }

    private void claim(Player player) {
        CollectPayments.INSTANCE.collectPayments(player);
    }

    private void openStore(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Open webstore.", "Donator store.", "Nevermind.");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        player.getPacketSender().sendURL("https://ferox-os.com/store/");
                    }
                    if (option == 2) {
                        World.getWorld().shop(43).open(player);
                        player.getPacketSender().sendConfig(1125, 1);
                        player.getPacketSender().sendConfig(1126, 0);
                        player.getPacketSender().sendConfig(1127, 0);
                    }
                    stop();
                }
            }
        });
    }
}
