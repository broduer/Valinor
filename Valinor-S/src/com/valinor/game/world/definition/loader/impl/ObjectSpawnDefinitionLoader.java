package com.valinor.game.world.definition.loader.impl;

import com.valinor.GameServer;
import com.valinor.game.world.definition.ObjectSpawnDefinition;
import com.valinor.game.world.definition.loader.DefinitionLoader;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.ObjectManager;
import com.google.gson.Gson;

import java.io.FileReader;

public class ObjectSpawnDefinitionLoader extends DefinitionLoader {

    @Override
    public void load() throws Exception {
        try (FileReader reader = new FileReader(file())) {
            ObjectSpawnDefinition[] defs = new Gson().fromJson(reader, ObjectSpawnDefinition[].class);
            for (ObjectSpawnDefinition def : defs) {
                if (!def.isEnabled())
                    continue;
                ObjectManager.addObj(new GameObject(def.getId(), def.getTile(), def.getType(), def.getFace()));
            }
        }
    }

    @Override
    public String file() {
        return GameServer.properties().definitionsDirectory + "object_spawns.json";
    }
}
