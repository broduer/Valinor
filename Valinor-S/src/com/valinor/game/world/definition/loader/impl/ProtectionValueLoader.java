package com.valinor.game.world.definition.loader.impl;

import com.google.gson.Gson;
import com.valinor.GameServer;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.world.World;
import com.valinor.game.world.definition.loader.DefinitionLoader;
import com.valinor.game.world.definition.loader.ProtectionValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class ProtectionValueLoader extends DefinitionLoader {

    private static final Logger logger = LogManager.getLogger(ProtectionValueLoader.class);

    @Override
    public void load() throws Exception {
        try (FileReader reader = new FileReader(file())) {
            ProtectionValue[] defs = new Gson().fromJson(reader, ProtectionValue[].class);
            StringBuilder duplicates = new StringBuilder();
            int duplicateCount = 0;
            for (ProtectionValue def : defs) {
                if (ProtectionValue.definitions.containsKey(def.id)) {
                    duplicates.append(def.id).append(", ");
                    duplicateCount++;
                }
                ProtectionValue.definitions.put(def.id, def);
                World.getWorld().definitions().get(ItemDefinition.class, def.id).protectionValue = def;
            }
            if (duplicateCount > 0) {
                logger.error("There are " + duplicateCount + " duplicate protection price JSON entries");
                logger.error(duplicates + " have duplicate protection price JSON values.");
            }
        }
    }

    @Override
    public String file() {
        return GameServer.properties().definitionsDirectory + "protection_values.json";
    }
}
