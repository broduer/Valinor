package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.GameServer;
import com.valinor.game.content.instance.impl.NightmareInstance;
import com.valinor.game.content.raids.party.Party;
import com.valinor.game.content.raids.theatre_of_blood.TheatreOfBlood;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.commands.CommandManager;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;

import java.util.Arrays;
import java.util.List;

public class TestCommand implements Command {

    private final List<Item> itemList = Arrays.asList(
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        //Row
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),

        //Row
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),

    //Row
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),

    //Row
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),

    //Row
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),

    //Row
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151),
        new Item(4151)
    );

    @Override
    public void execute(Player player, String command, String[] parts) {
        //System.out.println(player.pet().def().name);
        //player.getPacketSender().sendItemOnInterface(67541, itemList);
        //player.sound(2401);
        Party.createParty(player);
        player.setRaids(new TheatreOfBlood());
        Party.startRaid(player);
        player.getRaids().startup(player);
        player.teleport(3166,4323, player.raidsParty.getHeight());
        CommandManager.attempt(player, "invu");
        CommandManager.attempt(player, "alwayshit 500");
        player.message("Test command has been activated.");
    }

    @Override
    public boolean canUse(Player player) {
        return !GameServer.properties().production;
    }

}
