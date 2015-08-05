package com.adaptionsoft.games.trivia.event;

public @interface EventAnnotation {
    String pattern() default "";

    boolean unknownEvent() default false;
}
