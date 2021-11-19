package com.valinor.game.content.skill.impl.slayer.master;

import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerTaskDef;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.Skills;
import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;

import java.util.ArrayList;
import java.util.List;

import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.SLAYER_TASK;

/**
 * @author PVE
 * @Since juli 19, 2020
 */
public class SlayerMaster {

    public int npcId;
    public int id;

    public final List<SlayerTaskDef> defs = new ArrayList<>();

    public SlayerTaskDef randomTask(Player player) {
        // Grab our last task and exclude it to avoid b2b
        int last = player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0);

        // Build a map and fill it with possible tasks.
        final int[] tmp = {0};

        ImmutableRangeMap.Builder<Integer, SlayerTaskDef> builder = ImmutableRangeMap.builder();
        defs.forEach(task -> {
            if (task != null && task.getCreatureUid() != last &&
                player.skills().xpLevel(Skills.SLAYER) >= SlayerCreature.lookup(task.getCreatureUid()).req &&
                player.skills().combatLevel() >= SlayerCreature.lookup(task.getCreatureUid()).cbreq &&
                !player.getSlayerRewards().isTaskBlocked(task) && player.getSlayerRewards().canAssign(task)) {
                builder.put(Range.closedOpen(tmp[0], tmp[0] + task.getWeighing()), task);
                tmp[0] += task.getWeighing();
            }
        });
        ImmutableRangeMap<Integer, SlayerTaskDef> build = builder.build();
        if (tmp[0] == 0) {
            // builder is empty u cant do any tasks at all
            return null;
        }
        Range<Integer> range = build.span();
        int rnd = World.getWorld().random(range.upperEndpoint() - 1);
        return build.get(rnd);
    }
}
