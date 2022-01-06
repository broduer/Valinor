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
        while(true) {
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
