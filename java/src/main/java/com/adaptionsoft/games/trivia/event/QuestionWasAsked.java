package com.adaptionsoft.games.trivia.event;

import com.adaptionsoft.games.trivia.Category;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<category>.+) Question (?<questionNumber>\\d+)")
public record QuestionWasAsked(Category category, int questionNumber) implements Event {
    public QuestionWasAsked(Matcher matcher) {
        this(Category.valueOf(matcher.group("category")), Integer.parseInt(matcher.group("questionNumber")));
    }
}
