package com.valinor.game.world.entity.masks.chat;

/**
 * Represents a player's chat message.
 * 
 * @author relex lawl
 */

public class ChatMessage {
    /**
     * The color of the message.
     */
    private int colour;
    /**
     * The effects of the message.
     */
    private int effects;
    private byte[] bytes;
    /**
     * The actual text of the message.
     */
    private String text;

    public byte[] getBytes() {
        return bytes;
    }

    /**
     * The Message constructor.
     * @param colour  The color the message will have, done through color(#):
     * @param effects The effect the message will have, done through effect(#):
     * @param bytes
     * @param text    The actual message it will have.
     */
    public ChatMessage(int colour, int effects, byte[] bytes, String text) {
        this.colour = colour;
        this.effects = effects;
        this.bytes = bytes;
        this.text = text;
    }

    /**
     * Gets the message's chat color.
     *
     * @return colour.
     */
    public int getColour() {
        return colour;
    }

    /**
     * Gets the message's chat effect.
     *
     * @return effects.
     */
    public int getEffects() {
        return effects;
    }

    /**
     * Gets the message's actual text in byte form.
     *
     * @return text.
     */
    public String getText() {
        return text;
    }
}
