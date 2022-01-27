package com.valinor.game.world.entity.combat.method.impl;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.ranged.*;
import com.valinor.game.world.entity.combat.ranged.RangedData.RangedWeapon;
import com.valinor.game.world.entity.combat.weapon.WeaponType;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.container.equipment.Equipment;

import java.util.ArrayList;

import static com.valinor.game.world.entity.combat.weapon.WeaponType.BOW;
import static com.valinor.game.world.entity.combat.weapon.WeaponType.THROWN;
import static com.valinor.util.CustomItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * Represents the combat method for ranged attacks.
 *
 * @author Professor Oak
 */
public class RangedCombatMethod extends CommonCombatMethod {

    private int ballistaProjectileFor(int ammo) {
        return switch (ammo) {
            case BRONZE_JAVELIN -> 200;
            case IRON_JAVELIN -> 201;
            case STEEL_JAVELIN -> 202;
            case MITHRIL_JAVELIN -> 203;
            case ADAMANT_JAVELIN -> 204;
            case RUNE_JAVELIN -> 205;
            case DRAGON_JAVELIN -> 1301;
            case AMETHYST_JAVELIN -> 1386;
            default -> 1301;
        };
    }

    @Override
    public void prepareAttack(Mob attacker, Mob target) {
        //Get data

        //TODO sound here
        attacker.animate(attacker.attackAnimation());

        if (attacker.isNpc()) {
            new Projectile(attacker, target, attacker.getAsNpc().combatInfo().projectile, 41, 60, 40, 36, 15).sendProjectile();
            return;
        }

        if (attacker.isPlayer()) {
            Player player = attacker.getAsPlayer();

            //Decrement ammo
            CombatFactory.decrementAmmo(player);

            WeaponType weaponType = player.getCombat().getWeaponType();
            var weaponId = player.getEquipment().getId(EquipSlot.WEAPON);
            var ammoId = player.getEquipment().getId(EquipSlot.AMMO);
            var crystalBow = (weaponId >= NEW_CRYSTAL_BOW && weaponId <= CRYSTAL_BOW_110);
            var crawsBow = weaponId == CRAWS_BOW || weaponId == CRAWS_BOW_C || weaponId == BEGINNER_CRAWS_BOW;
            var bowOfFaerdhinen = weaponId == BOW_OF_FAERDHINEN || weaponId == BOW_OF_FAERDHINEN_C || (weaponId >= BOW_OF_FAERDHINEN_C_25884 && weaponId <= BOW_OF_FAERDHINEN_C_25896);
            var chins = weaponType == WeaponType.CHINCHOMPA;
            var ballista = weaponId == 19478 || weaponId == 19481;
            var dragonBolts = (ammoId >= 21932 && ammoId <= 21950);
            var baseDelay = chins ? 20 : ballista ? 30 : 41;
            var speed = attacker.projectileSpeed(target);
            var startHeight = 40;
            var endHeight = 36;
            var curve = 15;
            var graphic = -1; // 228

            if (crystalBow) {
                attacker.graphic(250, 96, 0);
                graphic = 249;
            }

            //Normal and (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN) || player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C_25894)) {
                attacker.graphic(1888, 96, 0);
                graphic = 1887;
            }

            //Red (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C)) {
                attacker.graphic(1923, 96, 0);
                graphic = 1922;
            }

            //White (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C_25884)) {
                attacker.graphic(1925, 96, 0);
                graphic = 1924;
            }

            //Black (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C_25886)) {
                attacker.graphic(1927, 96, 0);
                graphic = 1926;
            }

            //Purple (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C_25888)) {
                attacker.graphic(1929, 96, 0);
                graphic = 1928;
            }

            //Green (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C_25890)) {
                attacker.graphic(1931, 96, 0);
                graphic = 1930;
            }

            //Yellow (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C_25892)) {
                attacker.graphic(1933, 96, 0);
                graphic = 1932;
            }

            //Blue (c)
            if(player.getEquipment().hasAt(EquipSlot.WEAPON, BOW_OF_FAERDHINEN_C_25896)) {
                attacker.graphic(1935, 96, 0);
                graphic = 1934;
            }

            if (crawsBow) {
                attacker.graphic(1611, 96, 0);
                graphic = 1574;
            }

            if (chins) {
                graphic = weaponId == 10033 ? 908 : weaponId == 10034 ? 909 : 1272;
            }

            // Bows need special love.. projectile and graphic :D
            if (weaponType == BOW && !crystalBow && !crawsBow && !bowOfFaerdhinen) {
                var db = ArrowDrawBack.find(ammoId);
                speed = 56;

                // Find the gfx and do it : -)
                if (db != null) {
                    if (Equipment.darkbow(weaponId)) {
                        var db2 = DblArrowDrawBack.find(ammoId);
                        if (db2 != null) {
                            speed = 75;
                            player.graphic(db2.gfx, 96, 0);
                            graphic = db.projectile;
                        }
                    } else {
                        player.graphic(db.gfx, 96, 0);
                        graphic = db.projectile;
                    }
                }
            }

            // Knives are not your de-facto stuff either
            if (weaponType == THROWN || weaponType == WeaponType.DART) {
                if(weaponId == TOXIC_BLOWPIPE || weaponId == MAGMA_BLOWPIPE) {
                    graphic = 1122;
                }
                // Is this a knife? There are more than only knives that people throw.. think.. asparagus. uwat? darts, thrownaxes, javelins
                var drawback = KnifeDrawback.find(weaponId);
                if (drawback != null) {
                    player.graphic(drawback.gfx, 96, 0);
                    graphic = drawback.projectile;
                } else {
                    var db2 = DartDrawback.find(weaponId);
                    if (db2 != null) {
                        player.graphic(db2.gfx, 96, 0);
                        graphic = db2.projectile;
                    } else {
                        var db3 = ThrownaxeDrawback.find(weaponId);
                        if (db3 != null) {
                            player.graphic(db3.gfx, 96, 0);
                            graphic = db3.projectile;
                        }
                    }
                }
            }

            if(ballista) {
                baseDelay = 31;
                startHeight = 40;
                endHeight = 30;
                curve = 5;
                graphic = ballistaProjectileFor(ammoId);
            }

            // Crossbows are the other type of special needs
            if (weaponType == WeaponType.CROSSBOW) {
                baseDelay = 41;
                speed = 56;
                startHeight = 38;
                endHeight = 36;
                curve = 5;
                graphic = dragonBolts ? 1468 : 27;
            }

            if(weaponType == WeaponType.DART) {
                baseDelay = 32;
                speed = 45;
                startHeight = 40;
                endHeight = 36;
            }

            if (graphic != -1)
                new Projectile(attacker, target, graphic, baseDelay, speed, startHeight, endHeight, 0, curve, 11).sendProjectile();

            if (Equipment.darkbow(weaponId) || weaponId == SANGUINE_TWISTED_BOW) {
                // dark bow 2nd arrow
                new Projectile(attacker, target, graphic, 10 + baseDelay, 10 + speed, startHeight, endHeight, 0, curve, 105).sendProjectile();
            }
        }

        // Darkbow is double hits.
        if (attacker.getCombat().getRangedWeapon() != null) {
            var weaponId = -1;
            WeaponType weaponType = WeaponType.UNARMED;
            if(attacker.isPlayer()) {
                weaponId = attacker.getAsPlayer().getEquipment().getId(EquipSlot.WEAPON);
                weaponType = attacker.getAsPlayer().getCombat().getWeaponType();
            }

            var tileDist = attacker.tile().distance(target.tile());
            var delay = calcHitDelay(weaponId, weaponType, tileDist) + 1;

            //System.out.println("delay: "+delay);

            // At range 3 the hits hit the same time. may indicate mathematical rounding in calc.
            var secondArrowDelay = delay + (tileDist == 3 ? 0 : tileDist == 7 || tileDist == 8 || tileDist == 10 ? 2 : 1);
            //System.out.println("secondArrowDelay: "+secondArrowDelay);

            // primary range hit
            target.hit(attacker, CombatFactory.calcDamageFromType(attacker, target, CombatType.RANGED), delay, CombatType.RANGED).checkAccuracy().postDamage(this::handleAfterHit).submit();

            // secondary hits
            if (attacker.getCombat().getRangedWeapon() == RangedWeapon.DARK_BOW) {
                target.hit(attacker, CombatFactory.calcDamageFromType(attacker, target, CombatType.RANGED), secondArrowDelay, CombatType.RANGED).checkAccuracy().submit();
            } else if (weaponId == SANGUINE_TWISTED_BOW) {
                target.hit(attacker, CombatFactory.calcDamageFromType(attacker, target, CombatType.RANGED) / 2, secondArrowDelay, CombatType.RANGED).checkAccuracy().submit();
            }
        }
    }

    private void multi_target_chinchompas(Mob mob, Mob primary_target, int delay) {
        var targets = new ArrayList<Mob>();
        if (primary_target.isPlayer()) {
            World.getWorld().getPlayers().forEachInArea(primary_target.tile().area(1), t -> {
                if(mob.<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA, -1) == 1) {
                    targets.add(t);
                }
            });
        } else {
            World.getWorld().getNpcs().forEachInArea(primary_target.tile().area(1), t -> {
                if(mob.<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA, -1) == 1) {
                    targets.add(t);
                }
            });
        }

        for (Mob targ : targets) {
            if (targ == primary_target || targ == mob) {
                //dont hit us, or the target we've already hit
                continue;
            }
            if (targ.isNpc()) {
                var n = targ.getAsNpc();

                /*if (n.id() == InfernoContext.PILLAR_INVIS || n.id() == InfernoContext.PILLAR_VISIBLE) {
                    continue;
                }*/
            }
            if (!CombatFactory.canAttack(mob, targ)) { // Validate they're in an attackable location
                continue;
            }

            final Hit hit = targ.hit(mob, CombatFactory.calcDamageFromType(mob, targ, CombatType.RANGED), delay, CombatType.RANGED);
            hit.checkAccuracy().submit();

            targ.delayedGraphics(new Graphic(157,100,0), 1);

            targ.putAttrib(AttributeKey.LAST_DAMAGER, mob);
            targ.putAttrib(AttributeKey.LAST_WAS_ATTACKED_TIME, System.currentTimeMillis());
            mob.putAttrib(AttributeKey.LAST_TARGET, targ);
            targ.graphic(-1);

            Equipment.checkTargetVenomGear(mob, targ);
        }
        targets.clear();
    }

    private boolean ballista(int weaponId) {
        return weaponId == 19478 || weaponId == 19481;
    }

    // The hitmark delay without pid. If pid, it gets adjusted elsewhere.
    // https://i.gyazo.com/d84b1fb9969e3166ff5abf2978e77b4d.png
    private int calcHitDelay(int weaponId, WeaponType weaponType, int dist) {
        if (ballista(weaponId))
            return (dist <= 4) ? 2 : 3;
        if (weaponId == 12926 || weaponType == WeaponType.CHINCHOMPA)   // Blowpipe / chins longer range throwning weps
            return (dist <= 5) ? 2 : 3;
        if (weaponType == THROWN) {
            return 2; // darts / knives with max dist 4
        } else {
            return (dist <= 2) ? 3 : (dist <= 8) ? 3 : 4; // shortbow (and darkbow), longbow, karils xbow, crystal bow, crossbows
        }
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        RangedWeapon weapon = mob.getCombat().getRangedWeapon();
        if (weapon != null) {

            // Long range fight type has longer attack distance than other types
            if (mob.getCombat().getFightType() == weapon.getType().getLongRangeFightType()) {
                return weapon.getType().getLongRangeDistance();
            }

            return weapon.getType().getDefaultDistance();
        }
        return 6;
    }

    public void handleAfterHit(Hit hit) {
        if (hit.getAttacker() == null) {
            return;
        }

        final RangedWeapon rangedWeapon = hit.getAttacker().getCombat().getRangedWeapon();
        if (rangedWeapon == null) {
            return;
        }

        boolean chins = rangedWeapon == RangedWeapon.CHINCHOMPA;

        if (chins) {
            hit.getTarget().performGraphic(new Graphic(157, 100, 0));
            multi_target_chinchompas(hit.getAttacker(), hit.getTarget(), hit.getDelay());
        }
    }
}
