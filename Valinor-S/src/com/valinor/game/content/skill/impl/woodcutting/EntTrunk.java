package com.valinor.game.content.skill.impl.woodcutting;

import com.valinor.game.content.skill.impl.mining.Mining;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.ENT_TRUNK;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 20, 2022
 */
public class EntTrunk extends Interaction {

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        if (npc.id() == ENT_TRUNK) {
            var axe = Woodcutting.findAxe(player);

            if (axe.isEmpty()) {
                player.message("You do not have a axe which you have the Wooductting level to use.");
            } else {
                TaskManager.submit(player.loopTask = new Task("loop_skill_task_golem", 1) {

                    int internalTimer = 3;

                    @Override
                    public void execute() {
                        player.animate(axe.get().anim);

                        if (internalTimer-- == 0) {
                            // 35% chance per 3 ticks to cut a log from the trunk
                            if (World.getWorld().random(100) <=
                                Woodcutting.chance(player.skills().level(Skills.WOODCUTTING),
                                    Woodcutting.Tree.ENTTRUNK, axe.get())) {
                                player.skills().addXp(Skills.WOODCUTTING, 25.0);
                                player.inventory().add(new Item(Woodcutting.getEntLog(player)), true);
                                stop();
                            } else {
                                internalTimer = 3;
                            }
                        }
                    }

                    @Override
                    public void onStop() {
                        player.animate(-1);
                    }
                });
            }
            return true;
        }
        return false;
    }
}
