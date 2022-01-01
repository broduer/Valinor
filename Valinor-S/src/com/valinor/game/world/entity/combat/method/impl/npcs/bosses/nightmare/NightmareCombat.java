package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.position.Tile;
import com.valinor.util.chainedwork.Chain;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 28, 2021
 */
public class NightmareCombat extends CommonCombatMethod {

    private static final int MAGIC_ANIMATION = 8595, RANGE_ANIMATION = 8596, MAGIC_PROJECTILE = 1764, RANGED_PROJECTILE = 1766;

    private SpecialAttacks special;

    private void doSpecialAttack() {
        var randomSpecialAttack = World.getWorld().random(5);
        switch (randomSpecialAttack) {
            case 0 -> special = SpecialAttacks.GRASPING_CLAWS;
            case 1 -> special = SpecialAttacks.FLOWER_POWER;
            case 2 -> special = SpecialAttacks.HUSKS;
            case 3 -> special = SpecialAttacks.CURSE;
            case 4 -> special = SpecialAttacks.PARASITES;
        }
    }

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        Nightmare nightmare = (Nightmare) mob;

        //Force a special attack for now
        if(World.getWorld().rollDie(10, 2)) {
            doSpecialAttack();
        }

        /*
         * Special attack.
         */
        if (special != null) {
            nightmare.animate(special.animation);
            special.run(nightmare);
            special = null;
            return;
        }

        /*
         * Default attack.
         */
        if (World.getWorld().random(10) > 5) {
            for (Mob victim : nightmare.getPossibleTargets(64, true, false)) {
                nightmare.animate(MAGIC_ANIMATION);
                nightmare.getCombat().setTarget(victim);
                Chain.bound(null).runFn(1, () -> {
                    Projectile pr = new Projectile(nightmare, victim, MAGIC_PROJECTILE, 30, 66, 110, 90, 0);
                    pr.sendProjectile();
                });
                final Tile dest = victim.tile();
                int delay;
                if (nightmare.getCentrePosition().distance(dest) == 1) {
                    delay = 60;
                } else if (nightmare.getCentrePosition().distance(dest) <= 5) {
                    delay = 80;
                } else if (nightmare.getCentrePosition().distance(dest) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                victim.hit(nightmare, CombatFactory.calcDamageFromType(nightmare, victim, CombatType.MAGIC), (delay / 20) - 1, CombatType.MAGIC).checkAccuracy().submit();
            }
        } else {
            for (Mob victim : nightmare.getPossibleTargets(64, true, false)) {
                int delay = 60;
                nightmare.animate(RANGE_ANIMATION);
                nightmare.getCombat().setTarget(victim);
                Chain.bound(null).runFn(1, () -> {
                    Projectile pr = new Projectile(nightmare, victim, RANGED_PROJECTILE, 30, 66, 110, 90, 0);
                    pr.sendProjectile();
                });
                final Tile dest = victim.tile();
                if (nightmare.getCentrePosition().distance(dest) == 1) {
                    delay = 60;
                } else if (nightmare.getCentrePosition().distance(dest) <= 5) {
                    delay = 80;
                } else if (nightmare.getCentrePosition().distance(dest) <= 8) {
                    delay = 100;
                } else {
                    delay = 120;
                }
                victim.hit(nightmare, CombatFactory.calcDamageFromType(nightmare, victim, CombatType.RANGED), (delay / 20) - 1, CombatType.RANGED).checkAccuracy().submit();
            }
        }
    }

    public void setSpecial(SpecialAttacks attack) {
        special = attack;
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }

    @Override
    public void onDeath() {
        target.message("You have defeated The Nightmare!");
        target.message("You can leave by using the energy barrier.");
    }
}
