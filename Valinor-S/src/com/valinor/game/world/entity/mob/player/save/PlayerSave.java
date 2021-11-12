package com.valinor.game.world.entity.mob.player.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.valinor.GameServer;
import com.valinor.db.transactions.UpdatePasswordDatabaseTransaction;
import com.valinor.game.content.achievements.Achievements;
import com.valinor.game.content.bank_pin.BankPinModification;
import com.valinor.game.content.collection_logs.Collection;
import com.valinor.game.content.presets.Presetable;
import com.valinor.game.content.tasks.impl.Tasks;
import com.valinor.game.content.teleport.world_teleport_manager.TeleportInterface;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.combat.prayer.default_prayer.DefaultPrayerData;
import com.valinor.game.world.entity.combat.skull.SkullType;
import com.valinor.game.world.entity.combat.weapon.FightType;
import com.valinor.game.world.entity.mob.player.*;
import com.valinor.game.world.entity.mob.player.rights.MemberRights;
import com.valinor.game.world.entity.mob.player.rights.PlayerRights;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
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

    static final Map<Type, InstanceCreator<?>> instanceCreators = Collections.<Type, InstanceCreator<?>>emptyMap();

    public static final Gson SERIALIZE = new GsonBuilder().setDateFormat("MMM d, yyyy, HH:mm:ss a").setPrettyPrinting().registerTypeAdapterFactory(new MapTypeAdapterFactoryNulls(new ConstructorConstructor(instanceCreators), false)).disableHtmlEscaping().create();

    /**
     * Loads all the details of the {@code player}.
     *
     * @param player The player to load details for
     */
    public static boolean load(Player player) throws Exception {
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
            if (!file.exists()) { ;
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
                if (details.ironMode == null) {
                    player.ironMode(IronMode.NONE);
                } else {
                    player.ironMode(details.ironMode);
                }
                if(details.lastIP != null) {
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
                player.putAttrib(TOTAL_PAYMENT_AMOUNT, details.totalAmountPaid);
                player.putAttrib(PROMO_PAYMENT_AMOUNT, details.promoPaymentAmount);
                player.putAttrib(PROMO_ITEMS_UNLOCKED, details.promoItemsClaimed);
                player.putAttrib(MEMBER_UNLOCKED, details.memberUnlocked);
                player.putAttrib(SUPER_MEMBER_UNLOCKED, details.superMemberUnlocked);
                player.putAttrib(ELITE_MEMBER_UNLOCKED, details.eliteMemberUnlocked);
                player.putAttrib(EXTREME_MEMBER_UNLOCKED, details.extremeMemberUnlocked);
                player.putAttrib(LEGENDARY_MEMBER_UNLOCKED, details.legendaryMemberUnlocked);
                player.putAttrib(VIP_UNLOCKED, details.vipUnlocked);
                player.putAttrib(SPONSOR_UNLOCKED, details.sponsorMemberUnlocked);
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
                player.putAttrib(WILDERNESS_SLAYER_TASK_ACTIVE, details.wildernessSlayerActive);
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
                    if (details.tabAmounts.length >= 0)
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
                if (details.yellColour != null) {
                    if (details.yellColour.equals("0")) {
                        player.putAttrib(YELL_COLOUR, "006601");
                    } else {
                        player.putAttrib(YELL_COLOUR, details.yellColour);
                    }
                }
                player.putAttrib(ELDRITCH_NIGHTMARE_STAFF_QUESTION, details.dontAskAgainEldritch);
                player.putAttrib(VOLATILE_NIGHTMARE_STAFF_QUESTION, details.dontAskAgainVolatile);
                player.putAttrib(HARMONISED_NIGHTMARE_STAFF_QUESTION, details.dontAskAgainHarmonised);
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
                player.putAttrib(VOTE_POINS, details.votePoints);
                player.putAttrib(AttributeKey.PEST_CONTROL_POINTS, details.pestControlPoints);
                player.putAttrib(SLAYER_REWARD_POINTS, details.slayerRewardPoints);
                player.putAttrib(BOSS_POINTS, details.bossPoints);
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
                player.putAttrib(TARGET_KILLS, details.targetKills);
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
                player.putAttrib(MEN_IN_BLACK_KILLED, details.menInBlackKills);
                if (details.bossTimers != null) {
                    player.getBossTimers().setTimes(details.bossTimers);
                }
                if (details.recentTeleports != null) {
                    player.setRecentTeleports(details.recentTeleports);
                }
                if (details.favoriteTeleports != null) {
                    player.setFavorites(details.favoriteTeleports);
                }
                if (details.collectionLog != null) {
                    player.getCollectionLog().collectionLog = details.collectionLog;
                }
                if (details.achievements != null) {
                    player.achievements().putAll(details.achievements);
                }
                player.putAttrib(ACHIEVEMENTS_COMPLETED, details.achievementsCompleted);
                if (details.task != null) {
                    player.putAttrib(TASK, details.task);
                }
                player.putAttrib(TASK_AMOUNT, details.taskAmount);
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
                player.putAttrib(LARRANS_KEYS_TIER_ONE_USED, details.larranKeysUsed);
                player.putAttrib(EARNING_POTENTIAL, details.earningPotential);
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
                player.putAttrib(EDGE_PVP_DAILY_TASK_COMPLETION_AMOUNT, details.edgePvpDailyAmount);
                player.putAttrib(EDGE_PVP_DAILY_TASK_COMPLETED, details.edgePvpDailyCompleted);
                player.putAttrib(EDGE_PVP_DAILY_TASK_REWARD_CLAIMED, details.edgePvpDailyRewardClaimed);
                player.putAttrib(REVENANT_CAVE_PVP_DAILY_TASK_COMPLETION_AMOUNT, details.revCavePvpDailyAmount);
                player.putAttrib(REVENANT_CAVE_PVP_DAILY_TASK_COMPLETED, details.revCavePvpDailyCompleted);
                player.putAttrib(REVENANT_CAVE_PVP_DAILY_TASK_REWARD_CLAIMED, details.revCavePvpDailyRewardClaimed);
                player.putAttrib(DEEP_WILD_PVP_DAILY_TASK_COMPLETION_AMOUNT, details.deepWildPvpDailyAmount);
                player.putAttrib(DEEP_WILD_PVP_DAILY_TASK_COMPLETED, details.deepWildPvpDailyCompleted);
                player.putAttrib(DEEP_WILD_PVP_DAILY_TASK_REWARD_CLAIMED, details.deepWildPvpDailyRewardClaimed);
                player.putAttrib(PURE_PVP_DAILY_TASK_COMPLETION_AMOUNT, details.purePvpeDailyAmount);
                player.putAttrib(PURE_PVP_DAILY_TASK_COMPLETED, details.purePvpDailyCompleted);
                player.putAttrib(PURE_PVP_DAILY_TASK_REWARD_CLAIMED, details.purePvpDailyRewardClaimed);
                player.putAttrib(ZERKER_PVP_DAILY_TASK_COMPLETION_AMOUNT, details.zekerPvpDailyAmount);
                player.putAttrib(ZERKER_PVP_DAILY_TASK_COMPLETED, details.zekerPvpDailyCompleted);
                player.putAttrib(ZERKER_PVP_DAILY_TASK_REWARD_CLAIMED, details.zekerPVPDailyRewardClaimed);
                player.putAttrib(TIER_UPGRADE_DAILY_TASK_COMPLETION_AMOUNT, details.tierUpgradePvpDailyAmount);
                player.putAttrib(TIER_UPGRADE_DAILY_TASK_COMPLETED, details.tierUpgradePvpDailyCompleted);
                player.putAttrib(TIER_UPGRADE_DAILY_TASK_REWARD_CLAIMED, details.tierUpgradePvpDailyRewardClaimed);
                player.putAttrib(NO_ARM_DAILY_TASK_COMPLETION_AMOUNT, details.noArmPvpDailyAmount);
                player.putAttrib(NO_ARM_DAILY_TASK_COMPLETED, details.noArmPvpDailyCompleted);
                player.putAttrib(NO_ARM_DAILY_TASK_REWARD_CLAIMED, details.noArmPvpDailyRewardClaimed);
                player.putAttrib(DHAROK_DAILY_TASK_COMPLETION_AMOUNT, details.dharokPvpDailyAmount);
                player.putAttrib(DHAROK_DAILY_TASK_COMPLETED, details.dharokPvpDailyCompleted);
                player.putAttrib(DHAROK_DAILY_TASK_REWARD_CLAIMED, details.dharokPvpDailyRewardClaimed);
                player.putAttrib(BOTS_DAILY_TASK_COMPLETION_AMOUNT, details.botsPvpDailyAmount);
                player.putAttrib(BOTS_DAILY_TASK_COMPLETED, details.botsPvpDailyCompleted);
                player.putAttrib(BOTS_DAILY_TASK_REWARD_CLAIMED, details.botsPvpDailyRewardClaimed);
                player.putAttrib(TOURNEY_PARTICIPATION_DAILY_TASK_COMPLETION_AMOUNT, details.tourneyParticipationDailyAmount);
                player.putAttrib(TOURNEY_PARTICIPATION_DAILY_TASK_COMPLETED, details.tourneyParticipationDailyCompleted);
                player.putAttrib(TOURNEY_PARTICIPATION_DAILY_TASK_REWARD_CLAIMED, details.tourneyParticipationDailyRewardClaimed);
                player.putAttrib(DAILY_RAIDS_DAILY_TASK_COMPLETION_AMOUNT, details.dailyRaidsDailyAmount);
                player.putAttrib(DAILY_RAIDS_DAILY_TASK_COMPLETED, details.dailyRaidsDailyCompleted);
                player.putAttrib(DAILY_RAIDS_DAILY_TASK_REWARD_CLAIMED, details.dailyRaidsDailyRewardClaimed);
                player.putAttrib(WORLD_BOSS_DAILY_TASK_COMPLETION_AMOUNT, details.worldBossDailyDailyAmount);
                player.putAttrib(WORLD_BOSS_DAILY_TASK_COMPLETED, details.worldBossDailyDailyCompleted);
                player.putAttrib(WORLD_BOSS_DAILY_TASK_REWARD_CLAIMED, details.worldBossDailyRewardClaimed);
                player.putAttrib(DAILY_REVENANTS_TASK_COMPLETION_AMOUNT, details.revenantsDailyAmount);
                player.putAttrib(DAILY_REVENANTS_TASK_COMPLETED, details.revenantsDailyCompleted);
                player.putAttrib(DAILY_REVENANTS_TASK_REWARD_CLAIMED, details.revenantsDailyRewardClaimed);
                player.putAttrib(BATTLE_MAGE_DAILY_TASK_COMPLETION_AMOUNT, details.battleMageDailyAmount);
                player.putAttrib(BATTLE_MAGE_DAILY_TASK_COMPLETED, details.battleMageDailyCompleted);
                player.putAttrib(BATTLE_MAGE_DAILY_TASK_REWARD_CLAIMED, details.battleMageDailyRewardClaimed);
                player.putAttrib(WILDERNESS_BOSS_DAILY_TASK_COMPLETION_AMOUNT, details.wildernessBossDailyAmount);
                player.putAttrib(WILDERNESS_BOSS_DAILY_TASK_COMPLETED, details.wildernessBossDailyCompleted);
                player.putAttrib(WILDERNESS_BOSS_DAILY_TASK_REWARD_CLAIMED, details.wildernessBossDailyRewardClaimed);
                player.putAttrib(ZULRAH_DAILY_TASK_COMPLETION_AMOUNT, details.zulrahDailyAmount);
                player.putAttrib(ZULRAH_DAILY_TASK_COMPLETED, details.zulrahDailyCompleted);
                player.putAttrib(ZULRAH_DAILY_TASK_REWARD_CLAIMED, details.zulrahDailyRewardClaimed);
                player.putAttrib(SLAYER_DAILY_TASK_COMPLETION_AMOUNT, details.slayerDailyAmount);
                player.putAttrib(SLAYER_DAILY_TASK_COMPLETED, details.slayerDailyCompleted);
                player.putAttrib(SLAYER_DAILY_TASK_REWARD_CLAIMED, details.slayerDailyRewardClaimed);
                player.putAttrib(CORRUPTED_NECHRYARCHS_DAILY_TASK_COMPLETION_AMOUNT, details.corruptedNechryarchDailyAmount);
                player.putAttrib(CORRUPTED_NECHRYARCHS_DAILY_TASK_COMPLETED, details.corruptedNechryarchDailyCompleted);
                player.putAttrib(CORRUPTED_NECHRYARCHS_DAILY_TASK_REWARD_CLAIMED, details.corruptedNechryarchDailyRewardClaimed);
                player.putAttrib(VORKATH_DAILY_TASK_COMPLETION_AMOUNT, details.vorkathDailyAmount);
                player.putAttrib(VORKATH_DAILY_TASK_COMPLETED, details.vorkathDailyCompleted);
                player.putAttrib(VORKATH_DAILY_TASK_REWARD_CLAIMED, details.vorkathDailyRewardClaimed);
                player.putAttrib(CORPOREAL_BEAST_DAILY_TASK_COMPLETION_AMOUNT, details.corporealBeastDailyAmount);
                player.putAttrib(CORPOREAL_BEAST_DAILY_TASK_COMPLETED, details.corporealBeastDailyCompleted);
                player.putAttrib(CORPOREAL_BEAST_DAILY_TASK_REWARD_CLAIMED, details.corporealBeastDailyRewardClaimed);
                player.putAttrib(WILDY_RUNNER_DAILY_TASK_COMPLETION_AMOUNT, details.wildyRunnerDailyAmount);
                player.putAttrib(WILDY_RUNNER_DAILY_TASK_COMPLETED, details.wildyRunnerDailyCompleted);
                player.putAttrib(WILDY_RUNNER_DAILY_TASK_REWARD_CLAIMED, details.wildyRunnerDailyRewardClaimed);
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
                player.putAttrib(SLAYER_LOG_CLAIMED, details.slayerLogClaimed);
                player.putAttrib(LAST_DAILY_RESET, details.lastDailyReset);
                player.putAttrib(FINISHED_HALLOWEEN_TEACHER_DIALOGUE, details.finishedHalloweenDialogue);
                player.putAttrib(CANDIES_TRADED, details.candiesTraded);
                player.putAttrib(EVENT_REWARD_1_CLAIMED, details.eventReward1Claimed);
                player.putAttrib(EVENT_REWARD_2_CLAIMED, details.eventReward2Claimed);
                player.putAttrib(EVENT_REWARD_3_CLAIMED, details.eventReward3Claimed);
                player.putAttrib(EVENT_REWARD_4_CLAIMED, details.eventReward4Claimed);
                player.putAttrib(EVENT_REWARD_5_CLAIMED, details.eventReward5Claimed);
                player.putAttrib(EVENT_REWARD_6_CLAIMED, details.eventReward6Claimed);
                player.putAttrib(EVENT_REWARD_7_CLAIMED, details.eventReward7Claimed);
                player.putAttrib(EVENT_REWARD_8_CLAIMED, details.eventReward8Claimed);
                player.putAttrib(EVENT_REWARD_9_CLAIMED, details.eventReward9Claimed);
                player.putAttrib(EVENT_REWARD_10_CLAIMED, details.eventReward10Claimed);
                player.putAttrib(EVENT_REWARD_11_CLAIMED, details.eventReward11Claimed);
                player.putAttrib(EVENT_REWARD_12_CLAIMED, details.eventReward12Claimed);
                player.putAttrib(EVENT_REWARD_13_CLAIMED, details.eventReward13Claimed);
                player.putAttrib(EVENT_REWARD_14_CLAIMED, details.eventReward14Claimed);
                player.putAttrib(EVENT_REWARD_15_CLAIMED, details.eventReward15Claimed);
                player.putAttrib(EVENT_REWARD_16_CLAIMED, details.eventReward16Claimed);
                player.putAttrib(EVENT_REWARD_17_CLAIMED, details.eventReward17Claimed);
                player.putAttrib(EVENT_REWARD_18_CLAIMED, details.eventReward18Claimed);
                player.putAttrib(EVENT_REWARD_19_CLAIMED, details.eventReward19Claimed);
                player.putAttrib(EVENT_REWARD_20_CLAIMED, details.eventReward20Claimed);
                player.putAttrib(EVENT_REWARD_21_CLAIMED, details.eventReward21Claimed);
                player.putAttrib(EVENT_REWARD_22_CLAIMED, details.eventReward22Claimed);
                player.putAttrib(EVENT_REWARD_23_CLAIMED, details.eventReward23Claimed);
                player.putAttrib(EVENT_REWARD_24_CLAIMED, details.eventReward24Claimed);
                player.putAttrib(EVENT_REWARD_25_CLAIMED, details.eventReward25Claimed);
                player.putAttrib(EVENT_REWARD_26_CLAIMED, details.eventReward26Claimed);
                player.putAttrib(EVENT_REWARD_27_CLAIMED, details.eventReward27Claimed);
                player.putAttrib(EVENT_REWARD_28_CLAIMED, details.eventReward28Claimed);
                player.putAttrib(EVENT_REWARD_29_CLAIMED, details.eventReward29Claimed);
                player.putAttrib(EVENT_REWARD_30_CLAIMED, details.eventReward30Claimed);
                player.putAttrib(EVENT_REWARD_31_CLAIMED, details.eventReward31Claimed);
                player.putAttrib(EVENT_REWARD_32_CLAIMED, details.eventReward32Claimed);
                player.putAttrib(EVENT_REWARD_33_CLAIMED, details.eventReward33Claimed);
                player.putAttrib(EVENT_REWARD_34_CLAIMED, details.eventReward34Claimed);
                player.putAttrib(EVENT_REWARD_35_CLAIMED, details.eventReward35Claimed);
                player.putAttrib(EVENT_REWARD_36_CLAIMED, details.eventReward36Claimed);
                player.putAttrib(EVENT_REWARD_37_CLAIMED, details.eventReward37Claimed);
                player.putAttrib(EVENT_REWARD_38_CLAIMED, details.eventReward38Claimed);
                player.putAttrib(EVENT_REWARD_39_CLAIMED, details.eventReward39Claimed);
                player.putAttrib(EVENT_REWARD_40_CLAIMED, details.eventReward40Claimed);
                player.putAttrib(EVENT_REWARD_41_CLAIMED, details.eventReward41Claimed);
                player.putAttrib(EVENT_REWARD_42_CLAIMED, details.eventReward42Claimed);
                player.putAttrib(EVENT_REWARD_43_CLAIMED, details.eventReward43Claimed);
                player.putAttrib(EVENT_REWARD_44_CLAIMED, details.eventReward44Claimed);
                player.putAttrib(HWEEN_EVENT_TOKENS_SPENT, details.hweenEventTokensSpent);
                player.putAttrib(HERB_BOX_CHARGES, details.herbBoxCharges);
                player.putAttrib(COMBAT_MAXED, details.combatMaxed);
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
        private final IronMode ironMode;
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

        //Member attribs
        private final double totalAmountPaid;
        private final double promoPaymentAmount;
        private final int promoItemsClaimed;
        private final boolean memberUnlocked;
        private final boolean superMemberUnlocked;
        private final boolean eliteMemberUnlocked;
        private final boolean extremeMemberUnlocked;
        private final boolean legendaryMemberUnlocked;
        private final boolean vipUnlocked;
        private final boolean sponsorMemberUnlocked;

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
        private final boolean wildernessSlayerActive;
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
        private final String yellColour;
        private final boolean dontAskAgainEldritch;
        private final boolean dontAskAgainVolatile;
        private final boolean dontAskAgainHarmonised;
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

        //Unlocks
        private final boolean teleportToTargetUnlocked;
        private final boolean preserve;
        private final boolean rigour;
        private final boolean augury;

        //Pvp
        private final int kills;
        private final int deaths;
        private final List<String> recentKills;

        //counts
        private final int targetKills;
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
        private final int nightmaresKilled;
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
        private final int menInBlackKills;

        //Content
        private final Map<String, Integer> bossTimers;
        private final List<TeleportInterface.TeleportData> recentTeleports;
        private final List<TeleportInterface.TeleportData> favoriteTeleports;
        private final HashMap<Collection, ArrayList<Item>> collectionLog;
        private final HashMap<Achievements, Integer> achievements;
        private final int achievementsCompleted;
        private final Tasks task;
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
        private final int earningPotential;

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
        private final int edgePvpDailyAmount;
        private final boolean edgePvpDailyCompleted;
        private final boolean edgePvpDailyRewardClaimed;

        private final int revCavePvpDailyAmount;
        private final boolean revCavePvpDailyCompleted;
        private final boolean revCavePvpDailyRewardClaimed;

        private final int deepWildPvpDailyAmount;
        private final boolean deepWildPvpDailyCompleted;
        private final boolean deepWildPvpDailyRewardClaimed;

        private final int purePvpeDailyAmount;
        private final boolean purePvpDailyCompleted;
        private final boolean purePvpDailyRewardClaimed;

        private final int zekerPvpDailyAmount;
        private final boolean zekerPvpDailyCompleted;
        private final boolean zekerPVPDailyRewardClaimed;

        private final int tierUpgradePvpDailyAmount;
        private final boolean tierUpgradePvpDailyCompleted;
        private final boolean tierUpgradePvpDailyRewardClaimed;

        private final int noArmPvpDailyAmount;
        private final boolean noArmPvpDailyCompleted;
        private final boolean noArmPvpDailyRewardClaimed;

        private final int dharokPvpDailyAmount;
        private final boolean dharokPvpDailyCompleted;
        private final boolean dharokPvpDailyRewardClaimed;

        private final int botsPvpDailyAmount;
        private final boolean botsPvpDailyCompleted;
        private final boolean botsPvpDailyRewardClaimed;

        private final int tourneyParticipationDailyAmount;
        private final boolean tourneyParticipationDailyCompleted;
        private final boolean tourneyParticipationDailyRewardClaimed;

        private final int dailyRaidsDailyAmount;
        private final boolean dailyRaidsDailyCompleted;
        private final boolean dailyRaidsDailyRewardClaimed;

        private final int worldBossDailyDailyAmount;
        private final boolean worldBossDailyDailyCompleted;
        private final boolean worldBossDailyRewardClaimed;

        private final int revenantsDailyAmount;
        private final boolean revenantsDailyCompleted;
        private final boolean revenantsDailyRewardClaimed;

        private final int battleMageDailyAmount;
        private final boolean battleMageDailyCompleted;
        private final boolean battleMageDailyRewardClaimed;

        private final int wildernessBossDailyAmount;
        private final boolean wildernessBossDailyCompleted;
        private final boolean wildernessBossDailyRewardClaimed;

        private final int zulrahDailyAmount;
        private final boolean zulrahDailyCompleted;
        private final boolean zulrahDailyRewardClaimed;

        private final int slayerDailyAmount;
        private final boolean slayerDailyCompleted;
        private final boolean slayerDailyRewardClaimed;

        private final int corruptedNechryarchDailyAmount;
        private final boolean corruptedNechryarchDailyCompleted;
        private final boolean corruptedNechryarchDailyRewardClaimed;

        private final int vorkathDailyAmount;
        private final boolean vorkathDailyCompleted;
        private final boolean vorkathDailyRewardClaimed;

        private final int corporealBeastDailyAmount;
        private final boolean corporealBeastDailyCompleted;
        private final boolean corporealBeastDailyRewardClaimed;

        private final int wildyRunnerDailyAmount;
        private final boolean wildyRunnerDailyCompleted;
        private final boolean wildyRunnerDailyRewardClaimed;

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
        private final boolean revenantsLogClaimed;
        private final boolean slayerLogClaimed;
        private final int lastDailyReset;
        private final boolean finishedHalloweenDialogue;
        private final int candiesTraded;
        private final boolean eventReward1Claimed;
        private final boolean eventReward2Claimed;
        private final boolean eventReward3Claimed;
        private final boolean eventReward4Claimed;
        private final boolean eventReward5Claimed;
        private final boolean eventReward6Claimed;
        private final boolean eventReward7Claimed;
        private final boolean eventReward8Claimed;
        private final boolean eventReward9Claimed;
        private final boolean eventReward10Claimed;
        private final boolean eventReward11Claimed;
        private final boolean eventReward12Claimed;
        private final boolean eventReward13Claimed;
        private final boolean eventReward14Claimed;
        private final boolean eventReward15Claimed;
        private final boolean eventReward16Claimed;
        private final boolean eventReward17Claimed;
        private final boolean eventReward18Claimed;
        private final boolean eventReward19Claimed;
        private final boolean eventReward20Claimed;
        private final boolean eventReward21Claimed;
        private final boolean eventReward22Claimed;
        private final boolean eventReward23Claimed;
        private final boolean eventReward24Claimed;
        private final boolean eventReward25Claimed;
        private final boolean eventReward26Claimed;
        private final boolean eventReward27Claimed;
        private final boolean eventReward28Claimed;
        private final boolean eventReward29Claimed;
        private final boolean eventReward30Claimed;
        private final boolean eventReward31Claimed;
        private final boolean eventReward32Claimed;
        private final boolean eventReward33Claimed;
        private final boolean eventReward34Claimed;
        private final boolean eventReward35Claimed;
        private final boolean eventReward36Claimed;
        private final boolean eventReward37Claimed;
        private final boolean eventReward38Claimed;
        private final boolean eventReward39Claimed;
        private final boolean eventReward40Claimed;
        private final boolean eventReward41Claimed;
        private final boolean eventReward42Claimed;
        private final boolean eventReward43Claimed;
        private final boolean eventReward44Claimed;
        private final int hweenEventTokensSpent;
        private final int herbBoxCharges;
        private final boolean combatMaxed;

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
            ironMode = player.ironMode();
            lastIP = player.getHostAddress();
            mac = player.getAttribOr(MAC_ADDRESS, "invalid");
            accountPin = Player.getAttribIntOr(player, ACCOUNT_PIN,0);
            askAccountPin = Player.getAttribBooleanOr(player, ASK_FOR_ACCOUNT_PIN,false);
            accountPinAttemptsLeft = Player.getAttribIntOr(player, ACCOUNT_PIN_ATTEMPTS_LEFT,5);
            accountPinFrozenTicks = Player.getAttribIntOr(player, ACCOUNT_PIN_FREEZE_TICKS,0);
            creationDate = player.getCreationDate();
            creationIp = player.getCreationIp();
            lastLogin = player.getLastLogin();
            muted = Player.getAttribBooleanOr(player, MUTED, false);
            newPlayer = Player.getAttribBooleanOr(player, NEW_ACCOUNT, false);
            isBetaTester = Player.getAttribBooleanOr(player, IS_BETA_TESTER, false);
            veteran = Player.getAttribBooleanOr(player, VETERAN, false);
            gambler = Player.getAttribBooleanOr(player, GAMBLER, false);
            starterboxClaimed = Player.getAttribBooleanOr(player, STARTER_BOX_CLAIMED, false);
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
            totalAmountPaid = Player.getAttribDoubleOr(player, TOTAL_PAYMENT_AMOUNT, 0D);
            promoPaymentAmount = Player.getAttribDoubleOr(player, PROMO_PAYMENT_AMOUNT, 0D);
            promoItemsClaimed = Player.getAttribIntOr(player, PROMO_ITEMS_UNLOCKED, 0);
            memberUnlocked = Player.getAttribBooleanOr(player, MEMBER_UNLOCKED, false);
            superMemberUnlocked = Player.getAttribBooleanOr(player, SUPER_MEMBER_UNLOCKED, false);
            eliteMemberUnlocked = Player.getAttribBooleanOr(player, ELITE_MEMBER_UNLOCKED, false);
            extremeMemberUnlocked = Player.getAttribBooleanOr(player, EXTREME_MEMBER_UNLOCKED, false);
            legendaryMemberUnlocked = Player.getAttribBooleanOr(player, LEGENDARY_MEMBER_UNLOCKED, false);
            vipUnlocked = Player.getAttribBooleanOr(player, VIP_UNLOCKED, false);
            sponsorMemberUnlocked = Player.getAttribBooleanOr(player, SPONSOR_UNLOCKED, false);
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
            wildernessSlayerActive = Player.getAttribBooleanOr(player, WILDERNESS_SLAYER_TASK_ACTIVE, false);
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
            cartItems = player.<ArrayList<Item>>getAttribOr(CART_ITEMS, new ArrayList<Item>());
            nifflerItems = player.<ArrayList<Item>>getAttribOr(NIFFLER_ITEMS_STORED, new ArrayList<Item>());
            friends = player.getRelations().getFriendList();
            ignores = player.getRelations().getIgnoreList();
            clan = player.getClanChat();
            yellColour = Player.getAttribStringOr(player, YELL_COLOUR, "000000");
            dontAskAgainEldritch = Player.getAttribBooleanOr(player, ELDRITCH_NIGHTMARE_STAFF_QUESTION, false);
            dontAskAgainVolatile = Player.getAttribBooleanOr(player, VOLATILE_NIGHTMARE_STAFF_QUESTION, false);
            dontAskAgainHarmonised = Player.getAttribBooleanOr(player, HARMONISED_NIGHTMARE_STAFF_QUESTION, false);
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
            votePoints = Player.getAttribIntOr(player, VOTE_POINS, 0);
            pestControlPoints = Player.getAttribIntOr(player, AttributeKey.PEST_CONTROL_POINTS, 0);
            slayerRewardPoints = Player.getAttribIntOr(player, SLAYER_REWARD_POINTS, 0);
            bossPoints = Player.getAttribIntOr(player, BOSS_POINTS, 0);
            teleportToTargetUnlocked = Player.getAttribBooleanOr(player, BOUNTY_HUNTER_TARGET_TELEPORT_UNLOCKED, false);
            preserve = Player.getAttribBooleanOr(player, PRESERVE, false);
            rigour = Player.getAttribBooleanOr(player, RIGOUR, false);
            augury = Player.getAttribBooleanOr(player, AUGURY, false);
            kills = Player.getAttribIntOr(player, PLAYER_KILLS, 0);
            deaths = Player.getAttribIntOr(player, PLAYER_DEATHS, 0);
            recentKills = player.getRecentKills();
            targetKills = Player.getAttribIntOr(player, TARGET_KILLS, 0);
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
            menInBlackKills = Player.getAttribIntOr(player, MEN_IN_BLACK_KILLED, 0);
            bossTimers = player.getBossTimers().getTimes();
            recentTeleports = player.getRecentTeleports();
            favoriteTeleports = player.getFavorites();
            collectionLog = player.getCollectionLog().collectionLog;
            achievements = player.achievements();
            achievementsCompleted = Player.getAttribIntOr(player, ACHIEVEMENTS_COMPLETED, 0);
            task = player.getAttribOr(TASK, Tasks.NONE);
            taskAmount = Player.getAttribIntOr(player, TASK_AMOUNT, 0);
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
            larranKeysUsed = Player.getAttribIntOr(player, LARRANS_KEYS_TIER_ONE_USED, 0);
            earningPotential = Player.getAttribIntOr(player, EARNING_POTENTIAL, 0);
            slayerKeysOpened = Player.getAttribIntOr(player, SLAYER_KEYS_OPENED, 0);
            slayerKeysReceived = Player.getAttribIntOr(player, SLAYER_KEYS_RECEIVED, 0);
            doubleExpTicks = Player.getAttribIntOr(player, DOUBLE_EXP_TICKS,0);
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
            edgePvpDailyAmount = Player.getAttribIntOr(player, EDGE_PVP_DAILY_TASK_COMPLETION_AMOUNT, 0);
            edgePvpDailyCompleted = Player.getAttribBooleanOr(player, EDGE_PVP_DAILY_TASK_COMPLETED, false);
            edgePvpDailyRewardClaimed = Player.getAttribBooleanOr(player, EDGE_PVP_DAILY_TASK_REWARD_CLAIMED, false);
            revCavePvpDailyAmount = Player.getAttribIntOr(player, REVENANT_CAVE_PVP_DAILY_TASK_COMPLETION_AMOUNT, 0);
            revCavePvpDailyCompleted = Player.getAttribBooleanOr(player, REVENANT_CAVE_PVP_DAILY_TASK_COMPLETED, false);
            revCavePvpDailyRewardClaimed = Player.getAttribBooleanOr(player, REVENANT_CAVE_PVP_DAILY_TASK_REWARD_CLAIMED, false);
            deepWildPvpDailyAmount = Player.getAttribIntOr(player, DEEP_WILD_PVP_DAILY_TASK_COMPLETION_AMOUNT, 0);
            deepWildPvpDailyCompleted = Player.getAttribBooleanOr(player, DEEP_WILD_PVP_DAILY_TASK_COMPLETED, false);
            deepWildPvpDailyRewardClaimed = Player.getAttribBooleanOr(player, DEEP_WILD_PVP_DAILY_TASK_REWARD_CLAIMED, false);
            purePvpeDailyAmount = Player.getAttribIntOr(player, PURE_PVP_DAILY_TASK_COMPLETION_AMOUNT, 0);
            purePvpDailyCompleted = Player.getAttribBooleanOr(player, PURE_PVP_DAILY_TASK_COMPLETED, false);
            purePvpDailyRewardClaimed = Player.getAttribBooleanOr(player, PURE_PVP_DAILY_TASK_REWARD_CLAIMED, false);
            zekerPvpDailyAmount = Player.getAttribIntOr(player, ZERKER_PVP_DAILY_TASK_COMPLETION_AMOUNT, 0);
            zekerPvpDailyCompleted = Player.getAttribBooleanOr(player, ZERKER_PVP_DAILY_TASK_COMPLETED, false);
            zekerPVPDailyRewardClaimed = Player.getAttribBooleanOr(player, ZERKER_PVP_DAILY_TASK_REWARD_CLAIMED, false);
            tierUpgradePvpDailyAmount = Player.getAttribIntOr(player, TIER_UPGRADE_DAILY_TASK_COMPLETION_AMOUNT, 0);
            tierUpgradePvpDailyCompleted = Player.getAttribBooleanOr(player, TIER_UPGRADE_DAILY_TASK_COMPLETED, false);
            tierUpgradePvpDailyRewardClaimed = Player.getAttribBooleanOr(player, TIER_UPGRADE_DAILY_TASK_REWARD_CLAIMED, false);
            noArmPvpDailyAmount = Player.getAttribIntOr(player, NO_ARM_DAILY_TASK_COMPLETION_AMOUNT, 0);
            noArmPvpDailyCompleted = Player.getAttribBooleanOr(player, NO_ARM_DAILY_TASK_COMPLETED, false);
            noArmPvpDailyRewardClaimed = Player.getAttribBooleanOr(player, NO_ARM_DAILY_TASK_REWARD_CLAIMED, false);
            dharokPvpDailyAmount = Player.getAttribIntOr(player, DHAROK_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dharokPvpDailyCompleted = Player.getAttribBooleanOr(player, DHAROK_DAILY_TASK_COMPLETED, false);
            dharokPvpDailyRewardClaimed = Player.getAttribBooleanOr(player, DHAROK_DAILY_TASK_REWARD_CLAIMED, false);
            botsPvpDailyAmount = Player.getAttribIntOr(player, BOTS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            botsPvpDailyCompleted = Player.getAttribBooleanOr(player, BOTS_DAILY_TASK_COMPLETED, false);
            botsPvpDailyRewardClaimed = Player.getAttribBooleanOr(player, BOTS_DAILY_TASK_REWARD_CLAIMED, false);
            tourneyParticipationDailyAmount = Player.getAttribIntOr(player, TOURNEY_PARTICIPATION_DAILY_TASK_COMPLETION_AMOUNT, 0);
            tourneyParticipationDailyCompleted = Player.getAttribBooleanOr(player, TOURNEY_PARTICIPATION_DAILY_TASK_COMPLETED, false);
            tourneyParticipationDailyRewardClaimed = Player.getAttribBooleanOr(player, TOURNEY_PARTICIPATION_DAILY_TASK_REWARD_CLAIMED, false);
            dailyRaidsDailyAmount = Player.getAttribIntOr(player, DAILY_RAIDS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            dailyRaidsDailyCompleted = Player.getAttribBooleanOr(player, DAILY_RAIDS_DAILY_TASK_COMPLETED, false);
            dailyRaidsDailyRewardClaimed = Player.getAttribBooleanOr(player, DAILY_RAIDS_DAILY_TASK_REWARD_CLAIMED, false);
            worldBossDailyDailyAmount = Player.getAttribIntOr(player, WORLD_BOSS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            worldBossDailyDailyCompleted = Player.getAttribBooleanOr(player, WORLD_BOSS_DAILY_TASK_COMPLETED, false);
            worldBossDailyRewardClaimed = Player.getAttribBooleanOr(player, WORLD_BOSS_DAILY_TASK_REWARD_CLAIMED, false);
            revenantsDailyAmount = Player.getAttribIntOr(player, DAILY_REVENANTS_TASK_COMPLETION_AMOUNT, 0);
            revenantsDailyCompleted = Player.getAttribBooleanOr(player, DAILY_REVENANTS_TASK_COMPLETED, false);
            revenantsDailyRewardClaimed = Player.getAttribBooleanOr(player, DAILY_REVENANTS_TASK_REWARD_CLAIMED, false);
            battleMageDailyAmount = Player.getAttribIntOr(player, BATTLE_MAGE_DAILY_TASK_COMPLETION_AMOUNT, 0);
            battleMageDailyCompleted = Player.getAttribBooleanOr(player, BATTLE_MAGE_DAILY_TASK_COMPLETED, false);
            battleMageDailyRewardClaimed = Player.getAttribBooleanOr(player, BATTLE_MAGE_DAILY_TASK_REWARD_CLAIMED, false);
            wildernessBossDailyAmount = Player.getAttribIntOr(player, WILDERNESS_BOSS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            wildernessBossDailyCompleted = Player.getAttribBooleanOr(player, WILDERNESS_BOSS_DAILY_TASK_COMPLETED, false);
            wildernessBossDailyRewardClaimed = Player.getAttribBooleanOr(player, WILDERNESS_BOSS_DAILY_TASK_REWARD_CLAIMED, false);
            zulrahDailyAmount = Player.getAttribIntOr(player, ZULRAH_DAILY_TASK_COMPLETION_AMOUNT, 0);
            zulrahDailyCompleted = Player.getAttribBooleanOr(player, ZULRAH_DAILY_TASK_COMPLETED, false);
            zulrahDailyRewardClaimed = Player.getAttribBooleanOr(player, ZULRAH_DAILY_TASK_REWARD_CLAIMED, false);
            slayerDailyAmount = Player.getAttribIntOr(player, SLAYER_DAILY_TASK_COMPLETION_AMOUNT, 0);
            slayerDailyCompleted = Player.getAttribBooleanOr(player, SLAYER_DAILY_TASK_COMPLETED, false);
            slayerDailyRewardClaimed = Player.getAttribBooleanOr(player, SLAYER_DAILY_TASK_REWARD_CLAIMED, false);
            corruptedNechryarchDailyAmount = Player.getAttribIntOr(player, CORRUPTED_NECHRYARCHS_DAILY_TASK_COMPLETION_AMOUNT, 0);
            corruptedNechryarchDailyCompleted = Player.getAttribBooleanOr(player, CORRUPTED_NECHRYARCHS_DAILY_TASK_COMPLETED, false);
            corruptedNechryarchDailyRewardClaimed = Player.getAttribBooleanOr(player, CORRUPTED_NECHRYARCHS_DAILY_TASK_REWARD_CLAIMED, false);
            vorkathDailyAmount = Player.getAttribIntOr(player, VORKATH_DAILY_TASK_COMPLETION_AMOUNT, 0);
            vorkathDailyCompleted = Player.getAttribBooleanOr(player, VORKATH_DAILY_TASK_COMPLETED, false);
            vorkathDailyRewardClaimed = Player.getAttribBooleanOr(player, VORKATH_DAILY_TASK_REWARD_CLAIMED, false);
            corporealBeastDailyAmount = Player.getAttribIntOr(player, CORPOREAL_BEAST_DAILY_TASK_COMPLETION_AMOUNT, 0);
            corporealBeastDailyCompleted = Player.getAttribBooleanOr(player, CORPOREAL_BEAST_DAILY_TASK_COMPLETED, false);
            corporealBeastDailyRewardClaimed = Player.getAttribBooleanOr(player, CORPOREAL_BEAST_DAILY_TASK_REWARD_CLAIMED, false);
            wildyRunnerDailyAmount = Player.getAttribIntOr(player, WILDY_RUNNER_DAILY_TASK_COMPLETION_AMOUNT, 0);
            wildyRunnerDailyCompleted = Player.getAttribBooleanOr(player, WILDY_RUNNER_DAILY_TASK_COMPLETED, false);
            wildyRunnerDailyRewardClaimed = Player.getAttribBooleanOr(player, WILDY_RUNNER_DAILY_TASK_REWARD_CLAIMED, false);
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
            slayerLogClaimed = Player.getAttribBooleanOr(player, SLAYER_LOG_CLAIMED, false);
            lastDailyReset = Player.getAttribIntOr(player, LAST_DAILY_RESET, -1);
            finishedHalloweenDialogue = Player.getAttribBooleanOr(player, FINISHED_HALLOWEEN_TEACHER_DIALOGUE, false);
            candiesTraded = Player.getAttribIntOr(player, CANDIES_TRADED, -1);
            eventReward1Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_1_CLAIMED, false);
            eventReward2Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_2_CLAIMED, false);
            eventReward3Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_3_CLAIMED, false);
            eventReward4Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_4_CLAIMED, false);
            eventReward5Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_5_CLAIMED, false);
            eventReward6Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_6_CLAIMED, false);
            eventReward7Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_7_CLAIMED, false);
            eventReward8Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_8_CLAIMED, false);
            eventReward9Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_9_CLAIMED, false);
            eventReward10Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_10_CLAIMED, false);
            eventReward11Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_11_CLAIMED, false);
            eventReward12Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_12_CLAIMED, false);
            eventReward13Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_13_CLAIMED, false);
            eventReward14Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_14_CLAIMED, false);
            eventReward15Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_15_CLAIMED, false);
            eventReward16Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_16_CLAIMED, false);
            eventReward17Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_17_CLAIMED, false);
            eventReward18Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_18_CLAIMED, false);
            eventReward19Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_19_CLAIMED, false);
            eventReward20Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_20_CLAIMED, false);
            eventReward21Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_21_CLAIMED, false);
            eventReward22Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_22_CLAIMED, false);
            eventReward23Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_23_CLAIMED, false);
            eventReward24Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_24_CLAIMED, false);
            eventReward25Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_25_CLAIMED, false);
            eventReward26Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_26_CLAIMED, false);
            eventReward27Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_27_CLAIMED, false);
            eventReward28Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_28_CLAIMED, false);
            eventReward29Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_29_CLAIMED, false);
            eventReward30Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_30_CLAIMED, false);
            eventReward31Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_31_CLAIMED, false);
            eventReward32Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_32_CLAIMED, false);
            eventReward33Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_33_CLAIMED, false);
            eventReward34Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_34_CLAIMED, false);
            eventReward35Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_35_CLAIMED, false);
            eventReward36Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_36_CLAIMED, false);
            eventReward37Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_37_CLAIMED, false);
            eventReward38Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_38_CLAIMED, false);
            eventReward39Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_39_CLAIMED, false);
            eventReward40Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_40_CLAIMED, false);
            eventReward41Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_41_CLAIMED, false);
            eventReward42Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_42_CLAIMED, false);
            eventReward43Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_43_CLAIMED, false);
            eventReward44Claimed = Player.getAttribBooleanOr(player, EVENT_REWARD_44_CLAIMED, false);
            hweenEventTokensSpent = Player.getAttribIntOr(player, HWEEN_EVENT_TOKENS_SPENT, 0);
            herbBoxCharges = Player.getAttribIntOr(player, HERB_BOX_CHARGES, 0);
            combatMaxed = Player.getAttribBooleanOr(player, COMBAT_MAXED, false);
        }

        public void parseDetails() throws Exception {
            File dir = new File("./data/saves/characters/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/saves/characters/" + username + ".json", false))) {
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
