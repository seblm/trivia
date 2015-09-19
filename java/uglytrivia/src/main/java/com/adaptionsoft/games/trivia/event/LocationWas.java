package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+)'s new location is (?<newLocation>-?\\d+)")
public class LocationWas implements Event {
    public final String player;
    public final int newLocation;

    public LocationWas(String player, int newLocation) {
        this.player = player;
        this.newLocation = newLocation;
    }

    public LocationWas(Matcher matcher) {
        this(matcher.group("player"), Integer.parseInt(matcher.group("newLocation")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationWas that = (LocationWas) o;
        return Objects.equals(newLocation, that.newLocation) &&
                Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, newLocation);
    }

    @Override
    public String toString() {
        return "LocationWas(" + player + ", " + newLocation + ')';
    }
}
