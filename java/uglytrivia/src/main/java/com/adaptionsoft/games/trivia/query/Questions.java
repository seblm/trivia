package com.adaptionsoft.games.trivia.query;

import com.adaptionsoft.games.trivia.Category;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
}
