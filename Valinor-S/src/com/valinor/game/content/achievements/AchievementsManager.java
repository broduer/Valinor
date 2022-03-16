package com.valinor.game.content.achievements;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.util.Color;
import com.valinor.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

import static com.valinor.game.world.entity.AttributeKey.ACHIEVEMENT_POINTS;
import static com.valinor.game.world.entity.mob.player.QuestTab.InfoTab.SLAYER_POINTS;
import static java.lang.String.format;

/**
 * @author PVE
 * @Since juli 08, 2020
 */
public class AchievementsManager {

    private static final Logger logger = LogManager.getLogger(AchievementsManager.class);

    public static void activate(Player player, Achievements achievement, int increaseBy) {
        //Can't increase during tourneys
        if (player.inActiveTournament() || player.isInTournamentLobby()) {
            return;
        }

        //The user box test can't complete achievements.
        if (player.getUsername().equalsIgnoreCase("Box test")) {
            return;
        }

        final int current = player.achievements().computeIfAbsent(achievement, a -> 0);

        if (current >= achievement.getCompleteAmount())
            return;

        player.achievements().put(achievement, current + increaseBy);

        if (player.achievements().get(achievement) >= achievement.getCompleteAmount()) {
            int achievementsCompleted = player.getAttribOr(AttributeKey.ACHIEVEMENTS_COMPLETED, 0);
            player.putAttrib(AttributeKey.ACHIEVEMENTS_COMPLETED, achievementsCompleted);

            //When achievements complete, check if we can complete the COMPLETIONIST achievement.
            if (player.completedAllAchievements()) {
                activate(player, Achievements.COMPLETIONIST, 1);
            }

            //Complete all cooking achievements to become a chef
            if (isCompleted(player, Achievements.BAKER) && isCompleted(player, Achievements.COOK)) {
                activate(player, Achievements.CHEF, 1);
            }

            player.message("<col=297A29>Congratulations! You have completed the " + achievement.getName() + " achievement.");
            World.getWorld().sendWorldMessage(format("<img=1051>[<col=" + Color.MEDRED.getColorValue() + ">Achievement</col>]: %s just completed the %s achievement.", (PlayerRights.getCrown(player) + player.getUsername()), achievement.getName()));

            if (achievement.otherRewardString() != null) {
                checkForOtherReward(player, achievement);
                var points = player.<Integer>getAttribOr(ACHIEVEMENT_POINTS, 0) + achievement.points();
                player.putAttrib(ACHIEVEMENT_POINTS, points);
                player.getPacketSender().sendString(QuestTab.InfoTab.ACHIEVEMENT_POINTS.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.ACHIEVEMENT_POINTS.childId).fetchLineData(player));
            }

            Item[] reward = achievement.getReward();
            if (reward != null) {
                if (player.gameMode().isUltimateIronman()) {
                    player.inventory().addOrDrop(reward.clone());
                } else {
                    for (Item item : reward.clone()) {
                        player.getBank().depositFromNothing(item);
                    }
                }
                Utils.sendDiscordInfoLog(player.getUsername() + " has completed " + achievement.getName() + " and got " + Arrays.toString(reward.clone()), "achievements");
            }
        }
    }

    private static void checkForOtherReward(Player player, Achievements achievement) {
        switch (achievement) {

        }
    }

    public static boolean isCompleted(Player player, Achievements achievement) {
        return player.achievements().get(achievement) >= achievement.getCompleteAmount();
    }
}
