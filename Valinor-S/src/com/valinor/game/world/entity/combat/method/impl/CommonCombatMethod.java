package com.valinor.game.world.entity.combat.method.impl;

import com.valinor.game.content.duel.DuelRule;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.magic.CombatSpell;
import com.valinor.game.world.entity.combat.method.CombatMethod;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
import com.valinor.util.Debugs;
import com.valinor.util.timers.TimerKey;

import java.util.ArrayList;

import static com.valinor.game.world.entity.AttributeKey.VENOMED_BY;
import static com.valinor.game.world.entity.combat.method.impl.npcs.bosses.CorporealBeast.CORPOREAL_BEAST_AREA;
import static com.valinor.util.CustomItemIdentifiers.ELDER_WAND;
import static com.valinor.util.CustomItemIdentifiers.ELDER_WAND_RAIDS;
import static com.valinor.util.NpcIdentifiers.CORPOREAL_BEAST;
import static com.valinor.util.NpcIdentifiers.VESPULA;

/**
 * reduce code replication for the 90+ npc classes
 *
 * @author Jak Shadowrs tardisfan121@gmail.com
 */
public abstract class CommonCombatMethod implements CombatMethod {

    public Mob mob, target;

    public void set(Mob mob, Mob target) {
        this.mob = mob;
        this.target = target;
    }

    public void restore() {
        mob.heal(mob.maxHp());
        mob.putAttrib(AttributeKey.POISON_TICKS,0);
        mob.putAttrib(AttributeKey.VENOM_TICKS, 0);
        mob.putAttrib(AttributeKey.POISON_TICKS, 0);
        mob.clearAttrib(VENOMED_BY);
        mob.getTimers().cancel(TimerKey.FROZEN);
    }

    public ArrayList<Mob> getPossibleTargets() {
        return getPossibleTargets(14, true, false);
    }

    public ArrayList<Mob> getPossibleTargets(int ratio, boolean players, boolean npcs) {
        ArrayList<Mob> possibleTargets = new ArrayList<>();
        if (players) {
            for (Player player : World.getWorld().getPlayers()) {
                if (player == null || player.dead() || player
                    .tile()
                    .distance(
                        mob
                            .getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(player);
            }
        }
        if (npcs) {
            for (Npc npc : World.getWorld().getNpcs()) {
                if (npc == null || npc == mob || npc.dead() || npc.getCentrePosition().distance(mob.getCentrePosition()) > ratio) {
                    continue;
                }
                possibleTargets.add(npc);
            }
        }
        return possibleTargets;
    }

    public final void handleDodgableAttack(final Mob mob, final Mob target, CombatType type, final Projectile projectile, final Graphic graphic, final int damage, Task onhit) {
        if (mob == null || target == null)
            return;

        final Tile hitLoc = target.tile();
        if (target.isPlayer()) {
            projectile.sendProjectile();
        }

        Task task = new Task("handleDodgableAttackTask", 1, target, false) {

            @Override
            public void execute() {
                if (!target.tile().equals(hitLoc)) {
                    stop();
                    return;
                }
                target.hit(mob, damage, 0, type).setAccurate(false).graphic(graphic).submit();
                stop();
            }
        };
        TaskManager.submit(task);
        TaskManager.submit(onhit);
    }

    protected boolean withinDistance(int distance) {
        return DumbRoute.withinDistance(mob, target, distance);
    }

    /**
     * npc only
     */
    public void doFollowLogic() {
        DumbRoute.step(mob, target, getAttackDistance(mob));
    }

    /**
     * npc only
     */
    public boolean inAttackRange() {
        boolean instance = mob.tile().getZ() > 4;
        // just a hard limit. might need to replace/override with special cases
        //System.out.println(mob.tile().distance(target.tile()));
        if(mob.isNpc() && mob.getAsNpc().id() == CORPOREAL_BEAST) {
            if(!target.tile().inArea(CORPOREAL_BEAST_AREA)) {
                mob.getCombat().reset();//Target out of distance reset combat
            }
        }
        if (mob.tile().distance(target.tile()) >= 16 && !instance) {
            mob.getCombat().reset();//Target out of distance reset combat
            return false;
        }
        return DumbRoute.withinDistance(mob, target, getAttackDistance(mob));
    }

    /**
     * npc only
     */
    public void onDeath(Npc npc) {

    }

    /**
     * player only
     */
    public void postAttack() {

    }

    public void onHit(Mob mob, Mob target, Hit hit) {

    }

    public boolean canAttackStyle(Mob entity, Mob other, CombatType type) {
        //Specific combat style checks
        if (entity.isPlayer()) {
            Player player = (Player) entity;
            boolean magicOnly = player.getAttribOr(AttributeKey.MAGEBANK_MAGIC_ONLY, false);
            CombatSpell spell = player.getCombat().getCastSpell() != null ? player.getCombat().getCastSpell() : player.getCombat().getAutoCastSpell();

            // If you're in the mage arena, where it is magic only.
            if (type != CombatType.MAGIC && magicOnly) {
                player.message("You can only use magic inside the arena!");
                player.getCombat().reset();
                return false;
            }

            if (type == CombatType.MAGIC) {
                if (spell != null && !spell.canCast(player, other, false)) {
                    player.getCombat().reset();//We can't cast this spell reset combat
                    player.getCombat().setCastSpell(null);
                    Debugs.CMB.debug(entity, "spell !cancast.", other, true);
                    return false;
                }

                // Duel, disabled magic?
                if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_MAGIC.ordinal()]) {
                    DialogueManager.sendStatement(player, "Magic has been disabled in this duel!");
                    player.getCombat().reset();
                    Debugs.CMB.debug(entity, "no magic in duel.", other, true);
                    return false;
                }
            } else if (type == CombatType.RANGED) {
                // Duel, disabled ranged?
                if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_RANGED.ordinal()]) {
                    DialogueManager.sendStatement(player, "Ranged has been disabled in this duel!");
                    player.getCombat().reset();//Ranged attacks disabled, stop combat
                    Debugs.CMB.debug(entity, "no range in duel.", other, true);
                    return false;
                }

                // Check that we have the ammo required
                if (!CombatFactory.checkAmmo(player)) {
                    Debugs.CMB.debug(entity, "no ammo", other, true);
                    player.getCombat().reset();//Out of ammo, stop combat
                    return false;
                }
            } else if (type == CombatType.MELEE) {
                if (player.getDueling().inDuel() && player.getDueling().getRules()[DuelRule.NO_MELEE.ordinal()]) {
                    DialogueManager.sendStatement(player, "Melee has been disabled in this duel!");
                    player.getCombat().reset();//Melee attacks disabled, stop combat
                    Debugs.CMB.debug(entity, "no melee in duel.", other, true);
                    return false;
                }
                //Att acking Aviansie with melee.
                if (other.isNpc()) {
                    int id = other.getAsNpc().id();

                    if (id == VESPULA) {
                        entity.message("Vespula is flying too high for you to hit with melee!");
                        entity.getCombat().reset();//Vespula out of range, stop combat
                        return false;
                    }

                    if (id == 3166 || id == 3167 || id == 3168 || id == 3169 || id == 3170 || id == 3171
                        || id == 3172 || id == 3173 || id == 3174 || id == 3175 || id == 3176 || id == 3177
                        || id == 3178 || id == 3179 || id == 3180 || id == 3181 || id == 3182 || id == 3183) {
                        entity.message("The Aviansie is flying too high for you to attack using melee.");
                        entity.getCombat().reset();//Aviansie out of range, stop combat
                        return false;
                    } else if (id >= 3162 && id <= 3165 || id == 15016) {
                        entity.message("It's flying too high for you to attack using melee.");
                        entity.getCombat().reset();//Monster out of range, stop combat
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public CombatType styleOf() {
        if (!mob.isPlayer()) // this mtd is players only
            return null;
        if (this instanceof MagicCombatMethod)
            return CombatType.MAGIC;
        if (this instanceof RangedCombatMethod)
            return CombatType.RANGED;
        if (this instanceof MeleeCombatMethod)
            return CombatType.MELEE;
        if (this.getClass().getPackageName().contains("magic"))
            return CombatType.MAGIC;
        if (this.getClass().getPackageName().contains("melee"))
            return CombatType.MELEE;
        if (this.getClass().getPackageName().contains("range"))
            return CombatType.RANGED;
        if (mob.getAsPlayer().getEquipment().hasAt(EquipSlot.WEAPON, ELDER_WAND) || mob.getAsPlayer().getEquipment().hasAt(EquipSlot.WEAPON, ELDER_WAND_RAIDS))
            return CombatType.MAGIC;
        System.err.println("unknown player styleOf combat script: " + this + " wep " + mob.getAsPlayer().getEquipment().getId(3));
        return null;
    }
}
