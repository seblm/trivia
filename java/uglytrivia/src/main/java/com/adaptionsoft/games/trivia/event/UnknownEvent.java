package com.adaptionsoft.games.trivia.event;

import lombok.Value;

@Value
@EventAnnotation(unknownEvent = true)
public class UnknownEvent implements Event {
    private final String unknownEvent;
}
