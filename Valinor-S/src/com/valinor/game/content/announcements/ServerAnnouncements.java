package com.valinor.game.content.announcements;

import com.valinor.game.content.kill_logs.BossKillLog;
import com.valinor.game.content.kill_logs.SlayerKillLog;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import java.util.Arrays;

public class ServerAnnouncements {

    public static void tryBroadcastDrop(Player player, Npc npc, Item item) {
        for (BossKillLog.Bosses boss : BossKillLog.Bosses.values()) {
            if (Arrays.stream(boss.getNpcs()).anyMatch(id -> id == npc.id())) {
                String name = npc.def() == null ? "Unknown" : npc.def().name;
                if (item.getValue() > 30_000) {
                    int kc = player.getAttribOr(boss.getKc(), 0);
                    World.getWorld().sendWorldMessage("<col=0052cc>" + player.getUsername() + " Killed a " + name + " and received " + Utils.getVowelFormat(item.unnote().name()) + " drop! KC " + kc + "!");
                }
                break;
            }
        }

        for (SlayerKillLog.SlayerMonsters slayerMonster : SlayerKillLog.SlayerMonsters.values()) {
            if (Arrays.stream(slayerMonster.getNpcs()).anyMatch(id -> id == npc.id())) {
                String name = npc.def() == null ? "Unknown" : npc.def().name;
                if (item.getValue() > 30_000) {
                    int kc = player.getAttribOr(slayerMonster.getKc(), 0);
                    World.getWorld().sendWorldMessage("<col=0052cc>" + player.getUsername() + " Killed a " + name + " and received " + Utils.getVowelFormat(item.unnote().name()) + " drop! KC " + kc + "!");
                }
                break;
            }
        }
    }

}
