package com.valinor.game.world.position.areas.impl;

import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.areas.Controller;

import java.util.Collections;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 20, 2021
 */
public class SkotizoArea extends Controller {

    public SkotizoArea() {
        super(Collections.emptyList());
    }

    @Override
    public void enter(Mob mob) {

    }

    @Override
    public void leave(Mob mob) {
        if(mob.isPlayer()) {
            mob.getAsPlayer().getInterfaceManager().close(true);
        }
    }

    @Override
    public void process(Mob mob) {
        if(mob.isPlayer()) {
            mob.getAsPlayer().getInterfaceManager().openWalkable(29230);
        }
    }

    @Override
    public boolean canTeleport(Player player) {
        return true;
    }

    @Override
    public boolean canAttack(Mob attacker, Mob target) {
        return true;
    }

    @Override
    public void defeated(Player player, Mob mob) {

    }

    @Override
    public boolean canTrade(Player player, Player target) {
        return false;
    }

    @Override
    public boolean isMulti(Mob mob) {
        return true;
    }

    @Override
    public boolean canEat(Player player, int itemId) {
        return true;
    }

    @Override
    public boolean canDrink(Player player, int itemId) {
        return true;
    }

    @Override
    public void onPlayerRightClick(Player player, Player rightClicked, int option) {

    }

    @Override
    public boolean handleObjectClick(Player player, GameObject object, int type) {
        return false;
    }

    @Override
    public boolean handleNpcOption(Player player, Npc npc, int type) {
        return false;
    }

    @Override
    public boolean inside(Mob mob) {
        return mob.tile().region() == 6810;
    }

    @Override
    public boolean useInsideCheck() {
        return true;
    }
}
