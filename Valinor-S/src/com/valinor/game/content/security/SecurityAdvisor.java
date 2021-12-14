package com.valinor.game.content.security;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.NpcIdentifiers.SECURITY_GUARD;

/**
 * @author Patrick van Elderen | April, 29, 2021, 18:16
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class SecurityAdvisor extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if(npc.id() == SECURITY_GUARD) {
                player.getDialogueManager().start(new SecurityAdvisorDialogue());
                return true;
            }
        }
        return false;
    }
}
