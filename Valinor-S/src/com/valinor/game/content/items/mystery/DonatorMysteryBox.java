package com.valinor.game.content.items.mystery;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Utils;

import static com.valinor.util.CustomItemIdentifiers.DONATOR_MYSTERY_BOX;
import static com.valinor.util.ItemIdentifiers.*;

public class DonatorMysteryBox extends PacketInteraction {

    private static final int EXTREME_ROLL = 33;
    private static final int RARE_ROLL = 20;
    private static final int UNCOMMON_ROLL = 14;

    private static final Item[] EXTREMELY_RARE = new Item[]{
        new Item(GREEN_HALLOWEEN_MASK),
        new Item(BLUE_HALLOWEEN_MASK),
        new Item(RED_HALLOWEEN_MASK),
        new Item(BLACK_HWEEN_MASK),
        new Item(SANTA_HAT),
        new Item(BLACK_SANTA_HAT),
        new Item(INVERTED_SANTA_HAT),
        new Item(RED_PARTYHAT),
        new Item(YELLOW_PARTYHAT),
        new Item(BLUE_PARTYHAT),
        new Item(GREEN_PARTYHAT),
        new Item(PURPLE_PARTYHAT),
        new Item(WHITE_PARTYHAT),
        new Item(BLACK_PARTYHAT),
        new Item(RAINBOW_PARTYHAT),
        new Item(PARTYHAT__SPECS),
        new Item(CHRISTMAS_CRACKER),
        new Item(_3RD_AGE_WAND),
        new Item(_3RD_AGE_BOW),
        new Item(_3RD_AGE_LONGSWORD),
        new Item(_3RD_AGE_CLOAK),
        new Item(_3RD_AGE_RANGE_TOP),
        new Item(_3RD_AGE_RANGE_LEGS),
        new Item(_3RD_AGE_RANGE_COIF),
        new Item(_3RD_AGE_VAMBRACES),
        new Item(_3RD_AGE_ROBE_TOP),
        new Item(_3RD_AGE_ROBE),
        new Item(_3RD_AGE_MAGE_HAT),
        new Item(_3RD_AGE_AMULET),
        new Item(_3RD_AGE_PLATELEGS),
        new Item(_3RD_AGE_PLATEBODY),
        new Item(_3RD_AGE_FULL_HELMET),
        new Item(_3RD_AGE_KITESHIELD),
    };

    private static final Item[] RARE = new Item[]{
        new Item(ARCANE_SPIRIT_SHIELD),
        new Item(SPECTRAL_SPIRIT_SHIELD),
        new Item(FEROCIOUS_GLOVES),
        new Item(DRAGON_HUNTER_LANCE),
        new Item(ARMADYL_CROSSBOW),
        new Item(DRAGON_WARHAMMER),
        new Item(ARMADYL_CROSSBOW),
        new Item(TOXIC_STAFF_OF_THE_DEAD),
        new Item(STAFF_OF_THE_DEAD),
        new Item(SERPENTINE_HELM),
        new Item(ETERNAL_BOOTS),
        new Item(PEGASIAN_BOOTS),
        new Item(PRIMORDIAL_BOOTS),
        new Item(ARMADYL_CHESTPLATE),
        new Item(ARMADYL_CHAINSKIRT),
        new Item(ARMADYL_HELMET),
        new Item(BANDOS_TASSETS),
        new Item(BANDOS_CHESTPLATE),
        new Item(ARMADYL_GODSWORD),
        new Item(ZAMORAK_GODSWORD),
        new Item(SARADOMIN_GODSWORD),
        new Item(BANDOS_GODSWORD),
    };

    private static final Item[] UNCOMMON = new Item[]{
        new Item(BERSERKER_RING),
        new Item(ABYSSAL_DAGGER_P_13271),
        new Item(RUNE_POUCH),
        new Item(ROBIN_HOOD_HAT),
        new Item(RANGERS_TUNIC),
        new Item(BRIMSTONE_RING),
        new Item(LAVA_DRAGON_MASK),
        new Item(DRAGONFIRE_SHIELD),
        new Item(NEW_CRYSTAL_SHIELD),
        new Item(BLESSED_SPIRIT_SHIELD),
        new Item(ODIUM_WARD),
        new Item(MALEDICTION_WARD),
        new Item(WARRIOR_RING),
        new Item(ARCHERS_RING),
        new Item(SEERS_RING),
        new Item(BERSERKER_RING_I),
        new Item(WARRIOR_RING_I),
        new Item(ARCHERS_RING_I),
        new Item(SEERS_RING_I),
        new Item(OCCULT_NECKLACE),
        new Item(AMULET_OF_FURY),
        new Item(SCYTHE),
        new Item(BLACKSMITHS_HELM),
        new Item(BUCKET_HELM),
        new Item(BUCKET_HELM_G),
        new Item(OBSIDIAN_CAPE_R),
    };

    private static final Item[] COMMON = new Item[]{
        new Item(AHRIMS_HOOD),
        new Item(AHRIMS_ROBETOP),
        new Item(AHRIMS_ROBESKIRT),
        new Item(DHAROKS_HELM),
        new Item(DHAROKS_PLATEBODY),
        new Item(DHAROKS_PLATELEGS),
        new Item(DHAROKS_GREATAXE),
        new Item(KARILS_LEATHERTOP),
        new Item(ANGLERFISH+1, 100),
        new Item(DARK_BOW),
        new Item(BANDANA_EYEPATCH_8927),
        new Item(BLACK_CAVALIER),
        new Item(BLUE_HEADBAND),
        new Item(RANGER_BOOTS),
        new Item(GUTHIX_HALO),
        new Item(AFRO),
        new Item(BEANIE),
        new Item(ZAMORAK_HALO),
        new Item(SARADOMIN_HALO),
        new Item(DECORATIVE_ARMOUR_11899),
        new Item(DECORATIVE_ARMOUR_11900),
        new Item(DECORATIVE_ARMOUR_11898),
        new Item(DECORATIVE_ARMOUR_11896),
        new Item(DECORATIVE_ARMOUR_11897),
        new Item(BLACK_CANE),
        new Item(ADAMANT_CANE),
        new Item(IRON_DRAGON_MASK),
        new Item(STEEL_DRAGON_MASK),
        new Item(MITHRIL_DRAGON_MASK),
        new Item(GREEN_DRAGON_MASK),
        new Item(RED_DRAGON_MASK),
        new Item(BLACK_DRAGON_MASK),
        new Item(SPIRIT_SHIELD),
        new Item(INFINITY_GLOVES),
        new Item(INFINITY_HAT),
        new Item(INFINITY_TOP),
        new Item(INFINITY_BOTTOMS),
        new Item(TZHAARKETOM),
        new Item(TOKTZXILEK),
        new Item(ABYSSAL_WHIP),
        new Item(GRANITE_MAUL_24225),
        new Item(INFINITY_BOOTS),
        new Item(BERSERKER_NECKLACE),
        new Item(SUPER_COMBAT_POTION4+1, 250),
        new Item(ANGLERFISH+1, 250),
        new Item(SUPER_COMBAT_POTION4+1, 500),
        new Item(ANGLERFISH+1, 500),
        new Item(BUNNY_EARS),
        new Item(FLIPPERS),
        new Item(RUBBER_CHICKEN),
        new Item(BUNNY_FEET),
        new Item(ABYSSAL_TENTACLE),
        new Item(MUDSKIPPER_HAT),
        new Item(COW_MASK),
        new Item(COW_TOP),
        new Item(COW_TROUSERS),
        new Item(COW_GLOVES),
        new Item(COW_SHOES),
        new Item(RUNE_CANE),
        new Item(DRAGON_CANE),
        new Item(BRONZE_DRAGON_MASK),
        new Item(ELDER_CHAOS_TOP),
        new Item(ELDER_CHAOS_ROBE),
        new Item(ELDER_CHAOS_HOOD),
        new Item(SUPER_COMBAT_POTION4+1, 1000),
        new Item(ANGLERFISH+1, 1000),
        new Item(SUPER_COMBAT_POTION4+1, 1500),
        new Item(ANGLERFISH+1, 1500),
        new Item(SUPER_COMBAT_POTION4+1, 5000),
        new Item(ANGLERFISH+1, 5000),
        new Item(FANCY_TIARA),
        new Item(BOWL_WIG),
        new Item(LESSER_DEMON_MASK),
        new Item(GREATER_DEMON_MASK),
        new Item(BLACK_DEMON_MASK),
        new Item(OLD_DEMON_MASK),
        new Item(JUNGLE_DEMON_MASK)
    };

    private boolean rare = false;

    public Item rollReward() {
        if (Utils.rollDie(EXTREME_ROLL, 1)) {
            rare = true;
            return Utils.randomElement(EXTREMELY_RARE);
        } else if (Utils.rollDie(RARE_ROLL, 1)) {
            rare = true;
            return Utils.randomElement(RARE);
        } else if (Utils.rollDie(UNCOMMON_ROLL, 1)) {
            return Utils.randomElement(UNCOMMON);
        } else {
            return Utils.randomElement(COMMON);
        }
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == DONATOR_MYSTERY_BOX) {
                if(player.inventory().contains(DONATOR_MYSTERY_BOX)) {
                    player.inventory().remove(DONATOR_MYSTERY_BOX);
                    Item reward = rollReward();
                    if(rare) {
                        World.getWorld().sendWorldMessage("<img=1081><col=0052cc>" + player.getUsername() + " just received " + Utils.getVowelFormat(reward.unnote().name()) + " from a donator mystery box!");
                    }
                    player.inventory().addOrBank(reward);
                    rare = false;

                    var amt = reward.getAmount();
                    player.message("You open the donator mystery box and found...");
                    player.message("x"+amt+" "+reward.unnote().name()+".");
                }
                return true;
            }
        }
        return false;
    }
}
