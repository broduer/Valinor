package com.valinor.game.world.definition.loader.impl;

import com.google.gson.Gson;
import com.valinor.GameServer;
import com.valinor.fs.ItemDefinition;
import com.valinor.game.world.World;
import com.valinor.game.world.definition.loader.DefinitionLoader;
import com.valinor.game.world.definition.loader.PatrickTheMerchantValue;
import com.valinor.game.world.definition.loader.ProtectionValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 28, 2022
 */
public class PatrickTheMerchantLoader extends DefinitionLoader {

    private static final Logger logger = LogManager.getLogger(PatrickTheMerchantLoader.class);

    @Override
    public void load() throws Exception {
        try (FileReader reader = new FileReader(file())) {
            PatrickTheMerchantValue[] defs = new Gson().fromJson(reader, PatrickTheMerchantValue[].class);
            StringBuilder duplicates = new StringBuilder();
            int duplicateCount = 0;
            for (PatrickTheMerchantValue def : defs) {
                if (PatrickTheMerchantValue.definitions.containsKey(def.id)) {
                    duplicates.append(def.id).append(", ");
                    duplicateCount++;
                }
                PatrickTheMerchantValue.definitions.put(def.id, def);
                World.getWorld().definitions().get(ItemDefinition.class, def.id).patrickTheMerchantValue = def;
            }
            if (duplicateCount > 0) {
                logger.error("There are " + duplicateCount + " duplicate protection price JSON entries");
                logger.error(duplicates + " have duplicate protection price JSON values.");
            }
        }
    }

    @Override
    public String file() {
        return GameServer.properties().definitionsDirectory + "patrick_the_merchant.json";
    }
}
