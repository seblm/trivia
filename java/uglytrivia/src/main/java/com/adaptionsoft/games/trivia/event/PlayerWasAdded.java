package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<player>.+) was added")
public class PlayerWasAdded implements Event {
    public final String name;

    public PlayerWasAdded(Matcher matcher) {
        this(matcher.group("player"));
    }
}
