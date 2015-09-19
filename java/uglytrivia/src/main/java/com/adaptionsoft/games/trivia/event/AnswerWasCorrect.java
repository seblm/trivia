package com.adaptionsoft.games.trivia.event;

import java.util.regex.Matcher;

@EventAnnotation(pattern = "Answer was corre[c|n]t!!!!")
public class AnswerWasCorrect implements Event {
    
    public AnswerWasCorrect(Matcher matcher) {
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
        return "AnswerWasCorrect";
    }

}
