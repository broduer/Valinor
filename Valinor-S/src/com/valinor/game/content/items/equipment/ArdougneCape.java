package com.valinor.game.content.items.equipment;

import com.valinor.game.content.teleport.TeleportType;
import com.valinor.game.content.teleport.Teleports;
import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.masks.graphics.Graphic;
import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.ItemIdentifiers;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 26, 2021
 */
public class ArdougneCape extends Interaction {

    public static final int ARDY_MAXCAPE = 20760;
    public static final int ARDY_CLOAK_4 = 13124;
    private static final Tile MONASTERY = new Tile(2606, 3221);
    private static final Tile ARDY_FARM = new Tile(2664, 3375);

    private static void ardyCapeTeleport(Player player) {
        player.getDialogueManager().start(new Dialogue() {
            @Override
            protected void start(Object... parameters) {
                send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Kandarin Monastery", "Ardougne Farm");
                setPhase(0);
            }

            @Override
            protected void select(int option) {
                if(isPhase(0)) {
                    if(option == 1) {
                        if (Teleports.canTeleport(player, true, TeleportType.GENERIC)) {
                            Teleports.basicTeleport(player, MONASTERY, 3872, new Graphic(1237, 92));
                        }
                    } else if(option == 2) {
                        if (Teleports.canTeleport(player,true, TeleportType.GENERIC)) {
                            Teleports.basicTeleport(player, ARDY_FARM, 3872, new Graphic(1237, 92));
                        }
                    }
                }
            }
        });
    }

    private static void teleport(Player player, Tile tile) {
        player.optionsTitled("Dangerous teleport! Would you like to proceed?", "Yes.", "No.", () -> {
            if(!player.getEquipment().contains(ItemIdentifiers.ARDOUGNE_CLOAK_4) && !player.inventory().contains(ItemIdentifiers.ARDOUGNE_CLOAK_4)) {
                return;
            }
            if (Teleports.canTeleport(player, true, TeleportType.ABOVE_20_WILD)) {
                Teleports.basicTeleport(player, tile,3872, new Graphic(1237, 92));
            }
        });
    }

    @Override
    public boolean handleEquipmentAction(Player player, Item item, int slot) {
        if (item.getId() == ItemIdentifiers.ARDOUGNE_CLOAK_4 && slot == EquipSlot.CAPE) {
            player.getDialogueManager().start(new ArdougneCloakD());
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if (option == 3) {
            if (item.getId() == ItemIdentifiers.ARDOUGNE_CLOAK_4) {
                player.getDialogueManager().start(new ArdougneCloakD());
                return true;
            }
        }
        return false;
    }

    private static class ArdougneCloakD extends Dialogue {

        @Override
        protected void start(Object... parameters) {
            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "Revenants cave.", "Resource arena.", "Nevermind.");
            setPhase(0);
        }

        @Override
        protected void select(int option) {
            if(isPhase(0)) {
                if (option == 1) {
                    teleport(player, new Tile(3254,10189));
                }
                if (option == 2) {
                    teleport(player, new Tile(3184,3943));
                }
                if (option == 3) {
                    stop();
                }
            }
        }
    }
}
