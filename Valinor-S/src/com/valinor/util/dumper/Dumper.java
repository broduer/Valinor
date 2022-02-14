package com.valinor.util.dumper;

import com.valinor.GameServer;
import nl.bartpelle.dawnguard.DataStore;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since February 12, 2022
 */
public class Dumper {

    public static DataStore fileStore;

    public static void main(String[] args) {
        try  {
            fileStore = new DataStore(GameServer.properties().fileStore);
            System.out.println("Loaded "+ fileStore.getIndexCount() +" indexes from the filestore.");
            DefinitionOutput output = new ProtectionValues();
            output.dump();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
