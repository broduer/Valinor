package com.valinor.db.transactions;

import com.valinor.db.VoidDatabaseTransaction;
import com.valinor.db.statement.NamedPreparedStatement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Patrick van Elderen | July, 17, 2021, 16:18
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class UpdatePlayerInfoDatabaseTransaction extends VoidDatabaseTransaction {

    private static final Logger logger = LogManager.getLogger(UpdatePlayerInfoDatabaseTransaction.class);
    int id;
    String ip;
    String mac;
    int playtime;
    String expMode;
    String ironMode;

    public UpdatePlayerInfoDatabaseTransaction(int id, String ip, String mac, int playtime, String expMode, String ironMode) {
        this.id = id;
        this.ip = ip;
        this.mac = mac;
        this.playtime = playtime;
        this.ironMode = ironMode;
    }

    @Override
    public void executeVoid(Connection connection) throws SQLException {
        try (NamedPreparedStatement statement = prepareStatement(connection,"UPDATE users SET last_login_ip=:ip, last_login_mac=:mac, playtime=:playtime, exp_mode=:exp_mode, iron_mode=:iron_mode WHERE id=:id")) {
            statement.setInt("id", id);
            statement.setString("ip", ip);
            statement.setString("mac", mac);
            statement.setInt("playtime", playtime);
            statement.setString("exp_mode", expMode);
            statement.setString("iron_mode", ironMode);
            //logger.info("Executing query: " + statement.toString());
            statement.executeUpdate();
        }
    }

    @Override
    public void exceptionCaught(Throwable cause) {
        logger.error("There was an error updating the info column for Player with id: " + id + ": ");
        logger.catching(cause);
    }
}
