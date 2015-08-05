package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+)'s new location is (?<newLocation>-?\\d+)")
public record LocationWas(String player, int newLocation) implements Event {
    public LocationWas(Matcher matcher) {
        this(matcher.group("player"), Integer.parseInt(matcher.group("newLocation")));
    }
}
