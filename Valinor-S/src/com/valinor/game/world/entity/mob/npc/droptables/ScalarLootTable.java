package com.valinor.game.world.entity.mob.npc.droptables;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.valinor.fs.DefinitionRepository;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.content.skill.impl.slayer.Slayer;
import com.valinor.game.content.skill.impl.slayer.SlayerConstants;
import com.valinor.game.content.skill.impl.slayer.slayer_task.SlayerCreature;
import com.valinor.game.world.World;
import com.valinor.game.world.entity.AttributeKey;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.items.ground.GroundItem;
import com.valinor.game.world.items.ground.GroundItemHandler;
import com.valinor.game.world.position.areas.impl.WildernessArea;
import com.valinor.util.Color;
import com.valinor.util.CustomItemIdentifiers;
import com.valinor.util.ItemIdentifiers;
import com.valinor.util.Utils;
import nl.bartpelle.dawnguard.DataStore;
import org.apache.commons.lang3.math.Fraction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.SecureRandom;
import java.util.*;

import static com.valinor.util.CustomItemIdentifiers.*;

/**
 * Created by Bart on 6/8/2015.
 */
public class ScalarLootTable {

    public static class TableItem {
        public int id;
        public int points = 1;
        public int from;
        public int amount = 1;
        public int min, max;
        public boolean rare = false;
        public String name;
        public Fraction computedFraction;

        public Item convert() {
            return new Item(id, amount);
        }

        @Override
        public String toString() {
            return "TableItem{" +
                "id=" + id +
                ", points=" + points +
                ", from=" + from +
                ", amount=" + amount +
                ", min=" + min +
                ", max=" + max +
                ", rare=" + rare +
                ", name='" + name + '\'' +
                ", computedFraction=" + computedFraction +
                '}';
        }
    }

    public static Map<Integer, ScalarLootTable> registered = new HashMap<>();
    public TableItem[] items;
    public TableItem[] guaranteed;
    public ScalarLootTable[] tables;
    public int[] npcs;
    public int rndcap;
    private int tblrndcap;
    private boolean debug = false;
    private int rowmod = 1;
    public int points = 1;
    private int from;
    private boolean rareaffected;
    public String name;
    private int tblpts;
    private transient ScalarLootTable root;
    public int petRarity;
    public int petItem;
    public int odds = 0;

    public static void loadAll(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                loadAll(f);
            } else {
                try {
                    if (f.getName().endsWith(".json")) {
                        ScalarLootTable t = load(f);

                        t.process();

                        for (int n : t.npcs) {
                            registered.put(n, t);
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Error loading drop file " + f);
                    e.printStackTrace();
                }
            }
        }
    }

    private void process() {

        ArrayList<TableItem> temp = new ArrayList<>(getGuaranteedDrops());
        if (temp.contains(null)) {
            temp.removeIf(Objects::isNull);
            guaranteed = temp.toArray(TableItem[]::new);
        }

        if (items != null) {
            temp = new ArrayList<>(List.of(items));
            if (temp.contains(null)) {
                temp.removeIf(Objects::isNull);
                items = temp.toArray(TableItem[]::new);
            }
        }

        // Upscale it
        int current = 0;
        if (items != null) {
            for (TableItem item : items) {
                if (item.points == 0) // Filter out guaranteed drops
                    continue;

                item.from = current;
                current += item.points;

                if (item.rare)
                    rareaffected = true;
            }

            rndcap = current;
        }

        // Normalize the nested tables
        if (tables != null) {
            for (ScalarLootTable table : tables) {
                table.from = current;
                current += table.points;

                table.process();
            }

            rndcap = current;
        }

        calcRare();
    }

    private void recursiveCalcChances(int num, int denom) {
        int computed = ptsTotal();
        // Items..
        if (items != null) {
            for (TableItem item : items) {
                double chance = (double) (item.points * num) / (double) (denom * computed);
                item.computedFraction = Fraction.getFraction(chance);
            }
        }

        // Tables
        if (tables != null) {
            for (ScalarLootTable table : tables) {
                table.recursiveCalcChances(num * table.points, denom * computed);
            }
        }
    }

    private void setRoot(ScalarLootTable root) {
        this.root = root;
        if (tables != null) {
            for (ScalarLootTable table : tables) {
                table.setRoot(root);
            }
        }
    }

    public int ptsTotal() {
        int a = 0;
        if (items != null) {
            for (TableItem item : items) {
                a += item.points;
            }
        }
        if (tables != null) {
            for (ScalarLootTable table : tables) {
                a += table.points;
            }
        }
        return a;
    }

    private void calcRare() {
        // Ingest rare knowledge
        if (tables != null) {
            for (ScalarLootTable table : tables) {
                if (table.rareaffected)
                    rareaffected = true;
            }
        }
    }

    public static ScalarLootTable forNPC(int npc) {
        return registered.get(npc);
    }

    public ScalarLootTable randomTable(Random random, boolean ringOfWealth) {
        int drop = random.nextInt(tblrndcap - (ringOfWealth ? (rowmod == 0 ? 1 : rowmod) : 0));
        for (ScalarLootTable table : tables) {
            if (drop >= table.from && drop < table.from + table.points) {
                return table;
            }
        }

        return null;
    }

    public Item randomItem(Random random) {
        if (rndcap < 1) {
            return null;
        }

        int cap = rndcap;

        int drop = random.nextInt(cap < 1 ? 1 : cap);
        if (items != null) {
            for (TableItem item : items) {
                if (drop >= item.from && drop < item.from + item.points) {
                    if (item.min == 0) {
                        return new Item(item.id, item.amount);
                    } else {
                        return new Item(item.id, random(random, item.min, item.max));
                    }
                }
            }
        }

        // Try a table now
        if (tables != null && tables.length > 0) {
            for (ScalarLootTable table : tables) {
                if (drop >= table.from && drop < table.from + table.points) {
                    Item item = table.randomItem(random);
                    return item;
                }
            }
        }

        return null;
    }

    static int random(Random random, int min, int max) {
        final int n = Math.abs(max - min);
        return Math.min(min, max) + (n == 0 ? 0 : random.nextInt(n + 1));
    }

    public List<TableItem> getGuaranteedDrops() {
        return guaranteed == null ? Collections.emptyList() : Arrays.asList(guaranteed);
    }

    public List<Item> simulate(Random r, int samples) {
        List<Item> list = new LinkedList<>();
        Map<Integer, Integer> state = new HashMap<>();

        for (int i = 0; i < samples; i++) {
            Item random = randomItem(r);
            if (random != null)
                state.compute(random.getId(), (key, value) -> value == null ? random.getAmount() : (value + random.getAmount()));
        }

        state.forEach((k, v) -> list.add(new Item(k, v)));
        return list;
    }

    public static ScalarLootTable load(File file) {
        try {
            ScalarLootTable table = load(new String(Files.readAllBytes(file.toPath())));
            table.process();
            table.calcRare();
            table.calcRare();
            table.tblpts = table.ptsTotal();
            table.setRoot(table);
            table.recursiveCalcChances(1, 1);
            return table;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ScalarLootTable load(String json) {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.setLenient();
        Gson gson = builder.create();
        ScalarLootTable table = gson.fromJson(json, ScalarLootTable.class);
        table.process();
        table.tblpts = table.ptsTotal();
        table.setRoot(table);
        table.recursiveCalcChances(1, 1);
        return table;
    }

    public void rollForLarransKey(Npc npc, Player player) {
        var inWilderness = WildernessArea.inWilderness(player.tile());
        SlayerCreature task = SlayerCreature.lookup(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));

        if (inWilderness) {
            var isSlayerTask = task != null && task.matches(npc.id());
            var combatLvl = npc.def().combatlevel;
            var dropRate = combatLvl >= 1 && combatLvl <= 80 ? 1972 : 99;

            //if players are assigned to kill slayer monsters, the drop chance is increased by 25%
            if (isSlayerTask) {
                var reduction = dropRate * 25 / 100;
                dropRate -= reduction;
            }

            var larransLuck = player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.LARRANS_LUCK);
            if (larransLuck) {
                var reduction = dropRate * 15 / 100;
                dropRate -= reduction;
            }

            if (World.getWorld().rollDie(dropRate, 1)) {
                GroundItemHandler.createGroundItem(new GroundItem(new Item(ItemIdentifiers.LARRANS_KEY), player.tile(), player));
                player.message(Color.PURPLE.wrap("A larran's key appeared."));
            }
        }
    }

    public void rollForBrimstoneKey(Npc npc, Player player) {
        SlayerCreature task = SlayerCreature.lookup(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));
        var isSlayerTask = task != null && task.matches(npc.id());
        var master = Math.max(1, player.<Integer>getAttribOr(AttributeKey.SLAYER_MASTER, 0));
        if(master == Slayer.KONAR_QUO_MATEN_ID && isSlayerTask) {
            var combatLvl = npc.def().combatlevel;
            //var dropRate = combatLvl >= 100 ? 120 - (combatLvl / 5) : 100 + (100 - combatLvl) / 40;
            if (World.getWorld().rollDie(50, 1)) {
                player.message(Color.PURPLE.wrap("A brimstone key appeared."));
                GroundItemHandler.createGroundItem(new GroundItem(new Item(ItemIdentifiers.BRIMSTONE_KEY), player.tile(), player));
                Utils.sendDiscordInfoLog("Player " + player.getUsername() + " has received a brimstone key drop.", "brimstone_key_drop");
            }
        }
    }

    public void rollForKeyOfDrops(Player player, Npc npc) {
        SlayerCreature task = SlayerCreature.lookup(player.getAttribOr(AttributeKey.SLAYER_TASK_ID, 0));
        if (task != null && task.matches(npc.id()) && player.getSlayerRewards().getUnlocks().containsKey(SlayerConstants.KEY_OF_DROPS)) {
            int roll = 1000;
            if (World.getWorld().rollDie(roll, 1)) {
                player.message(Color.PURPLE.wrap("A key of drops appeared."));
                player.inventory().addOrDrop(new Item(CustomItemIdentifiers.KEY_OF_DROPS));
            }
        }
    }

    public Optional<Pet> rollForPet(Player player) {
        if (petItem > 0 && petRarity > 0) {
            //player.debugMessage(String.format("pet %d is 1/%d", petItem, petRarity));

            var roll = petRarity;
            var reduction = roll * player.dropRateBonus() / 100;
            roll -= reduction;

            if (World.getWorld().rollDie(player.hasPetOut("Jawa pet") ? roll / 2 : roll, 1)) {
                return Optional.ofNullable(Pet.getPetByItem(petItem));
            }
        }
        return Optional.empty();
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner;
        SecureRandom rand = new SecureRandom();
        DefinitionRepository repo = new DefinitionRepository(new DataStore("data/filestore", true), true);

        //while (true) {
        for (int i = 0; i < 10; i++) System.out.println();
        while (System.in.available() > 0) System.in.read();
        System.out.print("Drop file: ./data/combat/");
        scanner = new Scanner(System.in);
        String file = scanner.nextLine().trim();
        System.out.print("Number of kills: ");
        int kills = scanner.nextInt();

        ScalarLootTable root = load(new File("./data/combat/" + file));

        List<Item> simulate = root.simulate(rand, kills);
        simulate.sort((o1, o2) -> {
            int oo1 = kills / Math.max(1, o1.getAmount());
            int oo2 = kills / Math.max(1, o2.getAmount());
            return Integer.compare(oo1, oo2);
        });

        for (Item item : simulate) {
            int indiv = kills / Math.max(1, item.getAmount());
            System.out.println(item.getAmount() + " x " + repo.get(ItemDefinition.class, new Item(item.getId()).unnote(repo).getId()).name + " (1/" + indiv + ")");
        }

        System.out.println();
        System.out.println();
        //}
    }

    @Override
    public String toString() {
        return "ScalarLootTable{" +
            "items=" + Arrays.toString(items) +
            ", guaranteed=" + Arrays.toString(guaranteed) +
            ", tables=" + Arrays.toString(tables) +
            ", npcs=" + Arrays.toString(npcs) +
            ", rndcap=" + rndcap +
            ", tblrndcap=" + tblrndcap +
            ", debug=" + debug +
            ", rowmod=" + rowmod +
            ", points=" + points +
            ", from=" + from +
            ", rareaffected=" + rareaffected +
            ", name='" + name + '\'' +
            ", tblpts=" + tblpts +
            ", root=" + root.name +
            ", petRarity=" + petRarity +
            ", petItem=" + petItem +
            ", odds=" + odds +
            '}';
    }
}
