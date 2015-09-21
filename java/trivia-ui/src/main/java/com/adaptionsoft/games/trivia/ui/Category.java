package com.adaptionsoft.games.trivia.ui;

import processing.core.PApplet;

import java.util.Optional;

import static java.util.Optional.empty;

public enum Category {

    POP(200, 67, 67),
    SCIENCE(97, 142, 200),
    SPORTS(77, 188, 30),
    ROCK(51, 41, 51);

    final int red;
    final int green;
    final int blue;

    Category(int red, int green, int blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public void fill(PApplet context) {
        context.fill(red, green, blue);
    }

    public static Category fromIndex(int index) {
        return Category.values()[index % Category.values().length];
    }

    static Optional<Category> fromName(String name) {
        try {
            return Optional.of(valueOf(name.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return empty();
        }
    }

}
