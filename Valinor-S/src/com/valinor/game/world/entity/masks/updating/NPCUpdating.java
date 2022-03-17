package com.valinor.game.world.entity.masks.updating;

import com.valinor.game.world.World;
import com.valinor.game.world.entity.Entity;
import com.valinor.game.world.entity.combat.hit.Splat;
import com.valinor.game.world.entity.mob.Flag;
import com.valinor.game.world.entity.mob.UpdateFlag;
import com.valinor.game.world.entity.mob.npc.Npc;
import com.valinor.game.world.entity.mob.player.Player;
import com.valinor.game.world.position.Tile;
import com.valinor.net.packet.ByteOrder;
import com.valinor.net.packet.PacketBuilder;
import com.valinor.net.packet.PacketBuilder.AccessType;
import com.valinor.net.packet.PacketType;
import com.valinor.net.packet.ValueType;

import java.util.Iterator;
import java.util.List;

/**
 * Represents a player's npc updating task, which loops through all local
 * npcs and updates their masks according to their current attributes.
 * 
 * @author Relex lawl
 */
public class NPCUpdating {

    /**
     * The maximum amount of local npcs.
     */
    private static final int MAXIMUM_LOCAL_NPCS = 255;

    /**
     * The maximum number of npcs to load per cycle. This prevents the update packet from becoming too large (the
     * client uses a 5000 byte buffer) and also stops old spec PCs from crashing when they login or teleport.
     */
    private static final int NEW_NPCS_PER_CYCLE = 20;

    /**
     * Handles the actual npc updating for the associated player.
     * @return    The NPCUpdating instance.
     */
    public static void update(Player player) {
        PacketBuilder update = new PacketBuilder();
        PacketBuilder packet = new PacketBuilder(65, PacketType.VARIABLE_SHORT);
        packet.initializeAccess(AccessType.BIT);
        packet.putBits(8, player.getLocalNpcs().size());
        for (Iterator<Npc> npcIterator = player.getLocalNpcs().iterator(); npcIterator.hasNext();) {
            Npc npc = npcIterator.next();

            if (npc.getIndex() != -1 && World.getWorld().getNpcs().get(npc.getIndex()) != null && !npc.hidden() && player.tile().isWithinDistance(npc.tile()) && !npc.isNeedsPlacement()) {
                updateMovement(npc, packet);
                npc.inViewport(true); // Mark as in viewport
                if (npc.getUpdateFlag().isUpdateRequired()) {
                    appendUpdates(player, npc, update);
                }
            } else {
                npcIterator.remove();
                packet.putBits(1, 1);
                packet.putBits(2, 3);
            }
        }

        List<Npc> locals = player.getLocalNpcs();
        int added = 0, count = locals.size();
        for (Npc npc : World.getWorld().getNpcs()) {
            if (count >= MAXIMUM_LOCAL_NPCS) {
                break;
            } else if (added >= NEW_NPCS_PER_CYCLE) {
                break;
            }

            if (npc == null || locals.contains(npc) || npc.hidden() || npc.isNeedsPlacement())
                continue;

            Tile tile = npc.tile();
            if (tile.isWithinDistance(player.tile())) {
                locals.add(npc);
                count++;
                added++;

                addNPC(player, npc, packet);
                npc.inViewport(true); // Mark as in viewport
                //System.out.println(npc.getName()+" in viewport: "+npc.inViewport());
                if (npc.getUpdateFlag().isUpdateRequired()) {
                    appendUpdates(player, npc, update);
                }
            }
        }

        if (update.buffer().writerIndex() > 0) {
            packet.putBits(14, 16383);
            packet.initializeAccess(AccessType.BYTE);
            packet.writeBuffer(update.buffer());
        } else {
            packet.initializeAccess(AccessType.BYTE);
        }

        player.getSession().write(packet);
    }

    /**
     * Adds an npc to the associated player's client.
     * @param npc        The npc to add.
     * @param builder    The packet builder to write information on.
     * @return            The NPCUpdating instance.
     */
    private static void addNPC(Player player, Npc npc, PacketBuilder builder) {
        builder.putBits(14, npc.getIndex());
        builder.putBits(5, npc.tile().getY()-player.tile().getY());
        builder.putBits(5, npc.tile().getX()-player.tile().getX());
        builder.putBits(1, 0);
        builder.putBits(14, npc.id());
        builder.putBits(1, npc.getUpdateFlag().isUpdateRequired() ? 1 : 0);

        //Facing update. We don't want to update facing for npcs that walk.
        boolean updateFacing = npc.walkRadius() == 0;
        builder.putBits(1, updateFacing ? 1 : 0);
        if (updateFacing) {
            Tile tile = face(npc);
            builder.putBits(14, tile.getX() * 2 + 1); //face x
            builder.putBits(14, tile.getY() * 2 + 1); //face y
        }
    }

    private static Tile face(Npc npc) {
        return npc.faceBasedOnDir();
    }

    /**
     * Updates the npc's movement queue.
     * @param npc        The npc who's movement is updated.
     * @param out    The packet builder to write information on.
     * @return            The NPCUpdating instance.
     */
    private static void updateMovement(Npc npc, PacketBuilder out) {
        if (npc.getRunningDirection().toInteger() == -1) {
            if (npc.getWalkingDirection().toInteger() == -1) {
                if (npc.getUpdateFlag().isUpdateRequired()) {
                    out.putBits(1, 1);
                    out.putBits(2, 0);
                } else {
                    out.putBits(1, 0);
                }
            } else {
                out.putBits(1, 1);
                out.putBits(2, 1);
                out.putBits(3, npc.getWalkingDirection().toInteger());
                out.putBits(1, npc.getUpdateFlag().isUpdateRequired() ? 1 : 0);
            }
        } else {
            out.putBits(1, 1);
            out.putBits(2, 2);
            out.putBits(3, npc.getWalkingDirection().toInteger());
            out.putBits(3, npc.getRunningDirection().toInteger());
            out.putBits(1, npc.getUpdateFlag().isUpdateRequired() ? 1 : 0);
        }
    }

    /**
     * Appends a mask update for {@code npc}.
     * @param npc        The npc to update masks for.
     * @param block    The packet builder to write information on.
     * @return            The NPCUpdating instance.
     */
    private static void appendUpdates(Player player, Npc npc, PacketBuilder block) {
        int mask = 0;
        UpdateFlag flag = npc.getUpdateFlag();
        if (flag.flagged(Flag.ANIMATION) && npc.getAnimation() != null) {
            mask |= 0x10;
        }
        if (flag.flagged(Flag.GRAPHIC) && npc.graphic() != null) {
            mask |= 0x80;
        }
        if (flag.flagged(Flag.FIRST_SPLAT)) {
            mask |= 0x8;
        }
        if (flag.flagged(Flag.ENTITY_INTERACTION)) {
            mask |= 0x20;
        }
        if (flag.flagged(Flag.FORCED_CHAT) && npc.getForcedChat() != null) {
            mask |= 0x1;
        }
        /*if (flag.flagged(Flag.SECOND_SPLAT)) {
            mask |= 0x40;
        }*/
        if (flag.flagged(Flag.FORCED_MOVEMENT) && npc.getForceMovement() != null) {
            mask |= 0x40;
        }
        if (flag.flagged(Flag.TRANSFORM)) {
            mask |= 0x2;
        }
        if (flag.flagged(Flag.FACE_TILE) && npc.getFaceTile() != null) {
            mask |= 0x4;
        }
        block.put(mask);
        if (flag.flagged(Flag.ANIMATION) && npc.getAnimation() != null) {
            updateAnimation(block, npc);
        }
        if (flag.flagged(Flag.GRAPHIC) && npc.graphic() != null) {
            updateGraphics(block, npc);
        }
        if (flag.flagged(Flag.FIRST_SPLAT)) {
            updateSingleHit(block, npc);
        }
        if (flag.flagged(Flag.ENTITY_INTERACTION)) {
            Entity entity = npc.getInteractingEntity();
            block.putShort(entity == null ? -1 : entity.getIndex() + (entity instanceof Player ? 32768 : 0));
        }
        if (flag.flagged(Flag.FORCED_CHAT) && npc.getForcedChat() != null) {
            block.putString(npc.getForcedChat());
        }
        /*if (flag.flagged(Flag.SECOND_SPLAT)) {
            updateDoubleHit(block, npc);
        }*/
        if (flag.flagged(Flag.FORCED_MOVEMENT) && npc.getForceMovement() != null) {
            updateForcedMovement(player, npc, block);
        }
        if (flag.flagged(Flag.TRANSFORM)) {
            block.putShort(npc.transmog() <= 0 ? npc.id() : npc.transmog(), ValueType.A, ByteOrder.LITTLE);
        }
        if (flag.flagged(Flag.FACE_TILE) && npc.getFaceTile() != null) {
            final Tile tile = npc.getFaceTile();
            block.putShort(tile.getX() * 2 + 1, ByteOrder.LITTLE);
            block.putShort(tile.getY() * 2 + 1, ByteOrder.LITTLE);
        }
    }

    /**
     * This update block is used to update forced npc movement.
     * @param builder    The packet builder to write information on.
     * @return            The NpcUpdating instance.
     */
    private static void updateForcedMovement(Player player, Npc npc, PacketBuilder builder) {
        int startX = npc.getForceMovement().getStart().getLocalX(player.getLastKnownRegion());
        int startY = npc.getForceMovement().getStart().getLocalY(player.getLastKnownRegion());
        int endX = npc.getForceMovement().getEnd().getX();
        int endY = npc.getForceMovement().getEnd().getY();

        builder.put(startX, ValueType.S);
        builder.put(startY, ValueType.S);
        builder.put(startX + endX, ValueType.S);
        builder.put(startY + endY, ValueType.S);
        builder.putShort(npc.getForceMovement().getSpeed(), ValueType.A, ByteOrder.LITTLE);
        builder.putShort(npc.getForceMovement().getReverseSpeed(), ValueType.A, ByteOrder.BIG);
        builder.put(npc.getForceMovement().getDirection(), ValueType.S);
    }

    /**
     * Updates {@code npc}'s current animation and displays it for all local players.
     * @param builder    The packet builder to write information on.
     * @param npc        The npc to update animation for.
     * @return            The NPCUpdating instance.
     */
    private static void updateAnimation(PacketBuilder builder, Npc npc) {
        builder.putShort(npc.getAnimation().getId(), ByteOrder.LITTLE);
        builder.put(npc.getAnimation().getDelay());
    }

    /**
     * Updates {@code npc}'s current graphics and displays it for all local players.
     *
     * @param builder The packet builder to write information on.
     * @param npc     The npc to update graphics for.
     * @return The NPCUpdating instance.
     */
    private static void updateGraphics(PacketBuilder builder, Npc npc) {
        builder.putShort(npc.graphic().id());
        builder.putInt(npc.graphic().delay() + (65536 * npc.graphic().height()));
    }

    /**
     * Updates the npc's single hit.
     * @param builder    The packet builder to write information on.
     * @param npc        The npc to update the single hit for.
     * @return            The NPCUpdating instance.
     */
    private static void updateSingleHit(PacketBuilder builder, Npc npc) {
        builder.put(Math.min(npc.splats.size(), 4)); // count
        for (int i = 0; i < Math.min(npc.splats.size(), 4); i++) {
            Splat splat = npc.splats.get(i);
            builder.putShort(splat.getDamage());
            builder.put(splat.getType().getId());
            builder.putShort(npc.hp());
            builder.putShort(npc.combatInfo() == null ? 1 : npc.combatInfo().stats == null ? 1 : npc.combatInfo().stats.hitpoints);
            //System.out.println("Hitsplat id send out to the client: "+splat.getType().getId()+" VS type: "+splat.getType().name());
        }
    }

    /**
     * Updates the npc's double hit.
     * @param builder    The packet builder to write information on.
     * @param npc        The npc to update the double hit for.
     * @return            The NPCUpdating instance.
     */
    /*private static void updateDoubleHit(PacketBuilder builder, Npc npc) {
        builder.putShort(npc.getSecondaryHit().getDamage());
        builder.put(npc.getSecondaryHit().getType().getId());
        builder.putShort(npc.hp());
        builder.putShort(npc.combatInfo() == null ? 1 : npc.combatInfo().stats == null ? 1 : npc.combatInfo().stats.hitpoints);
    }*/
}
