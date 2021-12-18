package com.valinor.game.content.items;

import com.valinor.game.world.entity.mob.player.EquipSlot;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;

import static com.valinor.util.ItemIdentifiers.*;
import static com.valinor.util.ItemIdentifiers.VARROCK_ARMOUR_4;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since December 18, 2021
 */
public class ItemSet {

    public static double prospectorBonus(Player player) {
        double bonus = 1.0;
        Item helmet = player.getEquipment().get(EquipSlot.HEAD);
        Item jacket = player.getEquipment().get(EquipSlot.BODY);
        Item legs = player.getEquipment().get(EquipSlot.LEGS);
        Item boots = player.getEquipment().get(EquipSlot.FEET);

        if (helmet != null && helmet.getId() == PROSPECTOR_HELMET)
            bonus += 0.4;
        if (jacket != null && jacket.getId() == PROSPECTOR_JACKET)
            bonus += 0.8;
        if (legs != null && legs.getId() == PROSPECTOR_LEGS)
            bonus += 0.6;
        if (boots != null && boots.getId() == PROSPECTOR_BOOTS)
            bonus += 0.2;

        /* Whole set gives an additional 0.5% exp bonus */
        if (bonus >= 2.0)
            bonus += 0.5;

        return bonus;
    }

    public static double goldProspectorBonus(Player player) {
        double bonus = 1.0;
        Item helmet = player.getEquipment().get(EquipSlot.HEAD);
        Item jacket = player.getEquipment().get(EquipSlot.BODY);
        Item legs = player.getEquipment().get(EquipSlot.LEGS);
        Item boots = player.getEquipment().get(EquipSlot.FEET);

        if (helmet != null && helmet.getId() == PROSPECTOR_HELMET)
            bonus += 0.6;
        if (jacket != null && jacket.getId() == PROSPECTOR_JACKET)
            bonus += 1.0;
        if (legs != null && legs.getId() == PROSPECTOR_LEGS)
            bonus += 1.0;
        if (boots != null && boots.getId() == PROSPECTOR_BOOTS)
            bonus += 0.4;

        /* Whole set gives an additional 2% exp bonus */
        if (bonus >= 3.0)
            bonus += 2.0;

        return bonus;
    }

    public static double varrockDiaryArmour(Player player) {
        double chance = 1.0;
        Item body = player.getEquipment().get(EquipSlot.BODY);

        //When worn, 10% chance of mining 2 ores at once up to gold (with some exceptions), granting xp for both ores mined.
        //When worn, 10% chance of smelting 2 bars at once up to steel when using the Edgeville furnace. Edit: we made this for any furnace
        if (body != null && (body.getId() == VARROCK_ARMOUR_1 || body.getId() == VARROCK_ARMOUR_2 || body.getId() == VARROCK_ARMOUR_3 || body.getId() == VARROCK_ARMOUR_4))
            chance += 10.0;

        return chance;
    }

}
