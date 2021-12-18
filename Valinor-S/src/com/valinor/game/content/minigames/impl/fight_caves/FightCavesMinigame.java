package com.valinor.game.content.minigames.impl.fight_caves;

import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.minigames.Minigame;
import com.valinor.game.content.minigames.MinigameManager.ItemRestriction;
import com.valinor.game.content.minigames.MinigameManager.ItemType;
import com.valinor.game.content.minigames.MinigameManager.MinigameType;
import com.valinor.game.task.Task;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.dialogue.DialogueManager;
import com.valinor.game.world.entity.dialogue.Expression;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.game.world.route.routes.DumbRoute;
import com.valinor.util.chainedwork.Chain;

import java.util.HashSet;
import java.util.Set;

import static com.valinor.util.ItemIdentifiers.FIRE_CAPE;
import static com.valinor.util.ItemIdentifiers.TOKKUL;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * Handles the fight caves minigames
 *
 * @author 2012 <http://www.rune-server.org/members/dexter+morgan/>
 */
public class FightCavesMinigame extends Minigame {

    /**
     * The starting wave
     */
    private int wave;

    private int waveRotationOffset, spawnRotationOffset;

    private boolean killedJad;

    /**
     * Represents the fight cave minigame
     *
     * @param wave the wave
     */
    public FightCavesMinigame(int wave) {
        this.wave = wave;
    }

    private static final FightCavesMinigame SINGLETON = new FightCavesMinigame(1);

    public static FightCavesMinigame getInstance() {
        return SINGLETON;
    }

    public static final Tile EXIT = new Tile(2439, 5172);

    private static final Tile C = new Tile(2400, 5088);

    private static final Tile S = new Tile(2400, 5070);

    private static final Tile NW = new Tile(2382, 5106);

    private static final Tile SW = new Tile(2380, 5071);

    private static final Tile SE = new Tile(2418, 5082);

    private static final Tile[] ROTATIONS = {SE, SW, C, NW, SW, SE, S, NW, C, SE, SW, S, NW, C, S}; //https://vgy.me/ItO1Db.png

    /**
     * The possition outside the cave
     */
    public static final Tile OUTSIDE = new Tile(2438, 5169, 0);

    private final Set<Npc> npcSet = new HashSet<>();

    public Set<Npc> getNpcSet() {
        return npcSet;
    }

    public Tile nextSpawnPosition() {
        Tile pos = ROTATIONS[spawnRotationOffset];
        if(++spawnRotationOffset >= ROTATIONS.length)
            spawnRotationOffset = 0;
        return pos;
    }

    /**
     * Spawns a wave
     */
    private void beginWave(Player player) {
        int wave = this.wave;
        player.putAttrib(AttributeKey.FIGHT_CAVES_WAVE, wave);
        player.message("<col=ef1020>Wave: " + wave);
        /*
         * "Unique" waves
         */
        if (wave == 63) {
            spawn(player, TZTOKJAD);
            player.message("<col=ef1020>Final Challenge!");
            DialogueManager.npcChat(player, Expression.FURIOUS, TZHAARMEJJAL, "Look out, here comes TzTok-Jad!");
            return;
        }
        if (wave == 62) {
            spawn(player, KETZEK);
            spawn(player, KETZEK + 1);
            return;
        }
        if (wave == 30) {
            spawn(player, YTMEJKOT);
            spawn(player, YTMEJKOT + 1);
            return;
        }
        if (wave == 14) {
            spawn(player, TOKXIL_3121);
            spawn(player, TOKXIL_3121 + 1);
            return;
        }
        /*
         * "Basic" waves
         */
        while (wave >= 31) {
            wave -= 31;
            spawn(player, KETZEK);
        }
        while (wave >= 15) {
            wave -= 15;
            spawn(player, YTMEJKOT);
        }
        while (wave >= 7) {
            wave -= 7;
            spawn(player, TOKXIL_3121);
        }
        while (wave >= 3) {
            wave -= 3;
            spawn(player, TZKEK_3118);
        }
        while (wave > 0) {
            wave--;
            spawn(player, TZKIH_3116);
        }
    }

    private void spawn(Player player, int id) {
        Tile position = nextSpawnPosition();
        spawn(player, id, position.getX(), position.getY(), player.tile().getLevel());
    }

    private void spawn(Player player, int id, int x, int y, int level) {
        Npc npc = new Npc(id, new Tile(x,y, level)).spawn(false);
        DumbRoute.route(npc, player.tile().x, player.tile().y);
        npc.getCombat().setTarget(player);
        npc.getCombat().attack(player);
        npc.putAttrib(AttributeKey.MAX_DISTANCE_FROM_SPAWN,100);
        npcSet.add(npc);
    }

    @Override
    public void start(Player player, boolean login) {
        int level = player.getIndex() * 4;
        int x, y;
        if (login) {
            x = C.getX();
            y = C.getY();
        } else {
            x = 2412;
            y = 5115;
        }
        player.teleport(new Tile(x, y, level));
        player.getPacketSender().sendString(4536, "Wave: " + wave);
        player.getInterfaceManager().openWalkable(4535);
        waveRotationOffset = spawnRotationOffset = World.getWorld().get(ROTATIONS.length - 1);

        if (!login)
            DumbRoute.route(player, C.getX(), C.getY());
        DialogueManager.npcChat(player, Expression.FURIOUS, TZHAARMEJJAL, "You're on your own now JalYt, prepare to fight for your life!");

        Chain.bound(null).runFn(login ? 10 : 5, () -> beginWave(player));
    }

    @Override
    public Task getTask(Player player) {
        return null;
    }

    @Override
    public void end(Player player) {
        player.teleport(OUTSIDE);
        int lastWave = wave - 1;
        if(lastWave == 0) {
            DialogueManager.npcChat(player, Expression.SLIGHTLY_SAD, TZHAARMEJJAL, "Well I suppose you tried... better luck next time.");
        } else {
            int tokkul = 2 + ((lastWave - 50) * (3 + lastWave));
            if(!killedJad) {
                if(!player.getMemberRights().isSapphireMemberOrGreater(player)) {
                    DialogueManager.npcChat(player, Expression.HAPPY, TZHAARMEJJAL, "Well done in the cave, here take TokKul as reward.");
                    player.inventory().addOrDrop(new Item(TOKKUL, tokkul));
                } else {
                    DialogueManager.npcChat(player, Expression.SLIGHTLY_SAD, TZHAARMEJJAL, "Well I suppose you tried... better luck next time.");
                }
            } else {
                DialogueManager.npcChat(player, Expression.FURIOUS, TZHAARMEJJAL, "You have defeated TzTok-Jad, I am most impressed! Please accept this gift.", "Give cape back to me if you not want it.");
                player.inventory().addOrDrop(new Item(FIRE_CAPE, 1));
                player.inventory().addOrDrop(new Item(TOKKUL, tokkul + 4000));
                AchievementsManager.activate(player, Achievements.FIGHT_CAVES_I, 1);
                AchievementsManager.activate(player, Achievements.FIGHT_CAVES_II, 1);
            }
        }
        npcSet.forEach(npc -> World.getWorld().unregisterNpc(npc));
        player.setMinigame(null);
        player.putAttrib(AttributeKey.FIGHT_CAVES_WAVE, 1);
    }

    @Override
    public void killed(Player player, Mob entity) {
        if (entity.isNpc()) {
            Npc npc = entity.getAsNpc();
            npcSet.remove(npc);
            if(player.dead()) {
                //nothing should happen cause you failed
                return;
            }
            if(npc.id() == TZTOKJAD) {
                player.animate(862);
                npc.graphic(453);
                killedJad = true;
                Chain.bound(null).runFn(5, () -> {
                    player.teleport(EXIT);
                });
                return;
            }
            if(npc.id() == TZKEK_3118) {
                int x = npc.getAbsX();
                int y = npc.getAbsY();
                spawn(player, TZKEK_3120, World.getWorld().get(x, x + 1), World.getWorld().get(y, y + 1), player.tile().getLevel());
                spawn(player, TZKEK_3120, World.getWorld().get(x, x + 1), World.getWorld().get(y, y + 1), player.tile().getLevel());
                return;
            }
            if (npcSet.isEmpty()) {
                wave++;
                if (++waveRotationOffset >= ROTATIONS.length)
                    waveRotationOffset = 0;
                spawnRotationOffset = waveRotationOffset;
                beginWave(player);
            }
        }
    }

    @Override
    public ItemType getType() {
        return ItemType.SAFE;
    }

    @Override
    public ItemRestriction getRestriction() {
        return ItemRestriction.NONE;
    }

    @Override
    public MinigameType getMinigameType() {
        return MinigameType.SAFE_MULTI;
    }

    @Override
    public boolean canTeleportOut() {
        return false;
    }

    @Override
    public boolean hasRequirements(Player player) {
        return true;
    }

}
