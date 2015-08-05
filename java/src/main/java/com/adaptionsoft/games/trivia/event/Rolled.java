package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "They have rolled a (?<roll>-?\\d+)")
public record Rolled(int roll) implements Event {
    public Rolled(Matcher matcher) {
        this(Integer.parseInt(matcher.group("roll")));
    }
}
