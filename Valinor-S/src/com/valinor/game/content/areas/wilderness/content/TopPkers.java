package com.valinor.game.content.areas.wilderness.content;

import com.valinor.game.GameEngine;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.save.PlayerSave;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;
import java.util.*;

import static com.valinor.game.world.entity.AttributeKey.TOP_PKER_REWARD;
import static com.valinor.game.world.entity.AttributeKey.TOP_PKER_REWARD_UNCLAIMED;
import static com.valinor.util.CustomItemIdentifiers.DONATOR_MYSTERY_BOX;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 27, 2021
 */
public final class TopPkers extends Interaction {

    private static final Logger logger = LoggerFactory.getLogger(TopPkers.class);

    public static final TopPkers SINGLETON = new TopPkers();

    private static final LocalTime ANNOUNCEMENT_TIME = LocalTime.of(22, 0, 0); //10PM

    private final Map<String, Integer> totalKills = new HashMap<>();

    private final Task announcementTask = new Task("TopPkersAnnouncementTask") {
        private boolean announced = false;

        @Override
        public void execute() {
            LocalTime currentTime = LocalTime.now();
            if (currentTime.isAfter(ANNOUNCEMENT_TIME)) {
                if (!announced) {
                    announce();
                    announced = true;
                }
            } else {
                announced = false;
            }
        }
    };

    public void init() {
        TaskManager.submit(announcementTask);
    }

    public void increase(String username) {
        totalKills.merge(username, 1, Integer::sum);
    }

    private void announce() {
        broadcast("<sprite=780> <col=800000>Today's top PKers are now being announced:");

        var entry = getAndTakeTop();
        var position = 1;
        var details = "Nobody";
        var sprite = 203;

        if (entry != null) {
            var reward = new Item(DONATOR_MYSTERY_BOX);
            logger.info("{} was selected as todays {} best PK-er, being awarded with a {}.", entry.getUsername(), position, reward.name());
            String rewardMsg = "who has been awarded with a " + reward.name() + "!";
            details = Color.BLUE.tag() + "" + entry.getUsername() + " " + Color.BLACK.tag() + "with " + entry.getKills() + " kills - " + rewardMsg;
            give(entry, reward);
        }

        broadcast("<sprite=" + sprite + ">first: " + details);

        totalKills.clear();
    }

    private void broadcast(String message) {
        World.getWorld().getPlayers().forEach(player -> player.message(message));
    }

    private void give(KillEntry entry, Item reward) {
        String playerName = entry.getUsername();
        Optional<Player> playerToGive = World.getWorld().getPlayerByName(playerName);
        if (playerToGive.isEmpty()) {
            Player player = new Player();
            player.setUsername(playerName);
            GameEngine.getInstance().submitLowPriority(() -> {
                try {
                    if (PlayerSave.loadOfflineWithoutPassword(player)) {
                        player.putAttrib(TOP_PKER_REWARD_UNCLAIMED, true);
                        player.putAttrib(TOP_PKER_REWARD, reward);
                        PlayerSave.save(player);
                    } else {
                        logger.error("Something went wrong offline reward for offline Player " + player.getUsername());
                    }
                } catch (Exception e) {
                    logger.error("Something went wrong offline reward for offline Player " + player.getUsername());
                    logger.error("TopPkers give() error: ", e);
                }
            });
            return;
        }
        playerToGive.get().inventory().addOrBank(reward);
        playerToGive.get().message("Congratulations, you finished first in the top PKers!");
    }

    @Override
    public void onLogin(Player player) {
        var rewardUnclaimed = player.<Boolean>getAttribOr(TOP_PKER_REWARD_UNCLAIMED, false);

        //Reward wasn't claimed yet, lets claim.
        if (rewardUnclaimed) {
            Item reward = player.getAttribOr(TOP_PKER_REWARD, null);
            if (reward != null) {
                player.inventory().addOrBank(reward);
                player.message("Congratulations, you had the most kills and were awarded a " + reward.name() + "!");
                player.clearAttrib(TOP_PKER_REWARD_UNCLAIMED);
                player.clearAttrib(TOP_PKER_REWARD);
            }
        }
    }

    private KillEntry getAndTakeTop() {
        KillEntry top = getTop();
        if (top != null) {
            totalKills.remove(top.getUsername());
        }
        return top;
    }

    private KillEntry getTop() {
        Optional<Map.Entry<String, Integer>> entry = totalKills.entrySet().stream().max(Map.Entry.comparingByValue());
        return entry.map(stringIntegerEntry -> new KillEntry(stringIntegerEntry.getKey(), stringIntegerEntry.getValue())).orElse(null);
    }

    public void openLeaderboard(Player player) {
        if (totalKills.isEmpty()) {
            player.sendScroll("<col=800000>Today's Top Pkers", "Nobody");
            return;
        }

        //System.out.println(totalKills.toString());
        List<String> info = new ArrayList<>();

        totalKills.forEach((name, killCount) -> info.add(name + " Kills - " + killCount));

        player.sendScroll("<col=800000>Today's Top Pkers", info.toArray(new String[0]));
    }

    private static class KillEntry {

        private final String username;
        private final int kills;

        public KillEntry(String username, int kills) {
            this.username = username;
            this.kills = kills;
        }

        public String getUsername() {
            return username;
        }

        public int getKills() {
            return kills;
        }
    }

}
