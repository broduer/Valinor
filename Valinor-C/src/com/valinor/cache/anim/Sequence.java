package com.valinor.cache.anim;

import com.valinor.ClientConstants;
import com.valinor.cache.Archive;
import com.valinor.io.Buffer;
import com.valinor.util.FileUtils;

import java.util.Arrays;

public final class Sequence {

    public static Sequence[] cache;
    public int frameCount;
    public int[] frameIDs; // top 16 bits are FrameDefinition ids
    public int[] chatFrameIds;
    public int[] frameLenghts;
    public int[] frameSounds;
    public int[] secondaryFrames;
    public int frameStep = -1;
    public int[] interleaveOrder;
    public boolean stretches = false;
    public int forcedPriority = 5;
    public int leftHandItem = -1;
    public int rightHandItem = -1;
    public int maxLoops = 99;
    public int precedenceAnimating = -1;
    public int priority = -1;
    public int replyMode = 2;

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
            if(animation == 2851) {
                cache[animation] = new Sequence();
                cache[animation].frameCount = 6;
                cache[animation].frameLenghts = new int[]{5, 5, 3, 6, 4, 3};
                cache[animation].frameIDs = new int[]{983040, 983041, 983042, 983043, 983044, 983045};
                cache[animation].forcedPriority = 5;
                cache[animation].replyMode = 1;
            }
        }

        while (animation < cache.length) {
            if (animation == 11900) {
                cache[animation] = new Sequence();
                cache[animation].frameCount = 14;
                cache[animation].frameIDs = new int[] {188285009, 188284950, 188284989, 188285162, 188284987, 188285107, 188285131, 188285085, 188285159, 188285041, 188285071, 188285204, 188285174, 188285152};
                cache[animation].secondaryFrames = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                cache[animation].frameLenghts = new int[]{3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                cache[animation].precedenceAnimating = 3;
                cache[animation].priority = 1;
            }
            if (animation == 11901) {
                cache[animation] = new Sequence();
                cache[animation].frameCount = 13;
                cache[animation].frameIDs = new int[] {19267601, 19267602, 19267603, 19267604, 19267605, 19267606, 19267607, 19267606, 19267605, 19267604, 19267603, 19267602, 19267601};
                cache[animation].frameLenghts = new int[] {4, 3, 3, 4, 10, 10, 15, 10, 10, 4, 3, 3, 4};
                cache[animation].secondaryFrames = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                cache[animation].interleaveOrder = new int[] {1, 2, 9, 11, 13, 15, 17, 19, 37, 39, 41, 43, 45, 164, 166, 168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 191, 192, 9999999};
                cache[animation].forcedPriority = 6;
                cache[animation].precedenceAnimating = 2;
                cache[animation].priority = 2;
                cache[animation].replyMode = 1;
            }
            if (animation == 11902) {
                cache[animation] = new Sequence();
                cache[animation].frameCount = 31;
                cache[animation].frameIDs  = new int[] {114295000, 114294953, 114295281, 114295193, 114295189, 114295249, 114295182, 114295061, 114295124, 114295007, 114295337, 114295102, 114294995, 114295283, 114295025, 114294899, 114295233, 114294879, 114295175, 114295169, 114294903, 114295091, 114295059, 114295267, 114295003, 114294981, 114294951, 114295031, 114294986, 114294820, 114295226};
                cache[animation].frameLenghts  = new int[] {3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3};
                cache[animation].secondaryFrames  = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
                cache[animation].interleaveOrder = new int[] {164, 166, 168, 170, 172, 174, 176, 178, 180, 182, 183, 185, 193, 194, 196, 197, 198, 200, 202, 203, 204, 9999999};
                cache[animation].stretches = true;
                cache[animation].forcedPriority = 6;
                cache[animation].precedenceAnimating = 2;
                cache[animation].priority = 2;
            }
            //Custom animations unsupported by the OSRS rev
            animation++;
        }

        /*System.out.println("frameCount "+cache[7644].frameCount);
        System.out.println("frameIDs "+Arrays.toString(cache[7644].frameIDs));
        System.out.println("frameLenghts "+Arrays.toString(cache[7644].frameLenghts));
        System.out.println("secondaryFrames "+Arrays.toString(cache[7644].secondaryFrames));
        System.out.println("interleaveOrder "+Arrays.toString(cache[7644].interleaveOrder));
        System.out.println("frameStep "+cache[7644].frameStep);
        System.out.println("stretches "+cache[7644].stretches);
        System.out.println("forcedPriority "+cache[7644].forcedPriority);
        System.out.println("rightHandItem "+cache[7644].rightHandItem);
        System.out.println("leftHandItem "+cache[7644].leftHandItem);
        System.out.println("maxLoops "+cache[7644].maxLoops);
        System.out.println("precedenceAnimating "+cache[7644].precedenceAnimating);
        System.out.println("priority "+cache[7644].priority);
        System.out.println("replyMode "+cache[7644].replyMode);*/
        //System.out.println(findAnimFileId(2851));
        //System.out.println(findAnimFileId(7004));

        // temp fix for wintertodt howling snowstorm
        cache[7322].frameStep = 10;
    }

    public static int findAnimFileId(int animId) {
        return Animation.getFileId(cache[animId].frameIDs[0]);
    }

    public int duration(int id) {
        int duration = frameLenghts[id];
        if (duration == 0) {
            final Animation frame = Animation.get(frameIDs[id]);
            if (frame != null) {
                duration = frameLenghts[id] = frame.duration;
            }
        }
        if (duration == 0) {
            duration = 1;
        }
        return duration;
    }

    private void decode(Buffer buffer) {
        while (true) {
            int opcode = buffer.readUByte();
            if (opcode == 0) {
                break;
            } else if (opcode == 1) {
                frameCount = buffer.readUShort();
                frameIDs = new int[frameCount];
                secondaryFrames = new int[frameCount];
                frameLenghts = new int[frameCount];

                for (int i = 0; i < frameCount; i++) {
                    frameLenghts[i] = buffer.readUShort();
                }

                for (int i = 0; i < frameCount; i++) {
                    frameIDs[i] = buffer.readUShort();
                    secondaryFrames[i] = -1;
                }

                for (int i = 0; i < frameCount; i++) {
                    frameIDs[i] += buffer.readUShort() << 16;
                }
            } else if (opcode == 2) {
                frameStep = buffer.readUShort();
            } else if (opcode == 3) {
                int index = buffer.readUByte();
                interleaveOrder = new int[1 + index];
                for (int id = 0; id < index; id++) {
                    interleaveOrder[id] = buffer.readUByte();
                }
                interleaveOrder[index] = 9999999;
            } else if (opcode == 4) {
                stretches = true;
            } else if (opcode == 5) {
                forcedPriority = buffer.readUByte();
            } else if (opcode == 6) {
                leftHandItem = buffer.readUShort();
            } else if (opcode == 7) {
                rightHandItem = buffer.readUShort();
            } else if (opcode == 8) {
                maxLoops = buffer.readUByte();
            } else if (opcode == 9) {
                precedenceAnimating = buffer.readUByte();
            } else if (opcode == 10) {
                priority = buffer.readUByte();
            } else if (opcode == 11) {
                replyMode = buffer.readUByte();
            } else if (opcode == 12) {
                int len = buffer.readUnsignedByte();
                chatFrameIds = new int[len];

                for (int i = 0; i < len; i++) {
                    chatFrameIds[i] = buffer.readUShort();
                }

                for (int i = 0; i < len; i++) {
                    chatFrameIds[i] += buffer.readUShort() << 16;
                }
            } else if (opcode == 13) {
                int len = buffer.readUnsignedByte();
                frameSounds = new int[len];

                for (int i = 0; i < len; i++) {
                    frameSounds[i] = buffer.read24Int();
                }
            }
        }

        if (frameCount == 0) {
            frameCount = 1;
            frameIDs = new int[1];
            frameIDs[0] = -1;
            secondaryFrames = new int[1];
            secondaryFrames[0] = -1;
            frameLenghts = new int[1];
            frameLenghts[0] = -1;
        }

        if (precedenceAnimating == -1) {
            precedenceAnimating = (interleaveOrder == null) ? 0 : 2;
        }

        if (priority == -1) {
            priority = (interleaveOrder == null) ? 0 : 2;
        }
    }

    @Override
    public String toString() {
        return "Sequence{" +
            "frameCount=" + frameCount +
            ", frameIDs=" + Arrays.toString(frameIDs) +
            ", chatFrameIds=" + Arrays.toString(chatFrameIds) +
            ", frameLenghts=" + Arrays.toString(frameLenghts) +
            ", frameSounds=" + Arrays.toString(frameSounds) +
            ", secondaryFrames=" + Arrays.toString(secondaryFrames) +
            ", frameStep=" + frameStep +
            ", interleaveOrder=" + Arrays.toString(interleaveOrder) +
            ", stretches=" + stretches +
            ", forcedPriority=" + forcedPriority +
            ", leftHandItem=" + leftHandItem +
            ", rightHandItem=" + rightHandItem +
            ", maxLoops=" + maxLoops +
            ", precedenceAnimating=" + precedenceAnimating +
            ", priority=" + priority +
            ", replyMode=" + replyMode +
            '}';
    }
}
