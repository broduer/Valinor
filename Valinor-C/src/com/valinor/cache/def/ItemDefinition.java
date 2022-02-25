package com.valinor.cache.def;

import com.valinor.ClientConstants;
import com.valinor.cache.Archive;
import com.valinor.cache.def.impl.items.CustomItems;
import com.valinor.cache.def.impl.items.PreEocItems;
import com.valinor.cache.def.impl.items.UpgradeWeapons;
import com.valinor.cache.def.impl.items.WinterItems;
import com.valinor.cache.factory.ItemSpriteFactory;
import com.valinor.collection.TempCache;
import com.valinor.entity.model.Model;
import com.valinor.io.Buffer;
import com.valinor.util.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.valinor.util.ItemIdentifiers.*;

public final class ItemDefinition {

    public static void init(Archive archive) {
        data_buffer = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "items/obj.dat") : archive
            .get("obj.dat"));
        Buffer index_buffer = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "items/obj.idx") : archive
            .get("obj.idx"));
        length = index_buffer.readUShort();

        System.out.printf("Loaded %d items loading OSRS version %d and SUB version %d%n", length, ClientConstants.OSRS_DATA_VERSION, ClientConstants.OSRS_DATA_SUB_VERSION);

        pos = new int[length + 30_000];

        int offset = 2;
        for (int index = 0; index < length; index++) {
            pos[index] = offset;
            offset += index_buffer.readUShort();
        }

        cache = new ItemDefinition[10];

        for (int index = 0; index < 10; index++) {
            cache[index] = new ItemDefinition();
        }

        //dump();
    }

    private void decode(Buffer buffer) {
        while (true) {
            int opcode = buffer.readUnsignedByte();
            if (opcode == 0)
                return;
            if (opcode == 1)
                inventoryModel = buffer.readUShort();
            else if (opcode == 2)
                name = buffer.readString();
            else if (opcode == 3)
                description = buffer.readString();
            else if (opcode == 4)
                zoom2d = buffer.readUShort();
            else if (opcode == 5)
                xan2d = buffer.readUShort();
            else if (opcode == 6)
                yan2d = buffer.readUShort();
            else if (opcode == 7) {
                xOffset2d = buffer.readUShort();
                if (xOffset2d > 32767)
                    xOffset2d -= 0x10000;
            } else if (opcode == 8) {
                yOffset2d = buffer.readUShort();
                if (yOffset2d > 32767)
                    yOffset2d -= 0x10000;
            } else if (opcode == 11)
                stackable = 1;
            else if (opcode == 12)
                cost = buffer.readInt();
            else if (opcode == 16)
                members = true;
            else if (opcode == 23) {
                maleModel = buffer.readUShort();
                maleOffset = buffer.readSignedByte();
            } else if (opcode == 24)
                maleModel1 = buffer.readUShort();
            else if (opcode == 25) {
                femaleModel = buffer.readUShort();
                femaleOffset = buffer.readSignedByte();
            } else if (opcode == 26)
                femaleModel1 = buffer.readUShort();
            else if (opcode >= 30 && opcode < 35) {
                if (groundActions == null)
                    groundActions = new String[5];
                groundActions[opcode - 30] = buffer.readString();
                if (groundActions[opcode - 30].equalsIgnoreCase("hidden"))
                    groundActions[opcode - 30] = null;
            } else if (opcode >= 35 && opcode < 40) {
                if (inventoryActions == null)
                    inventoryActions = new String[5];
                inventoryActions[opcode - 35] = buffer.readString();
            } else if (opcode == 40) {
                int length = buffer.readUnsignedByte();
                recolorFrom = new int[length];
                recolorTo = new int[length];
                for (int index = 0; index < length; index++) {
                    recolorTo[index] = buffer.readUShort();
                    recolorFrom[index] = buffer.readUShort();
                }
            } else if (opcode == 41) {
                int length = buffer.readUnsignedByte();
                retextureFrom = new short[length];
                retextureTo = new short[length];
                for (int index = 0; index < length; index++) {
                    retextureFrom[index] = (short) buffer.readUShort();
                    retextureTo[index] = (short) buffer.readUShort();
                }
            } else if (opcode == 42) {
                shiftClickIndex = buffer.readUnsignedByte();
            } else if (opcode == 65) {
                isTradable = true;
            } else if (opcode == 78)
                maleModel2 = buffer.readUShort();
            else if (opcode == 79)
                femaleModel2 = buffer.readUShort();
            else if (opcode == 90)
                maleHeadModel = buffer.readUShort();
            else if (opcode == 91)
                femaleHeadModel = buffer.readUShort();
            else if (opcode == 92)
                maleHeadModel2 = buffer.readUShort();
            else if (opcode == 93)
                femaleHeadModel2 = buffer.readUShort();
            else if (opcode == 94)
                category = buffer.readUShort();
            else if (opcode == 95)
                zan2d = buffer.readUShort();
            else if (opcode == 97)
                note = buffer.readUShort();
            else if (opcode == 98)
                notedTemplate = buffer.readUShort();
            else if (opcode >= 100 && opcode < 110) {
                if (countobj == null) {
                    countobj = new int[10];
                    countco = new int[10];
                }
                countobj[opcode - 100] = buffer.readUShort();
                countco[opcode - 100] = buffer.readUShort();
            } else if (opcode == 110)
                resizeX = buffer.readUShort();
            else if (opcode == 111)
                resizeY = buffer.readUShort();
            else if (opcode == 112)
                resizeZ = buffer.readUShort();
            else if (opcode == 113)
                ambient = buffer.readSignedByte();
            else if (opcode == 114)
                contrast = buffer.readSignedByte() * 5;
            else if (opcode == 115)
                team = buffer.readUnsignedByte();
            else if (opcode == 139)
                bought_id = buffer.readUShort();
            else if (opcode == 140)
                bought_template_id = buffer.readUShort();
            else if (opcode == 148)
                placeholder = buffer.readUShort();
            else if (opcode == 149) {
                placeholderTemplate = buffer.readUShort();
            } else if (opcode == 249) {
                int length = buffer.readUnsignedByte();

                params = new HashMap<>(length);

                for (int i = 0; i < length; i++) {
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
            } else {
                System.err.printf("Error unrecognised {Items} opcode: %d%n%n", opcode);
            }
        }
    }

    public static void copyInventory(ItemDefinition itemDef, int id) {
        ItemDefinition copy = ItemDefinition.get(id);
        itemDef.inventoryModel = copy.inventoryModel;
        itemDef.zoom2d = copy.zoom2d;
        itemDef.xan2d = copy.xan2d;
        itemDef.yan2d = copy.yan2d;
        itemDef.zan2d = copy.zan2d;
        itemDef.resizeX = copy.resizeX;
        itemDef.resizeY = copy.resizeY;
        itemDef.resizeZ = copy.resizeZ;
        itemDef.xOffset2d = copy.xOffset2d;
        itemDef.yOffset2d = copy.yOffset2d;
        itemDef.inventoryActions = copy.inventoryActions;
        itemDef.cost = copy.cost;
        itemDef.stackable = copy.stackable;
    }

    public static void copyEquipment(ItemDefinition itemDef, int id) {
        ItemDefinition copy = ItemDefinition.get(id);
        itemDef.maleModel = copy.maleModel;
        itemDef.maleModel1 = copy.maleModel1;
        itemDef.femaleModel = copy.femaleModel;
        itemDef.femaleModel1 = copy.femaleModel1;
        itemDef.maleOffset = copy.maleOffset;
        itemDef.femaleOffset = copy.femaleOffset;
    }

    public static void printStatement(final String text) {
        System.out.println(text + ";");
    }

    public static void printDefinitions(final ItemDefinition definition) {
        printStatement("definition.name = \"" + definition.name + "\"");
        printStatement("definition.model_zoom = " + definition.zoom2d);
        printStatement("definition.rotation_y = " + definition.xan2d);
        printStatement("definition.rotation_x = " + definition.yan2d);
        printStatement("definition.translate_x = " + definition.xOffset2d);
        printStatement("definition.translate_y = " + definition.yOffset2d);
        printStatement("definition.inventory_model = " + definition.inventoryModel);
        printStatement("definition.male_equip_main = " + definition.maleModel);
        printStatement("definition.female_equip_main = " + definition.femaleModel);
        printStatement("definition.recolorFrom = " + Arrays.toString(definition.recolorFrom));
        printStatement("definition.recolorTo = " + Arrays.toString(definition.recolorTo));
    }

    public static void dump() {
        File f = new File(System.getProperty("user.home") + "/Desktop/items.txt");
        try {
            f.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(f));
            for (int id = 0; id < ItemDefinition.length; id++) {
                ItemDefinition definition = ItemDefinition.get(id);

                bf.write("case " + id + ":");
                bf.write(System.getProperty("line.separator"));
                if (definition.name == null || definition.name.equals("null") ||
                    definition.name.isEmpty()) continue;

                bf.write("definition[id].name = " + definition.name + ";");
                bf.write(System.getProperty("line.separator"));
                if (definition.inventoryModel != 0) {
                    bf.write("definition[id].inventory_model = " + definition.inventoryModel + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.recolorFrom != null) {
                    bf.write("definition[id].recolorFrom = new int[] "
                        + Arrays.toString(definition.recolorFrom).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.recolorTo != null) {
                    bf.write("definition[id].recolorTo = new int[] "
                        + Arrays.toString(definition.recolorTo).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.retextureFrom != null) {
                    bf.write("definition[id].retextureFrom = new int[] "
                        + Arrays.toString(definition.retextureFrom).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.retextureTo != null) {
                    bf.write("definition[id].retextureTo = new int[] "
                        + Arrays.toString(definition.retextureTo).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.zoom2d != 2000) {
                    bf.write("definition[id].model_zoom = " + definition.zoom2d + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.xan2d != 0) {
                    bf.write("definition[id].rotation_y = " + definition.xan2d + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.yan2d != 0) {
                    bf.write("definition[id].rotation_x = " + definition.yan2d + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.zan2d != 0) {
                    bf.write("definition[id].rotation_z = " + definition.zan2d + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.xOffset2d != -1) {
                    bf.write("definition[id].translate_x = " + definition.xOffset2d + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.yOffset2d != -1) {
                    bf.write("definition[id].translate_y = " + definition.yOffset2d + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                bf.write("definition[id].stackable = " + definition.stackable + ";");
                bf.write(System.getProperty("line.separator"));
                if (definition.groundActions != null) {
                    bf.write("definition[id].scene_actions = new int[] "
                        + Arrays.toString(definition.groundActions).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.inventoryActions != null) {
                    bf.write("definition[id].widget_actions = new int[] "
                        + Arrays.toString(definition.inventoryActions).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.maleModel != -1) {
                    bf.write("definition[id].male_equip_main = " + definition.maleModel + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.maleModel1 != -1) {
                    bf.write("definition[id].male_equip_attachment = " + definition.maleModel1 + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.maleOffset != 0) {
                    bf.write("definition[id].male_equip_translate_y = " + definition.maleOffset + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.femaleModel != -1) {
                    bf.write("definition[id].female_equip_main = " + definition.femaleModel + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.femaleModel1 != -1) {
                    bf.write("definition[id].female_equip_attachment = " + definition.femaleModel1 + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.femaleOffset != 0) {
                    bf.write("definition[id].female_equip_translate_y = " + definition.femaleOffset + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.maleModel2 != -1) {
                    bf.write("definition[id].male_equip_emblem = " + definition.maleModel2 + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.femaleModel2 != -1) {
                    bf.write("definition[id].female_equip_emblem = " + definition.femaleModel2 + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.maleHeadModel != -1) {
                    bf.write("definition[id].male_dialogue_head = " + definition.maleHeadModel + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.maleHeadModel2 != -1) {
                    bf.write("definition[id].male_dialogue_headgear = " + definition.maleHeadModel2 + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.femaleHeadModel != -1) {
                    bf.write("definition[id].female_dialogue_head = " + definition.femaleHeadModel + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.femaleHeadModel2 != -1) {
                    bf.write("definition[id].female_dialogue_headgear = " + definition.femaleHeadModel2 + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.countobj != null) {
                    bf.write("definition[id].stack_variant_id = new int[] "
                        + Arrays.toString(definition.countobj).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.countco != null) {
                    bf.write("definition[id].stack_variant_size = new int[] "
                        + Arrays.toString(definition.countco).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.note != -1) {
                    bf.write("definition[id].unnoted_item_id = " + definition.note + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.notedTemplate != -1) {
                    bf.write("definition[id].model_scale_xy = " + definition.notedTemplate + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.resizeX != 128) {
                    bf.write("definition[id].model_scale_x = " + definition.resizeX + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.resizeY != 128) {
                    bf.write("definition[id].model_scale_y = " + definition.resizeY + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.resizeZ != 128) {
                    bf.write("definition[id].model_scale_z = " + definition.resizeZ + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.ambient != 0) {
                    bf.write("definition[id].ambient = " + definition.ambient + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (definition.contrast != 0) {
                    bf.write("definition[id].contrast = " + definition.contrast + ";");
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

    public static ItemDefinition get(int id) {
        for (int index = 0; index < 10; index++)
            if (cache[index].id == id)
                return cache[index];

        cache_index = (cache_index + 1) % 10;
        ItemDefinition def = cache[cache_index];
        data_buffer.pos = pos[id];

        def.id = id;
        def.set_defaults();
        def.decode(data_buffer);

        if (def.name != null && (def.name.toLowerCase().contains("ardougne cloak"))) {
            def.inventoryActions = new String[]{null, "Wear", "Teleport", null, "Drop"};
        }

        if (def.name != null && (def.name.contains("Max cape") || def.name.contains("max cape"))) {
            def.inventoryActions = new String[]{null, "Wear", "Features", null, "Drop"};
        }

        if (def.name != null && (def.name.contains("slayer helmet") || def.name.contains("Slayer helmet"))) {
            def.inventoryActions = new String[]{null, "Wear", "Check", "Disassemble", "Drop"};
        }

        if (id == 24225 || (def.name != null && def.name.contains("crystal bow") || def.name.contains("crystal shield") || def.name.contains("crystal halberd"))) {
            def.inventoryActions = new String[]{null, "Wield", null, null, null};
        }

        if (def.name != null && def.name.contains("Blade of saeldor (c)")) {
            def.inventoryActions = new String[]{null, "Wield", null, null, "Dismantle"};
        }

        if (def.name != null && def.name.contains("Bow of faerdhinen (c)")) {
            def.inventoryActions = new String[]{null, "Wield", null, null, "Dismantle"};
        }

        PreEocItems.unpack(id);
        UpgradeWeapons.unpack(id);
        CustomItems.unpack(id);
        WinterItems.unpack(id);

        if (def.notedTemplate != -1)
            def.updateNote();

        int[] items = {RUNE_SCIMITAR_23330, RUNE_SCIMITAR_23332, RUNE_SCIMITAR_23334, BABYDRAGON_BONES, DRAGON_BONES, WYVERN_BONES, DAGANNOTH_BONES, FLIPPERS, SLED_4084, FISHBOWL_HELMET, DIVING_APPARATUS, CHICKEN_WINGS, CHICKEN_LEGS, CHICKEN_FEET, CHICKEN_HEAD, FANCY_BOOTS, GAS_MASK, MIME_MASK, FROG_MASK, LIT_BUG_LANTERN, SKELETON_BOOTS, SKELETON_GLOVES, SKELETON_MASK, SKELETON_LEGGINGS, SKELETON_SHIRT, LIT_BUG_LANTERN, FOX, CHICKEN, BONESACK, GRAIN_5607, RUBBER_CHICKEN, BUNNY_EARS};
        if (def.name != null) {
            if (!def.name.startsWith("<col=65280>")) {
                for (int item_id : items) {
                    if (id == item_id) {
                        def.name = ("<col=65280>" + def.name);
                        break;
                    }
                }
            }
            if (!def.name.startsWith("<col=65280>")) {
                if ((def.name.toLowerCase().contains("pet")) || (def.name.toLowerCase().contains("3rd")) || (def.name.toLowerCase().contains("toxic")) || (def.name.toLowerCase().contains("occult")) || (def.name.toLowerCase().contains("anguish")) || (def.name.toLowerCase().contains("torture")) || (def.name.toLowerCase().contains("avernic")) || (def.name.toLowerCase().contains("serpentine")) || (def.name.toLowerCase().contains("tanzanite")) || (def.name.toLowerCase().contains("magma")) || (def.name.toLowerCase().contains("ancestral")) || (def.name.toLowerCase().contains("armadyl")) || (def.name.toLowerCase().contains("void"))
                    || (def.name.toLowerCase().contains("bandos")) || (def.name.toLowerCase().contains("pegasian")) || (def.name.toLowerCase().contains("primordial")) || (def.name.toLowerCase().contains("eternal")) || (def.name.toLowerCase().contains("partyhat")) || (def.name.toLowerCase().contains("staff of light")) || (def.name.toLowerCase().contains("infernal")) || (def.name.toLowerCase().contains("slayer helm")) || (def.name.toLowerCase().contains("dragon hunter")) || (def.name.toLowerCase().contains("spectral")) || (def.name.toLowerCase().contains("ballista"))
                    || (def.name.toLowerCase().contains("justiciar")) || (def.name.toLowerCase().contains("dragon claws")) || (def.name.toLowerCase().contains("bulwark")) || (def.name.toLowerCase().contains("dragon warhammer")) || (def.name.toLowerCase().contains("blessed sword")) || (def.name.toLowerCase().contains("godsword")) || (def.name.toLowerCase().contains("ward")) || (def.name.toLowerCase().contains("wyvern shield")) || (def.name.toLowerCase().contains("morrigan")) || (def.name.toLowerCase().contains("vesta")) || (def.name.toLowerCase().contains("zuriel"))
                    || (def.name.toLowerCase().contains("statius")) || (def.name.toLowerCase().contains("dragon crossbow")) || (def.name.toLowerCase().contains("abyssal dagger")) || (def.name.toLowerCase().contains("ghrazi")) || (def.name.toLowerCase().contains("elder maul")) || (def.name.toLowerCase().contains("tormented")) || (def.name.toLowerCase().contains("infinity")) || (def.name.toLowerCase().contains("dragonfire")) || (def.name.toLowerCase().contains("blessed spirit shield")) || (def.name.toLowerCase().contains("of the dead")) || (def.name.toLowerCase().contains("ice arrow"))
                    || (def.name.toLowerCase().contains("dragon javelin")) || (def.name.toLowerCase().contains("dragon knife")) || (def.name.toLowerCase().contains("dragon thrownaxe")) || (def.name.toLowerCase().contains("abyssal tentacle")) || (def.name.toLowerCase().contains("dark bow")) || (def.name.toLowerCase().contains("fremennik kilt")) || (def.name.toLowerCase().contains("spiked manacles")) || (def.name.toLowerCase().contains("fury")) || (def.name.toLowerCase().contains("dragon boots")) || (def.name.toLowerCase().contains("ranger boots")) || (def.name.toLowerCase().contains("mage's book"))
                    || (def.name.toLowerCase().contains("master wand")) || (def.name.toLowerCase().contains("granite maul")) || (def.name.toLowerCase().contains("tome of fire")) || (def.name.toLowerCase().contains("recoil")) || (def.name.toLowerCase().contains("dharok")) || (def.name.toLowerCase().contains("karil")) || (def.name.toLowerCase().contains("guthan")) || (def.name.toLowerCase().contains("torag")) || (def.name.toLowerCase().contains("verac")) || (def.name.toLowerCase().contains("ahrim")) || (def.name.toLowerCase().contains("fire cape")) || (def.name.toLowerCase().contains("max cape"))
                    || (def.name.toLowerCase().contains("blighted")) || (def.name.toLowerCase().contains("dragon defender")) || (def.name.toLowerCase().contains("healer hat")) || (def.name.toLowerCase().contains("fighter hat")) || (def.name.toLowerCase().contains("runner hat")) || (def.name.toLowerCase().contains("ranger hat")) || (def.name.toLowerCase().contains("fighter torso")) || (def.name.toLowerCase().contains("runner boots")) || (def.name.toLowerCase().contains("penance skirt")) || (def.name.toLowerCase().contains("looting bag")) || (def.name.toLowerCase().contains("rune pouch"))
                    || (def.name.toLowerCase().contains("stamina")) || (def.name.toLowerCase().contains("anti-venom")) || (def.name.toLowerCase().contains("zamorakian")) || (def.name.toLowerCase().contains("hydra")) || (def.name.toLowerCase().contains("ferocious")) || (def.name.toLowerCase().contains("jar of")) || (def.name.toLowerCase().contains("brimstone")) || (def.name.toLowerCase().contains("crystal")) || (def.name.toLowerCase().contains("dagon")) || (def.name.toLowerCase().contains("dragon pickaxe")) || (def.name.toLowerCase().contains("tyrannical"))
                    || (def.name.toLowerCase().contains("dragon 2h")) || (def.name.toLowerCase().contains("elysian")) || (def.name.toLowerCase().contains("holy elixer")) || (def.name.toLowerCase().contains("odium")) || (def.name.toLowerCase().contains("malediction")) || (def.name.toLowerCase().contains("fedora")) || (def.name.toLowerCase().contains("suffering")) || (def.name.toLowerCase().contains("mole")) || (def.name.toLowerCase().contains("vampyre dust")) || (def.name.toLowerCase().contains("bludgeon")) || (def.name.toLowerCase().contains("kbd heads")) || (def.name.toLowerCase().contains("trident"))
                    || (def.name.toLowerCase().contains("nightmare")) || (def.name.toLowerCase().contains("kodai wand")) || (def.name.toLowerCase().contains("dragon sword")) || (def.name.toLowerCase().contains("dragon harpoon")) || (def.name.toLowerCase().contains("mystery box")) || (def.name.toLowerCase().contains("crystal key")) || (def.name.toLowerCase().contains("volatile")) || (def.name.toLowerCase().contains("eldritch")) || (def.name.toLowerCase().contains("harmonised")) || (def.name.toLowerCase().contains("inquisitor")) || (def.name.toLowerCase().contains("treasonous")) || (def.name.toLowerCase().contains("ring of the gods"))
                    || (def.name.toLowerCase().contains("vorkath")) || (def.name.toLowerCase().contains("dragonbone")) || (def.name.toLowerCase().contains("uncut onyx")) || (def.name.toLowerCase().contains("zulrah")) || (def.name.toLowerCase().contains("zul-andra")) || (def.name.toLowerCase().contains("sanguinesti")) || (def.name.toLowerCase().contains("blade of saeldor")) || (def.name.toLowerCase().contains("barrelchest anchor")) || (def.name.toLowerCase().contains("staff of balance")) || (def.name.toLowerCase().contains("twisted bow")) || (def.name.toLowerCase().contains("facegaurd")) || (def.name.toLowerCase().contains("guardian"))
                    || (def.name.toLowerCase().contains("twisted buckler")) || (def.name.toLowerCase().contains("dragon dart")) || (def.name.toLowerCase().contains("guthix rest")) || (def.name.toLowerCase().contains("obsidian")) || (def.name.toLowerCase().contains("regen bracelet")) || (def.name.toLowerCase().contains("rangers'")) || (def.name.toLowerCase().contains("dragon scimitar (or)")) || (def.name.toLowerCase().contains("valinor coins")) || (def.name.toLowerCase().contains("divine")) || (def.name.toLowerCase().contains("super antifire")) || (def.name.toLowerCase().contains("robin hood hat")) || (def.name.toLowerCase().contains("ankou"))
                    || (def.name.toLowerCase().contains("santa")) || (def.name.toLowerCase().contains("halloween")) || (def.name.toLowerCase().contains("dye")) || (def.name.toLowerCase().contains("cooking gauntlets")) || (def.name.toLowerCase().contains("magic secateurs")) || (def.name.toLowerCase().contains("explorer's ring")) || (def.name.toLowerCase().contains("blessing")) || (def.name.toLowerCase().contains("charos")) || (def.name.toLowerCase().contains("ardougne cloak")) || (def.name.toLowerCase().contains("bonecrusher")) || (def.name.toLowerCase().contains("ornament kit")) || (def.name.toLowerCase().contains("coal bag"))
                    || (def.name.toLowerCase().contains("imcando hammer")) || (def.name.toLowerCase().contains("goldsmith gauntlets")) || (def.name.toLowerCase().contains("graceful")) || (def.name.toLowerCase().contains("dark totem")) || (def.name.toLowerCase().contains("prospector")) || (def.name.toLowerCase().contains("angler")) || (def.name.toLowerCase().contains("nexling")) || (def.name.toLowerCase().contains("ancient hilt") || (def.name.toLowerCase().contains("ancient godsword"))) || (def.name.toLowerCase().contains("nihil horn")) || (def.name.toLowerCase().contains("torva")) || (def.name.toLowerCase().contains("nihil shard"))
                    || (def.name.toLowerCase().contains("vorki")) || (def.name.toLowerCase().contains("vet'ion")) || (def.name.toLowerCase().contains("venenatis")) || (def.name.toLowerCase().contains("tzrek")) || (def.name.toLowerCase().contains("dragon chainbody")) || (def.name.toLowerCase().contains("zik")) || (def.name.toLowerCase().contains("skotos")) || (def.name.toLowerCase().contains("ancient shard")) || (def.name.toLowerCase().contains("scorpia")) || (def.name.toLowerCase().contains("sraracha")) || (def.name.toLowerCase().contains("giant egg sac")) || (def.name.toLowerCase().contains("sarachnis cudgel")) || (def.name.toLowerCase().contains("kraken tentacle"))
                    || (def.name.toLowerCase().contains("abyssal whip")) || (def.name.toLowerCase().contains("draconic vissage")) || (def.name.toLowerCase().contains("smouldering stone")) || (def.name.toLowerCase().contains("kq head")) || (def.name.toLowerCase().contains("dragon axe")) || (def.name.toLowerCase().contains("seercull")) || (def.name.toLowerCase().contains("mud battlestaff")) || (def.name.toLowerCase().contains("light frame")) || (def.name.toLowerCase().contains("heavy frame")) || (def.name.toLowerCase().contains("monkey tail")) || (def.name.toLowerCase().contains("holy elixir")) || (def.name.toLowerCase().contains("spirit shield"))
                    || (def.name.toLowerCase().contains("saradomin hilt")) || (def.name.toLowerCase().contains("saradomin sword")) || (def.name.toLowerCase().contains("saradomin's light")) || (def.name.toLowerCase().contains("olmlet")) || (def.name.toLowerCase().contains("metamorphic dust")) || (def.name.toLowerCase().contains("kodai insignia")) || (def.name.toLowerCase().contains("dexterous prayer scroll")) || (def.name.toLowerCase().contains("torn prayer scroll")) || (def.name.toLowerCase().contains("dark relic")) || (def.name.toLowerCase().contains("onyx")) || (def.name.toLowerCase().contains("hellpuppy")) || (def.name.toLowerCase().contains("key master teleport"))
                    || (def.name.toLowerCase().contains("callisto")) || (def.name.toLowerCase().contains("bryophyta's essence")) || (def.name.toLowerCase().contains("bolt rack")) || (def.name.toLowerCase().contains("abyssal orphan")) || (def.name.toLowerCase().contains("unsired")) || (def.name.toLowerCase().contains("zaryte")) || (def.name.toLowerCase().contains("ancient cere")) || (def.name.toLowerCase().contains("mining gloves")) || (def.name.toLowerCase().contains("bag full of")) || (def.name.toLowerCase().contains("snow imp")) || (def.name.toLowerCase().contains("bow of faerdhinen"))) {
                    def.name = ("<col=65280>" + def.name);
                }
                if ((def.name.toLowerCase().contains("berserker ring")) || (def.name.toLowerCase().contains("seers")) || (def.name.toLowerCase().contains("archers")) || (def.name.toLowerCase().contains("warrior ring"))) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("scythe")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("gilded")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("bunny")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("zanik")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("ele'")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("prince")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("zombie")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("mithril seeds")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("tribal")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("broodoo")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if ((def.name.toLowerCase().contains("scarf")) || (def.name.toLowerCase().contains("woolly")) || (def.name.toLowerCase().contains("bobble"))) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("cane")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("jester")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("(g)")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if ((def.name.toLowerCase().contains("(t)")) && (!def.name.toLowerCase().endsWith("cape(t)"))) {
                    def.name = ("<col=65280>" + def.name);
                }
                if ((def.name.toLowerCase().contains("camo")) || (def.name.toLowerCase().contains("boxing glove"))) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("dharok")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("dragon spear")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("phoenix neck")) {
                    def.name = ("<col=65280>" + def.name);
                }
                if (def.name.toLowerCase().contains("dragon bolts (e)")) {
                    def.name = ("<col=65280>" + def.name);
                }
            }
        }
        return def;
    }

    private void set_defaults() {
        name = "null";
        zoom2d = 2000;
        xan2d = 0;
        yan2d = 0;
        zan2d = 0;
        xOffset2d = 0;
        yOffset2d = 0;
        stackable = 0;
        cost = 1;
        members = false;
        groundActions = new String[]{null, null, "Take", null, null};
        inventoryActions = new String[]{null, null, null, null, "Drop"};
        shiftClickIndex = -2;
        maleModel = -1;
        maleModel1 = -1;
        maleOffset = 0;
        femaleModel = -1;
        femaleModel1 = -1;
        femaleOffset = 0;
        maleModel2 = -1;
        femaleModel2 = -1;
        maleHeadModel = -1;
        maleHeadModel2 = -1;
        femaleHeadModel = -1;
        femaleHeadModel2 = -1;
        note = -1;
        notedTemplate = -1;
        resizeX = 128;
        resizeY = 128;
        resizeZ = 128;
        ambient = 0;
        contrast = 0;
        team = 0;
        isTradable = false;
        bought_id = -1;
        bought_template_id = -1;
        placeholder = -1;
        placeholderTemplate = -1;
        inventoryModel = 0;
        countobj = null;
        countco = null;
        animate_inv_sprite = false;
        modelCustomColor = 0;
        modelCustomColor2 = 0;
        modelCustomColor3 = 0;
        modelCustomColor4 = 0;
        modelSetColor = 0;
        recolorFrom = null;
        recolorTo = null;
    }

    private void updateNote() {
        ItemDefinition noted = get(notedTemplate);
        inventoryModel = noted.inventoryModel;
        zoom2d = noted.zoom2d;
        xan2d = noted.xan2d;
        yan2d = noted.yan2d;
        zan2d = noted.zan2d;
        xOffset2d = noted.xOffset2d;
        yOffset2d = noted.yOffset2d;
        recolorFrom = noted.recolorFrom;
        recolorTo = noted.recolorTo;
        retextureFrom = noted.retextureFrom;
        retextureTo = noted.retextureTo;
        ItemDefinition unnotedItem = get(note);
        name = unnotedItem.name;
        members = unnotedItem.members;
        cost = unnotedItem.cost;
        stackable = 1;

        String consonant_or_vowel_lead = "a";
        if (!ClientConstants.OSRS_DATA) {
            char character = unnotedItem.name.charAt(0);
            if (character == 'A' || character == 'E' || character == 'I' || character == 'O' || character == 'U')
                consonant_or_vowel_lead = "an";
        } else {
            String character = unnotedItem.name;
            if (character.equals("A") || character.equals("E") || character.equals("I") || character
                .equals("O") || character.equals("U"))
                consonant_or_vowel_lead = "an";
        }
        description = ("Swap this note at any bank for " + consonant_or_vowel_lead + " " + unnotedItem.name + ".");
    }

    public Model get_model(int stack_size) {
        if (countobj != null && stack_size > 1) {
            int stack_item_id = -1;
            for (int index = 0; index < 10; index++)
                if (stack_size >= countco[index] && countco[index] != 0)
                    stack_item_id = countobj[index];

            if (stack_item_id != -1)
                return get(stack_item_id).get_model(1);

        }
        Model model = (Model) model_cache.get(id);
        if (model != null) {
            return model;
        }

        model = Model.get(inventoryModel);
        if (model == null) {
            return null;
        }
        if (resizeX != 128 || resizeY != 128 || resizeZ != 128)
            model.scale(resizeX, resizeZ, resizeY);
        //System.err.println("Color to replace: " + recolorFrom + " | for id: " + id);
        if (recolorFrom != null) {
            //System.out.println("ISNT for model: " + id);
            for (int index = 0; index < recolorFrom.length; index++) {
                model.recolor(recolorFrom[index], recolorTo[index]);
            }
        }
        if (retextureFrom != null) {
            for (int index = 0; index < retextureFrom.length; index++) {
                model.retexture(retextureFrom[index], retextureTo[index]);
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

        model.light(64 + ambient, 768 + contrast, -50, -10, -50, true);
        model.within_tile = true;
        model_cache.put(model, id);
        return model;
    }

    public Model get_widget_model(int stack_size) {
        if (countobj != null && stack_size > 1) {
            int stack_item_id = -1;
            for (int index = 0; index < 10; index++) {
                if (stack_size >= countco[index] && countco[index] != 0)
                    stack_item_id = countobj[index];
            }
            if (stack_item_id != -1)
                return get(stack_item_id).get_widget_model(1);

        }
        Model widget_model = Model.get(inventoryModel);
        if (widget_model == null)
            return null;
        //System.err.println("Color to replace: " + recolorFrom + " | for id: " + id);
        if (recolorFrom != null) {
            //System.out.println("ISNT for model: " + id);
            for (int index = 0; index < recolorFrom.length; index++) {
                widget_model.recolor(recolorFrom[index], recolorTo[index]);
            }

        }
        if (retextureFrom != null) {
            for (int index = 0; index < retextureFrom.length; index++) {
                widget_model.retexture(retextureFrom[index], retextureTo[index]);
            }
        }

        //System.err.println("Color to replace: " + recolorFrom + " | for id: " + id);

        if (modelCustomColor > 0) {
            widget_model.completelyRecolor(modelCustomColor);
        }
        if (modelCustomColor2 != 0) {
            widget_model.shadingRecolor(modelCustomColor2);
        }
        if (modelCustomColor3 != 0) {
            widget_model.shadingRecolor2(modelCustomColor3);
        }
        if (modelCustomColor4 != 0) {
            widget_model.shadingRecolor4(modelCustomColor4);
        }
        if (modelSetColor != 0) {
            widget_model.shadingRecolor3(modelSetColor);
        }

        return widget_model;
    }

    public Model get_equipped_model(int gender) {
        int main = maleModel;
        int attatchment = maleModel1;
        int emblem = maleModel2;
        if (gender == 1) {
            main = femaleModel;
            attatchment = femaleModel1;
            emblem = femaleModel2;
        }
        if (main == -1)
            return null;

        Model equipped_model = Model.get(main);
        if (equipped_model == null) {
            return null;
        }
        if (attatchment != -1) {
            if (emblem != -1) {
                Model attachment_model = Model.get(attatchment);
                Model emblem_model = Model.get(emblem);
                Model[] list = {
                    equipped_model, attachment_model, emblem_model
                };
                equipped_model = new Model(3, list, true);
            } else {
                Model attachment_model = Model.get(attatchment);
                Model[] list = {
                    equipped_model, attachment_model
                };
                equipped_model = new Model(2, list, true);
            }
        }
        if (gender == 0 && maleOffset != 0)
            equipped_model.translate(0, maleOffset, 0);

        if (gender == 1 && femaleOffset != 0)
            equipped_model.translate(0, femaleOffset, 0);

        if (recolorFrom != null) {
            //System.out.println("ISNT for model: " + id);
            for (int index = 0; index < recolorFrom.length; index++) {
                equipped_model.recolor(recolorFrom[index], recolorTo[index]);
            }
        }
        if (retextureFrom != null) {
            for (int index = 0; index < retextureFrom.length; index++) {
                equipped_model.retexture(retextureFrom[index], retextureTo[index]);
            }
        }
        if (modelCustomColor > 0) {
            equipped_model.completelyRecolor(modelCustomColor);
        }
        if (modelCustomColor2 != 0) {
            equipped_model.shadingRecolor(modelCustomColor2);
        }
        if (modelCustomColor3 != 0) {
            equipped_model.shadingRecolor2(modelCustomColor3);
        }
        if (modelCustomColor4 != 0) {
            equipped_model.shadingRecolor4(modelCustomColor4);
        }
        if (modelSetColor != 0) {
            equipped_model.shadingRecolor3(modelSetColor);
        }

        return equipped_model;
    }

    public boolean equipped_model_cached(int gender) {
        int main = maleModel;
        int attachment = maleModel1;
        int emblem = maleModel2;
        if (gender == 1) {
            main = femaleModel;
            attachment = femaleModel1;
            emblem = femaleModel2;
        }
        if (main == -1)
            return true;

        boolean cached = true;
        if (!Model.cached(main))
            cached = false;

        if (attachment != -1 && !Model.cached(attachment))
            cached = false;

        if (emblem != -1 && !Model.cached(emblem))
            cached = false;

        return cached;
    }

    public Model get_equipped_dialogue_model(int gender) {
        int head_model = maleHeadModel;
        int equipped_headgear = maleHeadModel2;
        if (gender == 1) {
            head_model = femaleHeadModel;
            equipped_headgear = femaleHeadModel2;
        }
        if (head_model == -1)
            return null;

        Model dialogue_model = Model.get(head_model);
        if (equipped_headgear != -1) {
            Model headgear = Model.get(equipped_headgear);
            Model[] list = {
                dialogue_model, headgear
            };
            dialogue_model = new Model(2, list, true);
        }
        if (recolorFrom != null) {
            for (int index = 0; index < recolorFrom.length; index++) {
                dialogue_model.recolor(recolorFrom[index], recolorTo[index]);
            }

        }
        if (retextureFrom != null) {
            for (int index = 0; index < retextureFrom.length; index++) {
                dialogue_model.retexture(retextureFrom[index], retextureTo[index]);
            }
        }
        return dialogue_model;
    }

    public boolean dialogue_model_cached(int gender) {
        int head_model = maleHeadModel;
        int equipped_headgear = maleHeadModel2;
        if (gender == 1) {
            head_model = femaleHeadModel;
            equipped_headgear = femaleHeadModel2;
        }
        if (head_model == -1)
            return true;

        boolean cached = true;
        if (!Model.cached(head_model))
            cached = false;

        if (equipped_headgear != -1 && !Model.cached(equipped_headgear))
            cached = false;

        return cached;
    }

    public static void release() {
        model_cache = null;
        ItemSpriteFactory.sprites_cache = null;
        ItemSpriteFactory.scaled_cache = null;
        pos = null;
        cache = null;
        data_buffer = null;
    }

    private ItemDefinition() {
        id = -1;
    }

    public static int length;
    private static int cache_index;
    private static Buffer data_buffer;
    private static ItemDefinition[] cache;
    private static int[] pos;
    public static TempCache model_cache = new TempCache(50);

    public int cost;
    public int id;
    public int team;
    public int zoom2d;
    public int yan2d;
    public int xan2d;
    public int zan2d;
    public int inventoryModel;
    public int maleModel;
    public int maleModel1;
    public int maleModel2;

    public int femaleModel;
    public int femaleModel1;
    public int femaleModel2;

    public int maleHeadModel;
    private int maleHeadModel2;
    public int maleOffset;

    public int femaleHeadModel;
    private int femaleHeadModel2;
    public int femaleOffset;

    public int xOffset2d;
    public int yOffset2d;
    private int resizeX;
    private int resizeY;
    private int resizeZ;
    public int notedTemplate;
    public int note;
    public int ambient;
    public int contrast;
    public int[] countobj;
    public int[] countco;
    public int[] recolorFrom;
    public int[] recolorTo;
    public short[] retextureFrom;
    public short[] retextureTo;

    public String[] inventoryActions;
    public String[] groundActions;
    public String name;
    public String description;
    public static boolean members;
    public int stackable;
    public boolean animateInventory;

    public boolean animate_inv_sprite;

    public boolean isTradable;
    public int category;
    public int bought_id;
    public int bought_template_id;
    public int shiftClickIndex;
    public int placeholder;
    public int placeholderTemplate;

    public Map<Integer, Object> params = null;

    //Custom coloring
    public int modelCustomColor = 0;
    public int modelCustomColor2 = 0;
    public int modelCustomColor3 = 0;
    public int modelCustomColor4 = 0;
    public int modelSetColor = 0;

    public static int setInventoryModel(final int id) {
        final ItemDefinition definition = get(id);
        return definition.inventoryModel;
    }

    public static String setItemName(final int id) {
        final ItemDefinition definition = get(id);
        return definition.name;
    }

    public static int setMaleEquipmentId(final int id) {
        final ItemDefinition definition = get(id);
        return definition.maleModel;
    }

    public static int setFemaleEquipmentId(final int id) {
        final ItemDefinition definition = get(id);
        return definition.femaleModel;
    }

    public static int setModelZoom(final int id) {
        final ItemDefinition definition = get(id);
        return definition.zoom2d;
    }

    public static int setRotationX(final int id) {
        final ItemDefinition definition = get(id);
        return definition.yan2d;
    }

    public static int setRotationY(final int id) {
        final ItemDefinition definition = get(id);
        return definition.xan2d;
    }

    public static int setTranslateX(final int id) {
        final ItemDefinition definition = get(id);
        return definition.xOffset2d;
    }

    public static int setTranslateY(final int id) {
        final ItemDefinition definition = get(id);
        return definition.yOffset2d;
    }
}

