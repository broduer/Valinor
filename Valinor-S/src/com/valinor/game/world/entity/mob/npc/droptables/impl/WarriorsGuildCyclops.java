package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.util.NpcIdentifiers;
import com.valinor.util.Utils;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | March, 26, 2021, 18:59
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class WarriorsGuildCyclops implements Droptable {

    @Override
    public void reward(Npc killed, Player killer) {
        int defender = killer.getAttribOr(AttributeKey.WARRIORS_GUILD_CYCLOPS_ROOM_DEFENDER, BRONZE_DEFENDER);
        int chance = killer.getPlayerRights().isDeveloperOrGreater(killer) ? 2 : 30;

        //All the way up to rune
        if(killed.id() == NpcIdentifiers.CYCLOPS_2464 || killed.id() == NpcIdentifiers.CYCLOPS_2465) {
            if (Utils.rollDie(chance, 1)) {
                var def = World.getWorld().definitions().get(ItemDefinition.class, defender);
                String aOrAn;
                if (defender == 8845 || defender == 8849)
                    aOrAn = "an";
                else
                    aOrAn = "a";

                //Defender drop
                drop(killed, killer, new Item(defender));
                killer.message("<col=804080>The Cyclops drops " + aOrAn + " " + def.name + ". Be sure to leave the door to unlock");
                killer.message("<col=804080>the next tier Defender!");

                switch (defender) {
                    case BRONZE_DEFENDER -> AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_I, 1);
                    case IRON_DEFENDER -> AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_II, 1);
                    case STEEL_DEFENDER -> AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_III, 1);
                    case BLACK_DEFENDER -> AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_IV, 1);
                    case MITHRIL_DEFENDER -> AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_V, 1);
                    case ADAMANT_DEFENDER -> AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_VI, 1);
                    case RUNE_DEFENDER -> AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_VII, 1);
                }
            }
        } else if (killed.id() == NpcIdentifiers.CYCLOPS_2137) {//Dragon defender
            chance = killer.getPlayerRights().isDeveloperOrGreater(killer) ? 2 : 70;
            if (World.getWorld().rollDie(chance, 1)) {
                //Defender drop
                drop(killed, killer, new Item(defender));
                var count = killer.<Integer>getAttribOr(AttributeKey.DRAGON_DEFENDER_DROPS, 0) + 1;
                killer.putAttrib(AttributeKey.DRAGON_DEFENDER_DROPS, count);
                AchievementsManager.activate(killer, Achievements.DEFENDER_HUNT_IX, 1);
            }

            //Normal drop
            var table = ScalarLootTable.forNPC(NpcIdentifiers.CYCLOPS_2137);
            if (table != null) {
                var reward = table.randomItem(World.getWorld().random());
                drop(killed, killer.tile(), killer, reward);
            }
        }
    }
}
