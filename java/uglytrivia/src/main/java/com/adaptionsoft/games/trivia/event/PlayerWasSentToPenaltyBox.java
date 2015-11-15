package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<player>.+) was sent to the penalty box")
public class PlayerWasSentToPenaltyBox implements Event {
    public final String player;

    public PlayerWasSentToPenaltyBox(Matcher matcher) {
        this(matcher.group("player"));
    }
}
