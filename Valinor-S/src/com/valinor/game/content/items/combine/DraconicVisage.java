package com.valinor.game.content.items.combine;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import static com.valinor.util.ItemIdentifiers.*;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 26, 2021
 */
public class DraconicVisage extends Interaction {

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        if (object.definition().name.equalsIgnoreCase("anvil")) {
            if (item.getId() == DRACONIC_VISAGE) {
                player.faceObj(object);
                if (player.skills().xpLevel(Skills.SMITHING) < 90) {
                    player.message("You don't have the skills required to make this you need 90 Smithing.");
                    return true;
                }

                if (!player.inventory().contains(HAMMER)) {
                    player.message("You need an hammer to craft this powerful shield.");
                    return true;
                }

                player.animate(898);
                Chain.bound(player).name("DraconicVisageAnvilTask").runFn(6, () -> player.animate(898)).then(6, () -> {
                    if (player.inventory().containsAll(ANTIDRAGON_SHIELD, DRACONIC_VISAGE)) {
                        player.inventory().remove(new Item(DRACONIC_VISAGE), true);
                        player.inventory().remove(new Item(ANTIDRAGON_SHIELD), true);
                        player.inventory().add(new Item(DRAGONFIRE_SHIELD), true);
                        player.skills().addXp(Skills.SMITHING,2000);
                    }
                    player.getDialogueManager().start(new Dialogue() {
                        @Override
                        protected void start(Object... parameters) {
                            send(DialogueType.ITEM_STATEMENT, new Item(DRAGONFIRE_SHIELD), "", "You successfully combine the Draconic visage with the shield.");
                            setPhase(0);
                        }

                        @Override
                        protected void next() {
                            if (isPhase(0)) {
                                stop();
                            }
                        }
                    });
                });
                return true;
            }
        }
        return false;
    }
}
