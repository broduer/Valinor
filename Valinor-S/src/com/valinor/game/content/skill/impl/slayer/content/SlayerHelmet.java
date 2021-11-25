package com.valinor.game.content.skill.impl.slayer.content;

import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.ItemIdentifiers;

/**
 * This class represents the Slayer interface functionality
 *
 * @author Patrick van Elderen | 23 jan. 2017 : 19:32:54
 * @see <a href="https://www.rune-server.ee/members/_Patrick_/">Rune-Server profile</a>
 */
public class SlayerHelmet extends PacketInteraction {

    private static final int NOSE_PEG = ItemIdentifiers.NOSE_PEG;
    private static final int FACEMASK = ItemIdentifiers.FACEMASK;
    private static final int GEM = ItemIdentifiers.ENCHANTED_GEM;
    private static final int EARMUFFS = ItemIdentifiers.EARMUFFS;
    private static final int BLACK_MASK = ItemIdentifiers.BLACK_MASK;
    private static final int BLACK_MASK_IMBUE = ItemIdentifiers.BLACK_MASK_I;
    private static final int SPINY_HELM = ItemIdentifiers.SPINY_HELMET;
    private static final int SLAYER_HELM = ItemIdentifiers.SLAYER_HELMET;
    private static final int SLAYER_HELM_IMBUE = ItemIdentifiers.SLAYER_HELMET_I;

    public static int RED_SLAYER_HELM = ItemIdentifiers.RED_SLAYER_HELMET;
    private static final int RED_HELM_IMBUE = ItemIdentifiers.RED_SLAYER_HELMET_I;
    public static int GREEN_SLAYER_HELM = ItemIdentifiers.GREEN_SLAYER_HELMET;
    private static final int GREEN_HELM_IMBUE = ItemIdentifiers.GREEN_SLAYER_HELMET_I;
    public static int BLACK_SLAYER_HELM = ItemIdentifiers.BLACK_SLAYER_HELMET;
    private static final int BLACK_HELM_IMBUE = ItemIdentifiers.BLACK_SLAYER_HELMET_I;
    public static int PURPLE_SLAYER_HELM = ItemIdentifiers.PURPLE_SLAYER_HELMET;
    public static int PURPLE_HELM_IMBUE = ItemIdentifiers.PURPLE_SLAYER_HELMET_I;
    private static final int TURQUOISE_SLAYER_HELM = ItemIdentifiers.TURQUOISE_SLAYER_HELMET;
    private static final int TURQUOISE_HELM_IMBUE = ItemIdentifiers.TURQUOISE_SLAYER_HELMET_I;
    private static final int HYDRA_SLAYER_HELM = ItemIdentifiers.HYDRA_SLAYER_HELMET;
    private static final int HYDRA_SLAYER_HELM_IMBUE = ItemIdentifiers.HYDRA_SLAYER_HELMET_I;
    private static final int TWISTED_SLAYER_HELMET = ItemIdentifiers.TWISTED_SLAYER_HELMET;
    private static final int TWISTED_SLAYER_HELMET_IMBUE = ItemIdentifiers.TWISTED_SLAYER_HELMET_I;

    private static final int ABYSSAL_HEAD = ItemIdentifiers.ABYSSAL_HEAD;
    private static final int KQ_HEAD = ItemIdentifiers.KQ_HEAD;
    private static final int KBD_HEADS = ItemIdentifiers.KBD_HEADS;
    private static final int DARK_CLAW = ItemIdentifiers.DARK_CLAW;
    private static final int VORKATH_HEAD = ItemIdentifiers.VORKATHS_HEAD_21907;
    private static final int HYDRA_HEAD = ItemIdentifiers.ALCHEMICAL_HYDRA_HEAD;
    private static final int TWISTED_HORNS = ItemIdentifiers.TWISTED_HORNS;

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item used, Item with) {
        // Combinations
        if ((used.getId() == NOSE_PEG && with.getId() == FACEMASK) || (used.getId() == FACEMASK && with.getId() == NOSE_PEG)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == NOSE_PEG && with.getId() == GEM) || (used.getId() == GEM && with.getId() == NOSE_PEG)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == NOSE_PEG && with.getId() == EARMUFFS) || (used.getId() == EARMUFFS && with.getId() == NOSE_PEG)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == NOSE_PEG && with.getId() == BLACK_MASK) || (used.getId() == BLACK_MASK && with.getId() == NOSE_PEG)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == NOSE_PEG && with.getId() == BLACK_MASK_IMBUE) || (used.getId() == BLACK_MASK_IMBUE && with.getId() == NOSE_PEG)) {
            makeMask(player, true);
            return true;
        }
        if ((used.getId() == NOSE_PEG && with.getId() == SPINY_HELM) || (used.getId() == SPINY_HELM && with.getId() == NOSE_PEG)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == FACEMASK && with.getId() == GEM) || (used.getId() == GEM && with.getId() == FACEMASK)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == FACEMASK && with.getId() == EARMUFFS) || (used.getId() == EARMUFFS && with.getId() == FACEMASK)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == FACEMASK && with.getId() == BLACK_MASK) || (used.getId() == BLACK_MASK && with.getId() == FACEMASK)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == FACEMASK && with.getId() == BLACK_MASK_IMBUE) || (used.getId() == BLACK_MASK_IMBUE && with.getId() == FACEMASK)) {
            makeMask(player, true);
            return true;
        }
        if ((used.getId() == FACEMASK && with.getId() == SPINY_HELM) || (used.getId() == SPINY_HELM && with.getId() == FACEMASK)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == GEM && with.getId() == EARMUFFS) || (used.getId() == EARMUFFS && with.getId() == GEM)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == GEM && with.getId() == BLACK_MASK) || (used.getId() == BLACK_MASK && with.getId() == GEM)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == GEM && with.getId() == BLACK_MASK_IMBUE) || (used.getId() == BLACK_MASK_IMBUE && with.getId() == GEM)) {
            makeMask(player, true);
            return true;
        }
        if ((used.getId() == GEM && with.getId() == SPINY_HELM) || (used.getId() == SPINY_HELM && with.getId() == GEM)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == EARMUFFS && with.getId() == BLACK_MASK) || (used.getId() == BLACK_MASK && with.getId() == EARMUFFS)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == EARMUFFS && with.getId() == BLACK_MASK_IMBUE) || (used.getId() == BLACK_MASK_IMBUE && with.getId() == EARMUFFS)) {
            makeMask(player, true);
            return true;
        }
        if ((used.getId() == EARMUFFS && with.getId() == SPINY_HELM) || (used.getId() == SPINY_HELM && with.getId() == EARMUFFS)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == SPINY_HELM && with.getId() == BLACK_MASK) || (used.getId() == BLACK_MASK && with.getId() == SPINY_HELM)) {
            makeMask(player);
            return true;
        }
        if ((used.getId() == SPINY_HELM && with.getId() == BLACK_MASK_IMBUE) || (used.getId() == BLACK_MASK_IMBUE && with.getId() == SPINY_HELM)) {
            makeMask(player, true);
            return true;
        }

        // Colorizing
        if ((used.getId() == ABYSSAL_HEAD && with.getId() == SLAYER_HELM) || (used.getId() == SLAYER_HELM && with.getId() == ABYSSAL_HEAD)) {
            player.getInventory().remove(new Item(ABYSSAL_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM), true);
            player.getInventory().add(new Item(RED_SLAYER_HELM), true);
            return true;
        }

        if ((used.getId() == ABYSSAL_HEAD && with.getId() == SLAYER_HELM_IMBUE) || (used.getId() == SLAYER_HELM_IMBUE && with.getId() == ABYSSAL_HEAD)) {
            player.getInventory().remove(new Item(ABYSSAL_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM_IMBUE), true);
            player.getInventory().add(new Item(RED_HELM_IMBUE), true);
            return true;
        }

        if ((used.getId() == KBD_HEADS && with.getId() == SLAYER_HELM) || (used.getId() == SLAYER_HELM && with.getId() == KBD_HEADS)) {
            player.getInventory().remove(new Item(KBD_HEADS), true);
            player.getInventory().remove(new Item(SLAYER_HELM), true);
            player.getInventory().add(new Item(BLACK_SLAYER_HELM), true);
            return true;
        }

        if ((used.getId() == KBD_HEADS && with.getId() == SLAYER_HELM_IMBUE) || (used.getId() == SLAYER_HELM_IMBUE && with.getId() == KBD_HEADS)) {
            player.getInventory().remove(new Item(KBD_HEADS), true);
            player.getInventory().remove(new Item(SLAYER_HELM_IMBUE), true);
            player.getInventory().add(new Item(BLACK_HELM_IMBUE), true);
            return true;
        }

        if ((used.getId() == KQ_HEAD && with.getId() == SLAYER_HELM) || (used.getId() == SLAYER_HELM && with.getId() == KQ_HEAD)) {
            player.getInventory().remove(new Item(KQ_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM), true);
            player.getInventory().add(new Item(GREEN_SLAYER_HELM), true);
            return true;
        }

        if ((used.getId() == KQ_HEAD && with.getId() == SLAYER_HELM_IMBUE) || (used.getId() == SLAYER_HELM_IMBUE && with.getId() == KQ_HEAD)) {
            player.getInventory().remove(new Item(KQ_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM_IMBUE), true);
            player.getInventory().add(new Item(GREEN_HELM_IMBUE), true);
            return true;
        }

        if ((used.getId() == DARK_CLAW && with.getId() == SLAYER_HELM) || (used.getId() == SLAYER_HELM && with.getId() == DARK_CLAW)) {
            player.getInventory().remove(new Item(DARK_CLAW), true);
            player.getInventory().remove(new Item(SLAYER_HELM), true);
            player.getInventory().add(new Item(PURPLE_SLAYER_HELM), true);
            return true;
        }

        if ((used.getId() == DARK_CLAW && with.getId() == SLAYER_HELM_IMBUE) || (used.getId() == SLAYER_HELM_IMBUE && with.getId() == DARK_CLAW)) {
            player.getInventory().remove(new Item(DARK_CLAW), true);
            player.getInventory().remove(new Item(SLAYER_HELM_IMBUE), true);
            player.getInventory().add(new Item(PURPLE_HELM_IMBUE), true);
            return true;
        }

        if ((used.getId() == VORKATH_HEAD && with.getId() == SLAYER_HELM) || (used.getId() == SLAYER_HELM && with.getId() == VORKATH_HEAD)) {
            player.getInventory().remove(new Item(VORKATH_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM), true);
            player.getInventory().add(new Item(TURQUOISE_SLAYER_HELM), true);
            return true;
        }

        if ((used.getId() == VORKATH_HEAD && with.getId() == SLAYER_HELM_IMBUE) || (used.getId() == SLAYER_HELM_IMBUE && with.getId() == VORKATH_HEAD)) {
            player.getInventory().remove(new Item(VORKATH_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM_IMBUE), true);
            player.getInventory().add(new Item(TURQUOISE_HELM_IMBUE), true);
            return true;
        }

        if ((used.getId() == HYDRA_HEAD && with.getId() == SLAYER_HELM) || (used.getId() == SLAYER_HELM && with.getId() == HYDRA_HEAD)) {
            player.getInventory().remove(new Item(HYDRA_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM), true);
            player.getInventory().add(new Item(HYDRA_SLAYER_HELM), true);
            return true;
        }

        if ((used.getId() == HYDRA_HEAD && with.getId() == SLAYER_HELM_IMBUE) || (used.getId() == SLAYER_HELM_IMBUE && with.getId() == HYDRA_HEAD)) {
            player.getInventory().remove(new Item(HYDRA_HEAD), true);
            player.getInventory().remove(new Item(SLAYER_HELM_IMBUE), true);
            player.getInventory().add(new Item(HYDRA_SLAYER_HELM_IMBUE), true);
            return true;
        }

        if ((used.getId() == TWISTED_HORNS && with.getId() == SLAYER_HELM) || (used.getId() == SLAYER_HELM && with.getId() == TWISTED_HORNS)) {
            player.getInventory().remove(new Item(TWISTED_HORNS), true);
            player.getInventory().remove(new Item(SLAYER_HELM), true);
            player.getInventory().add(new Item(TWISTED_SLAYER_HELMET), true);
            return true;
        }

        if ((used.getId() == TWISTED_HORNS && with.getId() == SLAYER_HELM_IMBUE) || (used.getId() == SLAYER_HELM_IMBUE && with.getId() == TWISTED_HORNS)) {
            player.getInventory().remove(new Item(TWISTED_HORNS), true);
            player.getInventory().remove(new Item(SLAYER_HELM_IMBUE), true);
            player.getInventory().add(new Item(TWISTED_SLAYER_HELMET_IMBUE), true);
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 2) {
            // Disassembly
            if (item.getId() == SLAYER_HELM) {
                disassemble(player, new Item(SLAYER_HELM));
                return true;
            }

            if (item.getId() == SLAYER_HELM_IMBUE) {
                disassemble(player, new Item(SLAYER_HELM_IMBUE), null, true);
                return true;
            }

            if (item.getId() == RED_SLAYER_HELM) {
                disassemble(player, new Item(RED_SLAYER_HELM), new Item(ABYSSAL_HEAD));
                return true;
            }

            if (item.getId() == RED_HELM_IMBUE) {
                disassemble(player, new Item(RED_HELM_IMBUE), new Item(ABYSSAL_HEAD), true);
                return true;
            }

            if (item.getId() == GREEN_SLAYER_HELM) {
                disassemble(player, new Item(GREEN_SLAYER_HELM), new Item(KQ_HEAD));
                return true;
            }

            if (item.getId() == GREEN_HELM_IMBUE) {
                disassemble(player, new Item(GREEN_HELM_IMBUE), new Item(KQ_HEAD), true);
                return true;
            }

            if (item.getId() == BLACK_SLAYER_HELM) {
                disassemble(player, new Item(BLACK_SLAYER_HELM), new Item(KBD_HEADS));
                return true;
            }

            if (item.getId() == BLACK_HELM_IMBUE) {
                disassemble(player, new Item(BLACK_HELM_IMBUE), new Item(KBD_HEADS), true);
                return true;
            }

            if (item.getId() == PURPLE_SLAYER_HELM) {
                disassemble(player, new Item(PURPLE_SLAYER_HELM), new Item(DARK_CLAW));
                return true;
            }

            if (item.getId() == PURPLE_HELM_IMBUE) {
                disassemble(player, new Item(PURPLE_HELM_IMBUE), new Item(DARK_CLAW), true);
                return true;
            }
        }
        return false;
    }

    private void disassemble(Player player, Item remove) {
        disassemble(player, remove, null, false);
    }

    private void disassemble(Player player, Item remove, Item additional) {
        disassemble(player, remove, additional, false);
    }

    private void disassemble(Player player, Item remove, Item additional, boolean imbued) {
        int free_slots = additional == null ? 5 : 6;
        if (player.getInventory().getFreeSlots() >= free_slots) {
            // Delete helm
            player.getInventory().remove(new Item(remove), true);

            // Add components
            player.getInventory().add(new Item(NOSE_PEG), true);
            player.getInventory().add(new Item(FACEMASK), true);
            player.getInventory().add(new Item(GEM), true);
            player.getInventory().add(new Item(EARMUFFS), true);
            player.getInventory().add(new Item(imbued ? BLACK_MASK_IMBUE : BLACK_MASK), true);
            player.getInventory().add(new Item(SPINY_HELM), true);

            if (additional != null) {
                player.getInventory().add(additional, true);
            }

            player.message("You disassemble your Slayer helm.");
        } else {
            player.message("You don't have space to disassemble that item.");
        }
    }

    private void makeMask(Player player) {
        makeMask(player, false);
    }

    private void makeMask(Player player, boolean imbued) {
        // Got the skill unlocked?
        if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.MALEVOLENT_MASQUERADE)) {
            player.message("You have not unlocked the ability to combine these items.");
            return;
        }

        if (!player.getInventory().containsAll(NOSE_PEG, FACEMASK, GEM, EARMUFFS, imbued ? BLACK_MASK_IMBUE : BLACK_MASK, SPINY_HELM)) {
            player.message("You need a nosepeg, facemask, earmuffs, spiny helmet, enchanted gem and a black mask in your inventory in order to construct a Slayer helm.");
        } else {
            if (player.skills().level(Skills.CRAFTING) < 55) {
                player.message("You need a Crafting level of 55 to make a Slayer helm.");
            } else {
                player.getInventory().remove(new Item(NOSE_PEG), true);
                player.getInventory().remove(new Item(FACEMASK), true);
                player.getInventory().remove(new Item(GEM), true);
                player.getInventory().remove(new Item(EARMUFFS), true);
                player.getInventory().remove(new Item(SPINY_HELM), true);
                player.getInventory().remove(new Item(imbued ? BLACK_MASK_IMBUE : BLACK_MASK), true);
                player.getInventory().add(new Item(imbued ? SLAYER_HELM_IMBUE : SLAYER_HELM), true);

                player.message("You combine the items into a Slayer helm.");
            }
        }
    }

}
