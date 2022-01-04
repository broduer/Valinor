package com.valinor.db.transactions

import com.valinor.GameServer
import com.valinor.db.makeQuery
import com.valinor.db.onDatabase
import com.valinor.db.query
import com.valinor.game.world.entity.dialogue.DialogueManager
import com.valinor.game.world.entity.dialogue.Expression
import com.valinor.game.world.entity.mob.player.Player
import com.valinor.game.world.items.Item
import com.valinor.util.NpcIdentifiers.FANCY_DAN
import com.valinor.util.Utils

object CollectTopVoterRewards {

    class TopVotersRow(val id: Int, val username: String, val reward: Int)

    fun Player.collectTopVoterReward() {
        // get a list of reward, name, row
        query<List<TopVotersRow>> {
            val list = mutableListOf<TopVotersRow>()
            prepareStatement(
                connection,
                "SELECT * FROM DoAIIDlB_rs_top_voters WHERE lower(username)=:user"
            ).apply {
                setString("user", username)
                execute()
                while (resultSet.next()) {
                    list.add(
                        TopVotersRow(
                            resultSet.getInt("id"),
                            resultSet.getString("username"),
                            resultSet.getInt("reward")
                        )
                    )
                }
            }
            list
        }.onDatabase(GameServer.getDatabaseService()) { list ->
            if (list.isEmpty()) {
                DialogueManager.npcChat(
                    this,
                    Expression.DEFAULT,
                    FANCY_DAN,
                    "There are no pending rewards to claim.",
                    "Wait a minute and try again, or contact an Administrator."
                )
            }
            list.forEach { row ->

                val item = row.reward
                val amount = 1

                // each row is a reward in the table. do check that we can fit it inside the bank before actually giving.
                val spaceFor = (!bank.contains(item) || bank.count(item) < Int.MAX_VALUE - amount)

                if (!spaceFor) {
                    // no space. inform user, reward is NOT set as claimed.
                    message("Your bank was too full. Make some space and reclaim.")
                } else {
                    // now query again, setting claimed after we've confirmed they have space
                    makeQuery {
                        prepareStatement(connection, "DELETE FROM DoAIIDlB_rs_top_voters WHERE lower(username) = :username").apply {
                            setString("username", username)
                            execute()
                        }
                    }.onDatabase(GameServer.getDatabaseService()) {
                        inventory.addOrBank(Item(row.reward))
                        message("You have received a ${Item(row.reward).name()} for being one of the top voters.")
                        Utils.sendDiscordInfoLog("$username used command: ::claimtopvoter and claimed their reward of ${Item(row.reward).name()}.", "votes_claimed")
                    }
                }
            }
        }
    }
}
