package com.valinor.game.content.packet_actions.interactions.container;

import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.gambling.GamblingSession;
import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.syntax.impl.*;
import com.valinor.game.content.trade.Trading;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.PlayerStatus;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.shop.Shop;
import com.valinor.game.world.items.container.shop.ShopUtility;
import com.valinor.net.packet.interaction.PacketInteractionManager;

import java.util.Optional;

import static com.valinor.game.world.InterfaceConstants.*;
import static com.valinor.game.world.entity.AttributeKey.USING_TRADING_POST;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class FifthContainerAction {

    public static void fifthAction(Player player, int interfaceId, int slot, int id) {
        boolean banking = player.getAttribOr(AttributeKey.BANKING, false);
        boolean priceChecking = player.getAttribOr(AttributeKey.PRICE_CHECKING, false);
        if(PacketInteractionManager.checkItemContainerActionInteraction(player, new Item(id), slot, interfaceId, 5)) {
            return;
        }

        if(player.getRunePouch().removeFromPouch(interfaceId, id, slot,5)) {
            return;
        }

        if(player.getRunePouch().moveToRunePouch(interfaceId, id, slot,5)) {
            return;
        }

        /* Bank x */
        if (interfaceId == WITHDRAW_BANK) {
            if (banking) {
                player.setEnterSyntax(new BankX(id, slot, false));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?");
            }
        }

        if(interfaceId == GROUP_STORAGE_CONTAINER) {
            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if (group.isPresent() && group.get().storageInUse()) {
                player.setEnterSyntax(new GroupStorageX(id, slot, false));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?");
                return;
            }
        }

        if (interfaceId == DEPOSIT_BOX_CONTAINER_ID) {
            player.setEnterSyntax(new DepositBoxX(id, slot));
            player.getPacketSender().sendEnterAmountPrompt("How many would you like to deposit?");
        }

        if (interfaceId == INVENTORY_STORE) {
            if (priceChecking) {
                player.setEnterSyntax(new PriceCheckX(id, slot, true));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to deposit?");
                return;
            }

            if (banking) {
                player.setEnterSyntax(new BankX(id, slot, true));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to deposit?");
                return;
            }

            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if (group.isPresent() && group.get().storageInUse()) {
                player.setEnterSyntax(new GroupStorageX(id, slot, true));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to deposit?");
                return;
            }
        }

        if (interfaceId == PRICE_CHECKER_DISPLAY_ID) {
            player.setEnterSyntax(new PriceCheckX(id, slot, false));
            player.getPacketSender().sendEnterAmountPrompt("How many would you like to withdraw?");
        }

        if (interfaceId == ShopUtility.ITEM_CHILD_ID) {
            Shop.exchange(player, id, slot, 5, true);
        }

        if (interfaceId == SHOP_INVENTORY) {
            Shop.exchange(player, id, slot, 5, false);
        }

        if (interfaceId == REMOVE_INVENTORY_ITEM) { // Duel/Trade inventory

            if (player.<Boolean>getAttribOr(USING_TRADING_POST,false)) {
                TradingPost.handleXOptionInput(player, id, slot);
            } else if (player.getStatus() == PlayerStatus.TRADING) {
                player.setEnterSyntax(new TradeX(id, slot, true));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to offer?");
            } else if (player.getStatus() == PlayerStatus.DUELING) {
                player.setEnterSyntax(new StakeX(id, slot, true));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to offer?");
            } else if (player.getStatus() == PlayerStatus.GAMBLING) {
                player.setEnterSyntax(new GambleX(id, slot, true));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to offer?");
            }
        }

        if (interfaceId == Trading.CONTAINER_INTERFACE_ID) {
            if (player.getStatus() == PlayerStatus.TRADING) {
                player.setEnterSyntax(new TradeX(id, slot, false));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
            }
        }

        if (interfaceId == Dueling.MAIN_INTERFACE_CONTAINER) {
            if (player.getStatus() == PlayerStatus.DUELING) {
                player.setEnterSyntax(new StakeX(id, slot, false));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
            }
        }

        if (interfaceId == GamblingSession.MY_ITEMS_OFFERED || interfaceId == GamblingSession.OPPONENT_ITEMS_OFFERED) {
            if (player.getStatus() == PlayerStatus.GAMBLING) {
                player.setEnterSyntax(new GambleX(id, slot, false));
                player.getPacketSender().sendEnterAmountPrompt("How many would you like to remove?");
            }
        }
    }
}
