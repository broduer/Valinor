package com.valinor.game.content.shootingStars;

import com.valinor.GameServer;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.action.impl.UnwalkableAction;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.skill.impl.mining.Mining;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.valinor.util.ObjectIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 27, 2021
 */
public class ShootingStars {

    private static final ShootingStars INSTANCE = new ShootingStars(null);

    public static ShootingStars getINSTANCE() {
        return INSTANCE;
    }

    public static final boolean DISABLED = false;
    private static int METEORITE_REMAINING = 1000;
    private static ShootingStars ACTIVE;
    private static GameObject rock;
    public static ArrayList<Player> players = new ArrayList<>(500);
    public static int STAR_CURRENCY = 25527;
    private static boolean SHOOTING_STAR_REMOVED = false;

    private static final ShootingStars[] SPAWNS = {
        new ShootingStars(new Tile(3299, 3303, 0)),
        new ShootingStars(new Tile(2830, 3200, 0)),
        new ShootingStars(new Tile(3045, 3470, 0)),
        new ShootingStars(new Tile(2572, 3411, 0)),
        new ShootingStars(new Tile(2969, 3654, 0)),
        new ShootingStars(new Tile(3287, 3354, 0)), //Varrock 1
        new ShootingStars(new Tile(3175, 3378, 0)), //Varrock 2
    };

    private static String getLocation() {
        if(ACTIVE.starSpawn.equals(3299, 3303)) {
            return "Alkharid mine";
        }
        if(ACTIVE.starSpawn.equals(2830, 3200)) {
            return "Karamja";
        }
        if(ACTIVE.starSpawn.equals(2572, 3411)) {
            return "Fishing Guild";
        }
        if(ACTIVE.starSpawn.equals(2969, 3654)) {
            return "Level 17 Wild.";
        }
        if(ACTIVE.starSpawn.equals(3045, 3470)) {
            return "Edgeville Monastery";
        }
        if(ACTIVE.starSpawn.equals(3287, 3354) || ACTIVE.starSpawn.equals(3175, 3378)) {
            return "Varrock mine";
        }
        return "Unknown";
    }

    private final Tile starSpawn;

    public ShootingStars(Tile starSpawn) {
        this.starSpawn = starSpawn;
    }

    private static void addStar() {
        rock = new GameObject(CRASHED_STAR_41223, new Tile(ACTIVE.starSpawn.getX(), ACTIVE.starSpawn.getY(), 0), 10, 0).spawn();
        METEORITE_REMAINING = 2200;
    }

    public static void addStar(int x, int y, int z) {
        rock = new GameObject(CRASHED_STAR_41223, new Tile(x, y, z), 10, 0).spawn();
        METEORITE_REMAINING = 2200;
    }

    private static void removeStar(boolean success) {
        if (rock != null) {
            rock.setId(-1);
            rock = null;
            SHOOTING_STAR_REMOVED = true;
            if (success) {
                String successMessage = "The Shooting star has been completely mined!";
                World.getWorld().sendWorldMessage("<img=452><shad=0><col=6a1a18> " + successMessage);
            }
        }
    }

    private static void removeShards(int amt) {
        METEORITE_REMAINING -= amt;
        if (METEORITE_REMAINING <= 0)
            METEORITE_REMAINING = 0;
    }

    private static void inspect(Player player) {
        DialogueManager.sendStatement(player, "The rock looks like it has "+METEORITE_REMAINING+" x fragments in it.");
    }

    /**
     * The interval at which server-wide shooting star events occur. Event runs every 45 minutes
     */
    public static final int EVENT_INTERVAL = GameServer.properties().production ? 4500 : 500;

    public void startEvent() {
        if (!DISABLED) {
            ShootingStars star = World.getWorld().get(SPAWNS);
            if (star != ACTIVE) {
                // Despawn the star if existing
                removeStar(false);
                ACTIVE = star;
                SHOOTING_STAR_REMOVED = false;
                String eventMessage = "There's been a sighting of a star around " + getLocation() + "!";
                World.getWorld().sendWorldMessage("<img=452><shad=0><col=6a1a18> " + eventMessage);
                addStar();

                last = LocalDateTime.now();
                next = LocalDateTime.now().plus((long) (EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);
            }
        }
    }

    public LocalDateTime last = LocalDateTime.now().minus((long) (EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);
    public LocalDateTime next = LocalDateTime.now().plus((long) (EVENT_INTERVAL * 0.6d), ChronoUnit.SECONDS);

    public static void onServerStart() {
        // every 45 mins
        TaskManager.submit(new ShootingStarEventTask());
    }

    public String timeTill(boolean displaySeconds) {
        LocalDateTime now = LocalDateTime.now();
        long difference = now.until(next, ChronoUnit.SECONDS);
        if (difference < 60 && displaySeconds) {
            return difference+" seconds";
        }
        difference = now.until(next, ChronoUnit.MINUTES);
        if (difference <= 2) {
            return 1+difference+" minutes";
        } else if (difference <= 59) {
            return difference+" minutes";
        } else {
            return (difference / 60)+"h "+difference % 60+"min";
        }
    }

    private static void mine(Player player) {
        Optional<Mining.Pickaxe> pick = Mining.findPickaxe(player);
        GameObject obj = player.getAttribOr(AttributeKey.INTERACTION_OBJECT, null);

        if (pick.isEmpty()) {
            player.sound(2277, 0);
            DialogueManager.sendStatement(player, "You do not have an pick-axe which you have the mining level to use.");
            return;
        }

        // Is the inventory full?
        if (player.getInventory().isFull() && !player.getInventory().contains(STAR_CURRENCY)) {
            DialogueManager.sendStatement(player, "Your inventory is too full to do this.");
            return;
        }

        player.faceObj(obj);
        player.animate(pick.get().crystalAnim);
        Chain.bound(player).runFn(1, () -> player.message("You swing your pickaxe at the rock."));
        player.action.execute(new UnwalkableAction(player, 4) {

            @Override
            public void execute() {
                // check obj
                if (!ObjectManager.objWithTypeExists(10, obj.tile()) && !ObjectManager.objWithTypeExists(11, obj.tile()) && !ObjectManager.objWithTypeExists(0, obj.tile())) {
                    player.resetAnimation();
                    stop();
                    return;
                }

                if (METEORITE_REMAINING <= 0) {
                    removeStar(true);
                    player.resetAnimation();
                    stop();
                    return;
                }

                if (rock == null) {
                    player.resetAnimation();
                    stop();
                    return;
                }

                if(SHOOTING_STAR_REMOVED) {
                    player.resetAnimation();
                    stop();
                    return;
                }

                var random = World.getWorld().random(1, 3);
                player.getInventory().add(STAR_CURRENCY, random);
                DailyTaskManager.increase(DailyTasks.STARDUST, player, random);
                player.skills().addXp(Skills.MINING, 30 * Mining.xpBonus(player), true);
                removeShards(random);
                player.message("You mine " + random + " "+ World.getWorld().definitions().get(ItemDefinition.class, STAR_CURRENCY).name.toLowerCase()+".");
                if(METEORITE_REMAINING > 500 && METEORITE_REMAINING <= 700) {
                    rock.setId(CRASHED_STAR_41224);
                }
                if(METEORITE_REMAINING >= 400 && METEORITE_REMAINING <= 500) {
                    rock.setId(CRASHED_STAR_41225);
                }
                if(METEORITE_REMAINING >= 300 && METEORITE_REMAINING <= 400) {
                    rock.setId(CRASHED_STAR_41226);
                }
                if(METEORITE_REMAINING >= 200 && METEORITE_REMAINING <= 300) {
                    rock.setId(CRASHED_STAR_41227);
                }
                if(METEORITE_REMAINING >= 100 && METEORITE_REMAINING <= 200) {
                    rock.setId(CRASHED_STAR_41228);
                }
                if(METEORITE_REMAINING >= 1 && METEORITE_REMAINING <= 100) {
                    rock.setId(CRASHED_STAR_41229);
                }
                player.animate(pick.get().crystalAnim);
            }
        });
    }

    public boolean handleObjectInteraction(Player player, GameObject object, int option) {
        List<Integer> crashedStars = Arrays.asList(CRASHED_STAR_41223, CRASHED_STAR_41224, CRASHED_STAR_41225, CRASHED_STAR_41226, CRASHED_STAR_41227, CRASHED_STAR_41228, CRASHED_STAR_41229);
        boolean objectMatches = crashedStars.stream().anyMatch(s -> s == object.getId());
        if(option == 1) {
            if(objectMatches) {
                mine(player);
                return true;
            }
        }
        if(option == 2) {
            if(objectMatches) {
                inspect(player);
                return true;
            }
        }
        return false;
    }
}
