package com.valinor.game.world.definition.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 28, 2022
 */
public class PatrickTheMerchantValue {

    public static final Map<Integer, PatrickTheMerchantValue> definitions = new HashMap<>();

    public static final PatrickTheMerchantValue DEFAULT = new PatrickTheMerchantValue();

    public static PatrickTheMerchantValue get(int item) {
        return definitions.getOrDefault(item, DEFAULT);
    }

    public int id;
    public int price;
}
