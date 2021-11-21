package com.valinor.db.transactions;

import com.valinor.db.VoidDatabaseTransaction;
import com.valinor.db.statement.NamedPreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since November 21, 2021
 */
public class UpdateKillsDatabaseTransaction extends VoidDatabaseTransaction {
    private static final Logger logger = LogManager.getLogger(UpdateKillsDatabaseTransaction.class);
    int kills;
    String username;

    public UpdateKillsDatabaseTransaction(int kills, String username) {
        this.kills = kills;
        this.username = username;
    }

    @Override
    public void executeVoid(Connection connection) throws SQLException {
        try (NamedPreparedStatement statement = prepareStatement(connection,"UPDATE users SET kills = :kills WHERE username = :username")) {
            statement.setInt("kills", kills);
            statement.setString("username", username);
            //logger.info("Executing query: " + statement.toString());
            statement.executeUpdate();
        }
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        logger.error("There was an error updating the kills column for Player " + username + ": ");
        logger.catching(cause);
    }
}
