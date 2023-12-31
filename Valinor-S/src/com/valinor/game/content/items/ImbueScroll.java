package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.game.content.skill.impl.slayer.content.SlayerHelmet.*;
import static com.valinor.util.CustomItemIdentifiers.IMBUEMENT_SCROLL;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | December, 31, 2020, 18:53
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ImbueScroll extends Interaction {

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        if ((use.getId() == IMBUEMENT_SCROLL && usedWith.getId() == SLAYER_HELMET) || (use.getId() == SLAYER_HELMET && usedWith.getId() == IMBUEMENT_SCROLL)) {
            player.getInventory().remove(new Item(IMBUEMENT_SCROLL), true);
            player.getInventory().remove(new Item(SLAYER_HELMET), true);
            player.getInventory().add(new Item(SLAYER_HELMET_I), true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == HYDRA_SLAYER_HELM || usedWith.getId() == HYDRA_SLAYER_HELM)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(HYDRA_SLAYER_HELM),true);
            player.inventory().add(new Item(HYDRA_SLAYER_HELM_IMBUE),true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == RED_SLAYER_HELM || usedWith.getId() == RED_SLAYER_HELM)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(RED_SLAYER_HELM),true);
            player.inventory().add(new Item(RED_HELM_IMBUE),true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == GREEN_SLAYER_HELM || usedWith.getId() == GREEN_SLAYER_HELM)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(GREEN_SLAYER_HELM),true);
            player.inventory().add(new Item(GREEN_HELM_IMBUE),true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == PURPLE_SLAYER_HELM || usedWith.getId() == PURPLE_SLAYER_HELM)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(PURPLE_SLAYER_HELM),true);
            player.inventory().add(new Item(PURPLE_HELM_IMBUE),true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == TURQUOISE_SLAYER_HELM || usedWith.getId() == TURQUOISE_SLAYER_HELM)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(TURQUOISE_SLAYER_HELM),true);
            player.inventory().add(new Item(TURQUOISE_HELM_IMBUE),true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == BLACK_SLAYER_HELM || usedWith.getId() == BLACK_SLAYER_HELM)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(BLACK_SLAYER_HELM),true);
            player.inventory().add(new Item(BLACK_HELM_IMBUE),true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == MAGIC_SHORTBOW || usedWith.getId() == MAGIC_SHORTBOW)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(MAGIC_SHORTBOW),true);
            player.inventory().add(new Item(MAGIC_SHORTBOW_I),true);
            return true;
        }

        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == ARCHERS_RING || usedWith.getId() == ARCHERS_RING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(ARCHERS_RING),true);
            player.inventory().add(new Item(ARCHERS_RING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == SEERS_RING || usedWith.getId() == SEERS_RING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(SEERS_RING),true);
            player.inventory().add(new Item(SEERS_RING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == WARRIOR_RING || usedWith.getId() == WARRIOR_RING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(WARRIOR_RING),true);
            player.inventory().add(new Item(WARRIOR_RING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == BERSERKER_RING || usedWith.getId() == BERSERKER_RING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(BERSERKER_RING),true);
            player.inventory().add(new Item(BERSERKER_RING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == RING_OF_THE_GODS || usedWith.getId() == RING_OF_THE_GODS)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(RING_OF_THE_GODS),true);
            player.inventory().add(new Item(RING_OF_THE_GODS_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == TYRANNICAL_RING || usedWith.getId() == TYRANNICAL_RING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(TYRANNICAL_RING),true);
            player.inventory().add(new Item(TYRANNICAL_RING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == TREASONOUS_RING || usedWith.getId() == TREASONOUS_RING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(TREASONOUS_RING),true);
            player.inventory().add(new Item(TREASONOUS_RING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == GRANITE_RING || usedWith.getId() == GRANITE_RING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(GRANITE_RING),true);
            player.inventory().add(new Item(GRANITE_RING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == RING_OF_SUFFERING || usedWith.getId() == RING_OF_SUFFERING)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(RING_OF_SUFFERING),true);
            player.inventory().add(new Item(RING_OF_SUFFERING_I),true);
            return true;
        }
        if ((use.getId() == IMBUEMENT_SCROLL || usedWith.getId() == IMBUEMENT_SCROLL) && (use.getId() == RING_OF_WEALTH || usedWith.getId() == RING_OF_WEALTH)) {
            player.inventory().remove(new Item(IMBUEMENT_SCROLL),true);
            player.inventory().remove(new Item(RING_OF_WEALTH),true);
            player.inventory().add(new Item(RING_OF_WEALTH_I),true);
            return true;
        }
        return false;
    }
}
