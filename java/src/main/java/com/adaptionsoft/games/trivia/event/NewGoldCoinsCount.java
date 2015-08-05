package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<player>.+) now has (?<newGoldCoinsCount>\\d+) Gold Coins.")
public record NewGoldCoinsCount(String player, int newGoldCoinsCount) implements Event {
    public NewGoldCoinsCount(Matcher matcher) {
        this(matcher.group("player"), Integer.parseInt(matcher.group("newGoldCoinsCount")));
    }
}
