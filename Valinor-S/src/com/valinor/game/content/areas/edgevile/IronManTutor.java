package com.valinor.game.content.areas.edgevile;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.player.*;
import com.valinor.game.world.items.Item;
import com.valinor.util.NpcIdentifiers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | January, 19, 2021, 13:41
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class IronManTutor extends Dialogue {

    private static final Logger logger = LogManager.getLogger(IronManTutor.class);

    public static void armour(Player player) {
        switch (player.gameMode()) {
            case REGULAR -> {
                if (player.ownsAny(IRONMAN_HELM, IRONMAN_PLATEBODY, IRONMAN_PLATELEGS)) {
                    player.message("Come back when you've lost your ironman armour.");
                    return;
                }
                player.inventory().add(new Item(IRONMAN_HELM, 1), true);
                player.inventory().add(new Item(IRONMAN_PLATEBODY, 1), true);
                player.inventory().add(new Item(IRONMAN_PLATELEGS, 1), true);
            }
            case HARDCORE -> {
                if (player.ownsAny(HARDCORE_IRONMAN_HELM, HARDCORE_IRONMAN_PLATEBODY, HARDCORE_IRONMAN_PLATELEGS)) {
                    player.message("Come back when you've lost your ironman armour.");
                    return;
                }
                player.inventory().add(new Item(HARDCORE_IRONMAN_HELM, 1), true);
                player.inventory().add(new Item(HARDCORE_IRONMAN_PLATEBODY, 1), true);
                player.inventory().add(new Item(HARDCORE_IRONMAN_PLATELEGS, 1), true);
            }
            case ULTIMATE -> {
                if (player.ownsAny(ULTIMATE_IRONMAN_HELM, ULTIMATE_IRONMAN_PLATEBODY, ULTIMATE_IRONMAN_PLATELEGS)) {
                    player.message("Come back when you've lost your ironman armour.");
                    return;
                }
                player.inventory().add(new Item(ULTIMATE_IRONMAN_HELM, 1), true);
                player.inventory().add(new Item(ULTIMATE_IRONMAN_PLATEBODY, 1), true);
                player.inventory().add(new Item(ULTIMATE_IRONMAN_PLATELEGS, 1), true);
            }
            default -> player.message("Only ironman players can claim their armour.");
        }

        var playerIsIron = player.gameMode().isIronman() || player.gameMode().isHardcoreIronman() || player.gameMode().isUltimateIronman();
        if (!playerIsIron) {
            DialogueManager.npcChat(player, Expression.HAPPY, NpcIdentifiers.IRON_MAN_TUTOR, "There you go. Wear it with pride.");
        }
    }

    @Override
    protected void start(Object... parameters) {

    }

    @Override
    public void next() {

    }

    @Override
    public void select(int option) {

    }
}
