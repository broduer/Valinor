package com.valinor.cache.anim;
import com.valinor.io.Buffer;

public final class Skins {

    /**
     * The type of each transformation.
     */
    public final int[] transformationType;
    public final int[][] skinList;

    public Skins(Buffer stream) {
        int count = stream.readUShort();

        transformationType = new int[count];
        skinList = new int[count][];

        for (int index = 0; index < count; index++) {
            transformationType[index] = stream.readUShort();
        }

        for (int label = 0; label < count; label++) {
            skinList[label] = new int[stream.readUShort()];
        }

        for (int label = 0; label < count; label++) {
            for (int index = 0; index < skinList[label].length; index++) {
                skinList[label][index] = stream.readUShort();
            }
        }
    }

}
