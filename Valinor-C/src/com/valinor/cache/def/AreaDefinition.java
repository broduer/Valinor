package com.valinor.cache.def;

import com.valinor.Client;
import com.valinor.ClientConstants;
import com.valinor.cache.Archive;
import com.valinor.cache.graphics.SimpleImage;
import com.valinor.io.Buffer;
import com.valinor.util.FileUtils;

import java.util.HashMap;

public final class AreaDefinition {

    public static int size;
    public static int mapFunctionsSize;
    public static AreaDefinition[] cache;
    public static HashMap<Integer, SimpleImage> sprites = new HashMap<>();
    private static int cacheIndex;
    private static Buffer area_data;
    private static int[] indices;

    public int id;
    public int spriteId = -1;
    public int field3294 = -1;
    public String name = "";
    public int field3296 = -1;
    public int field3297 = -1;
    public String[] actions;
    public int field3310 = -1;


    private AreaDefinition() {
        id = -1;
    }

    public static void clear() {
        indices = null;
        cache = null;
        area_data = null;
    }

    public static void init(Archive archive) {
        area_data = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "areas/areas.dat") : archive.get("areas.dat"));
        Buffer stream = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "areas/areas.idx") : archive.get("areas.idx"));

        size = stream.readUShort();
        mapFunctionsSize = stream.readUShort();

        indices = new int[size];
        int offset = 2;

        for (int _ctr = 0; _ctr < size; _ctr++) {
            indices[_ctr] = offset;
            offset += stream.readUShort();
        }

        cache = new AreaDefinition[10];

        for (int _ctr = 0; _ctr < 10; _ctr++) {
            cache[_ctr] = new AreaDefinition();
        }

        System.out.println("Areas read -> " + size);

    }

    public static SimpleImage getImage(int sprite) {
        return sprites.get(sprite);
    }

    public static AreaDefinition lookup(int area) {
        for (int count = 0; count < 10; count++) {
            if (cache[count].id == area) {
                return cache[count];
            }
        }
        cacheIndex = (cacheIndex + 1) % 10;
        AreaDefinition data = cache[cacheIndex];
        if (area >= 0) {
            area_data.pos = indices[area];
            data.readValues(area_data);
            if (!sprites.containsKey(data.spriteId)) {
                try {
                    sprites.put(data.spriteId, new SimpleImage(Client.singleton.mediaStreamLoader, "mapfunction", data.spriteId));
                } catch (Exception e) {
                    System.out.println("Missing Sprite: " + data.spriteId + " Using Shop Icon");
                    sprites.put(data.spriteId, new SimpleImage(Client.singleton.mediaStreamLoader, "mapfunction", 0));
                }
            }
        }
        return data;
    }

    public void readValues(Buffer buffer) {
        do {
            int opCode = buffer.readUnsignedByte();
            if (opCode == 0)
                return;
            if (opCode == 1)
                spriteId = buffer.readInt();
            else if (opCode == 2)
                field3294 = buffer.readInt();
            else if (opCode == 3)
                name = buffer.readNewString();
            else if (opCode == 4)
                field3296 = buffer.readInt();
            else if (opCode == 5)
                field3297 = buffer.readInt();
            else if (opCode == 6)
                field3296 = buffer.readInt();
            else if (opCode >= 6 && opCode < 11) {
                if (actions  == null)
                    actions = new String[5];
                actions[opCode - 6] = buffer.readNewString();
            } else if (opCode == 12)
                field3310 = buffer.readInt();

        } while (true);
    }

}
