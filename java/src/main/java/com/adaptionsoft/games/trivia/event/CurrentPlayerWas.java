package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) is the current player")
public record CurrentPlayerWas(String player) implements Event {
    public CurrentPlayerWas(Matcher matcher) {
        this(matcher.group("player"));
    }
}
