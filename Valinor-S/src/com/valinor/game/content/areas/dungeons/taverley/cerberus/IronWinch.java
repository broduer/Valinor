package com.valinor.game.content.areas.dungeons.taverley.cerberus;

import com.valinor.game.world.entity.dialogue.Dialogue;
import com.valinor.game.world.entity.dialogue.DialogueType;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.chainedwork.Chain;

import java.util.LinkedList;
import java.util.List;

import static com.valinor.util.ObjectIdentifiers.IRON_WINCH;

public class IronWinch extends Interaction {

    @Override
    public boolean handleObjectInteraction(Player player, GameObject obj, int option) {
        if (obj.getId() == IRON_WINCH) {
            Tile objectTile = obj.tile();

            Tile destination = objectTile.equals(new Tile(1291, 1254)) ? new Tile(1240, 1226) : //West
                objectTile.equals(new Tile(1328, 1254)) ? new Tile(1368, 1226) : //East
                    objectTile.equals(new Tile(1307, 1269)) ? new Tile(1304, 1290) : //North
                        new Tile(0, 0);

            int region = objectTile.equals(new Tile(1291, 1254)) ? 4883 : //West
                objectTile.equals(new Tile(1328, 1254)) ? 5395 : //East
                    objectTile.equals(new Tile(1307, 1269)) ? 5140 : //North
                        0;

            if (option == 1)
                teleportPlayer(player, destination);
            else
                peek(player, region);
            return true;
        }
        return false;
    }

    private void teleportPlayer(Player player, Tile tile) {
        Chain.bound(null).runFn(2, () -> player.animate(4506)).then(2, () -> player.teleport(tile));
    }

    private void peek(Player player, int region) {
        List<Player> count = new LinkedList<>();

        for (Player p : player.getLocalPlayers()) {
            if (p.tile().region() == region) {
                count.add(p);
            }
        }

        if (count.size() == 0) {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.NPC_STATEMENT, 5870, Expression.HAPPY, "No adventurers are inside the cave.");
                    setPhase(0);
                }

                @Override
                public void next() {
                    if (getPhase() == 0) {
                        stop();
                    }
                }
            });
        } else {
            player.getDialogueManager().start(new Dialogue() {
                @Override
                protected void start(Object... parameters) {
                    send(DialogueType.NPC_STATEMENT, 5870, Expression.HAPPY, count.size() + " adventurer is inside the cave.");
                    setPhase(0);
                }

                @Override
                public void next() {
                    if (getPhase() == 0) {
                        stop();
                    }
                }
            });
        }
    }
}
