package com.valinor.game.content.areas.riskzone;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.mob.movement.MovementQueue;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.StepType;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 26, 2022
 */
public class RiskFightArea extends Interaction {

    public static final Area NH_AREA = new Area(3117, 3506, 3122, 3518);
    public static final Area ONE_V_ONE_1 = new Area(3111, 3506, 3116, 3509);
    public static final Area ONE_V_ONE_2 = new Area(3103, 3514, 3110, 3518);
    public static final Area ONE_V_ONE_3 = new Area(3111, 3514, 3116, 3518);

    private void walk(Player player, GameObject obj) {
        player.runFn(1, () -> {
            int x = player.getAbsX();
            int y = player.getAbsY();

            switch (obj.getRotation()) {
                case 1:
                case 3:
                    if(y < obj.tile().y) {
                        System.out.println("we here 3");
                        y += 1;
                    } else {
                        System.out.println("we here 4");
                        y -= 1;
                    }
                default: break;
            }
            player.lock();
            player.stepAbs(x, y, StepType.FORCE_WALK);
            final int finalY = y;
            player.waitForTile(new Tile(x, finalY), player::unlock);
        });
    }

    private void walkX(Player player, boolean xUp) {
        Tile targTile = player.tile().transform(xUp ? +1 : -1, 0, 0);
        player.getMovementQueue().interpolate(targTile, MovementQueue.StepType.FORCED_WALK);
    }

    private void walkY(Player player, boolean yUp) {
        Tile targTile = player.tile().transform(0, yUp ? +1 : -1, 0);
        player.getMovementQueue().interpolate(targTile, MovementQueue.StepType.FORCED_WALK);
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if(option == 1) {
            var risk = player.<Long>getAttribOr(AttributeKey.RISKED_WEALTH, 0L);
            if(obj.getId() == BELL_21394) {
                if (player.getTimers().has(TimerKey.RISK_FIGHT_BELL)) {
                    player.message(Color.RED.wrap("You can ring the bell again in " + player.getTimers().asMinutesAndSecondsLeft(TimerKey.RISK_FIGHT_BELL) + "."));
                } else {
                    player.getTimers().register(TimerKey.RISK_FIGHT_BELL, 300);
                    World.getWorld().sendWorldMessage("<img=162>" + Color.BLUE.wrap(player.getUsername()) + Color.RAID_PURPLE.wrap(" has just rung the bell at the risk zone and is looking for a fight!"));
                }
                return true;
            }
            if(obj.getId() == MAGICAL_BARRIER_31808) {
                walk(player, obj);
                return true;
            }
            if(obj.getId() == ENERGY_BARRIER_4470) {
                //50M risk area
                if(obj.tile().equals(3106,3513,0) || obj.tile().equals(3107,3513,0)) {
                    player.getRisk().update(); // make sure our wealth is up-to-date.
                    if (risk <= 50_000_000) {
                        player.message(Color.RED.wrap("You need to risk at least 50M coins to enter this risk zone."));
                        return true;
                    }
                    if (player.tile().y == 3513) {
                        if (!player.tile().equals(obj.tile().transform(0, 0, 0))) {
                            player.getMovementQueue().walkTo(obj.tile().transform(0, 0, 0));
                        }
                        Chain.bound(player).name("RiskFightArenaTask").waitForTile(obj.tile().transform(0, 0, 0), () -> {
                            Skulling.assignSkullState(player, SkullType.RED_SKULL);
                            player.forceChat("I am currently risking " + Utils.formatNumber(risk) + " Coins!");
                            Tile targTile = player.tile().transform(0, +1, 0);
                            player.getMovementQueue().interpolate(targTile, MovementQueue.StepType.FORCED_WALK);
                            Chain.bound(player).name("RiskFightArenaTask2").waitForTile(targTile, player::unlock);
                        });
                    } else if (player.tile().y == 3514) {
                        Skulling.unskull(player);
                        walkY(player, false);
                    }
                    return true;
                }
                //250M risk area
                if(obj.tile().equals(3110,3509,0) || obj.tile().equals(3110,3508,0)) {
                    player.getRisk().update(); // make sure our wealth is up-to-date.
                    if (risk <= 250_000_000) {
                        player.message(Color.RED.wrap("You need to risk at least 250M Coins to enter this risk zone."));
                        return true;
                    }
                    if (player.tile().x == 3110 && (player.tile().y == 3508 || player.tile().y == 3509)) {
                        Skulling.assignSkullState(player, SkullType.RED_SKULL);
                        player.forceChat("I am currently risking "+ Utils.formatNumber(risk)+" Coins!");
                        walkX(player,true);
                    } else if(player.tile().x == 3111 && (player.tile().y == 3508 || player.tile().y == 3509)) {
                        Skulling.unskull(player);
                        walkX(player,false);
                    }
                    return true;
                }
                //1B risk area
                if(obj.tile().equals(3112,3513,0) || obj.tile().equals(3113,3513,0)) {
                    player.getRisk().update(); // make sure our wealth is up-to-date.
                    if (risk <= 1_000_000_000) {
                        player.message(Color.RED.wrap("You need to risk at least 1B coins to enter this risk zone."));
                        return true;
                    }
                    if (player.tile().y == 3513 && (player.tile().x == 3112 || player.tile().x == 3113)) {
                        Skulling.assignSkullState(player, SkullType.RED_SKULL);
                        player.forceChat("I am currently risking "+ Utils.formatNumber(risk)+" Coins!");
                        walkY(player, true);
                    } else if(player.tile().y == 3514 && (player.tile().x == 3112 || player.tile().x == 3113)) {
                        Skulling.unskull(player);
                        walkY(player, false);
                    }
                    return true;
                }
                //No restrictions here
                if(obj.tile().equals(3117,3513,0) || obj.tile().equals(3117,3512,0) || obj.tile().equals(3117,3511,0)) {
                    if (player.tile().x == 3116) {
                        Skulling.assignSkullState(player, SkullType.RED_SKULL);
                        player.forceChat("I am currently risking "+ Utils.formatNumber(risk)+" Coins!");
                        walkX(player,true);
                    } else if(player.tile().x == 3117) {
                        if (player.tile().x <= 3117) {
                            if (!player.tile().equals(obj.tile().transform(0, 0, 0))) {
                                player.getMovementQueue().walkTo(obj.tile().transform(0, 0, 0));
                            }
                            Chain.bound(player).name("RiskFightArenaTask").waitForTile(obj.tile().transform(0, 0, 0), () -> {
                                Skulling.unskull(player);
                                Tile targTile = player.tile().transform(-1, 0, 0);
                                player.getMovementQueue().interpolate(targTile, MovementQueue.StepType.FORCED_WALK);
                                Chain.bound(player).name("RiskFightArenaTask2").waitForTile(targTile, player::unlock);
                            });
                        }
                    }
                    return true;
                }
                return true;
            }
        }
        return false;
    }
}
