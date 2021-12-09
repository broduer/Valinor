package com.valinor.cache.anim;

import com.valinor.ClientConstants;
import com.valinor.cache.Archive;
import com.valinor.io.Buffer;
import com.valinor.util.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public final class Sequence {

    public static Sequence[] cache;
    public int frames;
    public int[] primary_frame;
    public int[] frame_list;
    public int[] frame_length;
    public int step;
    public int[] flow_control;
    public boolean stretch;
    public int appended_frames;
    public int shield_delta;
    public int weapon_delta;
    public int loops;
    public int tempo;
    public int priority;
    public int reset;

    public Sequence() {
        step = -1;
        stretch = false;
        appended_frames = 5;
        shield_delta = -1; // Removes shield
        weapon_delta = -1; // Removes weapon
        loops = 99;
        tempo = -1; // Stops character from moving
        priority = -1;
        reset = 2; // replayMode default value 2 in OSRS, can change back to 1 if causes problems with animations.
    }

    public static void init(Archive archive) {
        final Buffer buffer = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "/anims/seq.dat") : archive.get("seq.dat"));
        int animation_size = buffer.readUShort();

        System.out.printf("Loaded %d animations loading OSRS version %d and SUB version %d%n", animation_size, ClientConstants.OSRS_DATA_VERSION, ClientConstants.OSRS_DATA_SUB_VERSION);

        if (cache == null) {
            cache = new Sequence[animation_size + 15_000];
        }

        int animation;
        for (animation = 0; animation < animation_size; animation++) {
            if (cache[animation] == null) {
                cache[animation] = new Sequence();
            }
            cache[animation].decode(buffer);

            //Fix Dagannoth Rex animation by reverting frames to older
            if (animation == 2851) {
                cache[animation] = new Sequence();
                cache[animation].frames = 6;
                cache[animation].frame_length = new int[]{5, 5, 3, 6, 4, 3};
                cache[animation].primary_frame = new int[]{983040, 983041, 983042, 983043, 983044, 983045};
                cache[animation].appended_frames = 5;
                cache[animation].reset = 1;
            }

            if (ClientConstants.CHRISTMAS) {
                if (animation == 8534) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157614162, 157614084, 157614094, 157614087, 157614082, 157614179, 157614135, 157614199, 157614183, 157614142, 157614160, 157614125, 157614090, 157614115, 157614149};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8535) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157614150, 157614124, 157614188, 157614146, 157614198, 157614164, 157614190, 157614112, 157614126, 157614151, 157614159, 157614095, 157614153, 157614178, 157614193};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8536) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157614117, 157614127, 157614139, 157614141, 157614106, 157614191, 157614128, 157614105, 157614111, 157614123, 157614140, 157614145, 157614143, 157614182, 157614186};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8537) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157614174, 157614201, 157614089, 157614176, 157614129, 157614166, 157614154, 157614200, 157614080, 157614180, 157614108, 157614148, 157614185, 157614114, 157614197};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8538) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 12;
                    cache[animation].primary_frame = new int[]{157614163, 157614085, 157614131, 157614165, 157614181, 157614169, 157614081, 157614119, 157614133, 157614195, 157614196, 157614104};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 7175) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 92;
                    cache[animation].primary_frame = new int[]{97124466, 97124467, 97124399, 97124357, 97124394, 97124430, 97124407, 97124443, 97124352, 97124405, 97124391, 97124397, 97124393, 97124436, 97124378, 97124454, 97124379, 97124390, 97124448, 97124442, 97124388, 97124363, 97124373, 97124406, 97124394, 97124450, 97124465, 97124365, 97124466, 97124415, 97124455, 97124418, 97124352, 97124425, 97124445, 97124392, 97124393, 97124385, 97124423, 97124371, 97124379, 97124384, 97124356, 97124410, 97124388, 97124457, 97124412, 97124354, 97124394, 97124421, 97124461, 97124449, 97124466, 97124395, 97124403, 97124382, 97124352, 97124464, 97124446, 97124416, 97124393, 97124377, 97124453, 97124459, 97124379, 97124429, 97124368, 97124435, 97124388, 97124426, 97124366, 97124452, 97124359, 97124381, 97124376, 97124386, 97124463, 97124469, 97124387, 97124355, 97124422, 97124361, 97124409, 97124370, 97124463, 97124441, 97124440, 97124402, 97124359, 97124375, 97124462, 97124358};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 3, 5, 8, 5, 3, 3, 3, 3, 3, 6, 10, 7, 4, 4, 4, 4, 4, 7, 11, 8, 6, 5};
                    cache[animation].step = 78;
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8541) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157548565, 157548716, 157548590, 157548714, 157548568, 157548754, 157548675, 157548637, 157548582, 157548760, 157548710, 157548650, 157548559, 157548828, 157548757};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8542) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157548773, 157548694, 157548548, 157548583, 157548771, 157548782, 157548587, 157548607, 157548776, 157548693, 157548649, 157548634, 157548767, 157548623, 157548783};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8543) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157548705, 157548713, 157548648, 157548699, 157548823, 157548666, 157548556, 157548630, 157548751, 157548758, 157548824, 157548835, 157548793, 157548646, 157548614};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8544) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 15;
                    cache[animation].primary_frame = new int[]{157548749, 157548575, 157548644, 157548643, 157548809, 157548811, 157548804, 157548740, 157548605, 157548659, 157548555, 157548613, 157548678, 157548552, 157548794};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }

                if (animation == 8545) {
                    cache[animation] = new Sequence();
                    cache[animation].frames = 41;
                    cache[animation].primary_frame = new int[]{157548697, 157548731, 157548726, 157548766, 157548706, 157548731, 157548697, 157548719, 157548687, 157548825, 157548798, 157548668, 157548832, 157548586, 157548792, 157548826, 157548774, 157548580, 157548562, 157548586, 157548832, 157548668, 157548711, 157548664, 157548744, 157548593, 157548786, 157548698, 157548589, 157548764, 157548574, 157548546, 157548796, 157548546, 157548574, 157548764, 157548589, 157548698, 157548561, 157548593, 157548617};
                    cache[animation].frame_list = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                    cache[animation].frame_length = new int[]{30, 4, 4, 4, 4, 4, 30, 4, 4, 4, 4, 19, 4, 4, 4, 4, 25, 4, 4, 4, 4, 23, 4, 4, 4, 4, 4, 4, 18, 4, 4, 4, 19, 4, 4, 4, 4, 4, 4, 4, 4};
                    cache[animation].tempo = 0;
                    cache[animation].priority = 0;
                    cache[animation].reset = 2;
                }
            }
        }

        /*
        System.out.println("frames "+cache[7644].frames);
        System.out.println("primary_frame "+Arrays.toString(cache[7644].primary_frame));
        System.out.println("frame_length "+Arrays.toString(cache[7644].frame_length));
        System.out.println("frame_list "+Arrays.toString(cache[7644].frame_list));
        System.out.println("flow_control "+Arrays.toString(cache[7644].flow_control));
        System.out.println("step "+cache[7644].step);
        System.out.println("stretch "+cache[7644].stretch);
        System.out.println("appended_frames "+cache[7644].appended_frames);
        System.out.println("shield_delta "+cache[7644].shield_delta);
        System.out.println("weapon_delta "+cache[7644].weapon_delta);
        System.out.println("loops "+cache[7644].loops);
        System.out.println("tempo "+cache[7644].tempo);
        System.out.println("priority "+cache[7644].priority);
        System.out.println("reset "+cache[7644].reset);
        */
        //System.out.println(findAnimFileId(2851));
        //System.out.println(findAnimFileId(6953));

        //dump(8763);
        //dumpAnimations(9160);

        // temp fix for wintertodt howling snowstorm
        cache[7322].step = 10;
    }

    public static void dump(int amount) {
        File f = new File(System.getProperty("user.home") + "/Desktop/osrs_anim.txt");
        try {
            f.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(f));
            for (int id = 0; id < amount; id++) {
                bf.write(System.getProperty("line.separator"));
                bf.write("animation " + id + " //fileID: " + (cache[id].primary_frame[0] >> 16));
                bf.write(System.getProperty("line.separator"));
            }
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void dumpAnimations(int amount) {
        File f = new File(System.getProperty("user.home") + "/Desktop/animations.txt");
        try {
            f.createNewFile();
            BufferedWriter bf = new BufferedWriter(new FileWriter(f));
            for (int id = 0; id < amount; id++) {
                bf.write("//fileID: " + (cache[id].primary_frame[0] >> 16));
                bf.write(System.getProperty("line.separator"));
                bf.write("case " + id + ":");
                bf.write(System.getProperty("line.separator"));
                if (cache[id].frames > 0) {
                    bf.write("animations[animation].frames = " + cache[id].frames + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].primary_frame != null) {
                    bf.write("animations[animation].primary_frame = new int[] \""
                        + Arrays.toString(cache[id].primary_frame).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].frame_list != null) {
                    bf.write("animations[animation].frame_list = new int[] \""
                        + Arrays.toString(cache[id].frame_list).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].frame_length != null) {
                    bf.write("animations[animation].frame_length = new int[] "
                        + Arrays.toString(cache[id].frame_length).replace("[", "{").replace("]", "}") + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].step != -1) {
                    bf.write("animations[animation].step = " + cache[id].step + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].flow_control != null) {
                    bf.write("animations[animation].flow_control = " + cache[id].flow_control + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].stretch != false) {
                    bf.write("animations[animation].stretch = " + cache[id].stretch + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].appended_frames != 5) {
                    bf.write("animations[animation].appended_frames = " + cache[id].appended_frames + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].shield_delta != -1) {
                    bf.write("animations[animation].shield_delta = " + cache[id].shield_delta + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].weapon_delta != -1) {
                    bf.write("animations[animation].weapon_delta = " + cache[id].weapon_delta + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].loops != 99) {
                    bf.write("animations[animation].loops = " + cache[id].loops + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].tempo != -1) {
                    bf.write("animations[animation].tempo = " + cache[id].tempo + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].priority != 2) {
                    bf.write("animations[animation].priority = " + cache[id].priority + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                if (cache[id].reset > 0) {
                    bf.write("animations[animation].reset = " + cache[id].reset + ";");
                    bf.write(System.getProperty("line.separator"));
                }
                bf.write("break;");
                bf.write(System.getProperty("line.separator"));
                bf.write(System.getProperty("line.separator"));
            }
            bf.close();
        } catch (

            IOException e) {
            e.printStackTrace();
        }
    }

    public static int findAnimFileId(int animId) {
        return Animation.getFileId(cache[animId].primary_frame[0]);
    }

    public int get_length(int id) {
        int duration = frame_length[id];
        if (duration == 0) {
            final Animation frame = Animation.get(primary_frame[id]);
            if (frame != null) {
                duration = frame_length[id] = frame.length;
            }
        }
        if (duration == 0) {
            duration = 1;
        }
        return duration;
    }

    private void decode(Buffer buffer) {
        do {
            int opcode = buffer.readUByte();
            if (opcode == 0)
                break;

            if (opcode == 1) {
                frames = buffer.readUShort();
                primary_frame = new int[frames];
                frame_list = new int[frames];
                frame_length = new int[frames];

                for (int frame = 0; frame < frames; frame++) {
                    frame_length[frame] = buffer.readUShort();
                }

                for (int frame = 0; frame < frames; frame++) {
                    primary_frame[frame] = buffer.readUShort();
                    frame_list[frame] = -1;
                }

                for (int frame = 0; frame < frames; frame++) {
                    primary_frame[frame] += buffer.readUShort() << 16;
                }
            } else if (opcode == 2) {
                step = buffer.readUShort();
            } else if (opcode == 3) {
                int index = buffer.readUByte();
                flow_control = new int[index + 1];
                for (int id = 0; id < index; id++) {
                    flow_control[id] = buffer.readUByte();
                }
                flow_control[index] = 0x98967f;
            } else if (opcode == 4) {
                stretch = true;
            } else if (opcode == 5) {
                appended_frames = buffer.readUByte();
            } else if (opcode == 6) {
                shield_delta = buffer.readUShort();
            } else if (opcode == 7) {
                weapon_delta = buffer.readUShort();
            } else if (opcode == 8) {
                loops = buffer.readUByte();
            } else if (opcode == 9) {
                tempo = buffer.readUByte();
            } else if (opcode == 10) {
                priority = buffer.readUByte();
            } else if (opcode == 11) {
                reset = buffer.readUByte();
            } else if (opcode == 12) {
                int length = buffer.readUByte();
                for (int index = 0; index < length; index++) {
                    buffer.readUShort();
                }
                for (int index = 0; index < length; index++) {
                    buffer.readUShort();
                }
            } else if (opcode == 13) {
                int length = buffer.readUByte();
                for (int index = 0; index < length; index++) {
                    buffer.read24Int();
                }
            } else {
                //System.out.println("Error unrecognised {SEQ} opcode: " + opcode);//use for debugging otherwise it spams the console (127 unrecognized)
            }
        } while (true);

        post_decode();
    }

    private void post_decode() {
        if (frames == 0) {
            frames = 1;
            primary_frame = new int[1];
            primary_frame[0] = -1;
            frame_list = new int[1];
            frame_list[0] = -1;
            frame_length = new int[1];
            frame_length[0] = -1;
        }

        if (tempo == -1) {
            tempo = (flow_control == null) ? 0 : 2;
        }

        if (priority == -1) {
            priority = (flow_control == null) ? 0 : 2;
        }
    }

    @Override
    public String toString() {
        return "Animation{" +
            "frames=" + frames +
            ", primary_frame=" + Arrays.toString(primary_frame) +
            ", frame_list=" + Arrays.toString(frame_list) +
            ", frame_length=" + Arrays.toString(frame_length) +
            ", loopOffset=" + step +
            ", interleaveOrder=" + Arrays.toString(flow_control) +
            ", stretches=" + stretch +
            ", appended_frames=" + appended_frames +
            ", playerOffhand=" + shield_delta +
            ", playerMainhand=" + weapon_delta +
            ", maximumLoops=" + loops +
            ", animatingPrecedence=" + tempo +
            ", walkingPrecedence=" + priority +
            ", replayMode=" + reset +
            '}';
    }
}
