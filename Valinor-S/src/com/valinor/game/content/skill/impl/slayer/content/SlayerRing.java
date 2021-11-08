package com.valinor.game.content.skill.impl.slayer.content;

import com.valinor.game.content.EffectTimer;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.util.timers.TimerKey;

public class SlayerRing {

    public static void check(Player player) {
        boolean hasTask = player.slayerTaskAmount() > 0;
        int amt = player.slayerTaskAmount();
        String name = Slayer.taskName(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));
        player.message(hasTask ? "You're assigned to kill " + name + " only " + amt + " more to go." : "You need something new to hunt.");
    }

    public static boolean onItemOption2(Player player, Item item) {
        if (item.getId() == 15332) {
            check(player);
            return true;
        }
        return false;
    }

    public static boolean onItemOption3(Player player, Item item) {
        if (item.getId() == 15332) {
            rub_ring(player, false);
            return true;
        }
        return false;
    }

    public static boolean onEquipmentOption(Player player, Item item, int slot) {
        if (item.getId() == 15332 && slot == EquipSlot.RING) {
            rub_ring(player, true);
            return true;
        }
        return false;
    }

    private static void rub_ring(Player player, boolean equipment) {
        player.message("You rub the ring...");
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, "Where would you like to teleport to?", "Slayer Tower", "Fremennik Slayer Dungeon", "Stronghold Slayer Cave", "Dark Beasts");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if (isPhase(0)) {
                    if (option == 1) {
                        do_ring(new Tile(3429, 3534), player, equipment);
                    } else if (option == 2) {
                        do_ring(new Tile(2794, 3615), player, equipment);
                    } else if (option == 3) {
                        do_ring(new Tile(2434, 3423), player, equipment);
                    } else if (option == 4) {
                        do_ring(new Tile(2026, 4636), player, equipment);
                    }
                }
            }
        });
    }

    private static void do_ring(Tile target, Player player, boolean equipment) {
        if (!Teleports.canTeleport(player, true, TeleportType.ABOVE_20_WILD)) {
            return;
        }

        Teleports.basicTeleport(player, World.getWorld().randomTileAround(target, 2));

        int slot = player.getAttribOr(AttributeKey.ITEM_SLOT, -1);

        Item item = (equipment ? player.getEquipment().get(EquipSlot.RING) : player.inventory().get(slot));
        if (item == null) return;

        player.getTimers().cancel(TimerKey.FROZEN);
        player.getTimers().cancel(TimerKey.REFREEZE);
        player.getPacketSender().sendEffectTimer(0, EffectTimer.FREEZE);
    }

}
