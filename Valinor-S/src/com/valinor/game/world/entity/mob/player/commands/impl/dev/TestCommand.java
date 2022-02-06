package com.valinor.game.world.entity.mob.player.commands.impl.dev;

import com.valinor.GameServer;
import com.valinor.db.transactions.GetBanStatusDatabaseTransaction;
import com.valinor.db.transactions.GetMuteStatusDatabaseTransaction;
import com.valinor.game.GameEngine;
import com.valinor.game.content.clan.ClanManager;
import com.valinor.game.content.instance.impl.NightmareInstance;
import com.valinor.game.content.mechanics.AntiSpam;
import com.valinor.game.content.mechanics.Censor;
import com.valinor.game.content.minigames.impl.Barrows;
import com.valinor.game.content.raids.party.Party;
import com.valinor.game.content.raids.theatre_of_blood.SupplyChest;
import com.valinor.game.content.raids.theatre_of_blood.TheatreOfBlood;
import com.valinor.game.content.skill.impl.hunter.Hunter;
import com.valinor.game.task.TaskManager;
import com.valinor.game.task.impl.ForceMovementTask;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.hit.SplatType;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.Nex;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.ZarosGodwars;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.ForceMovement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.Command;
import com.valinor.game.world.entity.mob.player.commands.CommandManager;
import com.valinor.game.world.entity.mob.player.commands.impl.players.YellCommand;
import com.valinor.game.world.entity.mob.player.rights.MemberRights;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.net.PlayerSession;
import com.valinor.net.login.LoginResponses;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.flood.Buffer;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.NexCombat.NO_ESCAPE_TELEPORTS;
import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.NEX;

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

        for (int i = 0; i < 100; i++) {
            if (GameServer.properties().enableSql) {
                //GameEngine.getInstance().submitLowPriority(() -> {
                    try {
                        //Here we use execute instead of submit, since we want this to be executed synchronously and not asynchronously, since we want to wait for the response of the query before continuing execution in this LoginResponses class.
                        player.muted = GameServer.getDatabaseService().execute(new GetMuteStatusDatabaseTransaction(player.getUsername()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                //});
            }
        }

        player.message("Test command has been activated.");
        //PlayerSession.main(new String[0]);
    }

    @Override
    public boolean canUse(Player player) {
        return !GameServer.properties().production;
    }

}
