package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) was added")
public record PlayerWasAdded(String name) implements Event {
    public PlayerWasAdded(Matcher matcher) {
        this(matcher.group("player"));
    }
}
