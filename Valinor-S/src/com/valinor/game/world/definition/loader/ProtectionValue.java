package com.valinor.game.world.definition.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class ProtectionValue {

    public static final Map<Integer, ProtectionValue> definitions = new HashMap<>();

    public static final ProtectionValue DEFAULT = new ProtectionValue();

    public static ProtectionValue get(int item) {
        return definitions.getOrDefault(item, DEFAULT);
    }

    public int id;
    public String name;
    public int price;
    public int wikiPrice;
}
