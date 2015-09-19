package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) was sent to the penalty box")
public class PlayerWasSentToPenaltyBox implements Event {

    public final String player;

    public PlayerWasSentToPenaltyBox(String player) {
        this.player = player;
    }

    public PlayerWasSentToPenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerWasSentToPenaltyBox that = (PlayerWasSentToPenaltyBox) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public String toString() {
        return "PlayerWasSentToPenaltyBox(" + player + ')';
    }

}
