package com.valinor.net.packet.interaction;

import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.items.Item;
import com.valinor.game.world.object.GameObject;
import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles the interaction
 *
 * @author 2012
 */
public final class InteractionManager {

    private static final Logger log = LoggerFactory.getLogger(InteractionManager.class);

    /**
     * The packet interactions
     */
    private static final List<Interaction> interactions = new ArrayList<>();

    /**
     * Loads all the interaction
     */
    public static void init() {
        try {
            loadRecursive();
        } catch (IOException ex) {
            log.error("Exception whilst loading packet interactions.", ex);
        }
        log.info("Loaded {} packet interactions", interactions.size());
    }

    private static void loadRecursive() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        Set<Class<?>> clazzes = classPath.getTopLevelClassesRecursive("com.valinor.game").stream().map(ClassPath.ClassInfo::load).collect(Collectors.toSet());
        clazzes.forEach(InteractionManager::load);
    }

    public static void onLogin(Player player) {
        for (Interaction interaction : interactions) {
            interaction.onLogin(player);
            //System.out.println("onLogin prints "+interaction.getClass().getSimpleName());
        }
    }

    public static boolean onLogout(Player player) {
        for (Interaction interaction : interactions) {
            if (interaction.onLogout(player)) {
                //System.out.println("onLogout prints "+interaction.getClass().getSimpleName());
            }
        }
        return false;
    }

    public static boolean onEquipItem(Player player, Item item) {
        for (Interaction interaction : interactions) {
            if (interaction.handleEquipment(player, item)) {
                return true;
            }
        }
        return false;
    }

    public static boolean onEquipmentAction(Player player, Item item, int slot) {
        for (Interaction interaction : interactions) {
            if (interaction.handleEquipmentAction(player, item, slot)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks button interaction
     *
     * @param player the player
     * @param button the buttons
     * @return interaction
     */
    public static boolean checkButtonInteraction(Player player, int button) {
        for (Interaction interaction : interactions) {
            if (interaction.handleButtonInteraction(player, button)) {
                //System.out.println("checkButtonInteraction prints "+interaction.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }

    /**
     * Checks item interaction
     *
     * @param player player
     * @param item   the item
     * @param option the type
     * @return interaction
     */
    public static boolean checkItemInteraction(Player player, Item item, int option) {
        for (Interaction interaction : interactions) {
            if (interaction.handleItemInteraction(player, item, option)) {
                //System.out.println("checkItemInteraction prints "+interaction.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }

    /**
     * Checks object interaction
     *
     * @param player the player
     * @param object the object
     * @param type   the type
     * @return interaction
     */
    public static boolean checkObjectInteraction(Player player, GameObject object, int type) {
        for (Interaction interaction : interactions) {
            if (interaction.handleObjectInteraction(player, object, type)) {
                //System.out.println("checkObjectInteraction prints "+interaction.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }

    /**
     * Checks npc interaction
     *
     * @param player the player
     * @param npc    the npc
     * @param type   the type
     * @return interaction
     */
    public static boolean checkNpcInteraction(Player player, Npc npc, int type) {
        for (Interaction interaction : interactions) {
            if (interaction.handleNpcInteraction(player, npc, type)) {
                //System.out.println("checkNpcInteraction prints "+interaction.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }

    /**
     * Checks item on item interaction
     *
     * @param player   the player
     * @param use      the use
     * @param usedWith the used with
     * @return interaction
     */
    public static boolean checkItemOnItemInteraction(Player player, Item use, Item usedWith) {
        for (Interaction interaction : interactions) {
            if (interaction.handleItemOnItemInteraction(player, use, usedWith)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks item on player interaction
     *
     * @param player   the player
     * @param use      the use
     * @param usedWith the used with
     * @return interaction
     */
    public static boolean checkItemOnPlayerInteraction(Player player, Item use, Player usedWith) {
        for (Interaction interaction : interactions) {
            if (interaction.handleItemOnPlayer(player, use, usedWith)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks item on object interaction
     *
     * @param player     the player
     * @param item       the item
     * @param gameObject the game object
     * @return interaction
     */
    public static boolean checkItemOnObjectInteraction(Player player, Item item, GameObject gameObject) {
        for (Interaction interaction : interactions) {
            if (interaction.handleItemOnObject(player, item, gameObject)) {
                //System.out.println("checkItemOnObjectInteraction prints "+interaction.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }

    public static boolean checkItemOnNpcInteraction(Player player, Item item, Npc npc) {
        for (Interaction interaction : interactions) {
            if (interaction.handleItemOnNpc(player, item, npc)) {
                //System.out.println("checkItemOnNpcInteraction prints "+interaction.getClass().getSimpleName());
                return true;
            }
        }
        return false;
    }

    /**
     * Checks item container interaction
     *
     * @param player      the player
     * @param item        the item
     * @param slot        the slot
     * @param interfaceId the interface id
     * @param type        the type
     * @return interaction
     */
    public static boolean checkItemContainerActionInteraction(Player player, Item item, int slot, int interfaceId, int type) {
        for (Interaction interaction : interactions) {
            if (interaction.handleItemContainerActionInteraction(player, item, slot, interfaceId, type)) {
                return true;
            }
        }
        return false;
    }

    private static void load(Class<?> clazz) {
        if (Modifier.isAbstract(clazz.getModifiers())
            || clazz.isAnonymousClass()
            || clazz.isEnum()
            || clazz.isInterface()) {
            ////System.out.println("Unable to load this class: "+clazz.getName()+" abstract class: "+Modifier.isAbstract(clazz.getModifiers())+" anonymousClass: "+clazz.isAnonymousClass()+" enumClass: "+clazz.isEnum()+" interfaceClass: "+clazz.isInterface());
            return;
        }

        ////System.out.println("Found: "+clazz.getName() + "default cons: "+hasDefaultConstructor(clazz)+" superclass: "+isSuperClass(clazz, PacketInteraction.class));

        // A valid interaction must extend PacketInteraction and have a default zero-parameters
        // constructor.
        if (hasDefaultConstructor(clazz) && isSuperClass(clazz, Interaction.class)) {
            ////System.out.println("Enter class: "+clazz.getName());
            try {
                // Try to create an instance of that type
                Interaction interaction = (Interaction) clazz.getDeclaredConstructor().newInstance();

                if (!interactions.contains(interaction)) {
                    interactions.add(interaction);
                    ////System.out.println("Add class: "+clazz.getName());
                }
            } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
                ////System.out.println("Unable to load class: "+ clazz.getName());
            }
        }
    }

    private static boolean hasDefaultConstructor(Class<?> clazz) {
        for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
            if (Modifier.isPublic(constructor.getModifiers()) && constructor.getParameterCount() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Recursive method to determine if a class has a given super class.
     */
    private static boolean isSuperClass(Class<?> clazz, Class<?> superClass) {
        if (clazz == null) return false;
        if (clazz.getSuperclass() == superClass) return true;
        return isSuperClass(clazz.getSuperclass(), superClass);
    }

}
