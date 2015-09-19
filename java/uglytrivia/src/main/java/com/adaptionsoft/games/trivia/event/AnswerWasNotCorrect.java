package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "Question was incorrectly answered")
public class AnswerWasNotCorrect implements Event {

    public AnswerWasNotCorrect(Matcher matcher) {
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj || !(obj == null || getClass() != obj.getClass());
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "AnswerWasNotCorrect";
    }

}
