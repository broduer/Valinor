package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.game.content.skill.impl.fishing.Fishing;
import com.valinor.game.content.skill.impl.hunter.Impling;
import com.valinor.game.world.World;
import com.valinor.game.world.definition.loader.impl.ObjectSpawnDefinitionLoader;
import com.valinor.game.world.definition.loader.impl.PatrickTheMerchantLoader;
import com.valinor.game.world.definition.loader.impl.ProtectionValueLoader;
import com.valinor.game.world.definition.loader.impl.ShopLoader;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;

import static java.lang.String.format;

public class ReloadCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ReloadCommand.class);

    @Override
    public void execute(Player player, String command, String[] parts) {
        String reload = parts[1];if (reload.equalsIgnoreCase("merchantv")) {
            player.message("Reloading patrick the merchant values...");
            new PatrickTheMerchantLoader().run();
            player.message("Finished.");
        } else if (reload.equalsIgnoreCase("protv")) {
            player.message("Reloading protection values...");
            new ProtectionValueLoader().run();
            player.message("Finished.");
        } else if (reload.equalsIgnoreCase("shops")) {
            player.message("Reloading shops...");
            new ShopLoader().run();
            player.message("Finished.");
        } else if (reload.equalsIgnoreCase("npcs")) {
            player.message("Reloading npcs...");
            for (Npc worldNpcs : World.getWorld().getNpcs()) {
                if(worldNpcs == null || worldNpcs.def().ispet) {
                    continue;
                }
                World.getWorld().unregisterNpc(worldNpcs);
            }
            //Halloween.loadNpcs();
            //Christmas.loadNpcs();
            Impling.onServerStartup();
            World.loadNpcSpawns(new File("data/map/npcs"));
            try {
                Fishing.respawnAllSpots(World.getWorld());
            } catch (FileNotFoundException e) {
                logger.catching(e);
            }
            player.message(format("Reloaded %d npcs. <col=ca0d0d>Warning: Npcs in Instances will not be respawned.", World.getWorld().getNpcs().size()));
            player.message("<col=ca0d0d>Must be done manually.");
        } else if (reload.equalsIgnoreCase("drops")) {
            player.message("Reloading drops...");
            World.getWorld().loadDrops();
            player.message("Finished.");
        } else if (reload.equalsIgnoreCase("equipinfo")) {
            player.message("Reloading equipment info...");
            World.getWorld().loadEquipmentInfo();
            player.message("Reloaded equip info.");
        } else if (reload.equalsIgnoreCase("combatdefs") || reload.equalsIgnoreCase("npcinfo")) {
            player.message("Reloading npc combat info...");
            World.getWorld().loadNpcCombatInfo();

            // Reload npcs
            World.getWorld().getNpcs().forEach(n -> {
                if (n != null) {
                    n.combatInfo(World.getWorld().combatInfo(n.id()));
                }
            });
            player.message("Finished.");
        } else if (reload.equalsIgnoreCase("objects")) {
            player.message("Reloading objects...");
            //TODO ask Jak why this is broken
            /*World.getWorld().getObjects().forEach(obj -> {
                if(obj != null) {
                    ObjectManager.removeObj(obj);
                }
            });*/
            new ObjectSpawnDefinitionLoader().run();
            player.message("Finished.");
        }
    }

    @Override
    public boolean canUse(Player player) {
        return (player.getPlayerRights().isDeveloperOrGreater(player) || player.getUsername().equalsIgnoreCase("Chaotic jr"));
    }

}
