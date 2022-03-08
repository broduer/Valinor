package com.valinor.game.content.areas.wilderness.content.hitman_services;

/**
 * The person being targeted, this person has a bounty on is head.
 * @author Patrick van Elderen <https://github.com/PVE95>
 * @Since March 08, 2022
 */
public class PersonOfInterest {

    public final String username;
    public final int bounty;

    public PersonOfInterest(String username, int bounty) {
        this.username = username;
        this.bounty = bounty;
    }

    @Override
    public String toString() {
        return "PersonOfInterest{" +
            "username=" + username +
            ", bounty=" + bounty +
            '}';
    }
}
