package com.adaptionsoft.games.trivia.event;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "They have rolled a (?<roll>-?\\d+)")
public class Rolled implements Event {

    public final int roll;

    public Rolled(int roll) {
        this.roll = roll;
    }

    public Rolled(Matcher matcher) {
        this(Integer.parseInt(matcher.group("roll")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rolled rolled = (Rolled) o;
        return Objects.equals(roll, rolled.roll);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roll);
    }

    @Override
    public String toString() {
        return "Rolled(" + roll + ')';
    }

}
