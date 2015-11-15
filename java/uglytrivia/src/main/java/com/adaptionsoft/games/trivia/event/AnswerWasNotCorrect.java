package com.adaptionsoft.games.trivia.event;

import lombok.Value;

import java.util.regex.Matcher;

@Value
@EventAnnotation(pattern = "Question was incorrectly answered")
public class AnswerWasNotCorrect implements Event {
    public AnswerWasNotCorrect(Matcher matcher) {
    }
}
