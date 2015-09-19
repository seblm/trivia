package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) is the current player")
public class CurrentPlayerWas implements Event {
    public final String player;

    public CurrentPlayerWas(String player) {
        this.player = player;
    }

    public CurrentPlayerWas(Matcher matcher) {
        this(matcher.group("player"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrentPlayerWas that = (CurrentPlayerWas) o;
        return Objects.equals(player, that.player);
    }

    @Override
    public int hashCode() {
        return Objects.hash(player);
    }

    @Override
    public String toString() {
        return "CurrentPlayerWas(" + player + ')';
    }
}
