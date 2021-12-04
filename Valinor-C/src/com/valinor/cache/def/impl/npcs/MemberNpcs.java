package com.valinor.cache.def.impl.npcs;

import com.valinor.cache.def.NpcDefinition;
import com.valinor.util.NpcIdentifiers;

import static com.valinor.util.CustomNpcIdentifiers.*;

/**
 * @author Patrick van Elderen | July, 12, 2021, 14:02
 * @see <a href="https://github.com/PVE95">Github profile</a>
 */
public class MemberNpcs {

    public static void unpack(int id) {
        NpcDefinition definition = NpcDefinition.get(id);

        if(id == ANCIENT_REVENANT_DARK_BEAST) {
            NpcDefinition.copy(definition, NpcIdentifiers.REVENANT_DARK_BEAST);
            definition.name = "Ancient revenant dark beast";
            definition.modelCustomColor4 = 235;
        }
        if(id == ANCIENT_REVENANT_ORK) {
            NpcDefinition.copy(definition, NpcIdentifiers.REVENANT_ORK);
            definition.name = "Ancient revenant ork";
            definition.modelCustomColor4 = 235;
        }
        if(id == ANCIENT_REVENANT_CYCLOPS) {
            NpcDefinition.copy(definition, NpcIdentifiers.REVENANT_CYCLOPS);
            definition.name = "Ancient revenant cyclops";
            definition.modelCustomColor4 = 235;
        }
        if(id == ANCIENT_REVENANT_DRAGON) {
            NpcDefinition.copy(definition, NpcIdentifiers.REVENANT_DRAGON);
            definition.name = "Ancient revenant dragon";
            definition.modelCustomColor4 = 235;
        }
        if(id == ANCIENT_REVENANT_KNIGHT) {
            NpcDefinition.copy(definition, NpcIdentifiers.REVENANT_KNIGHT);
            definition.name = "Ancient revenant knight";
            definition.modelCustomColor4 = 235;
        }
        if(id == ANCIENT_BARRELCHEST) {
            NpcDefinition.copy(definition, NpcIdentifiers.BARRELCHEST_6342);
            definition.name = "Ancient barrelchest";
            definition.modelCustomColor4 = 235;
        }
        if(id == ANCIENT_KING_BLACK_DRAGON) {
            NpcDefinition.copy(definition, NpcIdentifiers.KING_BLACK_DRAGON);
            definition.name = "Ancient king black dragon";
            definition.modelCustomColor4 = 235;
        }
        if(id == ANCIENT_CHAOS_ELEMENTAL) {
            NpcDefinition.copy(definition, NpcIdentifiers.CHAOS_ELEMENTAL);
            definition.name = "Ancient chaos elemental";
            definition.modelCustomColor4 = 235;
        }
    }
}
