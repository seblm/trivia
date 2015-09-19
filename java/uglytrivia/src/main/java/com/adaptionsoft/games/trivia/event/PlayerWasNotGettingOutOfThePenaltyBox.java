package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) is not getting out of the penalty box")
public class PlayerWasNotGettingOutOfThePenaltyBox implements Event {

    private final String player;

    public PlayerWasNotGettingOutOfThePenaltyBox(String player) {
        this.player = player;
    }

    public PlayerWasNotGettingOutOfThePenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }

    public String getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerWasNotGettingOutOfThePenaltyBox that = (PlayerWasNotGettingOutOfThePenaltyBox) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public String toString() {
        return "PlayerIsNotGettingOutOfThePenaltyBox(" + player + ')';
    }

}
