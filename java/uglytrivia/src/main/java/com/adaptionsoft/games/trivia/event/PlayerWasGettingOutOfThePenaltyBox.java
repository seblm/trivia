package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) is getting out of the penalty box")
public class PlayerWasGettingOutOfThePenaltyBox implements Event {

    public final String player;

    public PlayerWasGettingOutOfThePenaltyBox(String player) {
        this.player = player;
    }

    public PlayerWasGettingOutOfThePenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerWasGettingOutOfThePenaltyBox that = (PlayerWasGettingOutOfThePenaltyBox) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public String toString() {
        return "PlayerWasGettingOutOfThePenaltyBox(" + player + ')';
    }

}
