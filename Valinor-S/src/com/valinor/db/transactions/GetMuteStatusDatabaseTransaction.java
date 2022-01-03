package com.valinor.db.transactions;

import com.valinor.db.DatabaseTransaction;
import com.valinor.db.statement.NamedPreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Patrick van Elderen | November, 12, 2020, 18:09
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public final class GetMuteStatusDatabaseTransaction extends DatabaseTransaction<Boolean> {
    private static final Logger logger = LogManager.getLogger(GetMuteStatusDatabaseTransaction.class);

    private final String username;

    public GetMuteStatusDatabaseTransaction(String username) {
        this.username = username;
    }

    @Override
    public Boolean execute(Connection connection) throws SQLException {
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp expiryDate = null;
        String ip = "";
        try (NamedPreparedStatement statement = prepareStatement(connection, "SELECT mute_expires FROM users WHERE lower(username) = :username AND mute_expires IS NOT NULL")) {
            statement.setString("username", username.toLowerCase());
            statement.execute();
            if (statement.getResultSet().next()) {
                expiryDate = statement.getResultSet().getTimestamp("mute_expires");
            }
        }
        if (expiryDate != null && currentDateTime.isAfter(expiryDate.toLocalDateTime())) {
            try (NamedPreparedStatement statement = prepareStatement(connection, "UPDATE users SET is_muted = 0 WHERE lower(username) = :username")) {
                statement.setString("username", username.toLowerCase());
                statement.execute();
            }
        }
        try (NamedPreparedStatement statement = prepareStatement(connection, "SELECT is_muted from users WHERE lower(username) = :username")) {
            statement.setString("username", username.toLowerCase());
            //logger.info("Executing query: " + statement.toString());
            statement.execute();
            if (statement.getResultSet().next()) {
                if (statement.getResultSet().getInt("is_muted") == 1)
                    return true; // normal mute active
            }
        }
        if (ip != null && ip.length() > 0) {
            try (NamedPreparedStatement statement = prepareStatement(connection, "SELECT * from ip_mutes WHERE ip = :ip")) {
                statement.setString("ip", ip);
                statement.execute();
                while (statement.getResultSet().next()) {
                    Timestamp cidExpiryDate = statement.getResultSet().getTimestamp("unmute_at");
                    if (cidExpiryDate != null && !currentDateTime.isAfter(cidExpiryDate.toLocalDateTime())) {
                        return true; // ip active
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        logger.error("There was an error with the find missing users query: ");
        logger.catching(cause);
    }
}
