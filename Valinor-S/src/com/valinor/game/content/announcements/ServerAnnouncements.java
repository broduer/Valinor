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

public class ServerAnnouncements {

    private static final List<Integer> RARE_DROPS = Arrays.asList(
        11908, //uncharged trident
        11905, //trident of the seas (full)
        12004, //kraken tentacle

        12819, //Elysian sigil
        12923, //Spectral Sigil
        12927, //Arcane sigil

        6739, //dragon axe
        6737, //berserker ring
        6735, //warrior ring
        6731, //seers ring
        6733, //archers ring

        4151, //abyssal whip
        13265, //abyssal dagger
        7979, //abyssal head

        21637, //wyvern visage

        11286, //draconic visage

        13231, //primordial crystal
        13229, //pegasian crystal
        13227, //eternal crystal
        13233, //smouldering stone

        11920, //dragon pickaxe

        20724, //imbued heart
        21270, //eternal gem
        20736, //dust battlestaff
        20730, //mist battlestaff

        11838, //saradomin sword
        11785, //armadyl crossbow
        11814, //saradomin hilt
        11818, //godsword shard 1
        11820, //godsword shard 2
        11822, //godsword shard 3

        12954, //dragon defender

        11235, //dark bow

        19529, //zenyte shard
        19592, //ballista limbs
        19601, //ballista spring
        19586, //light frame
        19589, //heavy frame

        11832, //bandos chestplate
        11834, //bandos tassets
        11836, //bandos boots
        11812, //bandos hilt

        11824, //zamorakian spear
        11791, //staff of the dead
        11816, //zamorak hilt

        12885, //jar of sand
        12647, //kalphite princess

        12653, //prince black dragon

        7978, //kurask head

        11826, //armadyl helmet
        11828, //armadyl chestplate
        11830, //armdayl chainskirt
        11810, //armadyl hilt

        11840, //dragon boots
        13576, //dragon warhammer
        13652, //dragon claws
        12002, //occult necklace
        21918, //dragon limbs
        22103, //dragon metal lump

        13181, //scorpia's offspring

        22111, //dragonbone necklace
        2425, //vorkath's head
        22006, //skeletal visage

        12922, //tanzanite fang
        12932, //magic fang
        12927, //serpentine visage

        12605, //treasonous ring
        13177, //venenatis spiderling
        12601, //ring of gods

        22547, //craw's bow (u)
        22552, //thammaron's sceptre (u)
        22542, //viggora's chainmace (u)
        21804, //ancient crystal
        21807, //ancient emblem
        21810, //ancient totem
        21813, //ancient statuette
        22299, //ancient medallion
        22302, //ancient effigy
        22305, //ancient relic

        20997, //twisted bow
        21015, //dinh's bulwark
        21000 //twisted buckler
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
