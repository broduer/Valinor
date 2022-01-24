package com.valinor.game.world.entity.combat.method.impl.npcs.fightcaves;

import com.valinor.game.content.minigames.impl.fight_caves.FightCavesMinigame;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.util.NpcIdentifiers;

import java.util.ArrayList;
import java.util.stream.IntStream;

import static com.valinor.util.Utils.random;

/**
 * @author Patrick van Elderen | December, 23, 2020, 14:35
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class TzTokJad extends Npc {

    private final ArrayList<YtHurKot> healers = new ArrayList<>();

    public TzTokJad(int id, Tile tile) {
        super(id, tile);
        respawns(false);
    }

    //TODO healers
   /* var spawnedHealers = mob.<Boolean>getAttribOr(AttributeKey.JAD_SPAWNED_HEALERS, false);

    //Do we spawn the healers?
        if (mob.hp() < 130 && !spawnedHealers) {
        mob.spawnHealers(target);
    }*/

    /**
     * Spawn healers.
     */
    public void spawnHealers(Mob mob) {
        if(mob.isPlayer()) {
            Player player = mob.getAsPlayer();

            if (player.getMinigame() == null || !(player.getMinigame() instanceof FightCavesMinigame)) {
                return;
            }

            if (!healers.isEmpty()) {
                return;
            }

            IntStream.range(0, 4 - healers.size()).forEach(i -> {
                Tile tile = FightCavesMinigame.getInstance().nextSpawnPosition();
                YtHurKot monster = new YtHurKot(NpcIdentifiers.YTHURKOT, new Tile(tile.x, tile.y, player.tile().level), this);

                healers.add(monster);
                FightCavesMinigame minigame = (FightCavesMinigame) player.getMinigame();
                minigame.getNpcSet().add(monster);
                monster.walkRadius(100);
                monster.respawns(false);
                monster.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN, 100);
                World.getWorld().registerNpc(monster);
            });
        }
    }

    /**
     * Removes the healer from the healers list.
     *
     * @param healer
     */
    public void removeHealer(YtHurKot healer) {
        healers.remove(healer);
    }
}
