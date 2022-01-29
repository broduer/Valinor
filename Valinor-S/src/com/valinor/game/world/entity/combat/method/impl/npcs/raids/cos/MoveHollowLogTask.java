package com.valinor.game.world.entity.combat.method.impl.npcs.raids.cos;

import com.valinor.game.task.Task;
import com.valinor.game.world.object.GameObject;
import com.valinor.game.world.object.MapObjects;
import com.valinor.game.world.object.ObjectManager;
import com.valinor.game.world.position.Tile;
import com.valinor.util.Utils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 28, 2022
 */
public class MoveHollowLogTask extends Task {

    Optional<GameObject> log = MapObjects.get(1335, new Tile(3181, 4631, 0));

    private static final List<Tile> TILES = Arrays.asList(new Tile(3181, 4631), new Tile(3189, 4629), new Tile(3189, 4635));

    public MoveHollowLogTask() {
        super("MoveHollowLogTask", 50);
    }

    @Override
    public void execute() {
        if(log.isPresent()) {
            ObjectManager.removeObj(log.get());
            Tile tile = Utils.randomElement(TILES);
            //ObjectManager.addObj(log.get().tile());
        }
    }
}
