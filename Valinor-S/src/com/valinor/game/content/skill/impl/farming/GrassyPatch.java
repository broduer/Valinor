package com.valinor.game.content.skill.impl.farming;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.util.chainedwork.Chain;

public class GrassyPatch {
    public byte stage = 0;
    long time = System.currentTimeMillis();

    public void setTime() {
        time = System.currentTimeMillis();
    }

    public boolean isRaked() {
        return stage == 3;
    }

    public void process(Player player, int index) {
        if (stage == 0)
            return;
        long elapsed = (System.currentTimeMillis() - time) / 60_000;
        int grow = 1;

        if (elapsed >= grow) {
            for (int i = 0; i < elapsed / grow; i++) {
                if (stage == 0) {
                    player.getFarming().doConfig();
                    return;
                }

                stage = ((byte) (stage - 1));
                player.getFarming().doConfig();
            }
            setTime();
        }
    }

    public void click(Player player, int option, int index) {
        if (option == 1)
            rake(player, index);
    }

    boolean raking = false;
    public void rake(final Player p, final int index) {
        if(raking)
            return;
        if (isRaked()) {
            p.message("This plot is fully raked. Try planting a seed.");
            return;
        }
        if (!p.inventory().contains(5341)) {
            p.message("This patch needs to be raked before anything can grow in it.");
            p.message("You do not have a rake in your inventory.");
            return;
        }
        raking = true;
        p.animate(2273);
        Chain.bound(p).repeatingTask(3, t -> {
            if (!p.inventory().contains(5341)) {
                p.message("This patch needs to be raked before anything can grow in it.");
                p.message("You do not have a rake in your inventory.");
                t.stop();
                return;
            }
            p.animate(2273);
            setTime();
            GrassyPatch grassyPatch = GrassyPatch.this;
            grassyPatch.stage = ((byte) (grassyPatch.stage + 1));
            doConfig(p);
            p.skills().addXp(Skills.FARMING,World.getWorld().random(1) + 1, true);
            p.getInventory().addOrDrop(new Item(6055, 1));
            if (isRaked()) {
                p.message("Your patch is raked, no compost is required, just plant and water!");
                p.animate(65_535);
                t.stop();
            }
        });
        raking = false;
        p.animate(65535);
    }

    public static void doConfig(Player player) {
        player.getFarming().doConfig();
    }

    public int getStage() {
        return stage;
    }
}
