package com.valinor.cache.def;

import com.valinor.Client;
import com.valinor.ClientConstants;
import com.valinor.cache.Archive;
import com.valinor.cache.anim.Animation;
import com.valinor.cache.anim.Sequence;
import com.valinor.cache.config.VariableBits;
import com.valinor.cache.def.impl.npcs.CustomBosses;
import com.valinor.cache.def.impl.npcs.CustomPets;
import com.valinor.cache.def.impl.NpcManager;
import com.valinor.cache.def.impl.npcs.MemberNpcs;
import com.valinor.collection.TempCache;
import com.valinor.entity.model.Model;
import com.valinor.io.Buffer;
import com.valinor.util.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public final class NpcDefinition {

    public static int totalNPCs;

    public static void init(Archive archive) {
        buffer = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR+"/npcs/npc.dat") : archive.get("npc.dat"));
        final Buffer metaBuf = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR+"/npcs/npc.idx") : archive.get("npc.idx"));
        totalNPCs = metaBuf.readUShort();

        System.out.printf("Loaded %d npcs loading OSRS version %d and SUB version %d%n", totalNPCs, ClientConstants.OSRS_DATA_VERSION, ClientConstants.OSRS_DATA_SUB_VERSION);

        offsets = new int[totalNPCs + 30_000];
        int metaOffset = 2;
        for (int i = 0; i < totalNPCs; i++) {
            offsets[i] = metaOffset;
            metaOffset += metaBuf.readUShort();
        }

        cache = new NpcDefinition[20];

        for (int i = 0; i < 20; i++) {
            cache[i] = new NpcDefinition();
        }
        if(dump) {
            dump();
        }
    }

    private static final boolean dump = false;

    public static int getModelIds(final int id, final int models) {
        final NpcDefinition npcDefinition = get(id);
        return npcDefinition.models[models];
    }

    public static int getadditionalModels(final int id, final int models) {
        final NpcDefinition npcDefinition = get(id);
        return npcDefinition.additionalModels[models];
    }

    public static int getModelColorIds(final int id, final int color) {
        final NpcDefinition npcDefinition = get(id);
        return npcDefinition.recolorFrom[color];
    }

    public static int getStandAnim(final int id) {
        final NpcDefinition npcDefinition = get(id);
        return npcDefinition.standingAnimation;
    }

    public static int getWalkAnim(final int id) {
        final NpcDefinition entityDef = get(id);
        return entityDef.walkingAnimation;
    }

    public static int getHalfTurnAnimation(final int id) {
        final NpcDefinition entityDef = get(id);
        return entityDef.rotate180Animation;
    }

    public static int getQuarterClockwiseTurnAnimation(final int id) {
        final NpcDefinition entityDef = get(id);
        return entityDef.rotate90LeftAnimation;
    }

    public static int getQuarterAnticlockwiseTurnAnimation(final int id) {
        final NpcDefinition entityDef = get(id);
        return entityDef.rotate90RightAnimation;
    }

    public static NpcDefinition get(int id) {
        for (int i = 0; i < 20; i++) {
            if (cache[i].interfaceType == (long) id) {
                return cache[i];
            }
        }

        cache_index = (cache_index + 1) % 20;
        NpcDefinition npcDefinition = cache[cache_index] = new NpcDefinition();
        buffer.pos = offsets[id];
        npcDefinition.id = id;
        npcDefinition.interfaceType = id;
        npcDefinition.decode(buffer);

        if(id == 1612) {
            npcDefinition.name = "Battle mage";
            npcDefinition.actions = new String[]{null, "Attack", null, null, null};
            npcDefinition.recolorFrom = new int[]{22426, 926};
            npcDefinition.recolorTo = new int[]{8090, 22426};
            npcDefinition.combatLevel = 54;
            npcDefinition.models = new int[]{2909, 2898, 2920};
            npcDefinition.standingAnimation = 195;
            npcDefinition.walkingAnimation = 189;
        }

        if(!dump) {
            NpcManager.unpack(id);
            CustomPets.unpack(id);
            CustomBosses.unpack(id);
            MemberNpcs.unpack(id);
        }

        /*if(id == 8492) {
            System.out.println("Actions now: " + Arrays.toString(npcDefinition.actions));
        }*/

        return npcDefinition;
    }

    public static void dump() {
        File f = new File(System.getProperty("user.home") + "/Desktop/npcs.txt");
        try {
            f.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(f));
            for (int id = 0; id < NpcDefinition.totalNPCs; id++) {
                NpcDefinition definition = NpcDefinition.get(id);

                bf.write("case " + id + ":");
                bf.write(System.getProperty("line.separator"));
                if (definition.name == null || definition.name.equals("null") ||
                    definition.name.isEmpty()) continue;

                bf.write("definition[id].name = " + definition.name + ";");
                bf.write(System.getProperty("line.separator"));
                if (definition.models != null) {
                    bf.write("definition[id].model_id = new int[] "
                        + Arrays.toString(definition.models).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.size != 1) {
                    bf.write("definition[id].occupied_tiles = " + definition.size + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.standingAnimation != -1) {
                    bf.write("definition[id].standingAnimation = " + definition.standingAnimation + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.walkingAnimation != -1) {
                    bf.write("definition[id].walkingAnimation = " + definition.walkingAnimation + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.rotate180Animation != -1) {
                    bf.write("definition[id].halfTurnAnimation = " + definition.rotate180Animation + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.rotate90LeftAnimation != -1) {
                    bf.write("definition[id].quarterClockwiseTurnAnimation = " + definition.rotate90LeftAnimation + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.rotate90RightAnimation != -1) {
                    bf.write("definition[id].quarterAnticlockwiseTurnAnimation = " + definition.rotate90RightAnimation + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.actions != null) {
                    bf.write("definition[id].actions = new int[] "
                        + Arrays.toString(definition.actions).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.recolorFrom != null) {
                    bf.write("definition[id].src_color = new int[] "
                        + Arrays.toString(definition.recolorFrom).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.recolorTo != null) {
                    bf.write("definition[id].dst_color = new int[] "
                        + Arrays.toString(definition.recolorTo).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.retextureFrom != null) {
                    bf.write("definition[id].src_texture = new int[] "
                        + Arrays.toString(definition.retextureFrom).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.retextureTo != null) {
                    bf.write("definition[id].dst_texture = new int[] "
                        + Arrays.toString(definition.retextureTo).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.additionalModels != null) {
                    bf.write("definition[id].additionalModels = new int[] "
                        + Arrays.toString(definition.additionalModels).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.combatLevel != -1) {
                    bf.write("definition[id].cmb_level = " + definition.combatLevel + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.widthScale != 128) {
                    bf.write("definition[id].model_scale_xy = " + definition.widthScale + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.heightScale != 128) {
                    bf.write("definition[id].model_scale_z = " + definition.heightScale + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (!definition.hasRenderPriority) {
                    bf.write("definition[id].render_priority = " + definition.hasRenderPriority + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.ambient != -1) {
                    bf.write("definition[id].ambient = " + definition.ambient + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.contrast != -1) {
                    bf.write("definition[id].contrast = " + definition.contrast + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.headIconPrayer != -1) {
                    bf.write("definition[id].headIcon = " + definition.headIconPrayer + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.rotation != 32) {
                    bf.write("definition[id].rotation = " + definition.rotation + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.transformVarbit != -1) {
                    bf.write("definition[id].varbit = " + definition.transformVarbit + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.transformVarp != -1) {
                    bf.write("definition[id].varp = " + definition.transformVarp + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.transforms != null) {
                    bf.write("definition[id].configs = new int[] "
                        + Arrays.toString(definition.transforms).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                bf.write("break;");
                bf.write(System.getProperty("line.separator"));
                bf.write(System.getProperty("line.separator"));
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copy(NpcDefinition definition, int id) {
        NpcDefinition copy = NpcDefinition.get(id);
        definition.size = copy.size;
        definition.rotation = copy.rotation;
        definition.standingAnimation = copy.standingAnimation;
        definition.walkingAnimation = copy.walkingAnimation;
        definition.rotate180Animation = copy.rotate180Animation;
        definition.rotate90LeftAnimation = copy.rotate90LeftAnimation;
        definition.rotate90RightAnimation = copy.rotate90RightAnimation;
        definition.transformVarbit = copy.transformVarbit;
        definition.transformVarp = copy.transformVarp;
        definition.combatLevel = copy.combatLevel;
        definition.name = copy.name;
        definition.description = copy.description;
        definition.headIconPrayer = copy.headIconPrayer;
        definition.isClickable = copy.isClickable;
        definition.ambient = copy.ambient;
        definition.heightScale = copy.heightScale;
        definition.widthScale = copy.widthScale;
        definition.drawMapDot = copy.drawMapDot;
        definition.contrast = copy.contrast;
        definition.actions = new String[copy.actions.length];
        System.arraycopy(copy.actions, 0, definition.actions, 0, definition.actions.length);
        definition.models = new int[copy.models.length];
        System.arraycopy(copy.models, 0, definition.models, 0, definition.models.length);
        definition.hasRenderPriority = copy.hasRenderPriority;
    }

    public Model get_dialogue_model() {
        if (transforms != null) {
            NpcDefinition entityDef = get_configs();
            if (entityDef == null)
                return null;
            else
                return entityDef.get_dialogue_model();
        }
        if (additionalModels == null)
            return null;
        boolean cached = false;
        for (int index = 0; index < additionalModels.length; index++)
            if (!Model.cached(additionalModels[index]))
                cached = true;

        if (cached)
            return null;
        Model head_model[] = new Model[additionalModels.length];
        for (int index = 0; index < additionalModels.length; index++)
            head_model[index] = Model.get(additionalModels[index]);

        Model dialogue_model;
        if (head_model.length == 1)
            dialogue_model = head_model[0];
        else
            dialogue_model = new Model(head_model.length, head_model, true);

        if (modelCustomColor > 0) {
            dialogue_model.completelyRecolor(modelCustomColor);
        }
        if (modelCustomColor2 != 0) {
            dialogue_model.shadingRecolor(modelCustomColor2);
        }
        if (modelCustomColor3 != 0) {
            dialogue_model.shadingRecolor2(modelCustomColor3);
        }
        if (modelCustomColor4 != 0) {
            dialogue_model.shadingRecolor4(modelCustomColor4);
        }
        if (modelSetColor != 0) {
            dialogue_model.shadingRecolor3(modelSetColor);
        }

        if (recolorFrom != null) {
            for (int k = 0; k < recolorFrom.length; k++)
                dialogue_model.recolor(recolorFrom[k], recolorTo[k]);
        }
        
        if (retextureFrom != null) {
            for (int index = 0; index < retextureFrom.length; index++)
                dialogue_model.retexture(retextureFrom[index], retextureTo[index]);
        }

        return dialogue_model;
    }

    public NpcDefinition get_configs() {
        //Ken comment: Added try catch to get_configs method to catch any config errors
        try {
            int j = -1;
            if (transformVarbit != -1) {
                VariableBits varBit = VariableBits.cache[transformVarbit];
                int k = varBit.configId;
                int l = varBit.leastSignificantBit;
                int i1 = varBit.mostSignificantBit;
                int j1 = Client.BIT_MASKS[i1 - l];
                j = clientInstance.settings[k] >> l & j1;
            } else if (transformVarp != -1)
                j = clientInstance.settings[transformVarp];
            if (j < 0 || j >= transforms.length || transforms[j] == -1)
                return null;
            else
                return get(transforms[j]);
        } catch (Exception e) {
            System.err.println("There was an error getting configs for NPC " + id);
            e.printStackTrace();
        }
        //Ken comment: return null if we haven't returned already, this shouldn't be possible.
        return null;
    }

    public static void clear() {
        model_cache = null;
        offsets = null;
        cache = null;
        buffer = null;
    }

    public Model get_animated_model(int animation, int current, int[] label) {
        if (transforms != null) {
            final NpcDefinition def = get_configs();
            if (def == null) {
                return null;
            } else {
                return def.get_animated_model(animation, current, label);
            }
        }
        Model model = (Model) model_cache.get(interfaceType);
        if (model == null) {
            boolean cached = false;
            if(models == null) {
                return null;
            }
            for (int i : models) {
                if (!Model.cached(i)) {
                    cached = true;
                }
            }
            if (cached) {
                return null;
            }
            final Model[] models = new Model[this.models.length];
            for (int index = 0; index < this.models.length; index++) {
                models[index] = Model.get(this.models[index]);
            }
            if (models.length == 1) {
                model = models[0];
            } else {
                model = new Model(models.length, models, true);
            }
            if (recolorFrom != null) {
                for (int k1 = 0; k1 < recolorFrom.length; k1++) {
                    model.recolor(recolorFrom[k1], recolorTo[k1]);
                }
            }
            if (modelCustomColor > 0) {
                model.completelyRecolor(modelCustomColor);
            }
            if (modelCustomColor2 != 0) {
                model.shadingRecolor(modelCustomColor2);
            }
            if (modelCustomColor3 != 0) {
                model.shadingRecolor2(modelCustomColor3);
            }
            if (modelCustomColor4 != 0) {
                model.shadingRecolor4(modelCustomColor4);
            }
            if (modelSetColor != 0) {
                model.shadingRecolor3(modelSetColor);
            }

            model.skin();
            model.light(84 + ambient, 1000 + contrast, -90, -580, -90, true);
            model_cache.put(model, interfaceType);
        }
        final Model animated_model = Model.EMPTY_MODEL;
        animated_model.replace(model, Animation.noAnimationInProgress(current) & Animation.noAnimationInProgress(animation));
        if (current != -1 && animation != -1)
            animated_model.mix(label, animation, current);
        else if (current != -1)
            animated_model.interpolate(current);

        if (widthScale != 128 || heightScale != 128) {
            animated_model.scale(widthScale, heightScale, widthScale);
        }

        animated_model.calc_diagonals();
        animated_model.face_skin = null;
        animated_model.vertex_skin = null;
        if (size == 1) {
            animated_model.within_tile = true;
        }
        return animated_model;
    }

    public void decode(Buffer buffer) {
        while (true) {
            int opcode = buffer.readUByte();
            if (opcode == 0) {
                return;
            } else if (opcode == 1) {
                int len = buffer.readUByte();
                models = new int[len];
                for (int i = 0; i < len; i++) {
                    models[i] = buffer.readUShort();
                }
            } else if (opcode == 2) {
                name = buffer.readString();
            } else if (opcode == 12) {
                size = buffer.readUByte();
            } else if (opcode == 13) {
                standingAnimation = buffer.readUShort();
            } else if (opcode == 14) {
                walkingAnimation = buffer.readUShort();
            } else if (opcode == 15) {
                turnLeftSequence = buffer.readUShort(); //rotate left anim
            } else if (opcode == 16) {
                turnRightSequence = buffer.readUShort(); //rotate right anim
            } else if (opcode == 17) {
                walkingAnimation = buffer.readUShort();
                rotate180Animation = buffer.readUShort();
                rotate90LeftAnimation = buffer.readUShort();
                rotate90RightAnimation = buffer.readUShort();
                if (rotate180Animation == 65535) {
                    rotate180Animation = walkingAnimation;
                }
                if (rotate90LeftAnimation == 65535) {
                    rotate90LeftAnimation = walkingAnimation;
                }
                if (rotate90RightAnimation == 65535) {
                    rotate90RightAnimation = walkingAnimation;
                }
            } else if (opcode == 18) {
                buffer.readUShort();
            } else if (opcode >= 30 && opcode < 35) {
                actions[opcode - 30] = buffer.readString();
                if (actions[opcode - 30].equalsIgnoreCase("Hidden")) {
                    actions[opcode - 30] = null;
                }
            } else if (opcode == 40) {
                int len = buffer.readUByte();
                recolorFrom = new int[len];
                recolorTo = new int[len];
                for (int i = 0; i < len; i++) {
                    recolorFrom[i] = buffer.readUShort();
                    recolorTo[i] = buffer.readUShort();
                }
            } else if (opcode == 41) {
                int length = buffer.readUByte();
                retextureFrom = new short[length];
                retextureTo = new short[length];
                for (int index = 0; index < length; index++) {
                    retextureFrom[index] = (short) buffer.readUShort();
                    retextureTo[index] = (short) buffer.readUShort();
                }
            } else if (opcode == 60) {
                int len = buffer.readUByte();
                additionalModels = new int[len];
                for (int i = 0; i < len; i++) {
                    additionalModels[i] = buffer.readUShort(); //chatheadModels
                }
            } else if (opcode == 93) {
                //Make sure to draw PK bots in the minimap (NPC IDs 13000 to 13009)
                if (id < 13000 || id > 13009)
                    drawMapDot = false;
            } else if (opcode == 95) {
                combatLevel = buffer.readUShort();
            } else if (opcode == 97) {
                widthScale = buffer.readUShort(); //widthScale
            } else if (opcode == 98) {
                heightScale = buffer.readUShort();
            } else if (opcode == 99) {
                hasRenderPriority = true;
            } else if (opcode == 100) {
                ambient = buffer.readSignedByte();
            } else if (opcode == 101) {
                contrast = buffer.readSignedByte() * 5;
            } else if (opcode == 102) {
                headIconPrayer = buffer.readUShort();
            } else if (opcode == 103) {
                rotation = buffer.readUShort();
            } else if (opcode != 106 && opcode != 118) {
                if (opcode == 107) { // L: 153
                    isInteractable = false;
                } else if (opcode == 109) {
                    isClickable = false;
                } else if (opcode == 111) {
                    isFollower = true;
                } else if (opcode == 249) {

                }
            } else {
                transformVarbit = buffer.readUShort();
                if (transformVarbit == 65535) {
                    transformVarbit = -1;
                }

                transformVarp = buffer.readUShort();
                if (transformVarp == 65535) {
                    transformVarp = -1;
                }

                int var3 = -1;
                if (opcode == 118) {
                    var3 = buffer.readUShort();
                    if (var3 == 65535) {
                        var3 = -1;
                    }
                }

                int var4 = buffer.readUnsignedByte();
                transforms = new int[var4 + 2];

                for (int var5 = 0; var5 <= var4; ++var5) {
                    transforms[var5] = buffer.readUShort();
                    if (transforms[var5] == 65535) {
                        transforms[var5] = -1;
                    }
                }

                transforms[var4 + 1] = var3;
            }
        }
    }

    public NpcDefinition() {
        name = "null";
        size = 1;
        standingAnimation = -1;
        turnLeftSequence = -1;
        turnRightSequence = -1;
        walkingAnimation = -1;
        rotate180Animation = -1;
        rotate90LeftAnimation = -1;
        rotate90RightAnimation = -1;
        actions = new String[5];
        drawMapDot = true;
        combatLevel = -1;
        widthScale = 128;
        heightScale = 128;
        hasRenderPriority = false;
        ambient = 0;
        contrast = 0;
        headIconPrayer = -1;
        rotation = 32;
        transformVarbit = -1;
        transformVarp = -1;
        isInteractable = true;
        isClickable = true;
        isFollower = false;
        modelSetColor = 0;
        anInt64 = 1834;
        interfaceType = -1L;
        modelCustomColor = 0;
        modelCustomColor2 = 0;
        modelCustomColor3 = 0;
        modelCustomColor4 = 0;
    }

    public int modelCustomColor;
    public int modelCustomColor2;
    public int modelCustomColor3;
    public int modelCustomColor4;
    public int modelSetColor;
    public int rotate90RightAnimation;
    public static int cache_index;
    public int transformVarbit;
    public int rotate180Animation;
    public int transformVarp;
    public static Buffer buffer;
    public int combatLevel;
    public boolean largeHpBar;
    public final int anInt64;
    public String name;
    public String[] actions;
    public int walkingAnimation;
    public int turnLeftSequence;
    public int turnRightSequence;
    public int size;
    public int[] recolorTo;
    public static int[] offsets;
    public int[] additionalModels;
    public int headIconPrayer;
    public short[] retextureFrom;
    public short[] retextureTo;
    public int[] recolorFrom;
    public int standingAnimation;
    public long interfaceType;
    public int rotation;
    public static NpcDefinition[] cache;
    public static Client clientInstance;
    public int rotate90LeftAnimation;
    public boolean isClickable;
    public boolean isInteractable;
    public boolean isFollower;
    public int ambient;
    public int heightScale;
    public boolean drawMapDot;
    public int[] transforms;
    public String description;
    public int widthScale;
    public int contrast;
    public boolean hasRenderPriority;
    public int[] models;
    public int interfaceZoom = 0;
    public int id;
    public static TempCache model_cache = new TempCache(30);
}
