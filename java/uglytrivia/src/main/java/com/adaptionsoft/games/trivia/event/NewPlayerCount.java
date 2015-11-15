package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "They are player number (?<count>\\d+)")
public class NewPlayerCount implements Event {
    private final int count;

    public NewPlayerCount(Matcher matcher) {
        this(Integer.parseInt(matcher.group("count")));
    }
}
