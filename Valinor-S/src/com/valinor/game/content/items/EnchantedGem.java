package com.valinor.game.content.items;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.content.EnchantedGemD;
import com.valinor.game.content.skill.impl.slayer.slayer_partner.SlayerPartner;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.util.ItemIdentifiers.ENCHANTED_GEM;

/**
 * @author Patrick van Elderen | December, 24, 2020, 13:05
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class EnchantedGem extends Interaction {

    @Override
    public boolean handleEquipment(Player player, Item item) {
        if (item.getId() == ENCHANTED_GEM) {
            checkSlayerStatus(player);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == ENCHANTED_GEM) {
                player.getDialogueManager().start(new EnchantedGemD());
                return true;
            }
        }

        if (option == 2) {
            if (item.getId() == ENCHANTED_GEM) {
                player.getBossKillLog().openLog();
                return true;
            }
        }

        if (option == 3) {
            if (item.getId() == ENCHANTED_GEM) {
                SlayerPartner.partnerOption(player);
                return true;
            }
        }
        return false;
    }

    private static void checkSlayerStatus(Player player) {
        SlayerCreature task = SlayerCreature.lookup(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));
        int num = player.getAttribOr(AttributeKey.SLAYER_TASK_AMT, 0);

        if (task != null) {
            if (num > 0) {
                player.message("You're assigned to kill " + Slayer.taskName(task.uid) + "; only " + num + " more to go.");
            } else {
                player.message("You need something new to hunt.");
            }
        } else {
            player.message("You need something new to hunt.");
        }
    }
}
