package com.valinor.game.content.seasonal_events.halloween;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.NpcIdentifiers.TEACHER_AND_PUPIL_1922;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since October 12, 2021
 */
public class TeacherAndPupil extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if(option == 1) {
            if(npc.id() == TEACHER_AND_PUPIL_1922) {
                player.getDialogueManager().start(new TeacherAndPupilD());
                return true;
            }
        } else if(option == 2) {
            if(npc.id() == TEACHER_AND_PUPIL_1922) {
                World.getWorld().shop(48).open(player);
                return true;
            }
        }
        return false;
    }
}
