package com.valinor.util.dumper;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class ProtectionValues implements DefinitionOutput {

    private static final Logger logger = LogManager.getLogger(ProtectionValues.class);

    public static class Output {
        private int id;
        public String name = "";
        private int value;
    }

    @Override
    public void dump() throws IOException {
        Map<Integer, ItemProperties> ALL_ITEMS = new GsonBuilder().create().fromJson(new FileReader("./data/list/items-complete.json"), new TypeToken<HashMap<Integer, ItemProperties>>() {}.getType());
        ArrayList<Output> outputs = new ArrayList<>();
        for (ItemProperties prop : ALL_ITEMS.values()) {
            Output output = new Output();
            output.id = prop.id;
            output.name = prop.name;
            output.value = prop.cost;
            outputs.add(output);
        }

        PrintWriter pw = new PrintWriter("protection_values.json", "UTF-8");
        String json = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create().toJson(outputs);
        pw.print(json);
        pw.close();
        System.out.println("Finished dumping Protection values to protection_values.json");
    }
}
