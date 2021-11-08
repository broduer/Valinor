package com.valinor.game.world.entity.mob.npc.droptables.impl;

import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.NpcDeath;
import com.valinor.game.world.entity.mob.npc.droptables.Droptable;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;

import java.util.Optional;

import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.game.content.treasure.TreasureRewardCaskets.MASTER_CASKET;
import static com.valinor.game.world.entity.AttributeKey.DOUBLE_DROP_LAMP_TICKS;
import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen | January, 03, 2021, 14:37
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ZulrahDeath implements Droptable {

    @Override
    public void reward(Npc npc, Player killer) {
        var table = ScalarLootTable.forNPC(2042);
        if (table != null) {
            drop(npc, new Tile(2262, 3072, killer.tile().level), killer, new Item(ZULRAHS_SCALES, World.getWorld().random(100, 300)));

            Optional<Pet> pet = NpcDeath.checkForPet(killer, table);
            pet.ifPresent(value -> BOSSES.log(killer, npc.id(), new Item(value.item)));

            if (World.getWorld().rollDie(50, 1)) {
                drop(npc, new Tile(2262, 3072, killer.tile().level), killer, new Item(MASTER_CASKET,1));
                killer.message("<col=0B610B>You have received a treasure casket drop!");
            }

            var rolls = 2;
            var reward = table.randomItem(World.getWorld().random());
            for (int i = 0; i < rolls; i++) {
                if (reward != null) {
                    boolean doubleDropsLampActive = (Integer) killer.getAttribOr(DOUBLE_DROP_LAMP_TICKS, 0) > 0;
                    boolean founderImp = killer.pet() != null && killer.pet().def().name.equalsIgnoreCase("Founder Imp");
                    if (doubleDropsLampActive || founderImp) {
                        if(World.getWorld().rollDie(10, 1)) {
                            reward.setAmount(reward.getAmount() * 2);
                            killer.message("The double drop lamp effect doubled your drop.");
                        }
                    }
                    drop(npc, new Tile(2262, 3072, killer.tile().level), killer, reward);
                    ServerAnnouncements.tryBroadcastDrop(killer, npc, reward);
                }
            }

            // Slayer unlock
            if (killer.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.DOUBLE_DROP_CHANCE) && World.getWorld().rollDie(100, 1)) {
                killer.message("The Double drops perk grants you a second drop!");
                if (reward != null) {
                    drop(npc, new Tile(2262, 3072, killer.tile().level), killer, reward);
                    ServerAnnouncements.tryBroadcastDrop(killer, npc, reward);
                }
            }
        }
    }
}
