package com.valinor.game.content.raids.theatre_of_blood;

import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.mechanics.Poison;
import com.valinor.game.content.raids.Raids;
import com.valinor.game.content.raids.RaidsNpc;
import com.valinor.game.content.raids.party.Party;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.Venom;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Color;

import static com.valinor.game.world.entity.AttributeKey.PERSONAL_POINTS;
import static com.valinor.util.ItemIdentifiers.DAWNBRINGER;
import static com.valinor.util.NpcIdentifiers.*;
import static com.valinor.util.ObjectIdentifiers.CHEST_32758;
import static com.valinor.util.ObjectIdentifiers.MONUMENTAL_CHEST;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 07, 2022
 */
public class TheatreOfBlood extends Raids {

    @Override
    public void startup(Player player) {
        Party party = player.raidsParty;
        if (party == null) return;
        party.setRaidStage(1);
        final int height = party.getLeader().getIndex() * 4;

        for (Player member : party.getMembers()) {
            member.setRaids(this);
            member.teleport(new Tile((3218 + World.getWorld().random(0, 3)), (4459 + World.getWorld().random(0, 1)), height));
            party.setHeight(height);
        }

        //Clear kills
        party.setKills(0);

        //Clear npcs that somehow survived first
        clearParty(player);

        //Spawn all monsters
        spawnMonsters(player);

        //Spawn all resources chests
        spawnChests(player);
    }

    @Override
    public void exit(Player player) {
        player.setRaids(null);

        Party party = player.raidsParty;

        //Remove players from the party if they are not the leader
        if(party != null) {
            party.removeMember(player);
            //Last player in the party leaves clear the whole thing
            if(party.getMembers().size() == 0) {
                //Clear all party members that are left
                party.getMembers().clear();
                clearParty(player);
            }
            player.raidsParty = null;
        }

        //Reset points
        player.putAttrib(PERSONAL_POINTS,0);
        player.message("<col=" + Color.BLUE.getColorValue() + ">You have restored your hitpoints, run energy and prayer.");
        player.message("<col=" + Color.HOTPINK.getColorValue() + ">You've also been cured of poison and venom.");
        player.skills().resetStats();
        int increase = player.getEquipment().hpIncrease();
        player.hp(Math.max(increase > 0 ? player.skills().level(Skills.HITPOINTS) + increase : player.skills().level(Skills.HITPOINTS), player.skills().xpLevel(Skills.HITPOINTS)), 39); //Set hitpoints to 100%
        player.skills().replenishSkill(5, player.skills().xpLevel(5)); //Set the players prayer level to full
        player.setRunningEnergy(100.0, true);
        Poison.cure(player);
        Venom.cure(2, player);

        //Clear any dawnbringers
        player.removeAll(new Item(DAWNBRINGER));

        //Move outside of raids
        player.teleport(1245, 3561, 0);
        player.getInterfaceManager().close(true);
    }

    @Override
    public void complete(Party party) {
        party.forPlayers(p -> {
            p.message(Color.RAID_PURPLE.wrap("Congratulations - your raid is complete!"));
            var completed = p.<Integer>getAttribOr(AttributeKey.THEATRE_OF_BLOOD_RUNS_COMPLETED, 0) + 1;
            p.putAttrib(AttributeKey.THEATRE_OF_BLOOD_RUNS_COMPLETED, completed);
            p.message(String.format("Total points: " + Color.RAID_PURPLE.wrap("%,d") + ", Personal points: " + Color.RAID_PURPLE.wrap("%,d") + " (" + Color.RAID_PURPLE.wrap("%.2f") + "%%)",
                party.totalPoints(), p.<Integer>getAttribOr(PERSONAL_POINTS, 0), (double) (p.<Integer>getAttribOr(PERSONAL_POINTS, 0) / party.totalPoints()) * 100));

            //Daily raids task
            DailyTaskManager.increase(DailyTasks.RAIDING, p);
        });
    }

    @Override
    public void clearParty(Player player) {
        Party party = player.raidsParty;
        if(party == null) return;
        if(party.monsters == null) {
            return;
        }
        for(Npc npc : party.monsters) {
            //If npc is alive remove them
            if(npc.isRegistered() || !npc.dead()) {
                World.getWorld().unregisterNpc(npc);
            }
        }
        party.monsters.clear();

        if(party.objects == null) {
            return;
        }
        for(GameObject obj : party.objects) {
            ObjectManager.removeObj(obj);
        }
        party.objects.clear();
    }

    @Override
    public boolean death(Player player) {
        Party party = player.raidsParty;
        if (party == null) return false;
        player.teleport(respawnTile(party, player.tile().level));
        int pointsLost = (int) (player.<Integer>getAttribOr(PERSONAL_POINTS, 0) * 0.4);
        if (pointsLost > 0)
            addPoints(player, -pointsLost);

        //Make sure to heal
        player.healPlayer();

        party.bossFightLives.put(player.getUsername().toLowerCase(), 0);
        if (party.teamDead()) {
            party.getMembers().forEach(p -> p.teleport(new Tile(1245, 3561, 0)));
            party.getMembers().forEach(p -> p.putAttrib(AttributeKey.THEATRE_OF_BLOOD_POINTS, 0));
            party.getMembers().forEach(p -> p.message("Unfortunately your team has failed Theatre of Blood!"));
        } else {
            Tile deathTile = respawnTile(party, player.tile().level);
            player.teleport(deathTile.getX(), deathTile.getY(), party.getHeight());
        }

        return true;
    }

    @Override
    public Tile respawnTile(Party party, int level) {
        switch (party.getRaidStage()) {
            case 1://maiden
                return new Tile(3190, 4446, level);
            case 2://bloat
                return new Tile(3309, 4447, level);
            case 3://vasilias
                return new Tile(3295, 4262, level);
            case 4://sotetseg
                return new Tile(3280, 4302, level);
            case 5://xarpus
                return new Tile(3280, 4293, level);
            case 6://verzik
                int[] xPos = {3179, 3177, 3175, 3161, 3159, 3157};
                int yPos = 4325;
                for (int x : xPos) {
                    boolean playerInPos = party.getMembers().stream().anyMatch(plr -> plr.tile().equalsIgnoreHeight(Tile.of(x, yPos)));
                    if (!playerInPos) {
                        return Tile.of(x, yPos);
                    }
                }
                return Tile.of(3179, yPos);
            default:
                throw new IllegalStateException("Unexpected value: " + party.getRaidStage());
        }
    }

    @Override
    public void addPoints(Player player, int points) {
        if (!raiding(player))
            return;
        player.raidsParty.addPersonalPoints(player, points);
    }

    @Override
    public void addDamagePoints(Player player, Npc target, int points) {
        if (!raiding(player))
            return;
        if (target.getAttribOr(AttributeKey.RAIDS_NO_POINTS, false))
            return;
        points *= 10;
        addPoints(player, points);
    }

    private void spawnMonsters(Player player) {
        //Get the raids party
        Party party = player.raidsParty;

        //Create
        Npc maiden = new RaidsNpc(THE_MAIDEN_OF_SUGADINTI, new Tile(3162, 4444, party.getHeight()), party.getSize()).spawn(false);
        maiden.cantFollowUnderCombat(true);
        Npc bloat = new RaidsNpc(PESTILENT_BLOAT, new Tile(3299, 4440, party.getHeight()), party.getSize()).spawn(false);
        bloat.noRetaliation(true);
        bloat.cantFollowUnderCombat(true);
        bloat.resetFaceTile();
        Npc vasilias = new RaidsNpc(NYLOCAS_VASILIAS_8355, new Tile(3293, 4246, party.getHeight()), party.getSize()).spawn(false);
        vasilias.cantFollowUnderCombat(true);
        Npc sotetseg = new RaidsNpc(SOTETSEG_8388, new Tile(3278, 4329, party.getHeight()), party.getSize()).spawn(false);
        sotetseg.cantFollowUnderCombat(true);
        Npc xarpus = new RaidsNpc(XARPUS, new Tile(3169, 4386, party.getHeight()+1), party.getSize()).spawn(false);
        xarpus.cantFollowUnderCombat(true);
        Npc verzik = new RaidsNpc(VERZIK_VITUR_8369, new Tile(3166, 4323,party.getHeight()), party.getSize()).spawn(false);
        verzik.cantFollowUnderCombat(true);

        //Add to list
        party.monsters.add(maiden);
        party.monsters.add(bloat);
        party.monsters.add(vasilias);
        party.monsters.add(sotetseg);
        party.monsters.add(xarpus);
        party.monsters.add(verzik);

        party.setupBossLives();
    }

    private void spawnChests(Player player) {
        //Get the raids party
        Party party = player.raidsParty;

        GameObject chest1 = new GameObject(CHEST_32758, new Tile(3175, 4422, party.getHeight()),10,2).spawn(); //maiden
        GameObject chest2 = new GameObject(CHEST_32758, new Tile(3303, 4277, party.getHeight()), 10, 5).spawn(); //Vasilias
        GameObject chest3 = new GameObject(CHEST_32758, new Tile(3278, 4293, party.getHeight()), 10, 2).spawn(); //sotetseg
        GameObject chest4 = new GameObject(CHEST_32758, new Tile(3171, 4399, party.getHeight() + 1), 10, 5).spawn(); //xarpus
        GameObject lootChest = new GameObject(MONUMENTAL_CHEST, new Tile(3233, 4319, party.getHeight()), 10, 0).spawn(); //loot chest

        party.objects.add(chest1);
        party.objects.add(chest2);
        party.objects.add(chest3);
        party.objects.add(chest4);
        party.objects.add(lootChest);
    }
}
