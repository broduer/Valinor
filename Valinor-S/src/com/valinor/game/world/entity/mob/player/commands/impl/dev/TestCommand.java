package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.GameServer;
import com.valinor.game.content.areas.wilderness.content.hitman_services.Hitman;
import com.valinor.game.content.raids.RaidsType;
import com.valinor.game.content.raids.party.Party;
import com.valinor.game.content.raids.theatre_of_blood.TheatreOfBlood;
import com.valinor.game.content.raids.theatre_of_blood.TheatreOfBloodRewards;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
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

        //player.teleport(3166, 4323, player.tile().level);

        /*//Make a party
        Party.createParty(player);
        Party party = player.raidsParty;
        if (party == null) {
            return;
        }

        //Clear old loots
        player.getRaidRewards().clear();

        //Set TOB as raids
        party.setRaidsSelected(RaidsType.THEATRE_OF_BLOOD);
        player.setRaids(new TheatreOfBlood());

        //Teleport to loot room
        player.teleport(3234, 4318, player.tile().level);

        //Setup loot
        player.putAttrib(AttributeKey.PERSONAL_POINTS, 1_000_000);
        TheatreOfBloodRewards.giveRewards(player);

        //Spawn chest
        TheatreOfBlood.spawnLootChests(player);*/

        //Hitman.requestBounty(player);
        //Hitman.listOfBounties(player);

        player.message("Test command has been activated.");
        //PlayerSession.main(new String[0]);
    }

    @Override
    public boolean canUse(Player player) {
        return !GameServer.properties().production;
    }

}
