package com.adaptionsoft.games.trivia.event;

import com.adaptionsoft.games.trivia.Category;

import java.util.Objects;
import java.util.regex.Matcher;

@EventAnnotation(pattern = "The category is (?<category>.+)")
public class CategoryWas implements Event {
    public final Category category;

    public CategoryWas(Category category) {
        this.category = category;
    }

    public CategoryWas(Matcher matcher) {
        this(Category.valueOf(matcher.group("category")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoryWas that = (CategoryWas) o;
        return Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category);
    }

    @Override
    public String toString() {
        return "CategoryWas(" + category + ')';
    }
}
