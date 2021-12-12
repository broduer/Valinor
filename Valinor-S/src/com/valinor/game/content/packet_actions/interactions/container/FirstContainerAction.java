package com.valinor.game.content.packet_actions.interactions.container;

import com.valinor.game.content.duel.Dueling;
import com.valinor.game.content.gambling.GamblingSession;
import com.valinor.game.content.group_ironman.IronmanGroup;
import com.valinor.game.content.group_ironman.IronmanGroupHandler;
import com.valinor.game.content.interfaces.BonusesInterface;
import com.valinor.game.content.skill.impl.crafting.impl.Jewellery;
import com.valinor.game.content.skill.impl.smithing.EquipmentMaking;
import com.valinor.game.content.syntax.impl.GroupStorageX;
import com.valinor.game.content.trade.Trading;
import com.valinor.game.content.tradingpost.TradingPost;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.pets.insurance.PetInsurance;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.PlayerStatus;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.container.shop.Shop;
import com.valinor.game.world.items.container.shop.ShopUtility;
import com.valinor.net.packet.interaction.PacketInteractionManager;

import java.util.Optional;

import static com.valinor.game.content.skill.impl.smithing.EquipmentMaking.*;
import static com.valinor.game.world.InterfaceConstants.*;

/**
 * @author PVE
 * @Since augustus 26, 2020
 */
public class FirstContainerAction {

    public static void firstAction(Player player, int interfaceId, int slot, int id) {
        if (PacketInteractionManager.checkItemContainerActionInteraction(player, new Item(id), slot, interfaceId, 1)) {
            return;
        }

        if (TradingPost.handleSellingItem(player, interfaceId, id, 1))
            return;

        if (PetInsurance.onContainerAction(player, new Item(id), interfaceId, 1)) {
            return;
        }

        if (BonusesInterface.onContainerAction(player, interfaceId, slot)) {
            return;
        }

        if (player.getRunePouch().removeFromPouch(interfaceId, id, slot, 1)) {
            return;
        }

        if (player.getRunePouch().moveToRunePouch(interfaceId, id, slot, 1)) {
            return;
        }

        if (interfaceId == EQUIPMENT_CREATION_COLUMN_1 || interfaceId == EQUIPMENT_CREATION_COLUMN_2 || interfaceId == EQUIPMENT_CREATION_COLUMN_3 || interfaceId == EQUIPMENT_CREATION_COLUMN_4 || interfaceId == EQUIPMENT_CREATION_COLUMN_5) {
            if (player.getInterfaceManager().isInterfaceOpen(EquipmentMaking.EQUIPMENT_CREATION_INTERFACE_ID)) {
                EquipmentMaking.initialize(player, id, interfaceId, slot, 1);
            }
        }

        /* Jewellery */
        if (interfaceId == JEWELLERY_INTERFACE_CONTAINER_ONE || interfaceId == JEWELLERY_INTERFACE_CONTAINER_TWO || interfaceId == JEWELLERY_INTERFACE_CONTAINER_THREE) {
            Jewellery.click(player, id, 1);
        }

        /* Place holder */
        if (interfaceId == PLACEHOLDER) {
            player.getBank().placeHolder(id, slot);
        }

        if (interfaceId == LOOTING_BAG_BANK_CONTAINER_ID) {
            Item item = player.getLootingBag().get(slot);
            if (item == null) {
                return;
            }

            boolean banking = player.getAttribOr(AttributeKey.BANKING, false);

            if (banking) {
                player.getLootingBag().withdrawBank(item.createWithAmount(1), slot);
            }
        }

        if (interfaceId == LOOTING_BAG_DEPOSIT_CONTAINER_ID) {
            Item item = player.inventory().get(slot);
            if (item == null) {
                return;
            }

            player.getLootingBag().deposit(item, 1, null);
        }

        if (interfaceId == GROUP_STORAGE_CONTAINER) {
            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if (group.isPresent() && group.get().storageInUse()) {
                group.get().getGroupStorage(player).withdraw(id, slot, 1);
                return;
            }
        }

        if (interfaceId == WITHDRAW_BANK) {
            if (player.getBank().quantityFive) {
                player.getBank().withdraw(id, slot, 5);
            } else if (player.getBank().quantityTen) {
                player.getBank().withdraw(id, slot, 10);
            } else if (player.getBank().quantityAll) {
                player.getBank().withdraw(id, slot, Integer.MAX_VALUE);
            } else if (player.getBank().quantityX) {
                player.getBank().withdraw(id, slot, player.getBank().currentQuantityX);
            } else {
                player.getBank().withdraw(id, slot, 1);
            }
        }

        if (interfaceId == DEPOSIT_BOX_CONTAINER_ID) {
            if (player.getDepositBox().quantityFive) {
                player.getDepositBox().deposit(id, 5);
            } else if (player.getDepositBox().quantityTen) {
                player.getDepositBox().deposit(id, 10);
            } else if (player.getDepositBox().quantityAll) {
                player.getDepositBox().deposit(id, Integer.MAX_VALUE);
            } else if (player.getDepositBox().quantityX) {
                player.getDepositBox().deposit(id, player.getBank().currentQuantityX);
            } else {
                player.getDepositBox().deposit(id, 1);
            }
        }

        if (interfaceId == INVENTORY_STORE) {
            final Item item = player.inventory().get(slot);

            if (item == null || item.getId() != id) {
                return;
            }

            boolean priceChecking = player.getAttribOr(AttributeKey.PRICE_CHECKING, false);
            if (priceChecking) {
                player.getPriceChecker().deposit(slot, 1);
                return;
            }

            Optional<IronmanGroup> group = IronmanGroupHandler.getPlayersGroup(player);
            if (group.isPresent() && group.get().storageInUse()) {
                group.get().getGroupStorage(player).deposit(slot, 1);
                return;
            }

            if (player.getBank().quantityFive) {
                // System.out.println("deposit 5");
                player.getBank().deposit(slot, 5);
            } else if (player.getBank().quantityTen) {
                // System.out.println("deposit 10");
                player.getBank().deposit(slot, 10);
            } else if (player.getBank().quantityAll) {
                // System.out.println("deposit all");
                player.getBank().deposit(slot, Integer.MAX_VALUE);
            } else if (player.getBank().quantityX) {
                // System.out.println("deposit x: "+player.getBank().currentQuantityX);
                player.getBank().deposit(slot, player.getBank().currentQuantityX);
            } else {
                // System.out.println("deposit 1");
                player.getBank().deposit(slot, 1);
            }
        }

        if (interfaceId == PRICE_CHECKER_DISPLAY_ID) {
            boolean priceChecking = player.getAttribOr(AttributeKey.PRICE_CHECKING, false);
            if (priceChecking) {
                player.getPriceChecker().withdraw(id, 1);
                return;
            }
        }

        if (interfaceId == ShopUtility.ITEM_CHILD_ID || interfaceId == ShopUtility.SLAYER_BUY_ITEM_CHILD_ID) {
            Shop.exchange(player, id, slot, 1, true);
        }

        if (interfaceId == SHOP_INVENTORY) {
            int shop = player.getAttribOr(AttributeKey.SHOP, -1);
            Shop store = World.getWorld().shops.get(shop);
            if (store != null) {
                Shop.exchange(player, id, slot, 1, false);
            }
        }

        if (interfaceId == Dueling.MAIN_INTERFACE_CONTAINER) {
            if (player.getStatus() == PlayerStatus.DUELING) {
                player.getDueling().handleItem(id, 1, slot, player.getDueling().getContainer(), player.inventory());
            }
        }

        // Withdrawing items from gamble
        if (interfaceId == GamblingSession.MY_ITEMS_OFFERED || interfaceId == GamblingSession.OPPONENT_ITEMS_OFFERED) {
            if (player.getStatus() == PlayerStatus.GAMBLING) {
                player.getGamblingSession().handleItem(id, 1, slot, player.getGamblingSession().getContainer(), player.inventory());
            }
        }

        if (interfaceId == REMOVE_INVENTORY_ITEM) {
            if (player.getStatus() == PlayerStatus.TRADING) {
                player.getTrading().handleItem(id, 1, slot, player.inventory(), player.getTrading().getContainer());
            } else if (player.getStatus() == PlayerStatus.DUELING) {
                player.getDueling().handleItem(id, 1, slot, player.inventory(), player.getDueling().getContainer());
            } else if (player.getStatus() == PlayerStatus.GAMBLING) {
                player.getGamblingSession().handleItem(id, 1, slot, player.inventory(), player.getGamblingSession().getContainer());
            }
        }

        if (interfaceId == Trading.CONTAINER_INTERFACE_ID) {
            if (player.getStatus() == PlayerStatus.TRADING) {
                player.getTrading().handleItem(id, 1, slot, player.getTrading().getContainer(), player.inventory());
            }
        }

        if (interfaceId == REMOVE_OFFERED_GAMBLE_ITEMS && player.getGamblingSession() != null) {
            //player.getGamblingSession().removeStakedItem(id, 1, slot);
        }

        if (interfaceId == PRICE_CHECKER_CONTAINER) {
            player.getPriceChecker().withdraw(id, 1);
        }

    }
}
