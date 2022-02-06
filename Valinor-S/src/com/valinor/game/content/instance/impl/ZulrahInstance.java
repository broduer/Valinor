package com.valinor.game.content.instance.impl;

import com.valinor.game.content.instance.InstancedArea;
import com.valinor.game.content.instance.InstancedAreaManager;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.zulrah.Zulrah;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.NpcIdentifiers;
import com.valinor.util.Tuple;
import com.valinor.util.chainedwork.Chain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Patrick van Elderen | February, 10, 2021, 10:27
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class ZulrahInstance {

    /**
     * The Zulrah instance
     */
    private InstancedArea instance;

    /**
     * get the instance
     * @return the instance
     */
    public InstancedArea getInstance() {
        return instance;
    }

    public List<Npc> npcList = new ArrayList<>();

    public ZulrahInstance() {

    }

    private static final Tile ZULRAH_PLAYER_START_TILE = new Tile(2268, 3069);
    private static final Area ZULRAH_AREA = new Area(2251, 3058, 2281, 3088);

    public void enterInstance(Player player, boolean teleport) {
        clear(player);
        instance = InstancedAreaManager.getSingleton().createInstancedArea(player, ZULRAH_AREA);
        if (player != null && instance != null) {
            npcList.clear();
            player.lock();
            Npc zulrah = new Npc(NpcIdentifiers.ZULRAH, ZULRAH_PLAYER_START_TILE.transform(-2, 3, instance.getzLevel()));
            npcList.add(zulrah);
            Chain.bound(null).name("ZulAndraBoatTask").runFn(teleport ? 1 : 9, () -> {
                player.getMovementQueue().clear();
                player.teleport(ZULRAH_PLAYER_START_TILE.x, ZULRAH_PLAYER_START_TILE.y, instance.getzLevel());
                player.unlock();

                zulrah.respawns(false);
                zulrah.putAttrib(AttributeKey.OWNING_PLAYER, new Tuple<>(player.getIndex(), player));
                zulrah.resetFaceTile();
                zulrah.noRetaliation(true);
                zulrah.combatInfo().aggressive = false;
            }).then(1, () -> player.message("Welcome to Zulrah's shrine.")).then(1, () -> {
                World.getWorld().registerNpc(zulrah);
                zulrah.face(zulrah.tile().transform(0, -10, 0));
                zulrah.animate(5073);
                Zulrah.startZulrahBattle(zulrah, player);
            });
        }
    }

    public void clear(Player player) {
        for (Npc npc : npcList) {
            World.getWorld().unregisterNpc(npc);
        }
        npcList.clear();

        for (GroundItem gi : GroundItemHandler.getGroundItems()) {
            if (gi.getTile().inArea(ZULRAH_AREA) && gi.getTile().level == player.tile().level) {
                GroundItemHandler.sendRemoveGroundItem(gi);
            }
        }
    }
}
