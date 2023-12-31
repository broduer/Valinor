package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.game.GameConstants;
import com.valinor.game.content.skill.impl.magic.EnchantDragonBolts;
import com.valinor.game.content.skill.impl.magic.JewelleryEnchantment;
import com.valinor.game.content.skill.impl.smithing.Bar;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.magic.MagicClickSpells;
import com.valinor.game.world.entity.combat.magic.Spell;
import com.valinor.game.world.entity.combat.magic.lunar.BakePie;
import com.valinor.game.world.entity.combat.magic.lunar.SuperglassMake;
import com.valinor.game.world.entity.mob.player.*;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.RequiredItem;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.ClientToServerPackets;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;

import java.util.Arrays;
import java.util.Optional;

import static com.valinor.util.ItemIdentifiers.COINS_995;
import static com.valinor.util.ItemIdentifiers.GOLDSMITH_GAUNTLETS;

/**
 * Handles the packet for using magic spells on items ingame.
 *
 * @author Professor Oak
 */
public class MagicOnItemPacketListener implements PacketListener {

    @Override
    public void handleMessage(Player player, Packet packet) {
        if (packet.getOpcode() == ClientToServerPackets.MAGIC_ON_ITEM_OPCODE) {
            final int slot = packet.readShort();
            final int itemId = packet.readShortA();
            final int childId = packet.readShort();
            final int spellId = packet.readShortA();

            if (player.locked()) {
                return;
            }

            // Some checks
            if (slot < 0 || slot > 27) {
                return;
            }
            Item item = player.inventory().get(slot);
            if (item == null || item.getId() != itemId) {
                return;
            }

            final Optional<MagicClickSpells.MagicSpells> magicSpell = MagicClickSpells.MagicSpells.find(spellId);

            if (magicSpell.isEmpty()) {
                return;
            }

            player.debugMessage("[MagicOnItemPacket] spell=" + magicSpell + " itemId=" + itemId + " slot=" + slot + " childId=" + childId);

            if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
                player.getBankPin().openIfNot();
                return;
            }

            if (player.askForAccountPin()) {
                player.sendAccountPinMessage();
                return;
            }

            if (!player.dead()) {
                player.stopActions(false); // Seems like this does not cancel moving on rs? I can alch while I run.

                //Do actions...
                final MagicClickSpells.MagicSpells magicSpell2 = magicSpell.get();
                final Spell spell = magicSpell2.getSpell();
                final int itemValue = item.definition(World.getWorld()).highAlchValue();
                boolean illegalItem = false;
                Item finalItem = item;

                switch (magicSpell2) {
                    case SUPERGLASS_MAKE:
                        if (player.getSpellbook() != MagicSpellbook.LUNAR)
                            return;
                        if (!spell.canCast(player, null, spell.deleteRunes()))
                            return;
                        SuperglassMake.makeGlass(player);
                        break;
                    case BAKE_PIE:
                        if (player.getSpellbook() != MagicSpellbook.LUNAR)
                            return;
                        if (!spell.canCast(player, null, spell.deleteRunes()))
                            return;
                        BakePie.bake(player);
                        break;
                    case SUPERHEAT_ITEM:
                        if (player.getSpellbook() != MagicSpellbook.NORMAL)
                            return;
                        if (!spell.canCast(player, null, spell.deleteRunes()))
                            return;
                        if (!player.getClickDelay().elapsed(500)) {
                            return;
                        }

                        Optional<Bar> data = Bar.getDefinitionByItem(item.getId());

                        if (data.isEmpty()) {
                            player.message("You can not super heat this item!");
                            return;
                        }

                        for (RequiredItem requiredItem : data.get().getOres()) {
                            if (!player.inventory().containsAll(requiredItem.getItem())) {
                                player.message("You do not contain the required items to super heat!");
                                return;
                            }
                        }

                        if (player.skills().xpLevel(Skills.SMITHING) < data.get().getLevelReq()) {
                            player.message("You need a smithing level of " + data.get().getLevelReq() + " to do super heat this item!");
                            return;
                        }

                        player.animate(722);
                        player.graphic(148);
                        player.getPacketSender().sendTab(6);
                        for (RequiredItem requiredItem : data.get().getOres()) {
                            player.inventory().removeAll(requiredItem.getItem());
                        }
                        player.inventory().addAll(new Item(data.get().getBar()));
                        player.skills().addXp(Skills.MAGIC, spell.baseExperience(), true);
                        player.skills().addXp(Skills.SMITHING, player.getEquipment().hasAt(EquipSlot.HANDS, GOLDSMITH_GAUNTLETS) ? data.get().getXpReward() * 2.5 : data.get().getXpReward(), true);
                        player.getClickDelay().reset();
                        break;
                    case ENCHANT_SAPPHIRE:
                    case ENCHANT_DIAMOND:
                    case ENCHANT_EMERALD:
                    case ENCHANT_ONYX:
                    case ENCHANT_ZENYTE:
                    case ENCHANT_DRAGONSTONE:
                    case ENCHANT_RUBY_TOPAZ:
                        Optional<EnchantDragonBolts.DragonBolts> dragonBolts = EnchantDragonBolts.DragonBolts.forId(itemId);
                        if(dragonBolts.isPresent()) {
                            EnchantDragonBolts.enchant(player, dragonBolts.get());
                            return;
                        }

                        if (JewelleryEnchantment.check(player, itemId, spellId)) {
                            if (!spell.canCast(player, null, spell.deleteRunes())) {
                                return;
                            }
                            JewelleryEnchantment.enchantItem(player, itemId);
                        }
                        break;
                    case LOW_ALCHEMY:
                        if (!item.rawtradable() || item.getId() == COINS_995) {
                            illegalItem = true;
                        }

                        if(finalItem.definition(World.getWorld()).pvpSpawnable && player.gameMode() == GameMode.INSTANT_PKER) {
                            illegalItem = true;
                        }

                        if(illegalItem) {
                            player.message("You can't alch that item.");
                            return;
                        }

                        if (!spell.canCast(player, null, spell.deleteRunes())) {
                            return;
                        }

                        int coinAmountToGive = (int) Math.floor(itemValue * 0.15);

                        if (coinAmountToGive == 0) {
                            coinAmountToGive = 1;
                        }

                        spell.startCast(player, null);

                        item = new Item(item.getId(), 1);

                        player.inventory().remove(item, slot);
                        player.inventory().add(COINS_995, coinAmountToGive);
                        break;
                    case HIGH_ALCHEMY:

                        if (!item.rawtradable() || item.getId() == COINS_995) {
                            illegalItem = true;
                        }

                        if(finalItem.definition(World.getWorld()).pvpSpawnable && player.gameMode() == GameMode.INSTANT_PKER) {
                            illegalItem = true;
                        }

                        if(illegalItem) {
                            player.message("You can't alch that item.");
                            return;
                        }

                        if (!spell.canCast(player, null, spell.deleteRunes())) {
                            return;
                        }

                        coinAmountToGive = (int) Math.floor(itemValue * 0.25);

                        if (coinAmountToGive == 0) {
                            coinAmountToGive = 1;
                        }

                        spell.startCast(player, null);

                        item = new Item(item.getId(), 1);

                        player.inventory().remove(item, slot);
                        player.inventory().add(COINS_995, coinAmountToGive);
                        break;
                }
            }
        }

        if (packet.getOpcode() == ClientToServerPackets.MAGIC_ON_GROUND_ITEM_OPCODE) {
            final int groundItemX = packet.readLEShort();
            final int groundItemId = packet.readUnsignedShort();
            final int groundItemY = packet.readLEShort();
            final int spellId = packet.readShortA();

            if (!player.locked() && !player.dead()) {
                Tile tile = new Tile(groundItemX, groundItemY, player.tile().level);
                Optional<MagicClickSpells.MagicSpells> spell = MagicClickSpells.MagicSpells.find(spellId);
                if (spell.isEmpty()) {
                    return;
                }

                Optional<GroundItem> groundItem = Optional.of(new GroundItem(new Item(groundItemId), tile, player));
                player.putAttrib(AttributeKey.INTERACTED_GROUNDITEM, groundItem.get());
                player.putAttrib(AttributeKey.INTERACTION_OPTION, 4);

                switch (spell.get()) {
                    case TELEKINETIC_GRAB:
                        break;
                }
            }
        }
    }
}
