package com.valinor.game.world.entity.combat.magic;

import com.valinor.game.content.mechanics.MultiwayCombat;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.Combat;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.skull.Skulling;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.timers.TimerKey;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A {@link CombatSpell} implementation that is primarily used for spells that
 * are a part of the ancients spellbook.
 *
 * @author lare96
 */
public abstract class CombatEffectSpell extends CombatSpell {

    private ArrayList<Mob> targets;

    public void whenSpellCast(Mob cast, Mob castOn) {

        // The spell doesn't support multiple targets or we aren't in a
        // multicombat zone, so do nothing.
        if (spellRadius() == 0) {
            return;
        }

        // Flag the target as under attack at this moment to factor in delayed combat styles.
        castOn.putAttrib(AttributeKey.LAST_DAMAGER, cast);
        Combat.computeLastDamager(cast, castOn);
        castOn.putAttrib(AttributeKey.LAST_WAS_ATTACKED_TIME, System.currentTimeMillis());
        castOn.getTimers().register(TimerKey.COMBAT_LOGOUT, 16);
        cast.putAttrib(AttributeKey.LAST_ATTACK_TIME, System.currentTimeMillis());
        cast.putAttrib(AttributeKey.LAST_TARGET, castOn);
        cast.getTimers().register(TimerKey.COMBAT_LOGOUT, 16);

        targets = new ArrayList<>();

        // We passed the checks, so now we do multiple target stuff.
        Iterator<? extends Mob> it = null;
        if (cast.isPlayer() && castOn.isPlayer()) {
            it = cast.getLocalPlayers().iterator();
        } else if (cast.isPlayer() && castOn.isNpc()) {
            it = cast.getLocalNpcs().iterator();
        } else if (cast.isNpc() && castOn.isNpc()) {
            it = World.getWorld().getNpcs().iterator();
        } else if (cast.isNpc() && castOn.isPlayer()) {
            it = World.getWorld().getPlayers().iterator();
        }

        for (Iterator<? extends Mob> $it = it; $it.hasNext(); ) {
            Mob next = $it.next();

            if (next == null) {
                continue;
            }

            if (!(next.tile().isWithinDistance(castOn.tile(), spellRadius()) && next.hp() > 0 && next.hp() > 0)) {
                continue;
            }

            if (next.isNpc()) {
                Npc n = (Npc) next;
                if(n.isPet()) {
                    continue;
                }
                if (castOn == n) // we're already done damage for the primary target, don't do even more
                    continue;

                if (n.combatInfo() != null && n.combatInfo().unattackable) {
                    continue;
                }

                if (!MultiwayCombat.includes(n)) {
                    //not in the multi area and we were, don't carry over.
                    continue;
                }

                //Inferno checks
                if (n.id() == 7710 || n.id() == 7709) {
                    continue;
                }

                //Combat dummys
                if(n.isCombatDummy() || n.isPvPCombatDummy()) {
                    continue;
                }

                if (!CombatFactory.canAttack(cast, n)) {
                    cast.getCombat().reset();//Can't attack, reset combat
                    continue;
                }
                // the list of potential targets
                targets.add(n);
            } else {
                Player p = (Player) next;
                if (castOn == p) // we're already done damage for the primary target, don't do even more
                    continue;
                if (p.<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA,-1) == 0 || !WildernessArea.inAttackableArea(p) || !MultiwayCombat.includes(p)) {
                    //not in the multi area and we were, don't carry over.
                    continue;
                }
                if (!CombatFactory.canAttack(cast, p)) {
                    cast.getCombat().reset();//Can't attack, reset combat
                    continue;
                }
                // the list of potential targets
                targets.add(p);
            }
        }

        for (Mob t : targets) {

            // dmg is calcd inside hit
            Hit hit = t.hit(cast, CombatFactory.calcDamageFromType(cast, t, CombatType.MAGIC), cast.getCombat().magicSpellDelay(t), CombatType.MAGIC);
            if (cast.isPlayer() && t.isPlayer()) { // Check if the player should be skulled for making this attack..
                Player attacker = cast.getAsPlayer();
                Player playerTarget = t.getAsPlayer();
                if (WildernessArea.inWilderness(playerTarget.tile())) {
                    Skulling.skull(attacker, playerTarget, SkullType.WHITE_SKULL);
                }
            }

            var hitDelay = cast.getCombat().magicSpellDelay(t);
            if (hit.isAccurate()) {
                //Successful hit, send graphics and do spell effects.
                if(endGraphic().isPresent()) {
                    t.delayedGraphics(endGraphic().get(), hitDelay);
                }
            } else {
                //Unsuccessful hit. Send splash graphics for the spell because it wasn't accurate
                t.delayedGraphics(new Graphic(85, 90, 30),hitDelay - 1);
            }

            spellEffect(cast, t, hit);

            hit.submit();
        }
    }

    @Override
    public List<Item> equipmentRequired(Player player) {

        // Ancient spells never require any equipment, although the method can
        // still be overridden if by some chance a spell does.
        return List.of();
    }

    /**
     * The effect this spell has on the target.
     *  @param cast   the entity casting this spell.
     * @param castOn the person being hit by this spell.
     * @param hit
     */
    public abstract void spellEffect(Mob cast, Mob castOn, Hit hit);

    /**
     * The radius of this spell, only comes in effect when the victim is hit in
     * a multicombat area.
     *
     * @return how far from the target this spell can hit when targeting
     * multiple entities.
     */
    public abstract int spellRadius();
}
