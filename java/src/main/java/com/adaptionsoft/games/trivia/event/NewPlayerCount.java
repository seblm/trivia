package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "They are player number (?<count>\\d+)")
public record NewPlayerCount(int count) implements Event {
    public NewPlayerCount(Matcher matcher) {
        this(Integer.parseInt(matcher.group("count")));
    }
}
