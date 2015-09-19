package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) now has (?<newGoldCoinsCount>\\d+) Gold Coins.")
public class NewGoldCoinsCount implements Event {
    public final String player;
    public final int newGoldCoinsCount;

    public NewGoldCoinsCount(String player, int newGoldCoinsCount) {
        this.player = player;
        this.newGoldCoinsCount = newGoldCoinsCount;
    }

    public NewGoldCoinsCount(Matcher matcher) {
        this(matcher.group("player"), Integer.parseInt(matcher.group("newGoldCoinsCount")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewGoldCoinsCount that = (NewGoldCoinsCount) o;
        return Objects.equals(newGoldCoinsCount, that.newGoldCoinsCount) &&
                Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player, newGoldCoinsCount);
    }

    @Override
    public String toString() {
        return "NewGoldCoinsCount(" + player + ", " + newGoldCoinsCount + ')';
    }
}
