package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<player>.+) is not getting out of the penalty box")
public class PlayerWasNotGettingOutOfThePenaltyBox implements Event {
    private final String player;

    public PlayerWasNotGettingOutOfThePenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }
}
