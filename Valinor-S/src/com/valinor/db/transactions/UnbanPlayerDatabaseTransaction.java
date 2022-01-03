package com.valinor.db.transactions;

import com.valinor.db.VoidDatabaseTransaction;
import com.valinor.db.statement.NamedPreparedStatement;
import com.valinor.game.world.entity.mob.player.commands.impl.kotlin.MiscKotlin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Patrick van Elderen | November, 12, 2020, 18:13
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class UnbanPlayerDatabaseTransaction extends VoidDatabaseTransaction {
    private static final Logger logger = LogManager.getLogger(UnbanPlayerDatabaseTransaction.class);
    String username;

    public UnbanPlayerDatabaseTransaction(String username) {
        this.username = username;
    }

    @Override
    public void executeVoid(Connection connection) throws SQLException {
        try (NamedPreparedStatement statement = prepareStatement(connection,"UPDATE users SET is_banned = 0 WHERE lower(username) = :username")) {
            statement.setString("username", username.toLowerCase());
            //logger.info("Executing query: " + statement.toString());
            statement.executeUpdate();
        }
        MiscKotlin.INSTANCE.ipUnban(null, username);
        MiscKotlin.INSTANCE.unbanMac(null, username);
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        logger.error("There was an error updating the server status.");
        logger.catching(cause);
    }
}
