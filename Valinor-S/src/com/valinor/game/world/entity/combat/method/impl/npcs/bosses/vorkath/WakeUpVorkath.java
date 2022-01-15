package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.vorkath;

import com.valinor.game.task.Task;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.masks.animations.Priority;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Tuple;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.NpcIdentifiers.VORKATH_8061;

public class WakeUpVorkath extends Task {

    private static final Animation POKE_ANIMATION = new Animation(827, Priority.HIGH);
    private static final Animation WAKE_ANIMATION = new Animation(7950, Priority.HIGH);

    private final Player player;
    private int ticks;

    public WakeUpVorkath(Player player, int ticks) {
        super("WakeUpVorkathTask", 1, player, true);
        this.player = player;
        this.ticks = ticks;
    }

    @Override
    public void execute() {
        if (player == null || player.dead()) {
            this.stop();
            return;
        }

        Npc sleepingVorkath = player.getVorkathInstance().sleepingVorkath;
        if (sleepingVorkath == null) {
            this.stop();
            return;
        }

        ticks++;
        sleepingVorkath.getCombat().reset();
        System.out.println("cmb reset 518");
        if (ticks == 1) {
            player.setVorkathState(VorkathState.AWAKE);
            player.animate(POKE_ANIMATION);
            player.message("You poke the dragon..");
        } else if (ticks == 2) {
            sleepingVorkath.animate(WAKE_ANIMATION);
            sleepingVorkath.face(player.tile());
        } else if (ticks == 9) {
            //Remove sleeping vorkath from the world
            Tile tile = sleepingVorkath.spawnTile();
            World.getWorld().unregisterNpc(sleepingVorkath);

            //Create a vorkath instance
            player.getVorkathInstance().vorkath = new Npc(VORKATH_8061, tile);
            Npc vorkath = player.getVorkathInstance().vorkath;

            //Spawn the vorkath
            World.getWorld().registerNpc(vorkath);

            //Add the vorkath to the instance list
            player.getVorkathInstance().npcList.add(vorkath);

            vorkath.respawns(false);
            vorkath.putAttrib(AttributeKey.OWNING_PLAYER, new Tuple<>(player.getIndex(), player));
            Chain.bound(null).name("VorkathWakeTask").runFn(3, () -> {
                vorkath.getMovementQueue().setBlockMovement(true);
                vorkath.setCombatMethod(new Vorkath());
                vorkath.getCombat().attack(player);
            });
            this.stop();
        }
    }
}
