package com.valinor.game.world.entity.mob.player.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.valinor.GameServer;
import com.valinor.db.transactions.UpdatePasswordDatabaseTransaction;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.areas.wilderness.content.bounty_hunter.bounty_tasks.BountyHunterTask;
import com.valinor.game.content.bank_pin.BankPinModification;
import com.valinor.game.content.collection_logs.Collection;
import com.valinor.game.content.presets.Presetable;
import com.valinor.game.content.seasonal_events.rewards.EventRewards;
import com.valinor.game.content.tasks.BottleTasks;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.prayer.default_prayer.DefaultPrayerData;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.weapon.FightType;
import com.valinor.game.world.entity.mob.player.*;
import com.valinor.game.world.entity.mob.player.rights.MemberRights;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.util.timers.TimerKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mindrot.BCrypt;

import java.io.*;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.*;

import static com.valinor.game.world.entity.AttributeKey.*;

/**
 * Handles saving a player's container and details into a json file.
 * <br><br>
 * Type safety enforced when using OSS's {@link AttributeKey} by Shadowrs/Jak on 06/06/2020
 *
 * @author Patrick van Elderen | 28 feb. 2019 : 12:16:21
 * @see <a href="https://www.rune-server.ee/members/_Patrick_/">Rune-Server profile</a>
 */
public class PlayerSave {

    /**
     * SUPER IMPORTANT INFO: Player class needs to have default values set for any objects (or variables) that could be null that it tries to access on login to prevent NPEs thrown when loading a Player from PlayerSave.
     * In other words, when adding any new variables to PlayerSave that might be accessed upon login, make sure to set default values in Player class (for existing players that don't have the new features yet).
     * ALSO: Make super sure to be careful that when adding any new save objects (or variables) here, when loading the details, setting them here may mean they are null so they will set the Player variables to null which will cause NPEs.
     * In other words, make sure to properly null check in the Player class and in other places throughout the server code.
     */

    private static final Logger logger = LogManager.getLogger(PlayerSave.class);

    static final Map<Type, InstanceCreator<?>> instanceCreators = Collections.emptyMap();

    public static final Gson SERIALIZE = new GsonBuilder().setDateFormat("MMM d, yyyy, HH:mm:ss a").setPrettyPrinting().registerTypeAdapterFactory(new MapTypeAdapterFactoryNulls(new ConstructorConstructor(instanceCreators), false)).disableHtmlEscaping().create();

    /**
     * Loads all the details of the {@code player}.
     *
     * @param player The player to load details for
     */
    public static boolean load(Player player) throws Exception {
        try {
            player.getFarming().load();
        } catch (Exception e) {
            logger.error("Error while loading farming {}", player.getUsername(), e);
            e.printStackTrace();
        }
        return SaveDetails.loadDetails(player);
    }

    public static boolean loadOffline(Player player, String enteredPassword) throws Exception {
        if (!SaveDetails.loadDetails(player)) {
            return false;
        }
        player.setPassword(enteredPassword);
        return true;
    }

    public static boolean loadOfflineWithoutPassword(Player player) throws Exception {
        return SaveDetails.loadDetails(player);
    }

    /**
     * Saves all the details of the {@code player}.
     *
     * @param player The player to save details for
     */
    public static boolean save(Player player) {
        try {
            new SaveDetails(player).parseDetails();
            player.getFarming().save();
            return true;
        } catch (final Exception e) {
            logger.catching(e);
        }
        return false;
    }

    /**
     * Handles saving and loading player's details.
     */
    public static final class SaveDetails {

        public static boolean loadDetails(Player player) throws Exception {
            final File file = new File("./data/saves/characters/" + player.getUsername() + ".json");
            if (!file.exists()) {
                return false;
            }
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                final SaveDetails details = PlayerSave.SERIALIZE.fromJson(reader, SaveDetails.class);
                player.setUsername(details.username);
                player.setPassword(details.password);
                player.setNewPassword("");
                if (details.title != null)
                    player.putAttrib(TITLE, details.title);
                if (details.titleColor != null)
                    player.putAttrib(TITLE_COLOR, details.titleColor);
                if (details.tile != null)
                    player.setTile(details.tile);
                player.putAttrib(GAME_TIME, details.gameTime);
                player.putAttrib(RUN_ENERGY, details.runEnergy);
                player.putAttrib(IS_RUNNING, details.running);
                if (details.playerRights != null)
                    player.setPlayerRights(PlayerRights.valueOf(details.playerRights));
                if (details.memberRights != null)
                    player.setMemberRights(MemberRights.valueOf(details.memberRights));
                if (details.expMode != null)
                    player.expmode(details.expMode);
                player.gameMode(Objects.requireNonNullElse(details.gameMode, GameMode.NONE));
                player.putAttrib(GROUP_NAME_SET, details.groupNameSet);
                player.putAttrib(HARDCORE_GROUP_FALLEN, details.hardcoreGroupFallen);
                if (details.lastIP != null) {
                    player.setHostAddress(details.lastIP);
                }
                player.getHostAddressMap().put(player.getHostAddress(), 1);
                if (details.mac != null && !details.mac.equals("invalid"))
                    player.putAttrib(MAC_ADDRESS, details.mac);
                player.putAttrib(ACCOUNT_PIN, details.accountPin);
                player.putAttrib(ASK_FOR_ACCOUNT_PIN, details.askAccountPin);
                player.putAttrib(ACCOUNT_PIN_ATTEMPTS_LEFT, details.accountPinAttemptsLeft);
                player.putAttrib(ACCOUNT_PIN_FREEZE_TICKS, details.accountPinFrozenTicks);
                if (details.creationDate != null)
                    player.setCreationDate(details.creationDate);
                if (details.creationIp != null)
                    player.setCreationIp(details.creationIp);
                if (details.lastLogin != null)
                    player.setLastLogin(details.lastLogin);
                player.putAttrib(MUTED, details.muted);
                player.putAttrib(NEW_ACCOUNT, details.newPlayer);
                player.putAttrib(IS_BETA_TESTER, details.isBetaTester);
                player.putAttrib(VETERAN, details.veteran);
                player.putAttrib(GAMBLER, details.gambler);
                player.putAttrib(STARTER_BOX_CLAIMED, details.starterboxClaimed);
                player.putAttrib(CLAN_BOX_OPENED, details.clanBoxOpened);
                player.putAttrib(PROMO_CODE_CLAIMED, details.promoCodeClaimed);
                player.putAttrib(RECEIVED_MONTHLY_SPONSOR_REWARDS, details.receivedMonthlySponsorRewards);
                player.looks().female(details.female);
                if (details.looks != null)
                    player.looks().looks(details.looks);
                if (details.colors != null)
                    player.looks().colors(details.colors);
                if (details.spellBook != null)
                    player.setSpellbook(MagicSpellbook.valueOf(details.spellBook));
                if (details.fightType != null)
                    player.getCombat().setFightType(FightType.valueOf(details.fightType));
                player.getCombat().getFightType().setParentId(details.fightTypeVarp);
                player.getCombat().getFightType().setChildId(details.fightTypeVarpState);
                player.getCombat().setAutoRetaliate(details.autoRetaliate);
                if (details.previousSpellbook != null) {
                    player.setPreviousSpellbook(details.previousSpellbook);
                }
                player.putAttrib(VENOM_TICKS, details.venomTicks);
                player.putAttrib(POISON_TICKS, details.poisonTicks);
                player.setSpecialAttackPercentage(details.specPercentage);
                player.putAttrib(RING_OF_RECOIL_CHARGES, details.recoilCharges);
                player.getTargetSearchTimer().start(details.targetSearchTimer);
                player.getSpecialAttackRestore().start(details.specialAttackRestoreTimer);
                player.putAttrib(SKULL_CYCLES, Math.max(details.skullTimer, 0));
                player.setSkullType((details.skullTimer < 0 || details.skullType == null) ? SkullType.NO_SKULL : details.skullType);
                if (details.quickPrayers != null)
                    player.getQuickPrayers().setPrayers(details.quickPrayers);
                if (details.presets != null) {
                    // put into individual slots, dont replace an array[20] with a game save array[10]
                    for (int i = 0; i < details.presets.length; i++) {
                        player.getPresets()[i] = details.presets[i];
                    }
                }
                if (details.lastPreset != null) {
                    player.setLastPreset(details.lastPreset);
                }
                player.getTimers().register(TimerKey.SPECIAL_TELEBLOCK, details.specialTeleblockTimer);
                player.putAttrib(TOTAL_PAYMENT_AMOUNT, details.totalAmountPaid);
                player.putAttrib(PROMO_PAYMENT_AMOUNT, details.promoPaymentAmount);
                player.putAttrib(SAPPHIRE_MEMBER_UNLOCKED, details.sapphireMemberUnlocked);
                player.putAttrib(EMERALD_MEMBER_UNLOCKED, details.emeraldMemberUnlocked);
                player.putAttrib(RUBY_MEMBER_UNLOCKED, details.rubyMemberUnlocked);
                player.putAttrib(DIAMOND_MEMBER_UNLOCKED, details.diamondMemberUnlocked);
                player.putAttrib(DRAGONSTONE_MEMBER_UNLOCKED, details.dragonstoneMemberUnlocked);
                player.putAttrib(ONYX_MEMBER_UNLOCKED, details.onyxMemberUnlocked);
                player.putAttrib(ZENYTE_MEMBER_UNLOCKED, details.zenyteMemberUnlocked);
                player.skills().setAllLevels(details.dynamicLevels);
                player.skills().setAllXps(details.skillXP);
                player.putAttrib(ACTIVE_PET_ITEM_ID, details.activePetItemId);
                if (details.unlockedPets != null) {
                    player.setUnlockedPets(details.unlockedPets);
                }
                if (details.insuredPets != null) {
                    player.setInsuredPets(details.insuredPets);
                }
                player.putAttrib(SLAYER_TASK_ID, details.slayerTaskId);
                player.putAttrib(SLAYER_TASK_AMT, details.slayerTaskAmount);
                player.putAttrib(SLAYER_MASTER, details.slayerMasterId);
                player.putAttrib(SLAYER_TASK_SPREE, details.slayerTaskStreak);
                player.putAttrib(SLAYER_TASK_SPREE_RECORD, details.slayerTaskStreakRecord);
                player.putAttrib(COMPLETED_SLAYER_TASKS, details.completedSlayerTasks);
                player.putAttrib(WILDERNESS_SLAYER_DESCRIBED, details.wildernessSlayerDescribed);
                if (details.slayerPartner != null) {
                    player.putAttrib(SLAYER_PARTNER, details.slayerPartner);
                }

                if (details.blockedSlayerTasks != null) {
                    player.getSlayerRewards().setBlocked(details.blockedSlayerTasks);
                }

                if (details.slayerUnlocks != null) {
                    player.getSlayerRewards().setUnlocks(details.slayerUnlocks);
                }

                if (details.slayerExtensionsList != null) {
                    player.getSlayerRewards().setExtendable(details.slayerExtensionsList);
                }

                if (details.inventory != null) {
                    for (int i = 0; i < details.inventory.length; i++) {
                        player.inventory().set(i, details.inventory[i], false);
                    }
                }
                if (details.equipment != null) {
                    for (int i = 0; i < details.equipment.length; i++) {
                        player.getEquipment().set(i, details.equipment[i], false);
                    }
                }
                if (details.bank != null) {
                    for (int i = 0; i < details.bank.length; i++) {
                        player.getBank().set(i, details.bank[i], false);
                    }
                }
                if (details.tabAmounts != null) {
                    System.arraycopy(details.tabAmounts, 0, player.getBank().tabAmounts, 0, details.tabAmounts.length);
                }
                player.getBank().placeHolder = details.placeholdersActive;
                player.getBank().placeHolderAmount = details.placeHolderAmount;
                player.getBankPin().setHashedPin(details.hashedBankPin);
                player.getBankPin().setPinLength(details.bankPinLength);
                player.getBankPin().setRecoveryDays(details.recoveryDelay);
                player.getBankPin().setPendingMod(details.pendingBankPinMod);
                if (details.lootingBag != null) {
                    for (int index = 0; index < details.lootingBag.length; index++) {
                        player.getLootingBag().set(index, details.lootingBag[index], false);
                    }
                }
                player.getLootingBag().setAskHowManyToStore(details.askHowManyToStore);
                player.getLootingBag().setStoreAsMany(details.storeAsMany);
                if (details.runePouch != null) {
                    for (int index = 0; index < details.runePouch.length; index++) {
                        player.getRunePouch().set(index, details.runePouch[index], false);
                    }
                }
                player.putAttrib(CART_ITEMS_TOTAL_VALUE, details.totalCartValue);
                if (details.cartItems != null) {
                    player.putAttrib(CART_ITEMS, details.cartItems);
                }
                if (details.nifflerItems != null) {
                    player.putAttrib(NIFFLER_ITEMS_STORED, details.nifflerItems);
                }
                if (details.friends == null)
                    details.friends = new ArrayList<>(200);
                for (String friend : details.friends) {
                    player.getRelations().getFriendList().add(friend);
                }
                if (details.ignores == null)
                    details.ignores = new ArrayList<>(100);
                for (String ignore : details.ignores) {
                    player.getRelations().getIgnoreList().add(ignore);
                }
                if (details.clan != null)
                    player.setClanChat(details.clan);
                player.putAttrib(ACCEPT_AID, details.acceptAid);
                if (details.yellColour != null) {
                    if (details.yellColour.equals("0")) {
                        player.putAttrib(YELL_COLOUR, "006601");
                    } else {
                        player.putAttrib(YELL_COLOUR, details.yellColour);
                    }
                }
                player.putAttrib(YELL_TAG, details.yellTag);
                if (details.yellTagColour != null) {
                    if (details.yellTagColour.equals("0")) {
                        player.putAttrib(YELL_TAG_COLOUR, "006601");
                    } else {
                        player.putAttrib(YELL_TAG_COLOUR, details.yellTagColour);
                    }
                }
                player.putAttrib(NIGHTMARE_STAFF_QUESTION, details.dontAskAgainNightmareStaff);
                player.putAttrib(CURRENCY_COLLECTION, details.currencyCollection);
                player.putAttrib(GIVE_EMPTY_POTION_VIALS, details.emptyPotionVials);
                player.putAttrib(AGS_GFX_GOLD, details.gold_ags_spec);
                player.putAttrib(BGS_GFX_GOLD, details.gold_bgs_spec);
                player.putAttrib(SGS_GFX_GOLD, details.gold_sgs_spec);
                player.putAttrib(ZGS_GFX_GOLD, details.gold_zgs_spec);
                player.putAttrib(XP_LOCKED, details.xpLocked);
                player.putAttrib(DEBUG_MESSAGES, details.enableDebugMessages);
                player.getPresetManager().setSaveLevels(details.savePresetLevels);
                player.getPresetManager().setOpenOnDeath(details.openPresetsOnDeath);
                if (details.savedDuelConfig != null) {
                    player.setSavedDuelConfig(details.savedDuelConfig);
                }
                player.putAttrib(REPAIR_BROKEN_ITEMS_ON_DEATH, details.autoRepairBrokenItems);
                player.putAttrib(VOTE_POINTS, details.votePoints);
                player.putAttrib(AttributeKey.PEST_CONTROL_POINTS, details.pestControlPoints);
                player.putAttrib(SLAYER_REWARD_POINTS, details.slayerRewardPoints);
                player.putAttrib(BOSS_POINTS, details.bossPoints);
                player.putAttrib(PK_POINTS, details.pkPoints);
                player.putAttrib(ACHIEVEMENT_POINTS, details.achievementPoints);
                player.putAttrib(BOUNTY_HUNTER_POINTS, details.bountyHunterPoints);
                player.putAttrib(HOTSPOT_POINTS, details.hotspotPoints);
                player.putAttrib(BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED, details.teleportToTargetUnlocked);
                player.putAttrib(PRESERVE, details.preserve);
                player.putAttrib(RIGOUR, details.rigour);
                player.putAttrib(AUGURY, details.augury);
                player.putAttrib(PLAYER_KILLS, details.kills);
                player.putAttrib(PLAYER_DEATHS, details.deaths);
                if (details.recentKills != null) {
                    for (String kills : details.recentKills) {
                        player.getRecentKills().add(kills);
                    }
                }
                player.putAttrib(FIRST_KILL_OF_THE_DAY, details.firstKillOfTheDay);
                player.putAttrib(AttributeKey.BOUNTY_TASKS_SKIPPED, details.bountyTasksSkipped);
                player.putAttrib(AttributeKey.BOUNTY_HUNTER_TASK_NOTIFICATIONS, details.bountyTaskNotifications);
                player.putAttrib(AttributeKey.BOUNTY_TASKS_COMPLETED, details.bountyTasksCompleted);
                player.putAttrib(BOUNTY_HUNTER_TASK_COMPLETION_AMOUNT, details.bountyHunterTaskCompletionAmount);
                player.putAttrib(BOUNTY_HUNTER_TASK, details.bountyTask);
                player.putAttrib(KILLSTREAK, details.killstreak);
                player.putAttrib(KILLSTREAK_RECORD, details.killstreakRecord);
                player.putAttrib(KING_BLACK_DRAGONS_KILLED, details.kingBlackDragonsKilled);
                player.putAttrib(VETIONS_KILLED, details.vetionsKilled);
                player.putAttrib(CRAZY_ARCHAEOLOGISTS_KILLED, details.crazyArchaeologistsKilled);
                player.putAttrib(ZULRAHS_KILLED, details.zulrahsKilled);
                player.putAttrib(ALCHY_KILLED, details.alchysKilled);
                player.putAttrib(KRAKENS_KILLED, details.krakensKilled);
                player.putAttrib(REVENANTS_KILLED, details.revenantsKilled);
                player.putAttrib(ANCIENT_REVENANTS_KILLED, details.ancientRevenantsKilled);
                player.putAttrib(ANCIENT_KING_BLACK_DRAGONS_KILLED, details.ancientKingBlackDragonsKilled);
                player.putAttrib(CORRUPTED_HUNLEFFS_KILLED, details.corruptedHunleffsKilled);
                player.putAttrib(ANCIENT_CHAOS_ELEMENTALS_KILLED, details.ancientChaosElementalsKilled);
                player.putAttrib(ANCIENT_BARRELCHESTS_KILLED, details.ancientBarrelchestsKilled);
                player.putAttrib(KERBEROS_KILLED, details.kerberosKilled);
                player.putAttrib(ARACHNE_KILLED, details.arachneKilled);
                player.putAttrib(SKORPIOS_KILLED, details.skorpiosKilled);
                player.putAttrib(ARTIO_KILLED, details.artioKilled);
                player.putAttrib(JADS_KILLED, details.jadsKilled);
                player.putAttrib(CHAOS_ELEMENTALS_KILLED, details.chaosElementalsKilled);
                player.putAttrib(DEMONIC_GORILLAS_KILLED, details.demonicGorillasKilled);
                player.putAttrib(BARRELCHESTS_KILLED, details.barrelchestsKilled);
                player.putAttrib(CORPOREAL_BEASTS_KILLED, details.corporealBeastsKilled);
                player.putAttrib(CERBERUS_KILLED, details.abyssalSiresKilled);
                player.putAttrib(VORKATHS_KILLED, details.vorkathsKilled);
                player.putAttrib(LIZARDMAN_SHAMANS_KILLED, details.lizardmanShamansKilled);
                player.putAttrib(BARROWS_CHESTS_OPENED, details.barrowsChestsOpened);
                player.putAttrib(CORRUPTED_NECHRYARCHS_KILLED, details.corruptedNechryarchsKilled);
                player.putAttrib(FLUFFYS_KILLED, details.fluffysKilled);
                player.putAttrib(DEMENTORS_KILLED, details.dementorsKilled);
                player.putAttrib(HUNGARIAN_HORNTAILS_KILLED, details.hungarianHorntailsKilled);
                player.putAttrib(FENRIR_GREYBACKS_KILLED, details.fenrirGreybacksKilled);
                player.putAttrib(SCORPIAS_KILLED, details.scorpiasKilled);
                player.putAttrib(CALLISTOS_KILLED, details.callistosKilled);
                player.putAttrib(KC_GIANTMOLE, details.molesKilled);
                player.putAttrib(THE_NIGHTMARE_KC, details.nightmaresKilled);
                player.putAttrib(KREE_ARRA_KILLED, details.kreeArrasKilled);
                player.putAttrib(COMMANDER_ZILYANA_KILLED, details.zilyanasKilled);
                player.putAttrib(GENERAL_GRAARDOR_KILLED, details.graardorsKilled);
                player.putAttrib(KRIL_TSUTSAROTHS_KILLED, details.krilTsutsarothsKilled);
                player.putAttrib(NEX_KC, details.nexKilled);
                player.putAttrib(THEATRE_OF_BLOOD_RUNS_COMPLETED, details.theatreOfBloodRuns);
                player.putAttrib(CHAMBER_OF_XERIC_RUNS_COMPLETED, details.chamberOfXericRuns);
                player.putAttrib(CRYSTALLINE_HUNLEFF_KC, details.crystallineHunleffKilled);
                player.putAttrib(FRAGMENT_OF_SEREN_KC, details.fragmentOfSerenKilled);
                player.putAttrib(BLOOD_FURY_HESPORI_KC, details.bloodFuryHesporiKilled);
                player.putAttrib(INFERNAL_SPIDER_KC, details.infernalSpiderKilled);
                player.putAttrib(ENRAGED_GORILLA_KILLS, details.enragedGorillasKilled);
                player.putAttrib(CHAOTIC_NIGHTMARE_KILLS, details.chaoticNightmaresKilled);
                player.putAttrib(KC_REX, details.rexKilled);
                player.putAttrib(KC_PRIME, details.primeKilled);
                player.putAttrib(KC_SUPREME, details.supremeKilled);
                player.putAttrib(KC_KQ, details.kalphiteQueensKilled);
                player.putAttrib(LAVA_DRAGONS_KILLED, details.lavaDragonsKilled);
                player.putAttrib(SKOTIZOS_KILLED, details.skotizosKilled);
                player.putAttrib(ZOMBIES_CHAMPIONS_KILLED, details.zombieChampionsKilled);
                player.putAttrib(BRUTAL_LAVA_DRAGONS_KILLED, details.brutalLavaDragonsKilled);
                player.putAttrib(TEKTONS_KILLED, details.tektonsKilled);
                player.putAttrib(CHAOS_FANATICS_KILLED, details.chaosFanaticsKilled);
                player.putAttrib(THERMONUCLEAR_SMOKE_DEVILS_KILLED, details.thermonuclearSmokeDevilKilled);
                player.putAttrib(VENENATIS_KILLED, details.venenatisKilled);
                player.putAttrib(KC_ARAGOG, details.aragogKC);
                player.putAttrib(CHAMBER_OF_SECRET_RUNS_COMPLETED, details.chamberOfSecretRuns);
                player.putAttrib(KC_SMOKEDEVIL, details.smokeDevilKills);
                player.putAttrib(SUPERIOR, details.superiorCreatureKills);
                player.putAttrib(KC_CRAWL_HAND, details.crawlingHandKills);
                player.putAttrib(KC_CAVE_BUG, details.caveBugKills);
                player.putAttrib(KC_CAVE_CRAWLER, details.caveCrawlerKills);
                player.putAttrib(KC_BANSHEE, details.bansheeKills);
                player.putAttrib(KC_CAVE_SLIME, details.caveSlimeKills);
                player.putAttrib(KC_ROCKSLUG, details.rockslugKills);
                player.putAttrib(KC_DESERT_LIZARD, details.desertLizardKills);
                player.putAttrib(KC_COCKATRICE, details.cockatriceKills);
                player.putAttrib(KC_PYREFRIEND, details.pyrefiendKills);
                player.putAttrib(KC_MOGRE, details.mogreKills);
                player.putAttrib(KC_HARPIE_BUG, details.harpieBugSwarmKills);
                player.putAttrib(KC_WALL_BEAST, details.wallBeastKills);
                player.putAttrib(KC_KILLERWATT, details.killerwattKills);
                player.putAttrib(KC_MOLANISK, details.molaniskKills);
                player.putAttrib(KC_BASILISK, details.basiliskKills);
                player.putAttrib(KC_SEASNAKE, details.seaSnakeKills);
                player.putAttrib(KC_TERRORDOG, details.terrorDogKills);
                player.putAttrib(KC_FEVER_SPIDER, details.feverSpiderKills);
                player.putAttrib(KC_INFERNAL_MAGE, details.infernalMageKills);
                player.putAttrib(KC_BRINERAT, details.brineRatKills);
                player.putAttrib(KC_BLOODVELD, details.bloodveldKills);
                player.putAttrib(KC_JELLY, details.jellyKills);
                player.putAttrib(KC_TUROTH, details.turothKills);
                player.putAttrib(KC_ZYGOMITE, details.zygomiteKills);
                player.putAttrib(KC_CAVEHORROR, details.caveHorrorKills);
                player.putAttrib(KC_ABERRANT_SPECTRE, details.aberrantSpectreKills);
                player.putAttrib(KC_SPIRITUAL_WARRIOR, details.spiritualWarriorKills);
                player.putAttrib(KC_KURASK, details.kuraskKills);
                player.putAttrib(KC_SKELETAL_WYVERN, details.skeletalWyvernKills);
                player.putAttrib(KC_GARGOYLE, details.gargoyleKills);
                player.putAttrib(KC_NECHRYAEL, details.nechryaelKills);
                player.putAttrib(KC_SPIRITUAL_MAGE, details.spiritualMageKills);
                player.putAttrib(KC_ABYSSALDEMON, details.abyssalDemonKills);
                player.putAttrib(KC_CAVEKRAKEN, details.caveKrakenKills);
                player.putAttrib(KC_DARKBEAST, details.darkBeastKills);
                player.putAttrib(BRUTAL_BLACK_DRAGON, details.brutalBlackDragonKills);
                player.putAttrib(FOSSIL_WYVERN, details.fossilIslandWyvernKills);
                player.putAttrib(WYRM, details.wyrmKills);
                player.putAttrib(DRAKE, details.drakeKills);
                player.putAttrib(HYDRA, details.hydraKills);
                player.putAttrib(BASILISK_KNIGHT, details.basiliskKnightKills);
                if (details.eventRewards != null) {
                    player.getEventRewards().setEventRewardsUnlocked(details.eventRewards);
                }
                if (details.favoriteTeleports != null) {
                    player.setFavoriteTeleports(details.favoriteTeleports);
                }
                if (details.bossTimers != null) {
                    player.getBossTimers().setTimes(details.bossTimers);
                }
                if (details.collectionLog != null) {
                    player.getCollectionLog().collectionLog = details.collectionLog;
                }
                if (details.achievements != null) {
                    player.achievements().putAll(details.achievements);
                }
                player.putAttrib(ACHIEVEMENTS_COMPLETED, details.achievementsCompleted);
                if (details.task != null) {
                    player.putAttrib(BOTTLE_TASK, details.task);
                }
                player.putAttrib(BOTTLE_TASK_AMOUNT, details.taskAmount);
                player.putAttrib(TASK_COMPLETE_AMOUNT, details.taskCompletionAmount);
                player.putAttrib(TASKS_COMPLETED, details.totalTasksCompleted);
                player.putAttrib(CAN_CLAIM_TASK_REWARD, details.canClaimTaskReward);
                player.putAttrib(TREASURE_CHESTS_OPENED, details.treasuresOpened);
                player.putAttrib(REFERRAL_MILESTONE_10HOURS, details.referalMilestone10hoursPassed);
                player.putAttrib(REFERRAL_MILESTONE_1_DAY, details.referalMilestone1dayPassed);
                player.putAttrib(REFERRER_USERNAME, details.referrerUsername);
                player.putAttrib(REFERRALS_COUNT, details.referralsCount);
                player.putAttrib(DATABASE_PLAYER_ID, details.databaseId);
                player.putAttrib(REFERRAL_MILESTONE_THREE_REFERRALS, details.referalMilestone3refs);
                player.putAttrib(STAMINA_POTION_TICKS, details.staminaTicks);
                player.putAttrib(OVERLOAD_POTION, details.overloadTicks);
                player.putAttrib(ANTIFIRE_POTION, details.antifireTicks);
                player.putAttrib(SUPER_ANTIFIRE_POTION, details.superAntiFire);
                player.putAttrib(LARRANS_KEYS_OPENED, details.larranKeysUsed);
                player.putAttrib(BRIMSTONE_KEYS_OPENED, details.brimstoneKeysOpened);
                player.putAttrib(COLLECTION_LOG_KEYS_OPENED, details.collectionLogKeysOpened);
                player.putAttrib(WILDY_KEYS_OPENED, details.wildernessKeysOpened);
                player.putAttrib(SLAYER_KEYS_OPENED, details.slayerKeysOpened);
                player.putAttrib(SLAYER_KEYS_RECEIVED, details.slayerKeysReceived);
                player.putAttrib(DOUBLE_EXP_TICKS, details.doubleExpTicks);
                player.putAttrib(DOUBLE_DROP_LAMP_TICKS, details.dropRateLampTicks);
                player.putAttrib(ETHEREUM_ABSORPTION, details.ethereumAbsorption);
                player.putAttrib(JAILED, details.jailed);
                player.putAttrib(JAIL_ORES_TO_ESCAPE, details.jailOresToEscape);
                player.putAttrib(JAIL_ORES_MINED, details.jailOresMined);
                player.putAttrib(LOC_BEFORE_JAIL, details.locBeforeJail);
                player.putAttrib(TOURNAMENT_WINS, details.tournamentWins);
                player.putAttrib(TOURNAMENT_POINTS, details.tournamentPoints);
                player.putAttrib(LOST_CANNON, details.lostCannon);
                player.putAttrib(WILDY_COURSE_STATE, details.wildernessCourseState);
                player.putAttrib(ROCK_CRABS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyRockCrabsAmount);
                player.putAttrib(ROCK_CRABS_DAILY_TASK_COMPLETED, details.dailyRockCrabsCompleted);
                player.putAttrib(ROCK_CRABS_DAILY_TASK_REWARD_CLAIMED, details.dailyRockCrabsRewardClaimed);
                player.putAttrib(THIEVING_DAILY_TASK_COMPLETION_AMOUNT, details.dailyThievingAmount);
                player.putAttrib(THIEVING_DAILY_TASK_COMPLETED, details.dailyThievingCompleted);
                player.putAttrib(THIEVING_DAILY_TASK_REWARD_CLAIMED, details.dailyThievingRewardClaimed);
                player.putAttrib(VOTING_DAILY_TASK_COMPLETION_AMOUNT, details.dailyVotingAmount);
                player.putAttrib(VOTING_DAILY_TASK_COMPLETED, details.dailyVotingCompleted);
                player.putAttrib(VOTING_DAILY_TASK_REWARD_CLAIMED, details.dailyVotingRewardClaimed);
                player.putAttrib(BARROWS_TASK_COMPLETION_AMOUNT, details.dailyBarrowsAmount);
                player.putAttrib(BARROWS_DAILY_TASK_COMPLETED, details.dailyBarrowsCompleted);
                player.putAttrib(BARROWS_DAILY_TASK_REWARD_CLAIMED, details.dailyBarrowsRewardClaimed);
                player.putAttrib(SLAYER_DAILY_TASK_COMPLETION_AMOUNT, details.dailySlayerAmount);
                player.putAttrib(SLAYER_DAILY_TASK_COMPLETED, details.dailySlayerCompleted);
                player.putAttrib(SLAYER_DAILY_TASK_REWARD_CLAIMED, details.dailySlayerRewardClaimed);
                player.putAttrib(GREEN_DRAGONS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyGreenDragonsAmount);
                player.putAttrib(GREEN_DRAGONS_DAILY_TASK_COMPLETED, details.dailyGreenDragonsCompleted);
                player.putAttrib(GREEN_DRAGONS_DAILY_TASK_REWARD_CLAIMED, details.dailyGreenDragonsRewardClaimed);
                player.putAttrib(RAIDS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyRaidsAmount);
                player.putAttrib(RAIDS_DAILY_TASK_COMPLETED, details.dailyRaidsCompleted);
                player.putAttrib(RAIDS_DAILY_TASK_REWARD_CLAIMED, details.dailyRaidsRewardClaimed);
                player.putAttrib(ZULRAH_DAILY_TASK_COMPLETION_AMOUNT, details.dailyZulrahAmount);
                player.putAttrib(ZULRAH_DAILY_TASK_COMPLETED, details.dailyZulrahCompleted);
                player.putAttrib(ZULRAH_DAILY_TASK_REWARD_CLAIMED, details.dailyZulrahRewardClaimed);
                player.putAttrib(EXPERIMENTS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyExperimentsAmount);
                player.putAttrib(EXPERIMENTS_DAILY_TASK_COMPLETED, details.dailyExperimentsCompleted);
                player.putAttrib(EXPERIMENTS_DAILY_TASK_REWARD_CLAIMED, details.dailyExperimentsRewardClaimed);
                player.putAttrib(SKILLING_DAILY_TASK_COMPLETION_AMOUNT, details.dailySkillingAmount);
                player.putAttrib(SKILLING_DAILY_TASK_COMPLETED, details.dailySkillingCompleted);
                player.putAttrib(SKILLING_DAILY_TASK_REWARD_CLAIMED, details.dailySkillingRewardClaimed);
                player.putAttrib(PVMING_DAILY_TASK_COMPLETION_AMOUNT, details.dailyPvmingAmount);
                player.putAttrib(PVMING_DAILY_TASK_COMPLETED, details.dailyPvmingCompleted);
                player.putAttrib(PVMING_DAILY_TASK_REWARD_CLAIMED, details.dailyPvmingRewardClaimed);
                player.putAttrib(IMPLING_DAILY_TASK_COMPLETION_AMOUNT, details.dailyImplingAmount);
                player.putAttrib(IMPLING_DAILY_TASK_COMPLETED, details.dailyImplingCompleted);
                player.putAttrib(IMPLING_DAILY_TASK_REWARD_CLAIMED, details.dailyImplingRewardClaimed);
                player.putAttrib(CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETION_AMOUNT, details.dailyCorruptedNechryarchAmount);
                player.putAttrib(CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETED, details.dailyCorruptedNechryarchCompleted);
                player.putAttrib(CORRUPTED_NECHRYARCH_DAILY_TASK_REWARD_CLAIMED, details.dailyCorruptedNechryarchRewardClaimed);
                player.putAttrib(VALINOR_COINS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyValinorCoinsAmount);
                player.putAttrib(VALINOR_COINS_DAILY_TASK_COMPLETED, details.dailyValinorCoinsCompleted);
                player.putAttrib(VALINOR_COINS_DAILY_TASK_REWARD_CLAIMED, details.dailyValinorCoinsRewardClaimed);
                player.putAttrib(TOURNY_DAILY_TASK_COMPLETION_AMOUNT, details.dailyTournyAmount);
                player.putAttrib(TOURNY_DAILY_TASK_COMPLETED, details.dailyTournyCompleted);
                player.putAttrib(TOURNY_DAILY_TASK_REWARD_CLAIMED, details.dailyTournyRewardClaimed);
                player.putAttrib(STARDUST_DAILY_TASK_COMPLETION_AMOUNT, details.dailyStardustAmount);
                player.putAttrib(STARDUST_DAILY_TASK_COMPLETED, details.dailyStardustCompleted);
                player.putAttrib(STARDUST_DAILY_TASK_REWARD_CLAIMED, details.dailyStardustRewardClaimed);
                player.putAttrib(WORLD_BOSS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyWorldBossAmount);
                player.putAttrib(WORLD_BOSS_DAILY_TASK_COMPLETED, details.dailyWorldBossCompleted);
                player.putAttrib(WORLD_BOSS_DAILY_TASK_REWARD_CLAIMED, details.dailyWorldBossRewardClaimed);
                player.putAttrib(REVENANTS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyRevenantsAmount);
                player.putAttrib(REVENANTS_DAILY_TASK_COMPLETED, details.dailyRevenantsCompleted);
                player.putAttrib(REVENANTS_DAILY_TASK_REWARD_CLAIMED, details.dailyRevenantsRewardClaimed);
                player.putAttrib(KRAKEN_DAILY_TASK_COMPLETION_AMOUNT, details.dailyKrakensAmount);
                player.putAttrib(KRAKEN_DAILY_TASK_COMPLETED, details.dailyKrakensCompleted);
                player.putAttrib(KRAKEN_DAILY_TASK_REWARD_CLAIMED, details.dailyKrakensRewardClaimed);
                player.putAttrib(BATTLE_MAGE_DAILY_TASK_COMPLETION_AMOUNT, details.dailyBattleMagesAmount);
                player.putAttrib(BATTLE_MAGE_DAILY_TASK_COMPLETED, details.dailyBattleMagesCompleted);
                player.putAttrib(BATTLE_MAGE_DAILY_TASK_REWARD_CLAIMED, details.dailyBattleMagesRewardClaimed);
                player.putAttrib(NEX_DAILY_TASK_COMPLETION_AMOUNT, details.dailyNexAmount);
                player.putAttrib(NEX_DAILY_TASK_COMPLETED, details.dailyNexCompleted);
                player.putAttrib(NEX_DAILY_TASK_REWARD_CLAIMED, details.dailyNexRewardClaimed);
                player.putAttrib(DEMONIC_GORILLA_DAILY_TASK_COMPLETION_AMOUNT, details.dailyDemonicGorillasAmount);
                player.putAttrib(DEMONIC_GORILLA_DAILY_TASK_COMPLETED, details.dailyDemonicGorillasCompleted);
                player.putAttrib(DEMONIC_GORILLA_DAILY_TASK_REWARD_CLAIMED, details.dailyDemonicGorillasRewardClaimed);
                player.putAttrib(WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETION_AMOUNT, details.dailyWinPvPTournyAmount);
                player.putAttrib(WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETED, details.dailyWinPvPTournyCompleted);
                player.putAttrib(WIN_PVP_TOURNAMENT_DAILY_TASK_REWARD_CLAIMED, details.dailyWinPvPTournyRewardClaimed);
                player.putAttrib(WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETION_AMOUNT, details.dailyWildykeyEscapeAmount);
                player.putAttrib(WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETED, details.dailyWildykeyEscapeCompleted);
                player.putAttrib(WILDY_KEY_ESCAPE_DAILY_TASK_REWARD_CLAIMED, details.dailyWildykeyEscapeRewardClaimed);
                player.putAttrib(KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETION_AMOUNT, details.dailyKillPlayersRevCaveAmount);
                player.putAttrib(KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETED, details.dailyKillPlayersRevCaveCompleted);
                player.putAttrib(KILL_PLAYERS_REV_CAVE_DAILY_TASK_REWARD_CLAIMED, details.dailyKillPlayersRevCaveRewardClaimed);
                player.putAttrib(DEEP_WILD_DAILY_TASK_COMPLETION_AMOUNT, details.dailyDeepWildAmount);
                player.putAttrib(DEEP_WILD_DAILY_TASK_COMPLETED, details.dailyDeepWildCompleted);
                player.putAttrib(DEEP_WILD_DAILY_TASK_REWARD_CLAIMED, details.dailyDeepWildRewardClaimed);
                player.putAttrib(KILLSTREAK_DAILY_TASK_COMPLETION_AMOUNT, details.dailyKillstreakAmount);
                player.putAttrib(KILLSTREAK_DAILY_TASK_COMPLETED, details.dailyKillstreakCompleted);
                player.putAttrib(KILLSTREAK_DAILY_TASK_REWARD_CLAIMED, details.dailyKillstreakRewardClaimed);
                player.putAttrib(EDGEVILLE_KILLS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyEdgevilleKillsAmount);
                player.putAttrib(EDGEVILLE_KILLS_DAILY_TASK_COMPLETED, details.dailyEdgevilleKillsCompleted);
                player.putAttrib(EDGEVILLE_KILLS_DAILY_TASK_REWARD_CLAIMED, details.dailyEdgevilleKillsRewardClaimed);
                player.putAttrib(MAGE_BANK_KILLS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyMageBankKillsAmount);
                player.putAttrib(MAGE_BANK_KILLS_DAILY_TASK_COMPLETED, details.dailyMageBankKillsCompleted);
                player.putAttrib(MAGE_BANK_KILLS_DAILY_TASK_REWARD_CLAIMED, details.dailyMageBankKillsRewardClaimed);
                player.putAttrib(MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyMemberCaveKillsAmount);
                player.putAttrib(MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETED, details.dailyMemberCaveKillsCompleted);
                player.putAttrib(MEMBER_CAVE_KILLS_DAILY_TASK_REWARD_CLAIMED, details.dailyMemberCaveKillsRewardClaimed);
                player.putAttrib(ELDER_CHAOS_DRUID_KILLS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyElderChaosDruidKillsAmount);
                player.putAttrib(ELDER_CHAOS_DRUIDS_KILLS_DAILY_TASK_COMPLETED, details.dailyElderChaosDruidKillsCompleted);
                player.putAttrib(ELDER_CHAOS_DRUIDS_KILLS_DAILY_TASK_REWARD_CLAIMED, details.dailyElderChaosDruidKillsRewardClaimed);
                player.putAttrib(ALCHEMICAL_HYDRA_LOG_CLAIMED, details.alchemicalHydraLogClaimed);
                player.putAttrib(ANCIENT_BARRELCHEST_LOG_CLAIMED, details.ancientBarrelchestLogClaimed);
                player.putAttrib(ANCIENT_CHAOS_ELEMENTAL_LOG_CLAIMED, details.ancientChaosElementalLogClaimed);
                player.putAttrib(ANCIENT_KING_BLACK_DRAGON_LOG_CLAIMED, details.ancientKingBlackDragonLogClaimed);
                player.putAttrib(ARACHNE_LOG_CLAIMED, details.arachneLogClaimed);
                player.putAttrib(ARTIO_LOG_CLAIMED, details.artioLogClaimed);
                player.putAttrib(BARRELCHEST_LOG_CLAIMED, details.barrelchestLogClaimed);
                player.putAttrib(BRUTAL_LAVA_DRAGON_LOG_CLAIMED, details.brutalLavaDragonLogClaimed);
                player.putAttrib(CALLISTO_LOG_CLAIMED, details.callistoLogClaimed);
                player.putAttrib(CERBERUS_LOG_CLAIMED, details.cerberusLogClaimed);
                player.putAttrib(CHAOS_ELEMENTAL_LOG_CLAIMED, details.chaosElementalLogClaimed);
                player.putAttrib(CHAOS_FANATIC_LOG_CLAIMED, details.chaosFanaticLogClaimed);
                player.putAttrib(CORPOREAL_BEAST_LOG_CLAIMED, details.corporealBeastLogClaimed);
                player.putAttrib(CORRUPTED_NECHRYARCH_LOG_CLAIMED, details.corruptedNechryarchLogClaimed);
                player.putAttrib(CRAZY_ARCHAEOLOGIST_LOG_CLAIMED, details.crazyArchaeologistLogClaimed);
                player.putAttrib(DEMONIC_GORILLA_LOG_CLAIMED, details.demonicGorillaLogClaimed);
                player.putAttrib(GIANT_MOLE_LOG_CLAIMED, details.giantMoleLogClaimed);
                player.putAttrib(KREEARRA_LOG_CLAIMED, details.kreeArraLogClaimed);
                player.putAttrib(GENERAL_GRAARDOR_LOG_CLAIMED, details.generalGraardorLogClaimed);
                player.putAttrib(COMMANDER_ZILYANA_LOG_CLAIMED, details.commanderZilyanaLogClaimed);
                player.putAttrib(KRIL_TSUTSAROTH_LOG_CLAIMED, details.krilTsutsarothLogClaimed);
                player.putAttrib(ABYSSALSIRE_LOG_CLAIMED, details.abyssalSireLogClaimed);
                player.putAttrib(BARROWS_LOG_CLAIMED,details.barrowsLogClaimed);
                player.putAttrib(DAGANNOTH_KINGS_LOG_CLAIMED, details.dagannothKingsLogClaimed);
                player.putAttrib(KALPHITE_QUEEN_LOG_CLAIMED, details.kalphiteQueenLogClaimed);
                player.putAttrib(NEX_LOG_CLAIMED, details.nexLogClaimed);
                player.putAttrib(LARRANS_KEY_LOG_CLAIMED, details.larransKeyLogClaimed);
                player.putAttrib(CRYSTAL_KEY_LOG_CLAIMED, details.crystalKeyLogClaimed);
                player.putAttrib(SLAYER_KEY_LOG_CLAIMED, details.slayerKeyLogClaimed);
                player.putAttrib(BRIMSTONE_KEY_LOG_CLAIMED, details.brimstoneKeyLogClaimed);
                player.putAttrib(COLLECTION_KEY_LOG_CLAIMED, details.collectionKeyLogClaimed);
                player.putAttrib(WILDERNESS_KEY_LOG_CLAIMED, details.wildernessKeyLogClaimed);
                player.putAttrib(KERBEROS_LOG_CLAIMED, details.kerberosLogClaimed);
                player.putAttrib(KING_BLACK_DRAGON_LOG_CLAIMED, details.kingBlackDragonLogClaimed);
                player.putAttrib(KRAKEN_LOG_CLAIMED, details.krakenLogClaimed);
                player.putAttrib(LAVA_DRAGON_LOG_CLAIMED, details.lavaDragonLogClaimed);
                player.putAttrib(LIZARDMAN_SHAMAN_LOG_CLAIMED, details.lizardmanShamanLogClaimed);
                player.putAttrib(SCORPIA_LOG_CLAIMED, details.scorpiaLogClaimed);
                player.putAttrib(SKORPIOS_LOG_CLAIMED, details.skorpiosLogClaimed);
                player.putAttrib(SKOTIZO_LOG_CLAIMED, details.skotizoLogClaimed);
                player.putAttrib(TEKTON_LOG_CLAIMED, details.tektonLogClaimed);
                player.putAttrib(THERMONUCLEAR_SMOKE_DEVIL_LOG_CLAIMED, details.thermonuclearSmokeDevilLogClaimed);
                player.putAttrib(THE_NIGTHMARE_LOG_CLAIMED, details.theNightmareLogClaimed);
                player.putAttrib(CORRUPTED_HUNLEFF_LOG_CLAIMED, details.corruptedHunleffLogClaimed);
                player.putAttrib(MEN_IN_BLACK_LOG_CLAIMED, details.menInBlackLogClaimed);
                player.putAttrib(TZTOK_JAD_LOG_CLAIMED, details.tztokJadLogClaimed);
                player.putAttrib(VENENATIS_LOG_CLAIMED, details.venenatisLogClaimed);
                player.putAttrib(VETION_LOG_CLAIMED, details.vetionLogClaimed);
                player.putAttrib(VORKATH_LOG_CLAIMED, details.vorkathLogClaimed);
                player.putAttrib(ZOMBIES_CHAMPION_LOG_CLAIMED, details.zombiesChampionLogClaimed);
                player.putAttrib(ZULRAH_LOG_CLAIMED, details.zulrahLogClaimed);
                player.putAttrib(ANCIENT_REVENANTS_LOG_CLAIMED, details.ancientRevenantsLogClaimed);
                player.putAttrib(CHAMBER_OF_SECRETS_LOG_CLAIMED, details.chamberOfSecretsLogClaimed);
                player.putAttrib(REVENANTS_LOG_CLAIMED, details.revenantsLogClaimed);
                player.putAttrib(CHAMBER_OF_XERIC_LOG_CLAIMED, details.chamberOfXericLogClaimed);
                player.putAttrib(THEATRE_OF_BLOOD_LOG_CLAIMED, details.theatreOfBloodLogClaimed);
                player.putAttrib(CRYSTALLINE_HUNLLEF_LOG_CLAIMED, details.crystallineHunleffLogClaimed);
                player.putAttrib(FRAGMENT_OF_SEREN_LOG_CLAIMED, details.fragmentOfSerenLogClaimed);
                player.putAttrib(BLOOD_FURY_HESPORI_LOG_CLAIMED, details.bloodFurHesporiLogClaimed);
                player.putAttrib(INFERNAL_SPIDER_LOG_CLAIMED, details.infernalSpiderLogClaimed);
                player.putAttrib(DONATOR_MYSTERY_BOX_LOG_CLAIMED, details.donatorMysteryBoxLogClaimed);
                player.putAttrib(ARMOUR_MYSTERY_BOX_LOG_CLAIMED, details.armourMysteryBoxLogClaimed);
                player.putAttrib(WEAPON_MYSTERY_BOX_LOG_CLAIMED, details.weaponMysteryBoxLogClaimed);
                player.putAttrib(LEGENDARY_MYSTERY_BOX_LOG_CLAIMED, details.legendaryMysteryBoxLogClaimed);
                player.putAttrib(MYSTERY_TICKET_LOG_CLAIMED, details.mysteryTicketLogClaimed);
                player.putAttrib(MYSTERY_CHEST_LOG_CLAIMED, details.mysteryChestLogClaimed);
                player.putAttrib(PETS_MYSTERY_BOX_LOG_CLAIMED, details.petsMysteryLogClaimed);
                player.putAttrib(SARACHNIS_LOG_CLAIMED, details.sarachnisLogClaimed);
                player.putAttrib(BRYOPHYTA_LOG_CLAIMED, details.bryophytaLogClaimed);
                player.putAttrib(SLAYER_LOG_CLAIMED, details.slayerLogClaimed);
                player.putAttrib(ENRAGED_GORILLA_LOG_CLAIMED, details.enragedGorillaLogClaimed);
                player.putAttrib(CHAOTIC_NIGHTMARE_LOG_CLAIMED, details.chaoticNightmareLogClaimed);
                player.putAttrib(LAST_DAILY_RESET, details.lastDailyReset);
                player.putAttrib(FINISHED_HALLOWEEN_TEACHER_DIALOGUE, details.finishedHalloweenDialogue);
                player.putAttrib(CANDIES_TRADED, details.candiesTraded);
                player.putAttrib(HWEEN_EVENT_TOKENS_SPENT, details.hweenEventTokensSpent);
                player.putAttrib(WINTER_EVENT_TOKENS_SPENT, details.winterEventTokensSpent);
                player.putAttrib(HERB_BOX_CHARGES, details.herbBoxCharges);
                player.putAttrib(COMBAT_MAXED, details.combatMaxed);
                player.putAttrib(FIGHT_CAVES_WAVE, details.fightCaveWave);
                player.putAttrib(KYLIE_MINNOW_DIALOGUE_STARTED, details.kylieMinnowDialogueStarted);
                player.putAttrib(BAGGED_COAL, details.baggedCoal);
                player.putAttrib(EXP_LAMP_WARNING_SENT, details.expLampWarningSent);
                player.putAttrib(EXP_LAMP_USED, details.expLampUsed);
                player.putAttrib(SWIFT_ABILITY, details.swiftAbility);
                player.putAttrib(GOLIATH_ABILITY, details.goliathAbility);
                player.putAttrib(WEAPON_UPGRADES, details.weaponUpgrades);
                player.putAttrib(DDS_KILLS, details.ddsKills);
                player.putAttrib(GMAUL_KILLS, details.gmaulKills);
                player.putAttrib(WHIP_KILLS, details.whipKills);
                player.putAttrib(D_SCIM_KILLS, details.dScimKills);
                player.putAttrib(STAFF_OF_LIGHT_KILLS, details.staffOfLightKills);
                player.putAttrib(RUNE_C_BOW_KILLS, details.runeCBowKills);
                player.putAttrib(D_MACE_KILLS, details.dMaceKills);
                player.putAttrib(D_LONG_KILLS, details.dLongKills);
                player.putAttrib(MSB_KILLS, details.msbKills);
                player.putAttrib(DBOW_KILLS, details.dbowKills);
                player.putAttrib(DDS_KILLS_TIER_UNLOCKED, details.ddsKillsTier);
                player.putAttrib(GMAUL_KILLS_TIER_UNLOCKED, details.gmaulKillsTier);
                player.putAttrib(WHIP_KILLS_TIER_UNLOCKED, details.whipKillsTier);
                player.putAttrib(D_SCIM_KILLS_TIER_UNLOCKED, details.dScimKillsTier);
                player.putAttrib(STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED, details.staffOfLightKillsTier);
                player.putAttrib(RUNE_C_BOW_KILLS_TIER_UNLOCKED, details.runeCBowKillsTier);
                player.putAttrib(D_MACE_KILLS_TIER_UNLOCKED, details.dMaceKillsTier);
                player.putAttrib(D_LONG_KILLS_TIER_UNLOCKED, details.dLongKillsTier);
                player.putAttrib(MSB_KILLS_TIER_UNLOCKED, details.msbKillsTier);
                player.putAttrib(DBOW_KILLS_TIER_UNLOCKED, details.dbowKillsTier);
                player.putAttrib(ARMOUR_MYSTERY_BOXES_OPENED, details.armourMysteryBoxesOpened);
                player.putAttrib(DONATOR_MYSTERY_BOXES_OPENED, details.donatorMysteryBoxesOpened);
                player.putAttrib(LEGENDARY_MYSTERY_BOXES_OPENED, details.legendaryMysteryBoxesOpened);
                player.putAttrib(MYSTERY_CHESTS_OPENED, details.mysteryChestsOpened);
                player.putAttrib(MYSTERY_TICKETS_OPENED, details.mysteryTicketsOpened);
                player.putAttrib(PETS_MYSTERY_BOXES_OPENED, details.petsMysteryBoxesOpened);
                player.putAttrib(WEAPON_MYSTERY_BOXES_OPENED, details.weaponMysteryBoxesOpened);
                player.putAttrib(STARTER_WEAPON_DAMAGE, details.starterWeaponDamage);
                player.putAttrib(ANCIENT_VLS_ATTEMPTS, details.ancientVLSAttempts);
                player.putAttrib(ANCIENT_SWH_ATTEMPTS, details.ancientSWHAttempts);
                player.putAttrib(ARMADYL_GODSWORD_OR_ATTEMPTS, details.enchantedAGSAttempts);
                player.putAttrib(BANDOS_GODSWORD_OR_ATTEMPTS, details.enchantedBGSAttempts);
                player.putAttrib(SARADOMIN_GODSWORD_OR_ATTEMPTS, details.enchantedSGSAttempts);
                player.putAttrib(ZAMORAK_GODSWORD_OR_ATTEMPTS, details.enchantedZGSAttempts);
                player.putAttrib(FURY_OR_ATTEMPTS, details.enchantedFuryAttempts);
                player.putAttrib(OCCULT_OR_ATTEMPTS, details.enchantedOccultAttempts);
                player.putAttrib(TORTURE_OR_ATTEMPTS, details.enchantedTortureAttempts);
                player.putAttrib(ANGUISH_OR_ATTEMPTS, details.enchantedAnguishAttempts);
                player.putAttrib(BERSERKER_NECKLACE_OR_ATTEMPTS, details.enchantedBNeckAttempts);
                player.putAttrib(TORMENTED_BRACELET_OR_ATTEMPTS, details.enchantedTBraceAttempts);
                player.putAttrib(GRANITE_MAUL_OR_ATTEMPTS, details.enchantedGmaulAttempts);
                player.putAttrib(RUNE_POUCH_I_ATTEMPTS, details.enchantedRunePouchAttempts);
                player.putAttrib(DRAGON_CLAWS_OR_ATTEMPTS, details.enchantedDClawsAttempts);
                player.putAttrib(MAGMA_BLOWPIPE_ATTEMPTS, details.blowpipeAttempts);
                player.putAttrib(SANGUINE_TWISTED_BOW_ATTEMTPS, details.twistedBowAttempts);
                player.putAttrib(ANCESTRAL_HAT_I_ATTEMPTS, details.ancestralHatAttempts);
                player.putAttrib(ANCESTRAL_ROBE_TOP_I_ATTEMPTS, details.ancestralTopAttempts);
                player.putAttrib(ANCESTRAL_ROBE_BOTTOM_I_ATTEMPTS, details.ancestralBottomAttempts);
                player.putAttrib(PRIMORDIAL_BOOTS_OR_ATTEMPTS, details.primordialBootsAttempts);
                player.putAttrib(INFERNAL_CAPE_ATTEMPTS, details.infernalCapeAttempts);
                player.putAttrib(HOLY_SANGUINESTI_STAFF_ATTEMPTS, details.sanguistiStaffAttempts);
                player.putAttrib(HOLY_SCYTHE_OF_VITUR_ATTEMPTS, details.holyScytheAttempts);
                player.putAttrib(HOLY_GHRAZI_RAPIER_ATTEMPTS, details.ghraziRapierAttempts);
                player.putAttrib(SANGUINE_SCYTHE_OF_VITUR_ATTEMPTS, details.scytheOfViturAttempts);
                player.putAttrib(PEGASIAN_BOOTS_OR_ATTEMPTS, details.pegasianBootsAttempts);
                player.putAttrib(ETERNAL_BOOTS_OR_ATTEMPTS, details.eternalBootsAttempts);
                player.putAttrib(CORRUPTED_VIGGORAS_CHAINMACE_ATTEMPTS, details.viggorasChainmaceAttempts);
                player.putAttrib(CORRUPTED_CRAWS_BOW_ATTEMPTS, details.crawsBowAttempts);
                player.putAttrib(CORRUPTED_THAMMARONS_STAFF_ATTEMPTS, details.thammaronsStaffAttempts);
                player.putAttrib(CORRUPTED_BOOTS_ATTEMTPS, details.corruptedBootsAttempts);
                player.putAttrib(ANCIENT_FACEGUARD_ATTEMPTS, details.ancientFaceguardAttempts);
                player.putAttrib(TOXIC_STAFF_OF_THE_DEAD_C_ATTEMPTS, details.toxicStaffOfTheDeadAttempts);
                player.getTimers().register(TimerKey.DEATH_TELEPORT_TIMER, details.deathTeleportTimer);
                return true;
            }
        }

        //Account
        private final String username;
        private final String password;
        private final String title;
        private final String titleColor;
        private final Tile tile;
        private final int gameTime;
        private final double runEnergy;
        private final boolean running;
        private final String playerRights;
        private final String memberRights;
        private final ExpMode expMode;
        private final GameMode gameMode;
        private final boolean groupNameSet;
        private final boolean hardcoreGroupFallen;
        private final String lastIP;
        private final String mac;
        private final int accountPin;
        private final boolean askAccountPin;
        private final int accountPinAttemptsLeft;
        private final int accountPinFrozenTicks;
        private final Timestamp creationDate;
        private final String creationIp;
        private final Timestamp lastLogin;
        private final boolean muted;
        private final boolean newPlayer;
        private final boolean isBetaTester;
        private final boolean veteran;
        private final boolean gambler;
        private final boolean starterboxClaimed;
        private final boolean clanBoxOpened;
        private final boolean promoCodeClaimed;
        private final boolean receivedMonthlySponsorRewards;
        private final boolean female;
        private final int[] looks;
        private final int[] colors;

        //Combat attribs
        private final String spellBook;
        private final String fightType;
        private final int fightTypeVarp;
        private final int fightTypeVarpState;
        private final boolean autoRetaliate;
        private final MagicSpellbook previousSpellbook;
        private final int venomTicks;
        private final int poisonTicks;
        private final int specPercentage;
        private final int recoilCharges;
        private final int targetSearchTimer;
        private final int specialAttackRestoreTimer;
        private final int skullTimer;
        private final SkullType skullType;
        private final DefaultPrayerData[] quickPrayers;
        private final Presetable[] presets;
        private final Object[] lastPreset;
        private final int specialTeleblockTimer;

        //Member attribs
        private final double totalAmountPaid;
        private final double promoPaymentAmount;
        private final boolean sapphireMemberUnlocked;
        private final boolean emeraldMemberUnlocked;
        private final boolean rubyMemberUnlocked;
        private final boolean diamondMemberUnlocked;
        private final boolean dragonstoneMemberUnlocked;
        private final boolean onyxMemberUnlocked;
        private final boolean zenyteMemberUnlocked;

        //Skills
        private final int[] dynamicLevels;
        private final double[] skillXP;
        private final int activePetItemId;
        private final ArrayList<Integer> unlockedPets;
        private final ArrayList<Integer> insuredPets;

        private final int slayerTaskId;
        private final int slayerTaskAmount;
        private final int slayerMasterId;
        private final int slayerTaskStreak;
        private final int slayerTaskStreakRecord;
        private final int completedSlayerTasks;
        private final boolean wildernessSlayerDescribed;
        private final String slayerPartner;
        private final ArrayList<Integer> blockedSlayerTasks;
        private final HashMap<Integer, String> slayerUnlocks;
        private final HashMap<Integer, String> slayerExtensionsList;

        //Containers
        private final Item[] inventory;
        private final Item[] equipment;
        private final Item[] bank;
        private final int[] tabAmounts;
        private final boolean placeholdersActive;
        private final int placeHolderAmount;
        private final String hashedBankPin;
        private final int bankPinLength;
        private final int recoveryDelay;
        private final BankPinModification pendingBankPinMod;
        private final Item[] lootingBag;
        private final boolean askHowManyToStore;
        private final boolean storeAsMany;
        private final Item[] runePouch;
        private final ArrayList<Item> nifflerItems;
        private final int totalCartValue;
        private final ArrayList<Item> cartItems;

        //Friends
        private List<String> friends;

        //Ignores
        private List<String> ignores;

        //Clan
        private final String clan;

        //Settings
        private final boolean acceptAid;
        private final String yellColour;
        private final String yellTag;
        private final String yellTagColour;
        private final boolean dontAskAgainNightmareStaff;
        private final boolean currencyCollection;
        private final boolean emptyPotionVials;
        private final boolean gold_ags_spec;
        private final boolean gold_bgs_spec;
        private final boolean gold_sgs_spec;
        private final boolean gold_zgs_spec;
        private final boolean xpLocked;
        private final boolean enableDebugMessages;
        private final boolean savePresetLevels;
        private final boolean openPresetsOnDeath;
        private final boolean[] savedDuelConfig;
        private final boolean autoRepairBrokenItems;

        //Points
        private final int votePoints;
        private final int pestControlPoints;
        private final int slayerRewardPoints;
        private final int bossPoints;
        private final int pkPoints;
        private final int achievementPoints;
        private final int bountyHunterPoints;
        private final int hotspotPoints;

        //Unlocks
        private final boolean teleportToTargetUnlocked;
        private final boolean preserve;
        private final boolean rigour;
        private final boolean augury;

        //Pvp
        private final int kills;
        private final int deaths;
        private final List<String> recentKills;
        private final long firstKillOfTheDay;
        private final int bountyTasksSkipped;
        private final boolean bountyTaskNotifications;
        private final int bountyTasksCompleted;
        private final int bountyHunterTaskCompletionAmount;
        private final BountyHunterTask.BountyTasks bountyTask;

        //counts
        private final int killstreak;
        private final int killstreakRecord;
        private final int kingBlackDragonsKilled;
        private final int vetionsKilled;
        private final int crazyArchaeologistsKilled;
        private final int zulrahsKilled;
        private final int alchysKilled;
        private final int krakensKilled;
        private final int revenantsKilled;
        private final int ancientRevenantsKilled;
        private final int ancientKingBlackDragonsKilled;
        private final int corruptedHunleffsKilled;
        private final int ancientChaosElementalsKilled;
        private final int ancientBarrelchestsKilled;
        private final int kerberosKilled;
        private final int arachneKilled;
        private final int skorpiosKilled;
        private final int artioKilled;
        private final int jadsKilled;
        private final int chaosElementalsKilled;
        private final int demonicGorillasKilled;
        private final int barrelchestsKilled;
        private final int corporealBeastsKilled;
        private final int abyssalSiresKilled;
        private final int vorkathsKilled;
        private final int lizardmanShamansKilled;
        private final int barrowsChestsOpened;
        private final int corruptedNechryarchsKilled;
        private final int fluffysKilled;
        private final int dementorsKilled;
        private final int hungarianHorntailsKilled;
        private final int fenrirGreybacksKilled;
        private final int scorpiasKilled;
        private final int callistosKilled;
        private final int molesKilled;
        private final int kreeArrasKilled;
        private final int zilyanasKilled;
        private final int graardorsKilled;
        private final int krilTsutsarothsKilled;
        private final int nightmaresKilled;
        private final int nexKilled;
        private final int theatreOfBloodRuns;
        private final int chamberOfXericRuns;
        private final int crystallineHunleffKilled;
        private final int fragmentOfSerenKilled;
        private final int bloodFuryHesporiKilled;
        private final int infernalSpiderKilled;
        private final int enragedGorillasKilled;
        private final int chaoticNightmaresKilled;
        private final int rexKilled;
        private final int primeKilled;
        private final int supremeKilled;
        private final int kalphiteQueensKilled;
        private final int lavaDragonsKilled;
        private final int skotizosKilled;
        private final int zombieChampionsKilled;
        private final int brutalLavaDragonsKilled;
        private final int tektonsKilled;
        private final int chaosFanaticsKilled;
        private final int thermonuclearSmokeDevilKilled;
        private final int venenatisKilled;
        private final int aragogKC;
        private final int chamberOfSecretRuns;
        private final int smokeDevilKills;
        private final int superiorCreatureKills;
        private final int crawlingHandKills;
        private final int caveBugKills;
        private final int caveCrawlerKills;
        private final int bansheeKills;
        private final int caveSlimeKills;
        private final int rockslugKills;
        private final int desertLizardKills;
        private final int cockatriceKills;
        private final int pyrefiendKills;
        private final int mogreKills;
        private final int harpieBugSwarmKills;
        private final int wallBeastKills;
        private final int killerwattKills;
        private final int molaniskKills;
        private final int basiliskKills;
        private final int seaSnakeKills;
        private final int terrorDogKills;
        private final int feverSpiderKills;
        private final int infernalMageKills;
        private final int brineRatKills;
        private final int bloodveldKills;
        private final int jellyKills;
        private final int turothKills;
        private final int zygomiteKills;
        private final int caveHorrorKills;
        private final int aberrantSpectreKills;
        private final int spiritualWarriorKills;
        private final int kuraskKills;
        private final int skeletalWyvernKills;
        private final int gargoyleKills;
        private final int nechryaelKills;
        private final int spiritualMageKills;
        private final int abyssalDemonKills;
        private final int caveKrakenKills;
        private final int darkBeastKills;
        private final int brutalBlackDragonKills;
        private final int fossilIslandWyvernKills;
        private final int wyrmKills;
        private final int drakeKills;
        private final int hydraKills;
        private final int basiliskKnightKills;

        //Content
        private final HashMap<EventRewards, Boolean> eventRewards;
        private final ArrayList<Integer> favoriteTeleports;
        private final Map<String, Integer> bossTimers;
        private final HashMap<Collection, ArrayList<Item>> collectionLog;
        private final HashMap<Achievements, Integer> achievements;
        private final int achievementsCompleted;
        private final BottleTasks task;
        private final int taskAmount;
        private final int taskCompletionAmount;
        private final int totalTasksCompleted;
        private final boolean canClaimTaskReward;
        private final int treasuresOpened;
        private final boolean referalMilestone10hoursPassed;
        private final boolean referalMilestone1dayPassed;
        private final String referrerUsername;
        private final int referralsCount;
        private final int databaseId;
        private final boolean referalMilestone3refs;
        private final int staminaTicks;
        private final int overloadTicks;
        private final int antifireTicks;
        private final boolean superAntiFire;
        private final int larranKeysUsed;
        private final int brimstoneKeysOpened;
        private final int collectionLogKeysOpened;
        private final int wildernessKeysOpened;

        private final int slayerKeysOpened;
        private final int slayerKeysReceived;

        private final int doubleExpTicks;
        private final int dropRateLampTicks;
        private final boolean ethereumAbsorption;

        private final int jailed;
        private final int jailOresToEscape;
        private final int jailOresMined;
        private final Tile locBeforeJail;
        private final int tournamentWins;
        private final int tournamentPoints;
        private final boolean lostCannon;

        private final int wildernessCourseState;

        //Daily task save attributes
        private final int dailyRockCrabsAmount;
        private final boolean dailyRockCrabsCompleted;
        private final boolean dailyRockCrabsRewardClaimed;

        private final int dailyExperimentsAmount;
        private final boolean dailyExperimentsCompleted;
        private final boolean dailyExperimentsRewardClaimed;

        private final int dailyThievingAmount;
        private final boolean dailyThievingCompleted;
        private final boolean dailyThievingRewardClaimed;

        private final int dailyVotingAmount;
        private final boolean dailyVotingCompleted;
        private final boolean dailyVotingRewardClaimed;

        private final int dailySkillingAmount;
        private final boolean dailySkillingCompleted;
        private final boolean dailySkillingRewardClaimed;

        private final int dailyPvmingAmount;
        private final boolean dailyPvmingCompleted;
        private final boolean dailyPvmingRewardClaimed;

        private final int dailyBarrowsAmount;
        private final boolean dailyBarrowsCompleted;
        private final boolean dailyBarrowsRewardClaimed;

        private final int dailySlayerAmount;
        private final boolean dailySlayerCompleted;
        private final boolean dailySlayerRewardClaimed;

        private final int dailyGreenDragonsAmount;
        private final boolean dailyGreenDragonsCompleted;
        private final boolean dailyGreenDragonsRewardClaimed;

        private final int dailyImplingAmount;
        private final boolean dailyImplingCompleted;
        private final boolean dailyImplingRewardClaimed;

        private final int dailyRaidsAmount;
        private final boolean dailyRaidsCompleted;
        private final boolean dailyRaidsRewardClaimed;

        private final int dailyZulrahAmount;
        private final boolean dailyZulrahCompleted;
        private final boolean dailyZulrahRewardClaimed;

        private final int dailyCorruptedNechryarchAmount;
        private final boolean dailyCorruptedNechryarchCompleted;
        private final boolean dailyCorruptedNechryarchRewardClaimed;

        private final int dailyValinorCoinsAmount;
        private final boolean dailyValinorCoinsCompleted;
        private final boolean dailyValinorCoinsRewardClaimed;

        private final int dailyTournyAmount;
        private final boolean dailyTournyCompleted;
        private final boolean dailyTournyRewardClaimed;

        private final int dailyStardustAmount;
        private final boolean dailyStardustCompleted;
        private final boolean dailyStardustRewardClaimed;

        private final int dailyWorldBossAmount;
        private final boolean dailyWorldBossCompleted;
        private final boolean dailyWorldBossRewardClaimed;

        private final int dailyRevenantsAmount;
        private final boolean dailyRevenantsCompleted;
        private final boolean dailyRevenantsRewardClaimed;

        private final int dailyKrakensAmount;
        private final boolean dailyKrakensCompleted;
        private final boolean dailyKrakensRewardClaimed;

        private final int dailyBattleMagesAmount;
        private final boolean dailyBattleMagesCompleted;
        private final boolean dailyBattleMagesRewardClaimed;

        private final int dailyNexAmount;
        private final boolean dailyNexCompleted;
        private final boolean dailyNexRewardClaimed;

        private final int dailyDemonicGorillasAmount;
        private final boolean dailyDemonicGorillasCompleted;
        private final boolean dailyDemonicGorillasRewardClaimed;

        private final int dailyWinPvPTournyAmount;
        private final boolean dailyWinPvPTournyCompleted;
        private final boolean dailyWinPvPTournyRewardClaimed;

        private final int dailyWildykeyEscapeAmount;
        private final boolean dailyWildykeyEscapeCompleted;
        private final boolean dailyWildykeyEscapeRewardClaimed;

        private final int dailyKillPlayersRevCaveAmount;
        private final boolean dailyKillPlayersRevCaveCompleted;
        private final boolean dailyKillPlayersRevCaveRewardClaimed;

        private final int dailyDeepWildAmount;
        private final boolean dailyDeepWildCompleted;
        private final boolean dailyDeepWildRewardClaimed;

        private final int dailyKillstreakAmount;
        private final boolean dailyKillstreakCompleted;
        private final boolean dailyKillstreakRewardClaimed;

        private final int dailyEdgevilleKillsAmount;
        private final boolean dailyEdgevilleKillsCompleted;
        private final boolean dailyEdgevilleKillsRewardClaimed;

        private final int dailyMageBankKillsAmount;
        private final boolean dailyMageBankKillsCompleted;
        private final boolean dailyMageBankKillsRewardClaimed;

        private final int dailyMemberCaveKillsAmount;
        private final boolean dailyMemberCaveKillsCompleted;
        private final boolean dailyMemberCaveKillsRewardClaimed;

        private final int dailyElderChaosDruidKillsAmount;
        private final boolean dailyElderChaosDruidKillsCompleted;
        private final boolean dailyElderChaosDruidKillsRewardClaimed;

        private final boolean alchemicalHydraLogClaimed;
        private final boolean ancientBarrelchestLogClaimed;
        private final boolean ancientChaosElementalLogClaimed;
        private final boolean ancientKingBlackDragonLogClaimed;
        private final boolean arachneLogClaimed;
        private final boolean artioLogClaimed;
        private final boolean barrelchestLogClaimed;
        private final boolean brutalLavaDragonLogClaimed;
        private final boolean callistoLogClaimed;
        private final boolean cerberusLogClaimed;
        private final boolean chaosElementalLogClaimed;
        private final boolean chaosFanaticLogClaimed;
        private final boolean corporealBeastLogClaimed;
        private final boolean corruptedNechryarchLogClaimed;
        private final boolean crazyArchaeologistLogClaimed;
        private final boolean demonicGorillaLogClaimed;
        private final boolean giantMoleLogClaimed;
        private final boolean kreeArraLogClaimed;
        private final boolean generalGraardorLogClaimed;
        private final boolean commanderZilyanaLogClaimed;
        private final boolean krilTsutsarothLogClaimed;
        private final boolean abyssalSireLogClaimed;
        private final boolean barrowsLogClaimed;
        private final boolean dagannothKingsLogClaimed;
        private final boolean kalphiteQueenLogClaimed;
        private final boolean nexLogClaimed;
        private final boolean larransKeyLogClaimed;
        private final boolean crystalKeyLogClaimed;
        private final boolean slayerKeyLogClaimed;
        private final boolean brimstoneKeyLogClaimed;
        private final boolean collectionKeyLogClaimed;
        private final boolean wildernessKeyLogClaimed;
        private final boolean kerberosLogClaimed;
        private final boolean kingBlackDragonLogClaimed;
        private final boolean krakenLogClaimed;
        private final boolean lavaDragonLogClaimed;
        private final boolean lizardmanShamanLogClaimed;
        private final boolean scorpiaLogClaimed;
        private final boolean skorpiosLogClaimed;
        private final boolean skotizoLogClaimed;
        private final boolean tektonLogClaimed;
        private final boolean thermonuclearSmokeDevilLogClaimed;
        private final boolean theNightmareLogClaimed;
        private final boolean corruptedHunleffLogClaimed;
        private final boolean menInBlackLogClaimed;
        private final boolean tztokJadLogClaimed;
        private final boolean venenatisLogClaimed;
        private final boolean vetionLogClaimed;
        private final boolean vorkathLogClaimed;
        private final boolean zombiesChampionLogClaimed;
        private final boolean zulrahLogClaimed;
        private final boolean ancientRevenantsLogClaimed;
        private final boolean chamberOfSecretsLogClaimed;
        private final boolean chamberOfXericLogClaimed;
        private final boolean theatreOfBloodLogClaimed;
        private final boolean crystallineHunleffLogClaimed;
        private final boolean fragmentOfSerenLogClaimed;
        private final boolean bloodFurHesporiLogClaimed;
        private final boolean infernalSpiderLogClaimed;
        private final boolean donatorMysteryBoxLogClaimed;
        private final boolean armourMysteryBoxLogClaimed;
        private final boolean weaponMysteryBoxLogClaimed;
        private final boolean legendaryMysteryBoxLogClaimed;
        private final boolean mysteryTicketLogClaimed;
        private final boolean mysteryChestLogClaimed;
        private final boolean petsMysteryLogClaimed;
        private final boolean sarachnisLogClaimed;
        private final boolean bryophytaLogClaimed;
        private final boolean revenantsLogClaimed;
        private final boolean slayerLogClaimed;
        private final boolean enragedGorillaLogClaimed;
        private final boolean chaoticNightmareLogClaimed;
        private final int lastDailyReset;
        private final boolean finishedHalloweenDialogue;
        private final int candiesTraded;
        private final int hweenEventTokensSpent;
        private final int winterEventTokensSpent;
        private final int herbBoxCharges;
        private final boolean combatMaxed;
        private final int fightCaveWave;
        private final boolean kylieMinnowDialogueStarted;
        private final int baggedCoal;
        private final boolean expLampWarningSent;
        private final boolean expLampUsed;
        private final boolean swiftAbility;
        private final boolean goliathAbility;
        private final int weaponUpgrades;
        private final int ddsKills;
        private final int gmaulKills;
        private final int whipKills;
        private final int dScimKills;
        private final int staffOfLightKills;
        private final int runeCBowKills;
        private final int dMaceKills;
        private final int dLongKills;
        private final int msbKills;
        private final int dbowKills;
        private final int ddsKillsTier;
        private final int gmaulKillsTier;
        private final int whipKillsTier;
        private final int dScimKillsTier;
        private final int staffOfLightKillsTier;
        private final int runeCBowKillsTier;
        private final int dMaceKillsTier;
        private final int dLongKillsTier;
        private final int msbKillsTier;
        private final int dbowKillsTier;
        private final int armourMysteryBoxesOpened;
        private final int donatorMysteryBoxesOpened;
        private final int legendaryMysteryBoxesOpened;
        private final int mysteryChestsOpened;
        private final int mysteryTicketsOpened;
        private final int petsMysteryBoxesOpened;
        private final int weaponMysteryBoxesOpened;
        private final int starterWeaponDamage;
        private final int ancientVLSAttempts;
        private final int ancientSWHAttempts;
        private final int enchantedAGSAttempts;
        private final int enchantedBGSAttempts;
        private final int enchantedSGSAttempts;
        private final int enchantedZGSAttempts;
        private final int enchantedFuryAttempts;
        private final int enchantedOccultAttempts;
        private final int enchantedTortureAttempts;
        private final int enchantedAnguishAttempts;
        private final int enchantedBNeckAttempts;
        private final int enchantedGmaulAttempts;
        private final int enchantedTBraceAttempts;
        private final int enchantedRunePouchAttempts;
        private final int enchantedDClawsAttempts;
        private final int blowpipeAttempts;
        private final int twistedBowAttempts;
        private final int ancestralHatAttempts;
        private final int ancestralTopAttempts;
        private final int ancestralBottomAttempts;
        private final int primordialBootsAttempts;
        private final int infernalCapeAttempts;
        private final int sanguistiStaffAttempts;
        private final int holyScytheAttempts;
        private final int ghraziRapierAttempts;
        private final int scytheOfViturAttempts;
        private final int pegasianBootsAttempts;
        private final int eternalBootsAttempts;
        private final int viggorasChainmaceAttempts;
        private final int crawsBowAttempts;
        private final int thammaronsStaffAttempts;
        private final int corruptedBootsAttempts;
        private final int ancientFaceguardAttempts;
        private final int toxicStaffOfTheDeadAttempts;
        private final int deathTeleportTimer;

        public String password() {
            return password;
        }

        public SaveDetails(Player player) {
            username = player.getUsername();
            if (player.getNewPassword() != null && !player.getNewPassword().equals("")) { // new pw has been set
                password = BCrypt.hashpw(player.getNewPassword(), BCrypt.gensalt());
                if (GameServer.properties().enableSql) {
                    GameServer.getDatabaseService().submit(new UpdatePasswordDatabaseTransaction(player, password));
                }
                player.setPassword(password);
            } else {
                password = player.getPassword();
            }
            title = Player.getAttribStringOr(player, TITLE, "");
            titleColor = Player.getAttribStringOr(player, TITLE_COLOR, "");
            tile = player.tile();
            gameTime = Player.getAttribIntOr(player, GAME_TIME, 0);
            runEnergy = Player.getAttribDoubleOr(player, RUN_ENERGY, 0D);
            running = Player.getAttribBooleanOr(player, IS_RUNNING, false);
            playerRights = player.getPlayerRights().name();
            memberRights = player.getMemberRights().name();
            expMode = player.expmode();
            gameMode = player.gameMode();
            groupNameSet = Player.getAttribBooleanOr(player, GROUP_NAME_SET, false);
            hardcoreGroupFallen = Player.getAttribBooleanOr(player, HARDCORE_GROUP_FALLEN, false);
            lastIP = player.getHostAddress();
            mac = player.getAttribOr(MAC_ADDRESS, "invalid");
            accountPin = Player.getAttribIntOr(player, ACCOUNT_PIN, 0);
            askAccountPin = Player.getAttribBooleanOr(player, ASK_FOR_ACCOUNT_PIN, false);
            accountPinAttemptsLeft = Player.getAttribIntOr(player, ACCOUNT_PIN_ATTEMPTS_LEFT, 5);
            accountPinFrozenTicks = Player.getAttribIntOr(player, ACCOUNT_PIN_FREEZE_TICKS, 0);
            creationDate = player.getCreationDate();
            creationIp = player.getCreationIp();
            lastLogin = player.getLastLogin();
            muted = Player.getAttribBooleanOr(player, MUTED, false);
            newPlayer = Player.getAttribBooleanOr(player, NEW_ACCOUNT, false);
            isBetaTester = Player.getAttribBooleanOr(player, IS_BETA_TESTER, false);
            veteran = Player.getAttribBooleanOr(player, VETERAN, false);
            gambler = Player.getAttribBooleanOr(player, GAMBLER, false);
            starterboxClaimed = Player.getAttribBooleanOr(player, STARTER_BOX_CLAIMED, false);
            clanBoxOpened = Player.getAttribBooleanOr(player, CLAN_BOX_OPENED, false);
            promoCodeClaimed = Player.getAttribBooleanOr(player, PROMO_CODE_CLAIMED, false);
            receivedMonthlySponsorRewards = Player.getAttribBooleanOr(player, RECEIVED_MONTHLY_SPONSOR_REWARDS, false);
            female = player.looks().female();
            looks = player.looks().looks();
            colors = player.looks().colors();
            spellBook = player.getSpellbook().name();
            fightType = player.getCombat().getFightType().name();
            fightTypeVarp = player.getCombat().getFightType().getParentId();
            fightTypeVarpState = player.getCombat().getFightType().getChildId();
            autoRetaliate = player.getCombat().autoRetaliate();
            previousSpellbook = player.getPreviousSpellbook();
            venomTicks = Player.getAttribIntOr(player, VENOM_TICKS, 0);
            poisonTicks = Player.getAttribIntOr(player, POISON_TICKS, 0);
            specPercentage = player.getSpecialAttackPercentage();
            recoilCharges = Player.getAttribIntOr(player, RING_OF_RECOIL_CHARGES, 40);
            targetSearchTimer = player.getTargetSearchTimer().secondsRemaining();
            specialAttackRestoreTimer = player.getSpecialAttackRestore().secondsRemaining();
            skullTimer = Player.getAttribIntOr(player, SKULL_CYCLES, 0);
            skullType = player.getSkullType();
            quickPrayers = player.getQuickPrayers().getPrayers();
            presets = player.getPresets();
            lastPreset = player.getLastPreset();
            specialTeleblockTimer = player.getTimers().left(TimerKey.SPECIAL_TELEBLOCK);
            totalAmountPaid = Player.getAttribDoubleOr(player, TOTAL_PAYMENT_AMOUNT, 0D);
            promoPaymentAmount = Player.getAttribDoubleOr(player, PROMO_PAYMENT_AMOUNT, 0D);
            sapphireMemberUnlocked = Player.getAttribBooleanOr(player, SAPPHIRE_MEMBER_UNLOCKED, false);
            emeraldMemberUnlocked = Player.getAttribBooleanOr(player, EMERALD_MEMBER_UNLOCKED, false);
            rubyMemberUnlocked = Player.getAttribBooleanOr(player, RUBY_MEMBER_UNLOCKED, false);
            diamondMemberUnlocked = Player.getAttribBooleanOr(player, DIAMOND_MEMBER_UNLOCKED, false);
            dragonstoneMemberUnlocked = Player.getAttribBooleanOr(player, DRAGONSTONE_MEMBER_UNLOCKED, false);
            onyxMemberUnlocked = Player.getAttribBooleanOr(player, ONYX_MEMBER_UNLOCKED, false);
            zenyteMemberUnlocked = Player.getAttribBooleanOr(player, ZENYTE_MEMBER_UNLOCKED, false);
            dynamicLevels = player.skills().levels();
            skillXP = player.skills().xp();
            activePetItemId = Player.getAttribIntOr(player, ACTIVE_PET_ITEM_ID, 0);
            unlockedPets = player.getUnlockedPets();
            insuredPets = player.getInsuredPets();
            slayerTaskId = Player.getAttribIntOr(player, SLAYER_TASK_ID, 0);
            slayerTaskAmount = Player.getAttribIntOr(player, SLAYER_TASK_AMT, 0);
            slayerMasterId = Player.getAttribIntOr(player, SLAYER_MASTER, 0);
            slayerTaskStreak = Player.getAttribIntOr(player, SLAYER_TASK_SPREE, 0);
            slayerTaskStreakRecord = Player.getAttribIntOr(player, SLAYER_TASK_SPREE_RECORD, 0);
            completedSlayerTasks = Player.getAttribIntOr(player, COMPLETED_SLAYER_TASKS, 0);
            wildernessSlayerDescribed = Player.getAttribBooleanOr(player, WILDERNESS_SLAYER_DESCRIBED, false);
            slayerPartner = player.getAttribOr(SLAYER_PARTNER, "None");
            blockedSlayerTasks = player.getSlayerRewards().getBlocked();
            slayerUnlocks = player.getSlayerRewards().getUnlocks();
            slayerExtensionsList = player.getSlayerRewards().getExtendable();
            inventory = player.inventory().toArray();
            equipment = player.getEquipment().toArray();
            bank = player.getBank().toNonNullArray();
            tabAmounts = player.getBank().tabAmounts;
            placeholdersActive = player.getBank().placeHolder;
            placeHolderAmount = player.getBank().placeHolderAmount;
            hashedBankPin = player.getBankPin().getHashedPin();
            bankPinLength = player.getBankPin().getPinLength();
            recoveryDelay = player.getBankPin().getRecoveryDays();
            pendingBankPinMod = player.getBankPin().getPendingMod();
            lootingBag = player.getLootingBag().toNonNullArray();
            askHowManyToStore = player.getLootingBag().askHowManyToStore();
            storeAsMany = player.getLootingBag().storeAsMany();
            runePouch = player.getRunePouch().toArray();
            totalCartValue = Player.getAttribIntOr(player, CART_ITEMS_TOTAL_VALUE, 0);
            cartItems = player.getAttribOr(CART_ITEMS, new ArrayList<Item>());
            nifflerItems = player.getAttribOr(NIFFLER_ITEMS_STORED, new ArrayList<Item>());
            friends = player.getRelations().getFriendList();
            ignores = player.getRelations().getIgnoreList();
            clan = player.getClanChat();
            acceptAid = Player.getAttribBooleanOr(player, ACCEPT_AID, false);
            yellColour = Player.getAttribStringOr(player, YELL_COLOUR, "000000");
            yellTag = Player.getAttribStringOr(player, YELL_TAG, "");
            yellTagColour = Player.getAttribStringOr(player, YELL_TAG_COLOUR, "000000");
            dontAskAgainNightmareStaff = Player.getAttribBooleanOr(player, NIGHTMARE_STAFF_QUESTION, false);
            currencyCollection = Player.getAttribBooleanOr(player, CURRENCY_COLLECTION, false);
            emptyPotionVials = Player.getAttribBooleanOr(player, GIVE_EMPTY_POTION_VIALS, false);
            gold_ags_spec = Player.getAttribBooleanOr(player, AGS_GFX_GOLD, false);
            gold_bgs_spec = Player.getAttribBooleanOr(player, BGS_GFX_GOLD, false);
            gold_sgs_spec = Player.getAttribBooleanOr(player, SGS_GFX_GOLD, false);
            gold_zgs_spec = Player.getAttribBooleanOr(player, ZGS_GFX_GOLD, false);
            xpLocked = Player.getAttribBooleanOr(player, XP_LOCKED, false);
            enableDebugMessages = Player.getAttribBooleanOr(player, DEBUG_MESSAGES, true);
            savePresetLevels = player.getPresetManager().saveLevels();
            openPresetsOnDeath = player.getPresetManager().openOnDeath();
            savedDuelConfig = player.getSavedDuelConfig();
            autoRepairBrokenItems = Player.getAttribBooleanOr(player, REPAIR_BROKEN_ITEMS_ON_DEATH, false);
            votePoints = Player.getAttribIntOr(player, VOTE_POINTS, 0);
            pestControlPoints = Player.getAttribIntOr(player, AttributeKey.PEST_CONTROL_POINTS, 0);
            slayerRewardPoints = Player.getAttribIntOr(player, SLAYER_REWARD_POINTS, 0);
            bossPoints = Player.getAttribIntOr(player, BOSS_POINTS, 0);
            pkPoints = Player.getAttribIntOr(player, PK_POINTS, 0);
            achievementPoints = Player.getAttribIntOr(player, ACHIEVEMENT_POINTS, 0);
            bountyHunterPoints = Player.getAttribIntOr(player, BOUNTY_HUNTER_POINTS, 0);
            hotspotPoints = Player.getAttribIntOr(player, HOTSPOT_POINTS, 0);

            teleportToTargetUnlocked = Player.getAttribBooleanOr(player, BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED, false);
            preserve = Player.getAttribBooleanOr(player, PRESERVE, false);
            rigour = Player.getAttribBooleanOr(player, RIGOUR, false);
            augury = Player.getAttribBooleanOr(player, AUGURY, false);
            kills = Player.getAttribIntOr(player, PLAYER_KILLS, 0);
            deaths = Player.getAttribIntOr(player, PLAYER_DEATHS, 0);
            recentKills = player.getRecentKills();
            firstKillOfTheDay = Player.getAttribLongOr(player, FIRST_KILL_OF_THE_DAY, 0L);
            bountyTasksSkipped = Player.getAttribIntOr(player, AttributeKey.BOUNTY_TASKS_SKIPPED, 0);
            bountyTaskNotifications = Player.getAttribBooleanOr(player, AttributeKey.BOUNTY_HUNTER_TASK_NOTIFICATIONS, true);
            bountyTasksCompleted = Player.getAttribIntOr(player, AttributeKey.BOUNTY_TASKS_COMPLETED, 0);
            bountyHunterTaskCompletionAmount = Player.getAttribIntOr(player, BOUNTY_HUNTER_TASK_COMPLETION_AMOUNT, 0);
            bountyTask = player.getAttribOr(BOUNTY_HUNTER_TASK, null);
            killstreak = Player.getAttribIntOr(player, KILLSTREAK, 0);
            killstreakRecord = Player.getAttribIntOr(player, KILLSTREAK_RECORD, 0);
            kingBlackDragonsKilled = Player.getAttribIntOr(player, KING_BLACK_DRAGONS_KILLED, 0);
            vetionsKilled = Player.getAttribIntOr(player, VETIONS_KILLED, 0);
            crazyArchaeologistsKilled = Player.getAttribIntOr(player, CRAZY_ARCHAEOLOGISTS_KILLED, 0);
            zulrahsKilled = Player.getAttribIntOr(player, ZULRAHS_KILLED, 0);
            alchysKilled = Player.getAttribIntOr(player, ALCHY_KILLED, 0);
            krakensKilled = Player.getAttribIntOr(player, KRAKENS_KILLED, 0);
            revenantsKilled = Player.getAttribIntOr(player, REVENANTS_KILLED, 0);
            ancientRevenantsKilled = Player.getAttribIntOr(player, ANCIENT_REVENANTS_KILLED, 0);
            ancientKingBlackDragonsKilled = Player.getAttribIntOr(player, ANCIENT_KING_BLACK_DRAGONS_KILLED, 0);
            corruptedHunleffsKilled = Player.getAttribIntOr(player, CORRUPTED_HUNLEFFS_KILLED, 0);
            ancientChaosElementalsKilled = Player.getAttribIntOr(player, ANCIENT_CHAOS_ELEMENTALS_KILLED, 0);
            ancientBarrelchestsKilled = Player.getAttribIntOr(player, ANCIENT_BARRELCHESTS_KILLED, 0);
            kerberosKilled = Player.getAttribIntOr(player, KERBEROS_KILLED, 0);
            arachneKilled = Player.getAttribIntOr(player, ARACHNE_KILLED, 0);
            skorpiosKilled = Player.getAttribIntOr(player, SKORPIOS_KILLED, 0);
            artioKilled = Player.getAttribIntOr(player, ARTIO_KILLED, 0);
            jadsKilled = Player.getAttribIntOr(player, JADS_KILLED, 0);
            chaosElementalsKilled = Player.getAttribIntOr(player, CHAOS_ELEMENTALS_KILLED, 0);
            demonicGorillasKilled = Player.getAttribIntOr(player, DEMONIC_GORILLAS_KILLED, 0);
            barrelchestsKilled = Player.getAttribIntOr(player, BARRELCHESTS_KILLED, 0);
            corporealBeastsKilled = Player.getAttribIntOr(player, CORPOREAL_BEASTS_KILLED, 0);
            abyssalSiresKilled = Player.getAttribIntOr(player, CERBERUS_KILLED, 0);
            vorkathsKilled = Player.getAttribIntOr(player, VORKATHS_KILLED, 0);
            lizardmanShamansKilled = Player.getAttribIntOr(player, LIZARDMAN_SHAMANS_KILLED, 0);
            barrowsChestsOpened = Player.getAttribIntOr(player, BARROWS_CHESTS_OPENED, 0);
            corruptedNechryarchsKilled = Player.getAttribIntOr(player, CORRUPTED_NECHRYARCHS_KILLED, 0);
            fluffysKilled = Player.getAttribIntOr(player, FLUFFYS_KILLED, 0);
            dementorsKilled = Player.getAttribIntOr(player, DEMENTORS_KILLED, 0);
            hungarianHorntailsKilled = Player.getAttribIntOr(player, HUNGARIAN_HORNTAILS_KILLED, 0);
            fenrirGreybacksKilled = Player.getAttribIntOr(player, FENRIR_GREYBACKS_KILLED, 0);
            scorpiasKilled = Player.getAttribIntOr(player, SCORPIAS_KILLED, 0);
            callistosKilled = Player.getAttribIntOr(player, CALLISTOS_KILLED, 0);
            molesKilled = Player.getAttribIntOr(player, KC_GIANTMOLE, 0);
            nightmaresKilled = Player.getAttribIntOr(player, THE_NIGHTMARE_KC, 0);
            kreeArrasKilled = Player.getAttribIntOr(player, KREE_ARRA_KILLED, 0);
            zilyanasKilled = Player.getAttribIntOr(player, COMMANDER_ZILYANA_KILLED, 0);
            graardorsKilled = Player.getAttribIntOr(player, GENERAL_GRAARDOR_KILLED, 0);
            krilTsutsarothsKilled = Player.getAttribIntOr(player, KRIL_TSUTSAROTHS_KILLED, 0);
            nexKilled = Player.getAttribIntOr(player, NEX_KC, 0);
            theatreOfBloodRuns = Player.getAttribIntOr(player, THEATRE_OF_BLOOD_RUNS_COMPLETED, 0);
            chamberOfXericRuns = Player.getAttribIntOr(player, CHAMBER_OF_XERIC_RUNS_COMPLETED, 0);
            crystallineHunleffKilled = Player.getAttribIntOr(player, CRYSTALLINE_HUNLEFF_KC, 0);
            fragmentOfSerenKilled = Player.getAttribIntOr(player, FRAGMENT_OF_SEREN_KC, 0);
            bloodFuryHesporiKilled = Player.getAttribIntOr(player, BLOOD_FURY_HESPORI_KC, 0);
            infernalSpiderKilled = Player.getAttribIntOr(player, INFERNAL_SPIDER_KC, 0);
            enragedGorillasKilled = Player.getAttribIntOr(player, ENRAGED_GORILLA_KILLS, 0);
            chaoticNightmaresKilled = Player.getAttribIntOr(player, CHAOTIC_NIGHTMARE_KILLS, 0);
            rexKilled = Player.getAttribIntOr(player, KC_REX, 0);
            primeKilled = Player.getAttribIntOr(player, KC_PRIME, 0);
            supremeKilled = Player.getAttribIntOr(player, KC_SUPREME, 0);
            kalphiteQueensKilled = Player.getAttribIntOr(player, KC_KQ, 0);
            lavaDragonsKilled = Player.getAttribIntOr(player, LAVA_DRAGONS_KILLED, 0);
            skotizosKilled = Player.getAttribIntOr(player, SKOTIZOS_KILLED, 0);
            zombieChampionsKilled = Player.getAttribIntOr(player, ZOMBIES_CHAMPIONS_KILLED, 0);
            brutalLavaDragonsKilled = Player.getAttribIntOr(player, BRUTAL_LAVA_DRAGONS_KILLED, 0);
            tektonsKilled = Player.getAttribIntOr(player, TEKTONS_KILLED, 0);
            chaosFanaticsKilled = Player.getAttribIntOr(player, CHAOS_FANATICS_KILLED, 0);
            thermonuclearSmokeDevilKilled = Player.getAttribIntOr(player, THERMONUCLEAR_SMOKE_DEVILS_KILLED, 0);
            venenatisKilled = Player.getAttribIntOr(player, VENENATIS_KILLED, 0);
            aragogKC = Player.getAttribIntOr(player, KC_ARAGOG, 0);
            chamberOfSecretRuns = Player.getAttribIntOr(player, CHAMBER_OF_SECRET_RUNS_COMPLETED, 0);
            smokeDevilKills = Player.getAttribIntOr(player, KC_SMOKEDEVIL, 0);
            superiorCreatureKills = Player.getAttribIntOr(player, SUPERIOR, 0);
            crawlingHandKills = Player.getAttribIntOr(player, KC_CRAWL_HAND, 0);
            caveBugKills = Player.getAttribIntOr(player, KC_CAVE_BUG, 0);
            caveCrawlerKills = Player.getAttribIntOr(player, KC_CAVE_CRAWLER, 0);
            bansheeKills = Player.getAttribIntOr(player, KC_BANSHEE, 0);
            caveSlimeKills = Player.getAttribIntOr(player, KC_CAVE_SLIME, 0);
            rockslugKills = Player.getAttribIntOr(player, KC_ROCKSLUG, 0);
            desertLizardKills = Player.getAttribIntOr(player, KC_DESERT_LIZARD, 0);
            cockatriceKills = Player.getAttribIntOr(player, KC_COCKATRICE, 0);
            pyrefiendKills = Player.getAttribIntOr(player, KC_PYREFRIEND, 0);
            mogreKills = Player.getAttribIntOr(player, KC_MOGRE, 0);
            harpieBugSwarmKills = Player.getAttribIntOr(player, KC_HARPIE_BUG, 0);
            wallBeastKills = Player.getAttribIntOr(player, KC_WALL_BEAST, 0);
            killerwattKills = Player.getAttribIntOr(player, KC_KILLERWATT, 0);
            molaniskKills = Player.getAttribIntOr(player, KC_MOLANISK, 0);
            basiliskKills = Player.getAttribIntOr(player, KC_BASILISK, 0);
            seaSnakeKills = Player.getAttribIntOr(player, KC_SEASNAKE, 0);
            terrorDogKills = Player.getAttribIntOr(player, KC_TERRORDOG, 0);
            feverSpiderKills = Player.getAttribIntOr(player, KC_FEVER_SPIDER, 0);
            infernalMageKills = Player.getAttribIntOr(player, KC_INFERNAL_MAGE, 0);
            brineRatKills = Player.getAttribIntOr(player, KC_BRINERAT, 0);
            bloodveldKills = Player.getAttribIntOr(player, KC_BLOODVELD, 0);
            jellyKills = Player.getAttribIntOr(player, KC_JELLY, 0);
            turothKills = Player.getAttribIntOr(player, KC_TUROTH, 0);
            zygomiteKills = Player.getAttribIntOr(player, KC_ZYGOMITE, 0);
            caveHorrorKills = Player.getAttribIntOr(player, KC_CAVEHORROR, 0);
            aberrantSpectreKills = Player.getAttribIntOr(player, KC_ABERRANT_SPECTRE, 0);
            spiritualWarriorKills = Player.getAttribIntOr(player, KC_SPIRITUAL_WARRIOR, 0);
            kuraskKills = Player.getAttribIntOr(player, KC_KURASK, 0);
            skeletalWyvernKills = Player.getAttribIntOr(player, KC_SKELETAL_WYVERN, 0);
            gargoyleKills = Player.getAttribIntOr(player, KC_GARGOYLE, 0);
            nechryaelKills = Player.getAttribIntOr(player, KC_NECHRYAEL, 0);
            spiritualMageKills = Player.getAttribIntOr(player, KC_SPIRITUAL_MAGE, 0);
            abyssalDemonKills = Player.getAttribIntOr(player, KC_ABYSSALDEMON, 0);
            caveKrakenKills = Player.getAttribIntOr(player, KC_CAVEKRAKEN, 0);
            darkBeastKills = Player.getAttribIntOr(player, KC_DARKBEAST, 0);
            brutalBlackDragonKills = Player.getAttribIntOr(player, BRUTAL_BLACK_DRAGON, 0);
            fossilIslandWyvernKills = Player.getAttribIntOr(player, FOSSIL_WYVERN, 0);
            wyrmKills = Player.getAttribIntOr(player, WYRM, 0);
            drakeKills = Player.getAttribIntOr(player, DRAKE, 0);
            hydraKills = Player.getAttribIntOr(player, HYDRA, 0);
            basiliskKnightKills = Player.getAttribIntOr(player, BASILISK_KNIGHT, 0);
            bossTimers = player.getBossTimers().getTimes();
            eventRewards = player.getEventRewards().rewardsUnlocked();
            favoriteTeleports = player.getFavoriteTeleports();
            collectionLog = player.getCollectionLog().collectionLog;
            achievements = player.achievements();
            achievementsCompleted = Player.getAttribIntOr(player, ACHIEVEMENTS_COMPLETED, 0);
            task = player.getAttribOr(BOTTLE_TASK, null);
            taskAmount = Player.getAttribIntOr(player, BOTTLE_TASK_AMOUNT, 0);
            taskCompletionAmount = Player.getAttribIntOr(player, TASK_COMPLETE_AMOUNT, 0);
            totalTasksCompleted = Player.getAttribIntOr(player, TASKS_COMPLETED, 0);
            canClaimTaskReward = Player.getAttribBooleanOr(player, CAN_CLAIM_TASK_REWARD, false);
            treasuresOpened = Player.getAttribIntOr(player, TREASURE_CHESTS_OPENED, 0);
            referalMilestone10hoursPassed = Player.getAttribBooleanOr(player, REFERRAL_MILESTONE_10HOURS, false);
            referalMilestone1dayPassed = Player.getAttribBooleanOr(player, REFERRAL_MILESTONE_1_DAY, false);
            referrerUsername = Player.getAttribStringOr(player, REFERRER_USERNAME, "");
            referralsCount = Player.getAttribIntOr(player, REFERRALS_COUNT, 0);
            databaseId = Player.getAttribIntOr(player, DATABASE_PLAYER_ID, -1);
            referalMilestone3refs = Player.getAttribBooleanOr(player, REFERRAL_MILESTONE_THREE_REFERRALS, false);
            staminaTicks = Player.getAttribIntOr(player, STAMINA_POTION_TICKS, 0);
            overloadTicks = Player.getAttribIntOr(player, OVERLOAD_POTION, 0);
            antifireTicks = Player.getAttribIntOr(player, ANTIFIRE_POTION, 0);
            superAntiFire = Player.getAttribBooleanOr(player, SUPER_ANTIFIRE_POTION, false);
            larranKeysUsed = Player.getAttribIntOr(player, LARRANS_KEYS_OPENED, 0);
            brimstoneKeysOpened = Player.getAttribIntOr(player, BRIMSTONE_KEYS_OPENED, 0);
            collectionLogKeysOpened = Player.getAttribIntOr(player, COLLECTION_LOG_KEYS_OPENED, 0);
            wildernessKeysOpened = Player.getAttribIntOr(player, WILDY_KEYS_OPENED, 0);
            slayerKeysOpened = Player.getAttribIntOr(player, SLAYER_KEYS_OPENED, 0);
            slayerKeysReceived = Player.getAttribIntOr(player, SLAYER_KEYS_RECEIVED, 0);
            doubleExpTicks = Player.getAttribIntOr(player, DOUBLE_EXP_TICKS, 0);
            dropRateLampTicks = Player.getAttribIntOr(player, DOUBLE_DROP_LAMP_TICKS, 0);
            ethereumAbsorption = Player.getAttribBooleanOr(player, ETHEREUM_ABSORPTION, false);
            jailed = Player.getAttribIntOr(player, JAILED, 0);
            jailOresToEscape = Player.getAttribIntOr(player, JAIL_ORES_TO_ESCAPE, 0);
            jailOresMined = Player.getAttribIntOr(player, JAIL_ORES_MINED, 0);
            locBeforeJail = player.getAttribOr(LOC_BEFORE_JAIL, new Tile(3092, 3500));
            tournamentWins = Player.getAttribIntOr(player, TOURNAMENT_WINS, 0);
            tournamentPoints = Player.getAttribIntOr(player, TOURNAMENT_POINTS, 0);
            lostCannon = Player.getAttribBooleanOr(player, LOST_CANNON, false);
            wildernessCourseState = Player.getAttribIntOr(player, WILDY_COURSE_STATE, 0);
            dailyRockCrabsAmount = Player.getAttribIntOr(player, ROCK_CRABS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyRockCrabsCompleted = Player.getAttribBooleanOr(player, ROCK_CRABS_DAILY_TASK_COMPLETED, false);
            dailyRockCrabsRewardClaimed = Player.getAttribBooleanOr(player, ROCK_CRABS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyExperimentsAmount = Player.getAttribIntOr(player, EXPERIMENTS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyExperimentsCompleted = Player.getAttribBooleanOr(player, EXPERIMENTS_DAILY_TASK_COMPLETED, false);
            dailyExperimentsRewardClaimed = Player.getAttribBooleanOr(player, EXPERIMENTS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyThievingAmount = Player.getAttribIntOr(player, THIEVING_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyThievingCompleted = Player.getAttribBooleanOr(player, THIEVING_DAILY_TASK_COMPLETED, false);
            dailyThievingRewardClaimed = Player.getAttribBooleanOr(player, THIEVING_DAILY_TASK_REWARD_CLAIMED, false);
            dailyVotingAmount = Player.getAttribIntOr(player, VOTING_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyVotingCompleted = Player.getAttribBooleanOr(player, VOTING_DAILY_TASK_COMPLETED, false);
            dailyVotingRewardClaimed = Player.getAttribBooleanOr(player, VOTING_DAILY_TASK_REWARD_CLAIMED, false);
            dailySkillingAmount = Player.getAttribIntOr(player, SKILLING_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailySkillingCompleted = Player.getAttribBooleanOr(player, SKILLING_DAILY_TASK_COMPLETED, false);
            dailySkillingRewardClaimed = Player.getAttribBooleanOr(player, SKILLING_DAILY_TASK_REWARD_CLAIMED, false);
            dailyPvmingAmount = Player.getAttribIntOr(player, PVMING_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyPvmingCompleted = Player.getAttribBooleanOr(player, PVMING_DAILY_TASK_COMPLETED, false);
            dailyPvmingRewardClaimed = Player.getAttribBooleanOr(player, PVMING_DAILY_TASK_REWARD_CLAIMED, false);
            dailyBarrowsAmount = Player.getAttribIntOr(player, BARROWS_TASK_COMPLETION_AMOUNT, 0);
            dailyBarrowsCompleted = Player.getAttribBooleanOr(player, BARROWS_DAILY_TASK_COMPLETED, false);
            dailyBarrowsRewardClaimed = Player.getAttribBooleanOr(player, BARROWS_DAILY_TASK_REWARD_CLAIMED, false);
            dailySlayerAmount = Player.getAttribIntOr(player, SLAYER_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailySlayerCompleted = Player.getAttribBooleanOr(player, SLAYER_DAILY_TASK_COMPLETED, false);
            dailySlayerRewardClaimed = Player.getAttribBooleanOr(player, SLAYER_DAILY_TASK_REWARD_CLAIMED, false);
            dailyGreenDragonsAmount = Player.getAttribIntOr(player, GREEN_DRAGONS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyGreenDragonsCompleted = Player.getAttribBooleanOr(player, GREEN_DRAGONS_DAILY_TASK_COMPLETED, false);
            dailyGreenDragonsRewardClaimed = Player.getAttribBooleanOr(player, GREEN_DRAGONS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyImplingAmount = Player.getAttribIntOr(player, IMPLING_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyImplingCompleted = Player.getAttribBooleanOr(player, IMPLING_DAILY_TASK_COMPLETED, false);
            dailyImplingRewardClaimed = Player.getAttribBooleanOr(player, IMPLING_DAILY_TASK_REWARD_CLAIMED, false);
            dailyRaidsAmount = Player.getAttribIntOr(player, RAIDS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyRaidsCompleted = Player.getAttribBooleanOr(player, RAIDS_DAILY_TASK_COMPLETED, false);
            dailyRaidsRewardClaimed = Player.getAttribBooleanOr(player, RAIDS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyZulrahAmount = Player.getAttribIntOr(player, ZULRAH_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyZulrahCompleted = Player.getAttribBooleanOr(player, ZULRAH_DAILY_TASK_COMPLETED, false);
            dailyZulrahRewardClaimed = Player.getAttribBooleanOr(player, ZULRAH_DAILY_TASK_REWARD_CLAIMED, false);
            dailyCorruptedNechryarchAmount = Player.getAttribIntOr(player, CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyCorruptedNechryarchCompleted = Player.getAttribBooleanOr(player, CORRUPTED_NECHRYARCH_DAILY_TASK_COMPLETED, false);
            dailyCorruptedNechryarchRewardClaimed = Player.getAttribBooleanOr(player, CORRUPTED_NECHRYARCH_DAILY_TASK_REWARD_CLAIMED, false);
            dailyValinorCoinsAmount = Player.getAttribIntOr(player, VALINOR_COINS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyValinorCoinsCompleted = Player.getAttribBooleanOr(player, VALINOR_COINS_DAILY_TASK_COMPLETED, false);
            dailyValinorCoinsRewardClaimed = Player.getAttribBooleanOr(player, VALINOR_COINS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyTournyAmount = Player.getAttribIntOr(player, TOURNY_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyTournyCompleted = Player.getAttribBooleanOr(player, TOURNY_DAILY_TASK_COMPLETED, false);
            dailyTournyRewardClaimed = Player.getAttribBooleanOr(player, TOURNY_DAILY_TASK_REWARD_CLAIMED, false);
            dailyStardustAmount = Player.getAttribIntOr(player, STARDUST_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyStardustCompleted = Player.getAttribBooleanOr(player, STARDUST_DAILY_TASK_COMPLETED, false);
            dailyStardustRewardClaimed = Player.getAttribBooleanOr(player, STARDUST_DAILY_TASK_REWARD_CLAIMED, false);
            dailyWorldBossAmount = Player.getAttribIntOr(player, WORLD_BOSS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyWorldBossCompleted = Player.getAttribBooleanOr(player, WORLD_BOSS_DAILY_TASK_COMPLETED, false);
            dailyWorldBossRewardClaimed = Player.getAttribBooleanOr(player, WORLD_BOSS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyRevenantsAmount = Player.getAttribIntOr(player, REVENANTS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyRevenantsCompleted = Player.getAttribBooleanOr(player, REVENANTS_DAILY_TASK_COMPLETED, false);
            dailyRevenantsRewardClaimed = Player.getAttribBooleanOr(player, REVENANTS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyKrakensAmount = Player.getAttribIntOr(player, KRAKEN_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyKrakensCompleted = Player.getAttribBooleanOr(player, KRAKEN_DAILY_TASK_COMPLETED, false);
            dailyKrakensRewardClaimed = Player.getAttribBooleanOr(player, KRAKEN_DAILY_TASK_REWARD_CLAIMED, false);
            dailyBattleMagesAmount = Player.getAttribIntOr(player, BATTLE_MAGE_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyBattleMagesCompleted = Player.getAttribBooleanOr(player, BATTLE_MAGE_DAILY_TASK_COMPLETED, false);
            dailyBattleMagesRewardClaimed = Player.getAttribBooleanOr(player, BATTLE_MAGE_DAILY_TASK_REWARD_CLAIMED, false);
            dailyNexAmount = Player.getAttribIntOr(player, NEX_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyNexCompleted = Player.getAttribBooleanOr(player, NEX_DAILY_TASK_COMPLETED, false);
            dailyNexRewardClaimed = Player.getAttribBooleanOr(player, NEX_DAILY_TASK_REWARD_CLAIMED, false);
            dailyDemonicGorillasAmount = Player.getAttribIntOr(player, DEMONIC_GORILLA_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyDemonicGorillasCompleted = Player.getAttribBooleanOr(player, DEMONIC_GORILLA_DAILY_TASK_COMPLETED, false);
            dailyDemonicGorillasRewardClaimed = Player.getAttribBooleanOr(player, DEMONIC_GORILLA_DAILY_TASK_REWARD_CLAIMED, false);
            dailyWinPvPTournyAmount = Player.getAttribIntOr(player, WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyWinPvPTournyCompleted = Player.getAttribBooleanOr(player, WIN_PVP_TOURNAMENT_DAILY_TASK_COMPLETED, false);
            dailyWinPvPTournyRewardClaimed = Player.getAttribBooleanOr(player, WIN_PVP_TOURNAMENT_DAILY_TASK_REWARD_CLAIMED, false);
            dailyWildykeyEscapeAmount = Player.getAttribIntOr(player, WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyWildykeyEscapeCompleted = Player.getAttribBooleanOr(player, WILDY_KEY_ESCAPE_DAILY_TASK_COMPLETED, false);
            dailyWildykeyEscapeRewardClaimed = Player.getAttribBooleanOr(player, WILDY_KEY_ESCAPE_DAILY_TASK_REWARD_CLAIMED, false);
            dailyKillPlayersRevCaveAmount = Player.getAttribIntOr(player, KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyKillPlayersRevCaveCompleted = Player.getAttribBooleanOr(player, KILL_PLAYERS_REV_CAVE_DAILY_TASK_COMPLETED, false);
            dailyKillPlayersRevCaveRewardClaimed = Player.getAttribBooleanOr(player, KILL_PLAYERS_REV_CAVE_DAILY_TASK_REWARD_CLAIMED, false);
            dailyDeepWildAmount = Player.getAttribIntOr(player, DEEP_WILD_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyDeepWildCompleted = Player.getAttribBooleanOr(player, DEEP_WILD_DAILY_TASK_COMPLETED, false);
            dailyDeepWildRewardClaimed = Player.getAttribBooleanOr(player, DEEP_WILD_DAILY_TASK_REWARD_CLAIMED, false);
            dailyKillstreakAmount = Player.getAttribIntOr(player, KILLSTREAK_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyKillstreakCompleted = Player.getAttribBooleanOr(player, KILLSTREAK_DAILY_TASK_COMPLETED, false);
            dailyKillstreakRewardClaimed = Player.getAttribBooleanOr(player, KILLSTREAK_DAILY_TASK_REWARD_CLAIMED, false);
            dailyEdgevilleKillsAmount = Player.getAttribIntOr(player, EDGEVILLE_KILLS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyEdgevilleKillsCompleted = Player.getAttribBooleanOr(player, EDGEVILLE_KILLS_DAILY_TASK_COMPLETED, false);
            dailyEdgevilleKillsRewardClaimed = Player.getAttribBooleanOr(player, EDGEVILLE_KILLS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyMageBankKillsAmount = Player.getAttribIntOr(player, MAGE_BANK_KILLS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyMageBankKillsCompleted = Player.getAttribBooleanOr(player, MAGE_BANK_KILLS_DAILY_TASK_COMPLETED, false);
            dailyMageBankKillsRewardClaimed = Player.getAttribBooleanOr(player, MAGE_BANK_KILLS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyMemberCaveKillsAmount = Player.getAttribIntOr(player, MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyMemberCaveKillsCompleted = Player.getAttribBooleanOr(player, MEMBER_CAVE_KILLS_DAILY_TASK_COMPLETED, false);
            dailyMemberCaveKillsRewardClaimed = Player.getAttribBooleanOr(player, MEMBER_CAVE_KILLS_DAILY_TASK_REWARD_CLAIMED, false);
            dailyElderChaosDruidKillsAmount = Player.getAttribIntOr(player, ELDER_CHAOS_DRUID_KILLS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyElderChaosDruidKillsCompleted = Player.getAttribBooleanOr(player, ELDER_CHAOS_DRUIDS_KILLS_DAILY_TASK_COMPLETED, false);
            dailyElderChaosDruidKillsRewardClaimed = Player.getAttribBooleanOr(player, ELDER_CHAOS_DRUIDS_KILLS_DAILY_TASK_REWARD_CLAIMED, false);
            alchemicalHydraLogClaimed = Player.getAttribBooleanOr(player, ALCHEMICAL_HYDRA_LOG_CLAIMED, false);
            ancientBarrelchestLogClaimed = Player.getAttribBooleanOr(player, ANCIENT_BARRELCHEST_LOG_CLAIMED, false);
            ancientChaosElementalLogClaimed = Player.getAttribBooleanOr(player, ANCIENT_CHAOS_ELEMENTAL_LOG_CLAIMED, false);
            ancientKingBlackDragonLogClaimed = Player.getAttribBooleanOr(player, ANCIENT_KING_BLACK_DRAGON_LOG_CLAIMED, false);
            arachneLogClaimed = Player.getAttribBooleanOr(player, ARACHNE_LOG_CLAIMED, false);
            artioLogClaimed = Player.getAttribBooleanOr(player, ARTIO_LOG_CLAIMED, false);
            barrelchestLogClaimed = Player.getAttribBooleanOr(player, BARRELCHEST_LOG_CLAIMED, false);
            brutalLavaDragonLogClaimed = Player.getAttribBooleanOr(player, BRUTAL_LAVA_DRAGON_LOG_CLAIMED, false);
            callistoLogClaimed = Player.getAttribBooleanOr(player, CALLISTO_LOG_CLAIMED, false);
            cerberusLogClaimed = Player.getAttribBooleanOr(player, CERBERUS_LOG_CLAIMED, false);
            chaosElementalLogClaimed = Player.getAttribBooleanOr(player, CHAOS_ELEMENTAL_LOG_CLAIMED, false);
            chaosFanaticLogClaimed = Player.getAttribBooleanOr(player, CHAOS_FANATIC_LOG_CLAIMED, false);
            corporealBeastLogClaimed = Player.getAttribBooleanOr(player, CORPOREAL_BEAST_LOG_CLAIMED, false);
            corruptedNechryarchLogClaimed = Player.getAttribBooleanOr(player, CORRUPTED_NECHRYARCH_LOG_CLAIMED, false);
            crazyArchaeologistLogClaimed = Player.getAttribBooleanOr(player, CRAZY_ARCHAEOLOGIST_LOG_CLAIMED, false);
            demonicGorillaLogClaimed = Player.getAttribBooleanOr(player, DEMONIC_GORILLA_LOG_CLAIMED, false);
            giantMoleLogClaimed = Player.getAttribBooleanOr(player, GIANT_MOLE_LOG_CLAIMED, false);
            kreeArraLogClaimed = Player.getAttribBooleanOr(player, KREEARRA_LOG_CLAIMED, false);
            generalGraardorLogClaimed = Player.getAttribBooleanOr(player, GENERAL_GRAARDOR_LOG_CLAIMED, false);
            commanderZilyanaLogClaimed = Player.getAttribBooleanOr(player, COMMANDER_ZILYANA_LOG_CLAIMED, false);
            krilTsutsarothLogClaimed = Player.getAttribBooleanOr(player, KRIL_TSUTSAROTH_LOG_CLAIMED, false);
            abyssalSireLogClaimed = Player.getAttribBooleanOr(player, ABYSSALSIRE_LOG_CLAIMED, false);
            barrowsLogClaimed = Player.getAttribBooleanOr(player, BARROWS_LOG_CLAIMED, false);
            dagannothKingsLogClaimed = Player.getAttribBooleanOr(player, DAGANNOTH_KINGS_LOG_CLAIMED, false);
            kalphiteQueenLogClaimed = Player.getAttribBooleanOr(player, KALPHITE_QUEEN_LOG_CLAIMED, false);
            nexLogClaimed = Player.getAttribBooleanOr(player, NEX_LOG_CLAIMED, false);
            larransKeyLogClaimed = Player.getAttribBooleanOr(player, LARRANS_KEY_LOG_CLAIMED, false);
            crystalKeyLogClaimed = Player.getAttribBooleanOr(player, CRYSTAL_KEY_LOG_CLAIMED, false);
            slayerKeyLogClaimed = Player.getAttribBooleanOr(player, SLAYER_KEY_LOG_CLAIMED, false);
            brimstoneKeyLogClaimed = Player.getAttribBooleanOr(player, BRIMSTONE_KEY_LOG_CLAIMED, false);
            collectionKeyLogClaimed = Player.getAttribBooleanOr(player, COLLECTION_KEY_LOG_CLAIMED, false);
            wildernessKeyLogClaimed = Player.getAttribBooleanOr(player, WILDERNESS_KEY_LOG_CLAIMED, false);
            kerberosLogClaimed = Player.getAttribBooleanOr(player, KERBEROS_LOG_CLAIMED, false);
            kingBlackDragonLogClaimed = Player.getAttribBooleanOr(player, KING_BLACK_DRAGON_LOG_CLAIMED, false);
            krakenLogClaimed = Player.getAttribBooleanOr(player, KRAKEN_LOG_CLAIMED, false);
            lavaDragonLogClaimed = Player.getAttribBooleanOr(player, LAVA_DRAGON_LOG_CLAIMED, false);
            lizardmanShamanLogClaimed = Player.getAttribBooleanOr(player, LIZARDMAN_SHAMAN_LOG_CLAIMED, false);
            scorpiaLogClaimed = Player.getAttribBooleanOr(player, SCORPIA_LOG_CLAIMED, false);
            skorpiosLogClaimed = Player.getAttribBooleanOr(player, SKORPIOS_LOG_CLAIMED, false);
            skotizoLogClaimed = Player.getAttribBooleanOr(player, SKOTIZO_LOG_CLAIMED, false);
            tektonLogClaimed = Player.getAttribBooleanOr(player, TEKTON_LOG_CLAIMED, false);
            thermonuclearSmokeDevilLogClaimed = Player.getAttribBooleanOr(player, THERMONUCLEAR_SMOKE_DEVIL_LOG_CLAIMED, false);
            theNightmareLogClaimed = Player.getAttribBooleanOr(player, THE_NIGTHMARE_LOG_CLAIMED, false);
            corruptedHunleffLogClaimed = Player.getAttribBooleanOr(player, CORRUPTED_HUNLEFF_LOG_CLAIMED, false);
            menInBlackLogClaimed = Player.getAttribBooleanOr(player, MEN_IN_BLACK_LOG_CLAIMED, false);
            tztokJadLogClaimed = Player.getAttribBooleanOr(player, TZTOK_JAD_LOG_CLAIMED, false);
            venenatisLogClaimed = Player.getAttribBooleanOr(player, VENENATIS_LOG_CLAIMED, false);
            vetionLogClaimed = Player.getAttribBooleanOr(player, VETION_LOG_CLAIMED, false);
            vorkathLogClaimed = Player.getAttribBooleanOr(player, VORKATH_LOG_CLAIMED, false);
            zombiesChampionLogClaimed = Player.getAttribBooleanOr(player, ZOMBIES_CHAMPION_LOG_CLAIMED, false);
            zulrahLogClaimed = Player.getAttribBooleanOr(player, ZULRAH_LOG_CLAIMED, false);
            ancientRevenantsLogClaimed = Player.getAttribBooleanOr(player, ANCIENT_REVENANTS_LOG_CLAIMED, false);
            chamberOfSecretsLogClaimed = Player.getAttribBooleanOr(player, CHAMBER_OF_SECRETS_LOG_CLAIMED, false);
            revenantsLogClaimed = Player.getAttribBooleanOr(player, REVENANTS_LOG_CLAIMED, false);
            chamberOfXericLogClaimed = Player.getAttribBooleanOr(player, CHAMBER_OF_XERIC_LOG_CLAIMED, false);
            theatreOfBloodLogClaimed = Player.getAttribBooleanOr(player, THEATRE_OF_BLOOD_LOG_CLAIMED, false);
            crystallineHunleffLogClaimed = Player.getAttribBooleanOr(player, CRYSTALLINE_HUNLLEF_LOG_CLAIMED, false);
            fragmentOfSerenLogClaimed = Player.getAttribBooleanOr(player, FRAGMENT_OF_SEREN_LOG_CLAIMED, false);
            bloodFurHesporiLogClaimed = Player.getAttribBooleanOr(player, BLOOD_FURY_HESPORI_LOG_CLAIMED, false);
            infernalSpiderLogClaimed = Player.getAttribBooleanOr(player, INFERNAL_SPIDER_LOG_CLAIMED, false);
            donatorMysteryBoxLogClaimed = Player.getAttribBooleanOr(player, DONATOR_MYSTERY_BOX_LOG_CLAIMED, false);
            armourMysteryBoxLogClaimed = Player.getAttribBooleanOr(player, ARMOUR_MYSTERY_BOX_LOG_CLAIMED, false);
            weaponMysteryBoxLogClaimed = Player.getAttribBooleanOr(player, WEAPON_MYSTERY_BOX_LOG_CLAIMED, false);
            legendaryMysteryBoxLogClaimed = Player.getAttribBooleanOr(player, LEGENDARY_MYSTERY_BOX_LOG_CLAIMED, false);
            mysteryTicketLogClaimed = Player.getAttribBooleanOr(player, MYSTERY_TICKET_LOG_CLAIMED, false);
            mysteryChestLogClaimed = Player.getAttribBooleanOr(player, MYSTERY_CHEST_LOG_CLAIMED, false);
            petsMysteryLogClaimed = Player.getAttribBooleanOr(player, PETS_MYSTERY_BOX_LOG_CLAIMED, false);
            sarachnisLogClaimed = Player.getAttribBooleanOr(player, SARACHNIS_LOG_CLAIMED, false);
            bryophytaLogClaimed = Player.getAttribBooleanOr(player, BRYOPHYTA_LOG_CLAIMED, false);
            slayerLogClaimed = Player.getAttribBooleanOr(player, SLAYER_LOG_CLAIMED, false);
            enragedGorillaLogClaimed = Player.getAttribBooleanOr(player, ENRAGED_GORILLA_LOG_CLAIMED, false);
            chaoticNightmareLogClaimed = Player.getAttribBooleanOr(player, CHAOTIC_NIGHTMARE_LOG_CLAIMED, false);
            lastDailyReset = Player.getAttribIntOr(player, LAST_DAILY_RESET, -1);
            finishedHalloweenDialogue = Player.getAttribBooleanOr(player, FINISHED_HALLOWEEN_TEACHER_DIALOGUE, false);
            candiesTraded = Player.getAttribIntOr(player, CANDIES_TRADED, -1);
            hweenEventTokensSpent = Player.getAttribIntOr(player, HWEEN_EVENT_TOKENS_SPENT, 0);
            winterEventTokensSpent = Player.getAttribIntOr(player, WINTER_EVENT_TOKENS_SPENT, 0);
            herbBoxCharges = Player.getAttribIntOr(player, HERB_BOX_CHARGES, 0);
            combatMaxed = Player.getAttribBooleanOr(player, COMBAT_MAXED, false);
            fightCaveWave = Player.getAttribIntOr(player, FIGHT_CAVES_WAVE, 0);
            kylieMinnowDialogueStarted = Player.getAttribBooleanOr(player, KYLIE_MINNOW_DIALOGUE_STARTED, false);
            baggedCoal = Player.getAttribIntOr(player, BAGGED_COAL, 0);
            expLampWarningSent = Player.getAttribBooleanOr(player, EXP_LAMP_WARNING_SENT, false);
            expLampUsed = Player.getAttribBooleanOr(player, EXP_LAMP_USED, false);
            swiftAbility = Player.getAttribBooleanOr(player, SWIFT_ABILITY, false);
            goliathAbility = Player.getAttribBooleanOr(player, GOLIATH_ABILITY, false);
            weaponUpgrades = Player.getAttribIntOr(player, WEAPON_UPGRADES, 0);
            ddsKills = Player.getAttribIntOr(player, DDS_KILLS, 0);
            gmaulKills = Player.getAttribIntOr(player, GMAUL_KILLS, 0);
            whipKills = Player.getAttribIntOr(player, WHIP_KILLS, 0);
            dScimKills = Player.getAttribIntOr(player, D_SCIM_KILLS, 0);
            staffOfLightKills = Player.getAttribIntOr(player, STAFF_OF_LIGHT_KILLS, 0);
            runeCBowKills = Player.getAttribIntOr(player, RUNE_C_BOW_KILLS, 0);
            dMaceKills = Player.getAttribIntOr(player, D_MACE_KILLS, 0);
            dLongKills = Player.getAttribIntOr(player, D_LONG_KILLS, 0);
            msbKills = Player.getAttribIntOr(player, MSB_KILLS, 0);
            dbowKills = Player.getAttribIntOr(player, DBOW_KILLS, 0);
            ddsKillsTier = Player.getAttribIntOr(player, DDS_KILLS_TIER_UNLOCKED, 0);
            gmaulKillsTier = Player.getAttribIntOr(player, GMAUL_KILLS_TIER_UNLOCKED, 0);
            whipKillsTier = Player.getAttribIntOr(player, WHIP_KILLS_TIER_UNLOCKED, 0);
            dScimKillsTier = Player.getAttribIntOr(player, D_SCIM_KILLS_TIER_UNLOCKED, 0);
            staffOfLightKillsTier = Player.getAttribIntOr(player, STAFF_OF_LIGHT_KILLS_TIER_UNLOCKED, 0);
            runeCBowKillsTier = Player.getAttribIntOr(player, RUNE_C_BOW_KILLS_TIER_UNLOCKED, 0);
            dMaceKillsTier = Player.getAttribIntOr(player, D_MACE_KILLS_TIER_UNLOCKED, 0);
            dLongKillsTier = Player.getAttribIntOr(player, D_LONG_KILLS_TIER_UNLOCKED, 0);
            msbKillsTier = Player.getAttribIntOr(player, MSB_KILLS_TIER_UNLOCKED, 0);
            dbowKillsTier = Player.getAttribIntOr(player, DBOW_KILLS_TIER_UNLOCKED, 0);
            armourMysteryBoxesOpened = Player.getAttribIntOr(player, ARMOUR_MYSTERY_BOXES_OPENED, 0);
            donatorMysteryBoxesOpened = Player.getAttribIntOr(player, DONATOR_MYSTERY_BOXES_OPENED, 0);
            legendaryMysteryBoxesOpened = Player.getAttribIntOr(player, LEGENDARY_MYSTERY_BOXES_OPENED, 0);
            mysteryChestsOpened = Player.getAttribIntOr(player, MYSTERY_CHESTS_OPENED, 0);
            mysteryTicketsOpened = Player.getAttribIntOr(player, MYSTERY_TICKETS_OPENED, 0);
            petsMysteryBoxesOpened = Player.getAttribIntOr(player, PETS_MYSTERY_BOXES_OPENED, 0);
            weaponMysteryBoxesOpened = Player.getAttribIntOr(player, WEAPON_MYSTERY_BOXES_OPENED, 0);
            starterWeaponDamage = Player.getAttribIntOr(player, STARTER_WEAPON_DAMAGE,0);
            ancientVLSAttempts = Player.getAttribIntOr(player, ANCIENT_VLS_ATTEMPTS, 0);
            ancientSWHAttempts = Player.getAttribIntOr(player, ANCIENT_SWH_ATTEMPTS, 0);
            enchantedAGSAttempts = Player.getAttribIntOr(player, ARMADYL_GODSWORD_OR_ATTEMPTS, 0);
            enchantedBGSAttempts = Player.getAttribIntOr(player, BANDOS_GODSWORD_OR_ATTEMPTS, 0);
            enchantedSGSAttempts = Player.getAttribIntOr(player, SARADOMIN_GODSWORD_OR_ATTEMPTS, 0);
            enchantedZGSAttempts = Player.getAttribIntOr(player, ZAMORAK_GODSWORD_OR_ATTEMPTS, 0);
            enchantedFuryAttempts = Player.getAttribIntOr(player, FURY_OR_ATTEMPTS, 0);
            enchantedOccultAttempts = Player.getAttribIntOr(player, OCCULT_OR_ATTEMPTS, 0);
            enchantedTortureAttempts = Player.getAttribIntOr(player, TORTURE_OR_ATTEMPTS, 0);
            enchantedAnguishAttempts = Player.getAttribIntOr(player, ANGUISH_OR_ATTEMPTS, 0);
            enchantedBNeckAttempts = Player.getAttribIntOr(player, BERSERKER_NECKLACE_OR_ATTEMPTS, 0);
            enchantedGmaulAttempts = Player.getAttribIntOr(player, GRANITE_MAUL_OR_ATTEMPTS, 0);
            enchantedTBraceAttempts = Player.getAttribIntOr(player, TORMENTED_BRACELET_OR_ATTEMPTS, 0);
            enchantedRunePouchAttempts = Player.getAttribIntOr(player, RUNE_POUCH_I_ATTEMPTS, 0);
            enchantedDClawsAttempts = Player.getAttribIntOr(player, DRAGON_CLAWS_OR_ATTEMPTS, 0);
            blowpipeAttempts = Player.getAttribIntOr(player, MAGMA_BLOWPIPE_ATTEMPTS, 0);
            twistedBowAttempts = Player.getAttribIntOr(player, SANGUINE_TWISTED_BOW_ATTEMTPS, 0);
            ancestralHatAttempts = Player.getAttribIntOr(player, ANCESTRAL_HAT_I_ATTEMPTS, 0);
            ancestralTopAttempts = Player.getAttribIntOr(player, ANCESTRAL_ROBE_TOP_I_ATTEMPTS, 0);
            ancestralBottomAttempts = Player.getAttribIntOr(player, ANCESTRAL_ROBE_BOTTOM_I_ATTEMPTS, 0);
            primordialBootsAttempts = Player.getAttribIntOr(player, PRIMORDIAL_BOOTS_OR_ATTEMPTS, 0);
            infernalCapeAttempts = Player.getAttribIntOr(player, INFERNAL_CAPE_ATTEMPTS, 0);
            sanguistiStaffAttempts = Player.getAttribIntOr(player, HOLY_SANGUINESTI_STAFF_ATTEMPTS, 0);
            holyScytheAttempts = Player.getAttribIntOr(player, HOLY_SCYTHE_OF_VITUR_ATTEMPTS, 0);
            ghraziRapierAttempts = Player.getAttribIntOr(player, HOLY_GHRAZI_RAPIER_ATTEMPTS, 0);
            scytheOfViturAttempts = Player.getAttribIntOr(player, SANGUINE_SCYTHE_OF_VITUR_ATTEMPTS, 0);
            pegasianBootsAttempts = Player.getAttribIntOr(player, PEGASIAN_BOOTS_OR_ATTEMPTS, 0);
            eternalBootsAttempts = Player.getAttribIntOr(player, ETERNAL_BOOTS_OR_ATTEMPTS, 0);
            viggorasChainmaceAttempts = Player.getAttribIntOr(player, CORRUPTED_VIGGORAS_CHAINMACE_ATTEMPTS, 0);
            crawsBowAttempts = Player.getAttribIntOr(player, CORRUPTED_CRAWS_BOW_ATTEMPTS, 0);
            thammaronsStaffAttempts = Player.getAttribIntOr(player, CORRUPTED_THAMMARONS_STAFF_ATTEMPTS, 0);
            corruptedBootsAttempts = Player.getAttribIntOr(player, CORRUPTED_BOOTS_ATTEMTPS, 0);
            ancientFaceguardAttempts = Player.getAttribIntOr(player, ANCIENT_FACEGUARD_ATTEMPTS, 0);
            toxicStaffOfTheDeadAttempts = Player.getAttribIntOr(player, TOXIC_STAFF_OF_THE_DEAD_C_ATTEMPTS, 0);
            deathTeleportTimer = player.getTimers().left(TimerKey.DEATH_TELEPORT_TIMER);
        }

        public void parseDetails() throws Exception {
            File dir = new File("./data/saves/characters/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File("./data/saves/characters/" + username + ".json");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
                //BackupHandler.requestBackup(BackupHandler.BackupType.PLAYER, file);
                writer.write(PlayerSave.SERIALIZE.toJson(this));
                writer.flush();
            }
        }
    }

    public static boolean playerExists(String name) {
        File file;
        file = new File("./data/saves/characters/" + name + ".json");
        return file.exists();
    }

}
