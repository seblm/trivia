package com.adaptionsoft.games.trivia.event;

import com.adaptionsoft.games.trivia.Category;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "(?<category>.+) Question (?<questionNumber>\\d+)")
public class QuestionWasAsked implements Event {

    public final Category category;
    public final int questionNumber;

    public QuestionWasAsked(Category category, int questionNumber) {
        this.category = category;
        this.questionNumber = questionNumber;
    }

    public QuestionWasAsked(Matcher matcher) {
        this(Category.valueOf(matcher.group("category")), Integer.parseInt(matcher.group("questionNumber")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionWasAsked that = (QuestionWasAsked) o;
        return Objects.equals(questionNumber, that.questionNumber) &&
                Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, questionNumber);
    }

    @Override
    public String toString() {
        return "QuestionWasAsked(" + category + ", " + questionNumber + ')';
    }

}
