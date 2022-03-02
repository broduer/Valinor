package com.valinor.db.transactions

import com.valinor.GameServer
import com.valinor.db.makeQuery
import com.valinor.db.onDatabase
import com.valinor.db.query
import com.valinor.game.world.entity.AttributeKey
import com.valinor.game.world.entity.dialogue.DialogueManager
import com.valinor.game.world.entity.dialogue.Expression
import com.valinor.game.world.entity.mob.player.Player
import com.valinor.game.world.entity.mob.player.QuestTab
import com.valinor.game.world.items.Item
import com.valinor.util.CustomItemIdentifiers
import com.valinor.util.NpcIdentifiers
import com.valinor.util.Utils
import java.time.LocalDateTime

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 01, 2022
 */
object CollectOldPayments {

    private fun purchaseItem(itemNum: Int): Item {
        when (itemNum) {
            13190 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 125)
            }

            16278 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 250)
            }

            16263 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 500)
            }

            16264 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 1000)
            }

            16265 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 1250)
            }

            16266 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 2500)
            }

            6199 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 100)
            }

            16453 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 250)
            }

            16454 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 500)
            }

            16456 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 650)
            }

            16458 -> {
                return Item(CustomItemIdentifiers.DONATOR_TICKET, 1000)
            }
        }

        //Invalid item
        return Item(CustomItemIdentifiers.DONATOR_TICKET, 250)
    }

    fun Player.collectPayments() {
        // get a list of itemNumer, amt, paidAmt
        query<List<CollectPayments.DonationRow>> {
            val list = mutableListOf<CollectPayments.DonationRow>()
            prepareStatement(
                connection,
                "SELECT * FROM DoAIIDlB_legacy_rs_orders WHERE lower(username)=:user AND lower(claimed)='unclaimed'"
            ).apply {
                setString("user", username)
                execute()
                while (resultSet.next()) {
                    list.add(
                        CollectPayments.DonationRow(
                            resultSet.getInt("item_id"),
                            resultSet.getInt("item_amount"),
                            resultSet.getInt("id")
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
                    NpcIdentifiers.WISE_OLD_MAN,
                    "There are no pending purchases to claim.",
                    "Wait a minute and try again, or contact an Administrator."
                )
            }
            list.forEach { row ->
                val item = purchaseItem(row.itemId)

                // each row is a reward in the table. do checks that we can fit it inside the bank before actually giving.
                var spaceFor = (!bank.contains(item) || item?.let { bank.count(it.id) }!! < Int.MAX_VALUE - row.itemAmt)

                // if a purchase is like 2 items in one you'll need a custom check here:
                if (row.itemId == 100_000) { // example purchase product_number
                    // in this example, purchase 100k is actually 2 items, item 1 x5 and item2 x10. here we check bank space.
                    spaceFor = (!bank.contains(100_001) || bank.count(100_001) < Int.MAX_VALUE - 5)
                            && (!bank.contains(100_002) || bank.count(100_002) < Int.MAX_VALUE - 10)
                }

                if (!spaceFor) {
                    // no space. inform user, purchase is NOT set as claimed.
                    message("Your bank was too full. Make some space and reclaim.")
                    return@forEach // continue to next
                }

                // now query again, setting claimed after we've confirmed they have space
                val currentDateTime = LocalDateTime.now()
                // because it gives items here, the code below is for after stuff is claimed not before
                makeQuery {
                    prepareStatement(
                        connection,
                        "UPDATE DoAIIDlB_legacy_rs_orders SET claimed='Claimed', claim_date=:date, claim_ip=:ip WHERE id=:id"
                    ).apply {
                        setInt("id", row.rowId)
                        setString("date", currentDateTime.toString())
                        setString("ip", hostAddress ?: "unknown")
                        execute()
                    }
                }.onDatabase(GameServer.getDatabaseService()) {
                    var paymentAmount = 0.0
                    when (row.itemId) {
                        13190 -> {
                            paymentAmount = 5.0
                        }

                        16278 -> {
                            paymentAmount = 10.0
                        }

                        16263 -> {
                            paymentAmount = 20.0
                        }

                        16264 -> {
                            paymentAmount = 40.0
                        }

                        16265 -> {
                            paymentAmount = 50.0
                        }

                        16266 -> {
                            paymentAmount = 100.0
                        }

                        6199 -> {
                            paymentAmount = 2.50
                        }

                        16453 -> {
                            paymentAmount = 10.0
                        }

                        16454 -> {
                            paymentAmount = 30.0
                        }

                        16456 -> {
                            paymentAmount = 15.0
                        }
                        16458 -> {
                            paymentAmount = 50.0
                        }
                    }

                    val increaseTotalBy = getAttribOr<Int>(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0.0) + paymentAmount * row.itemAmt
                    putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, increaseTotalBy)
                    packetSender.sendString(QuestTab.InfoTab.TOTAL_DONATED.childId, QuestTab.InfoTab.INFO_TAB[QuestTab.InfoTab.TOTAL_DONATED.childId]!!.fetchLineData(this))

                    //Check if we can update the rank
                    memberRights.update(this, false)

                    val bonus = row.itemAmt / 2
                    val r = purchaseItem(row.itemId)
                    inventory.addOrBank(Item(r.id, (r.amount * row.itemAmt) + bonus))

                    Utils.sendDiscordInfoLog("$username used command: ::redeemold and claimed their payment of X${row.itemAmt} bonus amt + $bonus ${Item(row.itemId).name()}.", "donations_claimed")
                }
            }
        }
    }
}
