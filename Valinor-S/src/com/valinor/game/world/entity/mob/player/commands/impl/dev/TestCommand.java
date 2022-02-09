package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.GameServer;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;

import static com.valinor.util.ItemIdentifiers.*;

public class TestCommand implements Command {

    private final List<Item> itemList = Arrays.asList(
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),

        //Row
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP),
        new Item(ABYSSAL_WHIP)
    );

    @Override
    public void execute(Player player, String command, String[] parts) {
        //System.out.println(player.pet().def().name);
        //player.getPacketSender().sendItemOnInterface(27201, itemList);
        //player.sound(2401);
        //ZarosGodwars.end();

        //Hunter.exec(1000 * 5); //5s
        //player.getPacketSender().sendScreenFade("", 1, 3);

       player.teleport(3169, 4386, player.tile().level+1);
        player.message("Test command has been activated.");
        //PlayerSession.main(new String[0]);
    }

    @Override
    public boolean canUse(Player player) {
        return !GameServer.properties().production;
    }

}
