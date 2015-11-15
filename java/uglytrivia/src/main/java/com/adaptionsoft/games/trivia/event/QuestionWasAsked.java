package com.adaptionsoft.games.trivia.event;

import com.adaptionsoft.games.trivia.Category;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "(?<category>.+) Question (?<questionNumber>\\d+)")
public class QuestionWasAsked implements Event {
    public final Category category;
    public final int questionNumber;

    public QuestionWasAsked(Matcher matcher) {
        this(Category.valueOf(matcher.group("category")), Integer.parseInt(matcher.group("questionNumber")));
    }
}
