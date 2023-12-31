package com.valinor.game.content.items;

import com.valinor.GameServer;
import com.valinor.db.transactions.UpdateTotalPaymentAmountDatabaseTransaction;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.QuestTab;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.Color;

import java.util.Arrays;

import static com.valinor.util.CustomItemIdentifiers.*;

public class RedeemableBonds extends Interaction {

    private enum Bond {

        FIVE_DOLLAR(5.0, 500, FIVE_DOLLAR_BOND),
        TEN_DOLLAR(10.0, 1000, TEN_DOLLAR_BOND),
        TWENTY_DOLLAR(20.0, 2000, TWENTY_DOLLAR_BOND),
        THIRTY_DOLLAR(30.0, 3000, THIRTY_DOLLAR_BOND),
        FORTY_DOLLAR(40.0, 4000, FORTY_DOLLAR_BOND),
        FIFTY_DOLLAR(50.0, 5000, FIFTY_DOLLAR_BOND),
        SEVENTY_FIVE(75.0, 7500, SEVENTY_FIVE_DOLLAR_BOND, new Item(DONATOR_MYSTERY_BOX, 3)),
        ONE_HUNDRED_DOLLAR(100.0, 10000, ONE_HUNDRED_DOLLAR_BOND, new Item(DONATOR_MYSTERY_BOX, 5));

        private final double increasePaidAmountBy;
        private final int donatorTicketReward;
        private final int bondId;
        private final Item[] extra;

        Bond(double increasePaidAmountBy, int donatorTicketReward, int bondId, Item... extra) {
            this.increasePaidAmountBy = increasePaidAmountBy;
            this.donatorTicketReward = donatorTicketReward;
            this.bondId = bondId;
            this.extra = extra;
        }

        public static Bond getByBond(int id) {
            return (Arrays.stream(Bond.values()).filter(bond -> bond.bondId == id).findAny().orElse(null));
        }
    }

    private static final boolean BONDS_GIVE_REWARDS = true;

    private void claim(Player player, Bond bond) {
        //Get current amount paid
        double current = player.getAttribOr(AttributeKey.TOTAL_PAYMENT_AMOUNT, 0D);

        //Increase current amount paid
        player.putAttrib(AttributeKey.TOTAL_PAYMENT_AMOUNT, current + bond.increasePaidAmountBy);
        player.getPacketSender().sendString(QuestTab.InfoTab.TOTAL_DONATED.childId, QuestTab.InfoTab.INFO_TAB.get(QuestTab.InfoTab.TOTAL_DONATED.childId).fetchLineData(player));

        //Update database
        if(GameServer.properties().enableSql) {
            GameServer.getDatabaseService().submit(new UpdateTotalPaymentAmountDatabaseTransaction(player));
        }

        //Check if we can update the rank
        player.getMemberRights().update(player,false);

        if(BONDS_GIVE_REWARDS) {
            //Add the tickets
            player.inventory().addOrBank(new Item(DONATOR_TICKET, bond.donatorTicketReward));
            //player.inventory().addOrDrop(new Item(WINTER_TOKENS, bond.donatorTicketReward));

            if (bond.extra != null) {
                player.inventory().addOrBank(bond.extra.clone());
            }
        }
    }

    public void open(Player player, int id, boolean usedOnPlayer) {
        Bond bond = Bond.getByBond(id);

        if (bond == null) {
            return;
        }

        if(usedOnPlayer) {
            claim(player, bond);
            return;
        }

        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Would you like to open this bond?", "Yes.", "No.");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (option == 1) {
                    if(!player.inventory().contains(bond.bondId)) {
                        stop();
                        return;
                    }
                    //Get item slot and delete the bond
                    player.inventory().remove(new Item(bond.bondId), true);
                    claim(player, bond);
                }
                //Stop dialogue
                this.stop();
            }
        });
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        switch (item.getId()) {
            case FIVE_DOLLAR_BOND, TEN_DOLLAR_BOND, TWENTY_DOLLAR_BOND, THIRTY_DOLLAR_BOND, FORTY_DOLLAR_BOND, FIFTY_DOLLAR_BOND, SEVENTY_FIVE_DOLLAR_BOND, ONE_HUNDRED_DOLLAR_BOND -> {
                open(player, item.getId(),false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean handleItemOnPlayer(Player player, Item item, Player other) {
        switch (item.getId()) {
            case FIVE_DOLLAR_BOND, TEN_DOLLAR_BOND, TWENTY_DOLLAR_BOND, THIRTY_DOLLAR_BOND, FORTY_DOLLAR_BOND, FIFTY_DOLLAR_BOND, SEVENTY_FIVE_DOLLAR_BOND, ONE_HUNDRED_DOLLAR_BOND -> {
                if (other.getInterfaceManager().getMain() != -1) {
                    player.message("That player is busy.");
                    return true;
                }

                //Out of radius
                if (!other.tile().inSqRadius(player.tile(), 1)) {
                    return true;
                }

                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.STATEMENT, "<col=" + Color.RED.getColorValue() + ">Warning!</col> Using this bond on " + other.getUsername() + " will destroy the bond.", "The player the bond is used upon will get the reward.");
                        setPhase(0);
                    }

                    @Override
                    protected void next() {
                        if (getPhase() == 0) {
                            send(DialogueType.OPTION, "Use the bond on " + other.getUsername(), "Yes, I'd like to use the bond on " + other.getUsername(), "No.");
                            setPhase(1);
                        }
                    }

                    @Override
                    protected void select(int option) {
                        if (getPhase() == 1) {
                            if (option == 1) {
                                stop();
                                //Only give items when other player is online
                                if(other.isRegistered()) {
                                    //Delete the bond
                                    player.inventory().remove(item, true);

                                    //Open the bond on the other account
                                    open(other, item.getId(), true);
                                } else {
                                    player.message(other.getUsername()+" Is no longer online, the action has been canceled.");
                                }
                            } else if (option == 2) {
                                stop();
                            }
                        }
                    }
                });
                return true;
            }
        }
        return false;
    }
}
