package com.valinor.game.world.entity.mob.npc;

import com.valinor.fs.NpcDefinition;
import com.valinor.game.content.EffectTimer;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.achievements.AchievementsManager;
import com.valinor.game.content.announcements.ServerAnnouncements;
import com.valinor.game.content.areas.burthope.warriors_guild.MagicalAnimator;
import com.valinor.game.content.areas.wilderness.content.boss_event.WildernessBossEvent;
import com.valinor.game.content.areas.zeah.catacombs.KourendCatacombs;
import com.valinor.game.content.daily_tasks.DailyTaskManager;
import com.valinor.game.content.daily_tasks.DailyTasks;
import com.valinor.game.content.minigames.impl.fight_caves.FightCavesMinigame;
import com.valinor.game.content.raids.chamber_of_xeric.great_olm.Phases;
import com.valinor.game.content.raids.party.Party;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.skill.impl.slayer.slayer_partner.SlayerPartner;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.Mob;
import com.valinor.game.world.entity.combat.method.impl.CommonCombatMethod;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.kalphite.KalphiteQueenFirstForm;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.kalphite.KalphiteQueenSecondForm;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.wilderness.vetion.VetionMinion;
import com.valinor.game.world.entity.combat.method.impl.npcs.bosses.zulrah.Zulrah;
import com.valinor.game.world.entity.combat.method.impl.npcs.fightcaves.TzTokJad;
import com.valinor.game.world.entity.combat.method.impl.npcs.godwars.GwdLogic;
import com.valinor.game.world.entity.combat.method.impl.npcs.hydra.AlchemicalHydra;
import com.valinor.game.world.entity.combat.method.impl.npcs.karuulm.Drake;
import com.valinor.game.world.entity.combat.method.impl.npcs.karuulm.Wyrm;
import com.valinor.game.world.entity.combat.method.impl.npcs.slayer.Gargoyle;
import com.valinor.game.world.entity.combat.method.impl.npcs.slayer.Nechryael;
import com.valinor.game.world.entity.combat.method.impl.npcs.slayer.kraken.KrakenBoss;
import com.valinor.game.world.entity.combat.method.impl.npcs.slayer.superiors.nechryarch.NechryarchDeathSpawn;
import com.valinor.game.world.entity.mob.npc.droptables.ItemDrops;
import com.valinor.game.world.entity.mob.npc.droptables.ScalarLootTable;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.npc.pets.PetAI;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Area;
import com.valinor.game.world.position.Tile;
import com.valinor.util.*;
import com.valinor.util.chainedwork.Chain;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.valinor.game.content.collection_logs.CollectionLog.COS_RAIDS_KEY;
import static com.valinor.game.content.collection_logs.LogType.BOSSES;
import static com.valinor.game.content.collection_logs.LogType.OTHER;
import static com.valinor.game.world.entity.AttributeKey.*;
import static com.valinor.util.CustomItemIdentifiers.TASK_BOTTLE_SKILLING;
import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * Represents an npc's death task, which handles everything
 * an npc does before and after their death animation (including it),
 * such as dropping their drop table items.
 *
 * @author relex lawl
 * @author Created by Bart on 10/6/2015.
 */
public class NpcDeath {

    private static final Logger logger = LogManager.getLogger(NpcDeath.class);

    private static final List<Integer> customDrops = Arrays.asList(WHIRLPOOL_496, KRAKEN, CAVE_KRAKEN, WHIRLPOOL, ZULRAH, ZULRAH_2043, ZULRAH_2044);

    public static void execute(Npc npc) {
        try {
            // Path reset instantly when hitsplat appears killing the npc.
            var respawnTimer = Utils.secondsToTicks(45);// default 45 seconds
            NpcDefinition def = World.getWorld().definitions().get(NpcDefinition.class, npc.id());
            if(def != null) {
                if(def.combatlevel >= 1 && def.combatlevel <= 50) {
                    respawnTimer = Utils.secondsToTicks(30);//30 seconds
                } else if(def.combatlevel >= 51 && def.combatlevel <= 150) {
                    respawnTimer = Utils.secondsToTicks(25);//25 seconds
                } else {
                    respawnTimer = Utils.secondsToTicks(20);// 20 seconds
                }
            }

            respawnTimer = 5;

            npc.getMovementQueue().clear();
            npc.lockNoDamage();

            // Reset interacting entity..
            npc.setEntityInteraction(null);

            Optional<Player> killer_id = npc.getCombat().getKiller();

            // Player that did the most damage.
            Player killer = killer_id.orElse(null);

            Chain.bound(null).runFn(1, () -> {
                // 1t later facing is reset. Stops npcs looking odd when they reset facing their target the tick they die.
                npc.resetFaceTile();
            });

            if (killer != null) {
                respawnTimer -= switch (killer.getMemberRights()) {
                    case NONE -> 0;
                    case SAPPHIRE_MEMBER -> Utils.secondsToTicks(2);
                    case EMERALD_MEMBER -> Utils.secondsToTicks(4);
                    case RUBY_MEMBER -> Utils.secondsToTicks(6);
                    case DIAMOND_MEMBER -> Utils.secondsToTicks(8);
                    case DRAGONSTONE_MEMBER, ONYX_MEMBER, ZENYTE_MEMBER -> Utils.secondsToTicks(10);
                };

                var biggest_and_baddest_perk = killer.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.BIGGEST_AND_BADDEST) && Slayer.creatureMatches(killer, npc.id());
                var ancientRevSpawnRoll = 25;
                var superiorSpawnRoll = biggest_and_baddest_perk ? 4 : 6;

                var reduction = ancientRevSpawnRoll * killer.memberAncientRevBonus() / 100;
                ancientRevSpawnRoll -= reduction;

                var legendaryInsideCave = killer.tile().memberCave() && killer.getMemberRights().isDragonstoneMemberOrGreater(killer);
                var VIPInsideCave = killer.tile().memberCave() && killer.getMemberRights().isDragonstoneMemberOrGreater(killer);
                var SponsorInsideCave = killer.tile().memberCave() && killer.getMemberRights().isDragonstoneMemberOrGreater(killer);
                if(legendaryInsideCave)
                    respawnTimer = 34;
                if(VIPInsideCave)
                    respawnTimer = 30;
                if(SponsorInsideCave)
                    respawnTimer = 25;

                killer.getCombat().reset();

                // Increment kill.
                killer.getSlayerKillLog().addKill(npc);
                if (!npc.isWorldBoss() || npc.id() != THE_NIGHTMARE_9430 || npc.id() != KALPHITE_QUEEN_6500) {
                    killer.getBossKillLog().addKill(npc);
                }

                if (npc.def().name.equalsIgnoreCase("Yak")) {
                    AchievementsManager.activate(killer, Achievements.YAK_HUNTER, 1);
                }

                if (npc.def().name.equalsIgnoreCase("Rock Crab")) {
                    AchievementsManager.activate(killer, Achievements.ROCK_CRAB_HUNTER, 1);
                }

                if (npc.def().name.equalsIgnoreCase("Sand Crab")) {
                    AchievementsManager.activate(killer, Achievements.SAND_CRAB_HUNTER, 1);
                }

                if (npc.def().name.equalsIgnoreCase("Experiment")) {
                    AchievementsManager.activate(killer, Achievements.EXPERIMENTS_HUNTER, 1);
                }

                if (npc.def().name.equalsIgnoreCase("Adamant dragon")) {
                    var kc = killer.<Integer>getAttribOr(ADAMANT_DRAGONS_KILLED, 0) + 1;
                    killer.putAttrib(ADAMANT_DRAGONS_KILLED, kc);
                }

                if (npc.def().name.equalsIgnoreCase("Rune dragon")) {
                    var kc = killer.<Integer>getAttribOr(RUNE_DRAGONS_KILLED, 0) + 1;
                    killer.putAttrib(RUNE_DRAGONS_KILLED, kc);
                }

                if (npc.def().name.equalsIgnoreCase("Lava dragon")) {
                    var kc = killer.<Integer>getAttribOr(LAVA_DRAGONS_KILLED, 0) + 1;
                    killer.putAttrib(LAVA_DRAGONS_KILLED, kc);
                }

                if (npc.def().name.contains("dragon") || npc.def().name.contains("Dragon")) {
                    AchievementsManager.activate(killer, Achievements.DRAGON_SLAYER_I, 1);
                    killer.getTaskBottleManager().increase(BottleTasks.DRAGONS);
                }

                if (npc.def().name.contains("Black dragon") || npc.def().name.contains("black dragon")) {
                    AchievementsManager.activate(killer, Achievements.DRAGON_SLAYER_II, 1);
                }

                if (npc.def().name.equalsIgnoreCase("K'ril Tsutsaroth") || npc.def().name.equalsIgnoreCase("General Graardor") || npc.def().name.equalsIgnoreCase("Commander Zilyana") || npc.def().name.equalsIgnoreCase("Kree'arra")) {
                    AchievementsManager.activate(killer, Achievements.GODWAR, 1);
                }

                if (npc.def().name.contains("Revenant") || npc.def().name.contains("revenant")) {
                    AchievementsManager.activate(killer, Achievements.REVENANT_HUNTER, 1);
                    killer.getTaskBottleManager().increase(BottleTasks.REVENANTS);
                }

                if (npc.def().name.equalsIgnoreCase("Alchemical Hydra")) {
                    killer.getTaskBottleManager().increase(BottleTasks.ALCHEMICAL_HYDRA);
                }

                if (npc.def().name.equalsIgnoreCase("Chaos Fanatic")) {
                    killer.getTaskBottleManager().increase(BottleTasks.CHAOS_FANATIC);
                }

                if (npc.def().name.equalsIgnoreCase("Corporeal Beast")) {
                    AchievementsManager.activate(killer, Achievements.CORPOREAL_CRITTER, 1);
                    killer.getTaskBottleManager().increase(BottleTasks.CORPOREAL_BEAST);
                }

                if (npc.def().name.equalsIgnoreCase("Crazy archaeologist")) {
                    killer.getTaskBottleManager().increase(BottleTasks.CRAZY_ARCHAEOLOGIST);
                }

                if (npc.def().name.equalsIgnoreCase("Demonic gorilla")) {
                    killer.getTaskBottleManager().increase(BottleTasks.DEMONIC_GORILLA);
                }

                if (npc.def().name.equalsIgnoreCase("King Black Dragon")) {
                    AchievementsManager.activate(killer, Achievements.DRAGON_SLAYER_II, 1);
                    killer.getTaskBottleManager().increase(BottleTasks.KING_BLACK_DRAGON);

                    if (World.getWorld().rollDie(10, 1)) {
                        npc.respawns(false);//King black dragon can no longer spawn his ancient version spawns.
                        var ancientKingBlackDragon = new Npc(ANCIENT_KING_BLACK_DRAGON, npc.spawnTile()).respawns(false);
                        World.getWorld().getNpcs().add(ancientKingBlackDragon);
                    }
                }

                if (npc.id() == ANCIENT_KING_BLACK_DRAGON) {
                    AchievementsManager.activate(killer, Achievements.DRAGON_SLAYER_II, 1);
                    killer.getTaskBottleManager().increase(BottleTasks.KING_BLACK_DRAGON);
                    if(!npc.ancientSpawn()) {
                        Chain.bound(null).runFn(30, () -> {
                            var kingBlackDragon = new Npc(KING_BLACK_DRAGON, npc.spawnTile());
                            World.getWorld().getNpcs().add(kingBlackDragon);
                        });
                    }
                }

                if (npc.def().name.equalsIgnoreCase("Lizardman shaman")) {
                    killer.getTaskBottleManager().increase(BottleTasks.LIZARDMAN_SHAMAN);
                }

                if (npc.def().name.equalsIgnoreCase("Thermonuclear smoke devil")) {
                    killer.getTaskBottleManager().increase(BottleTasks.THERMONUCLEAR_SMOKE_DEVIL);
                }

                if (npc.def().name.equalsIgnoreCase("Vet'ion")) {
                    killer.getTaskBottleManager().increase(BottleTasks.VETION);
                }

                if (npc.def().name.equalsIgnoreCase("Chaos Elemental")) {
                    killer.getTaskBottleManager().increase(BottleTasks.CHAOS_ELEMENTAL);
                    AchievementsManager.activate(killer, Achievements.ULTIMATE_CHAOS, 1);

                    if (World.getWorld().rollDie(10, 1)) {
                        npc.respawns(false);//Chaos elemental can no longer spawn his ancient version spawns.
                        var ancientChaosEle = new Npc(ANCIENT_CHAOS_ELEMENTAL, npc.spawnTile()).respawns(false);
                        World.getWorld().getNpcs().add(ancientChaosEle);
                    }
                }

                if (npc.id() == ANCIENT_CHAOS_ELEMENTAL) {
                    killer.getTaskBottleManager().increase(BottleTasks.CHAOS_ELEMENTAL);
                    AchievementsManager.activate(killer, Achievements.ULTIMATE_CHAOS, 1);

                    if(!npc.ancientSpawn()) {
                        Chain.bound(null).runFn(30, () -> {
                            var chaosElemental = new Npc(CHAOS_ELEMENTAL, npc.spawnTile());
                            World.getWorld().getNpcs().add(chaosElemental);
                        });
                    }
                }

                if (npc.def().name.contains("Zulrah")) {
                    killer.getTaskBottleManager().increase(BottleTasks.ZULRAH);
                }

                if (npc.def().name.equalsIgnoreCase("Vorkath")) {
                    killer.getTaskBottleManager().increase(BottleTasks.VORKATH);
                }

                if (npc.def().name.equalsIgnoreCase("Zombies Champion") || npc.def().name.equalsIgnoreCase("Brutal lava dragon")) {
                    killer.getTaskBottleManager().increase(BottleTasks.WORLD_BOSS);
                }

                if (npc.def().name.equalsIgnoreCase("Kalphite Queen")) {
                    killer.getTaskBottleManager().increase(BottleTasks.KALPHITE_QUEEN);
                }

                if (npc.def().name.equalsIgnoreCase("Dagannoth Supreme") || npc.def().name.equalsIgnoreCase("Dagannoth Prime") || npc.def().name.equalsIgnoreCase("Dagannoth Rex")) {
                    AchievementsManager.activate(killer, Achievements.LORD_OF_THE_RINGS, 1);
                    killer.getTaskBottleManager().increase(BottleTasks.DAGANNOTH_KINGS);
                }

                if (npc.def().name.equalsIgnoreCase("Giant Mole")) {
                    killer.getTaskBottleManager().increase(BottleTasks.GIANT_MOLE);
                }

                if (npc.def().name.contains("green dragon")) {
                    DailyTaskManager.increase(DailyTasks.GREEN_DRAGONS, killer);
                }

                if (npc.def().name.equalsIgnoreCase("Barrelchest")) {
                    if (World.getWorld().rollDie(10, 1)) {
                        npc.respawns(false);//Barrelchest can no longer spawn his ancient version spawns.
                        var ancientBarrelchest = new Npc(ANCIENT_BARRELCHEST, npc.spawnTile()).respawns(false);
                        World.getWorld().getNpcs().add(ancientBarrelchest);
                    }
                }

                if (npc.id() == ANCIENT_BARRELCHEST) {

                    if(!npc.ancientSpawn()) {
                        Chain.bound(null).runFn(30, () -> {
                            var barrelchest = new Npc(BARRELCHEST_6342, npc.spawnTile());
                            World.getWorld().getNpcs().add(barrelchest);
                        });
                    }
                }

                if (npc.id() == AWAKENED_ALTAR) {
                    ObjectManager.remove(28923, 1694, 9904, killer.getSkotizoInstance().getInstance().getzLevel()); // Remove
                    // North
                    // -
                    // Awakened
                    // Altar
                    ObjectManager.addObj(new GameObject(28924, new Tile(1694, 9904, killer.getSkotizoInstance().getInstance().getzLevel()), 10, 2)); // North - Empty Altar
                    killer.getPacketSender().sendChangeSprite(29232, (byte) 0);
                    killer.getSkotizoInstance().altarCount--;
                    killer.getSkotizoInstance().northAltar = false;
                    killer.getSkotizoInstance().altarMap.remove(1);
                }
                if (npc.id() == AWAKENED_ALTAR_7290) {
                    ObjectManager.remove(28923, 1696, 9871, killer.getSkotizoInstance().getInstance().getzLevel()); // Remove
                    // South
                    // -
                    // Awakened
                    // Altar
                    ObjectManager.addObj(new GameObject(28924, new Tile(1696, 9871, killer.getSkotizoInstance().getInstance().getzLevel()),10, 0)); // South - Empty Altar
                    killer.getPacketSender().sendChangeSprite(29233, (byte) 0);
                    killer.getSkotizoInstance().altarCount--;
                    killer.getSkotizoInstance().southAltar = false;
                    killer.getSkotizoInstance().altarMap.remove(2);
                }
                if (npc.id() == AWAKENED_ALTAR_7292) {
                    ObjectManager.remove(28923, 1678, 9888, killer.getSkotizoInstance().getInstance().getzLevel()); // Remove
                    // West
                    // -
                    // Awakened
                    // Altar
                    ObjectManager.addObj(new GameObject(28924, new Tile(1678, 9888, killer.getSkotizoInstance().getInstance().getzLevel()), 10, 1)); // West - Empty Altar
                    killer.getPacketSender().sendChangeSprite(29234, (byte) 0);
                    killer.getSkotizoInstance().altarCount--;
                    killer.getSkotizoInstance().westAltar = false;
                    killer.getSkotizoInstance().altarMap.remove(3);
                }
                if (npc.id() == AWAKENED_ALTAR_7294) {
                    ObjectManager.remove(28923, 1714, 9888, killer.getSkotizoInstance().getInstance().getzLevel()); // Remove
                    // East
                    // -
                    // Awakened
                    // Altar
                    ObjectManager.addObj(new GameObject(28924, new Tile(1714, 9888, killer.getSkotizoInstance().getInstance().getzLevel()), 10, 3)); // East - Empty Altar
                    killer.getPacketSender().sendChangeSprite(29235, (byte) 0);
                    killer.getSkotizoInstance().altarCount--;
                    killer.getSkotizoInstance().eastAltar = false;
                    killer.getSkotizoInstance().altarMap.remove(4);
                }
                if (npc.id() == DARK_ANKOU) {
                    killer.getSkotizoInstance().ankouSpawned = false;
                }

                Slayer.reward(killer, npc);
                SlayerPartner.reward(killer, npc);

                if (killer.getMinigame() != null) {
                    killer.getMinigame().killed(killer, npc);
                }

                // Check if the dead npc is a barrows brother. Award killcount.
                var isBarrowsBro = false;

                switch (npc.id()) {
                    case DHAROK_THE_WRETCHED -> {
                        isBarrowsBro = true;
                        killer.putAttrib(DHAROK, 1);
                    }
                    case AHRIM_THE_BLIGHTED -> {
                        isBarrowsBro = true;
                        killer.putAttrib(AHRIM, 1);
                    }
                    case VERAC_THE_DEFILED -> {
                        isBarrowsBro = true;
                        killer.putAttrib(VERAC, 1);
                    }
                    case TORAG_THE_CORRUPTED -> {
                        isBarrowsBro = true;
                        killer.putAttrib(TORAG, 1);
                    }
                    case KARIL_THE_TAINTED -> {
                        isBarrowsBro = true;
                        killer.putAttrib(KARIL, 1);
                    }
                    case GUTHAN_THE_INFESTED -> {
                        isBarrowsBro = true;
                        killer.putAttrib(GUTHAN, 1);
                    }

                    case KrakenBoss.KRAKEN_NPCID -> {// Kraken boss transmogged KC
                        AchievementsManager.activate(killer, Achievements.SQUIDWARD, 1);
                        killer.getTaskBottleManager().increase(BottleTasks.KRAKEN);
                    }

                    case ADAMANT_DRAGON, ADAMANT_DRAGON_8090, RUNE_DRAGON, RUNE_DRAGON_8031, RUNE_DRAGON_8091 -> AchievementsManager.activate(killer, Achievements.DRAGON_SLAYER_III, 1);

                    case CERBERUS, CERBERUS_5863, CERBERUS_5866 -> {
                        killer.getTaskBottleManager().increase(BottleTasks.CERBERUS);
                        AchievementsManager.activate(killer, Achievements.FLUFFY, 1);

                        if (World.getWorld().rollDie(superiorSpawnRoll, 1)) {
                            npc.respawns(false);//Cerberus can no longer spawn his superior spawns in 1 minute.
                            var kerberos = new Npc(KERBEROS, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(kerberos);
                        }
                    }

                    case KERBEROS -> Chain.bound(null).runFn(30, () -> {
                        var cerberus = new Npc(CERBERUS, npc.spawnTile());
                        World.getWorld().getNpcs().add(cerberus);
                    });

                    case LIZARDMAN_SHAMAN_6767 -> {
                        AchievementsManager.activate(killer, Achievements.DR_CURT_CONNORS, 1);
                    }

                    case THERMONUCLEAR_SMOKE_DEVIL -> {
                        AchievementsManager.activate(killer, Achievements.TSJERNOBYL, 1);
                    }

                    case VETION, VETION_REBORN -> {
                        AchievementsManager.activate(killer, Achievements.VETION, 1);
                    }

                    case VENENATIS_6610 -> {
                        killer.getTaskBottleManager().increase(BottleTasks.VENENATIS);
                        AchievementsManager.activate(killer, Achievements.VENENATIS, 1);

                        if (World.getWorld().rollDie(superiorSpawnRoll, 1)) {
                            npc.respawns(false);//Venenatis can no longer spawn his superior spawns in 1 minute.
                            var arachne = new Npc(CustomNpcIdentifiers.ARACHNE, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(arachne);
                        }
                    }

                    case ARACHNE -> Chain.bound(null).runFn(30, () -> {
                        var venenatis = new Npc(VENENATIS_6610, npc.spawnTile());
                        World.getWorld().getNpcs().add(venenatis);
                    });

                    case CALLISTO_6609 -> {
                        killer.getTaskBottleManager().increase(BottleTasks.CALLISTO);
                        AchievementsManager.activate(killer, Achievements.BEAR_GRYLLS, 1);

                        if (World.getWorld().rollDie(superiorSpawnRoll, 1)) {
                            npc.respawns(false);//Callisto can no longer spawn his superior spawns in 1 minute.
                            var artio = new Npc(ARTIO, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(artio);
                        }
                    }

                    case ARTIO -> Chain.bound(null).runFn(30, () -> {
                        var callisto = new Npc(CALLISTO_6609, npc.spawnTile());
                        World.getWorld().getNpcs().add(callisto);
                    });

                    case REVENANT_DARK_BEAST -> {
                        if (World.getWorld().rollDie(ancientRevSpawnRoll, 1)) {
                            npc.respawns(false);//Rev dark beast can no longer spawn when we spawn his ancient version
                            var ancientDarkbeast = new Npc(ANCIENT_REVENANT_DARK_BEAST, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(ancientDarkbeast);
                        }
                    }

                    case ANCIENT_REVENANT_DARK_BEAST -> {
                        if(!npc.ancientSpawn()) {
                            Chain.bound(null).runFn(30, () -> {
                                var revDarkBeast = new Npc(REVENANT_DARK_BEAST, npc.spawnTile());
                                World.getWorld().getNpcs().add(revDarkBeast);
                            });
                        }
                    }

                    case REVENANT_ORK -> {
                        if (World.getWorld().rollDie(ancientRevSpawnRoll, 1)) {
                            npc.respawns(false);//Rev ork can no longer spawn when we spawn his ancient version
                            var ancientOrk = new Npc(ANCIENT_REVENANT_ORK, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(ancientOrk);
                        }
                    }

                    case ANCIENT_REVENANT_ORK -> {
                        if(!npc.ancientSpawn()) {
                            Chain.bound(null).runFn(30, () -> {
                                var revenantOrk = new Npc(REVENANT_ORK, npc.spawnTile());
                                World.getWorld().getNpcs().add(revenantOrk);
                            });
                        }
                    }

                    case REVENANT_CYCLOPS -> {
                        if (World.getWorld().rollDie(ancientRevSpawnRoll, 1)) {
                            npc.respawns(false);//Rev cyclops can no longer spawn when we spawn his ancient version
                            var ancientCyclops = new Npc(ANCIENT_REVENANT_CYCLOPS, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(ancientCyclops);
                        }
                    }

                    case ANCIENT_REVENANT_CYCLOPS -> {
                        if(!npc.ancientSpawn()) {
                            Chain.bound(null).runFn(30, () -> {
                                var revCyclops = new Npc(REVENANT_CYCLOPS, npc.spawnTile());
                                World.getWorld().getNpcs().add(revCyclops);
                            });
                        }
                    }

                    case REVENANT_DRAGON -> {
                        if (World.getWorld().rollDie(ancientRevSpawnRoll, 1)) {
                            npc.respawns(false);//Rev dragon can no longer spawn when we spawn his ancient version
                            var ancientDragon = new Npc(ANCIENT_REVENANT_DRAGON, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(ancientDragon);
                        }
                    }

                    case ANCIENT_REVENANT_DRAGON -> {
                        if(!npc.ancientSpawn()) {
                            Chain.bound(null).runFn(30, () -> {
                                var revDragon = new Npc(REVENANT_DRAGON, npc.spawnTile());
                                World.getWorld().getNpcs().add(revDragon);
                            });
                        }
                    }

                    case REVENANT_KNIGHT -> {
                        if (World.getWorld().rollDie(ancientRevSpawnRoll, 1)) {
                            npc.respawns(false);//Rev knight can no longer spawn when we spawn his ancient version
                            var ancientKnight = new Npc(ANCIENT_REVENANT_KNIGHT, npc.spawnTile()).respawns(false);
                            World.getWorld().getNpcs().add(ancientKnight);
                        }
                    }

                    case ANCIENT_REVENANT_KNIGHT -> {
                        if(!npc.ancientSpawn()) {
                            Chain.bound(null).runFn(30, () -> {
                                var revKnight = new Npc(REVENANT_KNIGHT, npc.spawnTile());
                                World.getWorld().getNpcs().add(revKnight);
                            });
                        }
                    }

                    case ZULRAH, ZULRAH_2043, ZULRAH_2044 -> {
                        AchievementsManager.activate(killer, Achievements.SNAKE_CHARMER, 1);
                        DailyTaskManager.increase(DailyTasks.ZULRAH, killer);
                    }

                    case VORKATH_8061 -> {
                        AchievementsManager.activate(killer, Achievements.VORKY, 1);
                    }

                    case ROCK_CRAB -> {
                        DailyTaskManager.increase(DailyTasks.ROCK_CRABS, killer);
                    }

                    case EXPERIMENT, EXPERIMENT_1274, EXPERIMENT_1275 -> {
                        DailyTaskManager.increase(DailyTasks.EXPERIMENTS, killer);
                    }

                    case CORRUPTED_NECHRYARCH -> {
                        DailyTaskManager.increase(DailyTasks.CORRUPTED_NECHRYARCH, killer);
                    }
                }

                if (isBarrowsBro) {
                    killer.clearAttrib(barrowsBroSpawned);
                    killer.putAttrib(BARROWS_MONSTER_KC, 1 + (int) killer.getAttribOr(BARROWS_MONSTER_KC, 0));
                    var newkc = killer.getAttribOr(BARROWS_MONSTER_KC, 0);
                    killer.getPacketSender().sendString(4536, "Kill Count: " + newkc);
                    killer.getPacketSender().sendEntityHintRemoval(false);
                }

                //Make sure spawns are killed on boss death
                if (npc.id() == SCORPIA) {
                    killer.getTaskBottleManager().increase(BottleTasks.SCORPIA);
                    AchievementsManager.activate(killer, Achievements.BARK_SCORPION, 1);

                    if (World.getWorld().rollDie(superiorSpawnRoll, 1)) {
                        npc.respawns(false);//Cerberus can no longer spawn his superior spawns in 1 minute.
                        var skorpios = new Npc(SKORPIOS, npc.spawnTile()).respawns(false);
                        World.getWorld().getNpcs().add(skorpios);
                    }
                }

                if (npc.id() == SKORPIOS) {
                    World.getWorld().getNpcs().forEachInArea(new Area(3219, 3248, 10329, 10353), n -> {
                        if (n.id() == SCORPIAS_GUARDIAN) {
                            World.getWorld().unregisterNpc(n);
                        }
                    });

                    Chain.bound(null).runFn(30, () -> {
                        var scorpia = new Npc(SCORPIA, npc.spawnTile());
                        World.getWorld().getNpcs().add(scorpia);
                    });
                }

                //Do custom area deaths
                if (killer.getController() != null) {
                    killer.getController().defeated(killer, npc);
                }

                var killerOpp = killer.<Mob>getAttribOr(AttributeKey.LAST_DAMAGER, null);
                if (killer.<Integer>getAttribOr(AttributeKey.MULTIWAY_AREA, -1) == 0 && killerOpp != null && killerOpp == npc) { // Last fighting with this dead npc.
                    killer.clearAttrib(AttributeKey.LAST_WAS_ATTACKED_TIME); // Allow instant aggro from other npcs/players.
                }

                var done = false;
                for (MagicalAnimator.ArmourSets set : MagicalAnimator.ArmourSets.values()) {
                    if (!done && set.npc == npc.id()) {
                        done = true;
                        killer.getPacketSender().sendEntityHintRemoval(true);// remove hint arrow
                    }
                }
            }

            //Do death animation
            if (npc instanceof AlchemicalHydra) {
                npc.animate(8257);
                Chain.bound(null).runFn(2, () -> {
                    npc.transmog(8622);
                    npc.animate(8258);
                });
            } else if (npc instanceof Drake) {
                npc.animate(8277);
                Chain.bound(null).runFn(1, () -> {
                    npc.transmog(8613);
                    npc.animate(8278);
                });
            } else {
                npc.animate(npc.combatInfo() != null ? npc.combatInfo().animations.death : -1);
            }

            // Death sound!
            if (killer != null) {
                if (npc.combatInfo() != null && npc.combatInfo().sounds != null) {
                    var sounds = npc.combatInfo().sounds.death;
                    if (sounds != null && sounds.length > 0) {
                        killer.sound(Utils.randomElement(sounds));
                    }
                }
            }

            int finalRespawnTimer = respawnTimer;
            Chain.bound(null).runFn(npc.combatInfo() != null ? npc.combatInfo().deathlen : 5, () -> {
                if (killer != null) {
                    //Do inferno minigame death here and fight caves

                    //Do death scripts
                    if (npc.id() == KRAKEN) {
                        KrakenBoss.onDeath(npc); //Kraken uses its own death script
                    }

                    if (npc.getCombatMethod() instanceof CommonCombatMethod) {
                        CommonCombatMethod commonCombatMethod = (CommonCombatMethod) npc.getCombatMethod();
                        commonCombatMethod.set(npc, killer);
                        commonCombatMethod.onDeath();
                    }

                    //Rock crabs
                    if (npc.id() == 101 || npc.id() == 103) {
                        switch (npc.id()) {
                            case 101 -> npc.transmog(101);
                            case 103 -> npc.transmog(103);
                        }
                        npc.walkRadius(0);
                    }

                    // so in java .. we dont have functions so we need to hardcode the id check
                    if (WildernessBossEvent.getINSTANCE().getActiveNpc().isPresent() &&
                        npc == WildernessBossEvent.getINSTANCE().getActiveNpc().get()) {
                        WildernessBossEvent.getINSTANCE().bossDeath(npc);
                    }

                    if(npc.id() == THE_NIGHTMARE_9430) {
                        nightmareDrops(npc);
                    }

                    killer.getBossTimers().submit(npc.def().name, (int) killer.getCombat().getFightTimer().elapsed(TimeUnit.SECONDS), killer);

                    ScalarLootTable table = ScalarLootTable.forNPC(npc.id());
                    //Drop loot, but the first form of KQ, Runite golem and world bosses do not drop anything.
                    if (table != null && (npc.id() != KALPHITE_QUEEN_6500 && npc.id() != RUNITE_GOLEM && !npc.isWorldBoss() && npc.id() != THE_NIGHTMARE_9430)) {
                        if(!customDrops.contains(npc.id())) {
                            //Always drops such as bones
                            ItemDrops.dropAlwaysItems(killer, npc);

                            //Roll for an actual drop of the table
                            ItemDrops.rollTheDropTable(killer, npc);
                        } else {
                            // Custom drop tables
                            if(npc.combatInfo() != null) {
                                npc.combatInfo().scripts.droptable_.reward(npc, killer);
                            }
                        }

                        int roll = npc.def() != null && npc.def().combatlevel > 100 ? 100 : 200;
                        if (World.getWorld().rollDie(roll, 1)) {
                            GroundItem item = new GroundItem(new Item(TASK_BOTTLE_SKILLING), killer.tile(), killer);
                            GroundItemHandler.createGroundItem(item);
                        }

                        //Custom drops
                        ItemDrops.treasure(killer, npc);
                        ItemDrops.dropCoins(killer, npc);
                        ItemDrops.supplyCrateDrops(killer, npc);
                        killer.getSlayerKey().drop(npc);
                        table.rollForLarransKey(npc, killer);
                        table.rollForBrimstoneKey(npc, killer);
                        table.rollForKeyOfDrops(killer, npc);
                        KourendCatacombs.drop(killer, npc, npc.tile());
                    }

                    // Custom drop tables
                    if (npc.combatInfo() != null && npc.combatInfo().scripts != null && npc.combatInfo().scripts.droptable_ != null) {
                        npc.combatInfo().scripts.droptable_.reward(npc, killer);
                    }
                }

                // Post-death scripts

                if (npc.id() == KALPHITE_QUEEN_6500) {
                    KalphiteQueenFirstForm.death(npc);
                    return;
                } else if (npc.id() == KALPHITE_QUEEN_6501) {
                    KalphiteQueenSecondForm.death(npc);
                }

                if (npc.id() == Phases.OLM_LEFT_HAND) {
                    if (killer == null) return;
                    Party party = killer.raidsParty;
                    if (party != null) {
                        party.forPlayers(player -> player.getPacketSender().sendObjectAnimation(party.getLeftHandObject(), 7370));
                        Chain.bound(null).runFn(2, () -> {
                            ObjectManager.addObj(new GameObject(29885, new Tile(3238, 5733, killer.tile().getZ()), 10, 1));
                            ObjectManager.addObj(new GameObject(29885, new Tile(3220, 5743, killer.tile().getZ()), 10, 3));
                        });
                        party.setLeftHandDead(true);
                    }
                }

                if (npc.id() == Phases.OLM_RIGHT_HAND) {
                    if (killer == null) return;
                    Party party = killer.raidsParty;
                    if (party != null) {
                        party.forPlayers(player -> player.getPacketSender().sendObjectAnimation(party.getRightHandObject(), 7352));
                        Chain.bound(null).runFn(2, () -> {
                            party.forPlayers(player -> player.getPacketSender().sendObjectAnimation(party.getRightHandObject(), 7353));
                        });
                        party.setRightHandDead(true);
                        party.setCanAttackLeftHand(true);
                    }
                }

                if (npc.id() == VETION_REBORN) {
                    npc.putAttrib(AttributeKey.VETION_REBORN_ACTIVE, false);
                    npc.clearAttrib(AttributeKey.VETION_HELLHOUND_SPAWNED);
                    npc.transmog(VETION);
                }

                if (npc.def().name.equalsIgnoreCase("Alchemical hydra")) {
                    if (killer != null && killer.getAlchemicalHydraInstance() != null) {
                        killer.getAlchemicalHydraInstance().death(killer);//Do Alchemical hydra death
                    }
                }

                if (npc.id() == 6613) {
                    VetionMinion.death(npc); //Do Vetíon minion death
                }

                if (npc.id() == 6716 || npc.id() == 6723 || npc.id() == 7649) {
                    NechryarchDeathSpawn.death(npc); //Do death spawn death
                }

                if (npc.id() == NECHRYAEL || npc.id() == NECHRYAEL_11) {
                    new Nechryael().onDeath(npc);
                }

                Zulrah.death(killer, npc);

                if (npc.id() == CORPOREAL_BEAST) { // Corp beast
                    // Reset damage counter

                    Npc corp = npc.getAttribOr(AttributeKey.BOSS_OWNER, null);
                    if (corp != null) {
                        //Check for any minions.
                        List<Npc> minList = corp.getAttribOr(AttributeKey.MINION_LIST, null);
                        if (minList != null) {
                            minList.remove(npc);
                        }
                    }
                }

                //Forgot to say its ALL npcs, happens to bots, kraken any npc
                if (killer != null) {
                    if (npc.respawns() && !npc.isBot())
                        killer.getPacketSender().sendEffectTimer((int) Utils.ticksToSeconds(finalRespawnTimer), EffectTimer.MONSTER_RESPAWN);
                }

                deathReset(npc);
                if (npc.respawns()) {
                    npc.teleport(npc.spawnTile());
                    npc.hidden(true);

                    // Remove from area..
                    if (npc.getController() != null) {
                        npc.getController().leave(npc);
                        npc.setController(null);
                    }

                    Chain.bound(null).runFn(finalRespawnTimer, () -> {
                        GwdLogic.onRespawn(npc);
                        respawn(npc);
                    });
                } else if (npc.id() != KALPHITE_QUEEN_6500) {
                    npc.hidden(true);
                    World.getWorld().unregisterNpc(npc);
                }
            });
        } catch (Exception e) {
            logger.catching(e);
        }
    }

    /**
     * If you're resetting an Npc as if it were by death but not, for example maybe kraken tentacles which go back down to
     * the depths when the boss is killed.
     */
    public static void deathReset(Npc npc) {
        if (npc.id() != KALPHITE_QUEEN_6500) { // KQ first stage keeps damage onto stage 2!
            npc.getCombat().clearDamagers(); //Clear damagers
        }

        npc.clearAttrib(AttributeKey.TARGET);
        npc.clearAttrib(AttributeKey.LAST_ATTACKED_MAP);
        npc.putAttrib(AttributeKey.VENOM_TICKS, 0);
        npc.putAttrib(AttributeKey.POISON_TICKS, 0);
        npc.clearAttrib(VENOMED_BY);
    }

    public static void respawn(Npc npc) {

        if (npc.id() == KrakenBoss.KRAKEN_NPCID) {
            npc.transmog(KrakenBoss.KRAKEN_WHIRLPOOL);
            // Transmog kraken info after the drop table is done otherwise it'll look for the wrong table
            npc.combatInfo(World.getWorld().combatInfo(KrakenBoss.KRAKEN_WHIRLPOOL));
        }

        if (npc.id() == KrakenBoss.TENTACLE_WHIRLPOOL || npc.id() == NpcIdentifiers.ENORMOUS_TENTACLE) {
            Npc boss = npc.getAttrib(AttributeKey.BOSS_OWNER);
            if (boss != null && npc.dead()) {
                // only respawn minions if our boss is alive
                return;
            }
        }

        if (npc.id() == NpcIdentifiers.GARGOYLE) {
            Gargoyle.onDeath(npc);
        }

        if (npc.id() == NpcIdentifiers.VETION) {//Just do it again for extra safety
            npc.clearAttrib(AttributeKey.VETION_HELLHOUND_SPAWNED);
            npc.putAttrib(AttributeKey.VETION_REBORN_ACTIVE, false);
        }

        if (npc.hidden()) { // not respawned yet. we do this check incase it was force-respawned by .. group spawning (gwd)
            deathReset(npc);
            npc.hidden(false);
            if (npc.combatInfo() != null) {
                if (npc.combatInfo().stats != null || npc.combatInfo().originalStats != null)
                    npc.combatInfo().stats = npc.combatInfo().originalStats.clone(); // Replenish all stats on this NPC.
                if (npc.combatInfo().bonuses != null || npc.combatInfo().originalBonuses != null)
                    npc.combatInfo().bonuses = npc.combatInfo().originalBonuses.clone(); // Replenish all stats on this NPC.
            }

            npc.hp(npc.maxHp(), 0); // Heal up to full hp
            npc.animate(-1); // Reset death animation
            npc.unlock();
            if (npc instanceof Drake) {
                npc.transmog(DRAKE_8612);
            }

            if (npc instanceof Wyrm) {
                npc.transmog(Wyrm.IDLE);
            }
        }
    }

    public static Optional<Pet> checkForPet(Player killer, ScalarLootTable table) {
        Optional<Pet> pet = table.rollForPet(killer);
        if (pet.isPresent()) {
            // Do we already own this pet?
            boolean caught = killer.isPetUnlocked(pet.get().varbit);

            // RS tries to add it as follower first. That only works if you don't have one.
            Npc currentPet = killer.pet();
            if (caught && pet.get().varbit != -1) {//Only applies to untradeable pets
                killer.message("You have a funny feeling like you would have been followed...");
            } else if (currentPet == null) {
                killer.message("You have a funny feeling like you're being followed.");
                PetAI.spawnPet(killer, pet.get(), false);
            } else {
                killer.inventory().addOrBank(new Item(pet.get().item));
                killer.message("You feel something weird sneaking into your backpack.");
            }

            if (!killer.isPetUnlocked(pet.get().varbit)) {
                if (pet.get().varbit != -1) { // -1 means tradeable pet
                    if (!killer.isPetUnlocked(pet.get().varbit)) {
                        killer.addUnlockedPet(pet.get().varbit);
                    }
                }
            }

            //Special collection log unlocks
            switch (pet.get()) {
                case CENTAUR_MALE -> BOSSES.log(killer, COS_RAIDS_KEY, new Item(Pet.CENTAUR_MALE.item));
                case CENTAUR_FEMALE -> BOSSES.log(killer, COS_RAIDS_KEY, new Item(Pet.CENTAUR_FEMALE.item));
                case DEMENTOR -> BOSSES.log(killer, COS_RAIDS_KEY, new Item(Pet.DEMENTOR.item));
                case FLUFFY_JR -> BOSSES.log(killer, COS_RAIDS_KEY, new Item(Pet.FLUFFY_JR.item));
                case FENRIR_GREYBACK_JR -> BOSSES.log(killer, COS_RAIDS_KEY, new Item(Pet.FENRIR_GREYBACK_JR.item));
            }

            World.getWorld().sendWorldMessage("<img=1081> <col=844e0d>" + killer.getUsername() + " has received a: " + new Item(pet.get().item).name() + ".");
            Utils.sendDiscordInfoLog("Player " + killer.getUsername() + " has received a: " + new Item(pet.get().item).name() + ".", "yell_item_drop");
        }
        return pet;
    }

    public static void notification(Player killer, Item drop) {
        Item loot = drop.unnote();
        //TODO: implement these
        // Enabled? Untradable buttons are only enabled if the threshold is enabled. Can't have one without the other.
        boolean notifications_enabled = killer.getAttribOr(AttributeKey.ENABLE_LOOT_NOTIFICATIONS_BUTTONS, false);
        boolean untrade_notifications = killer.getAttribOr(AttributeKey.UNTRADABLE_LOOT_NOTIFICATIONS, false);
        int lootDropThresholdValue = killer.getAttribOr(AttributeKey.LOOT_DROP_THRESHOLD_VALUE, 0);
        if (notifications_enabled) {
            if (loot.rawtradable()) {
                if (untrade_notifications) {
                    killer.message("Untradable drop: " + loot.getAmount() + " x <col=cc0000>" + loot.name() + "</col>.");
                }
            } else if (loot.getValue() >= lootDropThresholdValue) {
                killer.message("Valuable drop: " + loot.getAmount() + " x <col=cc0000>" + loot.name() + "</col> (" + loot.getValue() * loot.getAmount() + "coins).");
            }
        }
    }

    private static void nightmareDrops(Mob mob) {
        mob.getCombat().getDamageMap().forEach((key, hits) -> {
            Player player = (Player) key;
            player.message(Color.RED.wrap("You've dealt " + hits.getDamage() + " damage to The Nightmare!"));
            // Only people nearby are rewarded. This is to avoid people 'poking' the boss to do some damage
            // without really risking being there.
            if (mob.tile().isWithinDistance(player.tile(),10) && hits.getDamage() >= 500) {
                if(mob instanceof Npc) {
                    player.message("You received a drop roll from the table for dealing at least 500 damage!");
                    Npc npc = mob.getAsNpc();

                    //Always log kill timers
                    player.getBossTimers().submit(npc.def().name, (int) player.getCombat().getFightTimer().elapsed(TimeUnit.SECONDS), player);

                    //Always increase kill counts
                    player.getBossKillLog().addKill(npc);

                    //Random drop from the table
                    ScalarLootTable table = ScalarLootTable.forNPC(npc.id());
                    if (table != null) {
                        Item reward = table.randomItem(World.getWorld().random());
                        if (reward != null) {

                            // bosses, find npc ID, find item ID
                            BOSSES.log(player, npc.id(), reward);

                            //Niffler doesn't loot world The Nightmare loot
                            GroundItemHandler.createGroundItem(new GroundItem(reward, npc.tile(), player));
                            ServerAnnouncements.tryBroadcastDrop(player, npc, reward);

                            Utils.sendDiscordInfoLog("Player " + player.getUsername() + " got drop item " + reward, "npcdrops");
                        }
                    }
                }
            }
        });
    }
}
