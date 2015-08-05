package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "Question was incorrectly answered")
public record AnswerWasNotCorrect() implements Event {
    public AnswerWasNotCorrect(Matcher matcher) {
        this();
    }
}
