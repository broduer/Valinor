package com.valinor.game.content.mechanics;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.ItemActionDialogue;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.net.packet.interaction.Interaction;

import static com.valinor.game.world.entity.AttributeKey.PICKING_PVM_STARTER_WEAPON;
import static com.valinor.game.world.entity.AttributeKey.PICKING_PVP_STARTER_WEAPON;
import static com.valinor.util.CustomItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 02, 2022
 */
public class StarterWeaponPack extends Interaction {

    @Override
    public boolean handleItemInteraction(Player player, Item item, int option) {
        if(option == 1) {
            if(item.getId() == BEGINNER_WEAPON_PACK) {
                player.getDialogueManager().start(new Dialogue() {
                    @Override
                    protected void start(Object... parameters) {
                        send(DialogueType.STATEMENT, "As a starting player you may pick your weapon.", "Choose wisely if you're a pvmer pick PvM otherwise PvP.");
                        setPhase(0);
                    }

                    @Override
                    protected void next() {
                        if(isPhase(0)) {
                            send(DialogueType.OPTION, DEFAULT_OPTION_TITLE, "PVM Weapon", "PVP Weapon", "Pick later");
                            setPhase(1);
                        }
                    }

                    @Override
                    protected void select(int option) {
                        if(isPhase(1)) {
                            if(option == 1) {
                                if(!player.inventory().contains(BEGINNER_WEAPON_PACK)) {
                                    stop();
                                    return;
                                }
                                player.putAttrib(PICKING_PVM_STARTER_WEAPON,true);
                                ItemActionDialogue.sendInterface(player, BEGINNER_CHAINMACE, BEGINNER_CRAWS_BOW);
                            }
                            if(option == 2) {
                                if(!player.inventory().contains(BEGINNER_WEAPON_PACK)) {
                                    stop();
                                    return;
                                }
                                player.putAttrib(PICKING_PVP_STARTER_WEAPON,true);
                                ItemActionDialogue.sendInterface(player, BEGINNER_DRAGON_CLAWS, BEGINNER_AGS);
                            }
                            if(option == 3) {
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
