package com.valinor.game.content.items.combine;

import com.valinor.game.content.duel.Dueling;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | March, 16, 2021, 14:43
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ItemCombining extends Interaction {

    private enum Combine {
        BRIMSTONE_BOOTS(BOOTS_OF_BRIMSTONE, BOOTS_OF_STONE, DRAKES_CLAW),

        DEVOUT_BOOTS(ItemIdentifiers.DEVOUT_BOOTS, DRAKES_TOOTH, HOLY_SANDALS),

        BLOOD_FURY(AMULET_OF_BLOOD_FURY, AMULET_OF_FURY, BLOOD_SHARD, true),

        KODAI_WAND(ItemIdentifiers.KODAI_WAND, KODAI_INSIGNIA, MASTER_WAND),

        DRAG_CHAIN_G(DRAGON_CHAINBODY_G, DRAGON_CHAINBODY_3140, DRAGON_CHAINBODY_ORNAMENT_KIT, true),

        DRAG_LEGS_G(DRAGON_PLATELEGS_G, DRAGON_PLATELEGS, DRAGON_LEGSSKIRT_ORNAMENT_KIT, true),

        DRAG_SKIRT_G(DRAGON_PLATESKIRT_G, DRAGON_PLATESKIRT, DRAGON_LEGSSKIRT_ORNAMENT_KIT, true),

        DRAG_FULLHELM_G(DRAGON_FULL_HELM_G, DRAGON_FULL_HELM, DRAGON_FULL_HELM_ORNAMENT_KIT, true),

        DRAG_SQ_G(DRAGON_SQ_SHIELD_G, DRAGON_SQ_SHIELD, DRAGON_SQ_SHIELD_ORNAMENT_KIT, true),

        AGS(ARMADYL_GODSWORD_OR, ARMADYL_GODSWORD, ARMADYL_GODSWORD_ORNAMENT_KIT, true),

        BGS(BANDOS_GODSWORD_OR, BANDOS_GODSWORD, BANDOS_GODSWORD_ORNAMENT_KIT, true),

        SGS(SARADOMIN_GODSWORD_OR, SARADOMIN_GODSWORD, SARADOMIN_GODSWORD_ORNAMENT_KIT, true),

        ZGS(ZAMORAK_GODSWORD_OR, ZAMORAK_GODSWORD, ZAMORAK_GODSWORD_ORNAMENT_KIT, true),

        TORTURE(AMULET_OF_TORTURE_OR, AMULET_OF_TORTURE, TORTURE_ORNAMENT_KIT, true),

        DSCIM(DRAGON_SCIMITAR_OR, DRAGON_SCIMITAR, DRAGON_SCIMITAR_ORNAMENT_KIT, true),

        FURY(AMULET_OF_FURY_OR, AMULET_OF_FURY, FURY_ORNAMENT_KIT, true),

        OCCULT(OCCULT_NECKLACE_OR, OCCULT_NECKLACE, OCCULT_ORNAMENT_KIT, true),

        GRANITE_CLAMP(GRANITE_MAUL_12848, GRANITE_MAUL_24225, ItemIdentifiers.GRANITE_CLAMP, true),

        ODIUM_ORN(ODIUM_WARD_12807, ODIUM_WARD, WARD_UPGRADE_KIT, true),

        MALEDICTION_ORN(MALEDICTION_WARD_12806, MALEDICTION_WARD, WARD_UPGRADE_KIT, true),

        STEAM_STAFF_ORN(MYSTIC_STEAM_STAFF_12796, MYSTIC_STEAM_STAFF, STEAM_STAFF_UPGRADE_KIT, true),

        FROZEN_WHIP(FROZEN_ABYSSAL_WHIP, ABYSSAL_WHIP, FROZEN_WHIP_MIX, true),

        VOLCANIC_WHIP(VOLCANIC_ABYSSAL_WHIP, ABYSSAL_WHIP, VOLCANIC_WHIP_MIX, true),

        BLUE_DARKBOW(DARK_BOW_12765, DARK_BOW, BLUE_DARK_BOW_PAINT, true),

        GREEN_DARKBOW(DARK_BOW_12766, DARK_BOW, GREEN_DARK_BOW_PAINT, true),

        YELLOW_DARKBOW(DARK_BOW_12767, DARK_BOW, YELLOW_DARK_BOW_PAINT, true),

        WHITE_DARKBOW(DARK_BOW_12768, DARK_BOW, WHITE_DARK_BOW_PAINT, true),

        LIGHT_INFINITY_HAT(ItemIdentifiers.LIGHT_INFINITY_HAT, INFINITY_HAT, LIGHT_INFINITY_COLOUR_KIT, true),

        LIGHT_INFINITY_TOP(ItemIdentifiers.LIGHT_INFINITY_TOP, INFINITY_TOP, LIGHT_INFINITY_COLOUR_KIT, true),

        LIGHT_INFINITY_SKIRT(ItemIdentifiers.LIGHT_INFINITY_BOTTOMS, INFINITY_BOTTOMS, LIGHT_INFINITY_COLOUR_KIT, true),

        DARK_INFINITY_HAT(ItemIdentifiers.DARK_INFINITY_HAT, INFINITY_HAT, DARK_INFINITY_COLOUR_KIT, true),

        DARK_INFINITY_TOP(ItemIdentifiers.DARK_INFINITY_TOP, INFINITY_TOP, DARK_INFINITY_COLOUR_KIT, true),

        DARK_INFINITY_SKIRT(DARK_INFINITY_BOTTOMS, INFINITY_BOTTOMS, DARK_INFINITY_COLOUR_KIT, true),

        MAGMA_HELMET(MAGMA_HELM, SERPENTINE_HELM, MAGMA_MUTAGEN),

        TANZANITE_HELMET(TANZANITE_HELM, SERPENTINE_HELM, TANZANITE_MUTAGEN),

        SARADOMINS_BLESSED_SWORD(ItemIdentifiers.SARADOMINS_BLESSED_SWORD, SARADOMINS_TEAR,SARADOMIN_SWORD, true),

        DRAGON_PLATEBODY(DRAGON_PLATEBODY_G, ItemIdentifiers.DRAGON_PLATEBODY, DRAGON_PLATEBODY_ORNAMENT_KIT, true),

        DRAGON_KITESHIELD(DRAGON_KITESHIELD_G, ItemIdentifiers.DRAGON_KITESHIELD, DRAGON_KITESHIELD_ORNAMENT_KIT, true),

        DRAGON_BOOTS(DRAGON_BOOTS_G, ItemIdentifiers.DRAGON_BOOTS, DRAGON_BOOTS_ORNAMENT_KIT, true),

        ANGUISH(NECKLACE_OF_ANGUISH_OR, NECKLACE_OF_ANGUISH, ANGUISH_ORNAMENT_KIT, true),

        STAFF_OF_LIGHT(ItemIdentifiers.STAFF_OF_LIGHT, STAFF_OF_THE_DEAD,SARADOMINS_LIGHT, true),

        R_SCIM_GUTHIX(RUNE_SCIMITAR_23330, RUNE_SCIMITAR, RUNE_SCIMITAR_ORNAMENT_KIT_GUTHIX, true),

        R_SCIM_SARADOMIN(RUNE_SCIMITAR_23332, RUNE_SCIMITAR, RUNE_SCIMITAR_ORNAMENT_KIT_SARADOMIN, true),

        R_SCIM_ZAMORAK(RUNE_SCIMITAR_23334, RUNE_SCIMITAR, RUNE_SCIMITAR_ORNAMENT_KIT_ZAMORAK, true),

        RUNE_DEFENDER_T(ItemIdentifiers.RUNE_DEFENDER_T, RUNE_DEFENDER, RUNE_DEFENDER_ORNAMENT_KIT, true),

        DRAGON_DEFENDER_T(ItemIdentifiers.DRAGON_DEFENDER_T, ItemIdentifiers.DRAGON_DEFENDER, DRAGON_DEFENDER_ORNAMENT_KIT, true),

        TZHAAR_KET_OM_T(TZHAARKETOM_T, TZHAARKETOM, TZHAARKETOM_ORNAMENT_KIT, true),

        BERSERKER_NECKLACE_OR(ItemIdentifiers.BERSERKER_NECKLACE_OR, BERSERKER_NECKLACE, BERSERKER_NECKLACE_ORNAMENT_KIT, true),

        TORMENTED_BRACELET_OR(ItemIdentifiers.TORMENTED_BRACELET_OR, TORMENTED_BRACELET, TORMENTED_ORNAMENT_KIT, true),

        TWISTED_ANCESTRAL_HAT(ItemIdentifiers.TWISTED_ANCESTRAL_HAT, ANCESTRAL_HAT, TWISTED_ANCESTRAL_COLOUR_KIT, true),

        TWISTED_ANCESTRAL_ROBE_TOP(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_TOP, ANCESTRAL_ROBE_TOP, TWISTED_ANCESTRAL_COLOUR_KIT, true),

        TWISTED_ANCESTRAL_ROBE_BOTTOM(ItemIdentifiers.TWISTED_ANCESTRAL_ROBE_BOTTOM, ANCESTRAL_ROBE_BOTTOM, TWISTED_ANCESTRAL_COLOUR_KIT, true),

        HOLY_GHRAZI_RAPIER(ItemIdentifiers.HOLY_GHRAZI_RAPIER, GHRAZI_RAPIER, HOLY_ORNAMENT_KIT, true),

        HOLY_SANGUINESTI_STAFF(ItemIdentifiers.HOLY_SANGUINESTI_STAFF, SANGUINESTI_STAFF, HOLY_ORNAMENT_KIT, true),

        HOLY_SCYTHE_OF_VITUR(ItemIdentifiers.HOLY_SCYTHE_OF_VITUR, SCYTHE_OF_VITUR, HOLY_ORNAMENT_KIT, true),

        SANGUINE_SCYTHE_OF_VITUR(ItemIdentifiers.SANGUINE_SCYTHE_OF_VITUR, SCYTHE_OF_VITUR, SANGUINE_ORNAMENT_KIT, true),

        SANGUINE_TWISTED_BOW(CustomItemIdentifiers.SANGUINE_TWISTED_BOW, TWISTED_BOW, SANGUINE_ORNAMENT_KIT, true),

        GRACEFUL_HOOD(GRACEFUL_HOOD_24743, ItemIdentifiers.GRACEFUL_HOOD, DARK_DYE),

        GRACEFUL_TOP(GRACEFUL_TOP_24749, ItemIdentifiers.GRACEFUL_TOP, DARK_DYE),

        GRACEFUL_LEGS(GRACEFUL_LEGS_24752, ItemIdentifiers.GRACEFUL_LEGS, DARK_DYE),

        GRACEFUL_GLOVES(GRACEFUL_GLOVES_24755, ItemIdentifiers.GRACEFUL_GLOVES, DARK_DYE),

        GRACEFUL_BOOTS(GRACEFUL_BOOTS_24758, ItemIdentifiers.GRACEFUL_BOOTS, DARK_DYE),

        GRACEFUL_CAPE(GRACEFUL_CAPE_24746, ItemIdentifiers.GRACEFUL_CAPE, DARK_DYE),

        GRACEFUL_HOOD_TRAILBLAZER(GRACEFUL_HOOD_25069, ItemIdentifiers.GRACEFUL_HOOD, TRAILBLAZER_GRACEFUL_ORNAMENT_KIT),

        GRACEFUL_TOP_TRAILBLAZER(GRACEFUL_TOP_25075, ItemIdentifiers.GRACEFUL_TOP, TRAILBLAZER_GRACEFUL_ORNAMENT_KIT),

        GRACEFUL_LEGS_TRAILBLAZER(GRACEFUL_LEGS_25078, ItemIdentifiers.GRACEFUL_LEGS, TRAILBLAZER_GRACEFUL_ORNAMENT_KIT),

        GRACEFUL_GLOVES_TRAILBLAZER(GRACEFUL_GLOVES_25081, ItemIdentifiers.GRACEFUL_GLOVES, TRAILBLAZER_GRACEFUL_ORNAMENT_KIT),

        GRACEFUL_BOOTS_TRAILBLAZER(GRACEFUL_BOOTS_25084, ItemIdentifiers.GRACEFUL_BOOTS, TRAILBLAZER_GRACEFUL_ORNAMENT_KIT),

        GRACEFUL_CAPE_TRAILBLAZER(GRACEFUL_CAPE_25072, ItemIdentifiers.GRACEFUL_CAPE, TRAILBLAZER_GRACEFUL_ORNAMENT_KIT),

        DRAGON_AXE_TRAILBLAZER(DRAGON_AXE_OR, DRAGON_AXE, TRAILBLAZER_TOOL_ORNAMENT_KIT, true),

        INFERNAL_AXE_TRAILBLAZER(INFERNAL_AXE_OR, INFERNAL_AXE, TRAILBLAZER_TOOL_ORNAMENT_KIT, true),

        DRAGON_HARPOON_TRAILBLAZER(DRAGON_HARPOON_OR, DRAGON_HARPOON, TRAILBLAZER_TOOL_ORNAMENT_KIT, true),

        INFERNAL_HARPOON_TRAILBLAZER(INFERNAL_HARPOON_OR, INFERNAL_HARPOON, TRAILBLAZER_TOOL_ORNAMENT_KIT, true),

        DRAGON_PICKAXE_TRAILBLAZER(DRAGON_PICKAXE_OR_25376, ItemIdentifiers.DRAGON_PICKAXE, TRAILBLAZER_TOOL_ORNAMENT_KIT, true),

        INFERNAL_PICKAXE_TRAILBLAZER(INFERNAL_PICKAXE_OR, INFERNAL_PICKAXE, TRAILBLAZER_TOOL_ORNAMENT_KIT, true),

        DRAGON_PICKAXE_ZALCANO(DRAGON_PICKAXE_OR, ItemIdentifiers.DRAGON_PICKAXE, ZALCANO_SHARD, true),

        DRAGON_PICKAXE(DRAGON_PICKAXE_12797, ItemIdentifiers.DRAGON_PICKAXE, DRAGON_PICKAXE_UPGRADE_KIT, true),

        STEAM_STAFF(STEAM_BATTLESTAFF_12795, STEAM_BATTLESTAFF, STEAM_STAFF_UPGRADE_KIT, true),

        LAVA_STAFF(LAVA_BATTLESTAFF_21198, LAVA_BATTLESTAFF, LAVA_STAFF_UPGRADE_KIT, true),

        DRAGON_HUNTER_CROSSBOW_B(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW_B, DRAGON_HUNTER_CROSSBOW, KBD_HEADS, true),

        DRAGON_HUNTER_CROSSBOW_T(ItemIdentifiers.DRAGON_HUNTER_CROSSBOW_T, DRAGON_HUNTER_CROSSBOW, VORKATHS_HEAD_21907, true),

        CRYSTAL_OF_IORWERTH_CROWN(CRYSTAL_CROWN_23913, CRYSTAL_CROWN, CRYSTAL_OF_IORWERTH),

        CRYSTAL_OF_IORWERTH_BLADE(BLADE_OF_SAELDOR_C_25872, BLADE_OF_SAELDOR_C, CRYSTAL_OF_IORWERTH),

        CRYSTAL_OF_IORWERTH_BOW(BOW_OF_FAERDHINEN_C_25886, BOW_OF_FAERDHINEN_C_25884, CRYSTAL_OF_IORWERTH),

        CRYSTAL_OF_TRAHAEARN_CROWN(ItemIdentifiers.CRYSTAL_CROWN_23915, CRYSTAL_CROWN, CRYSTAL_OF_TRAHAEARN),

        CRYSTAL_OF_TRAHAEARN_BLADE(ItemIdentifiers.BLADE_OF_SAELDOR_C_25874, BLADE_OF_SAELDOR_C, CRYSTAL_OF_TRAHAEARN),

        CRYSTAL_OF_TRAHAEARN_BOW(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25888, BOW_OF_FAERDHINEN_C_25884, CRYSTAL_OF_TRAHAEARN),

        CRYSTAL_OF_CADARN_CROWN(ItemIdentifiers.CRYSTAL_CROWN_23917, CRYSTAL_CROWN, CRYSTAL_OF_CADARN),

        CRYSTAL_OF_CADARN_BLADE(ItemIdentifiers.BLADE_OF_SAELDOR_C_25876, BLADE_OF_SAELDOR_C, CRYSTAL_OF_CADARN),

        CRYSTAL_OF_CADARN_BOW(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25890, BOW_OF_FAERDHINEN_C_25884, CRYSTAL_OF_CADARN),

        CRYSTAL_OF_CRWYS_CROWN(ItemIdentifiers.CRYSTAL_CROWN_23919, CRYSTAL_CROWN, CRYSTAL_OF_CRWYS),

        CRYSTAL_OF_CRWYS_BLADE(ItemIdentifiers.BLADE_OF_SAELDOR_C_25878, BLADE_OF_SAELDOR_C, CRYSTAL_OF_CRWYS),

        CRYSTAL_OF_CRWYS_BOW(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25892, BOW_OF_FAERDHINEN_C_25884, CRYSTAL_OF_CRWYS),

        CRYSTAL_OF_MEILYR_CROWN(ItemIdentifiers.CRYSTAL_CROWN_23921, CRYSTAL_CROWN, CRYSTAL_OF_MEILYR),

        CRYSTAL_OF_MEILYR_BLADE(ItemIdentifiers.BLADE_OF_SAELDOR_C_25880, BLADE_OF_SAELDOR_C, CRYSTAL_OF_MEILYR),

        CRYSTAL_OF_MEILYR_BOW(ItemIdentifiers.BOW_OF_FAERDHINEN_C_25894, BOW_OF_FAERDHINEN_C_25884, CRYSTAL_OF_MEILYR),

        CRYSTAL_OF_HEFIN_CROWN(ItemIdentifiers.CRYSTAL_CROWN_23923, CRYSTAL_CROWN, CRYSTAL_OF_HEFIN),

        CRYSTAL_OF_HEFIN_BLADE(ItemIdentifiers.BLADE_OF_SAELDOR_C, BLADE_OF_SAELDOR_C, CRYSTAL_OF_HEFIN),

        CRYSTAL_OF_HEFIN_BOW(ItemIdentifiers.BOW_OF_FAERDHINEN_C, BOW_OF_FAERDHINEN_C_25884, CRYSTAL_OF_HEFIN),

        CRYSTAL_OF_AMLODD_CROWN(ItemIdentifiers.CRYSTAL_CROWN_23925, CRYSTAL_CROWN, CRYSTAL_OF_AMLODD),

        CRYSTAL_OF_AMLODD_BLADE(ItemIdentifiers.BLADE_OF_SAELDOR_C_25882, BLADE_OF_SAELDOR_C, CRYSTAL_OF_AMLODD),

        CRYSTAL_OF_AMLODDL_BOW(BOW_OF_FAERDHINEN_C_25896, BOW_OF_FAERDHINEN_C_25884, CRYSTAL_OF_AMLODD),

        AVERNIC_DEFENDER(ItemIdentifiers.AVERNIC_DEFENDER, AVERNIC_DEFENDER_HILT, DRAGON_DEFENDER);

        private final int result;
        private final int item1;
        private final int item2;
        private boolean revert;

        Combine(int result, int item1, int item2) {
            this.result = result;
            this.item1 = item1;
            this.item2 = item2;
        }

        Combine(int result,int item1, int item2, boolean revert) {
            this.result = result;
            this.item1 = item1;
            this.item2 = item2;
            this.revert = revert;
        }
    }

    @Override
    public boolean handleItemOnItemInteraction(Player player, Item use, Item usedWith) {
        for (Combine combine : Combine.values()) {
            if ((use.getId() == combine.item1 || usedWith.getId() == combine.item1) && (use.getId() == combine.item2 || usedWith.getId() == combine.item2)) {
                if (Dueling.screen_closed(player)) {
                    if (player.inventory().contains(combine.item1) && player.inventory().contains(combine.item2)) {
                        player.inventory().remove(combine.item1);
                        player.inventory().remove(combine.item2);
                        player.inventory().addOrBank(new Item(combine.result));
                    } else {
                        player.message("You don't have the required supplies to do this.");
                    }
                }
                return true;
            }
        }

        if ((use.getId() == VORKATHS_HEAD_21907 || usedWith.getId() == VORKATHS_HEAD_21907) && (use.getId() == AVAS_ACCUMULATOR || usedWith.getId() == AVAS_ACCUMULATOR)) {
            combineAssembler(player);
            return true;
        }

        if ((use.getId() == SAELDOR_SHARD || usedWith.getId() == SAELDOR_SHARD) && (use.getId() == BLADE_OF_SAELDOR || usedWith.getId() == BLADE_OF_SAELDOR)) {
            combineBladeOfSaeldor(player);
            return true;
        }

        if ((use.getId() == BANDOS_BOOTS || usedWith.getId() == BANDOS_BOOTS) && (use.getId() == BLACK_TOURMALINE_CORE || usedWith.getId() == BLACK_TOURMALINE_CORE)) {
            combineGuardianBoots(player);
            return true;
        }
        List<Integer> dkite_products = Arrays.asList(DRAGON_METAL_SHARD, DRAGON_METAL_SLICE);
        for (int id : dkite_products) {
            if ((use.getId() == id || usedWith.getId() == id) && (use.getId() == DRAGON_SQ_SHIELD || usedWith.getId() == DRAGON_SQ_SHIELD)) {
                createDragonKiteshield(player);
                return true;
            }
        }

        List<Integer> dplate_products = Arrays.asList(DRAGON_METAL_SHARD, DRAGON_METAL_LUMP);
        for (int id : dplate_products) {
            if ((use.getId() == id || usedWith.getId() == id) && (use.getId() == DRAGON_CHAINBODY || usedWith.getId() == DRAGON_CHAINBODY)) {
                createDragonPlatebody(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 1) {
            if (item.getId() == VORKATHS_HEAD_21907) {
                player.message("This blue dragon smells like it's been dead for a remarkably long time. Even by my...");
                player.message("standards, it smells awful.");
                return true;
            }

            if (item.getId() == AVERNIC_DEFENDER_HILT) {
                player.message("You raise the hilt, inspecting each section carefully. It looks as though it could...");
                player.message("combine with a powerful parrying dagger.");
                return true;
            }
        }

        if(option == 2) {
            for (Combine combine : Combine.values()) {
                if (item.getId() == combine.result) {
                    if (player.inventory().contains(combine.result)) {
                        player.inventory().remove(combine.result);
                        player.inventory().addOrBank(new Item(combine.item1));
                        player.inventory().addOrBank(new Item(combine.item2));
                    }
                    return true;
                }
            }
        }

        if (option == 4) {
            for (Combine combine : Combine.values()) {
                if (item.getId() == combine.result) {
                    if (combine.revert) {
                        player.getDialogueManager().start(new Dialogue() {
                            @Override
                            protected void start(Object... parameters) {
                                send(DialogueType.ITEM_STATEMENT, new Item(combine.result), "<col=7f0000>Warning!</col>", "When converting this item you will lose the kit.");
                                setPhase(0);
                            }

                            @Override
                            protected void next() {
                                if (isPhase(0)) {
                                    send(DialogueType.OPTION, "Proceed with reverting?", "Yes.", "No.");
                                    setPhase(1);
                                }
                            }

                            @Override
                            protected void select(int option) {
                                if (isPhase(1)) {
                                    if (option == 1) {
                                        if (player.inventory().contains(combine.result)) {
                                            player.inventory().remove(combine.result);
                                            player.inventory().addOrBank(new Item(combine.item2));
                                        }
                                        stop();
                                    } else if (option == 2) {
                                        stop();
                                    }
                                }
                            }
                        });
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private void combineAssembler(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Create Ava's Assembler?", "Yes, sacrifice Vorkath's head.", "No, not right now.");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        if (!player.inventory().containsAll(VORKATHS_HEAD_21907, AVAS_ACCUMULATOR)) {
                            stop();
                            return;
                        }
                        player.inventory().remove(VORKATHS_HEAD_21907);
                        player.inventory().remove(AVAS_ACCUMULATOR);
                        player.inventory().addOrBank(new Item(AVAS_ASSEMBLER));
                        player.message("You carefully attach the Vorkath's head to the device and create the assembler.");
                        stop();
                    } else if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }

    private void createDragonPlatebody(Player player) {
        if (!player.inventory().containsAny(DRAGON_METAL_SHARD, DRAGON_METAL_LUMP, DRAGON_CHAINBODY)) {
            player.message("You're missing some of the items to create the dragon platebody.");
        }
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Create the Dragon Platebody?", "Yes", "No");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(1)) {
                    stop();
                }
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        if (!player.inventory().containsAll(DRAGON_CHAINBODY, DRAGON_METAL_LUMP, DRAGON_METAL_SHARD)) {
                            stop();
                            return;
                        }
                        player.inventory().remove(DRAGON_CHAINBODY);
                        player.inventory().remove(DRAGON_METAL_LUMP);
                        player.inventory().remove(DRAGON_METAL_SHARD);
                        player.inventory().addOrBank(new Item(DRAGON_PLATEBODY));
                        send(DialogueType.ITEM_STATEMENT, new Item(DRAGON_PLATEBODY), "", "You combine the shard, lump and chainbody to create a Dragon Platebody.");
                        setPhase(1);
                    } else if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }

    private void createDragonKiteshield(Player player) {
        if (!player.inventory().containsAny(DRAGON_METAL_SHARD, DRAGON_METAL_SLICE, DRAGON_SQ_SHIELD)) {
            player.message("You're missing some of the items to create the dragon kiteshield.");
        }
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Create the Dragon Kiteshield?", "Yes", "No");
                setPhase(0);
            }

            @Override
            protected void next() {
                if (isPhase(1)) {
                    stop();
                }
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        if (!player.inventory().containsAll(DRAGON_SQ_SHIELD, DRAGON_METAL_SLICE, DRAGON_METAL_SHARD)) {
                            stop();
                            return;
                        }
                        player.inventory().remove(DRAGON_SQ_SHIELD);
                        player.inventory().remove(DRAGON_METAL_SLICE);
                        player.inventory().remove(DRAGON_METAL_SHARD);
                        player.inventory().addOrBank(new Item(DRAGON_KITESHIELD));
                        send(DialogueType.ITEM_STATEMENT, new Item(DRAGON_KITESHIELD), "", "You combine the shard, slice and square shield to create a Dragon Kiteshield.");
                        setPhase(1);
                    } else if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }

    private void combineGuardianBoots(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Create Guardian Boots?", "Yes, I'd like to merge the core usedWith the boots.", "No, not right now.");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        if (!player.inventory().containsAll(BANDOS_BOOTS, BLACK_TOURMALINE_CORE)) {
                            stop();
                            return;
                        }
                        player.inventory().remove(BANDOS_BOOTS);
                        player.inventory().remove(BLACK_TOURMALINE_CORE);
                        player.inventory().addOrBank(new Item(GUARDIAN_BOOTS));
                        player.message("You merge the black tourmaline core usedWith the boots to create a pair of guardian boots.");
                        stop();
                    } else if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }

    private void combineBladeOfSaeldor(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Upgrade the Blade of saeldor?", "Yes, sacrifice the shards.", "No, not right now.");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        if (!player.inventory().containsAll(new Item(SAELDOR_SHARD, 500),new Item(BLADE_OF_SAELDOR))){
                            player.message("You did not have enough shards, 500 shards are required to upgrade your blade.");
                            stop();
                            return;
                        }
                        player.inventory().remove(SAELDOR_SHARD, 500);
                        player.inventory().remove(BLADE_OF_SAELDOR);
                        player.inventory().addOrBank(new Item(BLADE_OF_SAELDOR_C_25870));
                        player.message("You carefully attach the shards to the blade to give it an additional boost.");
                        stop();
                    } else if (option == 2) {
                        stop();
                    }
                }
            }
        });
    }

}
