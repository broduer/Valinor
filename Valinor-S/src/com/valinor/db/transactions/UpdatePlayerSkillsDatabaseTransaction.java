package com.valinor.db.transactions;

import com.valinor.db.VoidDatabaseTransaction;
import com.valinor.db.statement.NamedPreparedStatement;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 13, 2021
 */
public final class UpdatePlayerSkillsDatabaseTransaction extends VoidDatabaseTransaction {

    private static final Logger logger = LogManager.getLogger(UpdatePlayerSkillsDatabaseTransaction.class);

    private final Player player;

    public UpdatePlayerSkillsDatabaseTransaction(Player player) {
        this.player = player;
    }

    @Override
    public void executeVoid(Connection connection) throws SQLException {
        try (NamedPreparedStatement s2 = prepareStatement(connection, "SELECT id FROM users WHERE lower(username) = :user")) {
            s2.setString("user", player.getUsername().toLowerCase());
            s2.execute();
            if (s2.getResultSet().next()) {
                final int pid = s2.getResultSet().getInt("id");

                try (NamedPreparedStatement s3 = prepareStatement(connection, "DELETE FROM player_skills WHERE player_id = :pid")) {
                    s3.setInt("pid", pid);
                    s3.execute();
                }

                for (int skillId = 0; skillId < Skills.SKILL_COUNT; skillId ++) {
                    try (NamedPreparedStatement s4 = prepareStatement(connection, "INSERT INTO player_skills (skill_id, skill_lvl, skill_xp, player_id) VALUES (:s_id, :s_lvl, :s_xp, :pid)")) {
                        s4.setInt("pid", pid);
                        s4.setInt("s_id", skillId);
                        s4.setInt("s_lvl", player.skills().xpLevel(skillId));
                        s4.setDouble("s_xp", player.skills().xp()[skillId]);
                        s4.execute();
                    }
                }

                //Skill 23 total lvl and exp
                try (NamedPreparedStatement s5 = prepareStatement(connection, "INSERT INTO player_skills (skill_id, skill_lvl, skill_xp, player_id) VALUES (:s_id, :s_lvl, :s_xp, :pid)")) {
                    s5.setInt("pid", pid);
                    s5.setInt("s_id", 23);
                    s5.setInt("s_lvl", player.skills().totalLevel());
                    s5.setDouble("s_xp", player.skills().totalXp());
                    s5.execute();
                }
            }
        }
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        logger.error("There was an updating skills user " + player.getUsername() + ": ");
        logger.catching(cause);
    }
}
