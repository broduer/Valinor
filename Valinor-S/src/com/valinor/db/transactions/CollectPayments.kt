package com.valinor.db.transactions

import com.valinor.GameServer
import com.valinor.db.makeQuery
import com.valinor.db.onDatabase
import com.valinor.db.query
import com.valinor.game.world.entity.AttributeKey
import com.valinor.game.world.entity.dialogue.DialogueManager
import com.valinor.game.world.entity.dialogue.Expression
import com.valinor.game.world.entity.mob.player.Player
import com.valinor.game.world.entity.mob.player.QuestTab.InfoTab
import com.valinor.game.world.items.Item
import com.valinor.util.Color
import com.valinor.util.CustomItemIdentifiers.*
import com.valinor.util.ItemIdentifiers.*
import com.valinor.util.NpcIdentifiers.WISE_OLD_MAN
import com.valinor.util.Utils
import java.time.LocalDateTime

object CollectPayments {

    class DonationRow(val itemId: Int, val itemAmt: Int, val rowId: Int)

    fun Player.collectPayments() {
        // get a list of itemNumer, amt, paidAmt
        query<List<DonationRow>> {
            val list = mutableListOf<DonationRow>()
            prepareStatement(
                    connection,
                    "SELECT * FROM DoAIIDlB_rs_orders WHERE lower(username)=:user AND lower(claimed)='unclaimed'"
            ).apply {
                setString("user", username)
                execute()
                while (resultSet.next()) {
                    list.add(
                            DonationRow(
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
                        WISE_OLD_MAN,
                        "There are no pending purchases to claim.",
                        "Wait a minute and try again, or contact an Administrator."
                )
            }
            var sendNonIronmanMsg = false
            list.forEach { row ->

                val item = row.itemId

                // each row is a reward in the table. do checks that we can fit it inside the bank before actually giving.
                var spaceFor = (!bank.contains(item) || bank.count(item) < Int.MAX_VALUE - row.itemAmt)

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
                var isIronManItem = false
                when (row.itemId) {
                    DONATOR_MYSTERY_BOX -> {
                        isIronManItem = false
                    }
                    SUPER_MYSTERY_BOX -> {
                        isIronManItem = false
                    }
                    PETS_MYSTERY_BOX -> {
                        isIronManItem = false
                    }
                    RAIDS_MYSTERY_BOX -> {
                        isIronManItem = false
                    }
                    MYSTERY_TICKET -> {
                        isIronManItem = false
                    }
                    MYSTERY_CHEST -> {
                        isIronManItem = false
                    }
                }

                val playerIsIron = gameMode().isIronman || gameMode().isHardcoreIronman || gameMode().isUltimateIronman
                val canClaimOnIron = playerIsIron && !isIronManItem

                if(!canClaimOnIron) {
                    sendNonIronmanMsg = true
                    return@forEach
                }

                    // now query again, setting claimed after we've confirmed they have space
                val currentDateTime = LocalDateTime.now()
                // because it gives items here, the code below is for after stuff is claimed not before
                makeQuery {
                    prepareStatement(
                            connection,
                            "UPDATE DoAIIDlB_rs_orders SET claimed='Claimed', claim_date=:date, claim_ip=:ip WHERE id=:id"
                    ).apply {
                        setInt("id", row.rowId)
                        setString("date", currentDateTime.toString())
                        setString("ip", hostAddress ?: "unknown")
                        execute()
                    }
                }.onDatabase(GameServer.getDatabaseService()) {
                    var paymentAmount = 0.0
                    when (row.itemId) {
                        DONATOR_MYSTERY_BOX -> {
                            paymentAmount = 6.0
                        }
                        SUPER_MYSTERY_BOX -> {
                            paymentAmount = 15.0
                        }
                        PETS_MYSTERY_BOX -> {
                            paymentAmount = 40.0
                        }
                        RAIDS_MYSTERY_BOX -> {
                            paymentAmount = 55.0
                        }
                        MYSTERY_TICKET -> {
                            paymentAmount = 23.0
                        }
                        MYSTERY_CHEST -> {
                            paymentAmount = 250.0
                        }
                        RUNE_POUCH -> {
                            paymentAmount = 3.0
                        }
                        IMBUED_HEART -> {
                            paymentAmount = 5.0
                        }
                        VOID_SET -> {
                            paymentAmount = 15.0
                        }
                        EXTRA_SUPPLY_CRATE -> {
                            paymentAmount = 3.0
                        }
                        DWARF_CANNON_SET -> {
                            paymentAmount = 10.0
                        }
                        COLLECTION_KEY -> {
                            paymentAmount = 15.0
                        }
                    }

                    val increaseTotalBy = getAttribOr<Int>(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0.0) + paymentAmount * row.itemAmt
                    putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, increaseTotalBy)
                    packetSender.sendString(InfoTab.TOTAL_DONATED.childId, InfoTab.INFO_TAB[InfoTab.TOTAL_DONATED.childId]!!.fetchLineData(this))

                    //Check if we can update the rank
                    memberRights.update(this, false)

                    //Buy two get one free promo
                    if (GameServer.properties().buyTwoGetOneFree) {
                        // Award a 'buy-two-get-one free' special if acceptable.
                        val bonus = row.itemAmt / 2
                        inventory.addOrBank(Item(row.itemId, row.itemAmt + bonus))
                        Utils.sendDiscordInfoLog("$username used command: ::redeem and claimed their payment of X${row.itemAmt} bonus amt + $bonus ${Item(row.itemId).name()}.", "donations_claimed")
                        if (bonus > 0)
                            message("${Color.RED.tag()}You have been rewarded extra items because of our active payment deal.")
                    } else {
                        //No promo active, give the purchase items without bonus
                        inventory.addOrBank(Item(row.itemId, row.itemAmt))
                        Utils.sendDiscordInfoLog("$username used command: ::redeem and claimed their payment of X${row.itemAmt} ${Item(row.itemId).name()}.", "donations_claimed")
                    }
                }

            }
            if (sendNonIronmanMsg) { // there
                message("You are unable to claim your order as a ironman. Contact a staff member to sort this")
                message("out.")
            }
        }
    }
}
