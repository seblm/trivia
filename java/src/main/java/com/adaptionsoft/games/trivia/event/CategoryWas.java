package com.adaptionsoft.games.trivia.event;

import com.adaptionsoft.games.trivia.Category;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "The category is (?<category>.+)")
public record CategoryWas(Category category) implements Event {
    public CategoryWas(Matcher matcher) {
        this(Category.valueOf(matcher.group("category")));
    }
}
