package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) is not getting out of the penalty box")
public record PlayerWasNotGettingOutOfThePenaltyBox(String player) implements Event {
    public PlayerWasNotGettingOutOfThePenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }
}
