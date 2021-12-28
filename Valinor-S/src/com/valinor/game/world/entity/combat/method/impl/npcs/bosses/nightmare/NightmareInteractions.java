package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.THE_NIGHTMARE_9461;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 28, 2021
 */
public class NightmareInteractions extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if(npc.id() == THE_NIGHTMARE_9461) {
                Chain.bound(null).runFn(1, () -> player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.STATEMENT, player.getUsername() + ", would you like to fight The Nightmare?");
                        setPhase(0);
                    }

                    @Override
                    protected void next() {
                        if (isPhase(0)) {
                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Start a new instance", "Join a friends instance", "No thanks.");
                            setPhase(1);
                        }
                    }

                    @Override
                    protected void select(int option) {
                        if (isPhase(1)) {
                            if (option == 1) {
                                stop();
                                player.getNightmareInstance().enterInstance(player);
                            }
                            if (option == 2) {
                                stop();
                                //TODO join team instance
                            }
                            if (option == 3) {
                                stop();
                            }
                        }
                    }
                }));
                return true;
            }
        }
        return false;
    }

    @Override
    public void onLogin(Player player) {
        if(player.tile().region() == 15515) {
            player.teleport(3808, 9749, 1);
        }
    }
}
