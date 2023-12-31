package com.valinor.sign;

import com.valinor.Client;
import com.valinor.ClientConstants;

import javax.sound.midi.*;
import javax.sound.sampled.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public final class SignLink implements Runnable {

    public static final int clientversion = 317;
    public static int storeid = 32;
    public static RandomAccessFile cache_dat = null;
    public static final RandomAccessFile[] indices = new RandomAccessFile[5];
    public static boolean sunjava;
    private static boolean active;
    private static int threadLiveId;
    private static InetAddress socketAddress;
    private static int socketRequest;
    private static Socket socket = null;
    private static int threadreqpri = 1;
    private static Runnable threadreq = null;
    private static String dnsreq = null;
    public static String dns = null;
    private static String urlRequest = null;
    private static DataInputStream urlStream = null;
    private static int savelen;
    private static String savereq = null;
    private static byte[] savebuf = null;
    private static boolean play;
    private static int midipos;
    public static String midi = null;
    public static int midiVolume;
    public static int fadeMidi;
    private static boolean waveplay;
    private static int wavepos;
    public static int wavevol;
    public static boolean reporterror = true;
    public static String errorName = "";

    private SignLink() {
    }

    public static void startpriv(InetAddress inetaddress) {
        threadLiveId = (int) (Math.random() * 99999999D);
        if (active) {
            try {
                Thread.sleep(500L);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
            active = false;
        }
        socketRequest = 0;
        threadreq = null;
        dnsreq = null;
        savereq = null;
        urlRequest = null;
        socketAddress = inetaddress;
        Thread thread = new Thread(new SignLink());
        thread.setName(""+ ClientConstants.CLIENT_NAME+"ClientSignLinkStartPrivThread");
        thread.setDaemon(true);
        thread.start();
        while (!active)
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
    }

    public void run() {
        active = true;
        String directory = findCacheDir();
        try {
            cache_dat = new RandomAccessFile(directory + "main_file_cache.dat", "rw");
            for (int index = 0; index < 5; index++) {
                indices[index] = new RandomAccessFile(directory + "main_file_cache.idx" + index, "rw");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        for (int i = threadLiveId; threadLiveId == i;) {
            if (socketRequest != 0) {
                try {
                    socket = new Socket(socketAddress, socketRequest);
                } catch (Exception _ex) {
                    _ex.printStackTrace();
                    socket = null;
                }
                socketRequest = 0;
            } else if (threadreq != null) {
                Thread thread = new Thread(threadreq);
                thread.setName(""+ ClientConstants.CLIENT_NAME+"ClientSignLinkThreadReqThread");
                thread.setDaemon(true);
                thread.start();
                thread.setPriority(threadreqpri);
                threadreq = null;
            } else if (dnsreq != null) {
                try {
                    dns = InetAddress.getByName(dnsreq).getHostName();
                } catch (Exception _ex) {
                    _ex.printStackTrace();
                    dns = "unknown";
                }
                dnsreq = null;
            } else if (savereq != null) {
                if (savebuf != null)
                    try {
                        FileOutputStream fileoutputstream = new FileOutputStream(
                                directory + savereq);
                        fileoutputstream.write(savebuf, 0, savelen);
                        fileoutputstream.close();
                    } catch (Exception _ex) {
                        _ex.printStackTrace();
                    }
                if (waveplay) {
                    String wave = directory + savereq;
                    waveplay = false;
                    AudioInputStream audioInputStream = null;
                    try {
                        audioInputStream = AudioSystem.getAudioInputStream(new File(wave));
                    } catch (UnsupportedAudioFileException | IOException e1) {
                        e1.printStackTrace();
                        return;
                    }
                    AudioFormat format = audioInputStream.getFormat();
                    SourceDataLine auline = null;
                    DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
                    try {
                        auline = (SourceDataLine) AudioSystem.getLine(info);
                        auline.open(format);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                if (play) {
                    midi = directory + savereq;
                    try {
                        if (music != null) {
                            music.stop();
                            music.close();
                        }
                        playMidi(midi);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    play = false;
                }
                savereq = null;
            } else if (urlRequest != null) {
                try {
                    System.out.println("urlstream");
                } catch (Exception _ex) {
                    urlStream = null;
                }
                urlRequest = null;
            }
            try {
                Thread.sleep(50L);
            } catch (Exception _ex) {
                _ex.printStackTrace();
            }
        }
    }

    public static String findCacheDir() {
        if (ClientConstants.production || ClientConstants.test) {
            File file;
            String home = System.getProperty("user.home");
            String separator = System.getProperty("file.separator");
            String cacheName = ClientConstants.CACHE_NAME;
            String cacheDir = home + separator + cacheName + separator;
            file = new File(cacheDir);
            if (file.exists() || file.mkdir()) {
                return cacheDir;
            }
        } else {
            final File cacheDirectory = new File("./.ValinorPK/");
            if (!cacheDirectory.exists())
                cacheDirectory.mkdir();
            return "./.ValinorPK/";
        }
        return null;
    }

    public static String findDataDir() {
        if (ClientConstants.production || ClientConstants.test) {
            File file;
            String home = System.getProperty("user.home");
            String separator = System.getProperty("file.separator");
            String cacheName = ClientConstants.DATA_NAME;
            String cacheDir = home + separator + cacheName + separator;
            file = new File(cacheDir);
            if (file.exists() || file.mkdir()) {
                return cacheDir;
            }
        } else {
            final File cacheDirectory = new File("./.ValinorPK-data/");
            if (!cacheDirectory.exists())
                cacheDirectory.mkdir();
            return "./.ValinorPK-data/";
        }
        return null;
    }

    /**
     * Plays the specified midi sequence.
     *
     * @param location
     */
    private void playMidi(String location) {
        music = null;
        synthesizer = null;
        sequence = null;
        File midiFile = new File(location);
        try {
            sequence = MidiSystem.getSequence(midiFile);
            music = MidiSystem.getSequencer();
            music.open();
            music.setSequence(sequence);
        } catch (Exception e) {
            System.err.println("Problem loading MIDI file.");
            e.printStackTrace();
            return;
        }
        if (music instanceof Synthesizer) {
            synthesizer = (Synthesizer) music;
        } else {
            try {
                synthesizer = MidiSystem.getSynthesizer();
                synthesizer.open();
                if (synthesizer.getDefaultSoundbank() == null) {
                    music.getTransmitter().setReceiver(MidiSystem.getReceiver());
                } else {
                    music.getTransmitter().setReceiver(synthesizer.getReceiver());
                }
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }
        }
        music.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);
        music.start();
    }

    /**
     * Sets the volume for the midi synthesizer.
     *
     * @param value
     */
    public static void setVolume(int value) {
        int CHANGE_VOLUME = 7;
        midiVolume = value;
        if (synthesizer.getDefaultSoundbank() == null) {
            try {
                ShortMessage volumeMessage = new ShortMessage();
                for (int i = 0; i < 16; i++) {
                    volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i, CHANGE_VOLUME, midiVolume);
                    volumeMessage.setMessage(ShortMessage.CONTROL_CHANGE, i,39, midiVolume);
                    MidiSystem.getReceiver().send(volumeMessage, -1);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MidiChannel[] channels = synthesizer.getChannels();
            for (int c = 0; channels != null && c < channels.length; c++) {
                channels[c].controlChange(CHANGE_VOLUME, midiVolume);
                channels[c].controlChange(39, midiVolume);
            }
        }
    }

    public static Sequencer music = null;
    public static Sequence sequence = null;
    public static Synthesizer synthesizer = null;

    public static synchronized boolean saveWave(byte abyte0[], int i) {
        if (i > 0x1e8480)
            return false;
        if (savereq != null) {
            return false;
        } else {
            wavepos = (wavepos + 1) % 5;
            savelen = i;
            savebuf = abyte0;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static synchronized boolean replayWave() {
        if (savereq != null) {
            return false;
        } else {
            savebuf = null;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static synchronized void saveMidi(byte abyte0[], int i) {
        if (i > 0x1e8480)
            return;
        if (savereq != null) {
        } else {
            midipos = (midipos + 1) % 5;
            savelen = i;
            savebuf = abyte0;
            play = true;
            savereq = "jingle" + midipos + ".mid";
        }
    }

    public static synchronized Socket openSocket(int port) throws IOException {
        for (socketRequest = port; socketRequest != 0;)
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }
        if (socket == null)
            throw new IOException("could not open socket");
        else
            return socket;
    }

    public static synchronized DataInputStream openUrl(String url)
            throws IOException {
        for (urlRequest = url; urlRequest != null;) {
            try {
                Thread.sleep(50L);
            } catch (Exception ignored) {
            }
        }

        if (urlStream == null) {
            throw new IOException("could not open: " + url);
        }
        return urlStream;
    }

    public static synchronized void dnslookup(String s) {
        dns = s;
        dnsreq = s;
    }

    public static synchronized void startthread(Runnable runnable, int i) {
        threadreqpri = i;
        threadreq = runnable;
    }

    public static synchronized boolean wavesave(byte abyte0[], int i) {
        if (i > 0x1e8480)
            return false;
        if (savereq != null) {
            return false;
        } else {
            wavepos = (wavepos + 1) % 5;
            savelen = i;
            savebuf = abyte0;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static synchronized boolean wavereplay() {
        if (savereq != null) {
            return false;
        } else {
            savebuf = null;
            waveplay = true;
            savereq = "sound" + wavepos + ".wav";
            return true;
        }
    }

    public static String indexLocation(int cacheIndex, int index) {
        return SignLink.findCacheDir() + "index" + cacheIndex + "/"
                + (index != -1 ? index + ".gz" : "");
    }

    public static void reporterror(String s) {
        System.err.println("Error: " + s);
    }

    public static void setError(String error) {
        errorName = error;
    }
}
