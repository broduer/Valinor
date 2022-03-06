package com.valinor.util;

import com.valinor.io.Buffer;

public final class ChatMessageCodec {

   private static final char[] VALID_CHARACTERS = { ' ', 'e', 't', 'a', 'o', 'i', 'h', 'n', 's', 'r', 'd', 'l', 'u', 'm', 'w', 'c', 'y', 'f', 'g', 'p', 'b', 'v', 'k', 'x', 'j', 'q', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ' ', '!', '?', '.', ',', ':', ';', '(', ')', '-', '&', '*', '\\', '\'', '@', '#', '+', '=', '£', '$', '%', '"', '[', ']', '>', '<', '^', '/', '_' };

    private static final char[] message = new char[100];
    private static final Buffer stream = new Buffer(new byte[100]);

    public static String decode(int length, Buffer buffer) {
        int index = 0;
        int next = -1;
        for (int count = 0; count < length; ++count) {
            int in = buffer.readUnsignedByte();
            message[index++] = VALID_CHARACTERS[in];
        }
        boolean capitaliseNext = true;
        for (int count = 0; count < index; ++count) {
            char character = message[count];
            if (capitaliseNext && character >= 'a' && character <= 'z') {
                message[count] += '\uffe0';
                capitaliseNext = false;
            }
            if (character == '.' || character == '!' || character == '?') {
                capitaliseNext = true;
            }
        }
        return new String(message, 0, index);
    }

    public static void encode(String string, Buffer buffer) {
        if (string.length() > 80) {
            string = string.substring(0, 80);
        }
        string = string.toLowerCase();
        int next = -1;
        for (int count = 0; count < string.length(); ++count) {
            char character = string.charAt(count);
            int charIndex = 0;
            for (int i = 0; i < VALID_CHARACTERS.length; ++i) {
                if (character == VALID_CHARACTERS[i]) {
                    charIndex = i;
                    break;
                }
            }
            buffer.writeWordBigEndian(charIndex);
        }
    }

    public static String processText(String string) {
        stream.resetPosition();
        encode(string, stream);
        int length = stream.pos;
        stream.resetPosition();
        return decode(length, stream);
    }
}
