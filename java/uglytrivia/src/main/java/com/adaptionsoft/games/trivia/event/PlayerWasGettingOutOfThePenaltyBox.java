package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<player>.+) is getting out of the penalty box")
public class PlayerWasGettingOutOfThePenaltyBox implements Event {
    public final String player;

    public PlayerWasGettingOutOfThePenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }
}
