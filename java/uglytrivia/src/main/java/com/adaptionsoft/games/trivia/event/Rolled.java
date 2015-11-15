package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "They have rolled a (?<roll>-?\\d+)")
public class Rolled implements Event {
    public final int roll;

    public Rolled(Matcher matcher) {
        this(Integer.parseInt(matcher.group("roll")));
    }
}
