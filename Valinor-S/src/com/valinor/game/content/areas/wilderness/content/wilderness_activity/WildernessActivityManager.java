package com.valinor.game.content.areas.wilderness.content.wilderness_activity;

import com.valinor.GameServer;
import com.valinor.game.GameEngine;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.EdgevilleActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.PureActivity;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.WildernessHotspot;
import com.valinor.game.content.areas.wilderness.content.wilderness_activity.impl.ZerkerActivity;
import com.valinor.game.task.Task;
import com.valinor.game.task.TaskManager;
import com.valinor.game.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since January 30, 2022
 */
public class WildernessActivityManager {

    /**
     * The logger for this class
     */
    private static final Logger logger = LogManager.getLogger(WildernessActivityManager.class);

    public static class WildernessActivityManagerTask extends Task {

        public WildernessActivityManagerTask() {
            super("WildernessActivityManagerTask",1);
        }

        @Override
        public void execute() {
            getSingleton().run();
        }
    }

    /**
     * When called, this method will register all activities into the
     * server
     */
    public void init() {
        logger.info("Loading Wilderness Activity Manager");

        activities.add(new EdgevilleActivity());
        activities.add(new PureActivity());
        activities.add(new ZerkerActivity());
        activities.add(new WildernessHotspot());
        nextActivityTime = !GameServer.properties().debugMode ? System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5) : System.currentTimeMillis() + (ACTIVITY_BETWEEN_DELAY / 2);
        TaskManager.submit(new WildernessActivityManagerTask());
    }

    public void run() {
        try {
            //We can't run any activity when the server isn't connected yet, we can't run an activity when
            // there is 0 players online and when the server is in its shutdown state.
            if (GameServer.startTime == -1 || World.getWorld().getPlayers().size() == 0 || GameEngine.shutdown) {
                return;
            }
            if (System.currentTimeMillis() >= nextActivityTime) {
                if (currentActivity != null) {
                    currentActivity.onFinish();
                    currentActivity = null;
                    nextActivityTime = System.currentTimeMillis() + ACTIVITY_BETWEEN_DELAY;
                    World.getWorld().sendWorldMessage(ACTIVITY_COMPLETE_MESSAGE);
                    return;
                }
                WildernessActivity randomActivity = getRandomActivity();

                randomActivity.onCreate();
                randomActivity.setActivityInitializeTime(System.currentTimeMillis());
                nextActivityTime = System.currentTimeMillis() + ACTIVITY_BETWEEN_DELAY;
                currentActivity = randomActivity;

                World.getWorld().sendWorldMessage("<img=452> "+randomActivity.announcement());
            } else if (currentActivity != null) {
                long activityTime = currentActivity.activityTime();
                currentActivity.process();
                if (activityTime == 0) {
                    return;
                }
                long timeOver = currentActivity.getActivityInitializeTime() + activityTime;
                if (System.currentTimeMillis() >= timeOver) {
                    currentActivity.onFinish();
                    currentActivity = null;
                    nextActivityTime = System.currentTimeMillis() + ACTIVITY_BETWEEN_DELAY;
                    World.getWorld().sendWorldMessage(ACTIVITY_COMPLETE_MESSAGE);
                }

            }
        } catch (Throwable t) {
            logger.catching(t);
        }
    }

    /**
     * If the activity is the current activity
     *
     * @param activity_class
     *            The activity to check for
     */
    public boolean isActivityCurrent(Class<? extends WildernessActivity> activity_class) {
        WildernessActivity activity = getActivity(activity_class);

        if (activity == null) {
            return false;
        }
        if (currentActivity == null) {
            return false;
        }
        return currentActivity.getClass().getSimpleName().equals(activity.getClass().getSimpleName());
    }

    /**
     * Gets a wilderness activity by the class
     *
     * @param clazz
     *            The class
     */
    @SuppressWarnings("unchecked")
    public <T extends WildernessActivity> T getActivity(Class<? extends WildernessActivity> clazz) {
        for (WildernessActivity activity : activities) {
            if (activity.getClass().getSimpleName().equals(clazz.getSimpleName())) {
                return (T) activity;
            }
        }
        return null;
    }

    /**
     * Gets a random activity from the {@link #activities} list
     *
     * @return A {@code Activity} {@code Object}
     */
    private WildernessActivity getRandomActivity() {
        if (activitiesPerformed.size() == activities.size()) {
            activitiesPerformed.clear();
        }
        List<WildernessActivity> activities = new ArrayList<>(this.activities);
        Collections.shuffle(activities);
        WildernessActivity random = activities.get(0);
        while (activitiesPerformed.contains(random)) {
            Collections.shuffle(activities);
            random = activities.get(0);
        }
        activitiesPerformed.add(random);
        return random;
    }

    /**
     * If we have an activity currently running, a description of the activity
     * is necessary. This method finds that description
     */
    public String getActivityDescription() {
        if (currentActivity == null) {
            return "None";
        }
        try {
            return currentActivity.description();
        } catch (Throwable t) {
            logger.catching(t);
            return null;
        }
    }

    public String getActivityLocation() {
        if (currentActivity == null) {
            return "None";
        }
        try {
            return currentActivity.location();
        } catch (Throwable t) {
            logger.catching(t);
            return null;
        }
    }

    /**
     * Getting the instance of this class
     */
    public static WildernessActivityManager getSingleton() {
        return SINGLETON;
    }

    /**
     * The current activity that is happening
     */
    private WildernessActivity currentActivity;

    /**
     * The next time an activity will be executed
     */
    private long nextActivityTime;

    /**
     * The delay that happens between activities
     */
    private static final long ACTIVITY_BETWEEN_DELAY = GameServer.properties().production ? TimeUnit.MINUTES.toMillis(60) : TimeUnit.SECONDS.toMillis(300);

    /**
     * The message players will see when the activity is over
     */
    private static final String ACTIVITY_COMPLETE_MESSAGE = "<img=452> The current wilderness activity has ended! Please wait for the next one.";

    /**
     * The list of wilderness activities in the server
     */
    private final List<WildernessActivity> activities = new ArrayList<>();

    /**
     * The list of wilderness activities performed
     */
    private final List<WildernessActivity> activitiesPerformed = new ArrayList<>();

    /**
     * The instance of this class
     */
    private static final WildernessActivityManager SINGLETON = new WildernessActivityManager();

}
