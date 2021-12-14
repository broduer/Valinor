package com.valinor.game.content.packet_actions.interactions.container;

import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.gambling.GamblingSession;
import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.packet_actions.interactions.equipment.EquipmentActions;
import com.valinor.game.content.skill.impl.crafting.impl.Jewellery;
import com.valinor.game.content.skill.impl.smithing.EquipmentMaking;
import com.valinor.game.content.trade.Trading;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.PlayerStatus;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.shop.Shop;
import com.valinor.game.world.items.container.shop.ShopUtility;
import com.valinor.net.packet.interaction.InteractionManager;

import java.util.Optional;

import static com.valinor.game.content.skill.impl.smithing.EquipmentMaking.*;
import static com.valinor.game.world.InterfaceConstants.*;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class SecondContainerAction {

    public static void secondAction(Player player, int interfaceId, int slot, int id) {
        if (InteractionManager.checkItemContainerActionInteraction(player, new Item(id), slot, interfaceId, 2)) {
            return;
        }

        if (player.getRunePouch().removeFromPouch(interfaceId, id, slot, 2)) {
            return;
        }

        if (TradingPost.handleSellingItem(player, interfaceId, id, 5))
            return;

        if (player.getRunePouch().moveToRunePouch(interfaceId, id, slot, 2)) {
            return;
        }

        if (interfaceId == EQUIPMENT_CREATION_COLUMN_1 || interfaceId == EQUIPMENT_CREATION_COLUMN_2 || interfaceId == EQUIPMENT_CREATION_COLUMN_3 || interfaceId == EQUIPMENT_CREATION_COLUMN_4 || interfaceId == EQUIPMENT_CREATION_COLUMN_5) {
            if (player.getInterfaceManager().isInterfaceOpen(EquipmentMaking.EQUIPMENT_CREATION_INTERFACE_ID)) {
                EquipmentMaking.initialize(player, id, interfaceId, slot, 5);
            }
        }

        if (interfaceId == DEPOSIT_BOX_CONTAINER_ID) {
            if (player.getDepositBox().quantityOne) {
                player.getDepositBox().deposit(id, 5);
            } else {
                player.getDepositBox().deposit(id, 1);
            }
        }

        if (interfaceId == EQUIPMENT_DISPLAY_ID) {
            final Item item = player.getEquipment().get(slot);

            if (item == null || item.getId() != id) {
                return;
            }
            if (EquipmentActions.operate(player, slot, item)) {
                return;
            }
        }

        /* Jewellery */
        if (interfaceId == JEWELLERY_INTERFACE_CONTAINER_ONE || interfaceId == JEWELLERY_INTERFACE_CONTAINER_TWO || interfaceId == JEWELLERY_INTERFACE_CONTAINER_THREE) {
            Jewellery.click(player, id, 5);
        }

        /* Looting bag */
        if (interfaceId == LOOTING_BAG_BANK_CONTAINER_ID) {
            Item item = player.getLootingBag().get(slot);
            if (item == null) {
                return;
            }
            boolean banking = player.getAttribOr(AttributeKey.BANKING, false);

            if (banking) {
                player.getLootingBag().withdrawBank(item.createWithAmount(5), slot);
                return;
            }
        }

        if (interfaceId == LOOTING_BAG_DEPOSIT_CONTAINER_ID) {
            Item item = player.inventory().get(slot);
            if (item == null) {
                return;
            }
            player.getLootingBag().deposit(item, 5, null);
        }

        if (interfaceId == WITHDRAW_BANK) {
            player.getBank().withdraw(id, slot, 5);
        }

        if (interfaceId == GROUP_STORAGE_CONTAINER) {
            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if (group.isPresent() && group.get().storageInUse()) {
                group.get().getGroupStorage(player).withdraw(id, slot, 5);
                return;
            }
        }

        if (interfaceId == INVENTORY_STORE) {
            boolean priceChecking = player.getAttribOr(AttributeKey.PRICE_CHECKING, false);
            if (priceChecking) {
                player.getPriceChecker().deposit(slot, 5);
                return;
            }

            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if (group.isPresent() && group.get().storageInUse()) {
                group.get().getGroupStorage(player).deposit(slot, 5);
                return;
            }

            boolean banking = player.getAttribOr(AttributeKey.BANKING, false);
            if (banking) {
                player.getBank().deposit(slot, 5);
                return;
            }
        }

        if (interfaceId == PRICE_CHECKER_DISPLAY_ID) {
            boolean priceChecking = player.getAttribOr(AttributeKey.PRICE_CHECKING, false);
            if (priceChecking) {
                player.getPriceChecker().withdraw(id, 5);
                return;
            }
        }

        if (interfaceId == ShopUtility.ITEM_CHILD_ID || interfaceId == ShopUtility.SLAYER_BUY_ITEM_CHILD_ID) {
            Shop.exchange(player, id, slot, 2, true);
        }

        if (interfaceId == SHOP_INVENTORY) {
            Shop.exchange(player, id, slot, 2, false);
        }

        if (interfaceId == Dueling.MAIN_INTERFACE_CONTAINER) {
            if (player.getStatus() == PlayerStatus.DUELING) {
                player.getDueling().handleItem(id, 5, slot, player.getDueling().getContainer(), player.inventory());
            }
        }

        // Withdrawing items from gamble
        if (interfaceId == GamblingSession.MY_ITEMS_OFFERED || interfaceId == GamblingSession.OPPONENT_ITEMS_OFFERED) {
            if (player.getStatus() == PlayerStatus.GAMBLING) {
                player.getGamblingSession().handleItem(id, 5, slot, player.getGamblingSession().getContainer(), player.inventory());
            }
        }

        if (interfaceId == REMOVE_INVENTORY_ITEM) { // Duel/Trade inventory
            if (player.getStatus() == PlayerStatus.TRADING) {
                player.getTrading().handleItem(id, 5, slot, player.inventory(), player.getTrading().getContainer());
            } else if (player.getStatus() == PlayerStatus.DUELING) {
                player.getDueling().handleItem(id, 5, slot, player.inventory(), player.getDueling().getContainer());
            } else if (player.getStatus() == PlayerStatus.GAMBLING) {
                player.getGamblingSession().handleItem(id, 5, slot, player.inventory(), player.getGamblingSession().getContainer());
            }
        }

        if (interfaceId == Trading.CONTAINER_INTERFACE_ID) {
            if (player.getStatus() == PlayerStatus.TRADING) {
                player.getTrading().handleItem(id, 5, slot, player.getTrading().getContainer(), player.inventory());
            }
        }

        if (interfaceId == PRICE_CHECKER_CONTAINER) {
            player.getPriceChecker().withdraw(id, 5);
        }
    }
}
