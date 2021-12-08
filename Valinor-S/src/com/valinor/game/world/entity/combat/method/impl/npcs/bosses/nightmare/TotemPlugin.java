package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class TotemPlugin extends Npc {

    private Nightmare nightmare;

    private boolean chargeable, charged;

    private int hitDelta = -1, spawnId;

    public TotemPlugin(int id, Tile spawn) {
        super(id, spawn);
        spawnId = id;
        spawn(false);
        lock();
        setHitpoints(1);
        //TODO skipMovementCheck = true;
    }

    @Override
    public void sequence() {
        super.sequence();
        if (hitDelta > 0 && --hitDelta == 0) {
            hitDelta = -1;
            nightmare.hit(this, 800);
        }
        if (id() != spawnId) {
            transmog(id());
        }
    }

    //TODO
   /* @Override
    public int hit(Hit... hits) {
        if (charged) return 0;
        for(Hit hit : hits) {
            hit.type = HitType.HEAL;
            if (hit.attacker != null && hit.attacker.isPlayer()) {
                if (hit.attacker.player.getEquipment().get(Equipment.SLOT_WEAPON).getId() == 11907 || hit.attacker.player.getEquipment().get(Equipment.SLOT_WEAPON).getId() == 11905 || hit.attacker.player.getEquipment().get(Equipment.SLOT_WEAPON).getId() == 22288) {
                    hit = new Hit().fixedDamage(hit.minDamage * 5);
                    hit.type = HitType.HEAL;
                }
            }
        }
        int rt = super.hit(hits);
        getCombat().getStat(StatType.Hitpoints).alter(getCombat().getStat(StatType.Hitpoints).currentLevel+ (rt * 2));
        if (getCombat().getStat(StatType.Hitpoints).currentLevel >= getCombat().getStat(StatType.Hitpoints).fixedLevel) {
            transform(spawnId + 2);
            charged = true;
            chargeable = false;
            boolean all = true;
            for (TotemPlugin t : nightmare.getTotems()) {
                if (!t.charged) {
                    all = false;
                }
            }
            if (all) {
                for (TotemPlugin t : nightmare.getTotems()) {
                    t.charged = false;
                    t.getCombat().getStat(StatType.Hitpoints).alter(1);
                    Projectile p = new Projectile(1768, 130, 110, 30, 56, 10, 10, 64);
                    p.send(t, nightmare);
                    t.transform(t.spawnId);
                    t.breakCombat();
                    t.hitDelta = 3;
                }
                for (Player player : getPosition().getRegion().players) {
                    player.sendMessage("<col=ff0000>All four totems are fully charged.");
//					player.getWidgetManager().close(Widget.NIGHTMARE_TOTEM_HEALTH);
                }
            }
        }
        return rt;
    }*/

    public void breakCombat() {
        World.getWorld().getPlayers().forEachInRegion(tile().region(), player -> {
            if (player.getCombat().getTarget() == this) {
                player.getCombat().reset();
            }
        });
    }

    public Nightmare getNightmare() {
        return nightmare;
    }

    public void setNightmare(Nightmare nightmare) {
        this.nightmare = nightmare;
    }

    public boolean isChargeable() {
        return chargeable;
    }

    //TODO
    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
        /*actions = new NPCAction[5];
        for (int i = 0; i < 5; i++) {
            actions[i] = (player, npc) -> {
                player.getCombat().setTarget(npc);
                player.face(npc);
            };
        }*/
        if (chargeable) {
            transmog(spawnId + 1);
        }
    }

}
