package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<player>.+)'s new location is (?<newLocation>-?\\d+)")
public class LocationWas implements Event {
    public final String player;
    public final int newLocation;

    public LocationWas(Matcher matcher) {
        this(matcher.group("player"), Integer.parseInt(matcher.group("newLocation")));
    }
}
