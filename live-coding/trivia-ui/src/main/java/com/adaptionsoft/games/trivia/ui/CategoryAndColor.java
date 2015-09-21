package com.adaptionsoft.games.trivia.ui;

import com.adaptionsoft.games.trivia.Category;

import java.util.HashMap;
import java.util.Map;

import static com.adaptionsoft.games.trivia.Category.*;

public class CategoryAndColor {
    Category category;
    int red;
    int green;
    int blue;

    private CategoryAndColor(Category category, int red, int green, int blue) {
        this.category = category;
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public static final Map<Integer, CategoryAndColor> categoryAndColorByLocation = new HashMap<>();

    static {
        CategoryAndColor sports = new CategoryAndColor(Sports, 200, 67, 67);
        CategoryAndColor rock = new CategoryAndColor(Rock, 97, 142, 200);
        CategoryAndColor pop = new CategoryAndColor(Pop, 77, 188, 30);
        CategoryAndColor science = new CategoryAndColor(Science, 51, 41, 51);

        categoryAndColorByLocation.put(0, sports);
        categoryAndColorByLocation.put(1, rock);
        categoryAndColorByLocation.put(2, pop);
        categoryAndColorByLocation.put(3, science);
        categoryAndColorByLocation.put(4, sports);
        categoryAndColorByLocation.put(5, rock);
        categoryAndColorByLocation.put(6, pop);
        categoryAndColorByLocation.put(7, science);
        categoryAndColorByLocation.put(8, sports);
        categoryAndColorByLocation.put(9, rock);
        categoryAndColorByLocation.put(10, pop);
        categoryAndColorByLocation.put(11, science);
    }

    public void fill(TriviaClient parent) {
        parent.fill(red, green, blue);
    }

}
