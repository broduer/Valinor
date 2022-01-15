package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.CombatFactory;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.position.Tile;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 27, 2021
 */
public class TotemPlugin extends Npc {

    private Nightmare nightmare;

    private boolean chargeable, charged;

    private int hitDelta = -1;
    private final int spawnId;

    public TotemPlugin(int id, Tile spawn) {
        super(id, spawn);
        spawnId = id;
        spawn(false);
        lock();
        setHitpoints(1);
    }

    @Override
    public void sequence() {
        super.sequence();
        // whats hit delta
        if (hitDelta > 0 && --hitDelta == 0) {
            hitDelta = -1;
            nightmare.hit(this, 800);
        }
        if (id() != spawnId) {
            transmog(id());
        }
    }

    public int overrideSubmit(Hit... hits) {
        if (charged) {
            return 0;
        }
        int dmg = 0;
        for(Hit hit : hits) {
            hit.splatType = SplatType.NPC_HEALING_HITSPLAT;
            if (hit.getAttacker() != null && hit.getAttacker().isPlayer()) {
                if (hit.getAttacker().getAsPlayer().getEquipment().get(EquipSlot.WEAPON).getId() == 11907 || hit.getAttacker().getAsPlayer().getEquipment().get(EquipSlot.WEAPON).getId() == 11905 || hit.getAttacker().getAsPlayer().getEquipment().get(EquipSlot.WEAPON).getId() == 22288) {
                    hit.setDamage(hit.getDamage() * 5);
                    hit.splatType = SplatType.NPC_HEALING_HITSPLAT;
                }
            }
            CombatFactory.addPendingHit(hit);
            dmg += hit.getDamage();
        }
        int rt = dmg; // infinite loop this call must be wrong compare with rift
        heal(rt * 2);
        if (hp() >= maxHp()) {
            transmog(spawnId + 2);
            charged = true;
            chargeable = false;
            boolean all = true;
            for (TotemPlugin t : nightmare.getTotems()) {
                if (!t.charged) {
                    all = false;
                    break;
                }
            }
            if (all) {
                for (TotemPlugin t : nightmare.getTotems()) {
                    t.charged = false;
                    t.combatInfo().stats.hitpoints = 1;

                    Projectile p = new Projectile(t, nightmare,1768, 30, 110, 130, 110, 0);
                    p.sendProjectile();
                    t.transmog(t.spawnId);
                    t.breakCombat();
                    t.hitDelta = 3;
                }
                World.getWorld().getPlayers().forEachInRegion(nightmare.tile().region(), player -> player.message("<col=ff0000>All four totems are fully charged."));
            }
        }
        return rt;
    }

    public void breakCombat() {
        World.getWorld().getPlayers().forEachInRegion(tile().region(), player -> {
            if (player.getCombat().getTarget() == this) {
                player.getCombat().reset();
                System.out.println("reset totem");
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

    public void setChargeable(boolean chargeable) {
        this.chargeable = chargeable;
        if (chargeable) {
            transmog(spawnId + 1);
        }
    }

}
