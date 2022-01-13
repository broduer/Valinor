package com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.hit.Hit;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

import java.util.ArrayList;

import static com.valinor.util.NpcIdentifiers.NEX_11280;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 13, 2022
 */
public class Nex extends Npc {

    private boolean followTarget;
    private int stage;
    private int minionStage;
    private int flyTime;
    private long lastVirus;
    private boolean embracedShadow;
    private boolean trapsSettedUp;
    private long lastSiphon;
    private boolean doingSiphon;
    private Npc[] bloodReavers;
    private int switchPrayersDelay;

    public Nex(int id, Tile tile) {
        super(id, tile);
        cantInteract(true);
        capDamage(50);
    }

    @Override
    public void sequence() {
        if (flyTime > 0)
            flyTime--;

        if (getStage() == 0 && minionStage == 0 && hp() <= 2400) {
            capDamage(0);
            forceChat("Fumus, don't fail me!");
            getCombat().delayAttack(1);
            ZarosGodwars.breakFumusBarrier();
            minionStage = 1;
        } else if (getStage() == 1 && minionStage == 1 && hp() <= 1800) {
            capDamage(0);
            forceChat("Umbra, don't fail me!");
            getCombat().delayAttack(1);
            ZarosGodwars.breakUmbraBarrier();
            minionStage = 2;
        } else if (getStage() == 2 && minionStage == 2 && hp() <= 1200) {
            capDamage(0);
            forceChat("Cruor, don't fail me!");
            getCombat().delayAttack(1);
            ZarosGodwars.breakCruorBarrier();
            minionStage = 3;
        } else if (getStage() == 3 && minionStage == 3 && hp() <= 600) {
            capDamage(0);
            forceChat("Glacies, don't fail me!");
            getCombat().delayAttack(1);
            ZarosGodwars.breakGlaciesBarrier();
            minionStage = 4;
        } else if (getStage() == 4 && minionStage == 4) {
            if (switchPrayersDelay > 0)
                switchPrayersDelay--;
            else {
                switchPrayers();
                resetSwitchPrayersDelay();
            }
        }
        
        if (dead() || doingSiphon || cantInteract()) {
            return;
        }
        super.sequence();
        /*if (!getCombat().process())
            checkAgressivity();*/
    }

    public ArrayList<Mob> calculatePossibleTargets(Tile current, Tile position, boolean northSouth) {
        ArrayList<Mob> list = new ArrayList<>();
        for (Mob e : ZarosGodwars.getPossibleTargets()) {
            if (e.tile().inArea(current.getX(), current.getY(), position.getX() + (northSouth ? 2 : 0),
                position.getY() + (!northSouth ? 2 : 0))

                || e.tile().inArea(position.getX(), position.getY(), current.getX() + (northSouth ? 2 : 0),
                current.getY() + (!northSouth ? 2 : 0)))
                list.add(e);
        }
        return list;
    }

    public void moveNextStage() {
        if (getStage() == 0 && minionStage == 1) {
            capDamage(50);
            forceChat("Darken my shadow!");
            Projectile projectile = new Projectile(ZarosGodwars.umbra, this, 2010, 30,60,18, 18,0);
            projectile.sendProjectile();
            getCombat().delayAttack(1);
            setStage(1);
        } else if (getStage() == 1 && minionStage == 2) {
            capDamage(50);
            forceChat("Flood my lungs with blood!");
            Projectile projectile = new Projectile(ZarosGodwars.cruor, this, 2010, 30,60,18, 18,0);
            projectile.sendProjectile();
            getCombat().delayAttack(1);
            setStage(2);
        } else if (getStage() == 2 && minionStage == 3) {
            capDamage(50);
            killBloodReavers();
            forceChat("Infuse me with the power of ice!");
            Projectile projectile = new Projectile(ZarosGodwars.glacies, this, 2010, 30,60,18, 18,0);
            projectile.sendProjectile();
            getCombat().delayAttack(1);
            setStage(3);
        } else if (getStage() == 3 && minionStage == 4) {
            capDamage(50);
            forceChat("NOW, THE POWER OF ZAROS!");
            animate(9179);
            getCombat().delayAttack(1);
            heal(600);
            setStage(4);
        }
    }

    public void resetSwitchPrayersDelay() {
        switchPrayersDelay = 35; // 25sec
    }

    public void switchPrayers() {
        transmog(NEX_11280);
    }

    @Override
    public void onHit(Hit hit) {
        /*if (doingSiphon)
            this.hit(this, hit.getDamage(), SplatType.NPC_HEALING_HITSPLAT);

        if (id() == NEX_11280 && hit.getCombatType() == CombatType.MELEE) {
            Mob source = hit.getTarget();
            if (source != null) {
                int deflectedDamage = (int) (hit.getDamage() * 0.1);
                if (deflectedDamage > 0)
                    hit.getAttacker().hit(source, deflectedDamage, 1, null).setIsReflected().submit();
            }
        }*/
        super.onHit(hit);
    }

    @Override
    public void animate(int animation) {
        if (doingSiphon)
            return;
        super.animate(animation);
    }

    @Override
    public void graphic(int graphic) {
        if (doingSiphon)
            return;
        super.graphic(graphic);
    }

    public int getAttacksStage() {
        return getStage();
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getFlyTime() {
        return flyTime;
    }

    public void setFlyTime(int flyTime) {
        this.flyTime = flyTime;
    }

    public long getLastVirus() {
        return lastVirus;
    }

    public void setLastVirus(long lastVirus) {
        this.lastVirus = lastVirus;
    }

    public boolean isEmbracedShadow() {
        return embracedShadow;
    }

    public void setEmbracedShadow(boolean embracedShadow) {
        this.embracedShadow = embracedShadow;
    }

    public boolean isTrapsSettedUp() {
        return trapsSettedUp;
    }

    public void setTrapsSettedUp(boolean trapsSettedUp) {
        this.trapsSettedUp = trapsSettedUp;
    }

    public long getLastSiphon() {
        return lastSiphon;
    }

    public void setLastSiphon(long lastSiphon) {
        this.lastSiphon = lastSiphon;
    }

    public Npc[] getBloodReavers() {
        return bloodReavers;
    }

    public void killBloodReavers() {
        for (int index = 0; index < bloodReavers.length; index++) {
            if (bloodReavers[index] == null)
                continue;
            Npc npc = bloodReavers[index];
            bloodReavers[index] = null;
            if (npc.dead())
                return;
            heal(npc.hp());
            npc.die(null);
        }
    }

    public boolean isDoingSiphon() {
        return doingSiphon;
    }

    public void setDoingSiphon(boolean doingSiphon) {
        this.doingSiphon = doingSiphon;
    }

    public int getStage() {
        return stage;
    }
}
