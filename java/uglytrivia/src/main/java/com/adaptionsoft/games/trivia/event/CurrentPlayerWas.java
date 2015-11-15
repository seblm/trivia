package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<player>.+) is the current player")
public class CurrentPlayerWas implements Event {
    public final String player;

    public CurrentPlayerWas(Matcher matcher) {
        this(matcher.group("player"));
    }
}
