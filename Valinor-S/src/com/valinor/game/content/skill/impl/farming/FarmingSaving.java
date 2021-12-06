package com.valinor.game.content.skill.impl.farming;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.valinor.game.GameEngine;
import com.valinor.game.world.entity.mob.player.Player;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPOutputStream;

public class FarmingSaving {

    private static final Logger log = LoggerFactory.getLogger(FarmingSaving.class);

    private static final Path SAVE_DIRECTORY = Paths.get("data", "saves", "farming", "crops");

    static final Gson gson;

    static final boolean gzCompression;

    static {
        GsonBuilder builder = new GsonBuilder().setPrettyPrinting();
        builder.disableHtmlEscaping();
        gzCompression = false;
        gson = builder.create();

        if (!Files.isDirectory(SAVE_DIRECTORY)) {
            if (Files.exists(SAVE_DIRECTORY)) {
                log.error("Path [{}] exists, but is NOT a directory. Save farming will not work.", SAVE_DIRECTORY);
            } else {
                try {
                    Files.createDirectories(SAVE_DIRECTORY);
                } catch (IOException | SecurityException e) {
                    log.error("Unable to create save farming directory: {}", SAVE_DIRECTORY, e);
                }
            }
        }
    }

    /**
     * Returns the path where the file will be saved.
     *
     * @param username
     * @return the path
     */
    public static Path getPathToSaveFile(String username) {
        StringBuilder builder = new StringBuilder(username.length() + 8);
        builder.append(StringUtils.capitalize(username).replaceAll(" ", "_"));
        builder.append(".json");

        if (gzCompression)
            builder.append(".gz");

        return SAVE_DIRECTORY.resolve(builder.toString());
    }

    /**
     * Saves the player's crops.
     *
     * @param player
     * @return if it was successful
     */
    public static boolean save(Player player) {
        GameEngine.getInstance().submitLowPriority(() -> {
            File file = getPathToSaveFile(player.getUsername()).toFile();

            JsonObject object = new JsonObject();

            object.addProperty("log_action", player.getFarming().getLastLogAction());

            object.add("tools_stored", gson.toJsonTree(player.getFarming().getToolStored()));

            object.add("patch_states", gson.toJsonTree(player.getFarming().getPatchStates()));

            object.add("compost_bins", gson.toJsonTree(player.getFarming().getCompostManager().getCompostBins()));

            try (OutputStreamWriter out = openWriter(file)) {
                gson.toJson(object, out);
            } catch (IOException e) {
                log.error("Could not save character: {}.", file.getName(), e);
            }
        });
        return true;
    }


    /**
     * Writes the file's information.
     *
     * @param file
     * @return
     * @throws IOException
     */
    private static OutputStreamWriter openWriter(File file) throws IOException {
        OutputStream out = new FileOutputStream(file);
        if (gzCompression) {
            out = new GZIPOutputStream(out);
        }
        return new OutputStreamWriter(out);
    }

}
