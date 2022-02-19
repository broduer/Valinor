package com.valinor.net.packet.incoming_packets;

import com.valinor.GameServer;
import com.valinor.fs.NpcDefinition;
import com.valinor.game.content.DropsDisplay;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.NpcCombatInfo;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.Packet;
import com.valinor.net.packet.PacketListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExamineNpcPacketListener implements PacketListener {
    private static final Logger logger = LogManager.getLogger(ExamineNpcPacketListener.class);

    @Override
    public void handleMessage(Player player, Packet packet) {
        int npc = packet.readShort();
        if (npc <= 0) {
            return;
        }

        if (player == null || player.dead()) {
            return;
        }

        if (!player.getBankPin().hasEnteredPin() && GameServer.properties().requireBankPinOnLogin) {
            player.getBankPin().openIfNot();
            return;
        }

        if (player.askForAccountPin()) {
            player.sendAccountPinMessage();
            return;
        }

        NpcCombatInfo combatInfo = World.getWorld().combatInfo(npc);
        NpcDefinition def = World.getWorld().definitions().get(NpcDefinition.class, npc);

        if (!player.locked() && def != null && combatInfo != null && !combatInfo.unattackable) {
            DropsDisplay.start(player, def.name, npc);

            Mob target = player.getCombat().getTarget();
            if (target != null && target.isNpc()) {
                Npc n = target.getAsNpc();
                if (n.combatInfo() != null)
                    player.message(def.name + "'s Current stats: Att: " + n.combatInfo().stats.attack + " Str: " + n.combatInfo().stats.strength + " Def: " + n.combatInfo().stats.defence);
            }
        }

        World.getWorld().examineRepository().npc(npc);
    }
}
