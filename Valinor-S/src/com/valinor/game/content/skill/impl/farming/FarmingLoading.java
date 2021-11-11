package com.valinor.game.content.skill.impl.farming;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.valinor.game.content.skill.impl.farming.compostbin.CompostBin;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Handles the loading of all farming information.
 *
 * @author Gabriel || Wolfsdarker
 */
public class FarmingLoading {

    /**
     * The logger's instance.
     */
    private static final Logger log = LoggerFactory.getLogger(FarmingLoading.class);

    /**
     * Loads the file's reader.
     *
     * @param file
     * @return the reader
     * @throws IOException
     */
    private static InputStreamReader openReader(File file) throws IOException {
        InputStream in = new FileInputStream(file);

        if (FarmingSaving.gzCompression) {
            in = new GZIPInputStream(in);
        }

        return new InputStreamReader(in);
    }

    /**
     * Loads all the crop's saved on the username.
     *
     * @param username
     * @param farming
     * @return if a file was found
     */
    public static boolean loadCrops(String username, Farming farming) {

        File file = FarmingSaving.getPathToSaveFile(username).toFile();


        try (InputStreamReader in = openReader(file)) {
            JsonParser fileParser = new JsonParser();
            Gson builder = FarmingSaving.gson;
            JsonObject reader = fileParser.parse(in).getAsJsonObject();

            if (reader.has("log_action")) {
                farming.setLastLogAction(reader.get("log_action").getAsLong());
            }

            if (reader.has("tools_stored")) {

                Type type = new TypeToken<HashMap<Integer, Integer>>() {
                }.getType();

                HashMap<Integer, Integer> patch_states = builder.fromJson(reader.get("tools_stored"), type);
                patch_states.keySet().forEach(key -> farming.getToolStored().put(key, patch_states.get(key)));
            }

            if (reader.has("patch_states")) {

                Type type = new TypeToken<HashMap<String, PatchState>>() {
                }.getType();

                HashMap<String, PatchState> patch_states = builder.fromJson(reader.get("patch_states"), type);
                patch_states.keySet().forEach(key -> farming.getPatchStates().put(key, patch_states.get(key)));
            }

            if (reader.has("compost_bins")) {
                List<CompostBin> compost_bins = builder.fromJson(reader.get("compost_bins"), new TypeToken<List<CompostBin>>() {}.getType());
                compost_bins.forEach(key -> farming.getCompostManager().getCompostBins().add(key));
            }

        } catch (FileNotFoundException ex) {
            return false;
        } catch (IOException ex) {
            log.error("Character failed to be loaded: {}", file, ex);
        }

        return true;
    }

}
