package com.valinor.game.content.announcements;

import com.valinor.game.content.kill_logs.BossKillLog;
import com.valinor.game.content.kill_logs.SlayerKillLog;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

public class ServerAnnouncements {

    private static final List<Integer> RARE_DROPS = Arrays.asList(
        //Kraken / Cave kraken
        TRIDENT_OF_THE_SEAS,
        TRIDENT_OF_THE_SEAS_FULL,
        KRAKEN_TENTACLE,
        JAR_OF_DIRT,

        //Corporeal beast
        ELYSIAN_SIGIL,
        SPECTRAL_SIGIL,
        ARCANE_SIGIL,

        //Dagannoth Kings
        DRAGON_AXE,
        BERSERKER_RING,
        WARRIOR_RING,
        SEERS_RING,
        ARCHERS_RING,

        //Abyssal Sire
        ABYSSAL_WHIP,
        ABYSSAL_DAGGER,
        ABYSSAL_HEAD,
        BLUDGEON_AXON,
        BLUDGEON_CLAW,
        BLUDGEON_SPINE,
        JAR_OF_MIASMA,
        UNSIRED,

        //Ancient Wyvern, Long-tailed Wyvern, Spitting Wyvern, Taloned Wyvern
        WYVERN_VISAGE,

        //Cerberus
        PRIMORDIAL_CRYSTAL,
        PEGASIAN_CRYSTAL,
        ETERNAL_CRYSTAL,
        SMOULDERING_STONE,

        //Slayer (other) & splitted drops
        IMBUED_HEART,
        ETERNAL_GEM,
        DUST_BATTLESTAFF,
        MIST_BATTLESTAFF,
        DRAGON_PICKAXE,
        DRAGON_BOOTS,
        KURASK_HEAD,
        DRACONIC_VISAGE,
        UNCUT_ONYX,
        BLACK_MASK,
        DRAGON_HARPOON,
        DRAKES_TOOTH,
        DRAKES_CLAW,
        BASILISK_JAW,
        DRAGON_SWORD,

        //Commander Zilyana
        SARADOMIN_SWORD,
        ARMADYL_CROSSBOW,
        SARADOMIN_HILT,
        GODSWORD_SHARD_1,
        GODSWORD_SHARD_2,
        GODSWORD_SHARD_3,
        SARADOMINS_LIGHT,

        //Cyclops
        DRAGON_DEFENDER,

        //Dark beast
        DARK_BOW,

        //Demonic gorilla
        ZENYTE_SHARD,
        BALLISTA_LIMBS,
        BALLISTA_SPRING,
        LIGHT_FRAME,
        HEAVY_FRAME,
        MONKEY_TAIL,

        //General graardor
        BANDOS_CHESTPLATE,
        BANDOS_TASSETS,
        BANDOS_BOOTS,
        BANDOS_HILT,

        //K'ril tsutsaroth
        ZAMORAKIAN_SPEAR,
        STAFF_OF_THE_DEAD,
        ZAMORAK_HILT,
        STEAM_BATTLESTAFF,

        //Kalphite queen
        JAR_OF_SAND,
        KQ_HEAD,
        DRAGON_CHAINBODY_3140,

        //King black dragon
        KBD_HEADS,
        DRACONIC_VISAGE,

        //Kree'arra
        ARMADYL_HELMET,
        ARMADYL_CHESTPLATE,
        ARMADYL_CHAINSKIRT,
        ARMADYL_HILT,

        //Lizardman Shaman
        DRAGON_WARHAMMER,

        //Thermonuclear smoke devil
        OCCULT_NECKLACE, //occult necklace
        SMOKE_BATTLESTAFF,

        //Scorpia
        ODIUM_SHARD_3,
        MALEDICTION_SHARD_3,

        //Crazy Archaeologist
        ODIUM_SHARD_2,
        MALEDICTION_SHARD_2,

        //Chaos fanatic
        ODIUM_SHARD_1,
        MALEDICTION_SHARD_1,

        //Venenatis
        TREASONOUS_RING,

        //Callisto
        TYRANNICAL_RING,

        //Vet'ion
        RING_OF_THE_GODS,

        //Chaos elemental
        ELEMENTAL_BOW,
        KORASI_SWORD,
        DRAGON_2H_SWORD,

        // Vorkath
        DRAGONBONE_NECKLACE,
        VORKATHS_HEAD_21907,
        SKELETAL_VISAGE,
        JAR_OF_DECAY,

        //Zulrah
        TANZANITE_FANG,
        MAGIC_FANG,
        SERPENTINE_VISAGE,
        TANZANITE_MUTAGEN,
        MAGMA_MUTAGEN,
        JAR_OF_SWAMP,

        //Revenants
        CRAWS_BOW,
        THAMMARONS_SCEPTRE,
        VIGGORAS_CHAINMACE,
        AMULET_OF_AVARICE,
        VESTAS_SPEAR,
        VESTAS_LONGSWORD,
        VESTAS_CHAINBODY,
        VESTAS_PLATESKIRT,
        STATIUSS_WARHAMMER,
        STATIUSS_FULL_HELM,
        STATIUSS_PLATEBODY,
        STATIUSS_PLATELEGS,

        ZURIELS_HOOD,
        ZURIELS_ROBE_TOP,
        ZURIELS_ROBE_BOTTOM,
        ZURIELS_STAFF,
        MORRIGANS_COIF,
        MORRIGANS_LEATHER_BODY,
        MORRIGANS_LEATHER_CHAPS,
        MORRIGANS_JAVELIN,
        MORRIGANS_THROWING_AXE,
        ANCIENT_CRYSTAL,
        ANCIENT_EMBLEM,
        ANCIENT_TOTEM,
        ANCIENT_STATUETTE,
        ANCIENT_MEDALLION,
        ANCIENT_EFFIGY,
        ANCIENT_RELIC,

        //Ancient Revenants
        DARK_ANCIENT_EMBLEM,
        DARK_ANCIENT_TOTEM,
        DARK_ANCIENT_STATUETTE,
        DARK_ANCIENT_MEDALLION,
        DARK_ANCIENT_EFFIGY,
        DARK_ANCIENT_RELIC,

        //Rune dragon
        DRAGON_LIMBS,
        DRAGON_METAL_LUMP,

        //Adamant dragon
        DRAGON_METAL_SLICE,

        //Alchemical Hydra
        HYDRA_TAIL,
        HYDRA_LEATHER,
        HYDRAS_FANG,
        HYDRAS_EYE,
        HYDRAS_HEART,
        JAR_OF_CHEMICALS,
        ALCHEMICAL_HYDRA_HEAD,
        ALCHEMICAL_HYDRA_HEADS,

        //Ancient barrelchest
        ANCIENT_WARRIOR_CLAMP,

        //Ancient chaos elemental
        RING_OF_VIGOUR,

        //Ancient king black dragon
        ANCIENT_FACEGAURD,

        //Arachne
        DARK_ARMADYL_HELMET,

        //Artio
        DARK_ARMADYL_CHESTPLATE,

        //Kerberos
        DARK_ARMADYL_CHAINSKIRT,

        //Skorpis
        SAELDOR_SHARD,

        //Barrelchest
        ANCIENT_WARRIOR_SWORD,
        ANCIENT_WARRIOR_MAUL,
        ANCIENT_WARRIOR_AXE,

        //Brutal Lava
        LAVA_DHIDE_COIF,
        LAVA_DHIDE_BODY,
        LAVA_DHIDE_CHAPS,
        LAVA_PARTYHAT,
        INFERNAL_CAPE,

        //Bryopyhta
        BRYOPHYTAS_ESSENCE,

        //Sarachnis
        JAR_OF_EYES,
        SARACHNIS_CUDGEL,

        //The nightmare
        INQUISITORS_MACE,
        INQUISITORS_GREAT_HELM,
        INQUISITORS_HAUBERK,
        INQUISITORS_PLATESKIRT,
        NIGHTMARE_STAFF,
        VOLATILE_ORB,
        HARMONISED_ORB,
        CURSED_ORB,
        ELDRITCH_ORB,
        JAR_OF_DREAMS,

        //Corrupted Nechryarch
        CORRUPTED_BOOTS,

        //Corrupted Hunleff
        CRYSTAL_HELM,
        CRYSTAL_BODY,
        CRYSTAL_LEGS,
        CORRUPTED_RANGER_GAUNTLETS,
        CORRUPTING_STONE,
        BLADE_OF_SAELDOR,
        BOW_OF_FAERDHINEN,
        BLADE_OF_SAELDOR_C,
        BOW_OF_FAERDHINEN_C,

        //Nex
        ANCIENT_HILT,
        NIHIL_HORN,
        ZARYTE_VAMBRACES,
        TORVA_FULL_HELM,
        TORVA_PLATEBODY,
        TORVA_PLATELEGS,
        NIHIL_SHARD,

        //Ancient revenants
        ANCIENT_VESTAS_LONGSWORD,
            ANCIENT_STATIUS_WARHAMMER,

        //Bloodfury hespori
        KEY_OF_DROPS,
        LEGENDARY_MYSTERY_BOX,
        RING_OF_PRECISION,
        RANGING_SCROLL,

        //Infernal spider
        RING_OF_SORCERY,
        INFERNAL_TRIDENT,

        //Fragment of Seren
        RING_OF_MANHUNTING,
        MELEE_SCROLL
    );

    public static void tryBroadcastDrop(Player player, Npc npc, Item item) {
        for (BossKillLog.Bosses boss : BossKillLog.Bosses.values()) {
            if (Arrays.stream(boss.getNpcs()).anyMatch(id -> id == npc.id())) {
                String name = npc.def() == null ? "Unknown" : npc.def().name;
                if (RARE_DROPS.stream().anyMatch(id -> id == item.getId())) {
                    int kc = player.getAttribOr(boss.getKc(), 0);
                    World.getWorld().sendWorldMessage("<col=0052cc>News:" + player.getUsername() + " Killed a " + name + " and received " + Utils.getVowelFormat(item.unnote().name()) + " drop! KC " + kc + "!");
                    Utils.sendDiscordInfoLog("Player " + player.getUsername() + " Killed a " + name + " and received " + Utils.getVowelFormat(item.unnote().name()) + " drop! KC " + kc + "!", "yell_item_drop");
                }
                break;
            }
        }

        for (SlayerKillLog.SlayerMonsters slayerMonster : SlayerKillLog.SlayerMonsters.values()) {
            if (Arrays.stream(slayerMonster.getNpcs()).anyMatch(id -> id == npc.id())) {
                String name = npc.def() == null ? "Unknown" : npc.def().name;
                if (RARE_DROPS.stream().anyMatch(id -> id == item.getId())) {
                    int kc = player.getAttribOr(slayerMonster.getKc(), 0);
                    World.getWorld().sendWorldMessage("<col=0052cc>News:" + player.getUsername() + " Killed a " + name + " and received " + Utils.getVowelFormat(item.unnote().name()) + " drop! KC " + kc + "!");
                    Utils.sendDiscordInfoLog("Player " + player.getUsername() + " Killed a " + name + " and received " + Utils.getVowelFormat(item.unnote().name()) + " drop! KC " + kc + "!", "yell_item_drop");
                }
                break;
            }
        }
    }

}
