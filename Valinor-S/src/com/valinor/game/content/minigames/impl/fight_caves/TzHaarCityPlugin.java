package com.valinor.game.content.minigames.impl.fight_caves;

import com.valinor.game.content.bank_pin.dialogue.BankTellerDialogue;
import com.valinor.game.content.minigames.MinigameManager;
import com.valinor.game.content.minigames.impl.fight_caves.dialogue.TzHaarMejJalDialogue;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.valinor.net.packet.interaction.Interaction;
import com.valinor.util.NpcIdentifiers;

/**
 * Created by Kaleem on 19/08/2017.
 */
public class TzHaarCityPlugin extends Interaction {

    private int getStartingWave(Player player) {
        if (player.getMemberRights().isZenyteMemberOrGreater(player)) {
            return 55;
        } else if (player.getMemberRights().isOnyxMemberOrGreater(player)) {
            return 50;
        } else if (player.getMemberRights().isDragonstoneMemberOrGreater(player)) {
            return 45;
        } else if (player.getMemberRights().isDiamondMemberOrGreater(player)) {
            return 40;
        } else if (player.getMemberRights().isRubyMemberOrGreater(player)) {
            return 30;
        } else if (player.getMemberRights().isEmeraldMemberOrGreater(player)) {
            return 25;
        } else if (player.getMemberRights().isSapphireMemberOrGreater(player)) {
            return 20;
        } else {
            return 1;
        }
    }

    @Override
    public boolean handleNpcInteraction(Player player, Npc npc, int option) {
        int npcId = npc.id();

        if (npcId == NpcIdentifiers.TZHAARMEJJAL) {
            if (option == 1 || option == 2) {
                handleMejJal(player);
            }
            return true;
        }

        if (npcId == NpcIdentifiers.TZHAARKETZUH) {
            if(option == 1) {
                player.getDialogueManager().start(new BankTellerDialogue(), npc);
            } else if(option == 2) {
                player.getBank().open();
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleObjectInteraction(Player player, GameObject object, int type) {
        if (object.getId() == 11833) { //Fight caves entrance
            MinigameManager.playMinigame(player, new FightCavesMinigame(getStartingWave(player)));
            return true;
        } else if (object.getId() == 11834) { //Fight caves leaving
            if(player.getMinigame() != null) {
                player.getMinigame().end(player);
            } else {
                player.teleport(FightCavesMinigame.OUTSIDE);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleItemOnObject(Player player, Item item, GameObject object) {
        return super.handleItemOnObject(player, item, object);
    }

    private void handleMejJal(Player player) {
        player.getDialogueManager().start(new TzHaarMejJalDialogue());
    }
}
