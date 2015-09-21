package com.adaptionsoft.games.trivia.query;

import com.adaptionsoft.games.trivia.Category;

import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;

public class Questions {
    final HashMap<Category, List<Integer>> questions;

    public Questions() {
        this.questions = new HashMap<>();
        stream(Category.values()).forEach(category -> questions.put(category, range(0, 50).mapToObj(Integer::valueOf).collect(toList())));
    }

    public Questions remove(Category category, int questionNumber) {
        Optional.ofNullable(questions.get(category)).ifPresent(questionsForCategory -> questionsForCategory.remove(new Integer(questionNumber)));
        return this;
    }

    public Questions copy() {
        Questions copy = new Questions();
        questions.forEach((category, questions) -> {
            ArrayList<Integer> questionsCopy = new ArrayList<>();
            questions.forEach(questionsCopy::add);
            copy.questions.put(category, questionsCopy);
        });
        return copy;
    }
}
