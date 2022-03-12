package com.valinor.cache.def;

import com.valinor.Client;
import com.valinor.ClientConstants;
import com.valinor.cache.Archive;
import com.valinor.cache.anim.Animation;
import com.valinor.cache.config.VariableBits;
import com.valinor.cache.def.impl.ObjectManager;
import com.valinor.collection.TempCache;
import com.valinor.entity.model.Model;
import com.valinor.io.Buffer;
import com.valinor.net.requester.ResourceProvider;
import com.valinor.util.FileUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public final class ObjectDefinition {

    public static void init(Archive archive) {
        data_buffer = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "/objects/loc.dat") : archive.get("loc.dat"));
        Buffer index_buffer = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "/objects/loc.idx") : archive.get("loc.idx"));
        length = index_buffer.readUShort();

        System.out.printf("Loaded %d objects loading OSRS version %d and SUB version %d%n", length, ClientConstants.OSRS_DATA_VERSION, ClientConstants.OSRS_DATA_SUB_VERSION);

        stream_indices = new int[length];
        int offset = 2;
        for (int index = 0; index < length; index++) {
            stream_indices[index] = offset;
            offset += index_buffer.readUShort();
        }

        cache = new ObjectDefinition[20];

        for (int index = 0; index < 20; index++) {
            cache[index] = new ObjectDefinition();
        }
    }

    public void decode(Buffer buffer) {
        do {
            int opcode = buffer.readUByte();
            if (opcode == 0)
                break;

            if (opcode == 1) {
                int length = buffer.readUByte();
                if (length > 0) {
                    if (modelIds == null || isLowDetail) {
                        models = new int[length];
                        modelIds = new int[length];
                        for (int index = 0; index < length; index++) {
                            modelIds[index] = buffer.readUShort();
                            models[index] = buffer.readUByte();
                        }
                    } else {
                        buffer.pos += length * 3;
                    }
                }
            } else if (opcode == 2)
                name = buffer.readString();
            else if (opcode == 3)
                description = buffer.readString();
            else if (opcode == 5) {
                int length = buffer.readUByte();
                if (length > 0) {
                    if (modelIds == null || isLowDetail) {
                        models = null;
                        modelIds = new int[length];
                        for (int index = 0; index < length; index++)
                            modelIds[index] = buffer.readUShort();
                    } else {
                        buffer.pos += length * 2;
                    }
                }
            } else if (opcode == 14)
                sizeX = buffer.readUByte();
            else if (opcode == 15)
                sizeY = buffer.readUByte();
            else if (opcode == 17)
                solid = false;
            else if (opcode == 18)
                walkable = false;
            else if (opcode == 19)
                interact_state = buffer.readUByte();//(buffer.readUnsignedByte() == 1);
            else if (opcode == 21)
                contour_to_tile = true;
            else if (opcode == 22)
                nonFlatShading = false;
            else if (opcode == 23)
                modelClipped = true;
            else if (opcode == 24) {
                animationId = buffer.readUShort();
                if (animationId == 65535)
                    animationId = -1;
            } else if (opcode == 28)
                decor_offset = buffer.readUByte();
            else if (opcode == 29)
                ambient = buffer.readSignedByte();
            else if (opcode == 39)
                contrast = buffer.readSignedByte();
            else if (opcode >= 30 && opcode < 35) {
                if (actions == null)
                    actions = new String[5];
                actions[opcode - 30] = buffer.readString();
                if (actions[opcode - 30].equalsIgnoreCase("hidden"))
                    actions[opcode - 30] = null;
            } else if (opcode == 40) {
                int length = buffer.readUByte();
                recolorFrom = new int[length];
                recolorTo = new int[length];
                for (int index = 0; index < length; index++) {
                    recolorFrom[index] = buffer.readUShort();
                    recolorTo[index] = buffer.readUShort();
                }
            } else if (opcode == 41) {
                int length = buffer.readUByte();
                retextureFrom = new short[length];
                retextureTo = new short[length];
                for (int index = 0; index < length; index++) {
                    retextureFrom[index] = (short) buffer.readUShort();
                    retextureTo[index] = (short) buffer.readUShort();
                }
            } else if (opcode == 61) {
                category = buffer.readUShort();
            } else if (opcode == 62)
                rotated = true;
            else if (opcode == 64)
                clipped = false;
            else if (opcode == 65)
                modelSizeX = buffer.readUShort();
            else if (opcode == 66)
                modelHeight = buffer.readUShort();
            else if (opcode == 67)
                modelSizeY = buffer.readUShort();
            else if (opcode == 68)
                mapSceneId = buffer.readUShort();
            else if (opcode == 69)
                orientation = buffer.readUByte();
            else if (opcode == 70)
                offsetX = buffer.readSignedShort();
            else if (opcode == 71)
                offsetHeight = buffer.readSignedShort();
            else if (opcode == 72)
                offsetY = buffer.readSignedShort();
            else if (opcode == 73)
                obstructs_ground = true;
            else if (opcode == 74)
                isSolid = true;
            else if (opcode == 75)
                merge_interact_state = buffer.readUByte();
            else if (opcode == 77 || opcode == 92) {
                varbit = buffer.readUShort();

                if (varp == 0xFFFF) {
                    varp = -1;
                }

                varp = buffer.readUShort();

                if (varbit == 0xFFFF) {
                    varbit = -1;
                }

                int value = -1;

                if (opcode == 92) {
                    value = buffer.readUShort();

                    if (value == 0xFFFF) {
                        value = -1;
                    }
                }

                int len = buffer.readUnsignedByte();

                transforms = new int[len + 2];
                for (int i = 0; i <= len; ++i) {
                    transforms[i] = buffer.readUShort();
                    if (transforms[i] == 0xFFFF) {
                        transforms[i] = -1;
                    }
                }
                transforms[len + 1] = value;
            } else if (opcode == 78) {
                ambientSoundId = buffer.readUShort();
                anInt2083 = buffer.readUByte();
            } else if (opcode == 79) {
                anInt2112 = buffer.readUShort();
                anInt2113 = buffer.readUShort();
                anInt2083 = buffer.readUByte();
                int length = buffer.readUByte();
                int[] anims = new int[length];
                for (int index = 0; index < length; index++) {
                    anims[index] = buffer.readUShort();
                }
            } else if (opcode == 81) {
                clipType = buffer.readUnsignedByte();
            } else if (opcode == 82) {
                mapIconId = buffer.readUShort();//AreaType
            } else if(opcode == 89) {
                randomAnimStart = false;
            } else if (opcode == 249) {
                int length = buffer.readUnsignedByte();

                Map<Integer, Object> params = new HashMap<>(length);
                for (int i = 0; i < length; i++)
                {
                    boolean isString = buffer.readUnsignedByte() == 1;
                    int key = buffer.read24Int();
                    Object value;

                    if (isString) {
                        value = buffer.readString();
                    } else {
                        value = buffer.readInt();
                    }

                    params.put(key, value);
                }

                this.params = params;
            } else {
                //System.err.printf("Error unrecognised {Objects} opcode: %d%n%n", opcode);
            }
        } while (true);

        post_decode();
    }

    public void post_decode() {
        if (interact_state == -1) {
            interact_state = 0;
            if (name != null && !name.equalsIgnoreCase("null")) {
                if (modelIds != null && (models == null || models[0] == 10))
                    interact_state = 1;//1

                if (actions != null)
                    interact_state = 1;

            }
        }
        if (isSolid) {
            solid = false;
            walkable = false;
        }
        if (merge_interact_state == -1)
            merge_interact_state = solid ? 1 : 0;

    }

    public static ObjectDefinition get(int id) {
        if (id > stream_indices.length) {
            id = stream_indices.length - 1;
        }

        if (id == 25913)
            id = 15552;

        if (id == 25916 || id == 25926)
            id = 15553;

        if (id == 25917)
            id = 15554;

        for (int index = 0; index < 20; index++) {
            if (cache[index].id == id) {
                return cache[index];
            }
        }

        cache_index = (cache_index + 1) % 20;
        ObjectDefinition def = cache[cache_index];
        data_buffer.pos = stream_indices[id];
        def.id = id;
        def.set_defaults();
        def.decode(data_buffer);

        if (def.id == 29308)//wintertoldt snow storm 1639 // 3997 cheap fix
            def.nonFlatShading = false;

        if (def.id >= 29167 && def.id <= 29225) {
            def.sizeX = 1;
            def.solid = false;
            def.actions = new String[]{"Take", null, null, null, null};
        }

        if (def.id == 14924) {
            def.sizeX = 1;
        }

        if (ClientConstants.WILDERNESS_DITCH_DISABLED) {
            if (id == 23271) {
                def.modelIds = null;
                def.interact_state = 0;
                def.solid = false;
                return def;
            }
        }

        if(id == 31484) {
            def.name = "Haunted chest";
            def.modelIds = new int[] {55630};
        }

        /*if(def.id > 16500) {
            if(def.delayShading == true)
                def.delayShading = false;

        }*/
        ObjectManager.get(id);

        /*if (def.name == null || def.name.equalsIgnoreCase("null"))
            def.name = "weee";

        def.interact_state = 1;*/
        return def;
    }

    public void set_defaults() {
        modelIds = null;
        models = null;
        name = null;
        description = null;
        recolorFrom = null;
        recolorTo = null;
        retextureTo = null;
        retextureFrom = null;
        sizeX = 1;
        sizeY = 1;
        solid = true;
        walkable = true;
        interact_state = -1;
        contour_to_tile = false;
        nonFlatShading = false;
        modelClipped = false;
        animationId = -1;
        decor_offset = 16;
        ambient = 0;
        contrast = 0;
        actions = new String[5];
        mapIconId = -1;
        mapSceneId = -1;
        rotated = false;
        clipped = true;
        modelSizeX = 128;
        modelHeight = 128;
        modelSizeY = 128;
        orientation = 0;
        offsetX = 0;
        offsetHeight = 0;
        offsetY = 0;
        obstructs_ground = false;
        isSolid = false;
        merge_interact_state = -1;
        varbit = -1;
        varp = -1;
        transforms = null;
    }

    public void passive_request_load(ResourceProvider provider) {
        if (modelIds == null)
            return;

        for (int modelId : modelIds) provider.passive_request(modelId & 0xffff, 0);

    }

    public Model get_object(int type, int orientation, int cosine_y, int sine_y, int cosine_x, int sine_x,
                            int animation_id) {
        Model model = get_animated_model(type, animation_id, orientation);

        if (model == null)
            return null;

        if (contour_to_tile || nonFlatShading) {
            model = new Model(contour_to_tile, nonFlatShading, model);
        }

        if (contour_to_tile) {
            int height = (cosine_y + sine_y + cosine_x + sine_x) / 4;
            for (int vertex = 0; vertex < model.vertices; vertex++) {
                int start_x = model.vertex_x[vertex];
                int start_y = model.vertex_z[vertex];
                int y = cosine_y + ((sine_y - cosine_y) * (start_x + 64)) / 128;
                int x = sine_x + ((cosine_x - sine_x) * (start_x + 64)) / 128;
                int undulation_offset = y + ((x - y) * (start_y + 64)) / 128;
                model.vertex_y[vertex] += undulation_offset - height;
            }
            model.computeSphericalBounds();
        }
        return model;
    }

    public boolean group_cached(int type) {
        if (models == null) {
            if (modelIds == null)
                return true;

            if (type != 10)
                return true;

            boolean cached = true;
            for (int index = 0; index < modelIds.length; index++)
                cached &= Model.cached(modelIds[index]);

            return cached;
        }
        for (int index = 0; index < models.length; index++)
            if (models[index] == type)
                return Model.cached(modelIds[index]);

        return true;
    }

    public boolean cached() {
        if (modelIds == null)
            return true;

        boolean cached = true;
        for (int model_id : modelIds) cached &= Model.cached(model_id);

        return cached;
    }

    public ObjectDefinition get_configs() {
        int setting_id = -1;
        if (varbit != -1) {
            VariableBits bit = VariableBits.cache[varbit];
            int setting = bit.configId;
            int low = bit.leastSignificantBit;
            int high = bit.mostSignificantBit;
            int mask = Client.BIT_MASKS[high - low];
            setting_id = Client.singleton.settings[setting] >> low & mask;
        } else if (varp != -1)
            setting_id = Client.singleton.settings[varp];

        if (setting_id < 0 || setting_id >= transforms.length || transforms[setting_id] == -1)
            return null;
        else
            return get(transforms[setting_id]);
    }

    public Model get_animated_model(int type, int animation_id, int orientation) {
        Model model = null;
        long key;
        if (models == null) {
            if (type != 10)
                return null;

            key = (long) ((id << 6) + orientation) + ((long) (animation_id + 1) << 32);
            Model cached = (Model) model_cache.get(key);
            if (cached != null)
                return cached;

            if (modelIds == null)
                return null;

            boolean invert = rotated ^ (orientation > 3);
            int length = modelIds.length;
            for (int index = 0; index < length; index++) {
                int invert_id = modelIds[index];
                if (invert)
                    invert_id += 0x10000;

                model = (Model) animated_model_cache.get(invert_id);
                if (model == null) {
                    model = Model.get(invert_id & 0xffff);
                    if (model == null)
                        return null;

                    if (invert)
                        model.invert();

                    animated_model_cache.put(model, invert_id);
                }
                if (length > 1)
                    modelData[index] = model;

            }
            if (length > 1)
                model = new Model(length, modelData, true);//fixes rotating textures on objects

        } else {
            int model_id = -1;
            for (int index = 0; index < models.length; index++) {
                if (models[index] != type)
                    continue;

                model_id = index;
                break;
            }
            if (model_id == -1)
                return null;

            key = (long) ((id << 8) + (model_id << 3) + orientation) + ((long) (animation_id + 1) << 32);
            Model cached = (Model) model_cache.get(key);
            if (cached != null)
                return cached;

            model_id = modelIds[model_id];
            boolean invert = rotated ^ (orientation > 3);
            if (invert)
                model_id += 0x10000;

            model = (Model) animated_model_cache.get(model_id);
            if (model == null) {
                model = Model.get(model_id & 0xffff);
                if (model == null)
                    return null;

                if (invert)
                    model.invert();

                animated_model_cache.put(model, model_id);
            }
        }
        boolean scale = modelSizeX != 128 || modelHeight != 128 || modelSizeY != 128;
        boolean translate = offsetX != 0 || offsetHeight != 0 || offsetY != 0;
        Model animated_model = new Model(recolorFrom == null, Animation.noAnimationInProgress(animation_id), orientation == 0 && animation_id == -1 && !scale && !translate, retextureFrom == null, model);
        if (animation_id != -1) {
            animated_model.skin();
            animated_model.interpolate(animation_id);
            animated_model.face_skin = null;
            animated_model.vertex_skin = null;
        }
        while (orientation-- > 0)
            animated_model.rotate_90();

        if (recolorFrom != null) {
            for (int index = 0; index < recolorFrom.length; index++)
                animated_model.recolor(recolorFrom[index], recolorTo[index]);

        }
        if (retextureFrom != null) {
            for (int index = 0; index < retextureFrom.length; index++) {
                animated_model.retexture(retextureFrom[index], retextureTo[index]);
            }
        }

        if (scale)
            animated_model.scale(modelSizeX, modelSizeY, modelHeight);

        if (translate)
            animated_model.translate(offsetX, offsetHeight, offsetY);

        animated_model.light(60 + ambient, 768 + contrast, -50, -10, -50, !nonFlatShading); // LocoPk
        if (merge_interact_state == 1) {
            animated_model.obj_height = animated_model.model_height;
        }
        animated_model_cache.put(animated_model, key);
        return animated_model;
    }

    public static void release() {
        model_cache = null;
        animated_model_cache = null;
        stream_indices = null;
        cache = null;
        data_buffer = null;
    }

    public ObjectDefinition() {
        id = -1;
    }

    public static int length;
    public static int cache_index;
    public static boolean isLowDetail = ClientConstants.OBJECT_DEFINITION_LOW_MEMORY;
    public static Buffer data_buffer;
    public static ObjectDefinition[] cache;
    public static int[] stream_indices;
    public static final Model[] modelData = new Model[4];
    public static TempCache model_cache = new TempCache(500);
    public static TempCache animated_model_cache = new TempCache(30);

    public int id;
    public int sizeX;
    public int sizeY;
    public int animationId;
    public int orientation;

    public int modelSizeX;
    public int modelHeight;
    public int modelSizeY;
    public int offsetX;
    public int offsetHeight;
    public int offsetY;
    public int mapIconId;
    public int mapSceneId;
    public int interact_state;
    public int decor_offset;
    public int merge_interact_state;
    public int varp;
    public int varbit;
    public int ambientSoundId;
    public int anInt2083;
    public int anInt2112;
    public int anInt2113;
    public int[] soundEffectIds;
    int clipType;
    public boolean randomAnimStart;
    public Map<Integer, Object> params = null;

    public int[] modelIds;
    public int[] transforms;
    public int[] models;

    public int[] recolorFrom;
    public int[] recolorTo;

    public short[] retextureFrom;
    public short[] retextureTo;

    public String name;
    public String description;
    public String[] actions;

    public int contrast;
    public byte ambient;

    public boolean rotated;
    public boolean walkable;
    public boolean contour_to_tile;
    public boolean modelClipped;
    public boolean isSolid;
    public boolean solid;
    public boolean clipped;
    public boolean nonFlatShading;//
    public boolean obstructs_ground;
    public int category;

}
