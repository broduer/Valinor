package com.valinor.fs;

import com.valinor.game.GameConstants;
import com.valinor.game.world.items.Item;
import com.valinor.io.RSBuffer;
import com.valinor.util.ItemIdentifiers;
import io.netty.buffer.Unpooled;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * Created by Bart Pelle on 10/4/2014.
 */
public class ItemDefinition implements Definition {

    public boolean isNote() {
        return notelink != -1 && noteModel != -1;
    }

    public int resizey;
    public int xan2d;
    public int cost = 1;
    public int inventoryModel;
    public int resizez;
    public short[] recol_s;
    public short[] recol_d;
    public String name = "null";
    public int zoom2d = 2000;
    public int yan2d;
    public int zan2d;
    public int yof2d;
    private boolean stackable;
    public int[] countco;
    public boolean members = false;
    public String[] options = new String[5];
    public String[] ioptions = new String[5];
    public int maleModel0;
    public int maleModel1;
    public short[] retex_s;
    public short[] retex_d;
    public int femaleModel1;
    public int maleModel2;
    public int xof2d;
    public int manhead;
    public int manhead2;
    public int womanhead;
    public int womanhead2;
    public int[] countobj;
    public int femaleModel2;
    public int notelink;
    public int femaleModel0;
    public int resizex;
    public int noteModel;
    public int ambient;
    public int contrast;
    public int team;
    public boolean grandexchange;
    public boolean unprotectable;
    public boolean dummyitem;
    public int placeheld = -1;
    public int pheld14401 = -1;
    public int shiftClickDropType = -1;
    private int op139 = -1;
    private int op140 = -1;

    public int id;

    // our fields: optimized speed so you dont need 1k loops
    public boolean isCrystal;
    public boolean tradeable_special_items;
    public boolean changes;
    public boolean autoKeptOnDeath;

    public ItemDefinition(int id, byte[] data) {
        this.id = id;

        if (data != null && data.length > 0)
            decode(new RSBuffer(Unpooled.wrappedBuffer(data)));
        custom();
    }

    void decode(RSBuffer buffer) {
        while (true) {
            int op = buffer.readUByte();
            if (op == 0)
                break;
            decode(buffer, op);
        }
        postDecode(id);
    }

    void custom() {
        List<Integer> itemsToClear = Arrays.asList(ItemIdentifiers.TOXIC_BLOWPIPE, ItemIdentifiers.SERPENTINE_HELM, ItemIdentifiers.TRIDENT_OF_THE_SWAMP, ItemIdentifiers.TOXIC_STAFF_OF_THE_DEAD, ItemIdentifiers.TOME_OF_FIRE, ItemIdentifiers.SCYTHE_OF_VITUR, ItemIdentifiers.SANGUINESTI_STAFF, ItemIdentifiers.CRAWS_BOW, ItemIdentifiers.VIGGORAS_CHAINMACE, ItemIdentifiers.THAMMARONS_SCEPTRE, ItemIdentifiers.TRIDENT_OF_THE_SEAS, ItemIdentifiers.MAGMA_HELM, ItemIdentifiers.TANZANITE_HELM, ItemIdentifiers.DRAGONFIRE_SHIELD, ItemIdentifiers.DRAGONFIRE_WARD, ItemIdentifiers.ANCIENT_WYVERN_SHIELD, ItemIdentifiers.ABYSSAL_TENTACLE, BARRELCHEST_ANCHOR, ItemIdentifiers.SARADOMINS_BLESSED_SWORD);
        boolean clear_options = itemsToClear.stream().anyMatch(item -> item == id);
        if (clear_options) {
            ioptions = new String[]{null, "Wield", null, null, "Drop"};
        }

        boolean replace_drop_with_destroy = Arrays.stream(Item.AUTO_KEPT_LIST).anyMatch(auto_kept_id -> auto_kept_id == id);
        if (replace_drop_with_destroy) {
            ioptions = new String[]{null, null, null, null, "Destroy"};
        }

        if (name.contains("slayer helmet") || name.contains("Slayer helmet")) {
            ioptions = new String[]{null, "Wear", "Check", "Disassemble", "Drop"};
        }

        //Bounty hunter emblem hardcoding.
        if (id == 12746 || (id >= 12748 && id <= 12756)) {
            unprotectable = true;
        } else if (id == PINK_SWEETS) {
            stackable = true;
        } else if (id == GIANT_KEY_OF_DROPS) {
            name = "Giant key of drops";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == RAIDS_MYSTERY_BOX) {
            name = "Raids mystery box";
            grandexchange = true;
        } else if (id == COLLECTION_KEY) {
            name = "Collection key";
            grandexchange = true;
        } else if (id == CURSED_AMULET_OF_THE_DAMNED) {
            name = "Cursed amulet of the damned";
            grandexchange = true;
            cost = 2_000_000;
        } else if (id == TREASURE_CASKET) {
            name = "Treasure casket";
            grandexchange = true;
            stackable = true;
        } else if (id == SKILLING_SCROLL) {
            name = "Skilling scroll";
            ioptions = new String[]{"Read", null, null, null, "Destroy"};
        } else if (id == PVMING_SCROLL) {
            name = "Pvming scroll";
            ioptions = new String[]{"Read", null, null, null, "Destroy"};
        } else if (id == TASK_BOTTLE_SKILLING) {
            name = "Task bottle (skilling)";
            grandexchange = true;
        } else if (id == TASK_BOTTLE_PVMING) {
            name = "Task bottle (pvming)";
            grandexchange = true;
        } else if (id == POINTS_MYSTERY_BOX) {
            name = "Points mystery box";
            grandexchange = true;
        } else if (id == POINTS_MYSTERY_BOX + 1) {
            name = "Points mystery box";
            grandexchange = true;
            notelink = POINTS_MYSTERY_BOX;
            noteModel = 799;
        } else if (id == BARROWS_MYSTERY_BOX) {
            name = "Barrows mystery box";
            grandexchange = true;
        } else if (id == POINTS_MYSTERY_CHEST) {
            name = "Points mystery chest";
            grandexchange = true;
        } else if (id == DOUBLE_DROPS_SCROLL) {
            name = "Double drop scroll";
            stackable = true;
            grandexchange = true;
        } else if (id == TOTEMIC_HELMET) {
            name = "Totemic helmet";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == TOTEMIC_PLATEBODY) {
            name = "Totemic platebody";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == TOTEMIC_PLATELEGS) {
            name = "Totemic platelegs";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == DARK_SAGE_HAT) {
            name = "Dark sage hat";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == DARK_SAGE_ROBE_TOP) {
            name = "Dark sage robe top";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == DARK_SAGE_ROBE_BOTTOM) {
            name = "Dark sage robe bottoms";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == TWISTED_BOW_ORANGE || id == TWISTED_BOW_PURPLE) {
            name = "Twisted bow";
            cost = 10_000_000;
        } else if (id == SARKIS_DARK_COIF) {
            name = "Sarkis dark coif";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == SARKIS_DARK_BODY) {
            name = "Sarkis dark body";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == SARKIS_DARK_LEGS) {
            name = "Sarkis dark legs";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == RESOURCE_PACK) {
            name = "Resource pack";
        } else if (id == SUMMER_TOKEN) {
            name = "Summer token";
            grandexchange = true;
        } else if (id == AGILITY_MASTER_CAPE) {
            name = "Agility master cape";
        } else if (id == ATTACK_MASTER_CAPE) {
            name = "Attack master cape";
        } else if (id == CONSTRUCCTION_MASTER_CAPE) {
            name = "Construction master cape";
        } else if (id == COOKING_MASTER_CAPE) {
            name = "Cooking master cape";
        } else if (id == CRAFTING_MASTER_CAPE) {
            name = "Crafting master cape";
        } else if (id == DEFENCE_MASTER_CAPE) {
            name = "Defence master cape";
        } else if (id == FARMING_MASTER_CAPE) {
            name = "Farming master cape";
        } else if (id == FIREMAKING_MASTER_CAPE) {
            name = "Firemaking master cape";
        } else if (id == FISHING_MASTER_CAPE) {
            name = "Fishing master cape";
        } else if (id == FLETCHING_MASTER_CAPE) {
            name = "Fletching master cape";
        } else if (id == HERBLORE_MASTER_CAPE) {
            name = "Herblore master cape";
        } else if (id == HITPOINTS_MASTER_CAPE) {
            name = "Hitpoints master cape";
        } else if (id == HUNTER_MASTER_CAPE) {
            name = "Hunter master cape";
        } else if (id == MAGIC_MASTER_CAPE) {
            name = "Magic master cape";
        } else if (id == MINING_MASTER_CAPE) {
            name = "Mining master cape";
        } else if (id == PRAYER_MASTER_CAPE) {
            name = "Prayer master cape";
        } else if (id == RANGE_MASTER_CAPE) {
            name = "Range master cape";
        } else if (id == RUNECRAFTING_MASTER_CAPE) {
            name = "Runecrafting master cape";
        } else if (id == SLAYER_MASTER_CAPE) {
            name = "Slayer master cape";
        } else if (id == SMITHING_MASTER_CAPE) {
            name = "Smithing master cape";
        } else if (id == STRENGTH_MASTER_CAPE) {
            name = "Strength master cape";
        } else if (id == THIEVING_MASTER_CAPE) {
            name = "Thieving master cape";
        } else if (id == WOODCUTTING_MASTER_CAPE) {
            name = "Woodcutting master cape";
        } else if (id == MYSTERY_TICKET) {
            name = "Mystery ticket";
            stackable = true;
            grandexchange = true;
            cost = 5_000_000;
        } else if (id == DARKLORD_BOW) {
            name = "Darklord bow";
            cost = 3_000_000;
        } else if (id == DARKLORD_SWORD) {
            name = "Darklord sword";
            cost = 3_000_000;
        } else if (id == DARKLORD_STAFF) {
            name = "Darklord staff";
            cost = 3_000_000;
        } else if (id == SLAYER_KEY) {
            name = "Slayer key";
            stackable = true;
            grandexchange = true;
        } else if (id == VAMPYRE_DUST) {
            notelink = -1;
            noteModel = -1;
        } else if (id == VETERAN_PARTYHAT) {
            name = "Veteran partyhat";
            grandexchange = true;
            ioptions = new String[]{null, "Wear", null, null, "Destroy"};
        } else if (id == VETERAN_HWEEN_MASK) {
            name = "Veteran halloween mask";
            grandexchange = true;
            ioptions = new String[]{null, "Wear", null, null, "Destroy"};
        } else if (id == VETERAN_SANTA_HAT) {
            name = "Veteran santa hat";
            grandexchange = true;
            ioptions = new String[]{null, "Wear", null, null, "Destroy"};
        } else if (id == BEGINNER_WEAPON_PACK) {
            name = "Beginner weapon pack";
            ioptions = new String[]{"Open", null, null, null, "Destroy"};
        } else if (id == BEGINNER_DRAGON_CLAWS) {
            name = "Beginner dragon claws";
            ioptions = new String[]{null, "Wield", null, null, "Destroy"};
        } else if (id == BEGINNER_AGS) {
            name = "Beginner AGS";
            ioptions = new String[]{null, "Wield", null, null, "Destroy"};
        } else if (id == BEGINNER_CHAINMACE) {
            name = "Beginner chainmace";
            ioptions = new String[]{null, "Wield", null, null, "Destroy"};
        } else if (id == BEGINNER_CRAWS_BOW) {
            name = "Beginner craw's bow";
            ioptions = new String[]{null, "Wield", null, null, "Destroy"};
        } else if (id == ZRIAWK) {
            name = "Zriawk pet";
            grandexchange = true;
        } else if (id == GAMBLER_SCROLL) {
            name = "Gambler scroll";
            grandexchange = true;
        } else if (id == FAWKES_32937) {
            name = "Fawkes pet";
            grandexchange = true;
        } else if (id == RING_OF_VIGOUR) {
            name = "Ring of vigour";
            grandexchange = true;
            cost = 15_000_000;
        } else if (id == DARK_BANDOS_CHESTPLATE) {
            name = "Dark bandos chestplate";
            grandexchange = true;
            cost = 10_000_000;
        } else if (id == DARK_BANDOS_TASSETS) {
            name = "Dark bandos tassets";
            grandexchange = true;
            cost = 10_000_000;
        } else if (id == DARK_ARMADYL_HELMET) {
            name = "Dark armadyl helmet";
            grandexchange = true;
            cost = 10_000_000;
        } else if (id == DARK_ARMADYL_CHESTPLATE) {
            name = "Dark armadyl chestplate";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == DARK_ARMADYL_CHAINSKIRT) {
            name = "Dark armadyl chainskirt";
            grandexchange = true;
            cost = 10_000_000;
        } else if (id == RING_OF_ELYSIAN) {
            name = "Ring of elysian";
            grandexchange = true;
        } else if (id == TOXIC_STAFF_OF_THE_DEAD_C) {
            name = "Toxic staff of the dead (c)";
            grandexchange = true;
            cost = 15_000_000;
        } else if (id == KERBEROS_PET) {
            name = "Kerberos pet";
            grandexchange = true;
        } else if (id == SKORPIOS_PET) {
            name = "Skorpios pet";
            grandexchange = true;
        } else if (id == ARACHNE_PET) {
            name = "Arachne pet";
            grandexchange = true;
        } else if (id == ARTIO_PET) {
            name = "Artio pet";
            grandexchange = true;
        } else if (id == SAELDOR_SHARD) {
            name = "Saeldor shard";
            grandexchange = true;
        } else if (id == CYAN_PARTYHAT) {
            name = "Cyan partyhat";
            grandexchange = true;
        } else if (id == LIME_PARTYHAT) {
            name = "Lime partyhat";
            grandexchange = true;
        } else if (id == ORANGE_PARTYHAT) {
            name = "Orange partyhat";
            grandexchange = true;
        } else if (id == WHITE_HWEEN_MASK) {
            name = "White h'ween mask";
            grandexchange = true;
        } else if (id == PURPLE_HWEEN_MASK) {
            name = "Purple h'ween mask";
            grandexchange = true;
        } else if (id == LIME_GREEN_HWEEN_MASK) {
            name = "Lime green h'ween mask";
            grandexchange = true;
        } else if (id == DARK_ELDER_MAUL) {
            name = "Dark elder maul";
            grandexchange = true;
            cost = 10_000_000;
        } else if (id == SANGUINE_TWISTED_BOW) {
            name = "Sanguine twisted bow";
            grandexchange = true;
            cost = 100_000_000;
        } else if (id == SANGUINE_SCYTHE_OF_VITUR) {
            cost = 100_000_000;
        } else if (id == ANCIENT_FACEGAURD) {
            name = "Ancient facegaurd";
            grandexchange = true;
            cost = 15_000_000;
        } else if (id == ANCIENT_WARRIOR_CLAMP) {
            name = "Ancient warrior clamp";
            grandexchange = true;
            cost = 25_500_000;
        } else if (id == ANCIENT_KING_BLACK_DRAGON_PET) {
            name = "Ancient king black dragon pet";
            grandexchange = true;
        } else if (id == ANCIENT_CHAOS_ELEMENTAL_PET) {
            name = "Ancient chaos elemental pet";
            grandexchange = true;
        } else if (id == ANCIENT_BARRELCHEST_PET) {
            name = "Ancient barrelchest pet";
            grandexchange = true;
        } else if (id == DARK_ANCIENT_EMBLEM) {
            name = "Dark ancient emblem";
        } else if (id == DARK_ANCIENT_TOTEM) {
            name = "Dark ancient totem";
        } else if (id == DARK_ANCIENT_STATUETTE) {
            name = "Dark ancient statuette";
        } else if (id == DARK_ANCIENT_MEDALLION) {
            name = "Dark ancient medallion";
        } else if (id == DARK_ANCIENT_EFFIGY) {
            name = "Dark ancient effigy";
        } else if (id == DARK_ANCIENT_RELIC) {
            name = "Dark ancient relic";
        } else if (id == ANCIENT_VESTAS_LONGSWORD) {
            name = "Ancient vesta's longsword";
            grandexchange = true;
            cost = 30_000_000;
        } else if (id == ANCIENT_STATIUSS_WARHAMMER) {
            name = "Ancient statius's warhammer";
            grandexchange = true;
            cost = 25_000_000;
        } else if (id == TASK_BOTTLE_CASKET) {
            name = "Task bottle casket";
            ioptions = new String[]{"Open", null, null, null, "Drop"};
        } else if (id == BLOOD_FIREBIRD) {
            name = "Blood firebird pet";
            grandexchange = true;
        } else if (id == SHADOW_MACE) {
            name = "Shadow mace";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == SHADOW_GREAT_HELM) {
            name = "Shadow great helm";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == SHADOW_HAUBERK) {
            name = "Shadow hauberk";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == SHADOW_PLATESKIRT) {
            name = "Shadow plateskirt";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == VIGGORAS_CHAINMACE_C) {
            name = "Viggora's chainmace (c)";
            grandexchange = true;
            cost = 25_000_000;
        } else if (id == CRAWS_BOW_C) {
            name = "Craw's bow (c)";
            grandexchange = true;
            cost = 25_000_000;
        } else if (id == THAMMARONS_STAFF_C) {
            name = "Thammaron's sceptre (c)";
            grandexchange = true;
            cost = 5_000_000;
        } else if (id == PEGASIAN_BOOTS_OR) {
            name = "Pegasian boots (or)";
            grandexchange = true;
            cost = 2_000_000;
        } else if (id == ETERNAL_BOOTS_OR) {
            name = "Eternal boots (or)";
            grandexchange = true;
            cost = 2_000_000;
        } else if (id == VALINOR_COINS) {
            name = GameConstants.SERVER_NAME + " coins";
            countco = new int[]{2, 3, 4, 5, 25, 100, 250, 1000, 10000, 0};
            countobj = new int[]{17001, 17002, 17003, 17004, 17005, 17006, 17007, 17008, 17009, 0};
            stackable = true;
            grandexchange = true;
        } else if (id == MYSTERY_CHEST) {
            name = "Mystery chest";
            grandexchange = true;
        } else if (id == IMBUEMENT_SCROLL) {
            name = "Imbuement scroll";
            grandexchange = true;
            ioptions = new String[]{null, null, null, null, "Drop"};
        } else if (id == WILDERNESS_KEY) {
            name = "Wilderness key";
            grandexchange = true;
            ioptions = new String[]{null, null, null, null, "Drop"};
        } else if (id == NIFFLER) {
            name = "Niffler pet";
            grandexchange = true;
        } else if (id == FAWKES) {
            name = "Fawkes pet";
            grandexchange = true;
        } else if (id == VOTE_TICKET) {
            name = "Vote ticket";
            stackable = true;
            grandexchange = true;
            ioptions = new String[]{"Convert to Points", null, null, null, "Drop"};
        } else if (id == DRAGON_CLAWS_OR) {
            name = "Dragon claws (or)";
            grandexchange = true;
            cost = 5_000_000;
        } else if (id == ETHEREAL_PARTYHAT) {
            name = "Ethereal partyhat";
            grandexchange = true;
        } else if (id == ETHEREAL_HWEEN_MASK) {
            name = "Ethereal halloween mask";
            grandexchange = true;
        } else if (id == ETHEREAL_SANTA_HAT) {
            name = "Ethereal santa hat";
            grandexchange = true;
        } else if (id == LAVA_DHIDE_COIF) {
            name = "Lava d'hide coif";
            grandexchange = true;
            cost = 1_000_000;
        } else if (id == LAVA_DHIDE_BODY) {
            name = "Lava d'hide body";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == LAVA_DHIDE_CHAPS) {
            name = "Lava d'hide chaps";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == TWISTED_BOW_I) {
            name = "Twisted bow (i)";
            grandexchange = true;
            cost = 35_000_000;
        } else if (id == ANCESTRAL_HAT_I) {
            name = "Ancestral hat (i)";
            grandexchange = true;
            cost = 30_000_000;
        } else if (id == ANCESTRAL_ROBE_TOP_I) {
            name = "Ancestral robe top (i)";
            grandexchange = true;
            cost = 30_000_000;
        } else if (id == ANCESTRAL_ROBE_BOTTOM_I) {
            name = "Ancestral robe bottom (i)";
            grandexchange = true;
            cost = 30_000_000;
        } else if (id == PRIMORDIAL_BOOTS_OR) {
            name = "Primordial boots (or)";
            grandexchange = true;
            cost = 1_000_000;
        } else if (id == RUNE_POUCH_I) {
            notelink = -1;
            noteModel = -1;
            name = "Rune pouch (i)";
            ioptions = new String[]{"Open", null, null, "Empty", "Destroy"};
        } else if (id == DOUBLE_DROPS_LAMP) {
            name = "Double drops lamp";
            grandexchange = true;
        } else if (id == DONATOR_MYSTERY_BOX) {
            name = "Donator Mystery Box";
            stackable = false;
            grandexchange = true;
        } else if (id == SUPER_MYSTERY_BOX) {
            name = "Super Mystery Box";
            stackable = false;
            grandexchange = true;
        } else if (id == PETS_MYSTERY_BOX) {
            name = "Super Mystery Box";
            stackable = false;
            grandexchange = true;
        } else if (id == ZOMBIES_CHAMPION_PET) {
            name = "Zombies champion pet";
            grandexchange = true;
        } else if (id == EARTH_ARROWS) {
            name = "Earth arrows";
            grandexchange = true;
        } else if (id == FIRE_ARROWS) {
            name = "Fire arrows";
            grandexchange = true;
        } else if (id == ANCIENT_WARRIOR_SWORD) {
            name = "Ancient warrior sword";
            grandexchange = true;
            cost = 50_000_000;
        } else if (id == ANCIENT_WARRIOR_AXE) {
            name = "Ancient warrior axe";
            grandexchange = true;
            cost = 50_000_000;
        } else if (id == ANCIENT_WARRIOR_MAUL) {
            name = "Ancient warrior maul";
            grandexchange = true;
            cost = 50_000_000;
        } else if (id == ANCIENT_WARRIOR_SWORD_C) {
            name = "Ancient warrior sword (c)";
            grandexchange = true;
            cost = 75_000_000;
        } else if (id == ANCIENT_WARRIOR_AXE_C) {
            name = "Ancient warrior axe (c)";
            grandexchange = true;
            cost = 75_000_000;
        } else if (id == ANCIENT_WARRIOR_MAUL_C) {
            name = "Ancient warrior maul (c)";
            grandexchange = true;
            cost = 75_000_000;
        } else if (id == KEY_OF_DROPS) {
            name = "Key of Drops";
            grandexchange = true;
        } else if (id == GENIE_PET) {
            ioptions = new String[]{null, null, null, null, "Drop"};
            name = "Genie pet";
            grandexchange = true;
        } else if (id == GRIM_REAPER_PET) {
            name = "Grim Reaper pet";
            grandexchange = true;
        } else if (id == ELEMENTAL_BOW) {
            name = "Elemental bow";
            grandexchange = true;
            cost = 5_000_000;
        } else if (id == DONATOR_TICKET) {
            name = "Donator ticket";
            stackable = true;
            grandexchange = true;
        } else if (id == FIVE_DOLLAR_BOND) {
            name = "5$ bond";
            grandexchange = true;
        } else if (id == HOME_TELEPORT) {
            name = "Home teleport";
        } else if (id == VENGEANCE_SKULL) {
            name = "Vengeance";
        } else if (id == LAVA_PARTYHAT) {
            stackable = false;
            name = "Lava Party hat";
            grandexchange = true;
        } else if (id == TEN_DOLLAR_BOND) {
            stackable = false;
            name = "$10 bond";
            grandexchange = true;
        } else if (id == TWENTY_DOLLAR_BOND) {
            stackable = false;
            name = "$20 bond";
            grandexchange = true;
        } else if (id == THIRTY_DOLLAR_BOND) {
            stackable = false;
            name = "$30 bond";
            grandexchange = true;
        } else if (id == FORTY_DOLLAR_BOND) {
            stackable = false;
            name = "$40 bond";
            grandexchange = true;
        } else if (id == FIFTY_DOLLAR_BOND) {
            stackable = false;
            name = "$50 bond";
            grandexchange = true;
        } else if (id == SEVENTY_FIVE_DOLLAR_BOND) {
            stackable = false;
            name = "$75 bond";
            grandexchange = true;
        } else if (id == ONE_HUNDRED_DOLLAR_BOND) {
            stackable = false;
            name = "$100 bond";
            grandexchange = true;
        } else if (id == BABY_DARK_BEAST_EGG) {
            stackable = false;
            name = "Baby Dark Beast pet";
            ioptions = new String[]{null, null, null, null, "Drop"};
            grandexchange = true;
        } else if (id == BABY_ABYSSAL_DEMON) {
            stackable = false;
            name = "Baby Abyssal demon pet";
            ioptions = new String[]{null, null, null, null, "Drop"};
            grandexchange = true;
        } else if (id == RING_OF_MANHUNTING) {
            stackable = false;
            name = "Ring of manhunting";
            ioptions = new String[]{null, "Wear", null, null, "Drop"};
            grandexchange = true;
        } else if (id == RING_OF_SORCERY) {
            stackable = false;
            name = "Ring of sorcery";
            ioptions = new String[]{null, "Wear", null, null, "Drop"};
            grandexchange = true;
        } else if (id == RING_OF_PRECISION) {
            stackable = false;
            name = "Ring of precision";
            ioptions = new String[]{null, "Wear", null, null, "Drop"};
            grandexchange = true;
        } else if (id == RING_OF_TRINITY) {
            stackable = false;
            name = "Ring of trinity";
            ioptions = new String[]{null, "Wear", null, null, "Drop"};
            grandexchange = true;
        } else if (id == HWEEN_TOKENS) {
            name = "H'ween token";
            ioptions = new String[]{null, null, null, null, "Drop"};
            grandexchange = true;
        } else if (id == XMAS_TOKENS) {
            name = "X'mas token";
            ioptions = new String[]{null, null, null, null, "Drop"};
            grandexchange = true;
        } else if (id == CORRUPTED_BOOTS) {
            name = "Corrupted boots";
            grandexchange = true;
        } else if (id == SALAZAR_SLYTHERINS_LOCKET) {
            name = "Salazar slytherins locket";
            grandexchange = true;
            cost = 15_000_000;
        } else if (id == FENRIR_GREYBACK_JR) {
            name = "Fenrir greyback Jr. pet";
            grandexchange = true;
        } else if (id == FLUFFY_JR) {
            name = "Fluffy Jr. pet";
            grandexchange = true;
        } else if (id == TALONHAWK_CROSSBOW) {
            name = "Talonhawk crossbow";
            grandexchange = true;
        } else if (id == ELDER_WAND_STICK) {
            name = "Elder wand stick";
            grandexchange = true;
            cost = 15_000_000;
        } else if (id == ELDER_WAND_HANDLE) {
            name = "Elder wand handle";
            grandexchange = true;
            cost = 15_000_000;
        } else if (id == ELDER_WAND) {
            name = "Elder wand";
            grandexchange = true;
            cost = 40_000_000;
        } else if (id == ELDER_WAND_RAIDS) {
            name = "Elder wand (raids)";
        } else if (id == CLOAK_OF_INVISIBILITY) {
            name = "Cloak of invisibility";
            grandexchange = true;
        } else if (id == MARVOLO_GAUNTS_RING) {
            name = "Marvolo Gaunts Ring";
            grandexchange = true;
        } else if (id == TOM_RIDDLE_DIARY) {
            name = "Tom Riddle's Diary";
            grandexchange = true;
        } else if (id == NAGINI) {
            name = "Nagini";
            grandexchange = true;
        } else if (id == SWORD_OF_GRYFFINDOR) {
            name = "Sword of gryffindor";
            grandexchange = true;
            cost = 20_000_000;
        } else if (id == CENTAUR_MALE) {
            name = "Male centaur pet";
            grandexchange = true;
        } else if (id == CENTAUR_FEMALE) {
            name = "Female centaur pet";
            grandexchange = true;
        } else if (id == DEMENTOR_PET) {
            name = "Dementor pet";
            grandexchange = true;
        } else if (id == JALNIBREK) {
            name = "Jal-nib-rek pet";
            grandexchange = true;
        } else if (id == CORRUPTING_STONE) {
            name = "Corrupting stone";
            grandexchange = true;
        } else if (id == CORRUPTED_RANGER_GAUNTLETS) {
            name = "Corrupted ranger gauntlets";
            grandexchange = true;
            cost = 3_000_000;
        } else if (id == BLOOD_REAPER) {
            name = "Blood Reaper pet";
            grandexchange = true;
        } else if (id == YOUNGLLEF) {
            name = "Yougnleff pet";
            grandexchange = true;
        } else if (id == CORRUPTED_YOUNGLLEF) {
            name = "Corrupted yougnleff pet";
            grandexchange = true;
        } else if (id == FOUNDER_IMP) {
            name = "Founder imp pet";
            grandexchange = true;
        } else if (id == PET_CORRUPTED_NECHRYARCH) {
            name = "Corrupted nechryarch pet";
            grandexchange = true;
        } else if (id == MINI_NECROMANCER) {
            name = "Mini necromancer pet";
            grandexchange = true;
        } else if (id == JALTOK_JAD) {
            name = "Jaltok-jad pet";
            grandexchange = true;
        } else if (id == BABY_LAVA_DRAGON) {
            name = "Baby lava dragon pet";
            grandexchange = true;
        } else if (id == JAWA_PET) {
            name = "Jawa pet";
            grandexchange = true;
        } else if (id == BABY_ARAGOG) {
            name = "Baby aragog pet";
            grandexchange = true;
        } else if (id == DHAROK_PET) {
            name = "Dharok pet";
            grandexchange = true;
        } else if (id == TZREKZUK) {
            name = "TzRek-Zuk pet";
            grandexchange = true;
        } else if (id == LITTLE_NIGHTMARE) {
            name = "Little nightmare pet";
            grandexchange = true;
        } else if (id == BABY_SQUIRT) {
            name = "Baby Squirt pet";
            stackable = false;
        } else if (id == PET_PAINT_BLACK) {
            name = "Pet paint (black)";
            grandexchange = true;
        } else if (id == PET_PAINT_WHITE) {
            name = "Pet paint (white)";
            grandexchange = true;
        } else if (id == BARRELCHEST_PET) {
            name = "Barrelchest pet";
            grandexchange = true;
            ioptions = new String[]{null, null, null, null, "Drop"};
            stackable = false;
        } else if (id == WAMPA_PET) {
            name = "Wampa pet";
            grandexchange = true;
            ioptions = new String[]{null, null, null, null, "Drop"};
            stackable = false;
        } else if (id == PET_KREE_ARRA_WHITE) {
            name = "Pet kree'arra (white)";
            stackable = false;
            grandexchange = true;
            ioptions = new String[]{null, null, null, "Wipe-off paint", null};
        } else if (id == PET_ZILYANA_WHITE) {
            name = "Pet zilyana (white)";
            stackable = false;
            grandexchange = true;
            ioptions = new String[]{null, null, null, "Wipe-off paint", null};
        } else if (id == PET_GENERAL_GRAARDOR_BLACK) {
            name = "Pet general graardor (black)";
            stackable = false;
            grandexchange = true;
            ioptions = new String[]{null, null, null, "Wipe-off paint", null};
        } else if (id == PET_KRIL_TSUTSAROTH_BLACK) {
            name = "Pet k'ril tsutsaroth (black)";
            stackable = false;
            grandexchange = true;
            ioptions = new String[]{null, null, null, "Wipe-off paint", null};
        } else if (id == GUTHANS_ARMOUR_SET || id == VERACS_ARMOUR_SET || id == DHAROKS_ARMOUR_SET || id == TORAGS_ARMOUR_SET || id == AHRIMS_ARMOUR_SET || id == KARILS_ARMOUR_SET) {
            ioptions = new String[5];
            ioptions[0] = "Open";
        } else if (id == MAGMA_BLOWPIPE) {
            name = "Magma blowpipe";
            ioptions = new String[]{null, "Wield", null, null, "Drop"};
        } else if (id == ItemIdentifiers.ATTACKER_ICON || id == ItemIdentifiers.COLLECTOR_ICON || id == ItemIdentifiers.DEFENDER_ICON || id == ItemIdentifiers.HEALER_ICON) {
            ioptions = new String[]{null, "Wear", null, null, "Destroy"};
            stackable = false;
        }
    }

    void decode(RSBuffer buffer, int code) {
        if (code == 1) {
            inventoryModel = buffer.readUShort();
        } else if (code == 2) {
            name = buffer.readString();
        } else if (code == 4) {
            zoom2d = buffer.readUShort();
        } else if (code == 5) {
            xan2d = buffer.readUShort();
        } else if (code == 6) {
            yan2d = buffer.readUShort();
        } else if (code == 7) {
            xof2d = buffer.readUShort();
            if (xof2d > 0x7FFF) {
                xof2d -= 0x10000;
            }
        } else if (code == 8) {
            yof2d = buffer.readUShort();
            if (yof2d > 0x7FFF) {
                yof2d -= 0x10000;
            }
        } else if (code == 11) {
            stackable = true;
        } else if (code == 12) {
            cost = buffer.readInt();
        } else if (code == 16) {
            members = true;
        } else if (code == 23) {
            maleModel0 = buffer.readUShort();
            buffer.readByte();
        } else if (code == 24) {
            maleModel1 = buffer.readUShort();
        } else if (code == 25) {
            femaleModel0 = buffer.readUShort();
            buffer.readByte();
        } else if (code == 26) {
            femaleModel1 = buffer.readUShort();
        } else if (code >= 30 && code < 35) {
            options[code - 30] = buffer.readString();
            if (options[code - 30].equalsIgnoreCase("null")) {
                options[code - 30] = null;
            }
        } else if (code >= 35 && code < 40) {
            ioptions[code - 35] = buffer.readString();
        } else if (code == 40) {
            int num = buffer.readUByte();
            recol_s = new short[num];
            recol_d = new short[num];

            for (int var4 = 0; var4 < num; ++var4) {
                recol_s[var4] = (short) buffer.readUShort();
                recol_d[var4] = (short) buffer.readUShort();
            }
        } else if (code == 41) {
            int num = buffer.readUByte();
            retex_s = new short[num];
            retex_d = new short[num];

            for (int var4 = 0; var4 < num; ++var4) {
                retex_s[var4] = (short) buffer.readUShort();
                retex_d[var4] = (short) buffer.readUShort();
            }
        } else if (code == 42) {
            shiftClickDropType = buffer.readByte();
        } else if (code == 65) {
            grandexchange = true;
        } else if (code == 78) {
            maleModel2 = buffer.readUShort();
        } else if (code == 79) {
            femaleModel2 = buffer.readUShort();
        } else if (code == 90) {
            manhead = buffer.readUShort();
        } else if (code == 91) {
            womanhead = buffer.readUShort();
        } else if (code == 92) {
            manhead2 = buffer.readUShort();
        } else if (code == 93) {
            womanhead2 = buffer.readUShort();
        } else if (code == 94) {
            buffer.readUShort();
        } else if (code == 95) {
            zan2d = buffer.readUShort();
        } else if (code == 97) {
            notelink = buffer.readUShort();
        } else if (code == 98) {
            noteModel = buffer.readUShort();
        } else if (code >= 100 && code < 110) {
            if (countobj == null) {
                countobj = new int[10];
                countco = new int[10];
            }

            countobj[code - 100] = buffer.readUShort();
            countco[code - 100] = buffer.readUShort();
        } else if (code == 110) {
            resizex = buffer.readUShort();
        } else if (code == 111) {
            resizey = buffer.readUShort();
        } else if (code == 112) {
            resizez = buffer.readUShort();
        } else if (code == 113) {
            ambient = buffer.readByte();
        } else if (code == 114) {
            contrast = buffer.readByte() * 5;
        } else if (code == 115) {
            team = buffer.readUByte();
        } else if (code == 139) {
            op139 = buffer.readShort();
        } else if (code == 140) {
            op140 = buffer.readShort();
        } else if (code == 148) {
            placeheld = buffer.readUShort();
        } else if (code == 149) {
            pheld14401 = buffer.readUShort();
        } else if (code == 249) {
            int length = buffer.readUByte();
            int index;
            if (clientScriptData == null) {
                index = method32(length);
                clientScriptData = new HashMap<>(index);
            }
            for (index = 0; index < length; index++) {
                boolean stringData = buffer.readUByte() == 1;
                int key = buffer.readTriByte();
                clientScriptData.put(key, stringData ? buffer.readString() : buffer.readInt());
            }
        } else {
            throw new RuntimeException("cannot parse item definition, missing config code: " + code);
        }
    }

    void postDecode(int id) {
        if (id == 6808) {
            name = "Scroll of Imbuement";
        }
    }

    public int highAlchValue() {
        return (int) (cost * 0.65);
    }

    public static int method32(int var0) {
        --var0;
        var0 |= var0 >>> 1;
        var0 |= var0 >>> 2;
        var0 |= var0 >>> 4;
        var0 |= var0 >>> 8;
        var0 |= var0 >>> 16;
        return var0 + 1;
    }

    public Map<Integer, Object> clientScriptData;

    public boolean stackable() {
        return stackable || noteModel > 0 || id == 13215 || id == 30235 || id == 30191;
    }

    public boolean noted() {
        return noteModel > 0;
    }

    @Override
    public String toString() {
        return "ItemDefinition{" +
            "resizey=" + resizey +
            ", xan2d=" + xan2d +
            ", cost=" + cost +
            ", inventoryModel=" + inventoryModel +
            ", resizez=" + resizez +
            ", recol_s=" + Arrays.toString(recol_s) +
            ", recol_d=" + Arrays.toString(recol_d) +
            ", name='" + name + '\'' +
            ", zoom2d=" + zoom2d +
            ", yan2d=" + yan2d +
            ", zan2d=" + zan2d +
            ", yof2d=" + yof2d +
            ", stackable=" + stackable +
            ", countco=" + Arrays.toString(countco) +
            ", members=" + members +
            ", options=" + Arrays.toString(options) +
            ", ioptions=" + Arrays.toString(ioptions) +
            ", maleModel0=" + maleModel0 +
            ", maleModel1=" + maleModel1 +
            ", retex_s=" + Arrays.toString(retex_s) +
            ", retex_d=" + Arrays.toString(retex_d) +
            ", femaleModel1=" + femaleModel1 +
            ", maleModel2=" + maleModel2 +
            ", xof2d=" + xof2d +
            ", manhead=" + manhead +
            ", manhead2=" + manhead2 +
            ", womanhead=" + womanhead +
            ", womanhead2=" + womanhead2 +
            ", countobj=" + Arrays.toString(countobj) +
            ", femaleModel2=" + femaleModel2 +
            ", notelink=" + notelink +
            ", femaleModel0=" + femaleModel0 +
            ", resizex=" + resizex +
            ", noteModel=" + noteModel +
            ", ambient=" + ambient +
            ", contrast=" + contrast +
            ", team=" + team +
            ", grandexchange=" + grandexchange +
            ", unprotectable=" + unprotectable +
            ", dummyitem=" + dummyitem +
            ", placeheld=" + placeheld +
            ", pheld14401=" + pheld14401 +
            ", shiftClickDropType=" + shiftClickDropType +
            ", op139=" + op139 +
            ", op140=" + op140 +
            ", id=" + id +
            ", clientScriptData=" + clientScriptData +
            '}';
    }
}
