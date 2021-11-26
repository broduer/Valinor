package com.valinor.game.content.option_menu;

import com.valinor.game.action.impl.TeleportAction;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;

import java.util.HashMap;

/**
 * The constants for the Teleport Menu.
 *
 * @author Lennard
 *
 */
public class TeleportMenuConstants {

    public static HashMap<Integer, OptionMenu> getDefaultTeleports(final Player player) {
        final HashMap<Integer, OptionMenu> teleportOptions = new HashMap<>();

        teleportOptions.put(0,
            new OptionMenu("Taverley Dungeon",
                new String[] { "Blue Dragon", "Chaos Druid", "Baby Blue Dragon", "Black Demon", "Black Dragon",
                    "Hellhound", "Hill Giant", "Lesser Demon", "Skeleton", "Ghost", "Chaos Dwarf" },
                new TeleportAction(player, new Tile(2884, 9798))));

        teleportOptions.put(1, new OptionMenu("Edgeville Dungeon",
            new String[] { "Chaos Druid", "Black Demon", "Rat", "Giant Rat", "Giant Spider", "Zombie", "Skeleton",
                "Hobgoblin", "Hill Giant", "Moss Giant", "Thug", "Deadly Red Spider", "Earth Warrior",
                "Poison Spider" },
            new TeleportAction(player, new Tile(3097, 9868))));

        teleportOptions.put(2,
            new OptionMenu("Slayer Tower",
                new String[] { "Crawling Hand", "Banshee", "Infernal mage", "Bloodveld", "Aberrant Spectre",
                    "Gargoyle", "Nechryael", "Abyssal Demon" },
                new TeleportAction(player, new Tile(3097, 9868))));

        teleportOptions.put(3, new OptionMenu("Mourmer Tunnel", new String[] { "Dark Beast" },
            new TeleportAction(player, new Tile(2029, 4636))));

        teleportOptions.put(4,
            new OptionMenu("Asgarnian Ice Dungeon",
                new String[] { "Skeletal Wyvern", "Ice Giant", "Ice warrior", "Pirate", "Hobgoblin" },
                new TeleportAction(player, new Tile(3007, 9550))));

        teleportOptions.put(5,
            new OptionMenu("Brimhaven Dungeon",
                new String[] { "Wild Dog", "Moss Giant", "Fire Giant", "Greater Demon", "Black Demon",
                    "Baby Green Dragon", "Baby Red dragon", "Red dragon", "Bronze Dragon", "Iron Dragon",
                    "Steel Dragon" },
                new TeleportAction(player, new Tile(2713, 9564))));

        teleportOptions.put(6,
            new OptionMenu("Lumbridge Swamp Caves",
                new String[] { "Cave Crawler", "Cave Slime", "Cave Bug", "Rockslug", "Wall Beast" },
                new TeleportAction(player, new Tile(3168, 9572))));

        teleportOptions.put(7,
            new OptionMenu("Stronghold Slayer Cave",
                new String[] { "Aberrant Spectre", "Abyssal Demon", "Ankou", "Baby Black Dragon", "Black Demon",
                    "Black Dragon", "Bloodveld", "Blue Dragon", "Bronze Dragon", "Fire Giant", "Gargoyle",
                    "Greater Demon", "Hellhound", "Iron Dragon", "Kalphite", "Nechryael", "Steel Dragon",
                    "Waterfiend" },
                new TeleportAction(player, new Tile(2430, 3424))));

        teleportOptions.put(8,
            new OptionMenu("Catacombs",
                new String[] { "Warped Jelly", "Moss giant", "Mutated Bloodveld", "King Sand Crab", "Dust devil",
                    "Dagannoth", "Cyclops", "Twisted Banshee", "Skeleton", "Shade", "Possessed pickaxe",
                    "Lesser demon", "Magic axe", "Hill giant", "Ghost", "Deviant spectre",
                    "Ankou", "Steel dragon", "Iron dragon", "Hellhound", "Fire giant", "Brutal red dragon",
                    "Brutal blue dragon", "Brutal black dragon", "Bronze dragon", "Greater nechryael", "Greater demon",
                "Black demon", "Abyssal demon"},
                new TeleportAction(player, new Tile(1666, 10048))));

        teleportOptions.put(9,
            new OptionMenu("Karuulm Dungeon",
                new String[] { "Drake", "Wyrm", "Hydra", "Alchemical Hydra"},
                new TeleportAction(player, new Tile(1311, 3812))));

        teleportOptions.put(10,
            new OptionMenu("Lithkren",
        new String[] { "Aadamant Dragon", "Rune Dragon"},
            new TeleportAction(player, new Tile(3565, 3998))));

        teleportOptions.put(11,
            new OptionMenu("Brine Rat Cavern",
                new String[] { "Brine Rat" },
                new TeleportAction(player, new Tile(2692, 10124))));

        teleportOptions.put(12,
            new OptionMenu("Mos Le'Harm",
                new String[] { "Cave Horror" },
                new TeleportAction(player, new Tile(3826, 9425))));

        teleportOptions.put(13,
            new OptionMenu("Ancient Cavern", new String[] { "Waterfiend", "Brutal Green Dragon", "Mithril Dragon" },
                new TeleportAction(player, new Tile(1746, 5327, 0))));

        teleportOptions.put(14, new OptionMenu("Waterfall Dungeon", new String[] { "Fire Giant" },
            new TeleportAction(player, new Tile(2575, 9863))));

        teleportOptions.put(15, new OptionMenu("Experiment Cave", new String[] { "Experiment" },
            new TeleportAction(player, new Tile(3577, 9927))));

        teleportOptions.put(16,
            new OptionMenu(
                "Fremennik Slayer Dungeon", new String[] { "Cave Crawler", "Rockslug", "Cockatrice",
                "Pyrefiend", "Basilisk", "Jelly", "Turoth", "Kurask" },
                new TeleportAction(player, new Tile(2807, 10002))));

        teleportOptions.put(17, new OptionMenu("Fossil Wyverns", new String[] { "Spitting Wyvern", "Taloned Wyvern", "Long-tailed Wyvern", "Ancient Wyvern" },
            new TeleportAction(player, new Tile(3609, 10278))));

        teleportOptions.put(18, new OptionMenu("Lighthouse", new String[] { "Dagannoth" },
            new TeleportAction(player, new Tile(2509, 3634))));

        teleportOptions.put(19, new OptionMenu("Rock crabs", new String[] { "Rock Crab" },
            new TeleportAction(player, new Tile(2673, 3714))));

        teleportOptions.put(20, new OptionMenu("Sand crabs", new String[] { "Rock Crab" },
            new TeleportAction(player, new Tile(1728, 3463))));

        teleportOptions.put(21,
            new OptionMenu("Smoke Dungeon", new String[] { "Dust Devil", "Fire Giant", "Pyrefiend" },
                new TeleportAction(player, new Tile(3206, 9379))));

        teleportOptions.put(22,
            new OptionMenu("Home", new String[] { "Trade", "Trading", "Market", "Pking", "Edgeville" },
                new TeleportAction(player, new Tile(3084, 3503))));

        teleportOptions.put(23, new OptionMenu("<img=1049>Graveyard", new String[] { "Wilderness", "Graveyard" },
            new TeleportAction(player, new Tile(2974, 3748))));

        teleportOptions.put(24, new OptionMenu("Varrock Multi", new String[] { "Wilderness", "Varrock Multi" },
            new TeleportAction(player, new Tile(3243, 3518))));

        teleportOptions.put(25, new OptionMenu("Edgeville", new String[] { "Wilderness", "Edgeville" },
            new TeleportAction(player, new Tile(3084, 3503))));

        teleportOptions.put(26, new OptionMenu("Mage Bank", new String[] { "Wilderness", "Mage Bank" },
            new TeleportAction(player, new Tile(2539, 4716))));

        teleportOptions.put(27,
            new OptionMenu("<img=1049>West Dragons",
                new String[] { "Wilderness", "Low Risk", "Green Dragon", "Varrock West" },
                new TeleportAction(player, new Tile(2982, 3603))));

        teleportOptions.put(28,
            new OptionMenu("<img=1049>East Dragons",
                new String[] { "Wilderness", "high Risk", "Green Dragon", "Varrock East" },
                new TeleportAction(player, new Tile(3366, 3634))));

        teleportOptions.put(29, new OptionMenu("Corporeal Beast Lair", new String[] { "Boss", "Corporal Beast" },
            new TeleportAction(player, new Tile(2969, 4382, 2))));

        teleportOptions.put(30,
            new OptionMenu("<img=1049>Rogue's Castle", new String[] { "Boss", "Wilderness", "Chaos Elemental" },
                new TeleportAction(player, new Tile(3307, 3916, 0))));

        teleportOptions.put(31, new OptionMenu("Abyssal Nexus", new String[] { "Boss", "Abyssal Sire" },
            new TeleportAction(player, new Tile(3039, 4772, 0))));

        teleportOptions.put(32, new OptionMenu("Cerberus Lair", new String[] { "Boss", "Cerberus" },
            new TeleportAction(player, new Tile(1292,1252, 0))));

        teleportOptions.put(33,
            new OptionMenu("Godwars Dungeon",
                new String[] { "Boss", "gwd", "Godwars", "Bandos", "Saradomin", "Zamorak", "Armadyl" },
                new TeleportAction(player, new Tile(2881, 5310, 2))));

        teleportOptions.put(34, new OptionMenu("Mole Lair", new String[] { "Boss", "Giant Mole" },
            new TeleportAction(player, new Tile(1760, 5163))));

        teleportOptions.put(35, new OptionMenu("Kalphite Lair", new String[] { "Boss", "Kq", "Kalphite Queen" },
            new TeleportAction(player, new Tile(3484, 9510, 2))));

        teleportOptions.put(36,
            new OptionMenu("Dagannoth Dungeon", new String[] { "Boss", "Dagannoth", "Rex", "Prime", "Supreme" },
                new TeleportAction(player, new Tile(2442, 10147, 0))));

        teleportOptions.put(37,
            new OptionMenu("King Black Dragon Lair", new String[] { "Boss", "KBD", "King Black Dragon" },
                new TeleportAction(player, new Tile(2271, 4680, 0))));

        teleportOptions.put(38, new OptionMenu("Kraken Cave", new String[] { "Boss", "Kraken" },
            new TeleportAction(player, new Tile(2280, 10016, 0))));

        teleportOptions.put(39, new OptionMenu("Smoke Devil Cave", new String[] { "Boss", "Thermonuclear Smoke Devil" },
            new TeleportAction(player, new Tile(2404, 9417, 0))));

        teleportOptions.put(40, new OptionMenu("Zulrah's Island", new String[] { "Boss", "Zulrah" },
            new TeleportAction(player, new Tile(2204, 3056, 0))));

        teleportOptions.put(41, new OptionMenu("<img=1049>Callisto", new String[] { "Boss", "Callisto" },
            new TeleportAction(player, new Tile(3307, 3837, 0))));

        teleportOptions.put(42, new OptionMenu("<img=1049>Chaos Fanatic", new String[] { "Boss", "Chaos Fanatic" },
            new TeleportAction(player, new Tile(2992, 3851, 0))));

        teleportOptions.put(43, new OptionMenu("<img=1049>Crazy Archeologist", new String[] { "Boss", "Crazy Archeologist" },
            new TeleportAction(player, new Tile(2976, 3694, 0))));

        teleportOptions.put(44, new OptionMenu("Demonic Gorillas", new String[] { "Boss", "Demonic Gorillas" },
            new TeleportAction(player, new Tile(2128, 5647, 0))));

        teleportOptions.put(45, new OptionMenu("Lizardman Shaman", new String[] { "Boss", "Lizardman Shaman" },
            new TeleportAction(player, new Tile(1420, 3715, 0))));

        teleportOptions.put(46, new OptionMenu("<img=1049>Venenatis", new String[] { "Boss", "Venenatis" },
            new TeleportAction(player, new Tile(3319, 3745, 0))));

        teleportOptions.put(47, new OptionMenu("<img=1049>Vet'ion", new String[] { "Boss", "Vet'ion" },
            new TeleportAction(player, new Tile(3239, 3783, 0))));

        teleportOptions.put(48, new OptionMenu("<img=1049>Scorpia", new String[] { "Boss", "Scorpia" },
            new TeleportAction(player, new Tile(3232, 3950, 0))));

        teleportOptions.put(49, new OptionMenu("Vorkath", new String[] { "Boss", "Vorkath" },
            new TeleportAction(player, new Tile(2273, 4049, 0))));

        teleportOptions.put(50, new OptionMenu("World Boss", new String[] { "Boss", "World Boss" },
            new TeleportAction(player, null,true, "World Boss")));

        teleportOptions.put(51, new OptionMenu("Alchemical Hydra", new String[] { "Boss", "Alchemical Hydra" },
            new TeleportAction(player, new Tile(1354, 10258,0))));

        teleportOptions.put(52, new OptionMenu("<img=1049>Barrelchest", new String[] { "Boss", "Barrelchest" },
            new TeleportAction(player, new Tile(3287, 3884,0))));

        teleportOptions.put(53, new OptionMenu("Corrupted Nechryarch", new String[] { "Boss", "Corrupted Nechryarch" },
            new TeleportAction(player, new Tile(1885, 3865,0))));

        teleportOptions.put(54, new OptionMenu("<img=1049>Revenants", new String[] { "Revenants", "revs", "rev cave" },
            new TeleportAction(player, new Tile(3127, 3832,0))));

        teleportOptions.put(55, new OptionMenu("Raids", new String[] { "Boss", "Raids" },
            new TeleportAction(player, new Tile(1245, 3561,0))));

        teleportOptions.put(56, new OptionMenu("Duel Arena", new String[] { "Minigames", "Duel Arena", "Staking" },
            new TeleportAction(player, new Tile(3365, 3265, 0))));

        teleportOptions.put(57, new OptionMenu("Barrows", new String[] { "Minigames", "Barrows" },
            new TeleportAction(player, new Tile(3565, 3316, 0))));

        teleportOptions.put(58, new OptionMenu("Fight Caves", new String[] { "Minigames", "Fight Caves", "Fire Cape" },
            new TeleportAction(player, new Tile(2480, 5175, 0))));

        teleportOptions.put(59,
            new OptionMenu("Warrior's Guild", new String[] { "Minigames", "Warrior Guild", "Defender" },
                new TeleportAction(player, new Tile(2841, 3538, 0))));

        teleportOptions.put(60, new OptionMenu("Pest Control", new String[] { "Minigames", "Pest Control", "Void" },
            new TeleportAction(player, new Tile(2658, 2649, 0))));

        teleportOptions.put(61, new OptionMenu("Brimhaven Agility", new String[] { "Minigames", "Brimhaven Agility" },
            new TeleportAction(player, new Tile(0, 0))));

        teleportOptions.put(62, new OptionMenu("Varrock", new String[] { "Cities", "Varrock" },
            new TeleportAction(player, new Tile(3110, 3424))));

        teleportOptions.put(63, new OptionMenu("Falador", new String[] { "Cities", "Falador" },
            new TeleportAction(player, new Tile(2965, 3380))));

        teleportOptions.put(64, new OptionMenu("Lumbridge", new String[] { "Cities", "Lumbridge" },
            new TeleportAction(player, new Tile(3223, 3218))));

        teleportOptions.put(65, new OptionMenu("Draynor", new String[] { "Cities", "Draynor" },
            new TeleportAction(player, new Tile(3093, 3244))));

        teleportOptions.put(66, new OptionMenu("Camelot", new String[] { "Cities", "Camelot" },
            new TeleportAction(player, new Tile(2757, 3477))));

        teleportOptions.put(67, new OptionMenu("Wintertodt", new String[] { "Wintertodt", "Minigames", "Firemaking" },
            new TeleportAction(player, new Tile(1630, 3947))));

        return teleportOptions;
    }

}
