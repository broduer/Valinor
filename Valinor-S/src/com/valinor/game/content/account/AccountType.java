package com.valinor.game.content.account;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

public enum AccountType {

    IRON_MAN(42402),
    ULTIMATE_IRON_MAN(42403),
    HARDCORE_IRON_MAN(42423),
    REGULAR_ACCOUNT(42405),
    INSTANT_PKER_ACCOUNT(42431),
    NONE(-1);

    private final int button;

    /**
     * We don't have to set a constructor because the Enum only consists of Types
     */
    AccountType(int button) {
        this.button = button;
    }

    /**
     * Gets the spriteId from the client.
     *
     * @return The spriteId
     */
    public int getSpriteId() {
        return 42402 + (ordinal());
    }

    /**
     * The buttonId
     *
     * @return The button we receive from the client.
     */
    public int getButtonId() {
        return button;
    }

    //A set of game types
    public static final Set<AccountType> TYPE = Collections.unmodifiableSet(EnumSet.allOf(AccountType.class));

}
