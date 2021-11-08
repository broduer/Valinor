package com.valinor.game.content.skill.impl.slayer.superior_slayer;

import com.valinor.GameServer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.util.Color;
import com.valinor.util.Tuple;
import com.valinor.util.Utils;
import com.valinor.util.timers.TimerKey;

public class SuperiorSlayer {

    public static void trySpawn(Player player, SlayerCreature taskDef, Npc npc) {
        // Must unlock the option first.
        if (!player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.BIGGER_AND_BADDER)) {
            return;
        }

        int superior = getSuperior(taskDef);

        if(superior == -1)
            return;

        // A superior task cannot spawn another instance of itself.
        if (superior == npc.id()) {
            return;
        }

        int odds = 10;

        //Almost always spawn for developers (testing)
        if (player.getPlayerRights().isDeveloperOrGreater(player) && !GameServer.properties().production)
            odds = 3;

        if (Utils.rollDie(odds, 1)) {
            Npc boss = new Npc(superior, npc.tile());
            World.getWorld().registerNpc(boss);
            boss.respawns(false);
            boss.putAttrib(AttributeKey.OWNING_PLAYER, new Tuple<>(player.getIndex(), player));
            boss.combatInfo().aggressive = true;
            TaskManager.submit(new RemoveSuperiorTask(player, boss));
            player.getTimers().register(TimerKey.SUPERIOR_BOSS_DESPAWN, 200);
            player.message("<col=" + Color.RED.getColorValue() + ">A superior foe has appeared...");
        }
    }

    private static int getSuperior(SlayerCreature taskDef) {
        return switch (taskDef) {
            case ABYSSAL_DEMON -> 7410;
            case CAVE_HORRORS -> 7401;
            case BLOODVELDS -> 7397;
            case DUST_DEVILS -> 7404;
            case CAVE_CRAWLER -> 7389;
            case CRAWLING_HANDS -> 7388;
            case ROCKSLUG -> 7392;
            case DARK_BEASTS -> 7409;
            case NECHRYAEL -> 7411;
            case SMOKE_DEVILS -> 7406;
            case ABERRANT_SPECRES -> 7402;
            case PYREFIEND -> 7394;
            case JELLIES -> 7399;
            default -> -1;
        };
    }
}
