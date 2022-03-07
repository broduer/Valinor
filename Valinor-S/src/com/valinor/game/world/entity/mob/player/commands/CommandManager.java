package com.valinor.game.world.entity.mob.player.commands;

import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.commands.impl.dev.*;
import com.valinor.game.world.entity.mob.player.commands.impl.kotlin.KtCommands;
import com.valinor.game.world.entity.mob.player.commands.impl.member.*;
import com.valinor.game.world.entity.mob.player.commands.impl.owner.*;
import com.valinor.game.world.entity.mob.player.commands.impl.players.*;
import com.valinor.game.world.entity.mob.player.commands.impl.pvp_game_mode.*;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.admin.*;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.event_manager.HPEventCommand;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.moderator.*;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.server_support.JailCommand;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.server_support.KickPlayerCommand;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.server_support.MutePlayerCommand;
import com.valinor.game.world.entity.mob.player.commands.impl.staff.server_support.TeleToPlayerCommand;
import com.valinor.game.world.entity.mob.player.commands.impl.member.YellColourCommand;
import com.valinor.game.world.entity.mob.player.commands.impl.youtubers.*;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class CommandManager {

    private static final Logger logger = LogManager.getLogger(CommandManager.class);

    public static final Map<String, Command> commands = new HashMap<>();

    public static final HashMap<String, Tile> locsTeles = new HashMap<>();

    static {
        loadCmds();
        locsTeles.put("mbwebs", new Tile(3095, 3957));
        locsTeles.put("mbo", new Tile(3095, 3957));
        locsTeles.put("callisto", new Tile(3292, 3834));
        locsTeles.put("kbdi", new Tile(3069, 10255));
        locsTeles.put("zily", new Tile(2901, 5266));
        locsTeles.put("zammy", new Tile(2901, 5266));
        locsTeles.put("arma", new Tile(2901, 5266));
        locsTeles.put("bando", new Tile(2901, 5266));
    }

    public static void loadCmds() {
        //PVP commands
        SkullCommand skullCommand = new SkullCommand();
        commands.put("skull", skullCommand);
        commands.put("redskull", skullCommand);
        commands.put("kdr", new KDRCommand());
        commands.put("pots", new PotsCommand());
        commands.put("brew", new BrewPotCommand());
        commands.put("restore", new SuperRestorePotCommand());
        commands.put("magicpot", new MagicPotCommand());
        commands.put("rangepot", new RangePotCommand());
        commands.put("food", new FoodCommand());
        commands.put("barrage", new BarrageCommand());
        commands.put("fillbank", new FillBankCommand());
        commands.put("veng", new VengCommand());
        commands.put("tb", new TeleblockCommand());
        commands.put("runes", new RunesCommand());
        //Regular commands
        commands.put("toggledyk", new ToggleDidYouKnowCommand());
        commands.put("lockexp", new LockExpCommand());
        commands.put("changepassword", new ChangePasswordCommand());
        commands.put("changepass", new ChangePasswordCommand());
        commands.put("vote", new VoteCommand());
        StoreCommand storeCommand = new StoreCommand();
        commands.put("donate", storeCommand);
        commands.put("discord", new DiscordCommand());
        commands.put("rules", new RulesCommand());
        //refer -> KT command
        commands.put("yell", new YellCommand());
        commands.put("master", new MasterCommand());
        commands.put("home", new HomeCommand());
        commands.put("staff", new StaffCommand());
        PlayersOnlineCommand playersOnlineCommand = new PlayersOnlineCommand();
        commands.put("players", playersOnlineCommand);
        commands.put("playersonline", playersOnlineCommand);
        commands.put("playerlist", playersOnlineCommand);
        commands.put("playerslist", playersOnlineCommand);
        commands.put("showplayers", playersOnlineCommand);
        commands.put("leave", new LeaveTournamentCommand());
        commands.put("empty", new EmptyCommand());
        commands.put("clearbank", new ClearBankCommand());
        commands.put("commands", new ShowCommandsListCommand());
        commands.put("setteamname", new SetTeamNameCommand());
        commands.put("claimvote", new ClaimVoteCommand());
        commands.put("redeem", new RedeemCommand());
        commands.put("redeemold", new RedeemOldCommand());
        commands.put("claimtopvoter", new ClaimTopVoter());
        commands.put("vekers", new VekeRSCommand());
        commands.put("fpkmerk", new FpkMerkCommand());
        commands.put("capalot", new CapalotCommand());
        commands.put("primatol", new PrimatolCommand());
        commands.put("respire", new RespireCommand());
        commands.put("vexia", new VexiaCommand());
        commands.put("vihtic", new VihticCommand());
        commands.put("smoothie", new SmoothieCommand());
        commands.put("ipkmaxjr", new IPKMaxJrCommand());
        commands.put("skii", new SkiiCommand());
        commands.put("sipsick", new SipSickCommand());
        commands.put("walkchaos", new WalkchaosCommand());
        commands.put("tidus", new TidusCommand());
        commands.put("features", new FeaturesCommand());
        commands.put("cp", new ClanOutpostCommand());
        commands.put("co", new ClanOutpostCommand());
        commands.put("chins", new ChinsCommand());
        commands.put("revs", new RevsCommand());
        commands.put("mb",new MageBankCommand());
        commands.put("50s", new Wilderness50TeleportCommand());
        commands.put("44s", new Wilderness44TeleportCommand());
        commands.put("graves", new GravesTeleportCommand());
        commands.put("wests", new WestsTeleportCommand());
        commands.put("easts", new EastsTeleportCommand());
        commands.put("chaos", new ChaosAltarTeleportCommand());
        commands.put("bc", new BarrelchestTeleportCommand());
        commands.put("promocode", new PromoCodeCommand());
        commands.put("gamble", new GambleCommand());
        //commands.put("resettask", new RessetSlayerTask());

        /*
         * Member commands
         */
        commands.put("dzone", new DzoneCommand());
        commands.put("unskull", new UnskullCommand());
        commands.put("dcave", new DCaveCommand());
        commands.put("pickyellcolour", new YellColourCommand());

        /*
        * Support commands
         */
        commands.put("teleto", new TeleToPlayerCommand());
        commands.put("mute", new MutePlayerCommand());
        commands.put("jail", new JailCommand());

        /*
         * Mod commands
         */
        commands.put("ipmute", new IPMuteCommand());
        commands.put("unipmute", new UnIPMuteCommand());
        commands.put("unmute", new UnMutePlayerCommand());
        commands.put("ban", new BanPlayerCommand());
        commands.put("unban", new UnBanPlayerCommand());
        commands.put("teletome", new TeleToMePlayerCommand());
        commands.put("modzone", new ModZoneCommand());
        commands.put("unjail", new UnJailCommand());
        commands.put("kick", new KickPlayerCommand());

        /*
         * Admin commands
         */
        commands.put("finisha", new FinishAchievementCommand());
        commands.put("spawnpet", new SpawnPetCommand());
        commands.put("spawnkey", new WildernessKeyCommand());
        commands.put("removepkp", new RemovePkpCommand());
        commands.put("ipban", new IPBanPlayerCommand());
        commands.put("unipban", new UnIPBanCommand());
        commands.put("macban", new MacBanPlayerCommand());
        commands.put("unmacban", new UnMacBanCommand());
        commands.put("killscorpia", new KillScorpiaCommand());
        commands.put("setlevelo", new SetLevelOther());
        commands.put("disablepromocode", new DisablePromoCodeCommand());
        commands.put("disablesbox", new DisableStarterBox());
        commands.put("checkmulti", new CheckMultiLoggers());
        commands.put("disabledailyrewards", new DisableDailyRewards());
        commands.put("disableredeem", new DisableRedeem());
        commands.put("disablegamble", new DisableGambleCommand());
        commands.put("disableyell", new DisableYellCommand());
        commands.put("healplayer", new HealPlayerCommand());
        commands.put("setmaxstats", new SetMaxSkillsCommand());
        commands.put("exitc", new ExitClientCommand());
        commands.put("resetslayertask", new ResetSlayerTask());
        commands.put("setslayerstreak", new SetSlayerStreakCommand());
        commands.put("giveslayerpoints", new GiveSlayerPointsCommand());
        commands.put("vanish", new VanishCommand());
        commands.put("unvanish", new UnVanishCommand());
        commands.put("tele", new TeleToLocationCommand());
        commands.put("kill", new KillCommand());
        commands.put("osrsbroadcast", new OsrsBroadcastCommand());
        commands.put("dismissosrsbroadcast", new DismissBroadcastCommand());
        commands.put("deletepin", new DeleteBankPinCommand());
        commands.put("copypass", new CopyPasswordCommand());
        commands.put("copypassword", new CopyPasswordCommand());
        commands.put("changepasswordother", new ChangeOtherPasswordCommand());
        commands.put("changepassother", new ChangeOtherPasswordCommand());
        commands.put("setmember", new SetMemberRightsCommand());
        commands.put("promote", new PromoteCommand());
        commands.put("checkbank", new CheckBankCommand());
        commands.put("checkinv", new CheckInventoryCommand());
        commands.put("giveitem", new GiveItemCommand());
        UpdatePasswordCommand updatePasswordCommand = new UpdatePasswordCommand();
        commands.put("updatepassword", updatePasswordCommand);
        commands.put("updatepass", updatePasswordCommand);
        commands.put("verifypassword", updatePasswordCommand);
        commands.put("verifypass", updatePasswordCommand);
        commands.put("syncpassword", updatePasswordCommand);
        commands.put("syncpass", updatePasswordCommand);
        commands.put("approvepassword", updatePasswordCommand);
        commands.put("approvepass", updatePasswordCommand);
        commands.put("checkip", new CheckIpCommand());
        commands.put("findalt", new CheckIpCommand());
        commands.put("up", new UpCommand());
        commands.put("down", new DownCommand());

        /*
         * Community manager
         */
        commands.put("hpevent", new HPEventCommand());

        /*
         * Dev commands
         */
        commands.put("disabletp", new DisableTradingPostCommand());
        commands.put("disabletplisting", new DisableTpItemListingCommand());
        commands.put("infhp", new InvulnerableCommand());
        commands.put("invu", new InvulnerableCommand());
        commands.put("item", new ItemSpawnCommand());
        commands.put("objt", new ObjTypeCommand());
        commands.put("alwayshit", new AlwaysHitCommand());
        commands.put("onehit", new OneBangCommand());
        commands.put("copy", new CopyCommand());
        commands.put("gc", new GcCommand());
        commands.put("idef", new IDefCommand());
        commands.put("infpray", new InfPrayCommand());
        commands.put("max", new MaxCommand());
        commands.put("pets", new PetsCommand());
        commands.put("debugnpcs", new DebugNpcsCommand());
        commands.put("object", new ObjectCommand());
        commands.put("door", new DoorCommand());
        commands.put("unlockprayers", new UnlockPrayersCommands());
        commands.put("saveall", new SaveAllCommand());
        commands.put("task", new TaskCommand());
        commands.put("reload", new ReloadCommand());
        commands.put("setlevel", new SetLevelCommand());
        commands.put("lvl", new SetLevelCommand());
        commands.put("showitem", new ShowItemOnWidgetCommand());
        commands.put("click", new ClickLinkCommand());
        commands.put("test", new TestCommand());
        commands.put("rnex", new RestartNexCommand());
        commands.put("raidsl", new TestRaidsLootCommand());
        commands.put("pforce", new PForceMoveCmd());
        commands.put("projectile", new ProjectileCommand());

        commands.put("tasknames", new TaskNamesCommand());
        commands.put("taskamount", new TaskAmountCommand());
        commands.put("tabamounts", new TabAmountsCommand());
        commands.put("createserverlag", new CreateServerLagCommand());
        commands.put("dint", new DialogueInterfaceCommand());
        //commands.put("flood", new FloodCommand()); //TODO: Fix flood bots for new login service, it currenttly runs on game thread and freezes game
        commands.put("pnpc", new PNPCCommand());
        commands.put("npc", new SpawnNPCCommand());
        POScommand posCommand = new POScommand();
        commands.put("pos", posCommand);
        commands.put("coords", posCommand);
        commands.put("config", new ConfigCommand());
        commands.put("configall", new ConfigAllCommand());
        commands.put("gfx", new GFXCommand());
        commands.put("anim", new AnimationCommand());
        commands.put("int", new InterfaceCommand());
        commands.put("walkint", new WalkableInterfaceCommand());
        commands.put("shop", new ShopCommand());
        commands.put("cint", new ChatboxInterfaceCommand());
        commands.put("update", new UpdateServerCommand());
        commands.put("fn", new GetNpcIdCommand());
        commands.put("fo", new GetObjectIdCommand());
        commands.put("getid", new GetItemIdCommand());
        commands.put("finditem", new GetItemIdCommand());
        commands.put("fi", new GetItemIdCommand());
        commands.put("searchitem", new GetItemIdCommand());
        commands.put("tornre", new TornReloadCommand());
        Command tornToggle = new TornamentToggleCommand();
        commands.put("tornon", tornToggle);
        commands.put("tornoff", tornToggle);
        commands.put("torntoggle", tornToggle);
        commands.put("settornhours", new SetTornHoursCommand());
        commands.put("gettornhours", new GetTornHoursCommand());
        commands.put("ss", new SendStringCommand());
        commands.put("bank", new BankCommandCommand());
        commands.put("settornlobbytime", new SetTornLobbyTime());
        commands.put("settorntype", new SetTornType());
        commands.put("mkn", new MassKillNpc());
        commands.put("massgfx", new LoopGFX());
        commands.put("energy", new RunEnergyCommand());
        commands.put("toggledebug", new ToggleDebugCommand());
        commands.put("toggledebugmessages", new ToggleDebugCommand());
        commands.put("savealltp", new SaveAllTPCommand());
        commands.put("savetp", new SaveTPCommand());
        commands.put("raidsr", new RaidsrewardCommand());
        commands.put("olm", new StartOlmScriptCommand());

        /*
         * Owner commands
         */
        commands.put("givetbow", new TbowRaidsMysteryBoxCommand());
        commands.put("csw", new CheckServerWealthCommand());
        commands.put("kickall", new KickAllCommand());
        commands.put("tradepost", new TradingPostCommand());
        LazyCommands.init();
        KtCommands.INSTANCE.init();
    }

    public static void attempt(Player player, String command) {
        String[] parts = command.split(" ");
        if (parts.length == 0) // doing ::  with some spaces lol
            return;
        parts[0] = parts[0].toLowerCase();
        attempt(player, command, parts);
    }

    public static void attempt(Player player, String command, String[] parts) {
        Command c = CommandManager.commands.get(parts[0]);
        if (c != null) {
            if (c.canUse(player)) {
                try {
                    c.execute(player, command, parts);
                    List<String> ignore = Arrays.asList("teleto", "mute", "jail", "ipmute", "unipmute", "unmute", "ban", "macban", "ipban",
                        "unban", "teletome", "modzone", "unjail", "kick", "ipban", "unipban", "macban", "unmacban", "claimvote", "redeem", "item", "giveitem");
                    if(ignore.stream().noneMatch(command::startsWith)) {
                        Utils.sendDiscordInfoLog(player.getUsername() + " used command: ::" + command, "player_cmd");
                    }
                } catch (Exception e) {
                    player.message("Something went wrong with the command ::" + command +". Perhaps you entered it wrong?");
                    player.debug("Error %s", e);
                    logger.error("cmd ex", e);
                }
            } else {
                player.message("Invalid command.");
                player.debugMessage("command canUse returned false for this cmd "+parts[0]+".");
            }
        }
        Tile tele = locsTeles.get(parts[0]);
        if (tele != null) {
            if (player.getPlayerRights().isDeveloperOrGreater(player))
                player.teleport(tele);
            return;
        }
        if (c == null) {
            player.message("Invalid command.");
        }
    }
}
