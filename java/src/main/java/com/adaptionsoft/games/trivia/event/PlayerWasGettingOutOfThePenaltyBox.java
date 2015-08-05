package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) is getting out of the penalty box")
public record PlayerWasGettingOutOfThePenaltyBox(String player) implements Event {
    public PlayerWasGettingOutOfThePenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }
}
