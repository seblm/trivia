package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "They are player number (?<count>\\d+)")
public class NewPlayerCount implements Event {
    private final int count;

    public NewPlayerCount(int count) {
        this.count = count;
    }

    public NewPlayerCount(Matcher matcher) {
        this(Integer.parseInt(matcher.group("count")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewPlayerCount that = (NewPlayerCount) o;
        return Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }

    @Override
    public String toString() {
        return "NewPlayerCount(" + count + ')';
    }
}
