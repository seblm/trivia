package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) was added")
public class PlayerWasAdded implements Event {
    public final String name;

    public PlayerWasAdded(String name) {
        this.name = name;
    }

    public PlayerWasAdded(Matcher matcher) {
        this(matcher.group("player"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerWasAdded that = (PlayerWasAdded) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PlayerWasAdded(" + name + ')';
    }
}
