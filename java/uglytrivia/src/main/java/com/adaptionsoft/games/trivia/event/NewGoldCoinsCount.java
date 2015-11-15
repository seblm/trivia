package com.adaptionsoft.games.trivia.event;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<player>.+) now has (?<newGoldCoinsCount>\\d+) Gold Coins.")
public class NewGoldCoinsCount implements Event {
    public final String player;
    public final int newGoldCoinsCount;

    public NewGoldCoinsCount(Matcher matcher) {
        this(matcher.group("player"), Integer.parseInt(matcher.group("newGoldCoinsCount")));
    }
}
