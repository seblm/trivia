package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "Answer was corre[c|n]t!!!!")
public record AnswerWasCorrect() implements Event {
    public AnswerWasCorrect(Matcher matcher) {
        this();
    }
}
