package com.valinor.game.content.areas.burthope.warriors_guild.dialogue;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;

import static com.valinor.util.NpcIdentifiers.ANTON;

/**
 * @author PVE
 * @Since juli 10, 2020
 */
public class Anton extends Dialogue {

    @Override
    protected void start(Object... parameters) {
        send(DialogueType.NPC_STATEMENT, ANTON, Expression.CALM_TALK, "Ahhh, hello there. How can I help?");
        setPhase(0);
    }

    @Override
    protected void next() {
        if(isPhase(0)) {
            send(DialogueType.PLAYER_STATEMENT, Expression.CALM_TALK, "Looks like you have a good selection", "of weapons around here...");
            setPhase(1);
        } else if(isPhase(1)) {
            send(DialogueType.NPC_STATEMENT, ANTON, Expression.CALM_TALK, "Indeed so, specially imported from the finest smiths around", "the lands, take a look at my wares.");
            setPhase(2);
        } else if(isPhase(2)) {
            stop();
            World.getWorld().shop(24).open(player);
        }
    }
}
