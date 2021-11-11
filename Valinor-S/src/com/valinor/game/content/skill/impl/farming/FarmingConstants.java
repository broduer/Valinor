package com.valinor.game.content.skill.impl.farming;

import com.valinor.game.content.skill.impl.farming.impl.DiseaseState;
import com.valinor.game.content.skill.impl.farming.impl.PatchState;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.entity.mob.player.Skills;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.position.Tile;
import com.valinor.util.ItemIdentifiers;

import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import static com.valinor.util.Utils.random;

public class FarmingConstants {

    public static final String ITEM_ON_OBJECT_ACTION = "ITEM_ON_OBJECT";

    public static final String FIRST_CLICK_OBJECT = "FIRST_CLICK_OBJECT";

    public static final String SECOND_CLICK_OBJECT = "SECOND_CLICK_OBJECT";

    /**
     * The speed the crops will crow ( from 0 to 1 ).
     */
    public static double STAGE_GROWTH_MULTIPLIER = 1.0;

    /**
     * The growth interval check in minutes.
     */
    public static final int GROWTH_INVTERVAL_CHECK = 5;

    /**
     * The configuration ID to be sent to client.
     */
    public static final int COMPOST_BIN_CONFIG_ID = 1057;

    public static final int WATERING_CAN_ANIM = 2293;
    public static final int RAKING_ANIM = 2273;
    public static final int SPADE_ANIM = 830;
    public static final int SEED_DIBBING = 2291;
    public static final int PUTTING_COMPOST = 2283;
    public static final int CURING_ANIM = 2288;
    public static final int RAKE = 5341;
    public static final int SEED_DIBBER = 5343;
    public static final int SPADE = 952;
    public static final int TROWEL = 5325;
    public static final int SECATEURS = 5329;
    public static final int MAGIC_SECATEURS = 7409;
    public static final Item ITEM_PLANT_CURE = new Item(6036, 1);

    private static final Set<Integer> WATERING_CANS = Set.of(
        ItemIdentifiers.WATERING_CAN1,
        ItemIdentifiers.WATERING_CAN2,
        ItemIdentifiers.WATERING_CAN3,
        ItemIdentifiers.WATERING_CAN4,
        ItemIdentifiers.WATERING_CAN5,
        ItemIdentifiers.WATERING_CAN6,
        ItemIdentifiers.WATERING_CAN7,
        ItemIdentifiers.WATERING_CAN8,
        ItemIdentifiers.GRICOLLERS_CAN
    );

    /**
     * Set of tools that can be stored in the leprechaun.
     */
    public static final Set<Integer> TOOLS = Set.of(
        ItemIdentifiers.RAKE,
        ItemIdentifiers.SEED_DIBBER,
        ItemIdentifiers.SPADE,
        ItemIdentifiers.SECATEURS,
        ItemIdentifiers.GARDENING_TROWEL,
        ItemIdentifiers.PLANT_CURE,
        ItemIdentifiers.EMPTY_BUCKET,
        ItemIdentifiers.COMPOST,
        ItemIdentifiers.SUPERCOMPOST,
        ItemIdentifiers.ULTRACOMPOST,

        ItemIdentifiers.WATERING_CAN1,
        ItemIdentifiers.WATERING_CAN2,
        ItemIdentifiers.WATERING_CAN3,
        ItemIdentifiers.WATERING_CAN4,
        ItemIdentifiers.WATERING_CAN5,
        ItemIdentifiers.WATERING_CAN6,
        ItemIdentifiers.WATERING_CAN7,
        ItemIdentifiers.WATERING_CAN8,
        ItemIdentifiers.GRICOLLERS_CAN
    );

    /**
     * Returns if the coordinates are in range of the area.
     *
     * @param base
     * @param top
     * @param x
     * @param y
     * @return if is in range
     */
    public static boolean inRangeArea(Tile base, Tile top, int x, int y) {
        return x >= base.getX() && y >= base.getY() && x <= top.getX() && y <= top.getY();
    }

    /**
     * Returns if the item is a watering can.
     *
     * @param id
     * @return if is a watering can
     */
    public static boolean isWateringCan(int id) {
        return WATERING_CANS.contains(id);
    }

    /**
     * Returns if the plant is in growth interval.
     *
     * @param state
     * @return if is in interval
     */
    public static boolean inGrowthInterval(PatchState state) {
        ZoneOffset offset = ZonedDateTime.now(ZoneId.systemDefault()).getOffset();
        LocalDateTime start = LocalDateTime.ofEpochSecond(state.getLastStageChangeMoment() / 1000, 0, offset);
        LocalDateTime now = LocalDateTime.now();
        while (start.toEpochSecond(offset) <= now.toEpochSecond(offset)) {
            if (inInterval(start.toLocalTime(), state.getSeed().getStageGrowthTime())) {
                return true;
            }
            start = start.plus(GROWTH_INVTERVAL_CHECK, ChronoUnit.MINUTES);
        }
        return false;
    }

    /**
     * Returns if the time is between the growth interval of time. (growth interval is the first 5 minutes of each
     * stage interval)
     *
     * @param time
     * @param interval_size_in_mins
     * @return if is in interval
     */
    public static boolean inInterval(LocalTime time, int interval_size_in_mins) {

        LocalTime start = LocalTime.from(LocalTime.of(0, 0, 0));

        while (start.toSecondOfDay() <= LocalTime.now().toSecondOfDay()) {
            if (time.toSecondOfDay() >= start.toSecondOfDay() && time.toSecondOfDay() <= (start.toSecondOfDay() + 300)) {
                return true;
            }
            start = start.plus(interval_size_in_mins, ChronoUnit.MINUTES);
        }
        return false;
    }

    /**
     * Returns if the plant is fully grown.
     *
     * @param state
     * @return if is grown
     */
    public static boolean isFullyGrown(PatchState state) {
        return state.getStage() >= state.getSeed().getMaxGrowth();
    }

    /**
     * Returns if the patch should have disease on it.
     *
     * @param state
     * @return if it will be diseased
     */
    public static boolean hasToApplyDisease(PatchState state) {
        if (state.getDiseaseState() == DiseaseState.IMMUNE || state.isWatered()) {
            return false;
        }
        return random(3 + (state.getTreatment().getLivesIncrease() * 3)) <= 1;
    }

    /**
     * Returns if the plant should lose a life.
     *
     * @param state
     * @return if it should lose a life
     */
    public static boolean hasToLoseLife(PatchState state, Player player) {
        return random(255) >= successChance(player.skills().level(Skills.FARMING), 99, state.getSeed().getLevelReq());
    }

    /**
     * Chance of success.
     *
     * @param min
     * @param max
     * @param level
     * @return
     */
    private static int successChance(int min, int max, int level) {
        return ((level - 1) * (max - min) / (99 - 1)) + min;
    }

}
