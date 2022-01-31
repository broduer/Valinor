package com.valinor.fs;

import com.valinor.game.world.entity.mob.npc.pets.Pet;
import com.valinor.io.RSBuffer;
import io.netty.buffer.Unpooled;
import nl.bartpelle.dawnguard.DataStore;
import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

import static com.valinor.util.CustomNpcIdentifiers.*;
import static com.valinor.util.CustomNpcIdentifiers.ARAGOG;
import static com.valinor.util.CustomNpcIdentifiers.DEMENTOR;
import static com.valinor.util.CustomNpcIdentifiers.FENRIR_GREYBACK;
import static com.valinor.util.CustomNpcIdentifiers.FLUFFY;
import static com.valinor.util.CustomNpcIdentifiers.HUNGARIAN_HORNTAIL;
import static com.valinor.util.CustomNpcIdentifiers.ZOMBIES_CHAMPION_PET;
import static com.valinor.util.NpcIdentifiers.*;

/**
 * Created by Bart Pelle on 10/4/2014.
 */
public class NpcDefinition implements Definition {

    public int getOption(String... searchOptions) {
        if (options != null) {
            for (String s : searchOptions) {
                for (int i = 0; i < options.length; i++) {
                    String option = options[i];
                    if (s.equalsIgnoreCase(option))
                        return i + 1;
                }
            }
        }
        return -1;
    }

    public int[] models;
    public String name = null;
    public int size = 1;
    public int idleAnimation = -1;
    public int walkAnimation = -1;
    public int render3 = -1;
    public int render4 = -1;
    public int render5 = -1;
    public int render6 = -1;
    public int render7 = -1;
    short[] recol_s;
    short[] recol_d;
    short[] retex_s;
    short[] retex_d;
    int[] anIntArray2224;
    public boolean mapdot = true;
    public int combatlevel = -1;
    int width = -1;
    int height = -1;
    public boolean render = false;
    int anInt2242 = 0;
    int contrast = 0;
    public int headIcon = -1;
    public int turnValue = -1;
    int varbit = -1;
    public boolean rightclick = true;
    int varp = -1;
    public boolean aBool2227 = true;
    public int[] altForms;
    public boolean ispet = false;
    public int anInt2252 = -1;
    public String[] options = new String[5];
    public Map<Integer, Object> clientScriptData;
    public int id;

    public static void main(String[] args) throws Exception {
        DataStore ds = new DataStore("./data/filestore/");
        System.out.println(discoverNPCAnims(ds, 3727, false));
    }

    private static List<Integer> discoverNPCAnims(DataStore store, int id, boolean debug) {
        NpcDefinition npcdef = new NpcDefinition(id, store.getIndex(2).getContainer(9).getFileData(id, true, true));
        int animId = npcdef.idleAnimation;
        if (debug) System.out.println("Beginning discovery for " + npcdef.name + ".");
        if (debug) System.out.print("Using stand animation to grab kinematic set... ");
        if (debug) System.out.println(animId);
        AnimationDefinition stand = new AnimationDefinition(animId, store.getIndex(2).getContainer(12).getFileData(animId, true, true));
        if (debug) System.out.print("Finding skin set... ");
        int set = stand.skeletonSets[0] >> 16;
        if (debug) System.out.println(set);
        if (debug) System.out.println("Using that set to find related animations...");
        int skin = AnimationSkeletonSet.get(store, set).loadedSkins.keySet().iterator().next();

        if (skin == 0) {
            return new ArrayList<>(0);
        }

        List<Integer> work = new LinkedList<>();
        for (int i = 0; i < 30000; i++) {
            AnimationDefinition a = new AnimationDefinition(i, store.getIndex(2).getContainer(12).getFileData(i, true, true));
            int skel = a.skeletonSets[0] >> 16;
            try {
                AnimationSkeletonSet sett = AnimationSkeletonSet.get(store, skel);
                if (sett.loadedSkins.keySet().contains(skin)) {
                    work.add(i);
                    //System.out.println("Animation #" + i + " uses player kinematic set.");
                }
                //System.out.println(skel);
            } catch (Exception e) {

            }
        }

        if (debug) System.out.println("Found a total of " + work.size() + " animations: " + work);
        return work;
    }

    private static final int[] GWD_ROOM_NPCIDS = new int[]{
        3165, 3163, 3164, 3162,
        2215, 2216, 2217, 2218,
        3129, 3130, 3132, 3131,
        2206, 2207, 2208, 2205
    };

    public boolean gwdRoomNpc;
    public boolean inferno;
    public boolean roomBoss;

    public NpcDefinition(int id, byte[] data) {
        this.id = id;

        if (data != null && data.length > 0)
            decode(new RSBuffer(Unpooled.wrappedBuffer(data)));
        custom();

        gwdRoomNpc = ArrayUtils.contains(GWD_ROOM_NPCIDS, id);
        inferno = id >= 7677 && id <= 7710;
        roomBoss = name != null && ((id >= 2042 && id <= 2044 || inferno) || gwdRoomNpc);
        flightClipping = name != null && (name.toLowerCase().contains("impling") || name.toLowerCase().contains("butterfly"));
    }

    void decode(RSBuffer buffer) {
        while (true) {
            int op = buffer.readUByte();
            if (op == 0)
                break;
            decode(buffer, op);
        }
    }

    void custom() {
        for (Pet pet : Pet.values()) {
            if (id == pet.npc) {
                ispet = true;
                size = 1;
                break;
            }
        }

        if (id == ICE_IMP) {
            name = "Ice imp";
            combatlevel = 77;
        } else if (id == ICE_IMP_15119) {
            name = "Ice imp";
            combatlevel = 127;
        } else if (id == ICE_IMP_15120) {
            name = "Ice imp";
            combatlevel = 188;
        } else if (id == 7632) {
            name = "Men in black";
            combatlevel = 80;
            options = new String[]{null, "Attack", null, null, null, null};
            size = 1;
        } else if (id == NAGINI) {
            name = "Nagini";
        } else if (id == LORD_VOLDEMORT) {
            name = "Lord voldemort";
            combatlevel = 1433;
        } else if (id == GRIM) {
            name = "Grim";
            size = 3;
            combatlevel = 1322;
        } else if (id == BLOOD_REAPER) {
            name = "Blood Reaper";
        } else if (id == MALE_CENTAUR) {
            name = "Male centaur";
            size = 2;
        } else if (id == FEMALE_CENTAUR) {
            name = "Female centaur";
            size = 2;
        } else if (id == DEMENTOR) {
            name = "Dementor";
            size = 1;
        } else if (id == ARAGOG) {
            name = "Aragog";
            size = 4;
        } else if (id == FLUFFY) {
            name = "Fluffy";
            size = 5;
        } else if (id == HUNGARIAN_HORNTAIL) {
            name = "Hungarian horntail";
            size = 4;
        } else if (id == FENRIR_GREYBACK) {
            name = "Fenrir greyback";
            size = 1;
        } else if (id == BRUTAL_LAVA_DRAGON || id == BRUTAL_LAVA_DRAGON_FLYING) {
            name = "Brutal lava dragon";
            size = 4;
        } else if (id == ZRIAWK) {
            name = "Zriawk";
        } else if (id == CENTAUR_MALE_PET) {
            name = "Centaur male";
        } else if (id == CENTAUR_FEMALE_PET) {
            name = "Centaur female";
        } else if (id == FLUFFY_JR) {
            name = "Fluffy Jr";
        } else if (id == FENRIR_GREYBACK_JR) {
            name = "Fenrir greyback Jr";
        } else if (id == DEMENTOR_PET) {
            name = "Dementor";
        } else if (id == FOUNDER_IMP) {
            name = "Founder imp";
        } else if (id == CORRUPTED_NECHRYARCH) {
            name = "Corrupted nechryarch";
            size = 2;
        } else if (id == CORRUPTED_NECHRYARCH_PET) {
            name = "Corrupted nechryarch";
        } else if (id == MINI_NECROMANCER) {
            name = "Mini necromancer";
        } else if (id == JALTOK_JAD) {
            name = "Jaltok-jad";
        } else if (id == BABY_LAVA_DRAGON) {
            name = "Baby lava dragon";
        } else if (id == FAWKES) {
            name = "Fawkes";
        } else if (id == ELYSIAN_PET) {
            name = "Elysian";
        } else if (id == KERBEROS) {
            name = "Kerberos";
            combatlevel = 318;
            size = 5;
        } else if (id == SKORPIOS) {
            name = "Skorpios";
            combatlevel = 225;
            size = 5;
        } else if (id == ARACHNE) {
            name = "Arachne";
            combatlevel = 464;
            size = 4;
        } else if (id == ARTIO) {
            name = "Artio";
            combatlevel = 470;
            size = 5;
        } else if (id == ANCIENT_REVENANT_DARK_BEAST) {
            name = "Ancient revenant dark beast";
            combatlevel = 120;
            size = 3;
        } else if (id == ANCIENT_REVENANT_ORK) {
            name = "Ancient revenant ork";
            combatlevel = 105;
            size = 3;
        } else if (id == ANCIENT_REVENANT_CYCLOPS) {
            name = "Ancient revenant cyclops";
            combatlevel = 82;
            size = 3;
        } else if (id == ANCIENT_REVENANT_DRAGON) {
            name = "Ancient revenant dragon";
            combatlevel = 135;
            size = 5;
        } else if (id == ANCIENT_REVENANT_KNIGHT) {
            name = "Ancient revenant knight";
            combatlevel = 126;
            size = 1;
        } else if (id == ANCIENT_BARRELCHEST) {
            name = "Ancient barrelchest";
            combatlevel = 190;
            size = 3;
        } else if (id == ANCIENT_KING_BLACK_DRAGON) {
            name = "Ancient king black dragon";
            combatlevel = 276;
            size = 5;
        } else if (id == ANCIENT_CHAOS_ELEMENTAL) {
            name = "Ancient chaos elemental";
            combatlevel = 305;
            size = 3;
        } else if (id == ANCIENT_KING_BLACK_DRAGON_PET) {
            name = "Ancient king black dragon";
            size = 1;
        } else if (id == ANCIENT_CHAOS_ELEMENTAL_PET) {
            name = "Ancient chaos elemental";
            size = 1;
        } else if (id == ANCIENT_BARRELCHEST_PET) {
            name = "Ancient barrelchest";
            size = 1;
        } else if (id == BLOOD_FIREBIRD) {
            name = "Blood firebird";
            size = 1;
        } else if (id == 13000 || id == 13001) {
            name = "Pure bot";
            size = 1;
            combatlevel = 80;
        } else if (id == 13002 || id == 13003) {
            name = "F2p bot";
            size = 1;
            combatlevel = 68;
        } else if (id == 13004) {
            name = "Maxed bot";
            size = 1;
            combatlevel = 126;
        } else if (id == 13005) {
            name = "Maxed bot";
            size = 1;
            combatlevel = 126;
        } else if (id == 13006) {
            name = "Archer bot";
            size = 1;
            combatlevel = 90;
        } else if (id == 13008 || id == 13009) {
            name = "Pure Archer bot";
            size = 1;
            combatlevel = 80;
        } else if (id == KERBEROS_PET) {
            name = "Kerberos";
        } else if (id == SKORPIOS_PET) {
            name = "Skorpios";
        } else if (id == ARACHNE_PET) {
            name = "Arachne";
        } else if (id == ARTIO_PET) {
            name = "Artio";
        } else if (id == TWIGGY_OKORN) {
            options = new String[]{"Talk-to", null, "Rewards", "Claim-cape", null};
        } else if (id == FANCY_DAN) {
            name = "Vote Manager";
            options[0] = "Trade";
        } else if (id == WISE_OLD_MAN) {
            name = "Credit Manager";
            options[0] = "Trade";
        } else if (id == SECURITY_GUARD) {
            name = "Security Advisor";
            options[0] = "Check Pin Settings";
        } else if (id == SIGMUND_THE_MERCHANT) {
            options[0] = "Buy-items";
            options[2] = "Sell-items";
            options[3] = "Sets";
            options[4] = null;
        } else if (id == GRAND_EXCHANGE_CLERK) {
            options[0] = "Exchange";
            options[2] = null;
            options[3] = null;
            options[4] = null;
        } else if (id == MAKEOVER_MAGE_1307) {
            options[0] = "Change-looks";
            options[2] = "Title-unlocks";
            options[3] = null;
            options[4] = null;
        } else if (id == LISA) {
            name = "Tournament Manager";
            options = new String[]{"Sign-up", null, "Quick-join", "Quick-spectate", "Daily-pkers", null, null};
        } else if (id == SHURA) {
            name = "Referral Manager";
        } else if (id == WAMPA_PET) {
            name = "Wampa";
        } else if (id == NIFFLER_PET) {
            name = "Niffler";
        } else if (id == FAWKES_15981) {
            name = "Fawkes";
        } else if (id == PET_ZILYANA_WHITE) {
            name = "Zilyana Jr";
        } else if (id == PET_GENERAL_GRAARDOR_BLACK) {
            name = "General Graardor Jr";
        } else if (id == PET_KREEARRA_WHITE) {
            name = "Kree'arra Jr";
        } else if (id == PET_KRIL_TSUTSAROTH_BLACK) {
            name = "K'ril Tsutsaroth Jr";
        } else if (id == BABY_SQUIRT) {
            name = "Baby Squirt";
        } else if (id == BARRELCHEST_PET) {
            name = "Baby Barrelchest";
        } else if (id == GRIM_REAPER_PET) {
            name = "Grim Reaper";
        } else if (id == BABY_DARK_BEAST) {
            name = "Baby Dark Beast";
        } else if (id == BABY_ARAGOG) {
            name = "Baby Aragog";
        } else if (id == JAWA_PET) {
            name = "Jawa";
        } else if (id == DHAROK_PET) {
            name = "Dharok the Wretched";
        } else if (id == 15849) {
            name = "Genie";
        } else if (id == BABY_ABYSSAL_DEMON) {
            name = "Baby Abyssal Demon";
        } else if (id == ZOMBIES_CHAMPION_PET) {
            name = "Zombies champion";
        }

        if (ispet) {
            this.name = this.name + " pet";
        }
    }

    void decode(RSBuffer buffer, int code) {
        if (code == 1) {
            int numModels = buffer.readUByte();
            models = new int[numModels];

            for (int mdl = 0; mdl < numModels; mdl++) {
                models[mdl] = buffer.readUShort();
            }
        } else if (code == 2) {
            name = buffer.readString();
        } else if (code == 12) {
            size = buffer.readUByte();
        } else if (code == 13) {
            idleAnimation = buffer.readUShort();
        } else if (code == 14) {
            walkAnimation = buffer.readUShort();
        } else if (code == 15) {
            render3 = buffer.readUShort();
        } else if (code == 16) {
            render4 = buffer.readUShort();
        } else if (code == 17) {
            walkAnimation = buffer.readUShort();
            render5 = buffer.readUShort();
            render6 = buffer.readUShort();
            render7 = buffer.readUShort();
        } else if (code == 18) {
            buffer.readUShort();
        } else if (code >= 30 && code < 35) {
            options[code - 30] = buffer.readString();
            if (options[code - 30].equalsIgnoreCase(null)) {
                options[code - 30] = null;
            }
        } else if (code == 40) {
            int var5 = buffer.readUByte();
            recol_s = new short[var5];
            recol_d = new short[var5];

            for (int var4 = 0; var4 < var5; var4++) {
                recol_s[var4] = (short) buffer.readUShort();
                recol_d[var4] = (short) buffer.readUShort();
            }
        } else if (code == 41) {
            int var5 = buffer.readUByte();
            retex_s = new short[var5];
            retex_d = new short[var5];

            for (int var4 = 0; var4 < var5; var4++) {
                retex_s[var4] = (short) buffer.readUShort();
                retex_d[var4] = (short) buffer.readUShort();
            }
        } else if (code == 60) {
            int var5 = buffer.readUByte();
            anIntArray2224 = new int[var5];

            for (int var4 = 0; var4 < var5; var4++) {
                anIntArray2224[var4] = buffer.readUShort();
            }
        } else if (code == 93) {
            mapdot = false;
        } else if (code == 95) {
            combatlevel = buffer.readUShort();
        } else if (code == 97) {
            width = buffer.readUShort();
        } else if (code == 98) {
            height = buffer.readUShort();
        } else if (code == 99) {
            render = true;
        } else if (code == 100) {
            anInt2242 = buffer.readByte();
        } else if (code == 101) {
            contrast = buffer.readByte();
        } else if (code == 102) {
            headIcon = buffer.readUShort();
        } else if (code == 103) {
            turnValue = buffer.readUShort();
        } else if (code == 106 || code == 118) {
            varbit = buffer.readUShort();
            if (varbit == 65535) {
                varbit = -1;
            }

            varp = buffer.readUShort();
            if (varp == 65535) {
                varp = -1;
            }

            int ending = -1;
            if (code == 118) {
                ending = buffer.readUShort();
                if (ending == 65535) {
                    ending = -1;
                }
            }

            int var5 = buffer.readUByte();
            altForms = new int[var5 + 2];

            for (int var4 = 0; var4 <= var5; var4++) {
                altForms[var4] = buffer.readUShort();
                if (altForms[var4] == 65535) {
                    altForms[var4] = -1;
                }
            }
            altForms[var5 + 1] = ending;
        } else if (code == 107) {
            rightclick = false;
        } else if (code == 109) {
            aBool2227 = false;
        } else if (code == 111) {
            ispet = true;
        } else if (code == 249) {
            int length = buffer.readUByte();
            int index;
            if (clientScriptData == null) {
                index = method32(length);
                clientScriptData = new HashMap<>(index);
            }
            for (index = 0; index < length; index++) {
                boolean stringData = buffer.readUByte() == 1;
                int key = buffer.readTriByte();
                clientScriptData.put(key, stringData ? buffer.readString() : buffer.readInt());
            }
        } else {
            throw new RuntimeException("cannot parse npc definition, missing config code: " + code);
        }
    }

    public static int method32(int var0) {
        --var0;
        var0 |= var0 >>> 1;
        var0 |= var0 >>> 2;
        var0 |= var0 >>> 4;
        var0 |= var0 >>> 8;
        var0 |= var0 >>> 16;
        return var0 + 1;
    }

    public int[] renderpairs() {
        return new int[]{idleAnimation, render7, walkAnimation, render7, render5, render6, walkAnimation};
    }

    public boolean ignoreOccupiedTiles;
    public boolean flightClipping, swimClipping;
}
