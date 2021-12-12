package com.valinor.sound;

import com.valinor.io.Buffer;

public final class Track {

    private Track() {
        samples = new Synthesizer[10];
    }

    public static void unpack(Buffer incoming) {
        output = new byte[0x6baa8];
        riff = new Buffer(output);
        Synthesizer.init();
        do {
            int id = incoming.readUShort();
            if (id == 65535)
                return;
            tracks[id] = new Track();
            tracks[id].decode(incoming);
            delays[id] = tracks[id].calculateDelay();
        } while (true);
    }

    public static Buffer data(int loops, int id) {
        if (tracks[id] != null) {
            Track soundTrack = tracks[id];
            return soundTrack.pack(loops);
        } else {
            return null;
        }
    }

    private void decode(Buffer stream) {
        for (int synthesizer = 0; synthesizer < 10; synthesizer++) {
            int valid = stream.readUByte();
            if (valid != 0) {
                stream.pos--;
                samples[synthesizer] = new Synthesizer();
                samples[synthesizer].load(stream);
            }
        }
        remaining = stream.readUShort();
        length = stream.readUShort();
    }

    private int calculateDelay() {
        int offset = 0x98967f;
        for (int syntheziser = 0; syntheziser < 10; syntheziser++)
            if (samples[syntheziser] != null
                    && samples[syntheziser].offset / 20 < offset)
                offset = samples[syntheziser].offset / 20;

        if (remaining < length && remaining / 20 < offset)
            offset = remaining / 20;
        if (offset == 0x98967f || offset == 0)
            return 0;
        for (int synthesizer = 0; synthesizer < 10; synthesizer++)
            if (samples[synthesizer] != null)
                samples[synthesizer].offset -= offset * 20;

        if (remaining < length) {
            remaining -= offset * 20;
            length -= offset * 20;
        }
        return offset;
    }

    private Buffer pack(int loops) {
        int size = mix(loops);
        riff.pos = 0;
        riff.writeInt(0x52494646);
        riff.writeLEInt(36 + size);
        riff.writeInt(0x57415645);
        riff.writeInt(0x666d7420);
        riff.writeLEInt(16);
        riff.writeLEShort(1);
        riff.writeLEShort(1);
        riff.writeLEInt(22050);
        riff.writeLEInt(22050);
        riff.writeLEShort(1);
        riff.writeLEShort(8);
        riff.writeInt(0x64617461);
        riff.writeLEInt(size);
        riff.pos += size;
        return riff;
    }

    private int mix(int loops) {
        int duration = 0;
        for (int synthesizer = 0; synthesizer < 10; synthesizer++)
            if (samples[synthesizer] != null
                    && samples[synthesizer].duration + samples[synthesizer].offset > duration)
                duration = samples[synthesizer].duration + samples[synthesizer].offset;

        if (duration == 0)
            return 0;
        int sampleCount = (22050 * duration) / 1000;
        int loopStart = (22050 * this.remaining) / 1000;
        int loopEnd = (22050 * this.length) / 1000;
        if (loopStart < 0 || loopStart > sampleCount || loopEnd < 0 || loopEnd > sampleCount || loopStart >= loopEnd)
            loops = 0;
        int size = sampleCount + (loopEnd - loopStart) * (loops - 1);
        for (int offset = 44; offset < size + 44; offset++)
            output[offset] = -128;

        for (int synthesizer = 0; synthesizer < 10; synthesizer++)
            if (samples[synthesizer] != null) {
                int synthDuration = (samples[synthesizer].duration * 22050) / 1000;
                int synthOffset = (samples[synthesizer].offset * 22050) / 1000;
                int[] samples = this.samples[synthesizer].synthesize(synthDuration,
                        this.samples[synthesizer].duration);
                for (int sample = 0; sample < synthDuration; sample++)
                    output[sample + synthOffset + 44] += (byte) (samples[sample] >> 8);
            }

        if (loops > 1) {
            loopStart += 44;
            loopEnd += 44;
            sampleCount += 44;
            int k2 = (size += 44) - sampleCount;
            if (sampleCount - loopEnd >= 0) System.arraycopy(output, loopEnd, output, loopEnd + k2, sampleCount - loopEnd);

            for (int k3 = 1; k3 < loops; k3++) {
                int l2 = (loopEnd - loopStart) * k3;
                System.arraycopy(output, loopStart, output, loopStart + l2, loopEnd - loopStart);
            }

            size -= 44;
        }
        return size;
    }

    private static final Track[] tracks = new Track[5000];
    public static final int[] delays = new int[5000];
    private static byte[] output;
    private static Buffer riff;
    private final Synthesizer[] samples;
    private int remaining;
    private int length;

}
