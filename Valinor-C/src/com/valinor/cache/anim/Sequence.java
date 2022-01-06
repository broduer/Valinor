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
    public int frameCount;
    public int[] primaryFrames;
    public int[] secondaryFrames;
    public int[] durations;
    public int loopOffset;
    public int[] interleaveOrder;
    public boolean stretches;
    public int forcedPriority;
    public int playerOffhand;
    public int playerMainhand;
    public int maximumLoops;
    public int animatingPrecedence;
    public int priority;
    public int replayMode;

    public Sequence() {
        loopOffset = -1;
        stretches = false;
        forcedPriority = 5;
        playerOffhand = -1; // Removes shield
        playerMainhand = -1; // Removes weapon
        maximumLoops = 99;
        animatingPrecedence = -1; // Stops character from moving
        priority = -1;
        replayMode = 1; // replayMode default value 2 in OSRS, can change back to 1 if causes problems with animations.
    }

    public static void init(Archive archive) {
        final Buffer buffer = new Buffer(ClientConstants.LOAD_OSRS_DATA_FROM_CACHE_DIR ? FileUtils.read(ClientConstants.DATA_DIR + "/anims/seq.dat") : archive.get("seq.dat"));
        int length = buffer.readUShort();

        System.out.printf("Loaded %d animations loading OSRS version %d and SUB version %d%n", length, ClientConstants.OSRS_DATA_VERSION, ClientConstants.OSRS_DATA_SUB_VERSION);

        if (cache == null) {
            cache = new Sequence[length + 5000];
        }

        for(int j = 0; j < length; j++) {
            if(cache[j] == null)
                cache[j] = new Sequence();
            cache[j].decode(buffer);
        }
    }

    public int duration(int id) {
        int duration = durations[id];
        if (duration == 0) {
            final Animation frame = Animation.get(primaryFrames[id]);
            if (frame != null) {
                duration = durations[id] = frame.duration;
            }
        }
        if (duration == 0) {
            duration = 1;
        }
        return duration;
    }

    private void decode(Buffer buffer) {
        while(true) {
            int opcode = buffer.readUByte();
            if (opcode == 0) {
                break;
            } else if (opcode == 1) {
                frameCount = buffer.readUShort();
                primaryFrames = new int[frameCount];
                secondaryFrames = new int[frameCount];
                durations = new int[frameCount];

                for (int i = 0; i < frameCount; i++) {
                    durations[i] = buffer.readUShort();
                }

                for (int i = 0; i < frameCount; i++) {
                    primaryFrames[i] = buffer.readUShort();
                    secondaryFrames[i] = -1;
                }

                for (int i = 0; i < frameCount; i++) {
                    primaryFrames[i] += buffer.readUShort() << 16;
                }
            } else if (opcode == 2) {
                loopOffset = buffer.readUShort();
            } else if (opcode == 3) {
                int index = buffer.readUByte();
                interleaveOrder = new int[index + 1];
                for (int id = 0; id < index; id++) {
                    interleaveOrder[id] = buffer.readUByte();
                }
                interleaveOrder[index] = 9999999;
            } else if (opcode == 4) {
                stretches = true;
            } else if (opcode == 5) {
                forcedPriority = buffer.readUByte();
            } else if (opcode == 6) {
                playerOffhand = buffer.readUShort();
            } else if (opcode == 7) {
                playerMainhand = buffer.readUShort();
            } else if (opcode == 8) {
                maximumLoops = buffer.readUByte();
            } else if (opcode == 9) {
                animatingPrecedence = buffer.readUByte();
            } else if (opcode == 10) {
                priority = buffer.readUByte();
            } else if (opcode == 11) {
                replayMode = buffer.readUByte();
            } else if (opcode == 12) {
                int len = buffer.readUnsignedByte();

                for (int i = 0; i < len; i++) {
                    buffer.readUShort();
                }

                for (int i = 0; i < len; i++) {
                    buffer.readUShort();
                }
            } else if (opcode == 13) {
                int len = buffer.readUnsignedByte();

                for (int i = 0; i < len; i++) {
                    buffer.read24Int();
                }
            }
        }

        if (frameCount == 0) {
            frameCount = 1;
            primaryFrames = new int[1];
            primaryFrames[0] = -1;
            secondaryFrames = new int[1];
            secondaryFrames[0] = -1;
            durations = new int[1];
            durations[0] = -1;
        }

        if (animatingPrecedence == -1) {
            animatingPrecedence = (interleaveOrder == null) ? 0 : 2;
        }

        if (priority == -1) {
            priority = (interleaveOrder == null) ? 0 : 2;
        }
    }

    @Override
    public String toString() {
        return "Sequence{" +
            "frameCount=" + frameCount +
            ", primaryFrames=" + Arrays.toString(primaryFrames) +
            ", secondaryFrames=" + Arrays.toString(secondaryFrames) +
            ", durations=" + Arrays.toString(durations) +
            ", loopOffset=" + loopOffset +
            ", interleaveOrder=" + Arrays.toString(interleaveOrder) +
            ", stretches=" + stretches +
            ", forcedPriority=" + forcedPriority +
            ", playerOffhand=" + playerOffhand +
            ", playerMainhand=" + playerMainhand +
            ", maximumLoops=" + maximumLoops +
            ", animatingPrecedence=" + animatingPrecedence +
            ", priority=" + priority +
            ", replayMode=" + replayMode +
            '}';
    }
}
