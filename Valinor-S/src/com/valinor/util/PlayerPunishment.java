package com.valinor.util;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Patrick van Elderen | January, 16, 2021, 10:18
 * @see <a href="https://www.rune-server.ee/members/Zerikoth/">Rune-Server profile</a>
 */
public class PlayerPunishment {

    /**
     * Bans & mutes.
     */
    public static Set<String> ipBannedUsers = new HashSet<>(),
        macBannedUsers = new HashSet<>(),
        ipMutedUsers = new HashSet<>(),
        mutedUsers = new HashSet<>(),
        bannedUsers = new HashSet<>();

    public static void init() {
        bannedUsers();
        mutedUsers();
        macBannedUsers();
        ipBannedUsers();
        ipMutedUsers();
    }

    public static void bannedUsers() {
        File file = new File("./data/saves/bannedUsers.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (!bannedUsers.contains(line) && !line.isEmpty()) {
                    bannedUsers.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addBan(String user) {
        if (!bannedUsers.contains(user) && !user.isEmpty()) {
            bannedUsers.add(user);
            updateBanFile();
        }
    }

    public static void removeBan(String user) {
        if (bannedUsers.contains(user) && !user.isEmpty()) {
            bannedUsers.remove(user);
            updateBanFile();
        }
    }

    private static void updateBanFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./data/saves/bannedUsers.txt"))) {
            for (String user : bannedUsers) {
                out.write(user);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a player is already on the ban list.
     */
    public static boolean banned(String name) {
        return bannedUsers.contains(name);
    }

    public static void mutedUsers() {
        File file = new File("./data/saves/mutedUsers.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (!mutedUsers.contains(line) && !line.isEmpty()) {
                    mutedUsers.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMute(String address) {
        if (!mutedUsers.contains(address) && !address.isEmpty()) {
            mutedUsers.add(address);
            updateMuteFile();
        }
    }

    public static void removeMute(String address) {
        if (mutedUsers.contains(address) && !address.isEmpty()) {
            mutedUsers.remove(address);
            updateMuteFile();
        }
    }

    private static void updateMuteFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./data/saves/mutedUsers.txt"))) {
            for (String user : mutedUsers) {
                out.write(user);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a player is already on the mute list.
     */
    public static boolean muted(String name) {
        return mutedUsers.contains(name);
    }

    public static void ipMutedUsers() {
        File file = new File("./data/saves/ipMutedUsers.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (!ipMutedUsers.contains(line) && !line.isEmpty()) {
                    ipMutedUsers.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addIPMute(String address) {
        if (!ipMutedUsers.contains(address) && !address.isEmpty()) {
            ipMutedUsers.add(address);
            updateIpMuteFile();
        }
    }

    public static void removeIpMute(String address) {
        if (ipMutedUsers.contains(address) && !address.isEmpty()) {
            ipMutedUsers.remove(address);
            updateIpMuteFile();
        }
    }

    private static void updateIpMuteFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./data/saves/ipMutedUsers.txt"))) {
            for (String ip : ipMutedUsers) {
                out.write(ip);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a player is already on the IP mute list.
     */
    public static boolean IPmuted(String name) {
        return ipMutedUsers.contains(name);
    }

    public static void ipBannedUsers() {
        File file = new File("./data/saves/ipBannedUsers.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (!ipBannedUsers.contains(line) && !line.isEmpty()) {
                    ipBannedUsers.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addIPBan(String address) {
        if (!ipBannedUsers.contains(address) && !address.isEmpty()) {
            ipBannedUsers.add(address);
            updateIpBanFile();
        }
    }

    public static void removeIpBan(String address) {
        if (ipBannedUsers.contains(address) && !address.isEmpty()) {
            ipBannedUsers.remove(address);
            updateIpBanFile();
        }
    }

    private static void updateIpBanFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./data/saves/ipBannedUsers.txt"))) {
            for (String ip : ipBannedUsers) {
                out.write(ip);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean ipBanned(String address) {
        return ipBannedUsers.contains(address);
    }

    public static void macBannedUsers() {
        File file = new File("./data/saves/macBannedUsers.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            return;
        }
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                if (!macBannedUsers.contains(line) && !line.isEmpty()) {
                    macBannedUsers.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addMacBan(String address) {
        if (!macBannedUsers.contains(address) && !address.isEmpty()) {
            macBannedUsers.add(address);
            updateMacBanFile();
        }
    }

    public static void removeMacBan(String address) {
        if (macBannedUsers.contains(address) && !address.isEmpty()) {
            macBannedUsers.remove(address);
            updateMacBanFile();
        }
    }

    private static void updateMacBanFile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter("./data/saves/macBannedUsers.txt"))) {
            for (String mac : macBannedUsers) {
                out.write(mac);
                out.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean macBanned(String address) {
        return macBannedUsers.contains(address);
    }
}
