package com.valinor.game.content.teleport;

import com.valinor.GameServer;
import com.valinor.game.content.events.wilderness_key.WildernessKeyPlugin;
import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.content.tournaments.TournamentManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.nex.ZarosGodwars;
import com.valinor.game.world.entity.masks.animations.Animation;
import com.valinor.game.world.entity.masks.animations.Priority;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.player.MagicSpellbook;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.position.areas.impl.TournamentArea;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import com.valinor.util.timers.TimerKey;

import java.util.concurrent.TimeUnit;

/**
 * @author Patrick van Elderen | January, 10, 2021, 11:08
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class Teleports {

    /**
     * Determines if the player is able to teleport. The inform parameter
     * allows us to inform the player of the reason they cannot teleport
     * if we so wish to.
     */
    public static boolean canTeleport(Player player, boolean inform, TeleportType teletype) {
        if ((WildernessKeyPlugin.hasKey(player) && WildernessArea.inWilderness(player.tile()) && !player.getPlayerRights().isDeveloperOrGreater(player))) {
            player.message("You cannot teleport outside the Wilderness with the Wilderness key.");
            return false;
        }

        //Put most important safety in this method just to be sure
        if (TournamentManager.teleportBlocked(player, true)) {
            return false;
        }

        if (player.getController() != null) {
            if (!player.getController().canTeleport(player) && (!(player.getController() instanceof TournamentArea))) {
                player.message("You aren't allowed to teleport from here.");
                player.getInterfaceManager().closeDialogue();
                return false;
            }
        }

        if (Dueling.in_duel(player)) {
            if (inform) {
                player.message("You cannot teleport out of a duel.");
            }
            return false;
        }

        if (player.getTimers().has(TimerKey.SPECIAL_TELEBLOCK)) {
            if(inform) {
                long millis = player.getTimers().left(TimerKey.SPECIAL_TELEBLOCK) * 600L;
                player.message(String.format("A teleport block has been cast on you. It should wear off in %d minutes, %d seconds.", TimeUnit.MILLISECONDS.toMinutes(millis), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))));
            }
            return false;
        }

        if (player.getTimers().has(TimerKey.TELEBLOCK)) {
            if (inform) {
                player.teleblockMessage();
            }
            return false;
        }

        boolean fawkes = player.hasPetOut("Fawkes");

        var capLvl = teletype == TeleportType.ABOVE_20_WILD ? 30 : 20;
        if (WildernessArea.wildernessLevel(player.tile()) > capLvl && !fawkes && (!player.getPlayerRights().isDeveloperOrGreater(player))) {

            if (inform) {
                player.message("A mysterious force blocks your teleport spell!");
                player.message("You can't use this teleport after level " + capLvl + " wilderness.");
            }
            return false;
        }

        if (player.jailed()) {
            if (inform) {
                player.message("You can't leave the jail yet.");
            }
            return false;
        }

        //Is our player currently in an active Fight Cave?
        if (player.getMinigame() != null && !player.getMinigame().canTeleportOut()) {
            player.message("You cannot do that right now.");
            return false;
        }

        if (player.getTimers().has(TimerKey.BLOCK_SPEC_AND_TELE)) {
            player.message("<col=804080>Teleport blocked for " + player.getTimers().asSeconds(TimerKey.BLOCK_SPEC_AND_TELE) + " more secs after using spec at the start of a battle.");
            return false;
        }

        var mage_arena = player.<Boolean>getAttribOr(AttributeKey.MAGEBANK_MAGIC_ONLY, false);
        if (fawkes && mage_arena) {
            player.message("A mysterious force blocks your teleport, you can't use fawkes to escape from here!");
            return false;
        }

        if (player.looks().hidden()) {
            player.looks().hide(false);
        }

        return true;
    }

    public static void teleportToTarget(Player player, Tile targetTile) {
        player.lockNoDamage();
        if (player.getSpellbook() == MagicSpellbook.NORMAL) {
            //Modern spells
            player.animate(714);
            player.graphic(111, 92, 0);
        } else if (player.getSpellbook() == MagicSpellbook.ANCIENT) {
            //Ancient spells
            player.animate(1979);
            player.graphic(392);
        }

        Chain.bound(null).runFn(3, () -> {
            player.teleport(World.getWorld().randomTileAround(targetTile, 1));
            player.animate(-1);
            player.graphic(-1);
            player.unlock();
        });
    }

    public static boolean rolTeleport(Player player) {
        // rol ringoflife ring of life
        player.stopActions(true);
        return canTeleport(player, true, TeleportType.GENERIC);
    }

    public static void ringOfLifeTeleport(Player player) {
        player.lockNoDamage();
        player.animate(714);
        player.graphic(111, 92, 0);
        Chain.bound(null).runFn(3, () -> {
            player.teleport(3094, 3469); //Teleport the player edge coffin spot
            player.animate(-1);
            player.graphic(-1);
            player.unlock();
        });
    }

    public static void basicTeleport(Player player, Tile tile) {
        basicTeleport(player, tile, 714, new Graphic(111, 92));
    }

    public static void basicTeleport(Player player, Tile tile, int anim, Graphic gfx) {
        if(tile == null) {
            player.message("[NULL tile] Contact a staff member or report to the bugs channel.");
            return;
        }

        //If the player is locked or dead
        if (player.locked() || player.dead() || player.hp() <= 0)
            return;

        if (!player.getPlayerRights().isDeveloperOrGreater(player)) {
            if (player.jailed()) {
                player.message("You can't use commands when Jailed.");
                return;
            }
            if (WildernessArea.inWilderness(tile)) {
                if (player.getTimers().has(TimerKey.DEATH_TELEPORT_TIMER)) {
                    player.message("Quick wilderness teleports are off limits %ds <col=FF0000>after death.</col>", (int) Utils.ticksToSeconds(GameServer.properties().pkTelesAfterSetupSet));
                    return;
                }
            }
        } else {
            player.message("As an admin you bypass pk-tele restrictions.");
        }

        //Close all interfaces
        player.getInterfaceManager().close();

        //Stop the players actions
        player.stopActions(true);

        player.clearInstance();

        var raids = player.getRaids();
        if(raids != null) {
            raids.exit(player,true);
        }

        //trigger checks
        var instancedArea = InstancedAreaManager.getSingleton().ofZ(player.getZ());
        if (instancedArea != null)
            instancedArea.onTeleport(player, tile);

        ZarosGodwars.removeFromList(player);

        //remove from tourny
        TournamentManager.leaveTourny(player, false, true);

        player.lockNoDamage();
        player.animate(anim);
        player.graphic(gfx.id(), gfx.height(), gfx.delay());
        player.sound(200);
        Chain.bound(null).runFn(3, () -> {
            player.teleport(tile);
            player.animate(new Animation(-1, Priority.HIGH));
            player.graphic(-1);
            player.unlock();
        });
    }

    /**
     * For uninterruptable scripts (BOTS ONLY!)
     */
    public static void teleportContextless(Player player, Tile tile, int anim, Graphic gfx) {
        player.lockNoDamage();
        player.animate(anim);
        player.graphic(gfx.id(), gfx.height(), gfx.delay());
        player.sound(200);
        Chain.bound(null).runFn(3, () -> {
            player.teleport(tile);
            player.animate(-1);
            player.graphic(-1);
            player.unlock();
        });
    }

}
