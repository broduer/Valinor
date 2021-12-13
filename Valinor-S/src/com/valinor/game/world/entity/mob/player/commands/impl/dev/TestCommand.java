
package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.GameServer;
import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        System.out.println(World.getWorld().getPlayers().size());
        player.message("Test command has been activated.");
    }

    @Override
    public boolean canUse(Player player) {
        return !GameServer.properties().production;
    }

}
