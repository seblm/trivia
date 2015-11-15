package com.adaptionsoft.games.trivia.event;

import lombok.Value;

import java.util.regex.Matcher;

@Value
@EventAnnotation(pattern = "Answer was corre[c|n]t!!!!")
public class AnswerWasCorrect implements Event {
    public AnswerWasCorrect(Matcher matcher) {
    }
}
