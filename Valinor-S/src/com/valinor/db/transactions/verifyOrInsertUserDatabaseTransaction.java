package com.valinor.db.transactions;

import com.valinor.db.VoidDatabaseTransaction;
import com.valinor.db.statement.NamedPreparedStatement;
import com.valinor.game.world.entity.mob.player.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import static com.valinor.game.world.entity.AttributeKey.GAME_TIME;
import static com.valinor.game.world.entity.AttributeKey.MAC_ADDRESS;

public final class verifyOrInsertUserDatabaseTransaction extends VoidDatabaseTransaction {
    private static final Logger logger = LogManager.getLogger(verifyOrInsertUserDatabaseTransaction.class);

    private final Player player;

    public verifyOrInsertUserDatabaseTransaction(Player player) {
        this.player = player;
    }

    @Override
    public void executeVoid(Connection connection) throws SQLException {
        int count = -1;
        try (NamedPreparedStatement statement = prepareStatement(connection,"SELECT COUNT(*) FROM users WHERE lower(username) = :user")) {
            statement.setString("user", player.getUsername().toLowerCase());
            statement.executeQuery();
            if (statement.getResultSet().next()) {
                count = statement.getResultSet().getInt(1);
            }
        }
        if (count == 0) {
            try (NamedPreparedStatement statement = prepareStatement(connection, "INSERT INTO users (username, password, last_login_ip, creation_ip, first_login_date, email, created_at, updated_at, last_login_mac, exp_mode, iron_mode, playtime) VALUES (:username, :password, :last_login_ip, :creation_ip, :first_login_date, :email, :created_at, :updated_at, :mac, :exp_mode, :iron_mode, :playtime)")) {
                statement.setString("username", player.getUsername());
                statement.setString("last_login_ip", player.getHostAddress());
                statement.setString("creation_ip", player.getCreationIp());
                statement.setTimestamp("first_login_date", player.getCreationDate());
                statement.setString("password", player.getPassword());
                statement.setString("email", player.getUsername()); //We'll treat the player's username as their email so we don't get a unique constraint error. email_verified_at will be null by default until they verify their email so it is fine to store their username as their email.
                statement.setTimestamp("created_at", new Timestamp(new Date().getTime()));
                statement.setTimestamp("updated_at", new Timestamp(new Date().getTime()));
                statement.setString("mac", player.getAttribOr(MAC_ADDRESS, "invalid"));
                statement.setString("exp_mode", player.expmode().toName());
                statement.setString("iron_mode", player.gameMode().name);
                statement.setInt("playtime", player.getAttribOr(GAME_TIME, 0));
                //logger.info("Executing query: " + statement.toString());
                statement.executeUpdate();
            }
        }
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        logger.error("There was an error inserting the user " + player.getUsername() + ": ");
        logger.catching(cause);
    }
}
