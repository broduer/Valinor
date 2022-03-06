package com.valinor.game.content.items;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.game.world.entity.AttributeKey.METAMORPHIC_DUST_USED_ON;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 06, 2022
 */
public class MetamorphicDust extends Interaction {

    private static class MetamorphicDustD extends Dialogue {

        @Override
        protected void start(Object... parameters) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Tektiny", "Enraged Tektiny", "Vespina", "Flying Vespina", "More...");
            setPhase(0);
        }

        @Override
        protected void select(int option) {
            int usedOnId = player.<Integer>getAttribOr(METAMORPHIC_DUST_USED_ON, -1);
            if(isPhase(0)) {
                if(option == 1) {
                    stop();
                   if(!player.inventory().contains(usedOnId))  {
                       return;
                   }
                   player.inventory().replace(usedOnId, TEKTINY,true);
                }
                if(option == 2) {
                    stop();
                    if(!player.inventory().contains(usedOnId))  {
                        return;
                    }
                    player.inventory().replace(usedOnId, ENRAGED_TEKTINY,true);
                }
                if(option == 3) {
                    stop();
                    if(!player.inventory().contains(usedOnId))  {
                        return;
                    }
                    player.inventory().replace(usedOnId, VESPINA,true);
                }
                if(option == 4) {
                    stop();
                    if(!player.inventory().contains(usedOnId))  {
                        return;
                    }
                    player.inventory().replace(usedOnId, FLYING_VESPINA,true);
                }
                if(option == 5) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Vanguard", "Puppadile", "Vasa Minirio", "Olmlet", "Back...");
                    setPhase(1);
                }
            } else if(isPhase(1)) {
                if(option == 1) {
                    stop();
                    if(!player.inventory().contains(usedOnId))  {
                        return;
                    }
                    player.inventory().replace(usedOnId, VANGUARD,true);
                }
                if(option == 2) {
                    stop();
                    if(!player.inventory().contains(usedOnId))  {
                        return;
                    }
                    player.inventory().replace(usedOnId, PUPPADILE,true);
                }
                if(option == 3) {
                    stop();
                    if(!player.inventory().contains(usedOnId))  {
                        return;
                    }
                    player.inventory().replace(usedOnId, VASA_MINIRIO,true);
                }
                if(option == 4) {
                    stop();
                    if(!player.inventory().contains(usedOnId))  {
                        return;
                    }
                    player.inventory().replace(usedOnId, OLMLET,true);
                }
                if(option == 5) {
                    send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Tektiny", "Enraged Tektiny", "Vespina", "Flying Vespina", "More...");
                    setPhase(0);
                }
            }
        }
    }

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == OLMLET || usedWith.getId() == OLMLET)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, OLMLET);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == TEKTINY || usedWith.getId() == TEKTINY)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, TEKTINY);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == ENRAGED_TEKTINY || usedWith.getId() == ENRAGED_TEKTINY)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, ENRAGED_TEKTINY);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == VESPINA || usedWith.getId() == VESPINA)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, VESPINA);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == FLYING_VESPINA || usedWith.getId() == FLYING_VESPINA)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, FLYING_VESPINA);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == VANGUARD || usedWith.getId() == VANGUARD)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, VANGUARD);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == PUPPADILE || usedWith.getId() == PUPPADILE)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, PUPPADILE);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        if ((use.getId() == METAMORPHIC_DUST || usedWith.getId() == METAMORPHIC_DUST) && (use.getId() == VASA_MINIRIO || usedWith.getId() == VASA_MINIRIO)) {
            player.putAttrib(METAMORPHIC_DUST_USED_ON, VASA_MINIRIO);
            player.getDialogueManager().start(new MetamorphicDustD());
            return true;
        }
        return false;
    }
}
