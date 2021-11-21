package com.valinor.db.transactions;

import com.valinor.db.DatabaseTransaction;
import com.valinor.db.statement.NamedPreparedStatement;
import com.valinor.game.world.entity.mob.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 21, 2021
 */
public class FindMissingUsersDatabaseTransaction extends DatabaseTransaction<List<String>> {

    private static final Logger logger = LogManager.getLogger(FindMissingUsersDatabaseTransaction.class);

    private final List<Player> players;

    public FindMissingUsersDatabaseTransaction(List<Player> players) {
        this.players = players;
    }

    @Override
    public List<String> execute(Connection connection) throws SQLException {
        List<String> missingPlayers = new ArrayList<String>();
        int id = 0;
        for (Player player : players) {
            try (NamedPreparedStatement statement = prepareStatement(connection, "SELECT id from users WHERE username = :username")) {
                statement.setString("username", player.getUsername());
                //logger.info("Executing query: " + statement.toString());
                statement.execute();
                if (!statement.getResultSet().next()) {
                    missingPlayers.add(player.getUsername());
                }
            }
        }
        return missingPlayers;
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        logger.error("There was an error with the find missing users query: ");
        logger.catching(cause);
    }
}
