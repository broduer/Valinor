package com.valinor.game.world.entity.combat.method.impl.specials.melee;

import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.CombatSpecial;
import com.valinor.game.world.entity.combat.CombatType;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.prayer.default_prayer.Prayers;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.CustomItemIdentifiers.BEGINNER_AGS;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 09, 2022
 */
public class AncientGodsword extends CommonCombatMethod {

    @Override
    public void prepareAttack(Mob mob, Mob target) {
        final Player player = (Player) mob;
        int animation = 9171;
        player.animate(animation);
        player.graphic(1996);
        //TODO it.player().world().spawnSound(it.player().tile(), 3869, 0, 10)

        Hit hit = target.hit(mob, CombatFactory.calcDamageFromType(mob, target, CombatType.MELEE),1, CombatType.MELEE).checkAccuracy();
        hit.submit();
        CombatSpecial.drain(mob, CombatSpecial.ANCIENT_GODSWORD.getDrainAmount());

        Tile targetTile = target.tile();
        Chain.bound(null).runFn(8, () -> {
            if(target.tile().isWithinDistance(targetTile,5)) {
                target.graphic(2003);
                int damage = Prayers.usingPrayer(target, Prayers.PROTECT_FROM_MAGIC) ? 12 : 25;
                target.hit(mob, damage);
                mob.heal(damage);
            }
        });
    }

    @Override
    public int getAttackSpeed(Mob mob) {
        return mob.getBaseAttackSpeed();
    }

    @Override
    public int getAttackDistance(Mob mob) {
        return 1;
    }
}
