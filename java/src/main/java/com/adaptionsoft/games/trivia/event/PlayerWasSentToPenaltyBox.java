package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) was sent to the penalty box")
public record PlayerWasSentToPenaltyBox(String player) implements Event {
    public PlayerWasSentToPenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }
}
