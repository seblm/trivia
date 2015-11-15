package com.adaptionsoft.games.trivia.event;

import com.adaptionsoft.games.trivia.Category;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.regex.Matcher;

@Value
@RequiredArgsConstructor
@EventAnnotation(pattern = "The category is (?<category>.+)")
public class CategoryWas implements Event {
    public final Category category;

    public CategoryWas(Matcher matcher) {
        this(Category.valueOf(matcher.group("category")));
    }
}
