package com.valinor.game.content.seasonal_events.christmas;

import com.valinor.GameServer;
import com.valinor.game.content.seasonal_events.halloween.Halloween;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.masks.Projectile;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.equipment.EquipmentInfo;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.TargetRoute;
import com.valinor.net.packet.interaction.PacketInteraction;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import com.valinor.util.chainedwork.Chain;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;

import static com.valinor.game.content.seasonal_events.rewards.UnlockEventRewards.UNLOCKED_ITEM_SLOT;
import static com.valinor.game.content.skill.impl.hunter.Impling.OVERWORLD_RANDOM_SPAWN_TILES;
import static com.valinor.util.CustomItemIdentifiers.XMAS_TOKENS;
import static com.valinor.util.CustomNpcIdentifiers.SANTA;
import static com.valinor.util.CustomNpcIdentifiers.ICE_IMP;
import static com.valinor.util.ItemIdentifiers.SNOWBALL;
import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 30, 2021
 */
public class Christmas extends PacketInteraction {

    private static final Logger logger = LogManager.getLogger(Halloween.class);

    public static int ACTIVE_OVERWORLD_ICE_IMP = 0;
    private static final int OVERWORLD_MAX_ICE_IMP = 200;

    public static void loadNpcs() {
        Npc santa = new Npc(SANTA, new Tile(3096, 3486));
        World.getWorld().registerNpc(santa);

        loadIceImp();
        logger.info("Loaded christmas npcs.");
    }

    public static void loadObjects() {
        GameObject snow = new GameObject(SNOW, new Tile(3098, 3501));
        GameObject snow2 = new GameObject(SNOW_15616, new Tile(3098, 3492));
        GameObject snow3 = new GameObject(SNOW_15617, new Tile(3089, 3485));
        GameObject snow4 = new GameObject(SNOW_19030, new Tile(3081, 3489));
        GameObject snow5 = new GameObject(SNOW_19030, new Tile(3085, 3506));
        GameObject snow6 = new GameObject(SNOW_19030, new Tile(3090, 3514));

        ObjectManager.addObj(snow);
        ObjectManager.addObj(snow2);
        ObjectManager.addObj(snow3);
        ObjectManager.addObj(snow4);
        ObjectManager.addObj(snow5);
        ObjectManager.addObj(snow6);
    }

    private static void loadIceImp() {
        for (Tile tile : IceImpSpawnTiles.lumbridgeSpawns) {
            Npc iceImp = new Npc(ICE_IMP, tile);
            iceImp.walkRadius(5);
            iceImp.getRouteFinder().routeAbsolute(iceImp.getSpawnArea().randomX(), iceImp.getSpawnArea().randomY());
            World.getWorld().registerNpc(iceImp);
        }

        for (Tile tile : IceImpSpawnTiles.draynorSpawns) {
            Npc iceImp = new Npc(ICE_IMP, tile);
            iceImp.walkRadius(5);
            iceImp.getRouteFinder().routeAbsolute(iceImp.getSpawnArea().randomX(), iceImp.getSpawnArea().randomY());
            World.getWorld().registerNpc(iceImp);
        }

        for (Tile tile : IceImpSpawnTiles.varrockSpawns) {
            Npc iceImp = new Npc(ICE_IMP, tile);
            iceImp.walkRadius(5);
            iceImp.getRouteFinder().routeAbsolute(iceImp.getSpawnArea().randomX(), iceImp.getSpawnArea().randomY());
            World.getWorld().registerNpc(iceImp);
        }

        for (Tile tile : IceImpSpawnTiles.faladorSpawns) {
            Npc iceImp = new Npc(ICE_IMP, tile);
            iceImp.walkRadius(5);
            iceImp.getRouteFinder().routeAbsolute(iceImp.getSpawnArea().randomX(), iceImp.getSpawnArea().randomY());
            World.getWorld().registerNpc(iceImp);
        }

        for (Tile tile : IceImpSpawnTiles.wildernessSpawns) {
            Npc iceImp = new Npc(ICE_IMP, tile);
            iceImp.walkRadius(5);
            iceImp.getRouteFinder().routeAbsolute(iceImp.getSpawnArea().randomX(), iceImp.getSpawnArea().randomY());
            World.getWorld().registerNpc(iceImp);
        }

        Chain.bound(null).name("ChristmasIceImpTask").repeatingTask(100, t -> {
            if (ACTIVE_OVERWORLD_ICE_IMP < OVERWORLD_MAX_ICE_IMP) {
                spawnRandomIceImpOverworld();
            }
        });
    }

    private static void spawnRandomIceImpOverworld() {
        Tile tile = Utils.randomElement(OVERWORLD_RANDOM_SPAWN_TILES);
        Npc impling = new Npc(ICE_IMP, new Tile(tile.getX(), tile.getY(), tile.getZ()));
        impling.walkRadius(16);
        impling.getRouteFinder().routeAbsolute(impling.getSpawnArea().randomX(), impling.getSpawnArea().randomY());
        World.getWorld().registerNpc(impling);
        ACTIVE_OVERWORLD_ICE_IMP++;
    }

    @Override
    public boolean handleButtonInteraction(Player player, int button) {
        if (button == 73307) {
            player.optionsTitled("Exchange your 5,000 X'mas tokens for a reward?", "Yes", "No", () -> {
                if (!player.inventory().contains(new Item(XMAS_TOKENS, 10_000))) {
                    return;
                }

                var unlockedAllRewards = player.getEventRewards().rewardsUnlocked().values().stream().allMatch(r -> r);
                if (unlockedAllRewards) {
                    player.message(Color.RED.wrap("You have already unlocked all rewards."));
                    return;
                }

                Item reward = player.getEventRewards().generateReward();
                if (reward == null) {
                    return;
                }

                if (player.inventory().contains(new Item(XMAS_TOKENS, 10_000))) {
                    player.getEventRewards().refreshItems();
                    player.getPacketSender().sendItemOnInterfaceSlot(UNLOCKED_ITEM_SLOT, reward.copy(), 0);
                    player.getEventRewards().rollForReward(XMAS_TOKENS, 10_000, reward.copy(), "X'mas");
                } else {
                    player.message(Color.RED.wrap("You do not have enough X'mas tokens to roll for a reward."));
                }
            });
            return true;
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        if (option == 1) {
            if (object.getId() == 2654) {
                if (GameServer.properties().christmas) {
                    player.getEventRewards().open("X'mas");
                }
                return true;
            }

            List<Integer> snow = Arrays.asList(SNOW, SNOW_15616, SNOW_15617, SNOW_19030);
            if (snow.stream().anyMatch(s -> s == object.getId())) {
                if (GameServer.properties().christmas) {
                    takeSnow(player, object);
                }
                return true;
            }
        }
        return false;
    }

    private void takeSnow(Player player, GameObject object) {
        player.faceObj(object);
        if (!player.getInventory().hasCapacityFor(new Item(SNOWBALL))) {
            player.message("You don't have enough room to carry snow!");
            return;
        }

        Chain.bound(player).runFn(1, () -> {
            player.message("You attempt to make some snowballs...");
            player.animate(5067);
            player.getInventory().add(SNOWBALL, 3);
        }).repeatingTask(5, r -> {
            if (!player.getInventory().hasCapacityFor(new Item(SNOWBALL)))
                return;

            player.animate(5067);
            player.getInventory().add(SNOWBALL, 3);
        });
    }

    public static void throwSnow(Mob mob, Mob target) {
        Player player = mob.getAsPlayer();
        Player playerTarget = target.getAsPlayer();
        if (target != null && target.isNpc()) {
            player.message("I don't think they'd find this too amusing...");
            return;
        }

        Chain.bound(mob).repeatingTask(1, r -> {
            // Ran out of snowballs
            if (!player.getEquipment().contains(SNOWBALL)) {
                r.stop();
                return;
            }

            // Begin by clearing the path queue
            player.getMovementQueue().clear();

            player.face(playerTarget.tile());

            // Are we in range distance?
            TargetRoute.set(player, target, 5);

            // Are we stunned?
            if (player.stunned()) {
                return;
            }

            // Wait for cool down
            if (!player.snowballCooldown.isDelayed()) {
                // Throw
                player.snowballCooldown.delay(4);
                player.animate(5063);
                playerTarget.graphic(862, 0, 50);
                playerTarget.animate(EquipmentInfo.blockAnimationFor(playerTarget), 50);
                player.getEquipment().remove(10501, 1);
                if (player.getEquipment().count(10501) < 1) {
                    player.getPacketSender().sendInteractionOption("null", 1, false); //Remove pelt option
                }
                Projectile projectile = new Projectile(player, playerTarget, 861, 50, 62, 30, 22, 0);
                projectile.sendProjectile();
            }
        });
    }
}
