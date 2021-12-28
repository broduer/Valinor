package com.valinor.game.world.entity.combat.method.impl.npcs.bosses.nightmare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 05, 2021
 */
public class NightmareEvent {

    //TODO
    /*private static Map<String, NightmareEvent> INSTANCES = new HashMap<String, NightmareEvent>();

    private DynamicMap map;

    private ArrayList<Player> players;

    private boolean inited = false;

    private Nightmare nightmare;

    private Position base;

    static {
        int[] orbs = { 24511, 24514, 24517 };
        int staff = 24422;
        int[] staffs = { 24423, 24424, 24425 };
        for (int i = 0; i <orbs.length; i++) {
            final int idx = i;
            ItemItemAction.register(orbs[idx], staffs[idx], (player, orbItem, staffItem) -> {
                player.getInventory().remove(orbs[idx], 1);
                player.getInventory().remove(staff, 1);
                player.getInventory().add(staffs[idx], 1);
            });
        }
    }

    private NightmareEvent(ArrayList<Player> players) {
        this.players = players;
        this.map = new DynamicMap().build(15515, 3);
        base = new Position(map.convertX(3840), map.convertY(9936), 3);
        nightmare = new Nightmare(9432, base);
        nightmare.transform(9432);
        nightmare.setTotems(new TotemPlugin[] { new TotemPlugin(9434, base.translated(23, 6, 0)), new TotemPlugin(9437, base.translated(39, 6, 0)), new TotemPlugin(9440, base.translated(23, 22, 0)), new TotemPlugin(9443, base.translated(39, 22, 0)) });
        for (TotemPlugin t : nightmare.getTotems()) {
            t.setNightmare(nightmare);
        }
        nightmare.spawn(base.translated(30, 13, 0));
        nightmare.addEvent(e-> {
            if (inited) {
                return;
            }
            nightmare.getMasks()[0].reset();
            nightmare.toggleShield();
            e.delay(1);
            for (Player p : this.players) {
                p.inMulti();
                p.getMovement().teleport(base.translated(32, 12));
            }
            e.delay(25);
            nightmare.transform(9430);
            nightmare.animate(8611);
            for (Player p : this.players) {
                p.sendMessage("<col=ff0000>The Nightmare has awoken!");
            }
            inited = true;
            nightmare.setStage(0);
            e.delay(8);
            nightmare.transform(9425);
            nightmare.animate(-1);
        });
    }

    public static NightmareEvent createInstance(ArrayList<Player> players) {
        NightmareEvent nme = new NightmareEvent(players);
        INSTANCES.put(players.get(0).getName().toLowerCase(), nme);
        return nme;
    }


    public static NightmareEvent joinInstance(String key, Player player) {
        NightmareEvent nme = INSTANCES.get(key.toLowerCase());
        if (nme == null) {
            player.sendMessage(key + " has no registered instances.");
        } else {
            nme.add(player);
        }
        return nme;
    }

    public static void hmSize() {
        System.out.println(INSTANCES.size());
    }

    public static void hmClear() {
        INSTANCES.clear();
    }

    private void add(Player player) {
        players.add(player);
        player.getMovement().teleport(base.translated(32, 12));
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
    */
}
